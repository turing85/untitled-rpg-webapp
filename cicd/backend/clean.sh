#!/bin/bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Cleaning the backend submodules"
echo "================================================================================"
cd ../..
mvn \
  ${MVN_CLI_OPTS} \
  --activate-profiles cicd \
  clean
cicd/backend/deployments