create pods on master node:
command: kubectl create -f <yamlfilename> 
ps:due to memory limit, can only create at most 2 pods at the same time, delete useless pod by-
kubectl delete pod <podname>. It doesn't affect execution, since all data has been sent to activemq.

check container running status: kubectl log <podname>
check pod status: kubectl describe pod <podname>
check node status: kubectl describe nodes

