base_dir="/home/OMSIII/nari-oauth2"
if [ -n "$1" ]; then
   version=$1
   echo "本次升级版本为："+$1
else
    echo "请输入版本号！例如：./rollout.sh 20181122"
    exit
fi
echo $version
echo `date "+%F %H:%M:%S"` >>${base_dir}/logs/rollout.log
kubectl rollout history deployment nari-oauth2-server >>${base_dir}/logs/rollout.log
echo "构建镜像..."+$version
docker build -t imagesregistry-test:5000/nari-oauth2-server:$version .
echo "上传仓库..."
docker push imagesregistry-test:5000/nari-oauth2-server:$version
echo "进行滚动升级...版本"+$version
kubectl set image deployment/nari-oauth2-server nari-oauth2-server=imagesregistry-test:5000/nari-oauth2-server:$version
docker rmi imagesregistry-test:5000/nari-oauth2-server:$version
echo "完成！"
