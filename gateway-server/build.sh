#! /bin/bash

./gradlew clean build

docker build -t tddo-gateway-server:0.0.1 .
