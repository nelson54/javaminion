#!/bin/bash
cd /app/src/main/js/
bash ./build.sh

cd /app/
bash ./gradlew clean compileJava bootRun