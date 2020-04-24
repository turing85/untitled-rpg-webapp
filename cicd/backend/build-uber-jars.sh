#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Building the backend standalone uber-jars"
echo "================================================================================"
cd ../..
mvn \
  ${MVN_CLI_OPTS} \
  --activate-profiles cicd \
  -DskipTests \
  -Dquarkus.package.uber-jar=true \
  package
echo "--------------------------------------------------------------------------------"
echo "The relevant build artifacts are available at:"
echo "    ${PWD}/backend/target"
echo "--------------------------------------------------------------------------------"
cd cicd/backend/deployments
