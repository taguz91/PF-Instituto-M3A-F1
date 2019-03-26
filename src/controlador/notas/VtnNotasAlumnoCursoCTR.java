package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
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
                    sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(VtnNotasAlumnoCursoCTR.class.getName()).log(Level.SEVERE, null, ex);
                }
                desktop.getLblEstado().setText("");

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
                    "N°", "Cedula", "Apellidos", "Nombres", "APORTE 1   /30", "EXAMEN INTERCICLO", "TOTAL INTERCICLO", "APORTE 2  /30", "EXAMEN FINAL  /25", "/25 SUSP.", "NOTA FINAL", "ESTADO", "Nro. Faltas", "% Faltas"
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
        double notaFinal = 0;

        try {
            notaInterCiclo = validarNumero(datos.getValueAt(fila, 4).toString());

            if (notaInterCiclo > 30) {
                notaInterCiclo = 30;
                datos.setValueAt(30, fila, 4);
            }

            examenInterCiclo = validarNumero(datos.getValueAt(fila, 5).toString());

            notaFinal = notaInterCiclo + examenInterCiclo;

            notaFinalPrimerParcial = notaInterCiclo + examenInterCiclo;

            datos.setValueAt(notaFinalPrimerParcial, fila, 6);

            datos.setValueAt(notaFinal, fila, 10);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return datos;
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
                                        obj.getNota1Parcial() + obj.getNotaExamenInter(),
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

            String path = Effects.getProjectPath() + "src\\vista\\notas\\Reportes\\reportecompuces.jrxml";
            String QUERY = "SELECT\n"
                    + "\"AlumnoCurso\".id_alumno,\n"
                    + "\"Personas\".persona_identificacion,\n"
                    + "\"Personas\".persona_primer_apellido || '  ' ||\"Personas\".persona_segundo_apellido AS \"APELLIDOS\",\n"
                    + "\"Personas\".persona_primer_nombre || '  '||\"Personas\".persona_segundo_nombre AS \"NOMBRES\",\n"
                    + "\"AlumnoCurso\".almn_curso_nt_1_parcial,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_interciclo,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_2_parcial,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_final,\n"
                    + "\"AlumnoCurso\".almn_curso_nt_examen_supletorio,\n"
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
                    + "\"Cursos\".id_docente = " + idDocente + " AND\n"
                    + "\"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' AND\n"
                    + "\"Cursos\".curso_ciclo = " + ciclo + " AND\n"
                    + "\"Cursos\".curso_paralelo = '" + paralelo + "' AND\n"
                    + "\"Jornadas\".nombre_jornada = '" + nombreJornada + "'\n"
                    + "ORDER BY\n"
                    + "\"Personas\".persona_primer_apellido ASC;";

            System.out.println(QUERY);

            JasperDesign jd = JRXmlLoader.load(path);

            JRDesignQuery newQuery = new JRDesignQuery();

            newQuery.setText(QUERY);

            jd.setQuery(newQuery);

            JasperReport jr = JasperCompileManager.compileReport(jd);

            JasperPrint jp = JasperFillManager.fillReport(jr, null, ResourceManager.getConnection());

            JasperViewer.viewReport(jp);

        } catch (JRException | NullPointerException q) {
            Logger.getLogger(VtnNotasAlumnoCurso.class.getName()).log(Level.SEVERE, null, q);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void generarReporteMenos70() {
        String QUERY = "SELECT\n"
                + "\"Alumnos\".id_alumno, \n"
                + "\"Personas\".persona_identificacion,\n"
                + "\"Personas\".persona_primer_apellido,\n"
                + "\"Personas\".persona_segundo_apellido,\n"
                + "\"Personas\".persona_primer_nombre,\n"
                + "\"Personas\".persona_segundo_nombre,\n"
                + "\"AlumnoCurso\".almn_curso_nota_final\n"
                + "FROM\n"
                + "\"Personas\"\n"
                + "JOIN \"Alumnos\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                + "JOIN \"AlumnoCurso\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno \n"
                + "where almn_curso_nota_final<70;";
    }
    
    private void generarReporteEntre70_80() {
        String QUERY = "SELECT\n"
                + "\"Alumnos\".id_alumno, \n"
                + "\"Personas\".persona_identificacion,\n"
                + "\"Personas\".persona_primer_apellido,\n"
                + "\"Personas\".persona_segundo_apellido,\n"
                + "\"Personas\".persona_primer_nombre,\n"
                + "\"Personas\".persona_segundo_nombre,\n"
                + "\"AlumnoCurso\".almn_curso_nota_final\n"
                + "FROM\n"
                + "\"Personas\"\n"
                + "JOIN \"Alumnos\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                + "JOIN \"AlumnoCurso\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno \n"
                + "where almn_curso_nota_final>=70 and almn_curso_nota_final <80;";
    }
    
     private void generarReporteEntre80_90() {
        String QUERY = "SELECT\n"
                + "\"Alumnos\".id_alumno, \n"
                + "\"Personas\".persona_identificacion,\n"
                + "\"Personas\".persona_primer_apellido,\n"
                + "\"Personas\".persona_segundo_apellido,\n"
                + "\"Personas\".persona_primer_nombre,\n"
                + "\"Personas\".persona_segundo_nombre,\n"
                + "\"AlumnoCurso\".almn_curso_nota_final\n"
                + "FROM\n"
                + "\"Personas\"\n"
                + "JOIN \"Alumnos\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                + "JOIN \"AlumnoCurso\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "where almn_curso_nota_final>=80 and almn_curso_nota_final <90;";
    }
     private void generarReporteEntre90_100() {
        String QUERY = "SELECT\n"
                + "\"Alumnos\".id_alumno, \n"
                + "\"Personas\".persona_identificacion,\n"
                + "\"Personas\".persona_primer_apellido,\n"
                + "\"Personas\".persona_segundo_apellido,\n"
                + "\"Personas\".persona_primer_nombre,\n"
                + "\"Personas\".persona_segundo_nombre,\n"
                + "\"AlumnoCurso\".almn_curso_nota_final\n"
                + "FROM\n"
                + "\"Personas\"\n"
                + "JOIN \"Alumnos\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                + "JOIN \"AlumnoCurso\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno \n"
                + "where almn_curso_nota_final>=90 and almn_curso_nota_final <100;";
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
                switch (r) {
                    case 0:
                        //  REPORTE DE Alumnos con menos de 70" 
                        generarReporteMenos70();
                        break;
                    case 1:
                        //REPORTE DE Alumnos entre 70 a 80"
                        generarReporteEntre70_80();
                        break;
                    case 2:
                        //REPORTE DE Alumnos entre 80 a 90"
                        generarReporteEntre80_90();
                        break;
                    case 3:
                         //REPORTE DE Alumnos entre 90 a 100"
                        generarReporteEntre90_100();
                        break;
                    case 4:
                         //REPORTE completo"
                        generarReporteCompleto();
                        break;
                    default:
                        break;
                }

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
