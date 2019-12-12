package controlador.persona;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import utils.CONS;
import modelo.estilo.TblEstilo;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.persona.FrmPersona;
import vista.persona.VtnPersona;

/**
 *
 * @author Johnny
 */
public class VtnPersonaCTR extends DVtnCTR {

    private final PersonaBD dbp;
    private final VtnPersona vtnPersona;
    //Para trabajar en los datos de la tabla
    private ArrayList<PersonaMD> personas;
    private final String tipoPersonas[] = {"Docente", "Alumno"};
    //Para permisos
    String[] accesos = {"Personas-Ingresar", "Personas-Editar", "Personas-Eliminar", "Personas-Estado"};

    public VtnPersonaCTR(VtnPersona vtnPersona, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnPersona = vtnPersona;
        //Iniciamos la clase persona
        dbp = new PersonaBD(ctrPrin.getConecta());
    }

    public void iniciar() {
        ctrPrin.agregarVtn(vtnPersona);

        vtnPersona.getChBx_PerEliminada().addActionListener(e -> {
            if (vtnPersona.getChBx_PerEliminada().isSelected()) {
                filtrarEliminados();
                vtnPersona.getBtnEditar().setEnabled(false);
                vtnPersona.getBtnEliminar().setEnabled(false);
            } else {
                cargarTipoPersona();
                vtnPersona.getBtnEditar().setEnabled(true);
                vtnPersona.getBtnEliminar().setEnabled(true);
            }
        });

        vtnPersona.getBtnReportePersona().setEnabled(false);
        cargarCmbTipoPersonas();
        //Inicializamos el error para que no se vea
        vtnPersona.getLblError().setVisible(false);
        //Le pasamos accion a los botones
        vtnPersona.getBtnIngresar().addActionListener(e -> ingresar());
        vtnPersona.getBtnEditar().addActionListener(e -> editar());

        vtnPersona.getBtnEliminar().addActionListener(e -> eliminar());
        String titulo[] = {"ID", "Identificacion", "Nombre Completo", "Fecha Nacimiento", "Celular", "Teléfono", "Correo"};
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
                if (e.getKeyCode() == 10) {
                    buscar();
                }
            }
        });
        //Validacion del buscador
        vtnPersona.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnPersona.getTxtBuscar(),
                vtnPersona.getBtnBuscar()));
        //vALIDACION BOTONE DE REPORTES
        vtnPersona.getTblPersona().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                validarBotonesReportes();
            }
        });
        vtnPersona.getBtnReportePersona().addActionListener(e -> llamaReportePersona());
        vtnPersona.getCmbTipoPersona().addActionListener(e -> cargarTipoPersona());
        vtnPersona.getBtnEditarIdentificacion().addActionListener(e -> editarIdentificacion());

        InitPermisos();
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

    public void filtrarEliminados() {
        List<PersonaMD> peliminados;
        peliminados = dbp.filtrarEliminados();
        mdTbl.setRowCount(0);
        ctrPrin.getVtnPrin().getDpnlPrincipal().setCursor(new Cursor(3));
        if (peliminados != null) {
            peliminados.forEach((p) -> {
                Object valores[] = {p.getIdPersona(), p.getIdentificacion(),
                    p.getPrimerNombre() + " " + p.getSegundoNombre() + " "
                    + p.getPrimerApellido() + " " + p.getSegundoApellido(),
                    p.getFechaNacimiento()};
                mdTbl.addRow(valores);
            });
            vtnPersona.getLblResultados().setText(peliminados.size() + " resultados obtenidos.");
        } else {
            vtnPersona.getLblResultados().setText("0 resultados obtenidos.");
        }

    }

    //carge de la lista de modelo a la tabla
    //formatear la tabla de mi modelo
    //obtenemos el modelo de la tabla de la vista y la pones en el modelo por defaulta con un castingt
    public void cargarLista() {
        mdTbl.setRowCount(0);
        ctrPrin.getVtnPrin().getDpnlPrincipal().setCursor(new Cursor(3));
        if (personas != null) {
            personas.forEach((p) -> {
                Object valores[] = {p.getIdPersona(), p.getIdentificacion(),
                    p.getPrimerNombre() + " " + p.getSegundoNombre() + " "
                    + p.getPrimerApellido() + " " + p.getSegundoApellido(),
                    p.getFechaNacimiento(), p.getCelular(), p.getTelefono(),
                    p.getCorreo()};
                mdTbl.addRow(valores);
            });
            vtnPersona.getLblResultados().setText(personas.size() + " resultados obtenidos.");
        }

        ctrPrin.getVtnPrin()
                .getDpnlPrincipal().setCursor(new Cursor(0));
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
        ctrPrin.abrirFrmPersona();
        vtnPersona.dispose();
        ctrPrin.cerradoJIF();
    }

    //Para ejecutar el metodo editar del frm
    public void editar() {
        posFila = vtnPersona.getTblPersona().getSelectedRow();
        if (posFila >= 0) {
            vtnPersona.getLblError().setVisible(false);
            FrmPersona frmPersona = new FrmPersona();
            FrmPersonaCTR ctrFrm = new FrmPersonaCTR(frmPersona, ctrPrin);
            ctrFrm.iniciar();
            //Le pasamos la persona de nuestro lista justo la persona seleccionada
            PersonaMD perEditar = dbp.buscarPersona(
                    Integer.parseInt(vtnPersona.getTblPersona().getValueAt(posFila, 0).toString()));
            ctrFrm.editar(perEditar);
            cargarTipoPersona();
        } else {
            vtnPersona.getLblError().setVisible(true);
        }
    }

    private void editarIdentificacion() {
        posFila = vtnPersona.getTblPersona().getSelectedRow();
        if (posFila >= 0) {
            System.out.println("Este es el ID Persona: " + personas.get(posFila).getIdPersona());
            System.out.println("Nombre Persona " + personas.get(posFila).getNombreCompleto());
            vtnPersona.getLblError().setVisible(false);
            JDEditarIdentificacionCTR ctr = new JDEditarIdentificacionCTR(ctrPrin, vtnPersona.getTblPersona().getValueAt(posFila, 0).toString(), vtnPersona.getTblPersona().getValueAt(posFila, 2).toString());
            ctr.iniciar();

            PersonaMD perEditar = dbp.buscarPersona(
                    Integer.parseInt(vtnPersona.getTblPersona().getValueAt(posFila, 0).toString()));
            ctr.editarIdentificacion(perEditar);
            cargarTipoPersona();

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila ");
        }

    }

    private void eliminar() {
        posFila = vtnPersona.getTblPersona().getSelectedRow();
        if (posFila >= 0) {
            PersonaMD persona;
            System.out.println(Integer.valueOf(vtnPersona.getTblPersona().getValueAt(posFila, 0).toString()));
            persona = dbp.buscarPersona(Integer.valueOf(vtnPersona.getTblPersona().getValueAt(posFila, 0).toString()));
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar a \n"
                    + vtnPersona.getTblPersona().getValueAt(posFila, 2) + "?", " Eliminar Persona", dialog);
            if (result == 0) {
                if (dbp.eliminarPersonaId(persona.getIdPersona()) == true) {
                    JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                    //cargarLista();
                    cargarTipoPersona();
                    vtnPersona.getTxtBuscar().setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR A LA PERSONA");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA PARA ELIMINAR A LA PERSONA");
        }
    }

    public void llamaReportePersona() {
        posFila = vtnPersona.getTblPersona().getSelectedRow();
        if (posFila >= 0) {
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/repPersona.jasper"));
                Map parametro = new HashMap();
                parametro.put("cedula", String.valueOf(mdTbl.getValueAt(posFila, 1)));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Persona");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione una fila para ver el reporte.");
        }

    }

    private void InitPermisos() {
        vtnPersona.getBtnEditarIdentificacion().getAccessibleContext().setAccessibleName("Personas-Editar Identificacion");
        vtnPersona.getBtnEliminar().getAccessibleContext().setAccessibleName("Personas-Eliminar");
        vtnPersona.getBtnEditar().getAccessibleContext().setAccessibleName("Personas-Editar");
        vtnPersona.getBtnIngresar().getAccessibleContext().setAccessibleName("Personas-Ingresar");
        vtnPersona.getChBx_PerEliminada().getAccessibleContext().setAccessibleName("Personas-Ver Personas Eliminadas");
        vtnPersona.getBtnReportePersona().getAccessibleContext().setAccessibleName("Personas-Reporte-Persona");
        
        CONS.activarBtns(vtnPersona.getBtnEditarIdentificacion(), vtnPersona.getBtnEliminar(), 
                vtnPersona.getBtnEditar(), vtnPersona.getBtnIngresar(), vtnPersona.getChBx_PerEliminada(),
                vtnPersona.getBtnReportePersona());

    }

    public void validarBotonesReportes() {
        
        if (CONS.getPermisos().contains("Personas-Reporte-Persona")) {
            
            int selecTabl = vtnPersona.getTblPersona().getSelectedRow();
            if (selecTabl >= 0) {
                vtnPersona.getBtnReportePersona().setEnabled(true);
            } else {
                vtnPersona.getBtnReportePersona().setEnabled(false);
            }            
            
        }
        
        
    }

}
