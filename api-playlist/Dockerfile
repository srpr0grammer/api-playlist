FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/api-playlist.jar

ENV APP_HOME=/app

WORKDIR $APP_HOME

COPY ${JAR_FILE} api-playlist.jar

ENTRYPOINT ["java", "-jar", "api-playlist.jar"]
