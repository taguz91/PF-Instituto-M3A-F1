package controlador.notas;

import controlador.Libraries.Middlewares;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCursoBD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.notas.NotasBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.usuario.UsuarioBD;
import vista.notas.VtnNotasAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnNotas {

    private VtnPrincipal desktop;
    private VtnNotasAlumnoCurso vista;
    private AlumnoCursoBD modelo;
    private UsuarioBD usuario;

    //LISTAS
    private Map<String, DocenteMD> listaDocentes;
    private List<PeriodoLectivoMD> listaPeriodos;
    private static List<AlumnoCursoBD> listaNotas;
    private List<MateriaMD> listaMaterias;

    //TABLA
    private static DefaultTableModel tablaNotas;

    //VARIABLES DE BUSQUEDA
    protected int idDocente = -1;
    protected int idPeriodoLectivo = -1;
    protected int idCurso = -1;

    //ACTIVACION DE HILOS
    private boolean cargarTabla = true;

    public VtnNotas(VtnPrincipal desktop, VtnNotasAlumnoCurso vista, AlumnoCursoBD modelo, UsuarioBD usuario) {
        vista.setValorMinimo(70);
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.usuario = usuario;
    }

    // <editor-fold defaultstate="collapsed" desc="INITS">    
    public void Init() {
        //TablaPresencial table = new TablaPresencial(vista.getTblNotas());

        vista.canEdit[1] = true;

        tablaNotas = (DefaultTableModel) vista.getTblNotas().getModel();
        //tablaNotas.isCellEditable(0, 0);

        listaDocentes = DocenteBD.selectAll();

        new Thread(() -> {
            activarForm(false);
            cargarComboDocente();
            cargarComboPeriodos();
            cargarComboCiclo();
//            cargarComboParalelo();
//            cargarComboJornadas();
            cargarComboMaterias();
            InitEventos();
            activarForm(true);
        }).start();

        Middlewares.addInDesktopPane(vista, desktop.getDpnlPrincipal());

    }

    private void InitEventos() {

        vista.getCmbDocente().addActionListener(e -> cargarComboPeriodos());

        vista.getCmbPeriodoLectivo().addActionListener(e -> {
            cargarComboCiclo();
        });

        vista.getCmbCiclo().addActionListener(e -> {
            cargarComboMaterias();
        });

        vista.getBtnVerNotas().addActionListener(e -> btnVerNotas(e));

//        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));
//
//        vista.getBtnBuscar().addActionListener(e -> btnBuscar(e));
//
        vista.getTblNotas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    editar();
                }
            }
        });

