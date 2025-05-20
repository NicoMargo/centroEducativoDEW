package dew.models;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Asignatura {
  // Este será el código (acronimo) en ambos JSON:
  @SerializedName(value = "acronimo", alternate = { "asignatura" })
  private String acronimo;

  // Sólo existe en el array de asignaturas del alumno:
  @SerializedName("nota")
  private String nota;

  // Sólo existen en el detalle de asignatura:
  private String nombre;
  private int curso;
  private String cuatrimestre;
  private double creditos;
  private List<Profesor> profesores;


  // getters & setters
  public String getAcronimo()      { return acronimo; }
  public void setAcronimo(String a){ this.acronimo = a; }

  public String getNota()          { return nota; }
  public void setNota(String n)    { this.nota = n; }

  public String getNombre()        { return nombre; }
  public void setNombre(String n)  { this.nombre = n; }

  public int getCurso()            { return curso; }
  public void setCurso(int c)      { this.curso = c; }

  public String getCuatrimestre()  { return cuatrimestre; }
  public void setCuatrimestre(String c) { this.cuatrimestre = c; }

  public double getCreditos()      { return creditos; }
  public void setCreditos(double c){ this.creditos = c; }
  
  public List<Profesor> getProfesores() { return profesores; }
  public void setProfesores(List<Profesor> profesores) { this.profesores = profesores;}
}
