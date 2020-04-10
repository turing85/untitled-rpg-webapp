#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ${SCRIPT_DIR}

echo "================================================================================"
echo "Starting docker deployments"
echo "================================================================================"
docker-compose up -d

cd ..
echo "================================================================================"
echo "Migrating language database"
echo "================================================================================"
./mvnw flyway:migrate --projects :deployments.quarkus.microservices.language.impl

echo "================================================================================"
echo "Migrating user database"
echo "================================================================================"
./mvnw flyway:migrate --projects :deployments.quarkus.microservices.user.impl
