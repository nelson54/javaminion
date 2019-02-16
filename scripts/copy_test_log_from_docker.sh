#!/bin/bash

docker cp $(docker ps -a -q -f "name=javaminion_app" | head -n 1):/app/build/reports/tests/test/index.html /root/