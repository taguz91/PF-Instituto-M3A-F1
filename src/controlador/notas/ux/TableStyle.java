package controlador.notas.ux;

import javax.swing.JTable;
import java.awt.Color;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
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
        
        dato = Double.parseDouble(this.getModel().getValueAt(rowIndex, 10).toString());
        if (dato >= 70){
            component.setBackground(Color.WHITE);
            component.setForeground(Color.BLUE);
        }else{
            component.setForeground(Color.RED);
        }
       
        return component;
    }

}
