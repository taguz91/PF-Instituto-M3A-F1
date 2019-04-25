package vista.carrera;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Johnny
 */
public class FrmCarrera extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmCarrera
     */
    public FrmCarrera() {
        initComponents();
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTable getTblDocentes() {
        return tblDocentes;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JComboBox<String> getCmbModalidad() {
        return cmbModalidad;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JLabel getLblErrorCodigo() {
        return lblErrorCodigo;
    }
    
    public JLabel getLblErrorNombre() {
        return lblErrorNombre;
    }

    public JDateChooser getJdFechaInicio() {
        return jdFechaInicio;
    }

    public JButton getBtnGuardarContinuar() {
        return btnGuardarContinuar;
    }

    public JTextField getTxtSemanas() {
        return txtSemanas;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblErrorCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        cmbModalidad = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblErrorNombre = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocentes = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jdFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtSemanas = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnGuardarContinuar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Formulario Carrera");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Coordinador:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, 20));

        lblErrorCodigo.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorCodigo.setText("Solo debe ingresar letras.");
        jPanel1.add(lblErrorCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 260, -1));
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 90, 25));

        jPanel1.add(cmbModalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 150, 25));

        jLabel5.setText("Modalidad:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, -1, -1));

        jLabel1.setText("Código:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jLabel3.setText("Fecha inicio:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        lblErrorNombre.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorNombre.setText("Solo debe ingresar letras.");
        jPanel1.add(lblErrorNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 268, -1));

        jLabel7.setText("Nombre:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 31, 430, 25));

        tblDocentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane1.setViewportView(tblDocentes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 430, 90));
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 310, 25));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Search_15px.png"))); // NOI18N
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 30, -1));
        jPanel1.add(jdFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 110, 25));

        jLabel2.setText("Semanas:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, 70, -1));

        txtSemanas.setToolTipText("Numero de semanas que tendra la carrera");
        jPanel1.add(txtSemanas, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 50, 25));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 290));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setText("Guardar");
        jPanel2.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 100, -1));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 460, 10));

        btnGuardarContinuar.setText("Guardar y continuar");
        jPanel2.add(btnGuardarContinuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 460, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarContinuar;
    private javax.swing.JComboBox<String> cmbModalidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JDateChooser jdFechaInicio;
    private javax.swing.JLabel lblErrorCodigo;
    private javax.swing.JLabel lblErrorNombre;
    private javax.swing.JTable tblDocentes;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSemanas;
    // End of variables declaration//GEN-END:variables
}
