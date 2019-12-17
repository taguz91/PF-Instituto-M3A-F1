package controlador.docente;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import modelo.curso.CursoMD;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.curso.CursoBD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.docente.JDFinContratacion;

/**
 *
 * @author Lina
 */
public class JDFinContratacionCTR extends DVtnCTR {

    private PeriodoLectivoBD periodoBD;
    private final DocenteBD DBD = DocenteBD.single();
    private DocenteMD docenteMD;
    private final int ID;
    private int periodo;
    private final JDFinContratacion frmFinContrato;
    private static LocalDate fechaInicio;
    //private DefaultTableModel mdTbl;
    private boolean guardar = false;
    private List<CursoMD> lista;

    public JDFinContratacionCTR(VtnPrincipalCTR ctrPrin,
            String cedula, int ID) {
        super(ctrPrin);
        this.ID = ID;
        this.frmFinContrato = new JDFinContratacion(ctrPrin.getVtnPrin(), false);
        this.frmFinContrato.setLocationRelativeTo(ctrPrin.getVtnPrin());
        this.frmFinContrato.setVisible(true);
        this.frmFinContrato.setTitle("Fin de Contrato");
    }

    public void iniciar() {
        docenteMD = DBD.buscarDocente(ID);
        frmFinContrato.getBtn_Cancelar().addActionListener(e -> cancelar());
        iniciarFinContrato();
        iniciarPeriodosDocente();
    }

    public void cancelar() {
        frmFinContrato.dispose();
    }

    public void iniciarPeriodosDocente() {
        frmFinContrato.getLbl_ErrPeriodos().setVisible(false);
        frmFinContrato.getJcbPeriodos().addActionListener((ActionEvent e) -> {
            int pos = frmFinContrato.getJcbPeriodos().getSelectedIndex();
            if (pos > 0) {
                frmFinContrato.getJcbPeriodos().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                if (frmFinContrato.getLbl_ErrPeriodos() != null) {
                    CursoBD CBD = CursoBD.single();
                    frmFinContrato.getLbl_ErrPeriodos().setVisible(false);
                    String periodo1 = frmFinContrato.getJcbPeriodos().getSelectedItem().toString();
                    List<Integer> IDs = CBD.consultaCursos();
                    filtrarMaterias(periodo1, IDs);
                    llenarTabla();
                    int num = frmFinContrato.getCbx_Periodos().getItemCount();
                    if (num < 2) {
                        JOptionPane.showMessageDialog(null, "No se filtró ningún Período Lectivo de este Docente");
                    }
                }
            } else {
                frmFinContrato.getJcbPeriodos().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                if (frmFinContrato.getLbl_ErrPeriodos() != null) {
                    frmFinContrato.getLbl_ErrPeriodos().setVisible(true);
                    DefaultTableModel modelo_Tabla;
                    modelo_Tabla = (DefaultTableModel) frmFinContrato.getTblMateriasCursos().getModel();
                    for (int i = frmFinContrato.getTblMateriasCursos().getRowCount() - 1; i >= 0; i--) {
                        modelo_Tabla.removeRow(i);
                    }
                }
            }
            habilitarGuardar();
        });

        listaPeriodos();
    }

    public void listaPeriodos() {
        periodoBD = PeriodoLectivoBD.single();
        List<PeriodoLectivoMD> listaPeriodos = periodoBD.periodoDocente(ID);
        for (int i = 0; i < listaPeriodos.size(); i++) {
            frmFinContrato.getJcbPeriodos().addItem(listaPeriodos.get(i).getNombre());
        }
    }

