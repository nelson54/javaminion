FROM gradle:5.2.1-jdk11-slim

ENV APP_HOME /app
WORKDIR $APP_HOME


COPY ./ /app

CMD ./gradlew bootRun -Dspring.profiles.active=prod
