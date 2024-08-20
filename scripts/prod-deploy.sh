#!/bin/bash

echo "> 현재 실행 중인 Docker 컨테이너 pid 확인" >> /home/ec2-user/deploy.log
CURRENT_PID=$(sudo docker container ls -q)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 Docker 컨테이너가 없으므로 종료하지 않습니다." >> /home/ec2-user/deploy.log
else
  echo "> sudo docker stop $CURRENT_PID"
  sudo docker stop $CURRENT_PID
  sleep 5
fi

cd /home/ec2-user/builds
sudo docker build -t hellogsm-api-docker .
sudo docker run -d -p 8080:8080 hellogsm-api-docker