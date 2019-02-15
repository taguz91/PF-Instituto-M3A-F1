package controlador.materia;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import vista.materia.VtnMateria;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMateriaCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnMateria vtnMateria;
    private final MateriaBD materia; 
    
    //El modelo de la tabla materias  
    DefaultTableModel mdTblMat; 
    //Aqui guardamos todas las materias  
    private ArrayList<MateriaMD> materias; 

    public VtnMateriaCTR(VtnPrincipal vtnPrin, VtnMateria vtnMateria, MateriaBD materia) {
        this.vtnPrin = vtnPrin;
        this.vtnMateria = vtnMateria;
        this.materia = materia; 
        
        vtnPrin.getDpnlPrincipal().add(vtnMateria);  
        vtnMateria.show(); 
    }
    
    public void iniciar(){
        String titulo [] = {"id", "Nombre", "Codigo"};
        String datos [][] = {}; 
        mdTblMat = new DefaultTableModel(datos, titulo);
        //Le pasamos el modelo a la tabla  v
        vtnMateria.getTblMateria().setModel(mdTblMat); 
        
        materias = materia.cargarMateria();  
        
        
        cargarTblMaterias();
    }
    
    public void abrirFrmMateria(){
        
    }
    
    public void cargarTblMaterias(){
        if (!materias.isEmpty()) {
            for(MateriaMD mt : materias){
                Object valores [] = {mt.getId(), 
                    mt.getNombre(), mt.getCodigo()};
                mdTblMat.addRow(valores); 
            }
        }
    }
    
    
}
