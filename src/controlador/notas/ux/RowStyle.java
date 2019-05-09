package controlador.notas.ux;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
                setForeground(new Color(37, 107, 187));
            } else if (valor.equalsIgnoreCase("REPROBADO")) {
                setForeground(new Color(214, 48, 12));
            } else {
                setForeground(new Color(0, 0, 0));
            }
            setHorizontalAlignment(CENTER);
            setFont(new Font("Arial", Font.PLAIN, 12));
            table.setSelectionBackground(Color.lightGray);
            //table.setSelectionForeground(Color.lightGray);
        } catch (NumberFormatException | NullPointerException e) {

        }

        return this;
    }

}
