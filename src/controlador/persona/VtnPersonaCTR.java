package controlador.persona;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.estilo.TblEstilo;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import vista.persona.VtnPersona;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnPersonaCTR {

    private final PersonaBD dbp;
    private final VtnPrincipal vtnPrin;
    private final VtnPersona vtnPersona;
    private DefaultTableModel mdTbl;
    private ArrayList<PersonaMD> personas;

    public VtnPersonaCTR(VtnPrincipal vtnPrin, VtnPersona vtnPersona) {
        this.vtnPrin = vtnPrin;
        this.vtnPersona = vtnPersona;
        vtnPrin.getDpnlPrincipal().add(vtnPersona);
        vtnPersona.show();
        //Iniciamos la clase persona
        dbp = new PersonaBD();
    }

    public void iniciar() {
        String titulo[] = {"ID", "Identificacion", "Nombre Completo", "Fecha Nacimiento"};
        String datos[][] = {};

        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        //Pasamos el modelo a la tabla  
        vtnPersona.getTblPersona().setModel(mdTbl);
        //Pasamos el formato a la tabla  
        TblEstilo.formatoTbl(vtnPersona.getTblPersona());
        //Cambiamos el tamaño de las columnas  
        TblEstilo.columnaMedida(vtnPersona.getTblPersona(), 0, 40);
        TblEstilo.columnaMedida(vtnPersona.getTblPersona(), 1, 100);
        TblEstilo.columnaMedida(vtnPersona.getTblPersona(), 3, 120);

        personas = dbp.cargarPersonas();
        cargarLista();
    }

    //carge de la lista de modelo a la tabla
    //formatear la tabla de mi modelo
    //obtenemos el modelo de la tabla de la vista y la pones en el modelo por defaulta con un castingt
    public void cargarLista() {
        mdTbl.setRowCount(0);
        System.out.println("Tamaño "+personas.size());
        if (personas != null) {
            for (PersonaMD p : personas) {
                Object valores[] = {p.getIdPersona(), p.getIdentificacion(),
                    p.getPrimerNombre() + " " + p.getSegundoNombre() + " "
                    + p.getPrimerApellido() + " " + p.getSegundoApellido(),
                    p.getFechaNacimiento()};

                mdTbl.addRow(valores);
            }
        }
        vtnPersona.getLblResultados().setText(personas.size() + " resultados obtenidos.");
    }

}
