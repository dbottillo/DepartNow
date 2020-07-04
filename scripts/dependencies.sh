#!/bin/bash

./gradlew dependencyUpdates -Drevision=release -DoutputFormatter=json
python3 scripts/check_lint_detekt_dependencies.py
