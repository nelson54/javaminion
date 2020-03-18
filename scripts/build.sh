#!/usr/bin/env bash

cd ./src/main/node

npm install

npm run build

rm -Rf ../resources/public
mkdir -p ../resources/public



cp -R ./dist/* ../resources/public

cd ../../../

#./gradlew bootJar
docker-compose up