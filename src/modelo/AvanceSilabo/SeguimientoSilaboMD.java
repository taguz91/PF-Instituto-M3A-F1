/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AvanceSilabo;

import java.time.LocalDate;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.persona.PersonaMD;

/**
 *
 * @author Skull
 */
public class SeguimientoSilaboMD {
    
   private int id_seguimientoS;
   
   private LocalDate fecha_entrega_informe;
   
   private  boolean esInterciclo;
   
   private CursoMD curso;
   
   private int estado_seguimiento;
   
   
   //PARA CARGAR EN LA TABLA
   private MateriaMD materia;
   private PersonaMD persona;

    public SeguimientoSilaboMD() {
        this.curso=new CursoMD();
        this.materia=new MateriaMD();
        this.persona=new PersonaMD();
    }

    public int getId_seguimientoS() {
        return id_seguimientoS;
    }

    public void setId_seguimientoS(int id_seguimientoS) {
        this.id_seguimientoS = id_seguimientoS;
    }

    public LocalDate getFecha_entrega_informe() {
        return fecha_entrega_informe;
    }

    public void setFecha_entrega_informe(LocalDate fecha_entrega_informe) {
        this.fecha_entrega_informe = fecha_entrega_informe;
    }

    public boolean isEsInterciclo() {
        return esInterciclo;
    }

    public void setEsInterciclo(boolean esInterciclo) {
        this.esInterciclo = esInterciclo;
    }

    public CursoMD getCurso() {
        return curso;
    }

    public void setCurso(CursoMD curso) {
        this.curso = curso;
    }

    public int getEstado_seguimiento() {
        return estado_seguimiento;
    }

    public void setEstado_seguimiento(int estado_seguimiento) {
        this.estado_seguimiento = estado_seguimiento;
    }

    public MateriaMD getMateria() {
        return materia;
    }

    public void setMateria(MateriaMD materia) {
        this.materia = materia;
    }

    public PersonaMD getPersona() {
        return persona;
    }

    public void setPersona(PersonaMD persona) {
        this.persona = persona;
    }
   
    
    
   
}
