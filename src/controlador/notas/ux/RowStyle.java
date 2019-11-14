package controlador.notas.ux;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author MrRainx
 */
public class RowStyle extends DefaultTableCellRenderer {

    private final int columna;
    private Map<String, Color> estados;
    private Color bgColor;

    public RowStyle(int columna) {
        this.columna = columna;
    }

    public Color getBgColor() {
        if (bgColor == null) {
            return Color.WHITE;
        }
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setEstados(Map<String, Color> estados) {
        this.estados = estados;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        setBackground(bgColor);
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        try {

            String valor = table.getValueAt(row, this.columna).toString();

            Color color = estados
                    .entrySet()
                    .stream()
                    .filter(item -> item.getKey().toLowerCase().contains(valor.toLowerCase()))
                    .map(c -> c.getValue())
                    .findFirst()
                    .orElse(null);

            setForeground(color);

            setHorizontalAlignment(CENTER);
            setFont(new Font("Arial", Font.PLAIN, 11));
            table.setSelectionBackground(Color.lightGray);

            setHorizontalAlignment(0);

        } catch (NumberFormatException | NullPointerException e) {

        }

        return this;
    }

}
