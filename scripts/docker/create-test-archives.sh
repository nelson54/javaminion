#!/usr/bin/env sh

cd /app

rm -Rf ./build/resources
SPRING_PROFILES_ACTIVE=test ./gradlew processResources test

cd /app/build/test-results
tar -cvzf ./test-results.tar.gz .
chmod 777 ./test-results.tar.gz
mv ./test-results.tar.gz /archives/

cd /app/build/reports
tar -cvzf ./test-reports.tar.gz .
chmod 777 ./test-reports.tar.gz
mv ./test-reports.tar.gz /archives/