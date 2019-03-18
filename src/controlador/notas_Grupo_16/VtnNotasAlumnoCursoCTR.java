package controlador.notas_Grupo_16;

import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.jornada.JornadaBD;
import modelo.jornada.JornadaMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.UsuarioBD;
import vista.notas_Grupo_16.VtnNotasAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Alejandro
 */
public class VtnNotasAlumnoCursoCTR {

    private VtnPrincipal desktop;
    private VtnNotasAlumnoCurso vista;
    private AlumnoCursoBD modelo;
    private UsuarioBD usuario;
    //Conexion
    private ConectarDB conexion;

    //MODELOS
    private PersonaBD persona;
    private JornadaBD jornada;
    private PeriodoLectivoBD periodoLectivo;
    private MateriaBD materia;
    private DocenteBD docente;
    private CarreraBD carrera;
    private PersonaBD alumnoPersona;

    //LISTAS
    private List<PersonaMD> listaPersona;
    private List<JornadaMD> listaJornadas;
    private List<PeriodoLectivoMD> listaPeriodoLectivo;
    private List<MateriaMD> listaMaterias;
    private List<DocenteMD> listaDocente;
    private List<CarreraMD> listaCarreras;
    private List<AlumnoCursoMD> listaNotas;
    private List<PersonaMD> listaAlumnosPersonas;

    //TABLA
    private DefaultTableModel tablaNotas;

    public VtnNotasAlumnoCursoCTR(VtnPrincipal desktop, VtnNotasAlumnoCurso vista, AlumnoCursoBD modelo, UsuarioBD usuario, ConectarDB conexion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.usuario = usuario;
        this.conexion = conexion;
    }

    /*
        INITS
     */
    public void Init() {
        //INICIALIZANDO MODELOS
        persona = new PersonaBD(conexion);
        jornada = new JornadaBD(conexion);
        periodoLectivo = new PeriodoLectivoBD(conexion);
        materia = new MateriaBD(conexion);
        docente = new DocenteBD(conexion);
        carrera = new CarreraBD(conexion);
        alumnoPersona = new PersonaBD(conexion);

        //CONSULTAS
        listaPersona = persona.selectWhereUsername(usuario.getUsername());
        listaJornadas = jornada.cargarJornadas();
        listaPeriodoLectivo = periodoLectivo.cargarPeriodos();
        listaDocente = docente.selectWhereUsername(usuario.getUsername());

        listaMaterias = materia.selectWhereDocenteID(listaDocente.get(0).getIdDocente());

        listaAlumnosPersonas = alumnoPersona.cargarAlumnos();

        listaNotas = modelo.cargarAlumnosCursos();

        //CARGA DE COMBO BOXES
        cargarCmbDocente();
        cargarCmbJornadas();
        cargarCmbPeriodo();
        cargarMaterias();
        cargarCmbCarreras();

        //INICIALIZANDO LA TABLA
        tablaNotas = (DefaultTableModel) vista.getTbl_notas().getModel();
        //CARGAR TABLA
        cargarTabla();

        InitEventos();
        try {
            desktop.getDpnlPrincipal().add(vista);
            vista.show();
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnNotasAlumnoCursoCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        tablaNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    TableModel modelo = vista.getTbl_notas().getModel();

                    vista.getTbl_notas().setModel(calcularNotaFinal(modelo));

                    active = false;
                }

            }
        });

    }

    /*
        METODOS DE APOYO
     */
    private TableModel calcularNotaFinal(TableModel datos) {

        int fila = vista.getTbl_notas().getSelectedRow();

        String valor = null;

        double notaInterCicloSuma = 0;
        double notaInterCiclo = 0;

        try {

            valor = String.valueOf((Double.valueOf((String) datos.getValueAt(fila, 4))) + (Double.valueOf((String) datos.getValueAt(fila, 5))));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        datos.setValueAt(valor, fila, 6);

        return datos;
    }

    private static double validarString(String numero) {

        if (numero.equals("0.0")) {
            System.out.println(numero + "<----------------------------");
            return 0;
        } else {
            return Double.parseDouble(numero);
        }

    }

    private void cargarCmbDocente() {

        listaPersona.stream()
                .forEach(obj -> {
                    vista.getCmb_docente().addItem(obj.getPrimerNombre());
                });
    }

    private void cargarCmbJornadas() {

        listaJornadas.stream()
                .forEach(obj -> {
                    vista.getCmb_jornada().addItem(obj.getNombre());
                });

    }

    private void cargarCmbPeriodo() {
        listaPeriodoLectivo.stream()
                .forEach(obj -> {

                    vista.getCmb_periodolectivo().addItem(obj.getNombre_PerLectivo());

                });
    }

    private void cargarMaterias() {

        listaMaterias.stream()
                .forEach(obj -> {

                    vista.getCmb_asignatura().addItem(obj.getNombre());

                });

    }

    private void cargarCmbCarreras() {

        listaMaterias.stream()
                .forEach(objMateria -> {

                    listaCarreras = carrera.selectWhereIdMateria(objMateria.getId());

                    listaCarreras.stream()
                            .forEach(objCarrera -> {
                                vista.getCmb_carrera().addItem(objCarrera.getNombre());
                            });
                });

    }

    private void cargarTabla() {

        tablaNotas.setRowCount(0);

        listaNotas.stream()
                .forEach(objNota -> {

                    listaAlumnosPersonas.stream()
                            .forEach(objAlumnoPersona -> {

                                if (objNota.getAlumno().getIdPersona() == objAlumnoPersona.getIdPersona()) {
                                    tablaNotas.addRow(new Object[]{
                                        tablaNotas.getDataVector().size() + 1,
                                        objAlumnoPersona.getIdentificacion(),
                                        objAlumnoPersona.getPrimerApellido(),
                                        objAlumnoPersona.getPrimerNombre(),
                                        objNota.getNota1Parcial(),
                                        objNota.getNotaExamenInter(),
                                        0.0,
                                        objNota.getNota2Parcial(),
                                        objNota.getNotaExamenFinal(),
                                        objNota.getNotaExamenSupletorio(),
                                        objNota.getNotaFinal(),
                                        objNota.getEstado(),
                                        objNota.getNumFalta(),
                                        0.0
                                    });

                                }

                            });
                });

    }


    /*
        PROCESADORES DE EVENTOS
     */
}
