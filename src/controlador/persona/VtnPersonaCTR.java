package controlador.persona;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.persona.TipoPersonaBD;
import modelo.persona.TipoPersonaMD;
import vista.persona.FrmPersona;
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
    private final ConectarDB conecta;
    //Para trabajar en los datos de la tabla
    private DefaultTableModel mdTbl;
    private ArrayList<PersonaMD> personas;
    //Para los tipos de persona  
    private ArrayList<TipoPersonaMD> tipos;
    //Para consultar los tipos de persona  
    private final TipoPersonaBD tip;

    public VtnPersonaCTR(VtnPrincipal vtnPrin, VtnPersona vtnPersona, ConectarDB conecta) {
        this.vtnPrin = vtnPrin;
        this.vtnPersona = vtnPersona;
        this.conecta = conecta; 
        this.tip = new TipoPersonaBD(conecta);
        
        vtnPrin.getDpnlPrincipal().add(vtnPersona);
        vtnPersona.show();
        //Iniciamos la clase persona
        dbp = new PersonaBD(conecta);
    }

    public void iniciar() {
     
//Inicializamos el error para que no se vea  
        vtnPersona.getLblError().setVisible(false);
        //Le pasamos accion a los botones  
        vtnPersona.getBtnIngresar().addActionListener(e -> ingresar());
        vtnPersona.getBtnEditar().addActionListener(e -> editar());
        
        vtnPersona.getBtnEliminar().addActionListener(e -> eliminar());
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
        //Llenamos el combo de tipos de persona
        cargarTipoPersona();
        //Le asignamos la accion al combo de tipos de persona  
        vtnPersona.getCmbTipoPersona().addActionListener(e -> filtrarPorTipoPersona());

        //Inciamos el txt de buscador  
        vtnPersona.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar();
            }
        });
    }

    //carge de la lista de modelo a la tabla
    //formatear la tabla de mi modelo
    //obtenemos el modelo de la tabla de la vista y la pones en el modelo por defaulta con un castingt
    public void cargarLista() {
        mdTbl.setRowCount(0);
        if (personas != null) {
            personas.forEach((p) -> {
                Object valores[] = {p.getIdPersona(), p.getIdentificacion(),
                    p.getPrimerNombre() + " " + p.getSegundoNombre() + " "
                    + p.getPrimerApellido() + " " + p.getSegundoApellido(),
                    p.getFechaNacimiento()};

                mdTbl.addRow(valores);
            });
        }
        vtnPersona.getLblResultados().setText(personas.size() + " resultados obtenidos.");
    }

    //Cargamos los tipos de persona para filtrarlos 
    public void cargarTipoPersona() {
        tipos = tip.cargarTipoPersona();
        vtnPersona.getCmbTipoPersona().removeAllItems();
        vtnPersona.getCmbTipoPersona().addItem("Todos");
        tipos.forEach((t) -> {
            vtnPersona.getCmbTipoPersona().addItem(t.getTipo());
        });
    }

    //consultamos por tipo de persona 
    public void filtrarPorTipoPersona() {
        int posTip = vtnPersona.getCmbTipoPersona().getSelectedIndex();
        if (posTip > 0) {
            personas = dbp.cargarPorTipo(tipos.get(posTip - 1).getId());
        } else {
            personas = dbp.cargarPersonas();
        }
        cargarLista();
    }

    //Buscamos persona
    public void buscar() {
        String busqueda = vtnPersona.getTxtBuscar().getText();
        busqueda = busqueda.trim();
        if (busqueda.length() > 2) {
            personas = dbp.buscar(busqueda);
        } else if (busqueda.length() == 0) {
            personas = dbp.cargarPersonas();
        }
        cargarLista();
    }

    //Damos accion al boton de guardar 
    public void ingresar() {
        FrmPersona frmPersona = new FrmPersona();
        FrmPersonaCTR ctrFrm = new FrmPersonaCTR(vtnPrin, frmPersona, conecta);
        ctrFrm.iniciar();
    }

    //Para ejecutar el metodo editar del frm 
    public void editar() {
        int posFila = vtnPersona.getTblPersona().getSelectedRow();
        if (posFila >= 0) {
            vtnPersona.getLblError().setVisible(false);
            FrmPersona frmPersona = new FrmPersona();
            FrmPersonaCTR ctrFrm = new FrmPersonaCTR(vtnPrin, frmPersona, conecta);
            ctrFrm.iniciar();
            //Le pasamos la persona de nuestro lista justo la persona seleccionada
            ctrFrm.editar(personas.get(posFila));
        } else {
            vtnPersona.getLblError().setVisible(true);
        }
    }

    private void eliminar() {


    }

}
