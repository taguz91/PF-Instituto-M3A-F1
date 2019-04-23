/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.docente;

import modelo.persona.DocenteMD;
import modelo.persona.PersonaMD;

/**
 *
 * @author DAVICHO
 */
public class RolDocenteMD {

    public PersonaMD getPersona() {
        return persona;
    }

    public void setPersona(PersonaMD persona) {
        this.persona = persona;
    }
    private RolPeriodoMD idRolPeriodo;
    private DocenteMD idDocente;
    private PersonaMD persona;
    

    public RolPeriodoMD getIdRolPeriodo() {
        return idRolPeriodo;
    }

    public void setIdRolPeriodo(RolPeriodoMD idRolPeriodo) {
        this.idRolPeriodo = idRolPeriodo;
    }

    public DocenteMD getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(DocenteMD idDocente) {
        this.idDocente = idDocente;
    }
    
    
}
