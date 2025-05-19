#!/bin/bash

ADMIN_DNI="111111111"
ADMIN_PASS="654321"

KEY="5j1e259pttrog2f2kt0cqfvgjo"

curl -s -X POST -H "content-type: application/json" \
--data '{"dni":"12345678W","nombre":"Pepe","apellidos":"Garcia Sanchez","password":"123456"}' \
"http://localhost:9090/CentroEducativo/alumnos?key=$KEY"

curl -s -X POST -H "content-type: application/json" \
--data '{"dni":"23456387R","nombre":"Maria","apellidos":"Fernandez Gomez","password":"123456"}' \
"http://localhost:9090/CentroEducativo/alumnos?key=$KEY"

curl -s -X POST -H "content-type: application/json" \
--data '{"dni":"34567891F","nombre":"Miguel","apellidos":"Hernandez Llopis","password":"123456"}' \
"http://localhost:9090/CentroEducativo/alumnos?key=$KEY"

curl -s -X POST -H "content-type: application/json" \
--data '{"dni":"93847525G","nombre":"Laura","apellidos":"Benitez Torres","password":"123456"}' \
"http://localhost:9090/CentroEducativo/alumnos?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"asignaturas": ["DEW", "DCU", "IAP"]}' \
"http://localhost:9090/CentroEducativo/alumnos/12345678W?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"asignaturas": ["DEW", "DCU"]}' \
"http://localhost:9090/CentroEducativo/alumnos/23456387R?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"asignaturas": ["DCU", "IAP"]}' \
"http://localhost:9090/CentroEducativo/alumnos/34567891F?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"asignaturas": ["DEW", "IAP"]}' \
"http://localhost:9090/CentroEducativo/alumnos/93847525G?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 8.5}' \
"http://localhost:9090/CentroEducativo/asignaturas/DEW/alumnos/12345678W?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 6.2}' \
"http://localhost:9090/CentroEducativo/asignaturas/DCU/alumnos/12345678W?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 7.3}' \
"http://localhost:9090/CentroEducativo/asignaturas/IAP/alumnos/12345678W?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 5.7}' \
"http://localhost:9090/CentroEducativo/asignaturas/DEW/alumnos/23456387R?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 6.9}' \
"http://localhost:9090/CentroEducativo/asignaturas/DCU/alumnos/23456387R?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 9.0}' \
"http://localhost:9090/CentroEducativo/asignaturas/DCU/alumnos/34567891F?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 7.1}' \
"http://localhost:9090/CentroEducativo/asignaturas/IAP/alumnos/34567891F?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 8.3}' \
"http://localhost:9090/CentroEducativo/asignaturas/DEW/alumnos/93847525G?key=$KEY"

curl -s -X PUT -H "content-type: application/json" \
--data '{"calificacion": 6.8}' \
"http://localhost:9090/CentroEducativo/asignaturas/IAP/alumnos/93847525G?key=$KEY"

