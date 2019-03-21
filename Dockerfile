FROM openjdk:11.0.2-jdk-slim-stretch

ENV APP_HOME /app
WORKDIR $APP_HOME

CMD $APP_HOME/gradlew bootRun -Dspring.profiles.active=prod
