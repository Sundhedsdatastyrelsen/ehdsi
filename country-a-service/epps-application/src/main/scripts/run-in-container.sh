#!/bin/sh

jar_file="$1"
shift

if [ ! -z "${JAVA_DEBUG}" ]; then
    DEBUGGING_FLAGS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=4000 "
fi

COMMAND="java ${DEBUGGING_FLAGS}-Duser.timezone=UTC -jar ${jar_file}"

echo ""
java -version
echo ""
echo "Running jar file ${jar_file}:"
echo ""
echo ">   ${COMMAND}"
echo ""
${COMMAND}
