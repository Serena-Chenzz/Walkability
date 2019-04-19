step 1: use Dockerfile to build image, it may take 15 mins, since step "mvn compile" needs to download a large amount of dependencies.
step 2: run docker, command: sudo docker run -v /home/ubuntu/.m2:/root/.m2 -it -p 8161:8161 <image name>. Then type hostIP:8161
in browser to find ActiveMQ.
