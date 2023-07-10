#!/bin/bash
docker-compose -f test-compose.yml down \
  && docker-compose -f test-compose.yml up -d \
	&& ./mvnw clean test \
	&& docker-compose -f test-compose.yml down
