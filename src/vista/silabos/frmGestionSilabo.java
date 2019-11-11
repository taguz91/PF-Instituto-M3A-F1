/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.silabos;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Andres Ullauri
 */
public class frmGestionSilabo extends javax.swing.JInternalFrame {

    /**
     * Creates new form Principal
     */
    public frmGestionSilabo() {
        initComponents();

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("vista/img/logo.png"));
        this.setFrameIcon(icon);
    }

    public JLabel getLblAcumuladoGestion() {
        return lblAcumuladoGestion;
    }

    public void setLblAcumuladoGestion(JLabel lblAcumuladoGestion) {
        this.lblAcumuladoGestion = lblAcumuladoGestion;
    }

    public JLabel getLblTotalGestion() {
        return lblTotalGestion;
    }

    public void setLblTotalGestion(JLabel lblTotalGestion) {
        this.lblTotalGestion = lblTotalGestion;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JLabel getLblEliminarUnidad() {
        return lblEliminarUnidad;
    }

    public void setLblEliminarUnidad(JLabel lblEliminarUnidad) {
        this.lblEliminarUnidad = lblEliminarUnidad;
    }

    public JLabel getLblEstrategiasPredeterminadas() {
        return lblEstrategiasPredeterminadas;
    }

    public void setLblEstrategiasPredeterminadas(JLabel lblEstrategiasPredeterminadas) {
        this.lblEstrategiasPredeterminadas = lblEstrategiasPredeterminadas;
    }

    public JLabel getLblHorasAutonomas() {
        return lblHorasAutonomas;
    }

    public void setLblHorasAutonomas(JLabel lblHorasAutonomas) {
        this.lblHorasAutonomas = lblHorasAutonomas;
    }

    public JLabel getLblHorasDocencia() {
        return lblHorasDocencia;
    }

    public void setLblHorasDocencia(JLabel lblHorasDocencia) {
        this.lblHorasDocencia = lblHorasDocencia;
    }

    public JLabel getLblHorasPracticas() {
        return lblHorasPracticas;
    }

    public void setLblHorasPracticas(JLabel lblHorasPracticas) {
        this.lblHorasPracticas = lblHorasPracticas;
    }

    public JLabel getLblNuevaEstrategia() {
        return lblNuevaEstrategia;
    }

    public void setLblNuevaEstrategia(JLabel lblNuevaEstrategia) {
        this.lblNuevaEstrategia = lblNuevaEstrategia;
    }

    public JList getLstEstrategiasPredeterminadas() {
        return lstEstrategiasPredeterminadas;
    }

    public void setLstEstrategiasPredeterminadas(JList lstEstrategiasPredeterminadas) {
        this.lstEstrategiasPredeterminadas = lstEstrategiasPredeterminadas;
    }

    public JScrollPane getScrEstrategiasPredeterminadas() {
        return scrEstrategiasPredeterminadas;
    }

    public void setScrEstrategiasPredeterminadas(JScrollPane scrEstrategiasPredeterminadas) {
        this.scrEstrategiasPredeterminadas = scrEstrategiasPredeterminadas;
    }

    public JSpinner getSpnHorasAutonomas() {
        return spnHorasAutonomas;
    }

    public void setSpnHorasAutonomas(JSpinner spnHorasAutonomas) {
        this.spnHorasAutonomas = spnHorasAutonomas;
    }

    public JSpinner getSpnHorasDocencia() {
        return spnHorasDocencia;
    }

    public void setSpnHorasDocencia(JSpinner spnHorasDocencia) {
        this.spnHorasDocencia = spnHorasDocencia;
    }

    public JSpinner getSpnHorasPracticas() {
        return spnHorasPracticas;
    }

    public void setSpnHorasPracticas(JSpinner spnHorasPracticas) {
        this.spnHorasPracticas = spnHorasPracticas;
    }

    public JTextField getTxtNuevaEstrategia() {
        return txtNuevaEstrategia;
    }

    public void setTxtNuevaEstrategia(JTextField txtNuevaEstrategia) {
        this.txtNuevaEstrategia = txtNuevaEstrategia;
    }

    public JLabel getLblAgregarEstrategia() {
        return lblAgregarEstrategia;
    }

    public void setLblAgregarEstrategia(JLabel lblAgregarEstrategia) {
        this.lblAgregarEstrategia = lblAgregarEstrategia;
    }

    public JLabel getLblAgregarUnidad() {
        return lblAgregarUnidad;
    }

    public void setLblAgregarUnidad(JLabel lblAgregarUnidad) {
        this.lblAgregarUnidad = lblAgregarUnidad;
    }

    public JButton getBtnAgregarA() {
        return btnAgregarA;
    }

    public void setBtnAgregarA(JButton btnAgregarA) {
        this.btnAgregarA = btnAgregarA;
    }

    public JButton getBtnAgregarAC() {
        return btnAgregarAC;
    }

    public void setBtnAgregarAC(JButton btnAgregarAC) {
        this.btnAgregarAC = btnAgregarAC;
    }

    public JButton getBtnAgregarAD() {
        return btnAgregarAD;
    }

    public void setBtnAgregarAD(JButton btnAgregarAD) {
        this.btnAgregarAD = btnAgregarAD;
    }

    public JButton getBtnAgregarP() {
        return btnAgregarP;
    }

    public void setBtnAgregarP(JButton btnAgregarP) {
        this.btnAgregarP = btnAgregarP;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }

    public void setBtnSiguiente(JButton btnFinalizar) {
        this.btnSiguiente = btnFinalizar;
    }

    public JButton getBtnQuitarA() {
        return btnQuitarA;
    }

    public void setBtnQuitarA(JButton btnQuitarA) {
        this.btnQuitarA = btnQuitarA;
    }

    public JButton getBtnQuitarAC() {
        return btnQuitarAC;
    }

    public void setBtnQuitarAC(JButton btnQuitarAC) {
        this.btnQuitarAC = btnQuitarAC;
    }

    public JButton getBtnQuitarAD() {
        return btnQuitarAD;
    }

    public void setBtnQuitarAD(JButton btnQuitarAD) {
        this.btnQuitarAD = btnQuitarAD;
    }

    public JButton getBtnQuitarP() {
        return btnQuitarP;
    }

    public void setBtnQuitarP(JButton btnQuitarP) {
        this.btnQuitarP = btnQuitarP;
    }

    public JComboBox<String> getCmbUnidad() {
        return cmbUnidad;
    }

    public void setCmbUnidad(JComboBox<String> cmbUnidad) {
        this.cmbUnidad = cmbUnidad;
    }

    public JDateChooser getDchFechaEnvioA() {
        return dchFechaEnvioA;
    }

    public void setDchFechaEnvioA(JDateChooser dchFechaEnvioA) {
        this.dchFechaEnvioA = dchFechaEnvioA;
    }

    public JDateChooser getDchFechaEnvioAC() {
        return dchFechaEnvioAC;
    }

    public void setDchFechaEnvioAC(JDateChooser dchFechaEnvioAC) {
        this.dchFechaEnvioAC = dchFechaEnvioAC;
    }

    public JDateChooser getDchFechaEnvioAD() {
        return dchFechaEnvioAD;
    }

    public void setDchFechaEnvioAD(JDateChooser dchFechaEnvioAD) {
        this.dchFechaEnvioAD = dchFechaEnvioAD;
    }

    public JDateChooser getDchFechaEnvioP() {
        return dchFechaEnvioP;
    }

    public void setDchFechaEnvioP(JDateChooser dchFechaEnvioP) {
        this.dchFechaEnvioP = dchFechaEnvioP;
    }

    public JDateChooser getDchFechaFin() {
        return dchFechaFin;
    }

    public void setDchFechaFin(JDateChooser dchFechaFin) {
        this.dchFechaFin = dchFechaFin;
    }

    public JDateChooser getDchFechaInicio() {
        return dchFechaInicio;
    }

    public void setDchFechaInicio(JDateChooser dchFechaInicio) {
        this.dchFechaInicio = dchFechaInicio;
    }

    public JDateChooser getDchFechaPresentacionA() {
        return dchFechaPresentacionA;
    }

    public void setDchFechaPresentacionA(JDateChooser dchFechaPresentacionA) {
        this.dchFechaPresentacionA = dchFechaPresentacionA;
    }

    public JDateChooser getDchFechaPresentacionAC() {
        return dchFechaPresentacionAC;
    }

    public void setDchFechaPresentacionAC(JDateChooser dchFechaPresentacionAC) {
        this.dchFechaPresentacionAC = dchFechaPresentacionAC;
    }

    public JDateChooser getDchFechaPresentacionAD() {
        return dchFechaPresentacionAD;
    }

    public void setDchFechaPresentacionAD(JDateChooser dchFechaPresentacionAD) {
        this.dchFechaPresentacionAD = dchFechaPresentacionAD;
    }

    public JDateChooser getDchFechaPresentacionP() {
        return dchFechaPresentacionP;
    }

    public void setDchFechaPresentacionP(JDateChooser dchFechaPresentacionP) {
        this.dchFechaPresentacionP = dchFechaPresentacionP;
    }

    public JLabel getLblContenidos() {
        return lblContenidos;
    }

    public void setLblContenidos(JLabel lblContenidos) {
        this.lblContenidos = lblContenidos;
    }

    public JLabel getLblFechaEnvioA() {
        return lblFechaEnvioA;
    }

    public void setLblFechaEnvioA(JLabel lblFechaEnvioA) {
        this.lblFechaEnvioA = lblFechaEnvioA;
    }

    public JLabel getLblFechaEnvioAC() {
        return lblFechaEnvioAC;
    }

    public void setLblFechaEnvioAC(JLabel lblFechaEnvioAC) {
        this.lblFechaEnvioAC = lblFechaEnvioAC;
    }

    public JLabel getLblFechaEnvioAD() {
        return lblFechaEnvioAD;
    }

    public void setLblFechaEnvioAD(JLabel lblFechaEnvioAD) {
        this.lblFechaEnvioAD = lblFechaEnvioAD;
    }

    public JLabel getLblFechaEnvioP() {
        return lblFechaEnvioP;
    }

    public void setLblFechaEnvioP(JLabel lblFechaEnvioP) {
        this.lblFechaEnvioP = lblFechaEnvioP;
    }

    public JLabel getLblFechaFin() {
        return lblFechaFin;
    }

    public void setLblFechaFin(JLabel lblFechaFin) {
        this.lblFechaFin = lblFechaFin;
    }

    public JLabel getLblFechaInicio() {
        return lblFechaInicio;
    }

    public void setLblFechaInicio(JLabel lblFechaInicio) {
        this.lblFechaInicio = lblFechaInicio;
    }

    public JLabel getLblFechaPresentacionA() {
        return lblFechaPresentacionA;
    }

    public void setLblFechaPresentacionA(JLabel lblFechaPresentacionA) {
        this.lblFechaPresentacionA = lblFechaPresentacionA;
    }

    public JLabel getLblFechaPresentacionAC() {
        return lblFechaPresentacionAC;
    }

    public void setLblFechaPresentacionAC(JLabel lblFechaPresentacionAC) {
        this.lblFechaPresentacionAC = lblFechaPresentacionAC;
    }

    public JLabel getLblFechaPresentacionAD() {
        return lblFechaPresentacionAD;
    }

    public void setLblFechaPresentacionAD(JLabel lblFechaPresentacionAD) {
        this.lblFechaPresentacionAD = lblFechaPresentacionAD;
    }

    public JLabel getLblFechaPresentacionP() {
        return lblFechaPresentacionP;
    }

    public void setLblFechaPresentacionP(JLabel lblFechaPresentacionP) {
        this.lblFechaPresentacionP = lblFechaPresentacionP;
    }

    public JLabel getLblIndicadorA() {
        return lblIndicadorA;
    }

    public void setLblIndicadorA(JLabel lblIndicadorA) {
        this.lblIndicadorA = lblIndicadorA;
    }

    public JLabel getLblIndicadorAC() {
        return lblIndicadorAC;
    }

    public void setLblIndicadorAC(JLabel lblIndicadorAC) {
        this.lblIndicadorAC = lblIndicadorAC;
    }

    public JLabel getLblIndicadorAD() {
        return lblIndicadorAD;
    }

    public void setLblIndicadorAD(JLabel lblIndicadorAD) {
        this.lblIndicadorAD = lblIndicadorAD;
    }

    public JLabel getLblIndicadorP() {
        return lblIndicadorP;
    }

    public void setLblIndicadorP(JLabel lblIndicadorP) {
        this.lblIndicadorP = lblIndicadorP;
    }

    public JLabel getLblInstrumentoA() {
        return lblInstrumentoA;
    }

    public void setLblInstrumentoA(JLabel lblInstrumentoA) {
        this.lblInstrumentoA = lblInstrumentoA;
    }

    public JLabel getLblInstrumentoAC() {
        return lblInstrumentoAC;
    }

    public void setLblInstrumentoAC(JLabel lblInstrumentoAC) {
        this.lblInstrumentoAC = lblInstrumentoAC;
    }

    public JLabel getLblInstrumentoAD() {
        return lblInstrumentoAD;
    }

    public void setLblInstrumentoAD(JLabel lblInstrumentoAD) {
        this.lblInstrumentoAD = lblInstrumentoAD;
    }

    public JLabel getLblInstrumentoP() {
        return lblInstrumentoP;
    }

    public void setLblInstrumentoP(JLabel lblInstrumentoP) {
        this.lblInstrumentoP = lblInstrumentoP;
    }

    public JLabel getLblObjetivos() {
        return lblObjetivos;
    }

    public void setLblObjetivos(JLabel lblObjetivos) {
        this.lblObjetivos = lblObjetivos;
    }

    public JLabel getLblResultados() {
        return lblResultados;
    }

    public void setLblResultados(JLabel lblResultados) {
        this.lblResultados = lblResultados;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblUnidad() {
        return lblUnidad;
    }

    public void setLblUnidad(JLabel lblUnidad) {
        this.lblUnidad = lblUnidad;
    }

    public JLabel getLblValoracionA() {
        return lblValoracionA;
    }

    public void setLblValoracionA(JLabel lblValoracionA) {
        this.lblValoracionA = lblValoracionA;
    }

    public JLabel getLblValoracionAC() {
        return lblValoracionAC;
    }

    public void setLblValoracionAC(JLabel lblValoracionAC) {
        this.lblValoracionAC = lblValoracionAC;
    }

    public JLabel getLblValoracionAD() {
        return lblValoracionAD;
    }

    public void setLblValoracionAD(JLabel lblValoracionAD) {
        this.lblValoracionAD = lblValoracionAD;
    }

    public JLabel getLblValoracionP() {
        return lblValoracionP;
    }

    public void setLblValoracionP(JLabel lblValoracionP) {
        this.lblValoracionP = lblValoracionP;
    }

    public JPanel getPnlAprendizajeColaborativo() {
        return pnlAprendizajeColaborativo;
    }

    public void setPnlAprendizajeColaborativo(JPanel pnlAprendizajeColaborativo) {
        this.pnlAprendizajeColaborativo = pnlAprendizajeColaborativo;
    }

    public JPanel getPnlAsistidaDocente() {
        return pnlAsistidaDocente;
    }

    public void setPnlAsistidaDocente(JPanel pnlAsistidaDocente) {
        this.pnlAsistidaDocente = pnlAsistidaDocente;
    }

    public JPanel getPnlAutonoma() {
        return pnlAutonoma;
    }

    public void setPnlAutonoma(JPanel pnlAutonoma) {
        this.pnlAutonoma = pnlAutonoma;
    }

    public JPanel getPnlPractica() {
        return pnlPractica;
    }

    public void setPnlPractica(JPanel pnlPractica) {
        this.pnlPractica = pnlPractica;
    }

    public JPanel getPnlUnidad() {
        return pnlUnidad;
    }

    public void setPnlUnidad(JPanel pnlUnidad) {
        this.pnlUnidad = pnlUnidad;
    }

    public JScrollPane getScrAprendizajeColaboratibo() {
        return scrAprendizajeColaborativo;
    }

    public void setScrAprendizajeColaborativo(JScrollPane scrAprendizajeColaboratibo) {
        this.scrAprendizajeColaborativo = scrAprendizajeColaboratibo;
    }

    public JScrollPane getScrAsistidaDocente() {
        return scrAsistidaDocente;
    }

    public void setScrAsistidaDocente(JScrollPane scrAsistidaDocente) {
        this.scrAsistidaDocente = scrAsistidaDocente;
    }

    public JScrollPane getScrAutonoma() {
        return scrAutonoma;
    }

    public void setScrAutonoma(JScrollPane scrAutonoma) {
        this.scrAutonoma = scrAutonoma;
    }

    public JScrollPane getScrContenidos() {
        return scrContenidos;
    }

    public void setScrContenidos(JScrollPane scrContenidos) {
        this.scrContenidos = scrContenidos;
    }

    public JScrollPane getScrObjetivos() {
        return scrObjetivos;
    }

    public void setScrObjetivos(JScrollPane scrObjetivos) {
        this.scrObjetivos = scrObjetivos;
    }

    public JScrollPane getScrPractica() {
        return scrPractica;
    }

    public void setScrPractica(JScrollPane scrPractica) {
        this.scrPractica = scrPractica;
    }

    public JScrollPane getScrResultados() {
        return scrResultados;
    }

    public void setScrResultados(JScrollPane scrResultados) {
        this.scrResultados = scrResultados;
    }

    public JSpinner getSpnValoracionA() {
        return spnValoracionA;
    }

    public void setSpnValoracionA(JSpinner spnValoracionA) {
        this.spnValoracionA = spnValoracionA;
    }

    public JSpinner getSpnValoracionAC() {
        return spnValoracionAC;
    }

    public void setSpnValoracionAC(JSpinner spnValoracionAC) {
        this.spnValoracionAC = spnValoracionAC;
    }

    public JSpinner getSpnValoracionAD() {
        return spnValoracionAD;
    }

    public void setSpnValoracionAD(JSpinner spnValoracionAD) {
        this.spnValoracionAD = spnValoracionAD;
    }

    public JSpinner getSpnValoracionP() {
        return spnValoracionP;
    }

    public void setSpnValoracionP(JSpinner spnValoracionP) {
        this.spnValoracionP = spnValoracionP;
    }

    public JTable getTblAprendizajeColaborativo() {
        return tblAprendizajeColaborativo;
    }

    public void setTblAprendizajeColaborativo(JTable tblAprendizajeColaborativo) {
        this.tblAprendizajeColaborativo = tblAprendizajeColaborativo;
    }

    public JTable getTblAsistidaDocente() {
        return tblAsistidaDocente;
    }

    public void setTblAsistidaDocente(JTable tblAsistidaDocente) {
        this.tblAsistidaDocente = tblAsistidaDocente;
    }

    public JTable getTblAutonoma() {
        return tblAutonoma;
    }

    public void setTblAutonoma(JTable tblAutonoma) {
        this.tblAutonoma = tblAutonoma;
    }

    public JTable getTblPractica() {
        return tblPractica;
    }

    public void setTblPractica(JTable tblPractica) {
        this.tblPractica = tblPractica;
    }

    public JTabbedPane getTbpEvaluacion() {
        return tbpEvaluacion;
    }

    public void setTbpEvaluacion(JTabbedPane tbpEvaluacion) {
        this.tbpEvaluacion = tbpEvaluacion;
    }

    public JTextArea getTxrContenidos() {
        return txrContenidos;
    }

    public void setTxrContenidos(JTextArea txrContenidos) {
        this.txrContenidos = txrContenidos;
    }

    public JTextArea getTxrObjetivos() {
        return txrObjetivos;
    }

    public void setTxrObjetivos(JTextArea txrObjetivos) {
        this.txrObjetivos = txrObjetivos;
    }

    public JTextArea getTxrResultados() {
        return txrResultados;
    }

    public void setTxrResultados(JTextArea txrResultados) {
        this.txrResultados = txrResultados;
    }

    public JTextField getTxtIndicadorA() {
        return txtIndicadorA;
    }

    public void setTxtIndicadorA(JTextField txtIndicadorA) {
        this.txtIndicadorA = txtIndicadorA;
    }

    public JTextField getTxtIndicadorAC() {
        return txtIndicadorAC;
    }

    public void setTxtIndicadorAC(JTextField txtIndicadorAC) {
        this.txtIndicadorAC = txtIndicadorAC;
    }

    public JTextField getTxtIndicadorAD() {
        return txtIndicadorAD;
    }

    public void setTxtIndicadorAD(JTextField txtIndicadorAD) {
        this.txtIndicadorAD = txtIndicadorAD;
    }

    public JTextField getTxtIndicadorP() {
        return txtIndicadorP;
    }

    public void setTxtIndicadorP(JTextField txtIndicadorP) {
        this.txtIndicadorP = txtIndicadorP;
    }

    public JTextField getTxtInstrumentoA() {
        return txtInstrumentoA;
    }

    public void setTxtInstrumentoA(JTextField txtInstrumentoA) {
        this.txtInstrumentoA = txtInstrumentoA;
    }

    public JTextField getTxtInstrumentoAC() {
        return txtInstrumentoAC;
    }

    public void setTxtInstrumentoAC(JTextField txtInstrumentoAC) {
        this.txtInstrumentoAC = txtInstrumentoAC;
    }

    public JTextField getTxtInstrumentoAD() {
        return txtInstrumentoAD;
    }

    public void setTxtInstrumentoAD(JTextField txtInstrumentoAD) {
        this.txtInstrumentoAD = txtInstrumentoAD;
    }

    public JTextField getTxtInstrumentoP() {
        return txtInstrumentoP;
    }

    public void setTxtInstrumentoP(JTextField txtInstrumentoP) {
        this.txtInstrumentoP = txtInstrumentoP;
    }

    public JTextField getTxtTitulo() {
        return txtTitulo;
    }

    public void setTxtTitulo(JTextField txtTitulo) {
        this.txtTitulo = txtTitulo;
    }

    public JLabel getLblAprendizajeColaborativo() {
        return lblAprendizajeColaborativo;
    }

    public void setLblAprendizajeColaborativo(JLabel lblAprendizajeColaborativo) {
        this.lblAprendizajeColaborativo = lblAprendizajeColaborativo;
    }

    public JLabel getLblAsistidaDocente() {
        return lblAsistidaDocente;
    }

    public void setLblAsistidaDocente(JLabel lblAsistidaDocente) {
        this.lblAsistidaDocente = lblAsistidaDocente;
    }

    public JLabel getLblAutonoma() {
        return lblAutonoma;
    }

    public void setLblAutonoma(JLabel lblAutonoma) {
        this.lblAutonoma = lblAutonoma;
    }

    public JLabel getLblPractica() {
        return lblPractica;
    }

    public void setLblPractica(JLabel lblPractica) {
        this.lblPractica = lblPractica;
    }

    public JLabel getLblGuardarEstrategia() {
        return lblGuardarEstrategia;

    }

    public void setLblGuardarEstrategia(JLabel lblGuardarEstrategia) {
        this.lblGuardarEstrategia = lblGuardarEstrategia;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pnlUnidad = new javax.swing.JPanel();
        cmbUnidad = new javax.swing.JComboBox<>();
        txtTitulo = new javax.swing.JTextField();
        lblUnidad = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        dchFechaInicio = new com.toedter.calendar.JDateChooser();
        dchFechaFin = new com.toedter.calendar.JDateChooser();
        lblFechaFin = new javax.swing.JLabel();
        lblObjetivos = new javax.swing.JLabel();
        lblResultados = new javax.swing.JLabel();
        lblContenidos = new javax.swing.JLabel();
        tbpEvaluacion = new javax.swing.JTabbedPane();
        pnlAsistidaDocente = new javax.swing.JPanel();
        lblIndicadorAD = new javax.swing.JLabel();
        txtIndicadorAD = new javax.swing.JTextField();
        txtInstrumentoAD = new javax.swing.JTextField();
        lblInstrumentoAD = new javax.swing.JLabel();
        spnValoracionAD = new javax.swing.JSpinner();
        lblValoracionAD = new javax.swing.JLabel();
        lblFechaEnvioAD = new javax.swing.JLabel();
        dchFechaEnvioAD = new com.toedter.calendar.JDateChooser();
        lblFechaPresentacionAD = new javax.swing.JLabel();
        dchFechaPresentacionAD = new com.toedter.calendar.JDateChooser();
        scrAsistidaDocente = new javax.swing.JScrollPane();
        tblAsistidaDocente = new javax.swing.JTable();
        btnAgregarAD = new javax.swing.JButton();
        btnQuitarAD = new javax.swing.JButton();
        lblAsistidaDocente = new javax.swing.JLabel();
        pnlAprendizajeColaborativo = new javax.swing.JPanel();
        lblIndicadorAC = new javax.swing.JLabel();
        txtIndicadorAC = new javax.swing.JTextField();
        txtInstrumentoAC = new javax.swing.JTextField();
        lblInstrumentoAC = new javax.swing.JLabel();
        spnValoracionAC = new javax.swing.JSpinner();
        lblValoracionAC = new javax.swing.JLabel();
        lblFechaEnvioAC = new javax.swing.JLabel();
        dchFechaEnvioAC = new com.toedter.calendar.JDateChooser();
        lblFechaPresentacionAC = new javax.swing.JLabel();
        dchFechaPresentacionAC = new com.toedter.calendar.JDateChooser();
        scrAprendizajeColaborativo = new javax.swing.JScrollPane();
        tblAprendizajeColaborativo = new javax.swing.JTable();
        btnAgregarAC = new javax.swing.JButton();
        btnQuitarAC = new javax.swing.JButton();
        lblAprendizajeColaborativo = new javax.swing.JLabel();
        pnlPractica = new javax.swing.JPanel();
        lblIndicadorP = new javax.swing.JLabel();
        txtIndicadorP = new javax.swing.JTextField();
        txtInstrumentoP = new javax.swing.JTextField();
        lblInstrumentoP = new javax.swing.JLabel();
        spnValoracionP = new javax.swing.JSpinner();
        lblValoracionP = new javax.swing.JLabel();
        lblFechaEnvioP = new javax.swing.JLabel();
        dchFechaEnvioP = new com.toedter.calendar.JDateChooser();
        lblFechaPresentacionP = new javax.swing.JLabel();
        dchFechaPresentacionP = new com.toedter.calendar.JDateChooser();
        scrPractica = new javax.swing.JScrollPane();
        tblPractica = new javax.swing.JTable();
        btnAgregarP = new javax.swing.JButton();
        btnQuitarP = new javax.swing.JButton();
        lblPractica = new javax.swing.JLabel();
        pnlAutonoma = new javax.swing.JPanel();
        lblIndicadorA = new javax.swing.JLabel();
        txtIndicadorA = new javax.swing.JTextField();
        txtInstrumentoA = new javax.swing.JTextField();
        lblInstrumentoA = new javax.swing.JLabel();
        spnValoracionA = new javax.swing.JSpinner();
        lblValoracionA = new javax.swing.JLabel();
        lblFechaEnvioA = new javax.swing.JLabel();
        dchFechaEnvioA = new com.toedter.calendar.JDateChooser();
        lblFechaPresentacionA = new javax.swing.JLabel();
        dchFechaPresentacionA = new com.toedter.calendar.JDateChooser();
        scrAutonoma = new javax.swing.JScrollPane();
        tblAutonoma = new javax.swing.JTable();
        btnAgregarA = new javax.swing.JButton();
        btnQuitarA = new javax.swing.JButton();
        lblAutonoma = new javax.swing.JLabel();
        scrResultados = new javax.swing.JScrollPane();
        txrResultados = new javax.swing.JTextArea();
        scrObjetivos = new javax.swing.JScrollPane();
        txrObjetivos = new javax.swing.JTextArea();
        scrContenidos = new javax.swing.JScrollPane();
        txrContenidos = new javax.swing.JTextArea();
        btnCancelar = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        spnHorasPracticas = new javax.swing.JSpinner();
        lblHorasPracticas = new javax.swing.JLabel();
        lblHorasDocencia = new javax.swing.JLabel();
        lblHorasAutonomas = new javax.swing.JLabel();
        spnHorasAutonomas = new javax.swing.JSpinner();
        spnHorasDocencia = new javax.swing.JSpinner();
        scrEstrategiasPredeterminadas = new javax.swing.JScrollPane();
        lstEstrategiasPredeterminadas = new javax.swing.JList();
        lblNuevaEstrategia = new javax.swing.JLabel();
        lblEstrategiasPredeterminadas = new javax.swing.JLabel();
        lblEliminarUnidad = new javax.swing.JLabel();
        lblGuardarEstrategia = new javax.swing.JLabel();
        lblAgregarUnidad = new javax.swing.JLabel();
        lblAgregarEstrategia = new javax.swing.JLabel();
        txtNuevaEstrategia = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        lblTotalGestion = new javax.swing.JLabel();
        lblAcumuladoGestion = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });

        lblUnidad.setText("Seleccione la Unidad:");

        lblTitulo.setText("Titulo de la Unidad:");

        lblFechaInicio.setText("Fecha de Inicio de Unidad:");

        lblFechaFin.setText("Fecha de Fin de Unidad:");

        lblObjetivos.setText("Objetivos Específicos de la Unidad:");

        lblResultados.setText("Resultados de Aprendizaje:");

        lblContenidos.setText("Contenidos de la Unidad:");

        lblIndicadorAD.setText("Indicador:");

        lblInstrumentoAD.setText("Instrumento:");

        spnValoracionAD.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 60, 0.5d));

        lblValoracionAD.setText("Valoración");

        lblFechaEnvioAD.setText("Fecha Envío:");

        lblFechaPresentacionAD.setText("Fecha Presentación:");

        tblAsistidaDocente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indicador", "Instrumento", "Valoración", "Fecha  Envío", "Fecha  Presentación", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAsistidaDocente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        scrAsistidaDocente.setViewportView(tblAsistidaDocente);
        if (tblAsistidaDocente.getColumnModel().getColumnCount() > 0) {
            tblAsistidaDocente.getColumnModel().getColumn(0).setMinWidth(270);
            tblAsistidaDocente.getColumnModel().getColumn(0).setPreferredWidth(270);
            tblAsistidaDocente.getColumnModel().getColumn(0).setMaxWidth(270);
            tblAsistidaDocente.getColumnModel().getColumn(1).setMinWidth(250);
            tblAsistidaDocente.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblAsistidaDocente.getColumnModel().getColumn(1).setMaxWidth(250);
            tblAsistidaDocente.getColumnModel().getColumn(2).setMinWidth(90);
            tblAsistidaDocente.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblAsistidaDocente.getColumnModel().getColumn(2).setMaxWidth(90);
            tblAsistidaDocente.getColumnModel().getColumn(3).setMinWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(3).setMaxWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(4).setMinWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(4).setMaxWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(5).setMinWidth(0);
            tblAsistidaDocente.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblAsistidaDocente.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        btnAgregarAD.setText("Agregar");

        btnQuitarAD.setText("Quitar");
        btnQuitarAD.setEnabled(false);

        lblAsistidaDocente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAsistidaDocente.setText("Asistida Docente");

        javax.swing.GroupLayout pnlAsistidaDocenteLayout = new javax.swing.GroupLayout(pnlAsistidaDocente);
        pnlAsistidaDocente.setLayout(pnlAsistidaDocenteLayout);
        pnlAsistidaDocenteLayout.setHorizontalGroup(
            pnlAsistidaDocenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAsistidaDocenteLayout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addComponent(lblIndicadorAD)
                .addGap(223, 223, 223)
                .addComponent(lblInstrumentoAD)
                .addGap(209, 209, 209)
                .addComponent(lblValoracionAD)
                .addGap(43, 43, 43)
                .addComponent(lblFechaEnvioAD)
                .addGap(86, 86, 86)
                .addComponent(lblFechaPresentacionAD))
            .addGroup(pnlAsistidaDocenteLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblAsistidaDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(txtIndicadorAD, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtInstrumentoAD, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(spnValoracionAD, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(dchFechaEnvioAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(dchFechaPresentacionAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlAsistidaDocenteLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addGroup(pnlAsistidaDocenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarAD)
                    .addComponent(btnQuitarAD, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(scrAsistidaDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlAsistidaDocenteLayout.setVerticalGroup(
            pnlAsistidaDocenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAsistidaDocenteLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlAsistidaDocenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIndicadorAD)
                    .addComponent(lblInstrumentoAD)
                    .addComponent(lblValoracionAD)
                    .addComponent(lblFechaEnvioAD)
                    .addComponent(lblFechaPresentacionAD))
                .addGap(8, 8, 8)
                .addGroup(pnlAsistidaDocenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAsistidaDocenteLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblAsistidaDocente))
                    .addGroup(pnlAsistidaDocenteLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtIndicadorAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAsistidaDocenteLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtInstrumentoAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spnValoracionAD, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaEnvioAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaPresentacionAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlAsistidaDocenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAsistidaDocenteLayout.createSequentialGroup()
                        .addComponent(btnAgregarAD)
                        .addGap(15, 15, 15)
                        .addComponent(btnQuitarAD))
                    .addComponent(scrAsistidaDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tbpEvaluacion.addTab("Gestión Docente - Asistida Docente", pnlAsistidaDocente);

        lblIndicadorAC.setText("Indicador:");

        lblInstrumentoAC.setText("Instrumento:");

        spnValoracionAC.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 60.0d, 0.5d));

        lblValoracionAC.setText("Valoración");

        lblFechaEnvioAC.setText("Fecha Envío:");

        lblFechaPresentacionAC.setText("Fecha Presentación:");

        tblAprendizajeColaborativo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indicador", "Instrumento", "Valoración", "Fecha  Envío", "Fecha  Presentación", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAprendizajeColaborativo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        scrAprendizajeColaborativo.setViewportView(tblAprendizajeColaborativo);
        if (tblAprendizajeColaborativo.getColumnModel().getColumnCount() > 0) {
            tblAprendizajeColaborativo.getColumnModel().getColumn(0).setMinWidth(270);
            tblAprendizajeColaborativo.getColumnModel().getColumn(0).setPreferredWidth(270);
            tblAprendizajeColaborativo.getColumnModel().getColumn(0).setMaxWidth(270);
            tblAprendizajeColaborativo.getColumnModel().getColumn(1).setMinWidth(250);
            tblAprendizajeColaborativo.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblAprendizajeColaborativo.getColumnModel().getColumn(1).setMaxWidth(250);
            tblAprendizajeColaborativo.getColumnModel().getColumn(2).setMinWidth(90);
            tblAprendizajeColaborativo.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblAprendizajeColaborativo.getColumnModel().getColumn(2).setMaxWidth(90);
            tblAprendizajeColaborativo.getColumnModel().getColumn(3).setMinWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(3).setMaxWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(4).setMinWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(4).setMaxWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(5).setMinWidth(0);
            tblAprendizajeColaborativo.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblAprendizajeColaborativo.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        btnAgregarAC.setText("Agregar");

        btnQuitarAC.setText("Quitar");
        btnQuitarAC.setEnabled(false);

        lblAprendizajeColaborativo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAprendizajeColaborativo.setText("Aprendizaje Colaborativo");

        javax.swing.GroupLayout pnlAprendizajeColaborativoLayout = new javax.swing.GroupLayout(pnlAprendizajeColaborativo);
        pnlAprendizajeColaborativo.setLayout(pnlAprendizajeColaborativoLayout);
        pnlAprendizajeColaborativoLayout.setHorizontalGroup(
            pnlAprendizajeColaborativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAprendizajeColaborativoLayout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addComponent(lblIndicadorAC)
                .addGap(223, 223, 223)
                .addComponent(lblInstrumentoAC)
                .addGap(209, 209, 209)
                .addComponent(lblValoracionAC)
                .addGap(43, 43, 43)
                .addComponent(lblFechaEnvioAC)
                .addGap(86, 86, 86)
                .addComponent(lblFechaPresentacionAC))
            .addGroup(pnlAprendizajeColaborativoLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(lblAprendizajeColaborativo)
                .addGap(124, 124, 124)
                .addComponent(txtIndicadorAC, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtInstrumentoAC, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(spnValoracionAC, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(dchFechaEnvioAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(dchFechaPresentacionAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlAprendizajeColaborativoLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addGroup(pnlAprendizajeColaborativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarAC)
                    .addComponent(btnQuitarAC, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(scrAprendizajeColaborativo, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlAprendizajeColaborativoLayout.setVerticalGroup(
            pnlAprendizajeColaborativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAprendizajeColaborativoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlAprendizajeColaborativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIndicadorAC)
                    .addComponent(lblInstrumentoAC)
                    .addComponent(lblValoracionAC)
                    .addComponent(lblFechaEnvioAC)
                    .addComponent(lblFechaPresentacionAC))
                .addGap(8, 8, 8)
                .addGroup(pnlAprendizajeColaborativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAprendizajeColaborativoLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblAprendizajeColaborativo))
                    .addGroup(pnlAprendizajeColaborativoLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtIndicadorAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAprendizajeColaborativoLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtInstrumentoAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spnValoracionAC, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaEnvioAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaPresentacionAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlAprendizajeColaborativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAprendizajeColaborativoLayout.createSequentialGroup()
                        .addComponent(btnAgregarAC)
                        .addGap(15, 15, 15)
                        .addComponent(btnQuitarAC))
                    .addComponent(scrAprendizajeColaborativo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tbpEvaluacion.addTab("Gestión Docente - Aprendizaje Colaborativo", pnlAprendizajeColaborativo);

        lblIndicadorP.setText("Indicador:");

        lblInstrumentoP.setText("Instrumento:");

        spnValoracionP.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 60.0d, 0.5d));
        spnValoracionP.setToolTipText("");

        lblValoracionP.setText("Valoración");

        lblFechaEnvioP.setText("Fecha Envío:");

        lblFechaPresentacionP.setText("Fecha Presentación:");

        tblPractica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indicador", "Instrumento", "Valoración", "Fecha  Envío", "Fecha  Presentación", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPractica.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        scrPractica.setViewportView(tblPractica);
        if (tblPractica.getColumnModel().getColumnCount() > 0) {
            tblPractica.getColumnModel().getColumn(0).setMinWidth(270);
            tblPractica.getColumnModel().getColumn(0).setPreferredWidth(270);
            tblPractica.getColumnModel().getColumn(0).setMaxWidth(270);
            tblPractica.getColumnModel().getColumn(1).setMinWidth(250);
            tblPractica.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblPractica.getColumnModel().getColumn(1).setMaxWidth(250);
            tblPractica.getColumnModel().getColumn(2).setMinWidth(90);
            tblPractica.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblPractica.getColumnModel().getColumn(2).setMaxWidth(90);
            tblPractica.getColumnModel().getColumn(3).setMinWidth(150);
            tblPractica.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblPractica.getColumnModel().getColumn(3).setMaxWidth(150);
            tblPractica.getColumnModel().getColumn(4).setMinWidth(150);
            tblPractica.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblPractica.getColumnModel().getColumn(4).setMaxWidth(150);
            tblPractica.getColumnModel().getColumn(5).setMinWidth(0);
            tblPractica.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblPractica.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        btnAgregarP.setText("Agregar");

        btnQuitarP.setText("Quitar");
        btnQuitarP.setEnabled(false);

        lblPractica.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPractica.setText("Gestión Práctica");

        javax.swing.GroupLayout pnlPracticaLayout = new javax.swing.GroupLayout(pnlPractica);
        pnlPractica.setLayout(pnlPracticaLayout);
        pnlPracticaLayout.setHorizontalGroup(
            pnlPracticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPracticaLayout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addComponent(lblIndicadorP)
                .addGap(223, 223, 223)
                .addComponent(lblInstrumentoP)
                .addGap(209, 209, 209)
                .addComponent(lblValoracionP)
                .addGap(43, 43, 43)
                .addComponent(lblFechaEnvioP)
                .addGap(86, 86, 86)
                .addComponent(lblFechaPresentacionP))
            .addGroup(pnlPracticaLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblPractica, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(txtIndicadorP, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtInstrumentoP, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(spnValoracionP, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(dchFechaEnvioP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(dchFechaPresentacionP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlPracticaLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addGroup(pnlPracticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarP)
                    .addComponent(btnQuitarP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(scrPractica, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlPracticaLayout.setVerticalGroup(
            pnlPracticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPracticaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlPracticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIndicadorP)
                    .addComponent(lblInstrumentoP)
                    .addComponent(lblValoracionP)
                    .addComponent(lblFechaEnvioP)
                    .addComponent(lblFechaPresentacionP))
                .addGap(8, 8, 8)
                .addGroup(pnlPracticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPracticaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblPractica))
                    .addGroup(pnlPracticaLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtIndicadorP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPracticaLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtInstrumentoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spnValoracionP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaEnvioP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaPresentacionP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlPracticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPracticaLayout.createSequentialGroup()
                        .addComponent(btnAgregarP)
                        .addGap(15, 15, 15)
                        .addComponent(btnQuitarP))
                    .addComponent(scrPractica, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tbpEvaluacion.addTab("Gestión Práctica", pnlPractica);

        lblIndicadorA.setText("Indicador:");

        lblInstrumentoA.setText("Instrumento:");

        spnValoracionA.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 60.0d, 0.5d));

        lblValoracionA.setText("Valoración");

        lblFechaEnvioA.setText("Fecha Envío:");

        lblFechaPresentacionA.setText("Fecha Presentación:");

        tblAutonoma.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indicador", "Instrumento", "Valoración", "Fecha  Envío", "Fecha  Presentación", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAutonoma.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        scrAutonoma.setViewportView(tblAutonoma);
        if (tblAutonoma.getColumnModel().getColumnCount() > 0) {
            tblAutonoma.getColumnModel().getColumn(0).setMinWidth(270);
            tblAutonoma.getColumnModel().getColumn(0).setPreferredWidth(270);
            tblAutonoma.getColumnModel().getColumn(0).setMaxWidth(270);
            tblAutonoma.getColumnModel().getColumn(1).setMinWidth(250);
            tblAutonoma.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblAutonoma.getColumnModel().getColumn(1).setMaxWidth(250);
            tblAutonoma.getColumnModel().getColumn(2).setMinWidth(90);
            tblAutonoma.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblAutonoma.getColumnModel().getColumn(2).setMaxWidth(90);
            tblAutonoma.getColumnModel().getColumn(3).setMinWidth(150);
            tblAutonoma.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblAutonoma.getColumnModel().getColumn(3).setMaxWidth(150);
            tblAutonoma.getColumnModel().getColumn(4).setMinWidth(150);
            tblAutonoma.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblAutonoma.getColumnModel().getColumn(4).setMaxWidth(150);
            tblAutonoma.getColumnModel().getColumn(5).setMinWidth(0);
            tblAutonoma.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblAutonoma.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        btnAgregarA.setText("Agregar");

        btnQuitarA.setText("Quitar");
        btnQuitarA.setEnabled(false);

        lblAutonoma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAutonoma.setText("Gestión Autónoma");

        javax.swing.GroupLayout pnlAutonomaLayout = new javax.swing.GroupLayout(pnlAutonoma);
        pnlAutonoma.setLayout(pnlAutonomaLayout);
        pnlAutonomaLayout.setHorizontalGroup(
            pnlAutonomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAutonomaLayout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addComponent(lblIndicadorA)
                .addGap(223, 223, 223)
                .addComponent(lblInstrumentoA)
                .addGap(209, 209, 209)
                .addComponent(lblValoracionA)
                .addGap(43, 43, 43)
                .addComponent(lblFechaEnvioA)
                .addGap(86, 86, 86)
                .addComponent(lblFechaPresentacionA))
            .addGroup(pnlAutonomaLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblAutonoma, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(txtIndicadorA, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtInstrumentoA, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(spnValoracionA, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(dchFechaEnvioA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(dchFechaPresentacionA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlAutonomaLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addGroup(pnlAutonomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarA)
                    .addComponent(btnQuitarA, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(scrAutonoma, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlAutonomaLayout.setVerticalGroup(
            pnlAutonomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAutonomaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlAutonomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIndicadorA)
                    .addComponent(lblInstrumentoA)
                    .addComponent(lblValoracionA)
                    .addComponent(lblFechaEnvioA)
                    .addComponent(lblFechaPresentacionA))
                .addGap(8, 8, 8)
                .addGroup(pnlAutonomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAutonomaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblAutonoma))
                    .addGroup(pnlAutonomaLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtIndicadorA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAutonomaLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtInstrumentoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spnValoracionA, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaEnvioA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaPresentacionA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlAutonomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAutonomaLayout.createSequentialGroup()
                        .addComponent(btnAgregarA)
                        .addGap(15, 15, 15)
                        .addComponent(btnQuitarA))
                    .addComponent(scrAutonoma, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tbpEvaluacion.addTab("Gestión Autónoma", pnlAutonoma);

        txrResultados.setColumns(20);
        txrResultados.setLineWrap(true);
        txrResultados.setRows(5);
        txrResultados.setWrapStyleWord(true);
        scrResultados.setViewportView(txrResultados);

        txrObjetivos.setColumns(20);
        txrObjetivos.setLineWrap(true);
        txrObjetivos.setRows(5);
        txrObjetivos.setWrapStyleWord(true);
        scrObjetivos.setViewportView(txrObjetivos);

        txrContenidos.setColumns(20);
        txrContenidos.setLineWrap(true);
        txrContenidos.setRows(5);
        txrContenidos.setWrapStyleWord(true);
        scrContenidos.setViewportView(txrContenidos);

        btnCancelar.setText("Cancelar");

        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        spnHorasPracticas.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblHorasPracticas.setText("Horas Prácticas:");

        lblHorasDocencia.setText("Horas Docencia:");

        lblHorasAutonomas.setText("Horas Autónomas:");

        spnHorasAutonomas.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        spnHorasDocencia.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        scrEstrategiasPredeterminadas.setViewportView(lstEstrategiasPredeterminadas);

        lblNuevaEstrategia.setText("Nueva Estrategia de Enseñanza:");

        lblEstrategiasPredeterminadas.setText("Seleccione la(s) Estrategias de Enseñanza:");

        lblEliminarUnidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEliminarUnidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_eliminar_unidad.png"))); // NOI18N
        lblEliminarUnidad.setToolTipText("Eliminar Unidad");

        lblGuardarEstrategia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_guardar_estrategia.png"))); // NOI18N
        lblGuardarEstrategia.setToolTipText("Guardar Nueva Estrategia");
        lblGuardarEstrategia.setEnabled(false);

        lblAgregarUnidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_agregar.png"))); // NOI18N
        lblAgregarUnidad.setToolTipText("Agregar Unidad");

        lblAgregarEstrategia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_agregar.png"))); // NOI18N
        lblAgregarEstrategia.setToolTipText("Agregar Nueva Estrategia");

        txtNuevaEstrategia.setEnabled(false);
        txtNuevaEstrategia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevaEstrategiaActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);

        lblTotalGestion.setText("Total de Gestion de  Aula:");

        lblAcumuladoGestion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAcumuladoGestion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAcumuladoGestion.setText("0/60");

        javax.swing.GroupLayout pnlUnidadLayout = new javax.swing.GroupLayout(pnlUnidad);
        pnlUnidad.setLayout(pnlUnidadLayout);
        pnlUnidadLayout.setHorizontalGroup(
            pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUnidad)
                    .addComponent(cmbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAgregarUnidad)
                            .addComponent(lblEliminarUnidad))))
                .addGap(54, 54, 54)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaInicio)
                    .addComponent(lblFechaFin))
                .addGap(51, 51, 51)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dchFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHorasDocencia)
                    .addComponent(lblHorasPracticas))
                .addGap(52, 52, 52)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnHorasDocencia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnHorasPracticas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addGap(1110, 1110, 1110)
                .addComponent(lblHorasAutonomas)
                .addGap(41, 41, 41)
                .addComponent(spnHorasAutonomas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblObjetivos)
                .addGap(273, 273, 273)
                .addComponent(lblEstrategiasPredeterminadas, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblAgregarEstrategia))
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrObjetivos, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContenidos)
                    .addComponent(scrContenidos, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(scrEstrategiasPredeterminadas, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(lblNuevaEstrategia)
                        .addGap(253, 253, 253)
                        .addComponent(lblGuardarEstrategia))
                    .addComponent(txtNuevaEstrategia, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblResultados)
                    .addComponent(scrResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addGap(1070, 1070, 1070)
                .addComponent(lblTotalGestion)
                .addGap(46, 46, 46)
                .addComponent(lblAcumuladoGestion, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(tbpEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addGap(1050, 1050, 1050)
                .addComponent(btnGuardar)
                .addGap(19, 19, 19)
                .addComponent(btnSiguiente)
                .addGap(13, 13, 13)
                .addComponent(btnCancelar))
        );
        pnlUnidadLayout.setVerticalGroup(
            pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUnidad)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(cmbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblAgregarUnidad)))
                        .addComponent(lblEliminarUnidad))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(7, 7, 7)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(lblFechaInicio)
                        .addGap(27, 27, 27)
                        .addComponent(lblFechaFin))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(dchFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(dchFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(lblHorasDocencia)
                        .addGap(16, 16, 16)
                        .addComponent(lblHorasPracticas))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(spnHorasDocencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(spnHorasPracticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHorasAutonomas)
                    .addComponent(spnHorasAutonomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblObjetivos)
                    .addComponent(lblEstrategiasPredeterminadas)
                    .addComponent(lblAgregarEstrategia))
                .addGap(4, 4, 4)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(scrObjetivos, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblContenidos)
                        .addGap(6, 6, 6)
                        .addComponent(scrContenidos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrEstrategiasPredeterminadas, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevaEstrategia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblGuardarEstrategia)))
                        .addGap(4, 4, 4)
                        .addComponent(txtNuevaEstrategia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(lblResultados)
                        .addGap(16, 16, 16)
                        .addComponent(scrResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalGestion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAcumuladoGestion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(tbpEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar)
                    .addComponent(btnSiguiente)
                    .addComponent(btnCancelar)))
        );

        jScrollPane1.setViewportView(pnlUnidad);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1334, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void txtNuevaEstrategiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevaEstrategiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevaEstrategiaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarA;
    private javax.swing.JButton btnAgregarAC;
    private javax.swing.JButton btnAgregarAD;
    private javax.swing.JButton btnAgregarP;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitarA;
    private javax.swing.JButton btnQuitarAC;
    private javax.swing.JButton btnQuitarAD;
    private javax.swing.JButton btnQuitarP;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JComboBox<String> cmbUnidad;
    private com.toedter.calendar.JDateChooser dchFechaEnvioA;
    private com.toedter.calendar.JDateChooser dchFechaEnvioAC;
    private com.toedter.calendar.JDateChooser dchFechaEnvioAD;
    private com.toedter.calendar.JDateChooser dchFechaEnvioP;
    private com.toedter.calendar.JDateChooser dchFechaFin;
    private com.toedter.calendar.JDateChooser dchFechaInicio;
    private com.toedter.calendar.JDateChooser dchFechaPresentacionA;
    private com.toedter.calendar.JDateChooser dchFechaPresentacionAC;
    private com.toedter.calendar.JDateChooser dchFechaPresentacionAD;
    private com.toedter.calendar.JDateChooser dchFechaPresentacionP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAcumuladoGestion;
    private javax.swing.JLabel lblAgregarEstrategia;
    private javax.swing.JLabel lblAgregarUnidad;
    private javax.swing.JLabel lblAprendizajeColaborativo;
    private javax.swing.JLabel lblAsistidaDocente;
    private javax.swing.JLabel lblAutonoma;
    private javax.swing.JLabel lblContenidos;
    private javax.swing.JLabel lblEliminarUnidad;
    private javax.swing.JLabel lblEstrategiasPredeterminadas;
    private javax.swing.JLabel lblFechaEnvioA;
    private javax.swing.JLabel lblFechaEnvioAC;
    private javax.swing.JLabel lblFechaEnvioAD;
    private javax.swing.JLabel lblFechaEnvioP;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblFechaPresentacionA;
    private javax.swing.JLabel lblFechaPresentacionAC;
    private javax.swing.JLabel lblFechaPresentacionAD;
    private javax.swing.JLabel lblFechaPresentacionP;
    private javax.swing.JLabel lblGuardarEstrategia;
    private javax.swing.JLabel lblHorasAutonomas;
    private javax.swing.JLabel lblHorasDocencia;
    private javax.swing.JLabel lblHorasPracticas;
    private javax.swing.JLabel lblIndicadorA;
    private javax.swing.JLabel lblIndicadorAC;
    private javax.swing.JLabel lblIndicadorAD;
    private javax.swing.JLabel lblIndicadorP;
    private javax.swing.JLabel lblInstrumentoA;
    private javax.swing.JLabel lblInstrumentoAC;
    private javax.swing.JLabel lblInstrumentoAD;
    private javax.swing.JLabel lblInstrumentoP;
    private javax.swing.JLabel lblNuevaEstrategia;
    private javax.swing.JLabel lblObjetivos;
    private javax.swing.JLabel lblPractica;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalGestion;
    private javax.swing.JLabel lblUnidad;
    private javax.swing.JLabel lblValoracionA;
    private javax.swing.JLabel lblValoracionAC;
    private javax.swing.JLabel lblValoracionAD;
    private javax.swing.JLabel lblValoracionP;
    private javax.swing.JList lstEstrategiasPredeterminadas;
    private javax.swing.JPanel pnlAprendizajeColaborativo;
    private javax.swing.JPanel pnlAsistidaDocente;
    private javax.swing.JPanel pnlAutonoma;
    private javax.swing.JPanel pnlPractica;
    private javax.swing.JPanel pnlUnidad;
    private javax.swing.JScrollPane scrAprendizajeColaborativo;
    private javax.swing.JScrollPane scrAsistidaDocente;
    private javax.swing.JScrollPane scrAutonoma;
    private javax.swing.JScrollPane scrContenidos;
    private javax.swing.JScrollPane scrEstrategiasPredeterminadas;
    private javax.swing.JScrollPane scrObjetivos;
    private javax.swing.JScrollPane scrPractica;
    private javax.swing.JScrollPane scrResultados;
    private javax.swing.JSpinner spnHorasAutonomas;
    private javax.swing.JSpinner spnHorasDocencia;
    private javax.swing.JSpinner spnHorasPracticas;
    private javax.swing.JSpinner spnValoracionA;
    private javax.swing.JSpinner spnValoracionAC;
    private javax.swing.JSpinner spnValoracionAD;
    private javax.swing.JSpinner spnValoracionP;
    private javax.swing.JTable tblAprendizajeColaborativo;
    private javax.swing.JTable tblAsistidaDocente;
    private javax.swing.JTable tblAutonoma;
    private javax.swing.JTable tblPractica;
    private javax.swing.JTabbedPane tbpEvaluacion;
    private javax.swing.JTextArea txrContenidos;
    private javax.swing.JTextArea txrObjetivos;
    private javax.swing.JTextArea txrResultados;
    private javax.swing.JTextField txtIndicadorA;
    private javax.swing.JTextField txtIndicadorAC;
    private javax.swing.JTextField txtIndicadorAD;
    private javax.swing.JTextField txtIndicadorP;
    private javax.swing.JTextField txtInstrumentoA;
    private javax.swing.JTextField txtInstrumentoAC;
    private javax.swing.JTextField txtInstrumentoAD;
    private javax.swing.JTextField txtInstrumentoP;
    private javax.swing.JTextField txtNuevaEstrategia;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
      public static class CheckListItem {

        private String label;
        private boolean isSelected = false;

        public CheckListItem(String label) {
            this.label = label;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public String toString() {
            return label;
        }
    }

    public static class CheckListRenderer extends JCheckBox implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((CheckListItem) value).isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }

    public static class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            setComponentOrientation(list.getComponentOrientation());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setSelected(isSelected);
            setEnabled(list.isEnabled());

            setText(value == null ? "" : value.toString());

            return this;
        }
    }

}
