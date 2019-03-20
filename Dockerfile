FROM openjdk:13-ea-9-jdk-alpine3.9

ENV APP_HOME /app
WORKDIR $APP_HOME


COPY ./ /app

CMD ./gradlew bootRun -Dspring.profiles.active=prod
