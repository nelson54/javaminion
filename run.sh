#!/usr/bin/env bash

cd /app/src/main/js/
bash ./copy-resources.sh;

cd /app/
bash ./gradlew -Dskip.tests bootRun