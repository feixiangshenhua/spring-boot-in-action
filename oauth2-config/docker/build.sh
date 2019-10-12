# 删除本地的旧版本镜像
docker rmi imagesregistry-test:5000/nari-oauth2-server:1.0.0
# 根据Dockerfile生成新的镜像
docker build -t imagesregistry-test:5000/nari-oauth2-server:1.0.0 .
# 推送镜像到仓库
docker push imagesregistry-test:5000/nari-oauth2-server:1.0.0
# 删除k8s旧的部署
kubectl delete -f nari-oauth2-deployment.yaml
# 使用k8s部署
kubectl create -f nari-oauth2-deployment.yaml --record
