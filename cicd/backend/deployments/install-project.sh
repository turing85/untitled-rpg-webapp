#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Installing the project"
echo "================================================================================"
cd ../../..
./mvnw \
  ${MVN_CLI_OPTS} \
  -DskipTests \
  install
cd cicd/backend/deployments