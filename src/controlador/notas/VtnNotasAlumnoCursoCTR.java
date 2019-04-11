package controlador.notas;

import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.tipoDeNota.IngresoNotasBD;
import modelo.tipoDeNota.IngresoNotasMD;
import modelo.usuario.UsuarioBD;
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
    private HashMap<String, DocenteMD> listaPersonasDocentes;
    private List<PeriodoLectivoMD> listaPeriodos;
    private List<AlumnoCursoBD> listaNotas;

    //TABLA
    private DefaultTableModel tablaNotas;

    //Variable para busqueda
    private int idDocente = 0;
    private int idPeriodoLectivo = 0;
    private boolean cargarTabla = true;
    private int idCurso = -1;

    //PARA LA EDICION DE LAS COLUMNAS
    private final boolean[] canEdit = new boolean[]{
        false, false, false, false, false, false, false, false, false, false, false, true, true, false, true
    //false, false, false, false, true, true, false, true, true, true, false, true, true, false, true
    };

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

        new Thread(() -> {
            //RELLENADO DE LISTAS
            listaPersonasDocentes = DocenteBD.selectAll();
            //RELLENADO DE COMBOS Y LABELS

            Middlewares.setLoadCursor(vista);
            desktop.getLblEstado().setText("CARGANDO INFORMACION DEL DOCENTE");

            new Thread(() -> {
                try {
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
                    Middlewares.setDefaultCursor(vista);
                    InitEventos();
                } catch (NullPointerException e) {
                    Middlewares.setDefaultCursor(vista);
                    JOptionPane.showMessageDialog(vista, "EL DOCENTE NO ESTA ASIGNADO A NINGUN CURSO ACTIVO!!");
                }

                activarColumnas();
            }).start();

        }).start();

        //TABLA
        try {
            desktop.getDpnlPrincipal().add(vista);
            vista.show();
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnNotasAlumnoCursoCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        vista.getCmbDocente().addActionListener(e -> rellenarCmbPeriodos(e));

        vista.getCmbPeriodoLectivo().addActionListener(e -> {
            rellenarLblCarrera();
            cargarComboCiclo();
            cargarComboParalelo();
        });

        vista.getCmbCiclo().addActionListener(e -> {
            cargarComboParalelo();
            cargarComboMaterias();
        });

        vista.getCmbParalelo().addActionListener(e -> cargarComboMaterias());

        vista.getCmbJornada().addActionListener(e -> cargarComboMaterias());

        vista.getBtnVerNotas().addActionListener(e -> btnVerNotas(e));

        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));

        vista.getTblNotas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    setObjFromTable(vista.getTblNotas().getSelectedRow()).editar();
                }
            }
        });

        tablaNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    setValorEnTabla();

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
                    "N°", "Cédula", "Apellidos", "Nombres", "Aporte 1   /30", "Exámen Interciclo /15", "Total Interciclo /45", "Aporte 2  /30", "Exámen Final  /25", "Supletorio /25", "Nota Final", "Estado", "Nro. Faltas", "% Faltas", "Estado Asistencia"
                }
        ) {
            Class[] types = new Class[]{
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                Object.class,
                String.class,
                Object.class
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

    private void activarColumnas() {
        String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
        String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();
        Integer ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());

        idCurso = CursoBD.selectIdCursoWhere(paralelo, ciclo, nombreJornada, idDocente, idPeriodoLectivo);
        IngresoNotasMD ingreso = IngresoNotasBD.selectFromViewActivos(idCurso);

        if (ingreso.isNotaPrimerInterCiclo()) {
            canEdit[4] = true;
        }
        if (ingreso.isNotaExamenInteCiclo()) {
            canEdit[5] = true;
        }
        if (ingreso.isNotaSegundoInterCiclo()) {
            canEdit[7] = true;
        }
        if (ingreso.isNotaExamenFinal()) {
            canEdit[8] = true;
        }
        if (ingreso.isNotaExamenDeRecuperacion()) {
            canEdit[9] = true;
        }

    }

    private int getSelectedRow() {
        return vista.getTblNotas().getSelectedRow();
    }

    private void setValorEnTabla() {
        int columna = vista.getTblNotas().getSelectedColumn();

        int fila = vista.getTblNotas().getSelectedRow();

        String valor = vista.getTblNotas().getValueAt(fila, columna).toString();

        switch (columna) {

            case 12:
                if (!valor.matches("^[0-9]*")) {

                    valor = String.valueOf(listaNotas.get(fila).getNumFalta());
                    tablaNotas.setValueAt(valor, fila, 12);
                }
                break;
            default:
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
                break;
        }

    }

    private TableModel calcularNotaFinal(TableModel datos) {

        int fila = getSelectedRow();
        double Faltas = 0;
        double notaInterCiclo = 0;
        double examenInterCiclo = 0;
        double notaFinalPrimerParcial = 0;
        double notaInterCiclo2 = 0;
        double examenFinal = 0;
        double notaSupletorio = 0;
        double notaFinal = 0;
        String estado = null;

        try {
            Faltas = validarNumero(datos.getValueAt(fila, 13).toString());
            notaInterCiclo = validarNumero(datos.getValueAt(fila, 4).toString());
            examenInterCiclo = validarNumero(datos.getValueAt(fila, 5).toString());
            notaInterCiclo2 = validarNumero(datos.getValueAt(fila, 7).toString());
            examenFinal = validarNumero(datos.getValueAt(fila, 8).toString());
            notaSupletorio = validarNumero(datos.getValueAt(fila, 9).toString());
            notaFinalPrimerParcial = notaInterCiclo + examenInterCiclo;
            datos.setValueAt(notaFinalPrimerParcial, fila, 6);
            notaFinal = notaInterCiclo + notaInterCiclo2 + examenInterCiclo + examenFinal;

            datos.setValueAt(Math.round(notaFinal), fila, 10);

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

            } else if (notaFinal > 70 || notaInterCiclo > 0.0 && notaInterCiclo2 > 0.0 && examenFinal > 0.0 || Faltas < 25) {
                if (notaFinal >= 70) {
                    notaFinal = notaFinalPrimerParcial + notaInterCiclo2 + notaSupletorio;
                    datos.setValueAt(Math.round(notaFinal), fila, 10);
                    estado = "Aprobado";
                    datos.setValueAt(estado, fila, 11);
                    vista.getTblNotas().updateUI();
                } else if (notaFinal < 70) {
                    notaFinal = notaFinalPrimerParcial + notaInterCiclo2 + examenFinal;
                    estado = "Reprobado";
                    datos.setValueAt(estado, fila, 11);
                    datos.setValueAt(Math.round(notaFinal), fila, 10);
                }
                notaFinal = notaFinalPrimerParcial + notaInterCiclo2 + notaSupletorio;
                datos.setValueAt(Math.round(notaFinal), fila, 10);
                estado = "Aprobado";
                datos.setValueAt(estado, fila, 11);
                vista.getTblNotas().updateUI();

            } else if (notaFinal < 70 && notaInterCiclo > 0.0 && notaInterCiclo2 > 0.0 && examenFinal > 0.0 || Faltas >= 25) {

                notaFinal = notaFinalPrimerParcial + notaInterCiclo2 + examenFinal;

                estado = "Reprobado";
                datos.setValueAt(estado, fila, 11);
                datos.setValueAt(Math.round(notaFinal), fila, 10);
            }

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

    private void setIdDocente(String key) {
        listaPersonasDocentes
                .entrySet()
                .stream()
                .filter((entry) -> (entry.getKey().equals(key)))
                .collect(Collectors.toList())
                .forEach(entry -> {
                    idDocente = entry.getValue().getIdDocente();
                });

    }

    private String getCmbDonceteKey() {
        return vista.getCmbDocente().getSelectedItem().toString();
    }

    private void setIdPeriodoLectivo() {
        String periodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();

        listaPeriodos
                .stream()
                .filter(item -> item.getNombre_PerLectivo().equals(periodo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    idPeriodoLectivo = obj.getId_PerioLectivo();
                });
    }

    private void cargarComboDocente() {

        listaPersonasDocentes.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            DocenteMD value = entry.getValue();

            vista.getCmbDocente().addItem(key);

        });

    }

    private void cargarComboPeriodos() {
        vista.getCmbPeriodoLectivo().removeAllItems();
        vista.getLblCarrera().setText("");

        setIdDocente(getCmbDonceteKey());

        listaPeriodos = PeriodoLectivoBD.selectPeriodoWhere(idDocente);
        listaPeriodos
                .stream()
                .forEach(obj -> {

                    vista.getCmbPeriodoLectivo().addItem(obj.getNombre_PerLectivo());
                });
    }

    private void rellenarLblCarrera() {
        try {
            vista.getLblCarrera().setText(CarreraBD.selectCarreraWherePerdLectivo(vista.getCmbPeriodoLectivo().getSelectedItem().toString()));

        } catch (NullPointerException e) {
        }

    }

    private void cargarComboCiclo() {

        try {
            vista.getCmbCiclo().removeAllItems();
            setIdDocente(getCmbDonceteKey());
            setIdPeriodoLectivo();
            CursoBD.selectCicloWhere(idDocente, idPeriodoLectivo)
                    .stream()
                    .forEach(obj -> {
                        vista.getCmbCiclo().addItem(obj + "");
                    });
        } catch (NullPointerException e) {
        }

    }

    private void cargarComboParalelo() {

        try {
            vista.getCmbParalelo().removeAllItems();
            vista.getCmbAsignatura().removeAllItems();
            setIdDocente(getCmbDonceteKey());
            setIdPeriodoLectivo();

            int ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());
            CursoBD.selectParaleloWhere(idDocente, ciclo, idPeriodoLectivo)
                    .stream()
                    .forEach(vista.getCmbParalelo()::addItem);

        } catch (NullPointerException e) {
        }

    }

    private void cargarComboJornadas() {

        try {
            vista.getCmbJornada().removeAllItems();
            setIdDocente(getCmbDonceteKey());
            setIdPeriodoLectivo();

            JornadaBD.selectJornadasWhere(idDocente, idPeriodoLectivo)
                    .stream()
                    .forEach(vista.getCmbJornada()::addItem);

        } catch (NullPointerException e) {
        }

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

            validarCombos();
        } catch (NullPointerException e) {
            vista.getCmbAsignatura().removeAllItems();
        }

    }

    private void validarCombos() {

        if (vista.getCmbAsignatura().getItemCount() > 0) {
            vista.getBtnVerNotas().setEnabled(true);
        } else {
            vista.getBtnVerNotas().setEnabled(false);
        }
    }

    private void cargarTabla() {
        new Thread(() -> {

            cargarTabla = false;

            Middlewares.setLoadCursor(vista);

            try {

                desktop.getLblEstado().setText("CARGANDO NOTAS");

                String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
                String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();
                String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
                String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
                Integer ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());

                listaNotas = AlumnoCursoBD.selectWhere(paralelo, ciclo, nombreJornada, nombreMateria, idDocente, nombrePeriodo);
                agregarFilas();

                desktop.getLblEstado().setText("");
                Middlewares.setDefaultCursor(vista);

            } catch (NullPointerException e) {
                System.out.println(e);
            }
            cargarTabla = true;
            vista.getBtnImprimir().setEnabled(true);

        }).start();

    }

    private void agregarFilas() {
        try {
            tablaNotas.setRowCount(0);
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
                        Math.round(obj.getNotaFinal()),
                        obj.getEstado(),
                        obj.getNumFalta(),
                        obj.getTotalHoras() + "%",
                        obj.getAsistencia()

                    });

                } else {
                    listaNotas = null;
                    listaPersonasDocentes = null;
                    System.gc();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    /*
        PROCESADORES DE EVENTOS
     */
    private void btnVerNotas(ActionEvent e) {
        if (cargarTabla) {
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!");
        }

    }

    private void rellenarCmbPeriodos(ActionEvent e) {
        cargarComboPeriodos();
    }

    private void btnImprimir(ActionEvent e) {
        new Thread(() -> {

            int r = JOptionPane.showOptionDialog(vista,
                    "Reporte de Notas por Curso\n"
                    + "¿Elegir el tipo de Reporte?", "REPORTE NOTAS",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Alumnos con menos de 70", "Alumnos entre 70 a 80",
                        "Alumnos entre 80 a 90", "Alumnos entre 90 a 100", "Reporte Completo"}, "Cancelar");

            Middlewares.setLoadCursor(vista);

            ReportesCTR reportes = new ReportesCTR(vista, idDocente);

            switch (r) {
                case 0:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteMenos70();
                    desktop.getLblEstado().setText("COMPLETADO");

                    break;

                case 1:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteEntre70_80();
                    desktop.getLblEstado().setText("COMPLETADO");

                    break;

                case 2:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteEntre80_90();
                    desktop.getLblEstado().setText("COMPLETADO");

                    break;

                case 3:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteEntre90_100();
                    desktop.getLblEstado().setText("COMPLETADO");

                    break;

                case 4:
                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteCompleto();
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
            Middlewares.setDefaultCursor(vista);
            vista.getBtnVerNotas().setEnabled(true);
        }).start();

    }

}
