#!/bin/bash
rm -Rf ./dist;

mkdir dist;

./node_modules/grunt-cli/bin/grunt build;

mkdir ./dist/styles;
sass ./app/styles/main.scss ./dist/styles/main.css;

./copy-resources.sh

