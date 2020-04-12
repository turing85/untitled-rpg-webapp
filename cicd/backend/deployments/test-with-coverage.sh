#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

cd ../../..
echo "================================================================================"
echo "Running unit tests with coverage report"
echo "================================================================================"
./mvnw \
  ${MVN_CLI_OPTS} \
  -DskipTests=false \
  --activate-profiles unit-test-coverage \
  verify
echo "--------------------------------------------------------------------------------"
echo "Test reports for the backend are available at:"
echo "    ${PWD}/backend/testaggregation/target/site/jacoco-aggregate-ut/index.html"
echo "--------------------------------------------------------------------------------"