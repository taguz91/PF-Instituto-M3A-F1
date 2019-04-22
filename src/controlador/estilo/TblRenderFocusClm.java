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
    private JLabel lbl;

    public TblRenderFocusClm(int clm) {
        this.clm = clm;
    }

    private void iniciaLbl() {
        lbl.setVisible(true);
        lbl.setOpaque(false);
        lbl.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
            boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        
        this.lbl = new JLabel();
        iniciaLbl();

        if (value != null) {
            lbl.setText(value.toString().split("%")[1]);
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
