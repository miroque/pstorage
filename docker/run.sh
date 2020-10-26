#!/bin/sh

while ! nc -z db 5432; do
    echo "Waiting for PostgreSql to start..."
    sleep 1
done

#while ! nc -z redis 6379; do
#    echo "Waiting for Redis to start..."
#    sleep 1
#done

if [ -z ${TZ+x} ]; then echo "TZ is unset" TZ="UTC"; else echo "TZ was defined"; fi

echo "TZ is set to '$TZ'";

#if [[ -z "${TZ}" ]]; then
#  DTZ="UTC"
#  echo "Default value to UTC"
#else
#  DTZ="${TZ}"
#  echo $DTZ
#fi

exec java -Duser.timezone=$DTZ -Dspring.profiles.active=prod -cp app:app/lib/* ru.miroque.pstorage.PstorageApplication
