FROM openjdk:11.0.2-jdk-slim-stretch

ENV APP_HOME /app
WORKDIR $APP_HOME

CMD ./gradlew bootRun
