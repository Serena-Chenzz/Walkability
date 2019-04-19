#!/usr/bin/env bash


./apache-activemq-5.15.6/bin/activemq start

mvn exec:java -Dexec.mainClass="org.mccaughey.SendPoint"
mvn exec:java -Dexec.mainClass="org.mccaughey.GeneratePolygon"
mvn exec:java -Dexec.mainClass="org.mccaughey.GenerateConnectivity"
mvn exec:java -Dexec.mainClass="org.mccaughey.GenerateDensity"
mvn exec:java -Dexec.mainClass="org.mccaughey.GenerateLUM"
mvn exec:java -Dexec.mainClass="org.mccaughey.GenerateZScore"