    public void filtrarMaterias(String nombre_Periodo, List<Integer> num) {
        DocenteBD DBD = DocenteBD.single();
        PeriodoLectivoBD PLBD = PeriodoLectivoBD.single();
        lista = DBD.capturarMaterias(PLBD.capturarIdPeriodo(nombre_Periodo).getID(), docenteMD.getIdDocente());

        for (int x = 0; x < num.size(); x++) {
            for (int i = 0; i < lista.size(); i++) {
                if (num.get(x) == lista.get(i).getMateria().getId()) {
                    lista.remove(i);
                    break;
                }
            }
        }
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todas las materias ya fueron reasignadas");
        }
        periodo = PLBD.capturarIdPeriodo(nombre_Periodo).getID();
    }

    public void llenarTabla() {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) frmFinContrato.getTblMateriasCursos().getModel();
        for (int i = frmFinContrato.getTblMateriasCursos().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            frmFinContrato.getTblMateriasCursos().setValueAt(lista.get(i).getMateria().getNombre(), i, 0);
            frmFinContrato.getTblMateriasCursos().setValueAt(lista.get(i).getNombre(), i, 1);
        }
    }

    private void aceptar() {
        frmFinContrato.getJcbPeriodos().getSelectedItem();
    }

    private void pnlAnterior() {
        frmFinContrato.getTpFrm().setSelectedIndex(0);
        frmFinContrato.getBtnGuardar().setEnabled(true);
        frmFinContrato.getBtnGuardar().setText("Siguiente");
        habilitarGuardar();
    }

    public void iniciarFinContrato() {
        frmFinContrato.getLblErrorFechaFinContratacion().setVisible(false);
        frmFinContrato.getLblErrorObservacion().setVisible(false);
        if (docenteMD.isDocenteEnFuncion() == false) {
            frmFinContrato.getJdcFinContratacion().setEnabled(false);
            frmFinContrato.getTxtObservacion().setEnabled(false);
            frmFinContrato.getBtnGuardar().setEnabled(false);
            frmFinContrato.getBtnReasignarMateria().setEnabled(true);
            frmFinContrato.getBtnReasignarMateria().addActionListener(e -> reasignarMateria());
        } else {
            frmFinContrato.getBtnGuardar().setEnabled(false);
            frmFinContrato.getBtnReasignarMateria().setEnabled(false);
            frmFinContrato.getBtnGuardar().addActionListener(e -> guardarFinContratacion());
            frmFinContrato.getBtnReasignarMateria().addActionListener(e -> reasignarMateria());
        }

        frmFinContrato.getTxtObservacion().addKeyListener(new KeyAdapter() {

            @Override
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
                    frmFinContrato.getLblErrorFechaFinContratacion().setText("El inicio de contrato no puede ser \n mayor al de finalización");
                    frmFinContrato.getLblErrorFechaFinContratacion().setVisible(true);
                }
                habilitarGuardar();
            }
        });

        PropertyChangeListener habilitar = (PropertyChangeEvent evt) -> {
            habilitarGuardar();
        };

        frmFinContrato.getCbx_Periodos().addActionListener(new CmbValidar(
                frmFinContrato.getCbx_Periodos(), frmFinContrato.getLbl_ErrPeriodos()));
        frmFinContrato.getCbx_Periodos().addPropertyChangeListener(habilitar);

