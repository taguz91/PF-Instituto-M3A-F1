package controlador.notas;

import controlador.Libraries.Validaciones;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.ConectarDB;
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
       // listaPersonasDocentes = PersonaBD.selectWhereUsername(usuario.getUsername());
       // idDocente = DocenteBD.selectIdDocenteWhereUsername(usuario.getUsername());

        //RELLENADO DE COMBOS Y LABELS
        /*cargarComboDocente();
        cargarComboPeriodos();
        rellenarLblCarrera();
        cargarComboCiclo();
        cargarComboParalelo();
        cargarComboJornadas();

        cargarComboMaterias();*/

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

        try {
            tablaNotas.setRowCount(0);
            String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
            String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();
            String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
            String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            Integer ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());

            listaNotas = modelo.selectWhere(paralelo, ciclo, nombreJornada, nombreMateria, idDocente, nombrePeriodo);

            listaNotas.stream()
                    .forEach(objAlumCurso -> {

                        PersonaMD persona = PersonaBD.selectNombresApellidosWhere(objAlumCurso.getAlumno().getId_Alumno());

                        tablaNotas.addRow(new Object[]{
                            tablaNotas.getDataVector().size() + 1,
                            persona.getIdentificacion(),
                            persona.getPrimerApellido() + " " + persona.getSegundoApellido(),
                            persona.getPrimerNombre() + " " + persona.getSegundoNombre(),
                            objAlumCurso.getNota1Parcial(),
                            objAlumCurso.getNotaExamenInter(),
                            0.0,
                            objAlumCurso.getNota2Parcial(),
                            objAlumCurso.getNotaExamenFinal(),
                            objAlumCurso.getNotaExamenSupletorio(),
                            objAlumCurso.getNotaFinal(),
                            objAlumCurso.getEstado(),
                            objAlumCurso.getNumFalta(),
                            "%"
                        });
                    });

        } catch (NullPointerException e) {
            System.out.println(e);
        }

    }

    /*
        PROCESADORES DE EVENTOS
     */
    private void btnImprimir(ActionEvent e) {
        cargarTabla();
        
        
     conector con = new conector();
     Connection conexion=con.getConexion();
     
     try {

           String path = "C:\\Users\\CESAR\\Documents\\GitHub\\PF-Instituto-M3A-F1\\src\\vista\\notas\\Reportes\\reportecompuces.jrxml";
                   
                      String sql = "SELECT\n" +
                                        "\"Alumnos\".id_alumno, \n" +
                                        "\"Personas\".persona_identificacion,\n" +
                                        "\"Personas\".persona_primer_apellido,\n" +
                                        "\"Personas\".persona_segundo_apellido,\n" +
                                        "\"Personas\".persona_primer_nombre,\n" +
                                        "\"Personas\".persona_segundo_nombre,\n" +
                                        "\"AlumnoCurso\".almn_curso_nt_1_parcial,\n" +
                                        "\"AlumnoCurso\".almn_curso_nt_examen_interciclo,\n" +
                                        "\"AlumnoCurso\".almn_curso_nt_2_parcial,\n" +
                                        "\"AlumnoCurso\".almn_curso_nt_examen_final,\n" +
                                        "\"AlumnoCurso\".almn_curso_nt_examen_supletorio,\n" +
                                        "\"AlumnoCurso\".almn_curso_asistencia,\n" +
                                        "\"AlumnoCurso\".almn_curso_nota_final,\n" +
                                        "\"AlumnoCurso\".almn_curso_estado,\n" +
                                        "\"AlumnoCurso\".almn_curso_num_faltas\n" +
                                        "FROM\n" +
                                        "\"Personas\"\n" +
                                        "JOIN \"Alumnos\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n" +
                                        "JOIN \"AlumnoCurso\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno;";
           
           System.out.println("-------------->1");
           JasperDesign jd = JRXmlLoader.load(path);
           System.out.println("-------------->2");
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
           System.out.println("-------------->3");
                    jd.setQuery(newQuery);
           System.out.println("-------------->4");
                    JasperReport jr = JasperCompileManager.compileReport(jd);
           System.out.println("-------------->5");
                    JasperPrint jp = JasperFillManager.fillReport(jr,null, new conector().getConexion());
           System.out.println("-------------->6");
                    JasperViewer.viewReport(jp);
           System.out.println("-------------->7");

                } catch (JRException q) {

                    Logger.getLogger(VtnNotasAlumnoCurso.class.getName()).log(Level.SEVERE, null, q);
                }
    }
}
