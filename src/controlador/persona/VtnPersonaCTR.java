package controlador.persona;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
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

//    public VtnPersonaCTR(VtnPrincipal vtnPrin, VtnPersona vtnPersona) {
//        this.vtnPrin = vtnPrin;
//        this.vtnPersona = vtnPersona;
//        vtnPrin.getDpnlPrincipal().add(vtnPersona);
//        vtnPersona.show();
//    }

    public VtnPersonaCTR(VtnPrincipal vtnPrin, VtnPersona vtnPersona, PersonaBD dbp) {
        this.vtnPrin = vtnPrin;
        this.vtnPersona = vtnPersona;
        this.dbp = dbp;

        vtnPrin.getDpnlPrincipal().add(vtnPersona);
        vtnPersona.show();
    }

    public void iniciar() throws SQLException {
        String titulo[] = {"ID", "Identificacion", "Nombre Completo", "Fecha Nacimiento"};
        String datos[][] = {};

        mdTbl = new DefaultTableModel(datos, titulo);
        personas = dbp.cargarPersonas();
        cargarLista();
    }

    //carge de la lista de modelo a la tabla
    //formatear la tabla de mi modelo
    //obtenemos el modelo de la tabla de la vista y la pones en el modelo por defaulta con un castingt
    public void cargarLista() {
        mdTbl.setRowCount(0);
        for (PersonaMD p : personas) {
            Object valores[] = {p.getIdPersona(), p.getIdentificacion(),
                p.getPrimerNombre() + " " + p.getSegundoNombre() + " "
                + p.getPrimerApellido() + " " + p.getSegundoApellido(),
                p.getFechaNacimiento()};

            mdTbl.addRow(valores);
        }

    }

}
