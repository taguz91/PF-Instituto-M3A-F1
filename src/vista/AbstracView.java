package vista;

import controlador.notas.ux.RowStyle;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.CONS;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcelo
 */
public abstract class AbstracView extends javax.swing.JInternalFrame {

    {
        this.setFrameIcon(CONS.getICONO());
    }

    public void centrarCabecera(JTable table) {
        DefaultTableCellRenderer headerTrad = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerTrad.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void centrarCeldas(JTable table) {
//        DefaultTableCellRenderer tableCellRender = new DefaultTableCellRenderer();
//
//        tableCellRender.setHorizontalAlignment(SwingConstants.CENTER);
//        table.getColumnModel().getColumn(0).setCellRenderer(tableCellRender);
        table.setDefaultRenderer(Object.class, new RowStyle(1));
    }
}
