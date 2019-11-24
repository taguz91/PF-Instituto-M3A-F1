package controlador.pagos;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.CMBAlumnoBD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.periodolectivo.CMBPeriodoLectivoBD;
import modelo.persona.AlumnoMD;
import vista.pagos.FrmComprobantes;

/**
 *
 * @author gus
 */
public class FRMComprobanteCTR extends DCTR {

    private final FrmComprobantes FRM = new FrmComprobantes();
    // Lista de objetos  
    private List<PeriodoLectivoMD> pls;
    private List<AlumnoMD> as;
    // Conexiones a la base de datos  
    private final CMBPeriodoLectivoBD PLBD = CMBPeriodoLectivoBD.single();
    private final CMBAlumnoBD CABD = CMBAlumnoBD.single();
    // Modelo de las tablas 
    private DefaultTableModel mdTblAlum, mdTblMate;

    public FRMComprobanteCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void iniciar() {
        iniciarCmbPeriodo();
        inicarTbls();
        iniciarBuscarAlumno();
        ctrPrin.agregarVtn(FRM);
    }

    private void inicarTbls() {
        String[] T_TBLALUM = {"Nombre"};
        mdTblAlum = TblEstilo.modelTblSinEditar(T_TBLALUM);
        FRM.getTblAlumnos().setModel(mdTblAlum);
        String[] T_TBLMATERIA = {"Materia", "# Matricula", "Monto"};
        mdTblMate = TblEstilo.modelTblSinEditar(T_TBLMATERIA);
        FRM.getTblMaterias().setModel(mdTblMate);
    }

    private void iniciarCmbPeriodo() {
        pls = PLBD.getForCmbSoloAbiertos();
        FRM.getCmbPeriodo().removeAllItems();
        FRM.getCmbPeriodo().addItem("Seleccione");
        pls.forEach(pl -> {
            FRM.getCmbPeriodo().addItem(pl.getNombre());
        });
    }

    private void iniciarBuscarAlumno() {
        FRM.getBtnBuscarAlumno().addActionListener(e -> buscarAlumno(
                FRM.getTxtAlumno().getText().trim()
        ));
        FRM.getTxtAlumno().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    buscarAlumno(FRM.getTxtAlumno().getText().trim());
                }
            }
        });
    }

    private void buscarAlumno(String aguja) {
        int posPeriodo = FRM.getCmbPeriodo().getSelectedIndex();
        if (posPeriodo > 0) {
            cursorCarga(FRM);
            as = CABD.getForBusquedaPeriodo(
                    aguja,
                    pls.get(posPeriodo - 1).getID()
            );
            mdTblAlum.setRowCount(0);
            as.forEach(a -> {
                mdTblAlum.addRow(new Object[]{a.getApellidosNombres()});
            });

            cursorNormal(FRM);
        } else {
            JOptionPane.showMessageDialog(
                    FRM,
                    "Debe seleccionar un periodo antes de buscar."
            );
        }
    }

}
