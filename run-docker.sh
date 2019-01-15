#!/bin/bash

old_container_id=$(docker ps | grep javaminion | awk '{print $1}')
old_image_id=$(docker images | grep javaminion | awk '{print $3}')

if (( $(echo $old_container_id | wc -l) > 1 ));
then
    docker stop $old_container_id
fi;


if (( $(echo $old_image_id | wc -l) > 1 ));
then
    docker rmi -f $old_image_id
fi;

docker build -t javaminion .
docker run -it -p 8080:8080 -v $PWD:/app javaminion

