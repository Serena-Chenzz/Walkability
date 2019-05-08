package org.mccaughey;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class transform {
    public static void main(String[] args){

        JSONParser parser = new JSONParser();
        JSONObject resultObj = new JSONObject();
        try (FileReader reader = new FileReader("src/main/java/org/mccaughey/Rndm5ptsProjected.json"))
        {
            //Read JSON file
            JSONObject obj = (JSONObject) parser.parse(reader);

            JSONArray features = (JSONArray) parser.parse(obj.get("features").toString());

            JSONArray newFeatures = new JSONArray();

            int counter = 1;

            while(counter <= 200){
                for(int i=0; i<features.size(); i++){
                    newFeatures.add(features.get(i));
                }
                counter++;
            }

            resultObj.put("type", "FeatureCollection");
            resultObj.put("features", newFeatures);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter("src/main/java/org/mccaughey/Rndm1000ptsProjected.json")) {

            file.write(resultObj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
