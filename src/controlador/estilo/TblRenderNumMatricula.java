/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.estilo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Johnny
 */
public class TblRenderNumMatricula extends DefaultTableCellRenderer {

    private final int clm;

    public TblRenderNumMatricula(int clm) {
        this.clm = clm;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
            boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        if (clm == column) {
            if (value != null) {
                switch (value.toString().charAt(0)) {
                    case '1':
                        setForeground(Color.WHITE);
                        setBackground(new Color(69, 183, 126));
                        break;
                    case '2':
                        setForeground(Color.WHITE);
                        setBackground(new Color(1, 100, 106));
                        break;
                    case '3':
                        setForeground(Color.WHITE);
                        setBackground(new Color(234, 69, 20));
                        break;
                    default:
                        setBackground(Color.white);
                        setForeground(Color.BLACK);
                        break;
                }
            }
        }
        return this;
    }
}
