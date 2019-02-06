#!/usr/bin/env bash

cd /build/
tar -xvzf /build/front-end.tar.gz

cd /build/dist
cp -R * /app/src/main/resources/public
