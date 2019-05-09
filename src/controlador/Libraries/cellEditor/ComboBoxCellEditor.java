package controlador.Libraries.cellEditor;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author MrRainx
 */
public class ComboBoxCellEditor extends DefaultTableCellRenderer {

    private int col = -1;

    public ComboBoxCellEditor() {
    }

    public ComboBoxCellEditor(int col) {
        this.col = col;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        setForeground(Color.red);
        if (col == column) {
            JComboBox comboBox = new JComboBox();
            comboBox.addItem(value);
            return comboBox;
        }

        return cellComponent;
    }
    
    
    
}
