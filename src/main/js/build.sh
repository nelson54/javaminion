#!/bin/bash
rm -Rf ./dist;

mkdir dist;

./node_modules/grunt-cli/bin/grunt build;

mkdir ./dist/styles;
sass ./app/styles/main.scss ./dist/styles/main.css;
cp -R ./app/images ./dist/images;

cp -R .tmp/concat/scripts ./dist/scripts;

rm -Rf ../resources/public
mkdir ../resources/public

cd ./dist
cp -R * ../../resources/public

cd ../../../..;

./gradlew build