//        tablaNotas.addTableModelListener(new TableModelListener() {
//
//            boolean active = false;
//
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                if (!active && e.getType() == TableModelEvent.UPDATE) {
//
//                    active = true;
//
//                    carlcularNotas(tablaNotas);
//
//                    active = false;
//                }
//
//            }
//
//        });
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="METODOS DE APOYO">    
    /*
        METODOS DE CARGA
     */
    private void cargarComboDocente() {
        listaDocentes.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            DocenteMD value = entry.getValue();

            vista.getCmbDocente().addItem(key);

        });

    }

    private void cargarComboPeriodos() {
        vista.getCmbPeriodoLectivo().removeAllItems();
        vista.getLblCarrera().setText("");

        listaPeriodos = PeriodoLectivoBD.selectPeriodoWhere(getIdDocente());
        listaPeriodos
                .stream()
                .forEach(obj -> {
                    vista.getCmbPeriodoLectivo().addItem(obj.getNombre_PerLectivo());
                    vista.getLblCarrera().setText(obj.getCarrera().getNombre());
                });
    }

    private void cargarComboCiclo() {
        try {
            vista.getCmbCiclo().removeAllItems();

            CursoBD.selectCicloWhere(getIdDocente(), getIdPeriodoLectivo())
                    .stream()
                    .forEach(obj -> {
                        vista.getCmbCiclo().addItem(obj + "");
                    });
        } catch (NullPointerException e) {
            Middlewares.bugProcessor(vista);
        }
    }

    private void cargarComboMaterias() {
        try {
            vista.getCmbAsignatura().removeAllItems();

            CursoMD curso = new CursoMD();
            DocenteMD docente = new DocenteMD();
            docente.setIdDocente(getIdDocente());
            curso.setId_docente(docente);
            PeriodoLectivoMD periodo = new PeriodoLectivoMD();
            periodo.setId_PerioLectivo(getIdPeriodoLectivo());
            curso.setId_prd_lectivo(periodo);
            curso.setCurso_nombre(vista.getCmbCiclo().getSelectedItem().toString());

            System.out.println(curso);

            listaMaterias = MateriaBD.selectWhere(curso);

            listaMaterias.stream()
                    .forEach(obj -> {
                        vista.getCmbAsignatura().addItem(obj.getNombre());
                    });

            validarCombos();
        } catch (NullPointerException e) {
            Middlewares.bugProcessor(e, vista);
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

    /*
        VARIOS
     */
    private double conversor(String texto) {
        return Math.round(Double.valueOf(texto) * 10) / 10d;
    }

    private void editar() {
        int fila = getSelectedRow();

        AlumnoCursoBD alumno = listaNotas.get(fila);

        alumno.setNotaFinal(Double.valueOf(vista.getTblNotas().getValueAt(fila, 12).toString()));
        alumno.setEstado(vista.getTblNotas().getValueAt(fila, 13).toString());
        alumno.setNumFalta(Integer.valueOf(vista.getTblNotas().getValueAt(fila, 14).toString()));
        alumno.setAsistencia(vista.getTblNotas().getValueAt(fila, 16).toString());

        String arg = "";

        switch (getSelectedColum()) {
            case 6:
                arg = "APORTE 1";
                break;
            case 7:
                arg = "EXAMEN INTERCICLO";
                break;
            case 8:
                arg = "NOTA INTERCICLO";
                break;
            case 9:
                arg = "APORTE 2";
                break;
            case 10:
                arg = "EXAMEN FINAL";
                break;
            case 11:
                arg = "EXAMEN SUPLETORIO";
                break;
            default:
                break;
        }
        alumno.getNotas()
                .stream()
                .filter(buscar(arg))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    System.out.println(obj);
                    try {
                        String valor = vista.getTblNotas().getValueAt(getSelectedRow(), getSelectedColum()).toString();
                        obj.setNotaValor(conversor(valor));
                        obj.editar();
                    } catch (NumberFormatException e) {
                    }
                });

        alumno.editar();

    }

    private static Predicate<NotasBD> buscar(String busqueda) {
        return item -> item.getTipoDeNota().getNombre().equals(busqueda);
    }

    private int getIdDocente() {
        listaDocentes
                .entrySet()
                .stream()
                .filter((entry) -> (entry.getKey().equals(vista.getCmbDocente().getSelectedItem().toString())))
                .collect(Collectors.toList())
                .forEach(entry -> {
                    idDocente = entry.getValue().getIdDocente();
                });

        return idDocente;

    }

    private int getIdPeriodoLectivo() {
        String periodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();

        listaPeriodos
                .stream()
                .filter(item -> item.getNombre_PerLectivo().equals(periodo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    idPeriodoLectivo = obj.getId_PerioLectivo();
                });
        return idPeriodoLectivo;
    }

    private int getSelectedRow() {
        return vista.getTblNotas().getSelectedRow();
    }

    private int getSelectedColum() {
        return vista.getTblNotas().getSelectedColumn();
    }

    private void activarForm(boolean estado) {
        vista.getTxtBuscar().setEnabled(estado);
        vista.getBtnBuscar().setEnabled(estado);
        vista.getCmbDocente().setEnabled(estado);
        vista.getCmbPeriodoLectivo().setEnabled(estado);
        vista.getCmbCiclo().setEnabled(estado);
        vista.getCmbParalelo().setEnabled(estado);
        vista.getCmbJornada().setEnabled(estado);
        vista.getCmbAsignatura().setEnabled(estado);
        vista.getTblNotas().setEnabled(estado);
    }

    private void cargarTabla() {
        new Thread(() -> {

            if (cargarTabla) {
                cargarTabla = false;
                tablaNotas.setRowCount(0);
                vista.getTblNotas().setEnabled(false);
                Middlewares.setLoadCursor(vista);

                try {

                    desktop.getLblEstado().setText("CARGANDO NOTAS");
                    String cursoNombre = vista.getCmbCiclo().getSelectedItem().toString();
                    String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
                    String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();

                    activarForm(false);

                    listaNotas = AlumnoCursoBD.selectWhere(cursoNombre, nombreMateria, getIdDocente(), nombrePeriodo);

                    listaNotas.stream().forEach(VtnNotas::agregarFilas);

                    activarForm(true);

                    vista.getLblResultados().setText(listaNotas.size() + " Resultados");

                    desktop.getLblEstado().setText("");
                    Middlewares.setDefaultCursor(vista);

                } catch (NullPointerException e) {
                    Middlewares.bugProcessor(vista);
                }
                cargarTabla = true;
                vista.getTblNotas().setEnabled(true);
                vista.getBtnImprimir().setEnabled(true);
            }

        }).start();

    }

    private static Consumer<NotasBD> agregar(Vector<Object> row) {
        return (objNota) -> {
            row.add(objNota.getNotaValor());
        };
    }

    private static void agregarFilas(AlumnoCursoBD obj) {

        Vector<Object> row = new Vector<>();

        row.add(tablaNotas.getDataVector().size() + 1);
        row.add(obj.getAlumno().getIdentificacion());
        row.add(obj.getAlumno().getPrimerApellido());
        row.add(obj.getAlumno().getSegundoApellido());
        row.add(obj.getAlumno().getPrimerNombre());
        row.add(obj.getAlumno().getSegundoNombre());

        obj.getNotas().stream().filter(buscar("APORTE 1")).forEach(agregar(row));
        obj.getNotas().stream().filter(buscar("EXAMEN INTERCICLO")).forEach(agregar(row));
        obj.getNotas().stream().filter(buscar("NOTA INTERCICLO")).forEach(agregar(row));
        obj.getNotas().stream().filter(buscar("APORTE 2")).forEach(agregar(row));
        obj.getNotas().stream().filter(buscar("EXAMEN FINAL")).forEach(agregar(row));
        obj.getNotas().stream().filter(buscar("EXAMEN SUPLETORIO")).forEach(agregar(row));

        row.add(obj.getNotaFinal());
        row.add(obj.getEstado());
        row.add(obj.getNumFalta());
        row.add(0);
        row.add(obj.getAsistencia());

        tablaNotas.addRow(row);

    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="EVENTOS"> 
    private void btnVerNotas(ActionEvent e) {
        if (cargarTabla) {
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!");
        }

    }
    // </editor-fold>  
}
