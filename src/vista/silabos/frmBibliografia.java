
package vista.silabos;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class frmBibliografia extends javax.swing.JInternalFrame {

    public JTextField getTxtAutor3() {
        return txtAutor3;
    }

    public void setTxtAutor3(JTextField txtAutor3) {
        this.txtAutor3 = txtAutor3;
    }

    public JTextField getTxtAutor2() {
        return txtAutor2;
    }

    public void setTxtAutor2(JTextField txtAutor2) {
        this.txtAutor2 = txtAutor2;
    }

    public JTextField getTxtArea() {
        return txtArea;
    }

    public void setTxtArea(JTextField txtArea) {
        this.txtArea = txtArea;
    }

    public JTextField getTxtCodigoDewey() {
        return txtCodigoDewey;
    }

    public void setTxtCodigoDewey(JTextField txtCodigoDewey) {
        this.txtCodigoDewey = txtCodigoDewey;
    }

    public JTextField getTxtCodigoKoha() {
        return txtCodigoKoha;
    }

    public void setTxtCodigoKoha(JTextField txtCodigoKoha) {
        this.txtCodigoKoha = txtCodigoKoha;
    }

    public JLabel getLbCodigoISBM() {
        return lbCodigoISBM;
    }

    public void setLbCodigoISBM(JLabel lbCodigoISBM) {
        this.lbCodigoISBM = lbCodigoISBM;
    }

    public JLabel getLbCodigoLibro() {
        return lbCodigoLibro;
    }

    public void setLbCodigoLibro(JLabel lbCodigoLibro) {
        this.lbCodigoLibro = lbCodigoLibro;
    }

    public JLabel getLbNumeroPaginas() {
        return lbNumeroPaginas;
    }

    public void setLbNumeroPaginas(JLabel lbNumeroPaginas) {
        this.lbNumeroPaginas = lbNumeroPaginas;
    }

    public JTextField getTxtCodigoISBM() {
        return txtCodigoISBM;
    }

    public void setTxtCodigoISBM(JTextField txtCodigoISBM) {
        this.txtCodigoISBM = txtCodigoISBM;
    }

    public JTextField getTxtNumeroPaginas() {
        return txtNumeroPaginas;
    }

    public void setTxtNumeroPaginas(JTextField txtNumeroPaginas) {
        this.txtNumeroPaginas = txtNumeroPaginas;
    }

   
    public frmBibliografia() {
        initComponents();
    }

    public JCheckBox getCbxExistenciaBiblioteca() {
        return cbxExistenciaBiblioteca;
    }

    public void setCbxExistenciaBiblioteca(JCheckBox cbxExistenciaBiblioteca) {
        this.cbxExistenciaBiblioteca = cbxExistenciaBiblioteca;
    }

    public JLabel getLbObeservaciones() {
        return lbObeservaciones;
    }

    public void setLbObeservaciones(JLabel lbObeservaciones) {
        this.lbObeservaciones = lbObeservaciones;
    }

    public JTextField getTxtObservaciones() {
        return txtCodigoKoha;
    }

    public void setTxtObservaciones(JTextField txtObservaciones) {
        this.txtCodigoKoha = txtObservaciones;
    }

    public JTextField getTxtCodigoLibro() {
        return txtCodigoLibro;
    }


    public JButton getGuardarB() {
        return GuardarB;
    }

    public void setGuardarB(JButton GuardarB) {
        this.GuardarB = GuardarB;
    }

    public JButton getBtnCancelarB() {
        return btnCancelarB;
    }

    public void setBtnCancelarB(JButton btnCancelarB) {
        this.btnCancelarB = btnCancelarB;
    }

    public JLabel getLbAnio() {
        return lbAnio;
    }

    public void setLbAnio(JLabel lbAnio) {
        this.lbAnio = lbAnio;
    }

    public JLabel getLbAutor() {
        return lbAutor;
    }

    public void setLbAutor(JLabel lbAutor) {
        this.lbAutor = lbAutor;
    }

    public JLabel getLbCiudad() {
        return lbCiudad;
    }

    public void setLbCiudad(JLabel lbCiudad) {
        this.lbCiudad = lbCiudad;
    }

    public JLabel getLbEditor() {
        return lbEditor;
    }

    public void setLbEditor(JLabel lbEditor) {
        this.lbEditor = lbEditor;
    }

    public JLabel getLbTitulo() {
        return lbTitulo;
    }

    public void setLbTitulo(JLabel lbTitulo) {
        this.lbTitulo = lbTitulo;
    }

    public JLabel getLbTituloVentanaCBApa() {
        return lbTituloVentanaCBApa;
    }

    public void setLbTituloVentanaCBApa(JLabel lbTituloVentanaCBApa) {
        this.lbTituloVentanaCBApa = lbTituloVentanaCBApa;
    }

    public JTextField getTxtAnio() {
        return txtAnio;
    }

    public void setTxtAnio(JTextField txtAnio) {
        this.txtAnio = txtAnio;
    }

    public JTextField getTxtAutor() {
        return txtAutor;
    }

    public void setTxtAutor(JTextField txtAutor) {
        this.txtAutor = txtAutor;
    }

    public JTextField getTxtCiudad() {
        return txtCiudad;
    }

    public void setTxtCiudad(JTextField txtCiudad) {
        this.txtCiudad = txtCiudad;
    }

    public JTextField getTxtEditor() {
        return txtEditor;
    }

    public void setTxtEditor(JTextField txtEditor) {
        this.txtEditor = txtEditor;
    }

    public JTextField getTxtTitulo() {
        return txtTitulo;
    }

    public void setTxtTitulo(JTextField txtTitulo) {
        this.txtTitulo = txtTitulo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        lbTituloVentanaCBApa = new javax.swing.JLabel();
        lbAutor = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        lbTitulo = new javax.swing.JLabel();
        txtAnio = new javax.swing.JTextField();
        lbAnio = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lbCiudad = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        lbEditor = new javax.swing.JLabel();
        txtEditor = new javax.swing.JTextField();
        btnCancelarB = new javax.swing.JButton();
        GuardarB = new javax.swing.JButton();
        lbCodigoLibro = new javax.swing.JLabel();
        txtCodigoLibro = new javax.swing.JTextField();
        txtCodigoKoha = new javax.swing.JTextField();
        lbObeservaciones = new javax.swing.JLabel();
        cbxExistenciaBiblioteca = new javax.swing.JCheckBox();
        txtNumeroPaginas = new javax.swing.JTextField();
        lbNumeroPaginas = new javax.swing.JLabel();
        txtCodigoISBM = new javax.swing.JTextField();
        lbCodigoISBM = new javax.swing.JLabel();
        txtObservaciones1 = new javax.swing.JTextField();
        txtArea = new javax.swing.JTextField();
        txtCodigoDewey = new javax.swing.JTextField();
        lbCodigokoha = new javax.swing.JLabel();
        lbCodigoDewey = new javax.swing.JLabel();
        lbArea = new javax.swing.JLabel();
        txtAutor2 = new javax.swing.JTextField();
        lbAutor1 = new javax.swing.JLabel();
        lbAutor2 = new javax.swing.JLabel();
        txtAutor3 = new javax.swing.JTextField();

        jRadioButton1.setText("jRadioButton1");

        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        lbTituloVentanaCBApa.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        lbTituloVentanaCBApa.setText("CAMPOS BIBLIOGRAFICOS APA");

        lbAutor.setText("Autor");

        txtAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutorActionPerformed(evt);
            }
        });

        lbTitulo.setText("Titulo");

        lbAnio.setText("Año");

        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });

        lbCiudad.setText("Ciudad");

        txtCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiudadActionPerformed(evt);
            }
        });

        lbEditor.setText("Editor");

        txtEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditorActionPerformed(evt);
            }
        });

        btnCancelarB.setText("Cancelar");

        GuardarB.setText("Guardar");

        lbCodigoLibro.setText("CodigoLibro");

        txtCodigoLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoLibroActionPerformed(evt);
            }
        });

        txtCodigoKoha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoKohaActionPerformed(evt);
            }
        });

        lbObeservaciones.setText("Observaciones");

        cbxExistenciaBiblioteca.setText("EXISTENCIA EN BIBLIOTECA LOCAL");
        cbxExistenciaBiblioteca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxExistenciaBibliotecaActionPerformed(evt);
            }
        });

        txtNumeroPaginas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroPaginasActionPerformed(evt);
            }
        });

        lbNumeroPaginas.setText("Numero de paginas");

        txtCodigoISBM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoISBMActionPerformed(evt);
            }
        });

        lbCodigoISBM.setText("Codigo ISBN");

        txtObservaciones1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservaciones1ActionPerformed(evt);
            }
        });

        txtArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAreaActionPerformed(evt);
            }
        });

        txtCodigoDewey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoDeweyActionPerformed(evt);
            }
        });

        lbCodigokoha.setText("Codigo koha");

        lbCodigoDewey.setText("Codigo Dewey");

        lbArea.setText("Area");

        txtAutor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutor2ActionPerformed(evt);
            }
        });

        lbAutor1.setText("Autor 2");

        lbAutor2.setText("Autor 3");

        txtAutor3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutor3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtAutor3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbCodigoLibro)
                                        .addComponent(lbTitulo)
                                        .addComponent(lbCiudad)
                                        .addComponent(lbEditor)
                                        .addComponent(lbAutor2)
                                        .addComponent(lbAutor1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtAutor2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                                            .addComponent(txtAutor, javax.swing.GroupLayout.Alignment.LEADING))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtEditor, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(btnCancelarB)
                                .addGap(10, 10, 10)
                                .addComponent(GuardarB)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbCodigoDewey)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lbCodigoISBM)
                                                .addComponent(txtCodigoISBM, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lbCodigokoha))
                                            .addComponent(txtCodigoKoha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(txtCodigoDewey, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtObservaciones1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbAnio)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbAutor)
                                    .addComponent(txtCodigoLibro, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                                    .addComponent(txtTitulo)
                                    .addComponent(txtAnio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbObeservaciones)
                                    .addComponent(lbArea)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbNumeroPaginas)
                                            .addComponent(txtNumeroPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxExistenciaBiblioteca)
                                .addGap(63, 63, 63)
                                .addComponent(lbTituloVentanaCBApa)
                                .addGap(0, 304, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTituloVentanaCBApa)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cbxExistenciaBiblioteca)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 17, Short.MAX_VALUE)
                        .addComponent(lbNumeroPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumeroPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbCodigoISBM)
                            .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigoISBM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbAutor1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbCodigokoha)
                            .addComponent(txtAutor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbCodigoLibro)
                        .addGap(13, 13, 13)
                        .addComponent(txtCodigoLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbAutor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoKoha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAutor2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCodigoDewey)
                    .addComponent(txtAutor3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoDewey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTitulo))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbArea)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lbAnio)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbObeservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCiudad)
                    .addComponent(txtObservaciones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbEditor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarB)
                    .addComponent(GuardarB)
                    .addComponent(txtEditor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutorActionPerformed

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloActionPerformed

    private void txtCodigoLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoLibroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoLibroActionPerformed

    private void txtEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditorActionPerformed

    private void txtCodigoKohaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoKohaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoKohaActionPerformed

    private void cbxExistenciaBibliotecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxExistenciaBibliotecaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxExistenciaBibliotecaActionPerformed

    private void txtNumeroPaginasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroPaginasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroPaginasActionPerformed

    private void txtCodigoISBMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoISBMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoISBMActionPerformed

    private void txtObservaciones1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservaciones1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservaciones1ActionPerformed

    private void txtAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAreaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAreaActionPerformed

    private void txtCodigoDeweyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoDeweyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoDeweyActionPerformed

    private void txtAutor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutor2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutor2ActionPerformed

    private void txtCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiudadActionPerformed

    private void txtAutor3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutor3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutor3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton GuardarB;
    public javax.swing.JButton btnCancelarB;
    private javax.swing.JCheckBox cbxExistenciaBiblioteca;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JLabel lbAnio;
    private javax.swing.JLabel lbArea;
    public javax.swing.JLabel lbAutor;
    public javax.swing.JLabel lbAutor1;
    public javax.swing.JLabel lbAutor2;
    private javax.swing.JLabel lbCiudad;
    private javax.swing.JLabel lbCodigoDewey;
    private javax.swing.JLabel lbCodigoISBM;
    public javax.swing.JLabel lbCodigoLibro;
    private javax.swing.JLabel lbCodigokoha;
    private javax.swing.JLabel lbEditor;
    private javax.swing.JLabel lbNumeroPaginas;
    private javax.swing.JLabel lbObeservaciones;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JLabel lbTituloVentanaCBApa;
    public javax.swing.JTextField txtAnio;
    public javax.swing.JTextField txtArea;
    public javax.swing.JTextField txtAutor;
    public javax.swing.JTextField txtAutor2;
    public javax.swing.JTextField txtAutor3;
    public javax.swing.JTextField txtCiudad;
    public javax.swing.JTextField txtCodigoDewey;
    public javax.swing.JTextField txtCodigoISBM;
    public javax.swing.JTextField txtCodigoKoha;
    public javax.swing.JTextField txtCodigoLibro;
    public javax.swing.JTextField txtEditor;
    public javax.swing.JTextField txtNumeroPaginas;
    public javax.swing.JTextField txtObservaciones1;
    public javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
