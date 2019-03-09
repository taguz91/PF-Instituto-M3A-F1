package controlador.persona;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.persona.DocenteBD;
import modelo.persona.PersonaMD;
import vista.persona.FrmDocente;
import vista.persona.VtnDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnDocenteCTR {
    //array de docente modelo
    //vector de titulos de la tabla en el iniciar
    // vtnMateriaCTR basarme en ese---->para el crud
    private final VtnPrincipal vtnPrin;
    private final VtnDocente vtnDocente; 
     private final DocenteBD docente;
    public VtnDocenteCTR(VtnPrincipal vtnPrin, VtnDocente vtnDocente) {
        this.vtnPrin = vtnPrin;
        this.vtnDocente = vtnDocente; 
        docente = new DocenteBD();
        vtnPrin.getDpnlPrincipal().add(vtnDocente);   
        vtnDocente.show(); 
    }
   
    public void iniciar(){
        vtnDocente.getBtnIngresar().addActionListener(e -> abrirFrmDocente());
        llenarTabla();
    }
     public void llenarTabla() {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnDocente.getTblDocente().getModel();
        for (int i = vtnDocente.getTblDocente().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<PersonaMD> lista = docente.llenarTabla();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(lista.get(i).getIdPersona()), i, 0);
            vtnDocente.getTblDocente().setValueAt(lista.get(i).getIdentificacion(), i, 1);
            vtnDocente.getTblDocente().setValueAt(lista.get(i).getPrimerNombre()
                    + " " + lista.get(i).getSegundoNombre()+lista.get(i).getPrimerApellido()
                    + " " + lista.get(i).getSegundoApellido(), i, 2);
     
        }
        vtnDocente.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }
    public void abrirFrmDocente() {
        DocenteBD docente = new DocenteBD(); 
        FrmDocente frmDocente  = new FrmDocente(); 
        FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, docente);
        ctrFrmDocente.iniciar();
    }
    
    
}
