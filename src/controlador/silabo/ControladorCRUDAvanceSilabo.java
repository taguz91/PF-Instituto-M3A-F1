/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.usuario.UsuarioBD;
import vista.silabos.frmConfiguracionSeguimientoSilabo;

/**
 *
 * @author Daniel
 */
public class ControladorCRUDAvanceSilabo {
    private final UsuarioBD usuario;
    private ConexionBD conexion;
       private boolean esCordinador =false;
        private List<CarreraMD> carreras_docente;
    private frmConfiguracionSeguimientoSilabo seguimiento;
    
      private void CARGAR_COMBO_CARRERAS() {
          
       seguimiento.getCbxCarrera().removeAllItems();
        carreras_docente=new ArrayList<>();
        if (esCordinador) {
            carreras_docente.add(new CarrerasBDS(conexion).retornaCarreraCoordinador(usuario.getUsername()));
        } else {
        carreras_docente = CarrerasBDS.consultar(conexion, usuario.getUsername());
        }
        
            
            carreras_docente.forEach((cmd) -> {
               seguimiento.getCmb_Carreras().addItem(cmd.getNombre());
            });
        
       seguimiento.getCmb_Carreras().setSelectedIndex(0);
    }
         private CursoMD curso_selecc() {
        int seleccion =seguimiento.getTlbTablaPLC().getSelectedRow();
        lista_curso = CursosBDS.Consultarcursos(conexion, usuario.getPersona().getIdPersona(), getid_periodo(),seguimiento.getTlbTablaPLC().getValueAt(seleccion, 2).toString());
        Optional<CursoMD> curso_selecccionado = lista_curso.stream().filter(lc -> lc.getNombre().equals(fCrud_plan_Clases.getTlbTablaPLC().getValueAt(seleccion, 3).toString())).findFirst();
        return curso_selecccionado.get();
    }
}
