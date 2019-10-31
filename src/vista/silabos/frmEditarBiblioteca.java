
package vista.silabos;

import javax.swing.JButton;
import javax.swing.JTextField;


public class frmEditarBiblioteca extends javax.swing.JInternalFrame {

    public JButton getCancelarBB() {
        return CancelarBB;
    }

    public void setCancelarBB(JButton CancelarBB) {
        this.CancelarBB = CancelarBB;
    }

    public JButton getGuargarBB() {
        return GuargarBB;
    }

    public void setGuargarBB(JButton GuargarBB) {
        this.GuargarBB = GuargarBB;
    }

    public JTextField getTxtEditableReferenciasBiblioteca() {
        return TxtEditableReferenciasBiblioteca;
    }

    public void setTxtEditableReferenciasBiblioteca(JTextField TxtEditableReferenciasBiblioteca) {
        this.TxtEditableReferenciasBiblioteca = TxtEditableReferenciasBiblioteca;
    }

   
    public frmEditarBiblioteca() {
        initComponents();
    }
   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        TxtEditableReferenciasBiblioteca = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        CancelarBB = new javax.swing.JButton();
        GuargarBB = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setResizable(true);
        jInternalFrame1.setVisible(true);

        TxtEditableReferenciasBiblioteca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtEditableReferenciasBibliotecaActionPerformed(evt);
            }
        });

        jLabel1.setText("EDITANDO DATOS");

        CancelarBB.setText("Cancelar");

        GuargarBB.setText("Guardar");

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtEditableReferenciasBiblioteca, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGap(0, 326, Short.MAX_VALUE)
                                .addComponent(CancelarBB)
                                .addGap(18, 18, 18)
                                .addComponent(GuargarBB))
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TxtEditableReferenciasBiblioteca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelarBB)
                    .addComponent(GuargarBB))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtEditableReferenciasBibliotecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtEditableReferenciasBibliotecaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtEditableReferenciasBibliotecaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelarBB;
    private javax.swing.JButton GuargarBB;
    private javax.swing.JTextField TxtEditableReferenciasBiblioteca;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
