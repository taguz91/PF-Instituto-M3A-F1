package vista.alumno;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Johnny
 */
public class JDMateriasCursadas extends javax.swing.JDialog {

    /**
     * Creates new form JDMateriasCursadas
     * @param parent
     * @param modal
     */
    public JDMateriasCursadas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JLabel getLblAlumno() {
        return lblAlumno;
    }

    public JLabel getLblCarrera() {
        return lblCarrera;
    }

    public JTable getTblMaterias() {
        return TblMaterias;
    }

    public JLabel getLblNota1() {
        return lblNota1;
    }

    public JLabel getLblNota2() {
        return lblNota2;
    }

    public JLabel getLblNota3() {
        return lblNota3;
    }

    public JPanel getPnlInfo() {
        return pnlInfo;
    }

    public JLabel getLblCiclo() {
        return lblCiclo;
    }

    public JLabel getLblNumMatricula() {
        return lblNumMatricula;
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
        lblAlumno = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblMaterias = new javax.swing.JTable();
        pnlInfo = new javax.swing.JPanel();
        lblAlumno8 = new javax.swing.JLabel();
        lblNota1 = new javax.swing.JLabel();
        lblAlumno10 = new javax.swing.JLabel();
        lblNota2 = new javax.swing.JLabel();
        lblAlumno6 = new javax.swing.JLabel();
        lblAlumno13 = new javax.swing.JLabel();
        lblNota3 = new javax.swing.JLabel();
        lblNumMatricula = new javax.swing.JLabel();
        lblCiclo = new javax.swing.JLabel();
        lblAlumno4 = new javax.swing.JLabel();
        lblAlumno2 = new javax.swing.JLabel();
        lblCarrera = new javax.swing.JLabel();
        lblAlumno1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(49, 79, 117));

        lblAlumno.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblAlumno.setForeground(new java.awt.Color(255, 255, 255));
        lblAlumno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAlumno.setText("Alumno");
        lblAlumno.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        TblMaterias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TblMaterias);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lblAlumno8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAlumno8.setForeground(new java.awt.Color(49, 79, 117));
        lblAlumno8.setText("Nota 1");
        lblAlumno8.setToolTipText("");

        lblNota1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNota1.setToolTipText("");

        lblAlumno10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAlumno10.setForeground(new java.awt.Color(49, 79, 117));
        lblAlumno10.setText("Nota 2:");
        lblAlumno10.setToolTipText("");

        lblNota2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNota2.setToolTipText("");

        lblAlumno6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAlumno6.setForeground(new java.awt.Color(49, 79, 117));
        lblAlumno6.setText("Numero Matricula:");
        lblAlumno6.setToolTipText("");

        lblAlumno13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAlumno13.setForeground(new java.awt.Color(49, 79, 117));
        lblAlumno13.setText("Nota 3:");
        lblAlumno13.setToolTipText("");

        lblNota3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNota3.setToolTipText("");

        lblNumMatricula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNumMatricula.setToolTipText("");

        lblCiclo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCiclo.setToolTipText("");

        lblAlumno4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAlumno4.setForeground(new java.awt.Color(49, 79, 117));
        lblAlumno4.setText("Ciclo:");
        lblAlumno4.setToolTipText("");

        lblAlumno2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAlumno2.setForeground(new java.awt.Color(49, 79, 117));
        lblAlumno2.setText("Carrera:");
        lblAlumno2.setToolTipText("");

        lblCarrera.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCarrera.setToolTipText("");

        lblAlumno1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAlumno1.setForeground(new java.awt.Color(49, 79, 117));
        lblAlumno1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAlumno1.setText("Informacion");
        lblAlumno1.setToolTipText("");

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(lblAlumno2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(lblAlumno4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAlumno6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(lblAlumno8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNota1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAlumno10, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNota2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAlumno13, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNota3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblAlumno1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addComponent(lblAlumno1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblAlumno2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAlumno4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAlumno6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCiclo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAlumno13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAlumno10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAlumno8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNota3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNota2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNota1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblMaterias;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAlumno;
    private javax.swing.JLabel lblAlumno1;
    private javax.swing.JLabel lblAlumno10;
    private javax.swing.JLabel lblAlumno13;
    private javax.swing.JLabel lblAlumno2;
    private javax.swing.JLabel lblAlumno4;
    private javax.swing.JLabel lblAlumno6;
    private javax.swing.JLabel lblAlumno8;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblCiclo;
    private javax.swing.JLabel lblNota1;
    private javax.swing.JLabel lblNota2;
    private javax.swing.JLabel lblNota3;
    private javax.swing.JLabel lblNumMatricula;
    private javax.swing.JPanel pnlInfo;
    // End of variables declaration//GEN-END:variables
}
