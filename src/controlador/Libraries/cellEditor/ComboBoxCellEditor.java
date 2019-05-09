/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.Libraries.cellEditor;

import java.awt.Component;
import java.awt.Font;
import java.util.EventObject;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author MrRainx
 */
public class ComboBoxCellEditor extends JComboBox implements TableCellEditor {

    private CellEditorListener cellEditorListener = null;

//    private final boolean isInteger = false;
    private Object oldValue;
    private final boolean editar;
    private final List<String> items;

    public ComboBoxCellEditor(boolean editar, List<String> items) {
        this.editar = editar;
        this.items = items;

        items.forEach(obj -> {
            addItem(obj);
        });
        this.addItemListener(e -> {
            stopCellEditing();
        });
    }

    // Start editing
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj, boolean isSelected, int row, int column) {

        super.setSelectedItem(obj.toString());

        setFont(new Font("Arial", Font.PLAIN, 12));
        return this;
    }

    // Retrieve e dited value
    @Override
    public Object getCellEditorValue() {
        return super.getSelectedItem();
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
        } catch (NullPointerException e) {
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
