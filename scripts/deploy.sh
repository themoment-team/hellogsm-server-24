#!/bin/bash

# 배포 그룹에 따라 다른 작업을 수행합니다.
if [ "$DEPLOYMENT_GROUP_NAME" == "hello-stage" ]; then
  chmod +x /home/ec2-user/builds/scripts/dev-deploy.sh
  /home/ec2-user/builds/scripts/dev-deploy.sh

elif [ "$DEPLOYMENT_GROUP_NAME" == "hello-prod" ]; then
  chmod +x /home/ec2-user/builds/scripts/prod-deploy.sh
  /home/ec2-user/builds/scripts/prod-deploy.sh

fi