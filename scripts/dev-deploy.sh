#!/bin/bash

# 변수 설정
BUILD_JAR=/home/ec2-user/builds/build/libs/hellogsm-server-24-0.0.1-SNAPSHOT.jar
JAR_NAME=hellogsm-server-24-0.0.1-SNAPSHOT.jar
IMAGE_NAME=hellogsm-stage-server:latest
CONTAINER_NAME=hellogsm-stage-server
DOCKERFILE_NAME=DockerfileStage

CURRENT_CONTAINER_ID=$(docker ps -q -f name=$CONTAINER_NAME)

if [ ! -z "$CURRENT_CONTAINER_ID" ]
then
  echo "현재 구동 중인 컨테이너가 있습니다. 중지하고 삭제합니다."
  docker stop $CONTAINER_NAME
  docker rm $CONTAINER_NAME
fi

cd /home/ec2-user/builds/
docker build -t $IMAGE_NAME -f $DOCKERFILE_PATH .

docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME --spring.profiles.active=dev
