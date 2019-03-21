#!/usr/bin/env sh

cd /app

./gradlew jacocoTestReport

cd /app/build/reports/checkstyle
tar -cvzf ./checkstyle.tar.gz .
chmod 777 ./checkstyle.tar.gz
mv ./checkstyle.tar.gz /archives/