/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.validaciones;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Johnny
 */
public class TxtVDireccion extends KeyAdapter {

    private final JTextField txt;
    private final JLabel lbl;
    private String ingreso;

    public TxtVDireccion(JTextField txt) {
        this.txt = txt;
        this.lbl = null;
    }

    public TxtVDireccion(JTextField txt, JLabel lbl) {
        this.txt = txt;
        this.lbl = lbl;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ingreso = txt.getText().trim();

        if (ingreso.equals("")) {
            lbl.setVisible(false);
            txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        } else {
            if (e.getKeyCode() != 10 && e.getKeyCode() != 127 && ingreso.length() > 0) {
                txt.setSize(txt.getWidth(), 20);
                txt.setPreferredSize(new Dimension(txt.getWidth(), 20));
                if (!Validar.esDireccion(ingreso)) {
                    txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                    if (lbl != null) {
                        lbl.setVisible(true);
                    }
                } else {
                    txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                    if (lbl != null) {
                        lbl.setVisible(false);
                    }
                }
            }
        }
    }
}