//        docenteMD = dc.buscarDocente(cedula);
    }

    public void habilitarGuardar() {
        String observacion = frmFinContrato.getTxtObservacion().getText();
        Date fecha = frmFinContrato.getJdcFinContratacion().getDate();
        int posPrd = frmFinContrato.getCbx_Periodos().getSelectedIndex();
        int pos = frmFinContrato.getTpFrm().getSelectedIndex();

        if (observacion.equals("") == false && fecha != null
                && posPrd > 0) {
            if (frmFinContrato.getLblErrorObservacion().isVisible() == false
                    && frmFinContrato.getLbl_ErrPeriodos().isVisible() == false
                    && lista != null) {
                frmFinContrato.getBtnGuardar().setEnabled(true);
                guardar = true;
            } else {
                frmFinContrato.getBtnGuardar().setEnabled(false);
            }
        } else {
            frmFinContrato.getBtnGuardar().setEnabled(false);
        }
    }

    private void guardarFinContratacion() {

        String Observacion = "", periodo = "";
        Date fecha;

        Observacion = frmFinContrato.getTxtObservacion().getText().trim().toUpperCase();
        fecha = frmFinContrato.getJdcFinContratacion().getDate();
        periodo = frmFinContrato.getCbx_Periodos().getSelectedItem().toString();
        if (docenteMD.getFechaInicioContratacion().isAfter(convertirDate(fecha)) == false
                && docenteMD.getFechaInicioContratacion().isEqual(convertirDate(fecha)) == false) {
            guardar = true;
            frmFinContrato.getLblErrorFechaFinContratacion().setVisible(false);
        } else {
            guardar = false;
            frmFinContrato.getLblErrorFechaFinContratacion().setText("La fecha de inicio de contrato no puede ser mayor a la de finalización");
            frmFinContrato.getLblErrorFechaFinContratacion().setVisible(true);
            JOptionPane.showMessageDialog(null, "No se puede guardar, revise la Fecha de Culminación de Contrato");
        }
        if (guardar == true) {
            DocenteMD docente = new DocenteMD();
            CursoMD curso = new CursoMD();
            PeriodoLectivoMD periodoMD = new PeriodoLectivoMD();
            docente.setObservacion(Observacion);
            docente.setFechaFinContratacion(convertirDate(fecha));
            docente.setIdDocente(docenteMD.getIdDocente());
            periodoMD.setID(periodoBD.capturarIdPeriodo(periodo).getID());

            curso.setPeriodo(periodoMD);
            curso.setDocente(docente);
            if (DBD.terminarContrato(docente) == true) {
                System.out.println("Se finalizó contrato");
                if (lista != null) {
                    int cont = 0;
                    for (int i = 0; i < lista.size(); i++) {
                        MateriaMD materia = new MateriaMD();
                        materia.setId(lista.get(i).getMateria().getId());
                        curso.setMateria(materia);
                        curso.setNombre(lista.get(i).getNombre());
                        if (DBD.deshabilitarCursos(curso) == true) {
                            cont++;
                        }
                    }
                    if (cont == lista.size()) {
                        JOptionPane.showMessageDialog(null, "Se finalizó el contrato del Docente con éxito");
                        frmFinContrato.getBtnReasignarMateria().setEnabled(true);
                        this.periodo = periodoMD.getID();
                        botoninformeDocente();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo finalizar el contrato de este Docente");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Se finalizó el contrato del Docente");
                    frmFinContrato.getBtnReasignarMateria().setEnabled(true);
                    this.periodo = periodoMD.getID();
                    botoninformeDocente();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo finalizar el contrato de este Docente");
            }

            System.out.println("Se guarda en base de datos");

        } else {
            frmFinContrato.getTpFrm().setSelectedIndex(1);
            habilitarGuardar();

        }

    }

    public boolean validarFecha() {
        Date fecha;
        fecha = frmFinContrato.getJdcFinContratacion().getDate();
        return docenteMD.getFechaInicioContratacion().isAfter(convertirDate(fecha)) == false
                && docenteMD.getFechaInicioContratacion().isEqual(convertirDate(fecha)) == false;
    }

    public LocalDate convertirDate(Date fecha) {
        return Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //llamada al informe de fin de contratacion
    public void llamainformeDocente() {
        JasperReport jr;
        String path = "/vista/reportes/INFORME_DOCENTE_RETIRADO.jasper";
        //int posfila = vtnDocente.getTblDocente().getSelectedRow();

        try {
            Map parametro = new HashMap();
            parametro.put("iddocente", docenteMD.getIdDocente());

            parametro.put("periodolectivo", frmFinContrato.getJcbPeriodos());
            parametro.put("periodolectivo", frmFinContrato.getCbx_Periodos().getSelectedItem().toString());
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            CON.mostrarReporte(jr, parametro, "Informe de Retiro");

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

    public void botoninformeDocente() {
        int s = JOptionPane.showOptionDialog(ctrPrin.getVtnPrin(),
                "Registro de persona \n"
                + "¿Desea Imprimir el Registro realizado ?", "Informe de Retiro",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"SI", "NO"}, "NO");
        switch (s) {
            case 0:
                llamainformeDocente();
                break;
            case 1:

                break;
            default:
                break;
        }
    }

    private void reasignarMateria() {
        CursoBD bdCurso = CursoBD.single();
        CursoMD c = new CursoMD();
        MateriaBD bdMateria = MateriaBD.single();
        posFila = frmFinContrato.getTblMateriasCursos().getSelectedRow();
        if (posFila >= 0) {
            boolean activo = bdCurso.atraparCurso(bdMateria.buscarMateria(frmFinContrato.getTblMateriasCursos().getValueAt(posFila, 0).toString()).getId(),
                    periodo, docenteMD.getIdDocente(), frmFinContrato.getTblMateriasCursos().getValueAt(posFila, 1).toString()).isActivo();
            if (activo) {
                JOptionPane.showMessageDialog(null, "Este curso está activo, para reasignarlo a un nuevo docente debe eliminarlo");
            } else {
                JDReasignarMateriasCTR ctr = new JDReasignarMateriasCTR(ctrPrin, frmFinContrato.getTblMateriasCursos().getValueAt(posFila, 0).toString(),
                        frmFinContrato.getTblMateriasCursos().getValueAt(posFila, 1).toString(), periodo, docenteMD.getIdDocente());
                ctr.iniciar();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila ");
        }

    }

}
