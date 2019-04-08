package org.mccaughey.testings;

import java.net.URL;
import java.util.*;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import org.geotools.data.DataUtilities;
import org.json.*;
import org.mccaughey.connectivity.NetworkBufferOMS;
import org.mccaughey.utilities.GeoJSONUtilities;


public class MainTestHadoop  {
    //Mapper class
    public static class E_EMapper extends MapReduceBase implements
            Mapper<LongWritable ,/*Input key Type */
                    Text,                /*Input value Type*/
                    Text,                /*Output key Type*/
                    Text>        /*Output value Type*/
    {
        //Map function
        public void map(LongWritable key, Text value,
                        OutputCollector<Text, Text> output,
                        Reporter reporter) throws IOException {

            String line = value.toString();
            try{
                JSONObject collectionObj = new JSONObject(line);
                JSONArray featureArrays = (JSONArray) collectionObj.get("features");
                for(int i=0; i<featureArrays.length(); i++){
                    JSONObject currObject = featureArrays.getJSONObject(i);
                    String currKey = currObject.getString("id");
                    JSONObject newObj = new JSONObject();
                    newObj.put("type", "FeatureCollection");
                    newObj.put("features", currObject);
                    output.collect(new Text(currKey), new Text(newObj.toString()));
                }

            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

        //Reducer class
        public static class E_EReduce extends MapReduceBase implements Reducer< Text, Text, Text, Text > {

            //Reduce function
            public void reduce( Text key, Iterator <Text> values,
                                OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
                //We have to create temp json file
                try{
                    //We only have one value actually.
                    while (values.hasNext()) {
                        Text val = values.next();
                        File file=new File("src/main/resources/"+ key + ".json");
                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file);

                        fileWriter.write(val.toString());
                        fileWriter.flush();
                        fileWriter.close();


                        //Process this small temp json file, then delete the file
                        URL roadsUrl = MainTest.class.getClass().getResource("/psma_cut_projected.geojson.gz");
                        URL pointsUrl = MainTest.class.getClass().getResource("src/main/resources/"+ key + ".json");

                        NetworkBufferOMS networkBufferOMS = new NetworkBufferOMS();
                        networkBufferOMS.network = DataUtilities.source(GeoJSONUtilities.readFeatures(roadsUrl));
                        networkBufferOMS.points = DataUtilities.source(GeoJSONUtilities.readFeatures(pointsUrl));
                        networkBufferOMS.bufferSize = 100.0;
                        networkBufferOMS.distance = 1600.0;
                        networkBufferOMS.run();

                        GeoJSONUtilities.writeFeatures(networkBufferOMS.regions.getFeatures(),
                                new File("src/main/resources/"+key+"_networkBufferOMSTest.json").toURI().toURL());

                        output.collect(key, new Text("src/main/resources/"+key+"_networkBufferOMSTest.json"));
                        file.delete();
                        System.out.println("tempfile 1 has been deleted");
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    //Main function
    public static void main(String args[])throws Exception {
        JobConf conf = new JobConf(MainTestHadoop.class);

        conf.setJobName("mapreduce_neighbourhood_generation");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);
        conf.setMapperClass(E_EMapper.class);
        conf.setCombinerClass(E_EMapper.E_EReduce.class);
        conf.setReducerClass(E_EMapper.E_EReduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, "/RndmMultiPoint5ptsProjected.json");
        FileOutputFormat.setOutputPath(conf, new Path("target/"));

        JobClient.runJob(conf);
    }
}
