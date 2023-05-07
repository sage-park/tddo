#! /bin/bash

./gradlew clean build

docker build -t tddo-discovery-server:0.0.1 .
