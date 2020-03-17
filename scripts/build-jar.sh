#!/usr/bin/env bash

SPRING_PROFILES_ACTIVE=prod ./gradlew build -x spotbugsMain -x spotbugsTest -x test -x jacocoTestReport -x checkstyleMain -x checkstyleTest
./gradlew check -x spotbugsMain -x spotbugsTest -x test javadoc || true
./gradlew spotbugsMain || true
#./gradlew test jacocoTestReport || true
./gradlew bootJar

