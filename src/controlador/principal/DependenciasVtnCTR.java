package controlador.principal;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import vista.principal.VtnPrincipal;

/**
 * En esta clase sera la padre de CTR aqui agregaremos dependencias, 
 * ya sean ventanas, ctroladores que se necesiten en todos los formularios, o no
 * pero controlando los que no son necesarios
 * @author Johnny
 */
public class DependenciasVtnCTR {
    public final ConectarDB conecta;
    public final VtnPrincipal vtnPrin;
    public final VtnPrincipalCTR ctrPrin;
    public DefaultTableModel mdTbl; 
    public int posFila; 

    public DependenciasVtnCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin) {
        this.conecta = conecta;
        this.vtnPrin = vtnPrin;
        this.ctrPrin = ctrPrin;
    }
    
    public void iniciarTbl(String[] titulo, String[][] datos, JTable tbl){
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo); 
        tbl.setModel(mdTbl);
        TblEstilo.formatoTbl(tbl);
    }
    
    
}
