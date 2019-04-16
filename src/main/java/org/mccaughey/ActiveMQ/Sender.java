package org.mccaughey.ActiveMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollectionIteration;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.feature.FeatureJSON.*;
import org.mccaughey.utilities.GeoJSONUtilities;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.geometry.aggregate.MultiPoint;

import java.io.IOException;
import java.net.URL;


public class Sender {
    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subject = "JCG_QUEUE"; // Queue Name.

    public static void main(String[] argv){
        Sender p = new Sender();
        p.run();
    }

    private void run(){
        try{
            // Getting JMS connection from the server and starting it
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            //Creating a non transactional session to send/receive JMS message.
            Session session = connection.createSession(false,
                    Session.DUPS_OK_ACKNOWLEDGE);

            //Destination represents here our queue 'JCG_QUEUE' on the JMS server.
            //The queue will be created automatically on the server.
            Destination destination = session.createQueue(subject);

            // MessageProducer is used for sending messages to the queue.
            MessageProducer producer = session.createProducer(destination);

            // read in the points
            URL pointsUrl = Sender.class.getClass().getResource("/Rndm5ptsProjected.json");
            SimpleFeatureIterator points = GeoJSONUtilities.readFeatures(pointsUrl).features();

            while(points.hasNext()){
                SimpleFeature point = points.next();
                FeatureJSON fjson = new FeatureJSON();
                String output = fjson.toString(point);
                TextMessage message = session
                        .createTextMessage(output);

                // Here we are sending our points!
                producer.send(message);
                System.out.println("Sent point: '" + message.getText() + "'");
            }

            connection.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(JMSException e){
            e.printStackTrace();
        }
    }


}
