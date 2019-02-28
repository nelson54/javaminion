#!/usr/bin/env bash

cd /app/build/test-results
tar -xvzf ./test-results.tar.gz

cd /app/build/reports
tar -xvzf ./test-reports.tar.gz