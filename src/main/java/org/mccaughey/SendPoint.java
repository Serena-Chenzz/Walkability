package org.mccaughey;

import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.mccaughey.ActiveMQ.Sender;
import org.mccaughey.utilities.GeoJSONUtilities;
import org.opengis.feature.simple.SimpleFeature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class SendPoint {
    public static void main(String[] args){
        Sender p = new Sender("PointQueue");
        p.run();

        try{
            URL pointsUrl = new File("./src/main/java/org/mccaughey/Rndm5ptsProjected.json").toURI().toURL();
            System.out.println(pointsUrl.toString());
            SimpleFeatureIterator points = GeoJSONUtilities.readFeatures(pointsUrl).features();

            while(points.hasNext()){
                SimpleFeature point = points.next();
                FeatureJSON fjson = new FeatureJSON();
                String output = fjson.toString(point);
                p.sendMessage(output);
            }

            File file_region = new File("./src/main/java/org/mccaughey/output/regionOMS.geojson");
            //If the file has already existed, the content will be overwritten.
            if (!file_region.exists()) {
                file_region.createNewFile();
            }
            FileOutputStream fout_0 = new FileOutputStream(file_region, false);
            fout_0.write("{\"type\":\"FeatureCollection\",\"features\":[".getBytes());
            fout_0.flush();
            fout_0.close();

            File file_connectivity = new File("./src/main/java/org/mccaughey/output/connectivityOMS.geojson");
            //If the file has already existed, the content will be overwritten.
            if (!file_connectivity.exists()) {
                file_connectivity.createNewFile();
            }
            FileOutputStream fout_1 = new FileOutputStream(file_connectivity, false);
            fout_1.write("{\"type\":\"FeatureCollection\",\"features\":[".getBytes());
            fout_1.flush();
            fout_1.close();

            File file_density = new File("./src/main/java/org/mccaughey/output/densityOMS.geojson");
            //If the file has already existed, the content will be overwritten.
            if (!file_density.exists()) {
                file_density.createNewFile();
            }
            FileOutputStream fout_2 = new FileOutputStream(file_density, false);
            fout_2.write("{\"type\":\"FeatureCollection\",\"features\":[".getBytes());
            fout_2.flush();
            fout_2.close();

            File file_lum = new File("./src/main/java/org/mccaughey/output/lumOMS.geojson");
            //If the file has already existed, the content will be overwritten.
            if (!file_lum.exists()) {
                file_lum.createNewFile();
            }
            FileOutputStream fout_3 = new FileOutputStream(file_lum, false);
            fout_3.write("{\"type\":\"FeatureCollection\",\"features\":[".getBytes());
            fout_3.flush();
            fout_3.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            p.close();
        }


    }
}
