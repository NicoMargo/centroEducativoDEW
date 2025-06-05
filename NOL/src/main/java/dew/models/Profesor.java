package dew.models;

import java.util.List;
import dew.models.Asignatura;

public class Profesor {
    private String apellidos;
    private String dni;
    private String nombre;
    private String password;
    private List<Asignatura> asignaturas;

    /**
     * Constructor vacío para deserialización y frameworks.
     */
    public Profesor() { }

    /**
     * Constructor que inicializa los campos básicos del profesor.
     * @param dni DNI del profesor
     * @param nombre Nombre del profesor
     * @param apellidos Apellidos del profesor
     * @param password Contraseña del profesor
     */
    public Profesor(String dni, String nombre, String apellidos, String password) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
    }

    /**
     * Obtiene los apellidos del profesor.
     * @return Apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del profesor.
     * @param apellidos Apellidos a asignar
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el DNI del profesor.
     * @return DNI
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del profesor.
     * @param dni DNI a asignar
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del profesor.
     * @return Nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del profesor.
     * @param nombre Nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la contraseña del profesor.
     * @return Contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del profesor.
     * @param password Contraseña a asignar
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene la lista de asignaturas que imparte el profesor.
     * @return Lista de Asignatura
     */
    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    /**
     * Establece la lista de asignaturas que imparte el profesor.
     * @param asignaturas Lista de Asignatura a asignar
     */
    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
}
