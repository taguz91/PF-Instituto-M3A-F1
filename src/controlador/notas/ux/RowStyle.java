package controlador.notas.ux;

import controlador.Libraries.Middlewares;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.beans.VetoableChangeListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author MrRainx
 */
public class RowStyle extends DefaultTableCellRenderer {

    private int columna;
    private int minValue;

    public RowStyle(int columna, int minValue) {
        this.columna = columna;
        this.minValue = minValue;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        setBackground(Color.white);
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        try {

            double valor = Double.valueOf(table.getValueAt(row, this.columna).toString());

            if (valor >= minValue) {
                setForeground(Color.BLUE);
            } else {
                setForeground(Color.RED);
            }
        } catch (NumberFormatException | NullPointerException e) {

        }

        return this;
    }

}
