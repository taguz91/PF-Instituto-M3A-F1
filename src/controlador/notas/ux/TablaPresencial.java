package controlador.notas.ux;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import vista.notas.VtnNotas;

/**
 *
 * @author Alejandro
 */
public class TablaPresencial extends JTable {

    double dato;
    private double valorMinimo;

    private VtnNotas vista;

    public TablaPresencial(VtnNotas vista) {
        this.vista = vista;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
        Component component = super.prepareRenderer(renderer, rowIndex, columnIndex);
        component.setBackground(Color.WHITE);
        component.setForeground(Color.BLACK);
        try {
            dato = Double.parseDouble(this.getModel().getValueAt(rowIndex, 10).toString());
            if (dato >= valorMinimo) {
                component.setBackground(Color.WHITE);
                component.setForeground(Color.BLUE);
            } else {
                component.setForeground(Color.RED);
            }
        } catch (NullPointerException e) {
        }
        return component;
    }

}
