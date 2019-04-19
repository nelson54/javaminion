#!/usr/bin/env sh

cd /app

./gradlew check -x spotbugsMain -x spotbugsTest -x test

cd /app/build/reports/checkstyle
tar -cvzf ./checkstyle.tar.gz .
chmod 777 ./checkstyle.tar.gz
mv ./checkstyle.tar.gz /archives/