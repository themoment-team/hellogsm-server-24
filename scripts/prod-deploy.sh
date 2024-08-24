#!/bin/bash

IMAGE_NAME=hellogsm-prod-server-img
CONTAINER_NAME=hellogsm-prod-server
DOCKERFILE_NAME=DockerfileProd

echo "> 현재 실행 중인 Docker 컨테이너 ID 확인" >> /home/ec2-user/deploy.log
CURRENT_CONTAINER_ID=$(docker ps -q -f name=$CONTAINER_NAME)

if [ -z $CURRENT_CONTAINER_ID ]
then
  echo "> 현재 구동중인 Docker 컨테이너가 없으므로 종료하지 않습니다." >> /home/ec2-user/deploy.log
else
  echo "> sudo docker stop $CURRENT_CONTAINER_ID"
  sudo docker stop $CURRENT_CONTAINER_ID
  sudo docker rm $CURRENT_CONTAINER_ID
fi

cd /home/ec2-user/builds/
docker build -t $IMAGE_NAME -f $DOCKERFILE_NAME .
docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME