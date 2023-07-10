#!/bin/bash
sh test.sh \
  && docker-compose down \
  && ./mvnw clean package -DskipTests \
  && docker-compose up
