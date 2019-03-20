#!/usr/bin/env sh

cd /app

./gradlew javadoc

cd /app/build/docs/javadoc
tar -cvzf ./javadoc.tar.gz .
chmod 777 ./javadoc.tar.gz
mv ./javadoc.tar.gz /archives/