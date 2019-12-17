package controlador.estilo.cmb;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author gus
 */
public class TblEditorSpinner extends AbstractCellEditor implements TableCellEditor {

    private final JSpinner spn;

    public TblEditorSpinner(JSpinner spn) {
        this.spn = spn;
    }

    @Override
    public Object getCellEditorValue() {
        SpinnerModel sm = spn.getModel();
        return sm;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        System.out.println("Valor al editar: " + value.toString());
        spn.setValue(1);
        return spn;
    }

}
