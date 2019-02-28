#!/bin/bash

docker exec $(docker ps -a -q -f "name=javaminion_app" | head -n 1) bash /app/scripts/create-test-archives.sh

docker cp $(docker ps -a -q -f "name=javaminion_app" | head -n 1):/app/build/reports/tests/test/index.html /root/

docker cp $(docker ps -a -q -f "name=javaminion_app" | head -n 1):/app/build/test-results/test-results.tar.gz /root/

docker cp $(docker ps -a -q -f "name=javaminion_app" | head -n 1):/app/build/reports/test-reports.tar.gz /root/