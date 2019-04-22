/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import modelo.carrera.CarreraMD;
import modelo.jornada.JornadaMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaMD;


public class CursoMDS {
    private int id_curso;
    private MateriaMD id_materia;
    private PeriodoLectivoMD id_prd_lectivo;
    private DocenteMD id_docente;
    private String curso_nombre;
    private JornadaMD curso_jornada;
    private int curso_capacidad;
    private int curso_ciclo;
    private String paralelo; 
    
    private CarreraMD id_carrera;
    private PersonaMD id_persona;
    
    public CursoMDS() {
        this.id_carrera=new CarreraMD();
        this.id_materia=new MateriaMD();
        this.id_persona=new PersonaMD();
    }

    public CursoMDS(MateriaMD id_materia, CarreraMD id_carrera, PersonaMD id_persona) {
        this.id_materia = id_materia;
        this.id_carrera = id_carrera;
        this.id_persona = id_persona;
    }

    public CarreraMD getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(CarreraMD id_carrera) {
        this.id_carrera = id_carrera;
    }

    public PersonaMD getId_persona() {
        return id_persona;
    }

    public void setId_persona(PersonaMD id_persona) {
        this.id_persona = id_persona;
    }
  
    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public MateriaMD getId_materia() {
        return id_materia;
    }

    public void setId_materia(MateriaMD id_materia) {
        this.id_materia = id_materia;
    }

    public PeriodoLectivoMD getId_prd_lectivo() {
        return id_prd_lectivo;
    }

    public void setId_prd_lectivo(PeriodoLectivoMD id_prd_lectivo) {
        this.id_prd_lectivo = id_prd_lectivo;
    }

    public DocenteMD getId_docente() {
        return id_docente;
    }

    public void setId_docente(DocenteMD id_docente) {
        this.id_docente = id_docente;
    }

    public String getCurso_nombre() {
        return curso_nombre;
    }

    public void setCurso_nombre(String curso_nombre) {
        this.curso_nombre = curso_nombre;
    }

    public JornadaMD getCurso_jornada() {
        return curso_jornada;
    }

    public void setCurso_jornada(JornadaMD curso_jornada) {
        this.curso_jornada = curso_jornada;
    }

    public int getCurso_capacidad() {
        return curso_capacidad;
    }

    public void setCurso_capacidad(int curso_capacidad) {
        this.curso_capacidad = curso_capacidad;
    }

    public int getCurso_ciclo() {
        return curso_ciclo;
    }

    public void setCurso_ciclo(int curso_ciclo) {
        this.curso_ciclo = curso_ciclo;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }
    
}
