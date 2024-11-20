#!/usr/bin/env bash

DB_USERNAME="$(< ../db_username.txt)" \
DB_PASSWORD="$(< ../db_password.txt)" \
./clojure.sh -X lms/load-dummy-lms22-transcoding
