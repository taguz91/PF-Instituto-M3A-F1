package controlador.principal;

import java.sql.SQLException;
import modelo.ConectarDB;
import vista.principal.JDConsolaBD;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDConsolaBDCTR {
    
    private final JDConsolaBD jd; 
    private final ConectarDB conecta;
    private int cont = 0;

    public JDConsolaBDCTR(VtnPrincipal vtnPrin, ConectarDB conecta) {
        this.jd = new JDConsolaBD(vtnPrin, false);
        jd.setLocationRelativeTo(vtnPrin);
        jd.setVisible(true);
        this.conecta = conecta;
    }
    
    public void iniciar(){
        //Para que salta la linea autimaticamente
        jd.getTxtArea().setLineWrap(true);
        jd.getTxtArea().setWrapStyleWord(true);
        
        jd.getBtnEjecutar().addActionListener(e -> ejecutar());
        
        jd.setModal(true);
    }
    
    private void ejecutar(){
        String txt = jd.getTxtArea().getText().trim();
        //System.out.println(txt);
        SQLException es = conecta.nosql(txt);
        if (es == null) {
            cont++;
            jd.getLblError().setText("<html>Se ejecuto la accion correctamente. Accion numero: "+cont+"</html>");
            jd.getTxtArea().selectAll();
        }else{
            jd.getLblError().setText("<html>"+es.getMessage()+ "\n"+es.getSQLState()+"</html>");
        }
        
    }
    
}
