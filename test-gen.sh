#!/usr/bin/env bash

rm -r ./test-back
rm -r ./test-front

openapi-generator-cli generate \
    --strict-spec true \
    -i src/main/resources/openapi/openapi.yaml \
    -c src/main/resources/openapi/config.yaml \
    -g spring \
    -o ./test-back

openapi-generator-cli generate \
    --strict-spec true \
    -i src/main/resources/openapi/openapi.yaml \
    -g typescript-angular \
    -o ./test-front
