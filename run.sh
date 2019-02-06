#!/usr/bin/env bash

cd /build/
tar -xvzf ./front-end.tar.gz

cd /build/dist
cp -R * /app/src/main/resources/public

cd /app
bash ./gradlew -Dskip.tests bootRun