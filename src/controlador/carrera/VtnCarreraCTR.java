package controlador.carrera;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnCarreraCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnCarrera vtnCarrera;
    private final CarreraBD car = new CarreraBD();
    ArrayList<CarreraMD> carreras; 
    
    DefaultTableModel mdTbl; 

    public VtnCarreraCTR(VtnPrincipal vtnPrin, VtnCarrera vtnCarrera) {
        this.vtnPrin = vtnPrin;
        this.vtnCarrera = vtnCarrera;

        vtnPrin.getDpnlPrincipal().add(vtnCarrera);
        vtnCarrera.show();
    }

    public void iniciar() {
        String titutlo [] = {"id", "Codigo", "Nombre", "Fecha Inicio", "Modalidad"}; 
        String datos [][] = {}; 
        mdTbl = TblEstilo.modelTblSinEditar(datos, titutlo);
        vtnCarrera.getTblMaterias().setModel(mdTbl);
        TblEstilo.ocualtarID(vtnCarrera.getTblMaterias());
        
        cargarCarreras();
    }

    public void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera);
        ctrFrmCarrera.iniciar();
    }
    
    public void cargarCarreras(){
        carreras = car.cargarCarreras();
        llenarTbl(carreras); 
    }
    
    public void llenarTbl(ArrayList<CarreraMD> carreras){
        mdTbl.setRowCount(0); 
        if (carreras != null) {
            carreras.forEach((c) -> {
                Object valores[] = { c.getId(), c.getCodigo(), c.getNombre(),
                    c.getFechaInicio(), c.getModalidad()};
                mdTbl.addRow(valores);
            });
        }
    }

}
