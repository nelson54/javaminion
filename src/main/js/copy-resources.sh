#!/bin/bash

cp -R ./app/images ./dist/images;

cp -R .tmp/concat/scripts ./dist/scripts;

rm -Rf /app/src/main/resources/public
mkdir /app/src/main/resources/public

cd ./dist
cp -R * /app/src/main/resources/public
