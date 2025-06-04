package dew.models;

import com.google.gson.annotations.SerializedName;


public class AsignaturaAlumno {
  /** El DNI del alumno: campo “alumno” en el JSON */
  @SerializedName("alumno")
  private String dni;

  /** La nota que tiene este alumno en la asignatura */
  @SerializedName("nota")
  private String nota;

  public String getDni() {
    return dni;
  }
  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getNota() {
    return nota;
  }
  public void setNota(String nota) {
    this.nota = nota;
  }
}