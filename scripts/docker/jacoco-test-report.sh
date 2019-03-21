#!/usr/bin/env sh

cd /app

./gradlew jacocoTestReport

cd /app/build/reports/jacoco
tar -cvzf ./checkstyle.tar.gz .
chmod 777 ./checkstyle.tar.gz
mv ./jacoco.tar.gz /archives/