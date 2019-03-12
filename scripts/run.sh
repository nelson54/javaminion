#!/usr/bin/env bash

cd /build
tar -xvzf ./front-end.tar.gz

cd dist
cp -R * /app/src/main/resources/public

cd /app

./gradlew bootRun -Dspring.profiles.active=prod