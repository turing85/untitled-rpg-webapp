#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

export SERVICE=user
"${SCRIPT_DIR}"/../../helper/build-service-native.sh
