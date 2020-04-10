#!/usr/bin/env bash
set -e

export SERVICE="language"

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
${SCRIPT_DIR}/../../helper/build-service-native.sh