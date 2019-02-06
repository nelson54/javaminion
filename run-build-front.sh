#!/bin/bash

docker-compose exec app bash -c "cd /app/src/main/js/; bash ./build.sh;"