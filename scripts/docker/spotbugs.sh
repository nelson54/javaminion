#!/usr/bin/env bash

cd /app

./gradlew check || true

cd /app/build/reports/spotbugs
tar -cvzf ./spotbugs.tar.gz .
chmod 777 ./spotbugs.tar.gz
mv ./spotbugs.tar.gz /archives/