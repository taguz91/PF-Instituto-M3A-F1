package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
import net.sf.jasperreports.engine.util.JRLoader;
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
    private int idDocente = 0;
    private boolean cargarTabla = true;

    //PARA LA EDICION DE LAS COLUMNAS
    private final boolean[] canEdit = new boolean[]{
        false, false, false, false, true, true, false, true, true, true, false, true, true, false
    };

    //Thread
    Thread thread = null;

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
        tablaNotas = setTablaFromTabla(vista.getTblNotas());

        //RELLENADO DE LISTAS
        listaPersonasDocentes = PersonaBD.selectWhereUsername(usuario.getUsername());
        idDocente = DocenteBD.selectIdDocenteWhereUsername(usuario.getUsername());
        //RELLENADO DE COMBOS Y LABELS
        thread = new Thread() {
            @Override
            public void run() {
                Effects.setLoadCursor(vista);
                desktop.getLblEstado().setText("CARGANDO INFORMACION DEL DOCENTE");
                cargarComboDocente();
                cargarComboPeriodos();
                rellenarLblCarrera();
                cargarComboCiclo();
                cargarComboParalelo();
                cargarComboJornadas();
                cargarComboMaterias();

                desktop.getLblEstado().setText("COMPLETADO");

                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(VtnNotasAlumnoCursoCTR.class.getName()).log(Level.SEVERE, null, ex);
                }
                desktop.getLblEstado().setText("");
                Effects.setDefaultCursor(vista);
                vista.getBtnVerNotas().setEnabled(true);

            }

        };
        thread.start();
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

        vista.getBtnVerNotas().addActionListener(e -> btnVerNotas(e));

        vista.getCmbParalelo().addActionListener(e -> cargarComboMaterias());

        vista.getCmbJornada().addActionListener(e -> cargarComboMaterias());

        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));

        vista.getTblNotas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tblNotasOnKeyTyped(e);
            }
        });

        tablaNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    setNumero();

                    vista.getTblNotas().setModel(calcularNotaFinal(tablaNotas));

                    active = false;
                }

            }

        });

    }

    /*
        METODOS DE APOYO
     */
    private DefaultTableModel setTablaFromTabla(JTable table) {
        DefaultTableModel tabla = new DefaultTableModel(new Object[][]{},
                new String[]{
                    "N°", "Cédula", "Apellidos", "Nombres", "Aporte 1  /30", "Exámen Interciclo  /15", "Total Interciclo  /45", "Aporte 2  /30", "Exámen Final  /25", "Supletorio  /25", "Nota Final", "Estado", "Nro. Faltas", "% Faltas"
                }) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            @Override
            public boolean isCellEditable(int row, int column) {
                return canEdit[column];
            }

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

        };
        table.setModel(tabla);

        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        vista.getjScrollPane1().setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);

        return tabla;

    }

    private int getSelectedRow() {
        return vista.getTblNotas().getSelectedRow();
    }

    private void setNumero() {
        int columna = vista.getTblNotas().getSelectedColumn();
        int fila = vista.getTblNotas().getSelectedRow();
        String valor = vista.getTblNotas().getValueAt(fila, columna).toString();
        if (!valor.isEmpty()) {
            if (Validaciones.isDecimal(valor)) {
                vista.getTblNotas().setValueAt(valor, fila, columna);
            } else {
                if (Validaciones.isInt(valor)) {
                    vista.getTblNotas().setValueAt(valor, fila, columna);
                } else {
                    vista.getTblNotas().setValueAt("0.0", fila, columna);
                }
            }
        } else {
            vista.getTblNotas().setValueAt("0.0", fila, columna);
        }

    }

    private TableModel calcularNotaFinal(TableModel datos) {
        int fila = vista.getTblNotas().getSelectedRow();

        double notaInterCiclo = 0;
        double examenInterCiclo = 0;
        double notaFinalPrimerParcial = 0;
        double notaInterCiclo2 = 0;
        double examenFinal = 0;
        double notaSupletorio = 0;
        double notaFinal = 0;
        String estado = null;

        try {
            notaInterCiclo = validarNumero(datos.getValueAt(fila, 4).toString());
            examenInterCiclo = validarNumero(datos.getValueAt(fila, 5).toString());
            notaInterCiclo2 = validarNumero(datos.getValueAt(fila, 7).toString());
            examenFinal = validarNumero(datos.getValueAt(fila, 8).toString());
            notaSupletorio = validarNumero(datos.getValueAt(fila, 9).toString());
            notaFinalPrimerParcial = notaInterCiclo + examenInterCiclo;
            datos.setValueAt(notaFinalPrimerParcial, fila, 6);
            notaFinal = notaFinalPrimerParcial;
            datos.setValueAt(notaFinal, fila, 10);

            if (notaInterCiclo > 30.0) {
                notaInterCiclo = 30.0;
                datos.setValueAt(notaInterCiclo, fila, 4);
            } else if (examenInterCiclo > 15.0) {
                examenInterCiclo = 15.0;
                datos.setValueAt(examenInterCiclo, fila, 5);
            } else if (notaInterCiclo2 > 30.0) {
                notaInterCiclo2 = 30.0;
                datos.setValueAt(notaInterCiclo2, fila, 7);
            } else if (examenFinal > 25.0) {
                examenFinal = 25.0;
                datos.setValueAt(examenFinal, fila, 8);
            } else if (notaSupletorio > 25.0) {
                examenFinal = 25.0;
                datos.setValueAt(examenFinal, fila, 9);

            } else if (notaFinal > 70 || notaInterCiclo > 0.0 && notaInterCiclo2 > 0.0 && examenFinal > 0.0) {

                notaFinal = notaFinalPrimerParcial + notaInterCiclo2 + notaSupletorio;
                datos.setValueAt(Math.round(notaFinal), fila, 10);
                isCellEditor(fila, 8);
                estado = "Aprobado";
                datos.setValueAt(estado, fila, 11);
            } else if (notaFinal < 70 && notaInterCiclo > 0.0 && notaInterCiclo2 > 0.0 && examenFinal > 0.0) {

                notaFinal = notaFinalPrimerParcial + notaInterCiclo2 + examenFinal;
                isCellEditor(fila, 9);
                estado = "Reprobado";
                datos.setValueAt(estado, fila, 11);
                datos.setValueAt(Math.round(notaFinal), fila, 10);
            }

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return datos;
    }

    private boolean isCellEditor(int row, int col) {
        return false;
    }

    private AlumnoCursoBD setObjFromTable(int fila) {

        if (fila != -1) {

            AlumnoCursoBD alumno = listaNotas.get(fila);

            alumno.setNota1Parcial(Double.valueOf(vista.getTblNotas().getValueAt(fila, 4).toString()));
            alumno.setNotaExamenInter(Double.valueOf(vista.getTblNotas().getValueAt(fila, 5).toString()));
            alumno.setNota2Parcial(Double.valueOf(vista.getTblNotas().getValueAt(fila, 7).toString()));
            alumno.setNotaExamenFinal(Double.valueOf(vista.getTblNotas().getValueAt(fila, 8).toString()));
            alumno.setNotaExamenSupletorio(Double.valueOf(vista.getTblNotas().getValueAt(fila, 9).toString()));
            alumno.setNotaFinal(Double.valueOf(vista.getTblNotas().getValueAt(fila, 10).toString()));
            alumno.setEstado(vista.getTblNotas().getValueAt(fila, 11).toString());
            alumno.setNumFalta(Integer.valueOf(vista.getTblNotas().getValueAt(fila, 12).toString()));

            return alumno;

        } else {
            return null;
        }

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
        if (cargarTabla == true) {

            thread = new Thread() {
                @Override
                public void run() {

                    cargarTabla = false;

                    Effects.setLoadCursor(vista);

                    try {

                        desktop.getLblEstado().setText("CARGANDO NOTAS");

                        tablaNotas.setRowCount(0);
                        String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
                        String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();
                        String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
                        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
                        Integer ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());

                        listaNotas = AlumnoCursoBD.selectWhere(paralelo, ciclo, nombreJornada, nombreMateria, idDocente, nombrePeriodo);

                        for (AlumnoCursoBD obj : listaNotas) {
                            if (vista.isVisible()) {
                                tablaNotas.addRow(new Object[]{
                                    tablaNotas.getDataVector().size() + 1,
                                    obj.getAlumno().getIdentificacion(),
                                    obj.getAlumno().getPrimerApellido() + " " + obj.getAlumno().getSegundoApellido(),
                                    obj.getAlumno().getPrimerNombre() + " " + obj.getAlumno().getSegundoNombre(),
                                    obj.getNota1Parcial(),
                                    obj.getNotaExamenInter(),
                                    obj.getNota1Parcial() + obj.getNotaExamenInter(),
                                    obj.getNota2Parcial(),
                                    obj.getNotaExamenFinal(),
                                    obj.getNotaExamenSupletorio(),
                                    obj.getNotaFinal(),
                                    obj.getEstado(),
                                    obj.getNumFalta(),
                                    "%"
                                });
                            } else {
                                listaNotas = null;
                                listaPersonasDocentes = null;
                                System.gc();
                                break;
                            }
                        }

                        desktop.getLblEstado().setText("");
                        Effects.setDefaultCursor(vista);
                    } catch (NullPointerException e) {
                        System.out.println(e);
                    }
                    cargarTabla = true;
                }
            };
            thread.start();
        } else {
            JOptionPane.showMessageDialog(vista, "ESPERE!! CARGAR DE NOTAS EN PROCESO");
        }

        for (int i = 0; i < tablaNotas.getDataVector().size(); i++) {
            tablaNotas.isCellEditable(i, 4);
        }

    }

    private void generarReporteCompleto() {
        try {
            String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
            String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
            String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

            String path = Effects.getProjectPath() + "src\\vista\\notas\\Reportes\\ReporteCompleto.jrxml";
            String QUERY = "SELECT\n"
                    + "	\"Alumnos\".id_alumno,\n"
                    + "	p_alu.persona_identificacion,\n"
                    + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                    + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                    + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                    + "	\"AlumnoCurso\".almn_curso_estado,\n"
                    + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                    + "	\"Materias\".materia_nombre, \n"
                    + "	\"Jornadas\".nombre_jornada,\n"
                    + "	\"Materias\".materia_ciclo,\n"
                    + "	\"Cursos\".curso_paralelo,\n"
                    + "	\"Carreras\".carrera_nombre,\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                    + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                    + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                    + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                    + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                    + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                    + "	\n"
                    + "FROM\n"
                    + "	\"AlumnoCurso\"\n"
                    + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                    + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                    + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                    + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                    + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                    + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                    + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                    + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                    + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                    + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                    + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona"
                    + "	WHERE\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                    + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                    + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                    + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                    + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                    + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                    + "ORDER BY\n"
                    + "	p_alu.persona_primer_apellido ASC;";

            System.out.println(QUERY);

            JasperDesign jd = JRXmlLoader.load(path);

            JRDesignQuery newQuery = new JRDesignQuery();

            newQuery.setText(QUERY);

            jd.setQuery(newQuery);

            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/notas/Reportes/ReporteCompleto.jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jr, null, ResourceManager.getConnection());

            JasperViewer.viewReport(jp, false);

        } catch (JRException | NullPointerException q) {
            Logger.getLogger(VtnNotasAlumnoCurso.class.getName()).log(Level.SEVERE, null, q);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void generarReporteMenos70() {
        try {
            String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
            String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
            String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

            String path = Effects.getProjectPath() + "src\\vista\\notas\\Reportes\\ReporteNotasMenor70.jrxml";
            String QUERY = "SELECT\n"
                    + "	\"Alumnos\".id_alumno,\n"
                    + "	p_alu.persona_identificacion,\n"
                    + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                    + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                    + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                    + "	\"AlumnoCurso\".almn_curso_estado,\n"
                    + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                    + "	\"Materias\".materia_nombre, \n"
                    + "	\"Jornadas\".nombre_jornada,\n"
                    + "	\"Materias\".materia_ciclo,\n"
                    + "	\"Cursos\".curso_paralelo,\n"
                    + "	\"Carreras\".carrera_nombre,\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                    + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                    + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                    + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                    + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                    + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                    + "	\n"
                    + "FROM\n"
                    + "	\"AlumnoCurso\"\n"
                    + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                    + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                    + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                    + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                    + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                    + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                    + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                    + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                    + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                    + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                    + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n"
                    + "	\n"
                    + "\n"
                    + "	WHERE\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                    + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                    + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                    + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                    + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                    + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                    + "	AND \"AlumnoCurso\".almn_curso_nota_final < 70\n"
                    + "ORDER BY\n"
                    + "	p_alu.persona_primer_apellido ASC;";

            System.out.println(QUERY);

            JasperDesign jd = JRXmlLoader.load(path);

            JRDesignQuery newQuery = new JRDesignQuery();

            newQuery.setText(QUERY);

            jd.setQuery(newQuery);

            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/notas/Reportes/ReporteNotasMenor70.jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jr, null, ResourceManager.getConnection());

            JasperViewer.viewReport(jp, false);

        } catch (JRException | NullPointerException q) {
            Logger.getLogger(VtnNotasAlumnoCurso.class.getName()).log(Level.SEVERE, null, q);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void generarReporteEntre70_80() {
        try {
            String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
            String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
            String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

            String path = Effects.getProjectPath() + "src\\vista\\notas\\Reportes\\ReporteNotasEntre70y80.jrxml";
            String QUERY = "SELECT\n"
                    + "	\"Alumnos\".id_alumno,\n"
                    + "	p_alu.persona_identificacion,\n"
                    + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                    + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                    + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                    + "	\"AlumnoCurso\".almn_curso_estado,\n"
                    + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                    + "	\"Materias\".materia_nombre, \n"
                    + "	\"Jornadas\".nombre_jornada,\n"
                    + "	\"Materias\".materia_ciclo,\n"
                    + "	\"Cursos\".curso_paralelo,\n"
                    + "	\"Carreras\".carrera_nombre,\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                    + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                    + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                    + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                    + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                    + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                    + "	\n"
                    + "FROM\n"
                    + "	\"AlumnoCurso\"\n"
                    + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                    + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                    + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                    + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                    + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                    + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                    + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                    + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                    + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                    + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                    + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n"
                    + "	\n"
                    + "\n"
                    + "	WHERE\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                    + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                    + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                    + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                    + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                    + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                    + "	AND \"AlumnoCurso\".almn_curso_nota_final >=70 \n"
                    + "	AND \"AlumnoCurso\".almn_curso_nota_final <81 \n"
                    + "ORDER BY\n"
                    + "	p_alu.persona_primer_apellido ASC;";

            System.out.println(QUERY);

            JasperDesign jd = JRXmlLoader.load(path);

            JRDesignQuery newQuery = new JRDesignQuery();

            newQuery.setText(QUERY);

            jd.setQuery(newQuery);

            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/notas/Reportes/ReporteNotasEntre70y80.jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jr, null, ResourceManager.getConnection());

            JasperViewer.viewReport(jp, false);

        } catch (JRException | NullPointerException q) {
            Logger.getLogger(VtnNotasAlumnoCurso.class.getName()).log(Level.SEVERE, null, q);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void generarReporteEntre80_90() {
        try {
            String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
            String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
            String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

            String path = Effects.getProjectPath() + "src\\vista\\notas\\Reportes\\ReporteNotasEntre80y90.jrxml";
            String QUERY = "SELECT\n"
                    + "	\"Alumnos\".id_alumno,\n"
                    + "	p_alu.persona_identificacion,\n"
                    + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                    + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                    + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                    + "	\"AlumnoCurso\".almn_curso_estado,\n"
                    + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                    + "	\"Materias\".materia_nombre, \n"
                    + "	\"Jornadas\".nombre_jornada,\n"
                    + "	\"Materias\".materia_ciclo,\n"
                    + "	\"Cursos\".curso_paralelo,\n"
                    + "	\"Carreras\".carrera_nombre,\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                    + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                    + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                    + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                    + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                    + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                    + "	\n"
                    + "FROM\n"
                    + "	\"AlumnoCurso\"\n"
                    + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                    + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                    + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                    + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                    + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                    + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                    + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                    + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                    + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                    + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                    + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n"
                    + "	\n"
                    + "\n"
                    + "	WHERE\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                    + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                    + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                    + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                    + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                    + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                    + "	AND \"AlumnoCurso\".almn_curso_nota_final >=80 \n"
                    + "	AND \"AlumnoCurso\".almn_curso_nota_final <91 \n"
                    + "ORDER BY\n"
                    + "	p_alu.persona_primer_apellido ASC;";

            System.out.println(QUERY);

            JasperDesign jd = JRXmlLoader.load(path);

            JRDesignQuery newQuery = new JRDesignQuery();

            newQuery.setText(QUERY);

            jd.setQuery(newQuery);

            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/notas/Reportes/ReporteNotasEntre80y90.jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jr, null, ResourceManager.getConnection());

            JasperViewer.viewReport(jp, false);

        } catch (JRException | NullPointerException q) {
            Logger.getLogger(VtnNotasAlumnoCurso.class.getName()).log(Level.SEVERE, null, q);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void generarReporteEntre90_100() {
        try {
            String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
            String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
            String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

            String path = Effects.getProjectPath() + "src\\vista\\notas\\Reportes\\ReporteNotasEntre90y100.jrxml";
            String QUERY = "SELECT\n"
                    + "	\"Alumnos\".id_alumno,\n"
                    + "	p_alu.persona_identificacion,\n"
                    + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                    + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                    + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                    + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                    + "	\"AlumnoCurso\".almn_curso_estado,\n"
                    + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                    + "	\"Materias\".materia_nombre, \n"
                    + "	\"Jornadas\".nombre_jornada,\n"
                    + "	\"Materias\".materia_ciclo,\n"
                    + "	\"Cursos\".curso_paralelo,\n"
                    + "	\"Carreras\".carrera_nombre,\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                    + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                    + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                    + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                    + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                    + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                    + "	\n"
                    + "FROM\n"
                    + "	\"AlumnoCurso\"\n"
                    + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                    + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                    + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                    + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                    + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                    + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                    + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                    + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                    + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                    + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                    + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n"
                    + "	\n"
                    + "\n"
                    + "	WHERE\n"
                    + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                    + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                    + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                    + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                    + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                    + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                    + "	AND \"AlumnoCurso\".almn_curso_nota_final >=90 \n"
                    + "	AND \"AlumnoCurso\".almn_curso_nota_final <101\n"
                    + "ORDER BY\n"
                    + "	p_alu.persona_primer_apellido ASC;";

            System.out.println(QUERY);

            JasperDesign jd = JRXmlLoader.load(path);

            JRDesignQuery newQuery = new JRDesignQuery();

            newQuery.setText(QUERY);

            jd.setQuery(newQuery);

            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/notas/Reportes/ReporteNotasEntre90y100.jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jr, null, ResourceManager.getConnection());

            JasperViewer.viewReport(jp, false);

        } catch (JRException | NullPointerException q) {
            Logger.getLogger(VtnNotasAlumnoCurso.class.getName()).log(Level.SEVERE, null, q);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
        PROCESADORES DE EVENTOS
     */
    private void btnImprimir(ActionEvent e) {

        int r = JOptionPane.showOptionDialog(vista,
                "Reporte de Notas por Curso\n"
                + "¿Elegir el tipo de Reporte?", "REPORTE NOTAS",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Alumnos con menos de 70", "Alumnos entre 70 a 80",
                    "Alumnos entre 80 a 90", "Alumnos entre 90 a 100", "Reporte Completo"}, "Cancelar");

        thread = new Thread() {
            @Override
            public void run() {
                Effects.setLoadCursor(vista);

                switch (r) {
                    case 0:

                        desktop.getLblEstado().setText("CARGANDO REPORTE....");
                        generarReporteMenos70();
                        desktop.getLblEstado().setText("COMPLETADO");

                        break;

                    case 1:

                        desktop.getLblEstado().setText("CARGANDO REPORTE....");
                        generarReporteEntre70_80();
                        desktop.getLblEstado().setText("COMPLETADO");

                        break;

                    case 2:

                        desktop.getLblEstado().setText("CARGANDO REPORTE....");
                        generarReporteEntre80_90();
                        desktop.getLblEstado().setText("COMPLETADO");

                        break;

                    case 3:

                        desktop.getLblEstado().setText("CARGANDO REPORTE....");
                        generarReporteEntre90_100();
                        desktop.getLblEstado().setText("COMPLETADO");

                        break;

                    case 4:
                        desktop.getLblEstado().setText("CARGANDO REPORTE....");
                        generarReporteCompleto();
                        desktop.getLblEstado().setText("COMPLETADO");
                        break;

                    default:
                        break;
                }

                try {
                    sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(VtnNotasAlumnoCursoCTR.class.getName()).log(Level.SEVERE, null, ex);
                }
                desktop.getLblEstado().setText("");
                Effects.setDefaultCursor(vista);
                vista.getBtnVerNotas().setEnabled(true);

            }

        };
        thread.start();

    }

    private void btnVerNotas(ActionEvent e) {

        cargarTabla();

    }

    private void tblNotasOnKeyTyped(KeyEvent e) {
        if (e.getKeyCode() == 10) {

            setObjFromTable(vista.getTblNotas().getSelectedRow()).editar();

        }
    }
}
