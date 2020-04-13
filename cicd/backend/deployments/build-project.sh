#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Building the project"
echo "================================================================================"
cd ../../..
./mvnw \
  "${MVN_CLI_OPTS}" \
  -DskipTests \
  package
cd cicd/backend/deplyoments
echo "--------------------------------------------------------------------------------"
echo "The relevant build artifacts can be found in"
echo "    ${PWD}/backend/target"
echo "--------------------------------------------------------------------------------"