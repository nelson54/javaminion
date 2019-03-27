#!/usr/bin/env sh

/app/gradlew shadowJar

mv ./build/libs/dominionweb*.jar /archives/
