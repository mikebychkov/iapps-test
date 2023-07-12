#!/bin/bash
sh init.sh \
  && sh test.sh \
  && docker-compose down \
  && ./mvnw clean package -DskipTests \
  && docker-compose up
