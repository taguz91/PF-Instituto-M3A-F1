package controlador.ventanas;

import java.awt.Component;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Johnny
 */
public class VtnLblToolTip {
    
    public static void agregarTooltipsLblJI(JInternalFrame ji){
        //Component[] cm = ji.getComponents();
        //Component[] cm = ct.getComponents();
        Component[] cm = ji.getContentPane().getComponents();
        for (Component c : cm) {
//            System.out.println("Componente: " + c.getClass());
//            System.out.println("Componente generico: " + c.getClass().toGenericString());
            if (c.getClass().toString().contains("JPanel")) {
                JPanel pnl = (JPanel)c;
//                System.out.println("Numero  de componentes de este panel "+pnl.getComponentCount());
                Component[] cp = pnl.getComponents();
                for (Component p: cp) {
                    if (p.getClass().toString().contains("JLabel")) {
//                        System.out.println("Componente del panel lbl: "+p.getClass().toString());
                        JLabel lbl = (JLabel) p;
                        lbl.setToolTipText(lbl.getText());
                    }
                }
            }
            
        }
    }
}
