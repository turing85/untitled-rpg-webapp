#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Starting docker deployments: jaeger, postgres and keycloak"
echo "================================================================================"
docker-compose up -d tracing-service dbms-service oidc-service

if [[ ${BUILD_PROJECT} == true ]]; then
  MIGRATE_DATABASES=true
  echo "================================================================================"
  echo "Building project and generating docker images"
  echo "================================================================================"
  cd ../
  ./mvnw -Pdocker -P!unit-test-coverage -DskipTests clean install
  cd localdeployment
fi

if [[ ${MIGRATE_DATABASES} == true ]]; then
  echo "================================================================================"
  echo "Migrating language database"
  echo "================================================================================"
  cd ..
  ./mvnw \
    --projects :untitled-rpg-webapp.backend.deployments.quarkus.microservices.language.impl \
    flyway:migrate
  cd localdeployment
fi

echo "================================================================================"
echo "Starting docker deployment: language-service"
echo "================================================================================"
docker-compose up -d language-service

if [[ ${MIGRATE_DATABASES} == true ]]; then
  echo "================================================================================"
  echo "Migrating user database"
  echo "================================================================================"
  cd ..
  ./mvnw \
    --projects :untitled-rpg-webapp.backend.deployments.quarkus.microservices.user.impl \
    flyway:migrate
  cd localdeployment
fi

echo "================================================================================"
echo "Starting docker deployment: user-service"
echo "================================================================================"
docker-compose up -d user-service
