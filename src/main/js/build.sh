#!/bin/bash
rm -Rf ./dist;

mkdir dist;

./node_modules/grunt-cli/bin/grunt build;

mkdir ./dist/styles;
sass ./app/styles/main.scss ./dist/styles/main.css;
cp -R ./app/images ./dist/images;

rm -Rf ../resources/public
mkdir ../resources/public

cp -R ./dist ../resources/public
