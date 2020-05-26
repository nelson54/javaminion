#!/usr/bin/env bash


find ./src/main/node/src -type f \( -name "*.ts" -o -name "*.html" \) | entr -c ./scripts/build.sh