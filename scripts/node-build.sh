#!/bin/bash
cd ../src/main/js/

rm -Rf ./dist/*;

./node_modules/grunt-cli/bin/grunt build;

mkdir ./dist/styles;
sass ./app/styles/main.scss ./dist/styles/main.css;

cp -R ./app/images ./dist/images;

cp -R .tmp/concat/scripts ./dist/scripts;

rm -Rf /app/src/main/resources/public
mkdir /app/src/main/resources/public

rm -Rf /build/*

tar -zcvf /build/front-end.tar.gz ./dist
