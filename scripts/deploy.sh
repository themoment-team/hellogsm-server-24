#!/bin/bash

# 배포 그룹 이름을 확인합니다. (CodeDeploy 환경 변수를 사용)
DEPLOYMENT_GROUP_NAME=$(echo $DEPLOYMENT_GROUP_NAME)

# 배포 그룹에 따라 다른 작업을 수행합니다.
if [ "$DEPLOYMENT_GROUP_NAME" == "dev-web-server" ]; then
  chmod +x /home/ec2-user/builds/scripts/dev-deploy.sh
  /home/ec2-user/builds/scripts/dev-deploy.sh

elif [ "$DEPLOYMENT_GROUP_NAME" == "prod-web-server" ]; then
  chmod +x /home/ec2-user/builds/scripts/prod-deploy.sh
  /home/ec2-user/builds/scripts/prod-deploy.sh

fi