package controlador.docente;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import groovy.xml.Entity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import modelo.curso.CursoMD;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
    private final DocenteBD dc;
    private DocenteMD docenteMD;
//    private final String cedula;
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
        this.dc = new DocenteBD(ctrPrin.getConecta());
//        this.cedula = cedula;
        this.ID = ID;
        this.frmFinContrato = new JDFinContratacion(ctrPrin.getVtnPrin(), false);
        this.frmFinContrato.setLocationRelativeTo(ctrPrin.getVtnPrin());
        this.frmFinContrato.setVisible(true);
        this.frmFinContrato.setTitle("Fin de Contrato");
    }

    public void iniciar() {
        docenteMD = dc.buscarDocente(ID);
//        frmFinContrato.getBtnGuardar().setText("Siguiente");
        frmFinContrato.getBtn_Cancelar().addActionListener(e -> cancelar());
//        frmFinContrato.getTpFrm().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int pos = frmFinContrato.getTpFrm().getSelectedIndex();
//                if (pos > 0) {
//                    frmFinContrato.getBtnAnterior().setEnabled(true);
//                    habilitarGuardar();
//                } else {
//                    frmFinContrato.getBtnAnterior().setEnabled(false);
//                    habilitarGuardar();
//                }
//            }
//        });

        iniciarFinContrato();
        iniciarPeriodosDocente();
    }
    
    public void cancelar(){
        frmFinContrato.dispose();
    }

    public void iniciarPeriodosDocente() {

        frmFinContrato.getLbl_ErrPeriodos().setVisible(false);
//        frmFinContrato.getBtnAnterior().addActionListener(e -> pnlAnterior());
        frmFinContrato.getJcbPeriodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int pos = frmFinContrato.getJcbPeriodos().getSelectedIndex();
                if (pos > 0) {
                    frmFinContrato.getJcbPeriodos().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                    if (frmFinContrato.getLbl_ErrPeriodos() != null) {
                        frmFinContrato.getLbl_ErrPeriodos().setVisible(false);
                        String periodo = frmFinContrato.getJcbPeriodos().getSelectedItem().toString();
                        filtrarMaterias(periodo);
                        llenarTabla();
                        int num = frmFinContrato.getCbx_Periodos().getItemCount();
                        if(num < 2){
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
            }
           
        });

        listaPeriodos();
    }

    public void listaPeriodos() {
        periodoBD = new PeriodoLectivoBD(ctrPrin.getConecta());

        List<PeriodoLectivoMD> listaPeriodos = periodoBD.periodoDocente(ID);
        for (int i = 0; i < listaPeriodos.size(); i++) {
            frmFinContrato.getJcbPeriodos().addItem(listaPeriodos.get(i).getNombre_PerLectivo());
            
        }

    }
    
    public void filtrarMaterias(String nombre_Periodo){
        DocenteBD d = new DocenteBD(ctrPrin.getConecta());
        PeriodoLectivoBD p = new PeriodoLectivoBD(ctrPrin.getConecta());
        lista = d.capturarMaterias(p.capturarIdPeriodo(nombre_Periodo).getId_PerioLectivo(), docenteMD.getIdDocente());
        periodo = p.capturarIdPeriodo(nombre_Periodo).getId_PerioLectivo();
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
        if(docenteMD.isDocenteEnFuncion() == false){
            frmFinContrato.getJdcFinContratacion().setEnabled(false);
            frmFinContrato.getTxtObservacion().setEnabled(false);
            frmFinContrato.getBtnGuardar().setEnabled(false);
            frmFinContrato.getBtnReasignarMateria().setEnabled(true);
            frmFinContrato.getBtnReasignarMateria().addActionListener(e -> reasignarMateria());
        } else{
            frmFinContrato.getBtnGuardar().setEnabled(false);
            frmFinContrato.getBtnReasignarMateria().setEnabled(false);
            frmFinContrato.getBtnGuardar().addActionListener(e -> guardarFinContratacion());
            frmFinContrato.getBtnReasignarMateria().addActionListener(e -> reasignarMateria());
        }

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
        
        PropertyChangeListener habilitar = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                habilitarGuardar();
            }
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
        
        if(observacion.equals("") == false && fecha != null &&
                posPrd > 0){
            if(frmFinContrato.getLblErrorObservacion().isVisible() == false &&
                    frmFinContrato.getLbl_ErrPeriodos().isVisible() == false &&
                    lista != null){
                frmFinContrato.getBtnGuardar().setEnabled(true);
                guardar = true;
            } else{
                frmFinContrato.getBtnGuardar().setEnabled(false);
            }
        } else{
            frmFinContrato.getBtnGuardar().setEnabled(false);
        }
    }

    private void guardarFinContratacion() {

        String Observacion = "", periodo = "";
        Date fecha;

        Observacion = frmFinContrato.getTxtObservacion().getText().trim().toUpperCase();
        fecha = frmFinContrato.getJdcFinContratacion().getDate();
        periodo = frmFinContrato.getCbx_Periodos().getSelectedItem().toString();
        
        
//        if (!fechaFinContra.equals("")) {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            fechaFinContra = sdf.format(fecha);
//            validarFecha();
//            System.out.println("DIA " + fechaFinContra);
//        } else {
//            guardar = false;
//        }

        if (docenteMD.getFechaInicioContratacion().isAfter(convertirDate(fecha)) == false
                && docenteMD.getFechaInicioContratacion().isEqual(convertirDate(fecha)) == false) {
            guardar = true;
            frmFinContrato.getLblErrorFechaFinContratacion().setVisible(false);
        } else {
            guardar = false;
            frmFinContrato.getLblErrorFechaFinContratacion().setText("La fecha de inicio de contrato no puede ser mayor a la de finalizacion");
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
            periodoMD.setId_PerioLectivo(periodoBD.capturarIdPeriodo(periodo).getId_PerioLectivo());
            
            curso.setPeriodo(periodoMD);
            curso.setDocente(docente);
            if(dc.terminarContrato(docente) == true){
                System.out.println("Se finalizó contrato");
                if(lista != null){
                    int cont = 0;
                    for (int i = 0; i < lista.size(); i++) {
                        MateriaMD materia = new MateriaMD();
                        materia.setId(lista.get(i).getMateria().getId());
                        curso.setMateria(materia);
                        curso.setNombre(lista.get(i).getNombre());
                        if(dc.deshabilitarCursos(curso) == true){
                            cont++;
                        }
                    }
                    if(cont == lista.size()){
                        JOptionPane.showMessageDialog(null, "Se finalizó el contrato del Docente con éxito");
                        frmFinContrato.getBtnReasignarMateria().setEnabled(true);
                        this.periodo = periodoMD.getId_PerioLectivo();
                        botoninformeDocente();
                    } else{
                        JOptionPane.showMessageDialog(null, "No se pudo finalizar el contrato de este Docente");
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Se finalizó el contrato del Docente");
                    frmFinContrato.getBtnReasignarMateria().setEnabled(true);
                    this.periodo = periodoMD.getId_PerioLectivo();
                    botoninformeDocente();
                }
            } else{
                JOptionPane.showMessageDialog(null, "No se pudo finalizar el contrato de este Docente");
            }
            
//            VtnPeriodosDocenteCTR vtnPeriodoDocenteCTR = new VtnPeriodosDocenteCTR(conecta, vtnPrin, docenteMD);
//            vtnPeriodoDocenteCTR.iniciarPeriodosDocente();
//            frmFinContrato.dispose();
            System.out.println("Se guarda en base de datos");
            
        } else {
            frmFinContrato.getTpFrm().setSelectedIndex(1);
            habilitarGuardar();

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
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Informe de Retiro");

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

    public void botoninformeDocente() {
        int s = JOptionPane.showOptionDialog(ctrPrin.getVtnPrin(),
                "Registro de persona \n"
                + "¿Dessea Imprimir el Registro realizado ?", "Informe de Retiro",
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
        posFila = frmFinContrato.getTblMateriasCursos().getSelectedRow();
        if (posFila >= 0) {
            System.out.println("periodo" + periodo);
            JDReasignarMateriasCTR ctr = new JDReasignarMateriasCTR(ctrPrin, frmFinContrato.getTblMateriasCursos().getValueAt(posFila, 0).toString(),
            frmFinContrato.getTblMateriasCursos().getValueAt(posFila, 1).toString(), periodo, docenteMD.getIdDocente());
            ctr.iniciar();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila ");
        }

    }

}
