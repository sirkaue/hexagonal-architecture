#!/bin/sh
# wait-for-mysql.sh

set -e

host="$1"
shift
cmd="$@"

echo "Aguardando MySQL em $host..."

while ! nc -z $host 3306; do
  sleep 2
  echo "Ainda aguardando MySQL..."
done

echo "MySQL está pronto! Iniciando aplicação..."
exec $cmd