#!/bin/bash

docker exec -it $(docker ps | grep javaminion | awk '{print $1}') bash

