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

    public RowStyle(int columna) {
        this.columna = columna;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        setBackground(Color.white);
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        try {

            String valor = table.getValueAt(row, this.columna).toString();

            if (valor.equalsIgnoreCase("APROBADO")) {
                setForeground(Color.BLUE);
            } else if (valor.equalsIgnoreCase("REPROBADO")) {
                setForeground(Color.RED);
            } else {
                setForeground(new Color(0, 0, 0));
            }
        } catch (NumberFormatException | NullPointerException e) {

        }

        return this;
    }

}
