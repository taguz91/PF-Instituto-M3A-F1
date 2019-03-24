package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.alumno.AlumnoCursoBD;
import modelo.carrera.CarreraBD;
import modelo.curso.CursoBD;
import modelo.jornada.JornadaBD;
import modelo.materia.MateriaBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.persona.DocenteBD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.UsuarioBD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.notas.VtnNotasAlumnoCurso;
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

    //LISTAS
    private List<PersonaMD> listaPersonasDocentes;
    private List<AlumnoCursoBD> listaNotas;

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
        tablaNotas = (DefaultTableModel) vista.getTblNotas().getModel();

        //RELLENADO DE LISTAS
        listaPersonasDocentes = PersonaBD.selectWhereUsername(usuario.getUsername());
        idDocente = DocenteBD.selectIdDocenteWhereUsername(usuario.getUsername());
        //RELLENADO DE COMBOS Y LABELS
        cargarComboDocente();
        cargarComboPeriodos();
        rellenarLblCarrera();
        cargarComboCiclo();
        cargarComboParalelo();
        cargarComboJornadas();

        cargarComboMaterias();
        //TABLA
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

        vista.getCmbPeriodoLectivo().addActionListener(e -> rellenarLblCarrera());

        vista.getCmbCiclo().addActionListener(e -> {
            cargarComboParalelo();
            cargarComboMaterias();
        });

        vista.getCmbParalelo().addActionListener(e -> cargarComboMaterias());

        vista.getCmbJornada().addActionListener(e -> cargarComboMaterias());

        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));

        tablaNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    TableModel modelo = vista.getTblNotas().getModel();

                    setNumero();

                    vista.getTblNotas().setModel(calcularNotaFinal(modelo));

                    active = false;
                }

            }
        });

    }

    /*
        METODOS DE APOYO
     */
    private void setNumero() {
        int columna = vista.getTblNotas().getSelectedColumn();
        int fila = vista.getTblNotas().getSelectedRow();
        String valor = vista.getTblNotas().getValueAt(fila, columna).toString();

        if (Validaciones.isDecimal(valor)) {
            vista.getTblNotas().setValueAt(valor, fila, columna);
        } else {
            if (Validaciones.isInt(valor)) {
                vista.getTblNotas().setValueAt(valor, fila, columna);
            } else {
                vista.getTblNotas().setValueAt("0.0", fila, columna);
            }
        }

    }

    private TableModel calcularNotaFinal(TableModel datos) {

        int fila = vista.getTblNotas().getSelectedRow();

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

    private void cargarComboDocente() {

        listaPersonasDocentes
                .stream()
                .forEach(obj -> {

                    vista.getCmbDocente().addItem(obj.getIdentificacion() + " " + obj.getPrimerNombre() + " " + obj.getSegundoNombre() + " " + obj.getPrimerApellido() + " " + obj.getSegundoApellido());
                });

    }

    private void cargarComboPeriodos() {
        PeriodoLectivoBD.selectPeriodoWhereUsername(usuario.getUsername())
                .stream()
                .forEach(vista.getCmbPeriodoLectivo()::addItem);

    }

    private void rellenarLblCarrera() {
        vista.getLblCarrera().setText(CarreraBD.selectCarreraWherePerdLectivo(vista.getCmbPeriodoLectivo().getSelectedItem().toString()));

    }

    private void cargarComboCiclo() {

        CursoBD.selectCicloWhere(idDocente)
                .stream()
                .forEach(obj -> {
                    vista.getCmbCiclo().addItem(obj.toString());
                });

    }

    private void cargarComboParalelo() {

        vista.getCmbParalelo().removeAllItems();
        vista.getCmbAsignatura().removeAllItems();

        int ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());
        CursoBD.selectParaleloWhere(idDocente, ciclo)
                .stream()
                .forEach(vista.getCmbParalelo()::addItem);
    }

    private void cargarComboJornadas() {
        JornadaBD.selectJornadasWhereIdDocenteAndNombPrd(idDocente, vista.getCmbPeriodoLectivo().getSelectedItem().toString())
                .stream()
                .forEach(vista.getCmbJornada()::addItem);
    }

    private void cargarComboMaterias() {
        try {
            int ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());
            String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
            String jornada = vista.getCmbJornada().getSelectedItem().toString();

            vista.getCmbAsignatura().removeAllItems();

            MateriaBD.selectWhere(idDocente, ciclo, paralelo, jornada)
                    .stream()
                    .forEach(vista.getCmbAsignatura()::addItem);

        } catch (NullPointerException e) {
            vista.getCmbAsignatura().removeAllItems();
        }

    }

    private void cargarTabla() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    desktop.getLblEstado().setText("CARGANDO NOTAS");

                    tablaNotas.setRowCount(0);
                    String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
                    String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();
                    String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
                    String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
                    Integer ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());

                    listaNotas = modelo.selectWhere(paralelo, ciclo, nombreJornada, nombreMateria, idDocente, nombrePeriodo);

                    listaNotas.stream()
                            .forEach(obj -> {

                                tablaNotas.addRow(new Object[]{
                                    tablaNotas.getDataVector().size() + 1,
                                    obj.getAlumno().getIdentificacion(),
                                    obj.getAlumno().getPrimerApellido() + " " + obj.getAlumno().getSegundoApellido(),
                                    obj.getAlumno().getPrimerNombre() + " " + obj.getAlumno().getSegundoNombre(),
                                    obj.getNota1Parcial(),
                                    obj.getNotaExamenInter(),
                                    0.0,
                                    obj.getNota2Parcial(),
                                    obj.getNotaExamenFinal(),
                                    obj.getNotaExamenSupletorio(),
                                    obj.getNotaFinal(),
                                    obj.getEstado(),
                                    obj.getNumFalta(),
                                    "%"
                                });
                            });

                    desktop.getLblEstado().setText("");

                } catch (NullPointerException e) {
                    System.out.println(e);
                }
                try {
                    tablaNotas.setRowCount(0);
                    String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
                    String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();
                    String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
                    String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
                    Integer ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());

                    listaNotas = modelo.selectWhere(paralelo, ciclo, nombreJornada, nombreMateria, idDocente, nombrePeriodo);

                    listaNotas.stream()
                            .forEach(obj -> {

                                tablaNotas.addRow(new Object[]{
                                    tablaNotas.getDataVector().size() + 1,
                                    obj.getAlumno().getIdentificacion(),
                                    obj.getAlumno().getPrimerApellido() + " " + obj.getAlumno().getSegundoApellido(),
                                    obj.getAlumno().getPrimerNombre() + " " + obj.getAlumno().getSegundoNombre(),
                                    obj.getNota1Parcial(),
                                    obj.getNotaExamenInter(),
                                    0.0,
                                    obj.getNota2Parcial(),
                                    obj.getNotaExamenFinal(),
                                    obj.getNotaExamenSupletorio(),
                                    obj.getNotaFinal(),
                                    obj.getEstado(),
                                    obj.getNumFalta(),
                                    "%"
                                });
                            });

                    System.out.println("thread fin");

                } catch (NullPointerException e) {
                    System.out.println(e);
                }
            }

        };
        thread.start();

    }

    private void generarReporte() {
        try {

            String path = Effects.getProjectPath() + "src\\vista\\notas\\Reportes\\reportecompuces.jrxml";

            String sql = "SELECT\n"
                    + "\"AlumnoCurso\".id_almn_curso,\n"
                    + "\"AlumnoCurso\".id_alumno,\n"
                    + "\"AlumnoCurso\".id_curso,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_1_parcial,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_interciclo,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_2_parcial,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_final,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_supletorio,\n"
                    + "\"AlumnoCurso\".almn_curso_asistencia,\n"
                    + "\"AlumnoCurso\".almn_curso_nota_final,\n"
                    + "\"AlumnoCurso\".almn_curso_estado,\n"
                    + "\"AlumnoCurso\".almn_curso_num_faltas\n"
                    + "FROM\n"
                    + "\"AlumnoCurso\"\n"
                    + "INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                    + "INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                    + "INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                    + "INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                    + "INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                    + "INNER JOIN \"Personas\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                    + "WHERE\n"
                    + "\"PeriodoLectivo\".prd_lectivo_estado = FALSE AND\n"
                    + "\"Cursos\".id_docente = 55 AND\n"
                    + "\"PeriodoLectivo\".prd_lectivo_nombre = 'SDS   MAYO/2019   OCTUBRE/2019' AND\n"
                    + "\"Cursos\".curso_ciclo = 4 AND\n"
                    + "\"Cursos\".curso_paralelo = 'A' AND\n"
                    + "\"Jornadas\".nombre_jornada = 'MATUTINA'\n"
                    + "ORDER BY\n"
                    + "\"Personas\".persona_primer_apellido ASC;\n"
                    + "SELECT\n"
                    + "\"AlumnoCurso\".id_almn_curso,\n"
                    + "\"AlumnoCurso\".id_alumno,\n"
                    + "\"AlumnoCurso\".id_curso,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_1_parcial,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_interciclo,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_2_parcial,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_final,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_supletorio,\n"
                    + "\"AlumnoCurso\".almn_curso_asistencia,\n"
                    + "\"AlumnoCurso\".almn_curso_nota_final,\n"
                    + "\"AlumnoCurso\".almn_curso_estado,\n"
                    + "\"AlumnoCurso\".almn_curso_num_faltas\n"
                    + "FROM\n"
                    + "\"AlumnoCurso\"\n"
                    + "INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                    + "INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                    + "INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                    + "INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                    + "INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                    + "INNER JOIN \"Personas\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                    + "WHERE\n"
                    + "\"PeriodoLectivo\".prd_lectivo_estado = FALSE AND\n"
                    + "\"Cursos\".id_docente = 55 AND\n"
                    + "\"PeriodoLectivo\".prd_lectivo_nombre = 'SDS   MAYO/2019   OCTUBRE/2019' AND\n"
                    + "\"Cursos\".curso_ciclo = 4 AND\n"
                    + "\"Cursos\".curso_paralelo = 'A' AND\n"
                    + "\"Jornadas\".nombre_jornada = 'MATUTINA'\n"
                    + "ORDER BY\n"
                    + "\"Personas\".persona_primer_apellido ASC;";

            JasperDesign jd = JRXmlLoader.load(path);

            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);

            jd.setQuery(newQuery);

            JasperReport jr = JasperCompileManager.compileReport(jd);

            JasperPrint jp = JasperFillManager.fillReport(jr, null, ResourceManager.getConnection());

            JasperViewer.viewReport(jp);

        } catch (JRException q) {
            Logger.getLogger(VtnNotasAlumnoCurso.class.getName()).log(Level.SEVERE, null, q);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
        PROCESADORES DE EVENTOS
     */
    private void btnImprimir(ActionEvent e) {
        cargarTabla();
    }

}
