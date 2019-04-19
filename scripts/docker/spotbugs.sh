#!/usr/bin/env sh

cd /app

./gradlew spotbugsMain || true

cd /app/build/reports/spotbugs
tar -cvzf ./spotbugs.tar.gz .
chmod 777 ./spotbugs.tar.gz
mv ./spotbugs.tar.gz /archives/