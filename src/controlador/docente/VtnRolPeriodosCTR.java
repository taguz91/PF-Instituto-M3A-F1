package controlador.docente;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.docente.RolPeriodoBD;
import modelo.docente.RolPeriodoMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.docente.FrmRolesPeriodos;
import vista.docente.VtnRolesPeriodos;

/**
 *
 * @author arman
 */
public class VtnRolPeriodosCTR extends DVtnCTR {

    private final VtnRolesPeriodos vtnRolPe;
    private final RolPeriodoBD rolPer;
// para combo de periodo
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;
    private ArrayList<RolPeriodoMD> roles;

    public VtnRolPeriodosCTR(VtnRolesPeriodos vtnRolPe, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnRolPe = vtnRolPe;
        this.rolPer = new RolPeriodoBD(ctrPrin.getConecta());
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
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

        ctrPrin.agregarVtn(vtnRolPe);
        InitPermisos();
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
            FrmRolPeriodoCTR ctr = new FrmRolPeriodoCTR(frm, ctrPrin);
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
            vtnRolPe.getTblAlumno().setValueAt(String.valueOf(roles.get(i).getPeriodo().getNombre()), i, 0);
            vtnRolPe.getTblAlumno().setValueAt(String.valueOf(roles.get(i).getNombre_rol()), i, 1);

        }
        vtnRolPe.getLblResultados().setText(String.valueOf(roles.size()) + " Resultados obtenidos.");
    }

    private void InitPermisos() {
        vtnRolPe.getBtnIngresar().getAccessibleContext().setAccessibleName("Roles-Periodo-Ingresar");
       vtnRolPe.getBtnEliminar().getAccessibleContext().setAccessibleName("Roles-Periodo-Eliminar ");
       vtnRolPe.getBtnEditar().getAccessibleContext().setAccessibleName("Roles-Periodo-Editar");

        CONS.activarBtns(vtnRolPe.getBtnIngresar(), vtnRolPe.getBtnEditar(), vtnRolPe.getBtnEliminar());
    }
}
