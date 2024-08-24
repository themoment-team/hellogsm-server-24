FROM openjdk:17

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} hellogsm-prod-server.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "hellogsm-prod-server.jar"]