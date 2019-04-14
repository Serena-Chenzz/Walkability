use maven to compile and run java files.
step 1: mvn clean
step 2: mvn compile
step 3: mvn exec:java -Dexec.mainClass="receiver"  #or sender

then check result in browser: instanceIP:8161  (alread add 8161 port in security group, can use it directly), will see results in ActiveMQ UI.

