/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.docente;

import modelo.persona.DocenteMD;

/**
 *
 * @author DAVICHO
 */
public class RolDocenteMD {
    private RolPeriodoMD idRolPeriodo;
    private DocenteMD idDocente;    

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
