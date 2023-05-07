#! /bin/bash

./gradlew clean build

docker build -t tddo-authentication-service:0.0.1 .
