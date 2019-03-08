package controlador.carrera;

import java.util.ArrayList;
import modelo.carrera.CarreraMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import vista.carrera.FrmCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmCarreraCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmCarrera frmCarrera;

    //Para cargar el combo de coordinador  
    private final DocenteBD docen = new DocenteBD();
    private ArrayList<DocenteMD> docentes;
    
    //Todas las modalidades que puede tener una carrera  
    private final String [] MODALIDADES = {"PRESENCIAL", "SEMIPRESENCIAL", "DISTANCIA", "DUAL"}; 

    public FrmCarreraCTR(VtnPrincipal vtnPrin, FrmCarrera frmCarrera) {
        this.vtnPrin = vtnPrin;
        this.frmCarrera = frmCarrera;

        vtnPrin.getDpnlPrincipal().add(frmCarrera);
        frmCarrera.show();
    }

    public void iniciar() {
        cargarCmbModalidades();
        cargarCmbCoordinador(); 
    }
    
    private void guardar(){
        boolean guardar = true; 
        String nombre = frmCarrera.getTxtNombre().getText(); 
        String codigo = frmCarrera.getTxtCodigo().getText(); 
        String dia = frmCarrera.getTxtDia().getText(); 
        String mes = frmCarrera.getTxtMes().getText(); 
        String anio = frmCarrera.getTxtAnio().getText(); 
        String modalidad = frmCarrera.getCmbModalidad().getSelectedItem().toString(); 
        int posCoord = frmCarrera.getCmbCoordinador().getSelectedIndex(); 
    }
    
    private void cargarCmbCoordinador() {
        docentes = docen.cargarDocentes();
        frmCarrera.getCmbCoordinador().removeAllItems();
        frmCarrera.getCmbCoordinador().addItem("Seleccione un docente");
        docentes.forEach(d -> {
            frmCarrera.getCmbCoordinador().addItem(d.getPrimerApellido() + " "
                    + d.getSegundoApellido() + " " + d.getPrimerApellido() + " " + d.getSegundoNombre());
        });
    }
    
    public void editar(CarreraMD carrera){
        frmCarrera.getTxtNombre().setText(carrera.getNombre()); 
        frmCarrera.getTxtCodigo().setText(carrera.getCodigo());
        frmCarrera.getTxtDia().setText(carrera.getFechaInicio().getDayOfMonth() + ""); 
        frmCarrera.getTxtMes().setText(carrera.getFechaInicio().getMonthValue() + ""); 
        frmCarrera.getTxtAnio().setText(carrera.getFechaInicio().getYear()+"");
        frmCarrera.getCmbModalidad().setSelectedItem(carrera.getModalidad()); 
        
        //frmCarrera.getCmbCoordinador().setSelectedItem(carrera.getCoordinador()); 
    }
    
    private  void cargarCmbModalidades(){ 
        frmCarrera.getCmbModalidad().removeAllItems(); 
        frmCarrera.getCmbModalidad().addItem("Seleccione una modalidad"); 
        for (String m : MODALIDADES) {
            frmCarrera.getCmbModalidad().addItem(m); 
        }
    }

}
