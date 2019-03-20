FROM debian:stretch-slim

ENV APP_HOME /app
WORKDIR $APP_HOME


COPY ./ /app

CMD ./gradlew bootRun -Dspring.profiles.active=prod
