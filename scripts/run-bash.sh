#!/usr/bin/env bash

docker-compose exec app -it $(docker ps | grep javaminion | awk '{print $1}') bash

