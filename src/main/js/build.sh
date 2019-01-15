#!/bin/bash
rm -Rf ./dist;

mkdir dist;

./node_modules/grunt-cli/bin/grunt build;

mkdir ./dist/styles;
sass ./app/styles/main.scss ./dist/styles/main.css;
cp -R ./app/images ./dist/images;

cp -R .tmp/concat/scripts ./dist/scripts;

rm -Rf /app/src/main/resources/public
mkdir /app/src/main/resources/public

cd ./dist
cp -R * /app/src/main/resources/public

#cd /app/

#./gradlew -Dskip.tests processResources

