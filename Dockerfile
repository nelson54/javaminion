FROM openjdk:13-ea-9-jdk-alpine3.9

COPY ./ /app

CMD ./gradlew bootRun -Dspring.profiles.active=prod
