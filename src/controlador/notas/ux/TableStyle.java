package controlador.notas.ux;

import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Alejandro
 */
public class TableStyle extends JTable {

    double dato;

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
        Component component = super.prepareRenderer(renderer, rowIndex, columnIndex);
        component.setBackground(Color.WHITE);
        component.setForeground(Color.BLACK);
        int filas = this.getRowCount();
        if (filas > 0) {
            for (int i = 0; i < filas; i++) {
                dato = Double.parseDouble(this.getModel().getValueAt(i, 10).toString());
                if (dato < 70.0) {

                    if ((Object.class.equals(this.getColumnClass(columnIndex))) && (getValueAt(i, columnIndex) != null)) {
                        component.setBackground(Color.RED);
                        component.setForeground(Color.BLACK);
                    }

                } else if (dato >= 70.0) {
                    if ((Object.class.equals(this.getColumnClass(columnIndex))) && (getValueAt(i, columnIndex) != null)) {
                        component.setBackground(Color.GREEN);
                        component.setForeground(Color.BLACK);
                    }

                }
            }
        }

        return component;
    }

}
