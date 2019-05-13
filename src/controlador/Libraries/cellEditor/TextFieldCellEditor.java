package controlador.Libraries.cellEditor;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        super.setOpaque(true);
        super.setBorder(null);
        super.setHorizontalAlignment(CENTER);
        super.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selectAll();
            }
        });

        super.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    stopCellEditing();
                }
            }
        });

    }

    // Start editing
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj, boolean isSelected, int row, int column) {
        super.setText(obj.toString());

        if (isSelected) {
            this.selectAll();
        }

        setFont(new Font("Arial", Font.PLAIN, 12));
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
