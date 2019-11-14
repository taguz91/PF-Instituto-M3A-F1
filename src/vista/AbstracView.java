package vista;

import controlador.notas.ux.RowStyle;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.CONS;

/**
 *
 * @author MrRainx
 */
public abstract class AbstracView extends JInternalFrame {

    {
        this.setFrameIcon(CONS.getICONO());
    }

    public void centrarCabecera(JTable table) {
        DefaultTableCellRenderer headerTrad = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerTrad.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void centrarCeldas(JTable table) {
        table.setDefaultRenderer(Object.class, new RowStyle(1));
    }
}
