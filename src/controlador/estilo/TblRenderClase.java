/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.estilo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Johnny
 */
public class TblRenderClase extends DefaultTableCellRenderer {

    private final int clm;
    private JLabel lbl;

    public TblRenderClase(int clm) {
        this.clm = clm;
    }

    private void iniciaLbl() {
        lbl.setVisible(true);
        lbl.setOpaque(false);
        lbl.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
            boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        this.lbl = new JLabel();
        iniciaLbl();

        if (value != null) {
            lbl.setText(value.toString());
        }

        if (focused) {
            if (clm == column) {
                lbl.setOpaque(true);
                lbl.setBackground(new Color(153, 153, 153));
            }
        }

        return lbl;
    }

}
