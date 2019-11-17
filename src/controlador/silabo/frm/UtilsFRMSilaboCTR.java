package controlador.silabo.frm;

import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.validaciones.Validar;

/**
 *
 * @author gus
 */
public class UtilsFRMSilaboCTR {

    private static UtilsFRMSilaboCTR USCTR;

    public static UtilsFRMSilaboCTR single() {
        if (USCTR == null) {
            USCTR = new UtilsFRMSilaboCTR();
        }
        return USCTR;
    }

    public double getHoraSPN(JSpinner spn) {
        double h = -1;
        String hs = spn.getValue().toString().trim();
        if (Validar.esNumerosDecimales(hs)) {
            h = Double.parseDouble(hs);
        }
        return h;
    }

    public void errorHoras(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "Horas no permitidas",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public void errorFecha(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "Fechas incorrectas",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public LocalDate getFechaJDC(JDateChooser jdc) {
        LocalDate fecha = null;
        if (jdc.getDate() != null) {
            fecha = jdc.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return fecha;
    }

    public void errorFrmEvaluacion(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "Error en el formulario de atividades",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public String getIdLocalActividad(JTable tbl) {
        String code = "";
        int pos = tbl.getSelectedRow();
        if (pos >= 0) {
            code = tbl.getValueAt(pos, 0).toString();
        }
        return code;
    }
    
        
    public String getTextFromTxt(JTextField jtf) {
        return jtf.getText().trim();
    }
    
    public String getTextFromTxa(JTextArea jta) {
        return jta.getText().trim();
    }

}
