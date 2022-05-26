#!/bin/bash
mvn clean
mvn package
docker build -t java-lab3 .
docker run -p 8787:8787 java-lab3