#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

cd ../..
echo "================================================================================"
echo "Installing the whole project"
echo "================================================================================"
./mvnw \
  ${MVN_CLI_OPTS} \
  -DskipTests \
  install