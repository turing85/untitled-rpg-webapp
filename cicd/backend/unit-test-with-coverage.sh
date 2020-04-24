#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Running unit tests with coverage report for the backend submodules"
echo "================================================================================"
cd ../..
mvn \
  ${MVN_CLI_OPTS} \
  --activate-profiles cicd,unit-test-coverage \
  -DskipTests=false \
  -Dquarkus.package.uber-jar=false \
  verify
echo "--------------------------------------------------------------------------------"
echo "Surefire test reports for the backend submodules are available at:"
echo "    ${PWD}/target/surefire-reports"
echo "--------------------------------------------------------------------------------"
echo "Jacoco test reports for the backend are available at:"
echo "    ${PWD}/backend/testaggregation/target/site/jacoco-aggregate-ut/index.html"
echo "--------------------------------------------------------------------------------"
cd cicd/backend/deployments
