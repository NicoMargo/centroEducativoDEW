#!/usr/bin/env bash
set -euo pipefail

API_URL="http://localhost:9090/CentroEducativo"
COOKIE_JAR="cucu"

# 1) Hacer login, guardar la cookie y capturar la respuesta (la KEY) en una variable
#    --fail hará que curl devuelva error en HTTP ≥400
login_resp=$(curl --silent --show-error --fail \
  -X POST "${API_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{"dni":"111111111","password":"654321"}' \
  -c "$COOKIE_JAR") || {
    echo "Error en login." >&2
    exit 1
}

# elimina posibles espacios o saltos de línea
KEY=$(printf '%s' "$login_resp" | tr -d '[:space:]')

# comprobación mínima
if [[ -z "$KEY" ]]; then
  echo "Error: la KEY está vacía." >&2
  exit 1
fi

# 2) Llamada autenticada usando cookie + KEY
curl --silent --show-error --fail \
  -X GET "${API_URL}/alumnosyasignaturas?key=${KEY}" \
  -b "$COOKIE_JAR" \
  -H "Accept: application/json"
