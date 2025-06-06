A continuación se presenta una descripción detallada y formal del funcionamiento del script `populate.sh`, que automatiza la creación y configuración inicial de datos en la API REST de CentroEducativo.

\---

\## Descripción general

El script `populate.sh` ejecuta una serie de peticiones HTTP contra la API REST de CentroEducativo con el fin de:

1. Autenticarse en la API (login).
1. Crear registros de alumnos.
1. Crear registros de profesores.
1. Asignar asignaturas a cada alumno.
1. Asignar asignaturas a cada profesor.
1. Recuperar y mostrar la lista de alumnos con sus asignaturas.
1. Recuperar y mostrar la lista de profesores existentes.

Todas las operaciones posteriores al login se realizan utilizando la clave (“KEY”) obtenida durante la autenticación y enviando la cookie de sesión (JSESSIONID) que se almacena en un fichero temporal.

\---

\## 1. Configuración inicial

* Se establece la URL base de la API en la variable:

\```bash

API\_URL="http://localhost:9090/CentroEducativo"

\```

* Se define el nombre del fichero que contendrá las cookies de sesión generadas durante el login:

\```bash

COOKIE\_JAR="cucu"

\```

* El script activa las siguientes opciones de Bash mediante:

\```bash

set -euo pipefail

\```

De este modo, cualquier error, uso de variable no definida o fallo en una tubería detendrá inmediatamente la ejecución.

\---

\## 2. Paso 1: Inicio de sesión en la API

1. Se muestra por pantalla el mensaje:

\```

1) Haciendo login...

\```

2\. Se realiza una petición \*\*POST\*\* al endpoint `/login` de la API con el siguiente comando `curl`:

\```bash

login\_resp=$(curl --silent --show-error --fail \

- X POST "${API\_URL}/login" \
- H "Content-Type: application/json" \
- d '{"dni":"111111111","password":"654321"}' \
- c "$COOKIE\_JAR") || {

echo "Error en login." >&2

exit 1

}

\```

* `--silent --show-error --fail` asegura que, en caso de código de respuesta distinto de 2xx, `curl` termine con error y el bloque `|| { … }` capture esa situación.
* `-H "Content-Type: application/json"` indica que se envía un cuerpo en formato JSON.
* `-d '{"dni":"111111111","password":"654321"}'` contiene el DNI y la contraseña del usuario administrador.
* `-c "$COOKIE\_JAR"` guarda en el fichero `cucu` cualquier cookie de sesión devuelta, típicamente el `JSESSIONID`.

3\. Si la llamada al endpoint `/login` falla, el script imprime el mensaje de error y finaliza con código de salida 1.

4\. Si la autenticación es satisfactoria, la respuesta JSON (por ejemplo, `{"key":"abcd1234…"}`) se almacena en la variable `login\_resp`. A partir de ella, se extrae la clave eliminando posibles espacios:

\```bash

KEY=$(printf '%s' "$login\_resp" | tr -d '[:space:]')

\```

5\. Se verifica que `KEY` no esté vacía. En caso de estarlo, se notifica el error y se interrumpe la ejecución.

6\. Finalmente, se advierte al usuario del resultado:

\```

Login correcto. KEY=<valor\_obtenido>

\```

\---

\## 3. Paso 2: Creación de alumnos

1. Se imprime por pantalla:

\```

\2) Añadiendo alumnos\.\.\.

\```

1. Se define un bucle `for` que recorre una serie de cadenas JSON, cada una representando a un nuevo alumno con sus datos:

\```bash

for alumno in \

'{"dni":"12345678W","nombre":"Pepe","apellidos":"Garcia Sanchez","password":"123456"}' \

'{"dni":"23456387R","nombre":"Maria","apellidos":"Fernandez Gómez","password":"123456"}' \

'{"dni":"34567891F","nombre":"Miguel","apellidos":"Hernandez Llopis","password":"123456"}' \

'{"dni":"93847525G","nombre":"Laura","apellidos":"Benitez Torres","password":"123456"}' \

'{"dni":"37264096W","nombre":"Minerva","apellidos":"Alonso Pérez","password":"123456"}'

do

…

done

\```

1. Dentro de ese bucle, para cada `alumno` se ejecuta el siguiente comando `curl`:

\```bash

code=$(curl --silent --show-error --fail -w "%{http\_code}" -o /dev/null \

- X POST "${API\_URL}/alumnos?key=${KEY}" \
- H "Content-Type: application/json" \
- d "$alumno" \
- b "$COOKIE\_JAR")

\```

* La URL es `/alumnos?key=<KEY>`, de modo que la clave obtenida en el login se envía como parámetro.
* `-b "$COOKIE\_JAR"` incluye en la petición la cookie de sesión (JSESSIONID) guardada previamente.
* `-w "%{http\_code}" -o /dev/null` hace que únicamente se capture el código de respuesta HTTP en la variable `code`, descartando el cuerpo de la respuesta.

4\. A continuación, el script comprueba el valor de `code`:

* Si `code` está entre 200 y 299 (códigos de éxito), se imprime:

\```

OK  Alumno añadido: <cadena\_JSON\_del\_alumno>

\```

* En caso contrario, se imprime:

\```

Error al añadir alumno: <cadena\_JSON\_del\_alumno> (HTTP <código>)

\```

\---

\## 4. Paso 3: Creación de profesores

1. Se muestra en pantalla:

\```

\3) Añadiendo profesores (con contraseña genérica)\.\.\.

\```

1. Se recorre un bucle similar al de alumnos, pero con registros de profesores:

\```bash

for profesor in \

'{"dni":"23456733H","nombre":"Ramón","apellidos":"Garcia","password":"123456"}' \

'{"dni":"10293756L","nombre":"Pedro","apellidos":"Valderas","password":"123456"}' \

'{"dni":"06374291A","nombre":"Manoli","apellidos":"ALbert","password":"123456"}' \

'{"dni":"65748923M","nombre":"Joan","apellidos":"Fons","password":"123456"}'

do

…

done

\```

1. Para cada `profesor` se ejecuta:

\```bash

code=$(curl --silent --show-error --fail -w "%{http\_code}" -o /dev/null \

- X POST "${API\_URL}/profesores?key=${KEY}" \
- H "Content-Type: application/json" \
- d "$profesor" \
- b "$COOKIE\_JAR")

\```

4\. Según el valor de `code`:

* Si está entre 200 y 299, se informa:

\```

OK  Profesor añadido: <cadena\_JSON\_del\_profesor>

\```

* En caso contrario:

\```

Error al añadir profesor: <cadena\_JSON\_del\_profesor> (HTTP <código>)

\```

\---

\## 5. Paso 4: Asignación de asignaturas a alumnos

1. Se imprime:

\```

\4) Asignando asignaturas a alumnos\.\.\.

\```

1. Se define un array asociativo (mapa) cuyo índice es el DNI del alumno y cuyo valor es una cadena con los acrónimos de las asignaturas separadas por espacios:

\```bash

declare -A asignaciones\_alumnos=(

["12345678W"]="DCU DEW IAP"

["23456387R"]="DCU DEW"

["34567891F"]="DCU IAP"

["93847525G"]="IAP DEW"

["37264096W"]=""

)

\```

1. Se recorre cada par `dni` → `lista\_de\_asignaturas`. Para cada `asignatura` en esa lista:

1. Se construye un pequeño JSON que consiste únicamente en el string del acrónimo entre comillas:

\```bash

json=$(printf '"%s"' "$asignatura")

\```

1. Se ejecuta un \*\*POST\*\* a:

\```

${API\_URL}/alumnos/${dni}/asignaturas?key=${KEY}

\```

enviando el cuerpo:

\```bash

- H "Content-Type: application/json" -d "$json" -b "$COOKIE\_JAR"

\```

3\. El resultado del comando se captura en la variable `code` del modo:

\```bash

code=$(curl --silent --show-error --fail -w "%{http\_code}" -o /dev/null \

- X POST "${API\_URL}/alumnos/${dni}/asignaturas?key=${KEY}" \
- H "Content-Type: application/json" \
- d "$json" \
- b "$COOKIE\_JAR")

\```

4\. A partir del valor de `code` se generan los mensajes correspondientes:

* Si `code` está en el rango \[200–299], se muestra:

\```

Asignatura <acronimo> asignada a alumno <dni>

\```

* Si `code` vale 400 (bad request), se interpreta como que la asignatura ya estaba asignada anteriormente, y se informa:

\```

Asignatura <acronimo> ya estaba asignada a alumno <dni>

\```

* Para cualquier otro código, se notifica:

\```

Error al asignar <acronimo> a alumno <dni> (HTTP <código>)

\```

\---

\## 6. Paso 5: Asignación de asignaturas a profesores

1. Se imprime:

\```

\5) Asignando asignaturas a profesores\.\.\.

\```

1. Se define un array asociativo donde la clave es el DNI del profesor y el valor es la lista de acrónimos de las asignaturas:

\```bash

declare -A asignaciones\_profesores=(

["23456733H"]="DEW"

["10293756L"]="DCU"

["06374291A"]="IAP"

["65748923M"]="DEW IAP"

)

\```

1. Por cada `dni` de profesor y cada `asignatura` en su lista:

1. Se forma el JSON sencillo `"<acronimo>"`:

\```bash

json=$(printf '"%s"' "$asignatura")

\```

1. Se hace la petición \*\*POST\*\* a:

\```

${API\_URL}/profesores/${dni}/asignaturas?key=${KEY}

\```

con `curl`:

\```bash

code=$(curl --silent --show-error --fail -w "%{http\_code}" -o /dev/null \

- X POST "${API\_URL}/profesores/${dni}/asignaturas?key=${KEY}" \
- H "Content-Type: application/json" \
- d "$json" \
- b "$COOKIE\_JAR")

\```

3\. Según el valor de `code`:

* Si está en \[200–299], se indica:

\```

Asignatura <acronimo> asignada a profesor <dni>

\```

* Si `code` es 400, se asume que la asignatura ya estaba asignada previamente y se informa:

\```

Asignatura <acronimo> ya estaba asignada a profesor <dni>

\```

* Para cualquier otro código, se indica:

\```

Error al asignar <acronimo> a profesor <dni> (HTTP <código>)

\```

\---

\## 7. Paso 6: Recuperar lista de alumnos con sus asignaturas

1. Se muestra:

\```

\6) Lista de alumnos con sus asignaturas:

\```

1. Se efectúa una petición \*\*GET\*\* al endpoint `/alumnosyasignaturas` con la clave como parámetro:

\```bash

curl --silent --show-error --fail \

- X GET "${API\_URL}/alumnosyasignaturas?key=${KEY}" \
- b "$COOKIE\_JAR" \
- H "Accept: application/json"

\```

3\. La respuesta es un documento JSON que contiene, para cada alumno, su DNI y las asignaturas en las que está matriculado. El resultado se imprime directamente por pantalla.

\---

\## 8. Paso 7: Recuperar lista de profesores

1. Se imprime:

\```

\7) Lista de profesores:

\```

1. Se realiza una petición \*\*GET\*\* al endpoint `/profesores`:

\```bash

curl --silent --show-error --fail \

- X GET "${API\_URL}/profesores?key=${KEY}" \
- b "$COOKIE\_JAR" \
- H "Accept: application/json"

\```

3\. El servidor devuelve un JSON con la lista completa de profesores creados, que se muestra en pantalla.

\---

\## 9. Finalización

Al completar todas las operaciones anteriores, el script imprime:

\```

Script completado

\```

y finaliza su ejecución bajo control de `set -euo pipefail`. A partir de ese momento, la API de CentroEducativo dispone de:

* Cinco alumnos registrados con sus datos personales.
* Cuatro profesores registrados con su contraseña genérica.
* Las asignaturas correspondientes matriculadas a cada alumno.
* Las asignaturas asignadas a cada profesor.
* Dos listados JSON: uno de alumnos con sus asignaturas y otro de profesores.

\---

\## Resumen de operaciones realizadas

1. \*\*Login en la API\*\*:

* Petición POST a `/login` con credenciales de administrador.
* Obtención de la clave (KEY) y la cookie de sesión (JSESSIONID).

2\. \*\*Creación de alumnos\*\*:

* Para cada objeto JSON de alumno, se envía POST a `/alumnos?key=<KEY>`.
* Se informa del éxito o error según el código HTTP.

3\. \*\*Creación de profesores\*\*:

* Para cada objeto JSON de profesor, se envía POST a `/profesores?key=<KEY>`.
* Se informa del resultado basado en el código HTTP.

4\. \*\*Asignación de asignaturas a alumnos\*\*:

* Se recorre la lista de alumnos y sus respectivas asignaturas.
* Para cada combinación (alumno, asignatura), se envía POST a `/alumnos/{dni}/asignaturas?key=<KEY>`.
* Se diferencia entre éxito, ya existente o fallo genérico según el código HTTP.

5\. \*\*Asignación de asignaturas a profesores\*\*:

* Se recorre la lista de profesores y las asignaturas que imparten.
* Para cada combinación (profesor, asignatura), se envía POST a `/profesores/{dni}/asignaturas?key=<KEY>`.
* Se informa del resultado (éxito, ya existente o error).

6\. \*\*Listar alumnos con asignaturas\*\*:

* GET a `/alumnosyasignaturas?key=<KEY>`.
* Se muestra el JSON resultante.

7\. \*\*Listar profesores\*\*:

* GET a `/profesores?key=<KEY>`.
* Se muestra el JSON resultante.

Con ello, el script cumple su objetivo de poblar la base de datos de CentroEducativo con datos de ejemplo para un entorno de desarrollo o pruebas.
