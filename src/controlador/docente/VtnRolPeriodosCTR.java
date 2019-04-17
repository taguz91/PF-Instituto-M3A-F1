/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.docente;

import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.docente.RolPeriodoBD;
import modelo.docente.RolPeriodoMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.docente.VtnRolesPeriodos;
import vista.principal.VtnPrincipal;

/**
 *
 * @author arman
 */
public class VtnRolPeriodosCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnRolesPeriodos vtnRolPe;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolPeriodoBD rolDoc;
    private DefaultTableModel mdTbl;
// para combo de periodo
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;

    public VtnRolPeriodosCTR(VtnPrincipal vtnPrin, VtnRolesPeriodos vtnRolPe, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.vtnRolPe = vtnRolPe;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.rolDoc = new RolPeriodoBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnRolPe);
        vtnRolPe.show();
    }
public void iniciar(){
     String titulo[] = {"Periodos Lectivos", "Roles"};
        String datos[][] = {};
        
        //Usamos el modelo que no nos deja editar los campos
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
     //Le pasamos el modelo a la tabla  v
        vtnRolPe.getTblAlumno().setModel(mdTbl);
         //Pasamos el estilo a la tabla 
        TblEstilo.formatoTbl(vtnRolPe.getTblAlumno());

    
    vtnRolPe.getBtnIngresar().addActionListener(e->abrirFRM());
    vtnRolPe.getBtnEditar().addActionListener(e->abrirFrmEditar());
    llenarTabla();
}    
private void abrirFRM(){
    ctrPrin.abrirFrmRolesPeriodos();
    vtnRolPe.dispose();
    ctrPrin.cerradoJIF();
}
    public void abrirFrmEditar(){
    ctrPrin.abrirFrmRolesPeriodos();
    vtnRolPe.dispose();
    ctrPrin.cerradoJIF();
    }
 public void llenarTabla() {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnRolPe.getTblAlumno().getModel();
        for (int i = vtnRolPe.getTblAlumno().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<RolPeriodoMD> lista = rolDoc.llenarTabla();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
           // vtnRolPe.getTblAlumno().setValueAt(String.valueOf(lista.get(i).getPeriodo().getNombre_PerLectivo()), i, 0);
            vtnRolPe.getTblAlumno().setValueAt(String.valueOf(lista.get(i).getNombre_rol()), i, 1);
        }
        vtnRolPe.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }
}
