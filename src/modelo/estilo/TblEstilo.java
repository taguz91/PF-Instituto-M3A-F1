package modelo.estilo;

import controlador.estilo.TblRenderFocusClm;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Johnny
 */
public class TblEstilo {

    public TblEstilo() {
    }

    public static void columnaMedida(JTable tbl, int columna, int medida) {
        TableColumnModel mdColum = tbl.getColumnModel();
        //Pasamos el tamaño a la columna indicada
        mdColum.getColumn(columna).setPreferredWidth(medida);
        mdColum.getColumn(columna).setWidth(medida);
        mdColum.getColumn(columna).setMinWidth(medida);
        mdColum.getColumn(columna).setMaxWidth(medida);
        mdColum.getColumn(columna).setMaxWidth(medida);
    }

    //Con este metodo ocultamos la primera columna de la tabla
    public static void ocualtarID(JTable tbl) {
        TableColumnModel mdColum = tbl.getColumnModel();
        //Ocultamso la id 
        mdColum.getColumn(0).setPreferredWidth(0);
        mdColum.getColumn(0).setWidth(0);
        mdColum.getColumn(0).setMinWidth(0);
        mdColum.getColumn(0).setMaxWidth(0);
    }

    public static void formatoTbl(JTable tbl) {
        //Con esto solo selecionaremos un fila
        tbl.setSelectionMode(0);
        JTableHeader tblHead = tbl.getTableHeader();
        //Para que no se pueda reordenar las columnas 
        tblHead.setReorderingAllowed(false);
        //Para que no se pueda cambiar su longitud  
        tblHead.setResizingAllowed(false);
        //Centramos los titulos de las tablas 
        DefaultTableCellRenderer hedRender = (DefaultTableCellRenderer) tblHead.getDefaultRenderer();
        //Centramos los textos
        hedRender.setHorizontalAlignment(JLabel.CENTER);
        //Le pasamos el render a nuestro table head
        tblHead.setDefaultRenderer(hedRender);
    }

    public static void formatoTblConColor(JTable tbl) {
        JTableHeader tblHead = tbl.getTableHeader();

        tblHead.setBackground(new Color(49, 79, 117));
        tblHead.setForeground(new Color(255, 255, 255));
        tblHead.setOpaque(false);
        tblHead.setFont(new Font("Arial", Font.PLAIN, 16));
        //Para que no se pueda reordenar las columnas 
        tblHead.setReorderingAllowed(false);
        //Para que no se pueda cambiar su longitud  
        tblHead.setResizingAllowed(false);
        //Centramos los titulos de las tablas 
        DefaultTableCellRenderer hedRender = (DefaultTableCellRenderer) tblHead.getDefaultRenderer();
        //Centramos los textos
        hedRender.setHorizontalAlignment(JLabel.CENTER);
        //Le pasamos el render a nuestro table head
        tblHead.setDefaultRenderer(hedRender);
        //Letra y anchura de las tablas
        tbl.setFont(new Font("Arial", Font.PLAIN, 14));
        tbl.setRowHeight(30);
        //Con esto solo selecionaremos un fila
        tbl.setSelectionMode(0);
    }

    public static void formatoTblFocus(JTable tbl) {
        JTableHeader tblHead = tbl.getTableHeader();

        tblHead.setBackground(new Color(49, 79, 117));
        tblHead.setForeground(new Color(255, 255, 255));
        tblHead.setOpaque(false);
        tblHead.setFont(new Font("Arial", Font.PLAIN, 16));
        //Para que no se pueda reordenar las columnas 
        tblHead.setReorderingAllowed(false);
        //Para que no se pueda cambiar su longitud  
        tblHead.setResizingAllowed(false);
        //Centramos los titulos de las tablas 
        DefaultTableCellRenderer hedRender = (DefaultTableCellRenderer) tblHead.getDefaultRenderer();
        //Centramos los textos
        hedRender.setHorizontalAlignment(JLabel.CENTER);
        //Le pasamos el render a nuestro table head
        tblHead.setDefaultRenderer(hedRender);
        //Letra y anchura de las tablas
        tbl.setFont(new Font("Arial", Font.PLAIN, 14));
        tbl.setRowHeight(30);
        //Con esto solo selecionaremos un fila
        tbl.setSelectionMode(0);

        for (int i = 1; i < tbl.getColumnCount(); i++) {
            tbl.getColumnModel().getColumn(i).setCellRenderer(new TblRenderFocusClm(i));
        }
    }

    public static DefaultTableModel modelTblSinEditar(String datos[][], String titulo[]) {
        DefaultTableModel modelo = new DefaultTableModel(datos, titulo) {
            @Override
            public final boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return modelo;
    }
}
