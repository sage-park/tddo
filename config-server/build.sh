#! /bin/bash

./gradlew clean build -x test

docker build -t tddo-config-server:0.0.1 .
