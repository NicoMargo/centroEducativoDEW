// src/main/java/dew/models/Alumno.java
package dew.models;

import java.util.List;

public class Alumno {
  private String dni;
  private String nombre;
  private String apellidos;
  // lista de asignaturas (ya existente)
  private List<Asignatura> asignaturas;
  // nuevo campo para contener la foto en Base64
  private String foto;    

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

  /** Getter/setter para la foto en Base64 **/
  public String getFoto() { return foto; }
  public void setFoto(String foto) { this.foto = foto; }
}
