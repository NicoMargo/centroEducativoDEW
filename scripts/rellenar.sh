#!/bin/bash

ADMIN_DNI="111111111"
ADMIN_PASS="654321"

KEY=$(curl -s --data "{\"dni\":\"$ADMIN_DNI\",\"password\":\"$ADMIN_PASS\"}" \
-X POST -H "content-type: application/json" \
http://localhost:9090/CentroEducativo/login | jq -r '.key')

echo "KEY obtenida: $KEY"

curl -s -X POST -H "content-type: application/json" \
--data '{"dni":"23456733H","nombre":"Ramón","apellidos":"Garcia","password":"prof1"}' \
"http://localhost:9090/CentroEducativo/profesores?key=$KEY"

curl -s -X POST -H "content-type: application/json" \
--data '{"dni":"12345678W","nombre":"Pepe","apellidos":"Garcia Sanchez","password":"123456"}' \
"http://localhost:9090/CentroEducativo/alumnos?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"asignaturas": ["DEW"]}' \
"http://localhost:9090/CentroEducativo/alumnos/12345678W?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 8.5}' \
"http://localhost:9090/CentroEducativo/asignaturas/DEW/alumnos/12345678W?key=$KEY"

