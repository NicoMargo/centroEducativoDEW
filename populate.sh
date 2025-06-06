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
  '{"dni":"11111111A","nombre":"Nicolas","apellidos":"Margossian","password":"123456"}' \
  '{"dni":"22222222A","nombre":"Carmen","apellidos":"Crespo Navarro","password":"123456"}' \
  '{"dni":"33333333A","nombre":"Alejandro","apellidos":"Navarro Sala","password":"123456"}'
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
  '{"dni":"22222222Z","nombre":"Juan","apellidos":"Britos","password":"123456"}' \
  '{"dni":"33333333Z","nombre":"Alejandro","apellidos":"De la fuente","password":"123456"}' \
  '{"dni":"44444444Z","nombre":"Marcos","apellidos":"Trossero","password":"123456"}'
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
  ["11111111A"]="DCU DEW IAP"
  ["22222222A"]="DCU DEW"
  ["33333333A"]="DCU IAP"
)

for dni in "${!asignaciones_alumnos[@]}"; do
  for asignatura in ${asignaciones_alumnos[$dni]}; do
    code=$(curl --silent --show-error --fail -w "%{http_code}" -o /dev/null \
      -X POST "${API_URL}/alumnos/${dni}/asignaturas?key=${KEY}" \
      -H "Content-Type: application/json" \
      -d "$asignatura" \
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
  ["22222222Z"]="DEW DCU IAP"
  ["33333333Z"]="DCU"
  ["44444444Z"]="IAP DEW"
)

for dni in "${!asignaciones_profesores[@]}"; do
  for asignatura in ${asignaciones_profesores[$dni]}; do
    code=$(curl --silent --show-error --fail -w "%{http_code}" -o /dev/null \
      -X POST "${API_URL}/profesores/${dni}/asignaturas?key=${KEY}" \
      -H "Content-Type: application/json" \
      -d "$asignatura" \
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


echo "8) Haciendo login Profesor..."
login_resp=$(curl --silent --show-error --fail \
  -X POST "${API_URL}/login" \
  -H "Content-Type: application/json" \
  -d '{"dni":"33333333Z","password":"123456"}' \
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



echo "9) Asignando notas a todos los alumnos..."
notas=(
  "12345678W IAP 8"
  "12345678W DCU 7"
  "12345678W DEW 9"
  "23456387R DCU 6"
  "23456387R DEW 7"
  "34567891F IAP 5"
  "34567891F DCU 8"
  "93847525G DEW 9"
  "93847525G IAP 6"
  "11111111A DCU 8"
  "11111111A IAP 7"
  "11111111A DEW 9"
  "22222222A DCU 6"
  "22222222A DEW 7"
  "33333333A IAP 8"
  "33333333A DCU 9"
  # El alumno 37264096W no tiene asignaturas, así que no aparece aquí
)

for entry in "${notas[@]}"; do
  read -r dni acronimo nota <<< "$entry"

  # Enviamos la nota como un entero plano, sin comillas
  http_code=$(curl --silent --show-error --fail -w "%{http_code}" -o /dev/null \    
    -X PUT "${API_URL}/alumnos/${dni}/asignaturas/${acronimo}?key=${KEY}" \
    -H "Content-Type: application/json" \
    -d "${nota}" \
    -b "$COOKIE_JAR")

  if (( http_code >= 200 && http_code < 300 )); then
    echo "✓ Nota ${nota} asignada a ${acronimo} de alumno ${dni} (HTTP ${http_code})"
  else
    echo "✗ Error al asignar nota ${nota} a ${acronimo} de alumno ${dni} (HTTP ${http_code})"
  fi
done

echo "Script completado."

