package dew.models;

import com.google.gson.annotations.SerializedName;

/**
 * Representa la relación entre un alumno y una asignatura,
 * incluyendo el DNI del alumno y la nota obtenida.
 */
public class AsignaturaAlumno {
  /** El DNI del alumno: campo “alumno” en el JSON */
  @SerializedName("alumno")
  private String dni;

  /** La nota que tiene este alumno en la asignatura */
  @SerializedName("nota")
  private String nota;

  /**
   * Obtiene el DNI del alumno.
   * @return DNI del alumno
   */
  public String getDni() {
    return dni;
  }

  /**
   * Establece el DNI del alumno.
   * @param dni DNI a asignar
   */
  public void setDni(String dni) {
    this.dni = dni;
  }

  /**
   * Obtiene la nota del alumno en la asignatura.
   * @return nota como String
   */
  public String getNota() {
    return nota;
  }

  /**
   * Establece la nota del alumno en la asignatura.
   * @param nota nota a asignar
   */
  public void setNota(String nota) {
    this.nota = nota;
  }
}
