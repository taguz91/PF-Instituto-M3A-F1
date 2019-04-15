/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.docente;

import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author arman
 */
public class RolDocenteBD extends RolDocenteMD {
   private final ConectarDB conecta;
   private PeriodoLectivoMD p;
   
   //Para consultar periodos
   private final PeriodoLectivoBD perLec;

    public RolDocenteBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.perLec=new PeriodoLectivoBD(conecta);
    }
    
    public void InsertarRol(){
        
    }
    public void EditarRol(){
        
    }
   //private ArrayList<RolDocenteMD>
   
   
}
