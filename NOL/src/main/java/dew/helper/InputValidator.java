package dew.helper;

import java.util.regex.Pattern;

/**
 * Helper de validación de entradas de formulario.
 * Todos los métodos devuelven true si la entrada es "segura" según las reglas,
 * false en caso contrario.
 */
public class InputValidator {

    // Sólo permitimos letras, dígitos, espacios, punto, coma, guión y guión bajo
    private static final Pattern ALLOWED_CHARS =
        Pattern.compile("^[a-zA-Z0-9\\s\\.,\\-_]*$");
    // Patrón simple para detectar SQL injection
    private static final Pattern SQL_INJECTION =
        Pattern.compile("('|\")|(--|;|/\\*|\\*/)|\\b(xp_)\\b",
                        Pattern.CASE_INSENSITIVE);
    // Patrón para detectar tags HTML (posible XSS)
    private static final Pattern XSS =
        Pattern.compile("<[^>]*>", Pattern.CASE_INSENSITIVE);
    // DNI español: 8 dígitos obligatorios + letra
    private static final Pattern DNI_PATTERN =
        Pattern.compile("^\\d{8}[A-Za-z]$");

    /**
     * Comprueba sólo que NO sea nulo ni vacío (trimmed).
     */
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Comprueba que sólo contenga caracteres del conjunto permitido.
     */
    public static boolean isAllowedChars(String input) {
        if (input == null) return false;
        return ALLOWED_CHARS.matcher(input).matches();
    }

    /**
     * Devuelve true si la entrada contiene patrones comunes de SQL injection.
     */
    public static boolean hasSqlInjectionRisk(String input) {
        if (input == null) return false;
        return SQL_INJECTION.matcher(input).find();
    }

    /**
     * Devuelve true si la entrada contiene tags HTML (posible XSS).
     */
    public static boolean hasXssRisk(String input) {
        if (input == null) return false;
        return XSS.matcher(input).find();
    }

    /**
     * Combina las tres comprobaciones "límpias":
     *   - Sólo allowed chars
     *   - No SQL injection
     *   - No XSS
     */
    private static boolean isClean(String input) {
        return isAllowedChars(input)
            && !hasSqlInjectionRisk(input)
            && !hasXssRisk(input);
    }

    /**
     * Valida un campo obligatorio:
     *   - No vacío
     *   - Pasa isClean()
     */
    public static boolean isValidRequired(String input) {
        return isNotEmpty(input) && isClean(input);
    }

    /**
     * Valida un campo opcional:
     *   - Si está vacío (o null) → OK
     *   - Si no → must pass isClean()
     */
    public static boolean isValidOptional(String input) {
        if (input == null || input.trim().isEmpty()) {
            return true;
        }
        return isClean(input);
    }

    /**
     * Valida un DNI español (campo obligatorio con formato):
     *   - No vacío
     *   - Cumple patrón de 8 dígitos + letra
     *   - Pasa isClean() (aunque el patrón ya limita, es buena praxis)
     */
    public static boolean isValidDni(String dni) {
        if (!isNotEmpty(dni)) {
            return false;
        }
        if (!DNI_PATTERN.matcher(dni).matches()) {
            return false;
        }
        return isClean(dni);
    }

     /**
     * Valida que la nota (entero) esté en el rango permitido [0..10].
     * @return true si nota ≥ 0 y ≤ 10; false en cualquier otro caso
     */
    public static boolean isValidGrade(int nota) {
        return nota >= 0 && nota <= 10;
    }

}
