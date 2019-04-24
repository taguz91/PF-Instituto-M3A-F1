/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.docente;

import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.docente.RolPeriodoBD;
import modelo.docente.RolPeriodoMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.docente.FrmRolesPeriodos;
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
    private final RolPeriodoBD rolPer;
    private DefaultTableModel mdTbl;
// para combo de periodo
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;
    private ArrayList<RolPeriodoMD> roles;
    private int posFila;

    public VtnRolPeriodosCTR(VtnPrincipal vtnPrin, VtnRolesPeriodos vtnRolPe, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.vtnRolPe = vtnRolPe;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.rolPer = new RolPeriodoBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnRolPe);
        vtnRolPe.show();
    }

    public void iniciar() {
        String titulo[] = {"Periodos Lectivos", "Roles"};
        String datos[][] = {};

        //Usamos el modelo que no nos deja editar los campos
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        //Le pasamos el modelo a la tabla  v
        vtnRolPe.getTblAlumno().setModel(mdTbl);
        //Pasamos el estilo a la tabla 
        TblEstilo.formatoTbl(vtnRolPe.getTblAlumno());
        vtnRolPe.getTblAlumno().setModel(mdTbl);

        vtnRolPe.getBtnIngresar().addActionListener(e -> abrirFRM());
        vtnRolPe.getBtnEditar().addActionListener(e -> abrirFrmEditar());
        vtnRolPe.getBtnEliminar().addActionListener(e -> eliminarRolPeriodo());
        llenarTabla();
    }

    private void abrirFRM() {
        ctrPrin.abrirFrmRolesPeriodos();
        vtnRolPe.dispose();
        ctrPrin.cerradoJIF();
    }

    public void abrirFrmEditar() {
        posFila = vtnRolPe.getTblAlumno().getSelectedRow();
        if (posFila >= 0) {
            //ctrPrin.abrirFrmRolesPeriodos();
            FrmRolesPeriodos frm = new FrmRolesPeriodos();
            ctrPrin.eventoInternal(frm);
            FrmRolPeriodoCTR ctr = new FrmRolPeriodoCTR(vtnPrin, frm, conecta, ctrPrin);
            ctr.iniciar();
            ctr.editarRolesPeriodos(roles.get(posFila));
            vtnRolPe.dispose();
            ctrPrin.cerradoJIF();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para editar");
        }
    }

    public void eliminarRolPeriodo() {
        posFila = vtnRolPe.getTblAlumno().getSelectedRow();
        if (posFila >= 0) {
            rolPer.eliminarRolPeriodo(roles.get(posFila).getId_rol());
            JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
            llenarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
        }
    }

    public void llenarTabla() {
        mdTbl = (DefaultTableModel) vtnRolPe.getTblAlumno().getModel();
        for (int i = vtnRolPe.getTblAlumno().getRowCount() - 1; i >= 0; i--) {
            mdTbl.removeRow(i);
        }

        roles = rolPer.llenarTabla();
        int columnas = mdTbl.getColumnCount();
        for (int i = 0; i < roles.size(); i++) {
            mdTbl.addRow(new Object[columnas]);
            vtnRolPe.getTblAlumno().setValueAt(String.valueOf(roles.get(i).getPeriodo().getNombre_PerLectivo()), i, 0);
            vtnRolPe.getTblAlumno().setValueAt(String.valueOf(roles.get(i).getNombre_rol()), i, 1);

        }
        vtnRolPe.getLblResultados().setText(String.valueOf(roles.size()) + " Resultados obtenidos.");
    }
}
