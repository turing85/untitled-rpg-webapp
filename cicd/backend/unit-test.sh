#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Running unit tests for the backend submodules"
echo "================================================================================"
cd ../..
mvn \
  ${MVN_CLI_OPTS} \
  --activate-profiles cicd,!unit-test-coverage \
  -DskipTests=false \
  test
echo "--------------------------------------------------------------------------------"
echo "Surefire test reports for the backend submodules are available at:"
echo "    ${PWD}/target/surefire-reports"
echo "--------------------------------------------------------------------------------"
cd cicd/backend/deployments
