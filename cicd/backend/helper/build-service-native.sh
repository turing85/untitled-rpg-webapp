#!/usr/bin/env bash
set -e

if [[ -z ${SERVICE} ]]; then
  echo "variable SERVICE is not set. It must be set to the service's name that should be compiled."
  return 1
fi

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

cd ../../..
echo "================================================================================"
echo "Building service ${SERVICE} with GraalVM natively"
echo "================================================================================"
./mvnw \
  ${MVN_CLI_OPTS} \
  -Pnative \
  -P!unit-test-coverage \
  -DskipTests \
  --projects :untitled-rpg-webapp.backend.deployments.quarkus.microservices."${SERVICE}".impl \
  package
chmod +x "${PWD}"/backend/target/"${SERVICE}"Service-runner
echo "--------------------------------------------------------------------------------"
echo "The native build artifact for %SERVICE% is available at"
echo "    ${PWD}/backend/target/${SERVICE}Service-runner"
echo "--------------------------------------------------------------------------------"