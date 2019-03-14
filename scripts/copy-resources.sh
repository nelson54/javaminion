#!/usr/bin/env bash

cd /build
tar -xvzf ./front-end.tar.gz

cd dist
cp -R * /root/javaminion/src/main/resources/public