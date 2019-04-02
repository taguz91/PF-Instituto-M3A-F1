package controlador.persona;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.persona.FrmAlumno;
import vista.persona.FrmPersona;
import vista.persona.VtnAlumno;
import vista.persona.VtnDocente;
import vista.principal.VtnPrincipal;

public class VtnAlumnoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnAlumno vtnAlumno;
    private final ConectarDB conecta; //Conexión con la Base de Datos
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos; //Rol que tiene el Usuario

    private DefaultTableModel mdTbl;
    private FrmAlumno frmAlumno;
    private final AlumnoBD bdAlumno;
    private String[] alumnoPermisoEstado = {"Alumnos-Ingresar","Alumnos-Editar","Alumnos-Eliminar","Alumnos-"};

    public VtnAlumnoCTR(VtnPrincipal vtnPrin, VtnAlumno vtnAlumno,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnAlumno = vtnAlumno;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Alumnos");
        ctrPrin.setIconJIFrame(vtnAlumno);
        vtnPrin.getDpnlPrincipal().add(vtnAlumno);
        vtnAlumno.show();
        //Inicializamos la clase de alumno  
        bdAlumno = new AlumnoBD(conecta);
    }

    public void iniciar() {
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                buscaIncremental(vtnAlumno.getTxtBuscar().getText().toUpperCase());
            }
        };

        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vtnAlumno.setVisible(false);
            }

        };

        ocultarAtributo(); //Oculta la Columna del Id de Persona
        llenarTabla(); //LLena la tabla con los datos de los Alumnos registrados
        //Asignación de eventos a los componentes de la Ventana
        vtnAlumno.getTxtBuscar().addKeyListener(kl);
        vtnAlumno.getBtnEliminar().addActionListener(e -> eliminarAlumno());
        vtnAlumno.getBtnEditar().addActionListener(e -> editarAlumno());
        vtnAlumno.getBtnIngresar().addActionListener(e -> abrirFrmAlumno());

        //Cuando termina de cargar todo se le vuelve a su estado normal.
        //Validacion del buscador  
        vtnAlumno.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnAlumno.getTxtBuscar(),
                vtnAlumno.getBtnBuscar()));

        vtnAlumno.getBtnReporteAlumnos().addActionListener(e -> llamaReporteAlumno());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Alumnos");
    }

    //Muestra el Formulario de Registro de Alumno
    public void abrirFrmAlumno() {
        frmAlumno = new FrmAlumno();
        ctrPrin.abrirFrmAlumno();
        vtnAlumno.dispose();
        ctrPrin.cerradoJIF();
    }

    //Oculta la columna del Id de Persona
    public void ocultarAtributo() {
        modelo.estilo.TblEstilo.ocualtarID(vtnAlumno.getTblAlumno());
    }

    //Rellena con datos de los Estudiantes registrados en el Sistema
    public void llenarTabla() {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnAlumno.getTblAlumno().getModel();
        for (int i = vtnAlumno.getTblAlumno().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<PersonaMD> lista = bdAlumno.llenarTabla();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
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
        boolean numero = true;
        if (modelo.validaciones.Validar.esNumeros(aguja) == true) {
            if (aguja.length() >= 5) {
                DefaultTableModel modelo_Tabla;
                modelo_Tabla = (DefaultTableModel) vtnAlumno.getTblAlumno().getModel();
                for (int i = vtnAlumno.getTblAlumno().getRowCount() - 1; i >= 0; i--) {
                    modelo_Tabla.removeRow(i);
                }
                List<PersonaMD> lista = bdAlumno.capturarPersona(aguja);
                int columnas = modelo_Tabla.getColumnCount();
                for (int i = 0; i < lista.size(); i++) {
                    modelo_Tabla.addRow(new Object[columnas]);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdPersona(), i, 0);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
                            + " " + lista.get(i).getSegundoNombre(), i, 2);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                            + " " + lista.get(i).getSegundoApellido(), i, 3);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
                }
                vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
            }
        } else if (modelo.validaciones.Validar.esLetras(aguja) == true) {
            if (aguja.length() >= 4) {
                DefaultTableModel modelo_Tabla;
                modelo_Tabla = (DefaultTableModel) vtnAlumno.getTblAlumno().getModel();
                for (int i = vtnAlumno.getTblAlumno().getRowCount() - 1; i >= 0; i--) {
                    modelo_Tabla.removeRow(i);
                }
                List<PersonaMD> lista = bdAlumno.capturarPersona(aguja);
                int columnas = modelo_Tabla.getColumnCount();
                for (int i = 0; i < lista.size(); i++) {
                    modelo_Tabla.addRow(new Object[columnas]);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdPersona(), i, 0);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
                            + " " + lista.get(i).getSegundoNombre(), i, 2);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                            + " " + lista.get(i).getSegundoApellido(), i, 3);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
                }
                vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
            }
        } else {
            if (!aguja.equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese un caracter válido para la Búsqueda");
            } else {
                DefaultTableModel modelo_Tabla;
                modelo_Tabla = (DefaultTableModel) vtnAlumno.getTblAlumno().getModel();
                for (int i = vtnAlumno.getTblAlumno().getRowCount() - 1; i >= 0; i--) {
                    modelo_Tabla.removeRow(i);
                }
                List<PersonaMD> lista = bdAlumno.capturarPersona(aguja);
                int columnas = modelo_Tabla.getColumnCount();
                for (int i = 0; i < lista.size(); i++) {
                    modelo_Tabla.addRow(new Object[columnas]);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdPersona(), i, 0);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
                            + " " + lista.get(i).getSegundoNombre(), i, 2);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                            + " " + lista.get(i).getSegundoApellido(), i, 3);
                    vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
                }
                vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
            }
        }
    }

    //Captura los datos del Alumno en un objeto
    public AlumnoMD capturarFila() {
        int i = vtnAlumno.getTblAlumno().getSelectedRow();
        if (i >= 0) {
            AlumnoMD alumno;
            alumno = bdAlumno.buscarPersona(Integer.valueOf(vtnAlumno.getTblAlumno().getValueAt(i, 0).toString()));
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
                FrmAlumnoCTR ctrFrm = new FrmAlumnoCTR(vtnPrin, frmAlumno, conecta, ctrPrin, permisos);
                ctrFrm.iniciar();
                ctrFrm.editar(al);
                vtnAlumno.dispose();
                ctrPrin.cerradoJIF();

            } else if (seleccion == 0) {
                PersonaBD extraer = new PersonaBD(conecta);
                FrmPersona frmPersona = new FrmPersona();
                PersonaMD persona;
                persona = extraer.buscarPersona(al.getIdPersona());
                FrmPersonaCTR ctrPers = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, ctrPrin);
                ctrPers.iniciar();
                ctrPers.editar(persona);
                vtnAlumno.dispose();
                ctrPrin.cerradoJIF();

            }
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia!! Seleccione una fila");
        }

    }

    //Permite la eliminación de algún Alumno seleccionado en la tabla
    public void eliminarAlumno() {
        AlumnoMD alumno = new AlumnoMD();
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
                    if (bdAlumno.eliminarAlumno(alumno, alumno.getIdPersona()) == true) {
                        JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                        llenarTabla();
                    } else {
                        JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL ALUMNO");
                    }
                }
            }
        }
    }

    //Muestra los reportes con todos los Alumnos registrados
    public void llamaReporteAlumno() {
        JasperReport jr;
        String path = "./src/vista/reportes/repAlumnos.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, null, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Alumnos");

        } catch (JRException ex) {
            Logger.getLogger(VtnAlumnoCTR.class.getName()).log(Level.SEVERE, null, ex);
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
