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
public class TblRenderFocusClm extends DefaultTableCellRenderer {

    private final int clm;

    public TblRenderFocusClm(int clm) {
        this.clm = clm;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
            boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        JLabel lbl = new JLabel();
        lbl.setVisible(true);
        lbl.setOpaque(false);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        if (value != null) {
            lbl.setText(value.toString());
        }

        if (focused) {
            if (clm == column) {
                lbl.setOpaque(true);
                lbl.setBackground(new Color(153, 153, 153));
            }
        }

        return lbl;
    }

}
