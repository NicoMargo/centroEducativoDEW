#!/usr/bin/env bash
set -euo pipefail

API_URL="http://localhost:9090/CentroEducativo"
COOKIE_JAR="cucu"

echo "1) Haciendo login..."
login_resp=$(curl -s -f -X POST "${API_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{"dni":"111111111","password":"654321"}' \
  -c "$COOKIE_JAR") || {
  echo "Error en login." >&2
  exit 1
}

KEY=$(printf '%s' "$login_resp" | tr -d '[:space:]')
if [[ -z "$KEY" ]]; then
  echo "Error: la KEY está vacía." >&2
  exit 1
fi
echo "Login correcto. KEY=$KEY"
echo

echo "2) Añadiendo alumnos..."
for alumno in \
  '{"dni":"12345678W","nombre":"Pepe","apellidos":"Garcia Sanchez","password":"123456"}' \
  '{"dni":"23456387R","nombre":"Maria","apellidos":"Fernandez Gómez","password":"123456"}' \
  '{"dni":"34567891F","nombre":"Miguel","apellidos":"Hernandez Llopis","password":"123456"}' \
  '{"dni":"93847525G","nombre":"Laura","apellidos":"Benitez Torres","password":"123456"}' \
  '{"dni":"37264096W","nombre":"XD","apellidos":"Alonso Pérez","password":"123456"}'
do
  code=$(curl -s -o /dev/null -w "%{http_code}" \
    -X POST "${API_URL}/alumnos?key=${KEY}" \
    -H "Content-Type: application/json" \
    -d "$alumno" \
    -b "$COOKIE_JAR")
  if (( code >= 200 && code < 300 )); then
    echo "Alumno añadido: $alumno"
  else
    echo "Error al añadir alumno: $alumno (HTTP $code)"
  fi
done
echo

declare -A asignaciones=(
  ["12345678W"]="DCU DEW IAP"
  ["23456387R"]="DCU DEW"
  ["34567891F"]="DCU IAP"
  ["93847525G"]="IAP DEW"
  ["37264096W"]=""
)

echo "3) Asignando asignaturas..."
for dni in "${!asignaciones[@]}"; do
  for asignatura in ${asignaciones[$dni]}; do
    json=$(printf '"%s"' "$asignatura")
    code=$(curl -s -o /dev/null -w "%{http_code}" \
      -X POST "${API_URL}/alumnos/${dni}/asignaturas?key=${KEY}" \
      -H "Content-Type: application/json" \
      -d "$json" \
      -b "$COOKIE_JAR")
    if (( code >= 200 && code < 300 )); then
      echo "Asignatura $asignatura asignada a DNI $dni"
    elif (( code == 400 )); then
      echo "Asignatura $asignatura ya estaba asignada a DNI $dni (HTTP 400)"
    else
      echo "Error al asignar $asignatura a DNI $dni (HTTP $code)"
    fi
  done
done
echo

curl --silent --show-error --fail \
  -X GET "${API_URL}/alumnosyasignaturas?key=${KEY}" \
  -b "$COOKIE_JAR" \
  -H "Accept: application/json"

echo "Script completado"
