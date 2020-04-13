#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Running unit tests with coverage report for the project"
echo "================================================================================"
cd ../../..
./mvnw \
  "${MVN_CLI_OPTS}" \
  -DskipTests=false \
  --activate-profiles unit-test-coverage \
  verify
cd cicd/backend/deployments
echo "--------------------------------------------------------------------------------"
echo "Test reports for the backend are available at:"
echo "    ${PWD}/backend/testaggregation/target/site/jacoco-aggregate-ut/index.html"
echo "--------------------------------------------------------------------------------"