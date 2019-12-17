package controlador.estilo.cmb;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author gus
 */
public class TblEditorSpinner extends AbstractCellEditor implements TableCellEditor {

    private final JSpinner spn;
    private CellEditorListener cellEditorListener = null;

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

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        try {
            cellEditorListener.editingStopped(new ChangeEvent(this));
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return true;
    }

    @Override
    public void cancelCellEditing() {
        cellEditorListener.editingCanceled(new ChangeEvent(this));
    }

    @Override
    public void addCellEditorListener(CellEditorListener celleditorlistener) {
        cellEditorListener = celleditorlistener;
    }

    @Override
    public void removeCellEditorListener(CellEditorListener celleditorlistener) {
        if (cellEditorListener == cellEditorListener) {
            cellEditorListener = null;
        }
    }

}
