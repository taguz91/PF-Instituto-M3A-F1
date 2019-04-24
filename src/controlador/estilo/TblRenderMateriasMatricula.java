package controlador.estilo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Johnny
 */
public class TblRenderMateriasMatricula extends DefaultTableCellRenderer {

    private final int clm;

    public TblRenderMateriasMatricula(int clm) {
        this.clm = clm;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
            boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        setBackground(Color.white);
        if (clm == column) {
            if (value != null) {
                if (value.toString().charAt(0) == 'C') {
                    setBackground(Color.red);
                }
            }

        }
        return this;
    }

}
