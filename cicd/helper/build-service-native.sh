#!/usr/bin/env bash
set -e

if [[ -z ${SERVICE} ]]; then
  echo "variable SERVICE is not set. It must be set to the service's name that should be compiled."
  return 1
fi

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ${SCRIPT_DIR}/../..

echo "================================================================================"
echo "Building service ${SERVICE} with GraalVM natively"
echo "================================================================================"
./mvnw -Pnative -pl :deployments.quarkus.microservices.${SERVICE}.impl package