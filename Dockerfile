FROM openjdk:11.0.2-jdk-slim-stretch

ENV APP_HOME /app
WORKDIR $APP_HOME

#COPY ./build/libs/dominionweb-1.0-SNAPSHOT.jar $APP_HOME/dominion.jar
#CMD java -jar -Dspring.profiles.active=prod $APP_HOME/dominion.jar

CMD ./gradlew bootRun
