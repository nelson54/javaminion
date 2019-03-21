#!/usr/bin/env sh

cd /app

./gradlew jacocoTestReport

cd /app/build/reports/jacoco
tar -cvzf ./jacoco.tar.gz .
chmod 777 ./jacoco.tar.gz
mv ./jacoco.tar.gz /archives/