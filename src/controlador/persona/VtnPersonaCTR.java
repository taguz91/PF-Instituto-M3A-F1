package controlador.persona;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
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
    private final VtnPrincipalCTR ctrPrin;
    //Para trabajar en los datos de la tabla
    private DefaultTableModel mdTbl;
    private ArrayList<PersonaMD> personas;
    private final String tipoPersonas[] = {"Docente", "Alumno"};

    public VtnPersonaCTR(VtnPrincipal vtnPrin, VtnPersona vtnPersona, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.vtnPersona = vtnPersona;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Personas");

        vtnPrin.getDpnlPrincipal().add(vtnPersona);
        vtnPersona.show();
        //Iniciamos la clase persona
        dbp = new PersonaBD(conecta);
    }

    public void iniciar() {
        cargarCmbTipoPersonas();
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

        vtnPersona.getCmbTipoPersona().addActionListener(e -> cargarTipoPersona());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Personas");
    }

    private void cargarCmbTipoPersonas() {

        vtnPersona.getCmbTipoPersona().removeAllItems();
        vtnPersona.getCmbTipoPersona().addItem("Todos");
        for (String t : tipoPersonas) {
            vtnPersona.getCmbTipoPersona().addItem(t);
        }

    }

    private void cargarTipoPersona() {
        String tipo = vtnPersona.getCmbTipoPersona().getSelectedItem().toString();

        switch (tipo) {

            case "Docente":
                personas = dbp.cargarDocentes();
                cargarLista();
                break;

            case "Alumno":
                personas = dbp.cargarAlumnos();
                cargarLista();
                break;

            default:
                personas = dbp.cargarPersonas();
                cargarLista();
                break;
        }

    }

    //carge de la lista de modelo a la tabla
    //formatear la tabla de mi modelo
    //obtenemos el modelo de la tabla de la vista y la pones en el modelo por defaulta con un castingt
    public void cargarLista() {
        mdTbl.setRowCount(0);
        vtnPrin.getDpnlPrincipal().setCursor(new Cursor(3));
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
        vtnPrin.getDpnlPrincipal().setCursor(new Cursor(0));
    }

    //consultamos por tipo de persona 
    public void filtrarPorTipoPersona() {
        int posTip = vtnPersona.getCmbTipoPersona().getSelectedIndex();
        if (posTip > 0) {
            //Debemos arreglar la consulta
            //personas = dbp.cargarPorTipo(tipos.get(posTip - 1).getId());
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
        FrmPersonaCTR ctrFrm = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, ctrPrin);
        ctrFrm.iniciar();
    }

    //Para ejecutar el metodo editar del frm 
    public void editar() {
        int posFila = vtnPersona.getTblPersona().getSelectedRow();
        if (posFila >= 0) {
            vtnPersona.getLblError().setVisible(false);
            FrmPersona frmPersona = new FrmPersona();
            FrmPersonaCTR ctrFrm = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, ctrPrin);
            ctrFrm.iniciar();
            //Le pasamos la persona de nuestro lista justo la persona seleccionada
            ctrFrm.editar(personas.get(posFila));
        } else {
            vtnPersona.getLblError().setVisible(true);
        }
    }

    private void eliminar() {
        int posFila = vtnPersona.getTblPersona().getSelectedRow();
        if (posFila >= 0) {
            PersonaMD persona;
            System.out.println(Integer.valueOf(vtnPersona.getTblPersona().getValueAt(posFila, 0).toString()));
            persona = dbp.buscarPersona(Integer.valueOf(vtnPersona.getTblPersona().getValueAt(posFila, 0).toString()));
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar a esta Persona ", " Elinimar Persona", dialog);
            if (result == 0) {
                if (dbp.eliminarPersonaId(persona.getIdPersona()) == true) {
                    JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                    //cargarLista();
                    cargarTipoPersona();
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR A LA PERSONA");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA PARA ELIMINAR A LA PERSONA");
        }
    }

}
