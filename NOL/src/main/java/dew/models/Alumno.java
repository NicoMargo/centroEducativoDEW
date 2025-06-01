package dew.models;

import java.util.List;

public class Alumno {
  private String dni;
  private String nombre;
  private String apellidos;
  // ahora guardamos directamente la lista de Asignatura
  private List<Asignatura> asignaturas;

  public String getDni() { return dni; }
  public void setDni(String dni) { this.dni = dni; }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }

  public String getApellidos() { return apellidos; }
  public void setApellidos(String apellidos) { this.apellidos = apellidos; }

  public List<Asignatura> getAsignaturas() { return asignaturas; }
  public void setAsignaturas(List<Asignatura> asignaturas) {
    this.asignaturas = asignaturas;
  }
}
