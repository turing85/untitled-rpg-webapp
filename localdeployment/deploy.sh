#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ${SCRIPT_DIR}

echo "================================================================================"
echo "Starting docker deployments: postgres and keycloak"
echo "================================================================================"
docker-compose up -d postgres keycloak

if [[ ${BUILD_PROJECT} == true ]]; then
  echo "================================================================================"
  echo "Building project and generating docker images"
  echo "================================================================================"
  cd ../
  ./mvnw -Pdocker -P!unit-test-coverage -DskipTests package
  cd localdeployment
fi

echo "================================================================================"
echo "Migrating language database"
echo "================================================================================"
cd ..
./mvnw flyway:migrate --projects :deployments.quarkus.microservices.language.impl
cd localdeployment

echo "================================================================================"
echo "Starting docker deployments: language-service"
echo "================================================================================"
docker-compose up -d language

echo "================================================================================"
echo "Migrating user database"
echo "================================================================================"
cd ..
./mvnw flyway:migrate --projects :deployments.quarkus.microservices.user.impl
cd localdeployment

echo "================================================================================"
echo "Starting docker deployments: user-service"
echo "================================================================================"
docker-compose up -d user
