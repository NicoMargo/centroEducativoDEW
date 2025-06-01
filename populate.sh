#!/usr/bin/env bash
set -euo pipefail

API_URL="http://localhost:9090/CentroEducativo"
COOKIE_JAR="cucu"

echo "1) Haciendo login..."
login_resp=$(curl --silent --show-error --fail \
  -X POST "${API_URL}/login" \
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
  '{"dni":"37264096W","nombre":"Minerva","apellidos":"Alonso Pérez","password":"123456"}'
do
  code=$(curl --silent --show-error --fail -w "%{http_code}" -o /dev/null \
    -X POST "${API_URL}/alumnos?key=${KEY}" \
    -H "Content-Type: application/json" \
    -d "$alumno" \
    -b "$COOKIE_JAR")
  if (( code >= 200 && code < 300 )); then
    echo "OK  Alumno añadido: $alumno"
  else
    echo "Error al añadir alumno: $alumno (HTTP $code)"
  fi
done
echo

echo "3) Añadiendo profesores (con contraseña genérica)..."
for profesor in \
  '{"dni":"23456733H","nombre":"Ramón","apellidos":"Garcia","password":"123456"}' \
  '{"dni":"10293756L","nombre":"Pedro","apellidos":"Valderas","password":"123456"}' \
  '{"dni":"06374291A","nombre":"Manoli","apellidos":"ALbert","password":"123456"}' \
  '{"dni":"65748923M","nombre":"Joan","apellidos":"Fons","password":"123456"}'
do
  code=$(curl --silent --show-error --fail -w "%{http_code}" -o /dev/null \
    -X POST "${API_URL}/profesores?key=${KEY}" \
    -H "Content-Type: application/json" \
    -d "$profesor" \
    -b "$COOKIE_JAR")
  if (( code >= 200 && code < 300 )); then
    echo "OK  Profesor añadido: $profesor"
  else
    echo "Error al añadir profesor: $profesor (HTTP $code)"
  fi
done
echo

echo "4) Asignando asignaturas a alumnos..."
declare -A asignaciones_alumnos=(
  ["12345678W"]="DCU DEW IAP"
  ["23456387R"]="DCU DEW"
  ["34567891F"]="DCU IAP"
  ["93847525G"]="IAP DEW"
  ["37264096W"]=""
)
for dni in "${!asignaciones_alumnos[@]}"; do
  for asignatura in ${asignaciones_alumnos[$dni]}; do
    json=$(printf '"%s"' "$asignatura")
    code=$(curl --silent --show-error --fail -w "%{http_code}" -o /dev/null \
      -X POST "${API_URL}/alumnos/${dni}/asignaturas?key=${KEY}" \
      -H "Content-Type: application/json" \
      -d "$json" \
      -b "$COOKIE_JAR")
    if (( code >= 200 && code < 300 )); then
      echo "✓ Asignatura $asignatura asignada a alumno $dni"
    elif (( code == 400 )); then
      echo "⚠ Asignatura $asignatura ya estaba asignada a alumno $dni"
    else
      echo "✗ Error al asignar $asignatura a alumno $dni (HTTP $code)"
    fi
  done
done
echo

echo "5) Asignando asignaturas a profesores..."
declare -A asignaciones_profesores=(
  ["23456733H"]="DEW"
  ["10293756L"]="DCU"
  ["06374291A"]="IAP"
  ["65748923M"]="DEW IAP"
)
for dni in "${!asignaciones_profesores[@]}"; do
  for asignatura in ${asignaciones_profesores[$dni]}; do
    json=$(printf '"%s"' "$asignatura")
    code=$(curl --silent --show-error --fail -w "%{http_code}" -o /dev/null \
      -X POST "${API_URL}/profesores/${dni}/asignaturas?key=${KEY}" \
      -H "Content-Type: application/json" \
      -d "$json" \
      -b "$COOKIE_JAR")
    if (( code >= 200 && code < 300 )); then
      echo "✓ Asignatura $asignatura asignada a profesor $dni"
    elif (( code == 400 )); then
      echo "⚠ Asignatura $asignatura ya estaba asignada a profesor $dni"
    else
      echo "✗ Error al asignar $asignatura a profesor $dni (HTTP $code)"
    fi
  done
done
echo

echo "6) Lista de alumnos con sus asignaturas:"
curl --silent --show-error --fail \
  -X GET "${API_URL}/alumnosyasignaturas?key=${KEY}" \
  -b "$COOKIE_JAR" \
  -H "Accept: application/json"
echo
echo

echo "7) Lista de profesores:"
curl --silent --show-error --fail \
  -X GET "${API_URL}/profesores?key=${KEY}" \
  -b "$COOKIE_JAR" \
  -H "Accept: application/json"
echo
echo

echo "Script completado"
