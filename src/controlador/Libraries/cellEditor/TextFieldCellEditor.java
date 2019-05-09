package controlador.Libraries.cellEditor;


import java.awt.Component;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author MrRainx
 */
public class TextFieldCellEditor extends JTextField implements TableCellEditor {

    private CellEditorListener cellEditorListener = null;

//    private final boolean isInteger = false;
    private Object oldValue;
    private final boolean editar;

    public TextFieldCellEditor(boolean editar) {
        this.editar = editar;
        setEditable(false);
    }

    // Start editing
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj, boolean isSelected, int row, int column) {

        super.setText(obj.toString());

        if (isSelected) {
            this.setSelectionStart(0);
            this.setSelectionEnd(obj.toString().length());
        } else {
            this.setSelectionStart(0);
            this.setSelectionEnd(obj.toString().length());
        }

        return this;
    }

    // Retrieve e dited value
    @Override
    public Object getCellEditorValue() {
        return super.getText();
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return editar;
    }

    @Override
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        cellEditorListener.editingStopped(new ChangeEvent(this));
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
