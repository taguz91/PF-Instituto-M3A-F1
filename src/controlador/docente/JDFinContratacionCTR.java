package controlador.docente;

import controlador.principal.DependenciasCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.curso.CursoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.Validar;
import vista.docente.JDFinContratacion;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class JDFinContratacionCTR extends DependenciasCTR {

    private PeriodoLectivoBD periodoBD;
    private DocenteBD dc;
    private DocenteMD docenteMD;
    private String cedula;
    private final JDFinContratacion frmFinContrato;
    private static LocalDate fechaInicio;
    private DefaultTableModel mdTbl;
    private boolean guardar = false;

    public JDFinContratacionCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin,
            String cedula) {
        super(conecta, vtnPrin, ctrPrin);
        this.dc = new DocenteBD(conecta);
        this.cedula = cedula;
        this.frmFinContrato = new JDFinContratacion(vtnPrin, false);
        this.frmFinContrato.setLocationRelativeTo(vtnPrin);
        this.frmFinContrato.setVisible(true);
        this.frmFinContrato.setTitle("Fin de Contrato");
    }

    public void iniciar() {
        docenteMD = dc.buscarDocente(cedula);

        iniciarFinContrato();
        iniciarPeriodosDocente();
    }

    public void iniciarPeriodosDocente() {

        frmFinContrato.getBtnCancelar().addActionListener(e -> cancelarPeriodo());
        frmFinContrato.getJcbPeriodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String periodo = frmFinContrato.getJcbPeriodos().getSelectedItem().toString();
                if (periodo.equals("NINGUNO") == true) {
                    JOptionPane.showConfirmDialog(null, "Este Docente no tiene ninguna materia asignada al no tener un Período Lectivo");
                } else {
                    llenarTabla(periodo);
                }
            }

        });

        listaPeriodos();
    }

    public void listaPeriodos() {
        periodoBD = new PeriodoLectivoBD(conecta);

        List<PeriodoLectivoMD> listaPeriodos = periodoBD.llenarTabla();
        for (int i = 0; i < listaPeriodos.size(); i++) {
            frmFinContrato.getJcbPeriodos().addItem(listaPeriodos.get(i).getNombre_PerLectivo());

        }

    }

    public void llenarTabla(String periodo) {
        System.out.println("Periodo " + periodo);
        DocenteBD d = new DocenteBD(conecta);
        PeriodoLectivoBD p = new PeriodoLectivoBD(conecta);
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) frmFinContrato.getTblMateriasCursos().getModel();
        for (int i = frmFinContrato.getTblMateriasCursos().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<CursoMD> lista = d.capturarMaterias(p.buscarPeriodo(periodo).getId_PerioLectivo(), docenteMD.getIdDocente());
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            frmFinContrato.getTblMateriasCursos().setValueAt(lista.get(i).getId_materia().getNombre(), i, 0);
            frmFinContrato.getTblMateriasCursos().setValueAt(lista.get(i).getCurso_nombre(), i, 1);
        }
    }

    private void aceptar() {
        frmFinContrato.getJcbPeriodos().getSelectedItem();

    }

    private void cancelarPeriodo() {
        frmFinContrato.dispose();
        System.out.println("Se dio clic en cancelar periodo docente");

    }

    public void iniciarFinContrato() {
        frmFinContrato.getLblErrorFechaFinContratacion().setVisible(false);
        frmFinContrato.getLblErrorObservacion().setVisible(false);
        frmFinContrato.getBtnGuardar().setEnabled(false);
        frmFinContrato.getBtnGuardar().addActionListener(e -> guardarFinContratacion());
        frmFinContrato.getBtnCancelar().addActionListener(e -> salir());

        frmFinContrato.getTxtObservacion().addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                String Observacion = frmFinContrato.getTxtObservacion().getText();
                if (!Validar.esObservacion(Observacion)) {

                    frmFinContrato.getLblErrorObservacion().setVisible(true);

                } else {
                    frmFinContrato.getLblErrorObservacion().setVisible(false);
                }
                habilitarGuardar();
            }
        });

        frmFinContrato.getJdcFinContratacion().addMouseListener(new MouseAdapter() {
            public void mouseClicked() {
                if (validarFecha() == true) {
                    frmFinContrato.getLblErrorFechaFinContratacion().setVisible(false);
                } else {
                    frmFinContrato.getLblErrorFechaFinContratacion().setText("El inicio de contrato no puede ser \n mayor al de finalizacion");
                    frmFinContrato.getLblErrorFechaFinContratacion().setVisible(true);
                }
                habilitarGuardar();
            }
        });

        docenteMD = dc.buscarDocente(cedula);
    }

    public void habilitarGuardar() {
        String observacion;
        observacion = frmFinContrato.getTxtObservacion().getText();
        Date fecha = frmFinContrato.getJdcFinContratacion().getDate();
        if (observacion.equals("") == false || fecha != null) {
            if (frmFinContrato.getLblErrorFechaFinContratacion().isVisible() == true
                    || frmFinContrato.getLblErrorObservacion().isVisible() == true) {
                frmFinContrato.getBtnGuardar().setEnabled(false);
            } else {
                frmFinContrato.getBtnGuardar().setEnabled(true);
            }
        } else {
            frmFinContrato.getBtnGuardar().setEnabled(false);
        }

    }

    private void guardarFinContratacion() {

        String Observacion, fechaFinContra;
        Date fecha;

        Observacion = frmFinContrato.getTxtObservacion().getText().trim().toUpperCase();
        fecha = frmFinContrato.getJdcFinContratacion().getDate();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaFinContra = sdf.format(fecha);

//        if (docentesMD.getFechaInicioContratacion().isAfter(convertirDate(fecha)) == false
//                && docentesMD.getFechaInicioContratacion().isEqual(convertirDate(fecha)) == false) {
//            guardar = true;
//            vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(false);
//        } else {
//            guardar = false;
//            vtnFinContratacion.getLblErrorFechaFinContratacion().setText("La fecha de inicio de contrato no puede ser mayor a la de finalizacion");
//            vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(true);
//        }
        validarFecha();
        System.out.println("DIA " + fechaFinContra);

        if (guardar == true) {
//            DocenteMD docente = new DocenteMD();
//            docente.setObservacion(Observacion);
//            docente.setFechaFinContratacion(convertirDate(fecha));

//            VtnPeriodosDocenteCTR vtnPeriodoDocenteCTR = new VtnPeriodosDocenteCTR(conecta, vtnPrin, docenteMD);
//            vtnPeriodoDocenteCTR.iniciarPeriodosDocente();
//            frmFinContrato.dispose();
              frmFinContrato.getTpFrm().setSelectedIndex(2);
        }

    }

    public boolean validarFecha() {
        Date fecha;
        fecha = frmFinContrato.getJdcFinContratacion().getDate();

        if (docenteMD.getFechaInicioContratacion().isAfter(convertirDate(fecha)) == false
                && docenteMD.getFechaInicioContratacion().isEqual(convertirDate(fecha)) == false) {

            return true;
        } else {

            return false;
        }

    }

    private void salir() {
        frmFinContrato.dispose();
        System.out.println("Se dio click en cancelar Fin contratacion");
    }

    public LocalDate convertirDate(Date fecha) {
        return Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
