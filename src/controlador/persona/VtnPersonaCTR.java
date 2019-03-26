package controlador.persona;

import controlador.carrera.VtnCarreraCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.estilo.TblEstilo;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
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
    private final RolMD permisos;
    //Para trabajar en los datos de la tabla
    private DefaultTableModel mdTbl;
    private ArrayList<PersonaMD> personas;
    private final String tipoPersonas[] = {"Docente", "Alumno"};

    public VtnPersonaCTR(VtnPrincipal vtnPrin, VtnPersona vtnPersona,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnPersona = vtnPersona;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Personas");
        ctrPrin.setIconJIFrame(vtnPersona);
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
        TblEstilo.ocualtarID(vtnPersona.getTblPersona());
        //Cambiamos el tamaño de las columnas  
        TblEstilo.columnaMedida(vtnPersona.getTblPersona(), 1, 100);
        TblEstilo.columnaMedida(vtnPersona.getTblPersona(), 3, 120);

        //Llenamos el combo de tipos de persona
        cargarTipoPersona();

        //Inciamos el txt de buscador  
        vtnPersona.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar();
            }
        });
        //Validacion del buscador
        vtnPersona.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnPersona.getTxtBuscar(),
                vtnPersona.getBtnBuscar()));
        vtnPersona.getBtnReportePersona().addActionListener(e -> llamaReportePersona());
        vtnPersona.getCmbTipoPersona().addActionListener(e -> cargarTipoPersona());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Personas");
        //Detenemos la animacion de carga.
        //ctrPrin.carga.detener();
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

    //Buscamos persona
    public void buscar() {
        String busqueda = vtnPersona.getTxtBuscar().getText();
        busqueda = busqueda.trim();
        if (Validar.esLetrasYNumeros(busqueda)) {
            if (busqueda.length() > 2) {
                personas = dbp.buscar(busqueda);
            } else if (busqueda.length() == 0) {
                personas = dbp.cargarPersonas();
            }
            cargarLista();
        }

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
            PersonaMD perEditar = dbp.buscarPersona(
                    Integer.parseInt(vtnPersona.getTblPersona().getValueAt(posFila, 0).toString()));
            ctrFrm.editar(perEditar);
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
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar a esta Persona ", " Eliminar Persona", dialog);
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

    public void llamaReportePersona() {
        JasperReport jr;
        String path = "./src/vista/reportes/repPersona.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            Map parametro = new HashMap();
            parametro.put("cedula", String.valueOf(mdTbl.getValueAt(vtnPersona.getTblPersona().getSelectedRow(), 1)));
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Materias por Carrera");

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void InitPermisos() {
        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {

//            if (obj.getNombre().equals("USUARIOS-Agregar")) {
//                vtnCarrera.getBtnIngresar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Editar")) {
//                vista.getBtnEditar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Eliminar")) {
//                vista.getBtnEliminar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-AsignarRoles")) {
//                vista.getBtnAsignarRoles().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-VerRoles")) {
//                vista.getBtnVerRoles().setEnabled(true);
//            }
        }
    }
}
