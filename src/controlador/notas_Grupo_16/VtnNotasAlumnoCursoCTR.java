package controlador.notas_Grupo_16;

import controlador.Libraries.Validaciones;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import modelo.curso.CursoBD;
import modelo.jornada.JornadaBD;
import modelo.materia.MateriaBD;
import modelo.periodolectivo.PeriodoLectivoBD;
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

    private final VtnPrincipal desktop;
    private final VtnNotasAlumnoCurso vista;
    private final AlumnoCursoBD modelo;
    private final UsuarioBD usuario;
    //Conexion
    private final ConectarDB conexion;

    //MODELOS
    private MateriaBD materia;
    private JornadaBD jornada;
    private CursoBD curso;
    private CarreraBD carrera;
    private PeriodoLectivoBD periodoLectivo;
    private PersonaBD persona;
    private DocenteBD docente;

    private PersonaBD alumno;

    //LISTAS
    private List<String> listaParalelos;
    private List<String> listaCiclos;
    private List<String> listaMaterias;
    private List<String> listaJornadas;
    private List<String> listaCarreras;
    private List<String> listaPeriodosLectivos;
    private List<PersonaMD> listaPersona;
    private List<DocenteMD> listaDocente;

    private List<AlumnoCursoMD> listaNotas;
    private List<PersonaMD> listaAlumnosPersonas;

    //TABLA
    private DefaultTableModel tablaNotas;

    //Variable para busqueda
    int idDocente = 0;

    private int contadorLetras = 0;

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
        tablaNotas = (DefaultTableModel) vista.getTbl_notas().getModel();

        //INICIALIZANDO MODELOS
        curso = new CursoBD(conexion);
        materia = new MateriaBD(conexion);
        jornada = new JornadaBD(conexion);
        carrera = new CarreraBD(conexion);
        periodoLectivo = new PeriodoLectivoBD(conexion);
        persona = new PersonaBD(conexion);
        docente = new DocenteBD(conexion);
        alumno = new PersonaBD(conexion);

        //CONSULTAS
        listaParalelos = curso.selectParaleloWhereUsername(usuario.getUsername());
        listaCiclos = curso.selectCicloWhereUsername(usuario.getUsername());
        listaMaterias = materia.selectMateriaWhereUsername(usuario.getUsername());
        listaJornadas = jornada.selectJornadasWhereUsername(usuario.getUsername());
        listaCarreras = carrera.selectCarreraWhereUsername(usuario.getUsername());
        listaPeriodosLectivos = periodoLectivo.selectPeriodoWhereUsername(usuario.getUsername());
        listaPersona = persona.selectWhereUsername(usuario.getUsername());
        listaDocente = docente.selectWhereUsername(usuario.getUsername());
        listaAlumnosPersonas = alumno.cargarAlumnos();

        //CONSULTA DEL DETALLE
        //SE GUARDA EL ID DE DOCENTE
        idDocente = listaDocente.get(0).getIdDocente();

        //CARGA DE COMBO BOXES
        cargarCombos();

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

        vista.getBtn_imprimir().addActionListener(e -> btnImprimir(e));
        vista.getCmb_ciclo().addItemListener((ItemEvent e) -> {
            cargarTabla();
        });
        vista.getCmb_paralelo().addItemListener((ItemEvent e) -> {
            cargarTabla();
        });
        vista.getCmb_jornada().addItemListener((ItemEvent e) -> {
            cargarTabla();
        });
        vista.getCmb_asignatura().addItemListener((ItemEvent e) -> {
            cargarTabla();
        });

//        vista.getTbl_notas().addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                int columna = vista.getTbl_notas().getSelectedColumn();
//                int fila = vista.getTbl_notas().getSelectedRow();
//                if (contadorLetras == 0) {
//                    vista.getTbl_notas().setValueAt("0", fila, columna);
//                    contadorLetras++;
//                }
//                if (columna == 4) {
//                    if (Character.isDigit(e.getKeyChar())) {
//                        vista.getToolkit().beep();
//                        e.consume();
//                        contadorLetras++;
//                    }
//
//                }
//
//            }
//
//        });
        tablaNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    TableModel modelo = vista.getTbl_notas().getModel();

                    setNumero();

                    vista.getTbl_notas().setModel(calcularNotaFinal(modelo));

                    active = false;
                }

            }
        });

    }

    /*
        METODOS DE APOYO
     */
    private void setNumero() {
        int columna = vista.getTbl_notas().getSelectedColumn();
        int fila = vista.getTbl_notas().getSelectedRow();
        String valor = vista.getTbl_notas().getValueAt(fila, columna).toString();

        if (Validaciones.isDecimal(valor)) {
            vista.getTbl_notas().setValueAt(valor, fila, columna);
        } else {
            if (Validaciones.isInt(valor)) {
                vista.getTbl_notas().setValueAt(valor, fila, columna);
            } else {
                vista.getTbl_notas().setValueAt("0.0", fila, columna);
            }
        }

    }

    private TableModel calcularNotaFinal(TableModel datos) {

        int fila = vista.getTbl_notas().getSelectedRow();

        double notaInterCiclo = 0;
        double examenInterCiclo = 0;

        String valor = "";

        try {

            notaInterCiclo = validarNumero(datos.getValueAt(fila, 4).toString());

            if (notaInterCiclo > 30) {
                notaInterCiclo = 30;
                datos.setValueAt(30, fila, 4);
            }

            examenInterCiclo = validarNumero(datos.getValueAt(fila, 5).toString());

            valor = notaInterCiclo + examenInterCiclo + "";

            datos.setValueAt(valor, fila, 6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return datos;
    }

    private double validarNumero(String texto) {

        if (texto.equals("0.0")) {

            return 0;

        } else {
            return Double.parseDouble(texto);
        }

    }

    private void cargarCombos() {
        listaParalelos.forEach(vista.getCmb_paralelo()::addItem);

        listaCiclos.forEach(vista.getCmb_ciclo()::addItem);

        listaMaterias.forEach(vista.getCmb_asignatura()::addItem);

        listaJornadas.forEach(vista.getCmb_jornada()::addItem);

        listaCarreras.forEach(vista.getCmb_carrera()::addItem);

        listaPeriodosLectivos.forEach(vista.getCmb_periodolectivo()::addItem);

        listaPersona.forEach(obj -> {
            vista.getCmb_docente().addItem(obj.getPrimerNombre() + " " + obj.getSegundoNombre() + " " + obj.getPrimerApellido() + " " + obj.getSegundoApellido());
        });

    }

    private void cargarTabla() {

        listaNotas = modelo.selectWhere(
                (String) vista.getCmb_paralelo().getSelectedItem(),
                Integer.parseInt(vista.getCmb_ciclo().getSelectedItem().toString()),
                (String) vista.getCmb_jornada().getSelectedItem(),
                (String) vista.getCmb_asignatura().getSelectedItem(),
                idDocente);

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
    private void btnImprimir(ActionEvent e) {

    }
}
