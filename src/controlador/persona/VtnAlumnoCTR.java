package controlador.persona;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.JOptionPane;
import utils.CONS;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.validaciones.TxtVBuscador;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.persona.FrmAlumno;
import vista.persona.FrmPersona;
import vista.persona.VtnAlumno;
import vista.persona.VtnMatRetiradas;

public class VtnAlumnoCTR extends DVtnCTR {

    private final VtnAlumno vtnAlumno;

    private FrmAlumno frmAlumno;
    private final AlumnoBD ABD = AlumnoBD.single();
    public static AlumnoMD mdAlumno;

    // Datos  
    private List<List<String>> alumnos, todosAlumnos;

    public VtnAlumnoCTR(VtnAlumno vtnAlumno, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnAlumno = vtnAlumno;
    }

    public void iniciar() {
        ctrPrin.agregarVtn(vtnAlumno);
        iniciarComponentes(); //Inicia los componentes
        //Asignación de eventos a los componentes de la Ventana
        vtnAlumno.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    buscaIncremental(vtnAlumno.getTxtBuscar().getText().toUpperCase());
                }
            }
        });
        vtnAlumno.getBtnEliminar().addActionListener(e -> eliminarAlumno());
        vtnAlumno.getBtnEditar().addActionListener(e -> editarAlumno());
        vtnAlumno.getBtnIngresar().addActionListener(e -> abrirFrmAlumno());
        vtnAlumno.getBtn_Materias().addActionListener(e -> abrirVtnMaterias());
        vtnAlumno.getCbx_Filtrar().addActionListener(e -> {
            String palabra = vtnAlumno.getCbx_Filtrar().getSelectedItem().toString();
            switch (palabra) {
                case "ALUMNOS ELMINADOS":
                    llenarElimanados();
                    vtnAlumno.getBtn_Materias().setVisible(false);
                    vtnAlumno.getBtnEliminar().setEnabled(false);
                    vtnAlumno.getBtnEditar().setEnabled(false);
                    break;
                case "ALUMNOS RETIRADOS":
                    llenarRetirados();
                    vtnAlumno.getBtn_Materias().setVisible(true);
                    vtnAlumno.getBtnEliminar().setEnabled(true);
                    vtnAlumno.getBtnEditar().setEnabled(true);
                    break;
                default:
                    alumnos = todosAlumnos;
                    llenarTbl(alumnos);
                    vtnAlumno.getBtn_Materias().setVisible(false);
                    vtnAlumno.getBtnEliminar().setEnabled(true);
                    vtnAlumno.getBtnEditar().setEnabled(true);
                    break;
            }
        });
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        //Validacion del buscador
        vtnAlumno.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnAlumno.getTxtBuscar(),
                vtnAlumno.getBtnBuscar()));
        InitPermisos();

        vtnAlumno.getBtnReporteAlumnos().addActionListener(e -> ListaDeAlumnos());

        iniciarTbl();
    }

    private void iniciarTbl() {
        String[] titulo = {
            "ID",
            "Identificacion",
            "Nombres",
            "Apellidos",
            "Correo",
            "Celular",
            "Carrera",
            "Curso"
        };
        mdTbl = iniciarTbl(vtnAlumno.getTblAlumno(), titulo);
        alumnos = ABD.getAllTable();
        todosAlumnos = alumnos;
        modelo.estilo.TblEstilo.ocualtarID(vtnAlumno.getTblAlumno());
        llenarTbl(alumnos);
        listenerTxtBuscarLocal(
                vtnAlumno.getTxtBuscar(),
                vtnAlumno.getBtnBuscar(),
                buscarFun()
        );
    }

    //Muestra el Formulario de Registro de Alumno
    public void abrirFrmAlumno() {
        frmAlumno = new FrmAlumno();
        ctrPrin.abrirFrmAlumno();
        vtnAlumno.dispose();
        ctrPrin.cerradoJIF();
    }

    public void abrirVtnMaterias() {
        mdAlumno = capturarFila();
        if (mdAlumno == null) {
            JOptionPane.showMessageDialog(null, "Advertencia!! Seleccione a un Alumno");
        } else {
            VtnMatRetiradas m = new VtnMatRetiradas(ctrPrin.getVtnPrin(), false);
            VtnMatReprobadasCTR materias = new VtnMatReprobadasCTR(this, vtnAlumno, ctrPrin);
            materias.iniciarVentana();
        }
    }

    public void iniciarComponentes() {
        vtnAlumno.getBtn_Materias().setVisible(false);
    }

    public void llenarElimanados() {
        mdTbl.setRowCount(0);
        List<PersonaMD> lista = ABD.llenarEliminados();
        int columnas = mdTbl.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            mdTbl.addRow(new Object[columnas]);
            vtnAlumno.getTblAlumno().setValueAt(String.valueOf(lista.get(i).getIdPersona()), i, 0);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
                    + " " + lista.get(i).getSegundoNombre(), i, 2);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                    + " " + lista.get(i).getSegundoApellido(), i, 3);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
        }
        vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }

    public void llenarRetirados() {
        mdTbl.setRowCount(0);
        List<AlumnoMD> lista = ABD.filtrarRetirados();
        int columnas = mdTbl.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            mdTbl.addRow(new Object[columnas]);
            vtnAlumno.getTblAlumno().setValueAt(String.valueOf(lista.get(i).getIdPersona()), i, 0);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
                    + " " + lista.get(i).getSegundoNombre(), i, 2);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                    + " " + lista.get(i).getSegundoApellido(), i, 3);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
        }
        vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }

    //Filtra los datos de los Alumnos buscados ya sea por su Cédula o por su Nombres
    public void buscaIncremental(String aguja) {
        if (modelo.validaciones.Validar.esLetrasYNumeros(aguja)) {
            mdTbl.setRowCount(0);
            List<PersonaMD> lista = ABD.capturarPersona(aguja);
            int columnas = mdTbl.getColumnCount();
            for (int i = 0; i < lista.size(); i++) {
                mdTbl.addRow(new Object[columnas]);
                vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdPersona(), i, 0);
                vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
                vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
                        + " " + lista.get(i).getSegundoNombre(), i, 2);
                vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                        + " " + lista.get(i).getSegundoApellido(), i, 3);
                vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
            }
            vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
        } else {
            if (!aguja.equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese un caracter válido para la Búsqueda");
            } else {
                alumnos = todosAlumnos;
                llenarTbl(alumnos);
            }
        }
    }

    private void llenarTbl(List<List<String>> alumnos) {
        mdTbl.setRowCount(0);
        alumnos.forEach(a -> {
            mdTbl.addRow(a.toArray());
        });
        vtnAlumno.getLblResultados().setText(alumnos.size() + " Resultados obtenidos.");
    }

    //Captura los datos del Alumno en un objeto
    public AlumnoMD capturarFila() {
        int i = vtnAlumno.getTblAlumno().getSelectedRow();
        if (i >= 0) {
            AlumnoMD alumno;
            alumno = ABD.buscarPersona(Integer.valueOf(vtnAlumno.getTblAlumno().getValueAt(i, 0).toString()));
            System.out.println("ID: " + vtnAlumno.getTblAlumno().getValueAt(i, 0).toString());
            return alumno;
        } else {
            return null;
        }
    }

    //Muestra el formulario de Alumno con los datos de algún Alumno seleccionado y permite su edición
    public void editarAlumno() {
        AlumnoMD al = capturarFila();
        if (al != null) {
            int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una Opcion",
                    "Selector de Opciones", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,// null para icono por defecto.
                    new Object[]{"Editar Datos Personales", "Editar Datos de Alumno"}, "Editar Datos de Alumno");
            if (seleccion == 1) {
                frmAlumno = new FrmAlumno();
                FrmAlumnoCTR ctrFrm = new FrmAlumnoCTR(frmAlumno, ctrPrin);
                ctrFrm.iniciar();
                ctrFrm.editar(al);
                vtnAlumno.dispose();
                ctrPrin.cerradoJIF();

            } else if (seleccion == 0) {
                PersonaBD PBD = PersonaBD.single();
                FrmPersona frmPersona = new FrmPersona();
                PersonaMD persona;
                persona = PBD.buscarPersona(al.getIdPersona());
                FrmPersonaCTR ctrPers = new FrmPersonaCTR(frmPersona, ctrPrin);
                ctrPers.iniciar();
                ctrPers.editar(persona);
                if (modelo.validaciones.Validar.esNumeros(persona.getIdentificacion()) == true) {
                    frmPersona.getCmbTipoId().setSelectedItem("CEDULA");
                } else {
                    frmPersona.getCmbTipoId().setSelectedItem("PASAPORTE");
                }
                frmPersona.getTxtIdentificacion().setText(persona.getIdentificacion());
                vtnAlumno.dispose();
                ctrPrin.cerradoJIF();

            }
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia!! Seleccione una fila");
        }

    }

    //Permite la eliminación de algún Alumno seleccionado en la tabla
    public void eliminarAlumno() {
        AlumnoMD alumno;
        if (capturarFila() == null) {
            JOptionPane.showMessageDialog(null, "No se puede Eliminar si no selecciona a un Alumno");
        } else {
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar a este Estudiante? ", " Eliminar Estudiante ", dialog);
            if (result == 0) {
                alumno = capturarFila();
                String observacion = JOptionPane.showInputDialog("¿Por que motivo elimina este alumno?");
                if (observacion != null) {
                    alumno.setObservacion(observacion.toUpperCase());
                    if (ABD.eliminarAlumno(alumno, alumno.getIdPersona()) == true) {
                        JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                        buscaIncremental("");
                    } else {
                        JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL ALUMNO");
                    }
                }
            }
        }
    }

    //Muestra los reportes con todos los Alumnos registrados
    public void ListaDeAlumnos() {
        JasperReport jr;
        String path = "/vista/reportes/repListaAlumnos.jasper";
        try {
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            CON.mostrarReporte(jr, null, "Lista de Docentes");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

    private void InitPermisos() {
        vtnAlumno.getBtnEliminar().getAccessibleContext().setAccessibleName("Alumnos-Eliminar");
        vtnAlumno.getBtnEditar().getAccessibleContext().setAccessibleName("Alumnos-Editar");
        vtnAlumno.getBtnIngresar().getAccessibleContext().setAccessibleName("Alumnos-Ingresar");
        vtnAlumno.getBtnReporteAlumnos().getAccessibleContext().setAccessibleName("Alumnos-Reporte-Alumnos");

        CONS.activarBtns(vtnAlumno.getBtnEliminar(), vtnAlumno.getBtnEditar(), vtnAlumno.getBtnIngresar(),
                vtnAlumno.getBtnReporteAlumnos());
    }

    // Nuevos metodos  
    private Function<String, Void> buscarFun() {
        return t -> {
            buscar(t.toUpperCase().trim());
            return null;
        };
    }

    private void buscar(String aguja) {
        alumnos = new ArrayList<>();
        todosAlumnos.forEach(ta -> {
            if (ta.get(1).contains(aguja)
                    || ta.get(2).contains(aguja)
                    || ta.get(3).contains(aguja)
                    || ta.get(4).contains(aguja)
                    || ta.get(5).contains(aguja)
                    || (ta.get(2) + " " + ta.get(3)).contains(aguja)) {
                alumnos.add(ta);
            }
        });
        llenarTbl(alumnos);
    }

}
