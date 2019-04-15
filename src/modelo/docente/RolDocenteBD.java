/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.docente;

import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoBD;

/**
 *
 * @author arman
 */
public class RolDocenteBD extends RolDocenteMD {
   private final ConectarDB conecta;
   
   //Para consultar periodos
   private final PeriodoLectivoBD perLec;

    public RolDocenteBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.perLec=new PeriodoLectivoBD(conecta);
    }
    
    public boolean InsertarRol(){
        String nsql="Insert into public.\"RolesPeriodo\"(\n"
                + "id_prd_lectivo,rol_prd)\n"
                + "Values (" + getPeriodo().getId_PerioLectivo()+ ", '"
                + getNombre_rol() + "');";
         if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
    public void EditarRol(){
        
    }
    
    public ArrayList<RolDocenteMD> cargarRoles(){
        return null;
    }
    
   //private ArrayList<RolDocenteMD>
}
