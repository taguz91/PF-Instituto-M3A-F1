package controlador.notas;

import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import controlador.notas.ux.RowStyle;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.tipoDeNota.TipoDeNotaMD;
import modelo.usuario.UsuarioBD;
import vista.notas.VtnNotasAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnNotas {

    private VtnPrincipal desktop;
    private static VtnNotasAlumnoCurso vista;
    private AlumnoCursoBD modelo;
    private UsuarioBD usuario;

    //LISTAS
    private Map<String, DocenteMD> listaDocentes;
    private List<PeriodoLectivoMD> listaPeriodos;
    private static List<AlumnoCursoBD> listaNotas;
    private static List<MateriaMD> listaMaterias;
    private static List<TipoDeNotaMD> listaValidaciones;

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

        vista.canEdit[1] = true;

        tablaNotas = (DefaultTableModel) vista.getTblNotas().getModel();

        listaDocentes = DocenteBD.selectAll();

        new Thread(() -> {
            activarForm(false);
            cargarComboDocente();
            cargarComboPeriodos();
            cargarComboCiclo();
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

        tablaNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    carlcularNotas(tablaNotas);

                    active = false;
                }

            }

        });
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="METODOS DE APOYO">    
    /*
        METODOS DE CARGA
     */
    private void carlcularNotas(TableModel datos) {

        try {
            String nombreNota = "";
            switch (getSelectedColum()) {
                case 6:
                    nombreNota = "APORTE 1";

                    String aporte1 = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(aporte1, nombreNota, datos);

                    break;
                case 7:
                    nombreNota = "EXAMEN INTERCICLO";

                    String examenInterCiclo = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(examenInterCiclo, nombreNota, datos);
                    break;
                case 9:
                    nombreNota = "APORTE 2";

                    String aporte2 = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(aporte2, nombreNota, datos);
                    break;
                case 10:
                    nombreNota = "EXAMEN FINAL";

                    String examenFinal = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(examenFinal, nombreNota, datos);
                    break;
                case 11:
                    nombreNota = "EXAMEN SUPLETORIO";

                    String examenSupletorio = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(examenSupletorio, nombreNota, datos);
                    break;

                case 14:
                    String materia = vista.getCmbAsignatura().getSelectedItem().toString();
                    String value = tablaNotas.getValueAt(getSelectedRow(), 14).toString();

                    if (Validaciones.isInt(value)) {
                        int faltas = (int) Middlewares.conversor(value);
                        setFaltas(materia, faltas);
                        editar();
                    } else {
                        JOptionPane.showMessageDialog(vista, "INGRESE SOLO NUMEROS ENTEROS!!");
                    }

                    refreshTabla();
                    break;
                default:
                    break;
            }

        } catch (NumberFormatException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void setFaltas(String materia, int faltas) {
        listaMaterias
                .stream()
                .filter(item -> item.getNombre().equals(materia))
                .collect(Collectors.toList())
                .forEach(setPorcentaje(faltas));

    }

    private static void guardarBD(String nota, String nombreNota, TableModel datos) {
        if (Validaciones.isDecimal(nota)) {
            double value = Middlewares.conversor(nota);

            TipoDeNotaMD rango = getRango(nombreNota);
            if (value >= 0 && value <= rango.getValorMaximo()) {
                datos.setValueAt(Middlewares.conversor(nota), getSelectedRow(), getSelectedColum());
                sumarColumnas();
                editar();
                refreshTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "EL RANGO DE LA NOTA DEBE ESTAR ENTRE: " + rango.getValorMinimo() + " Y " + rango.getValorMaximo());
                refreshTabla();
            }
        } else {
            mensajeDeError();
            refreshTabla();
        }
    }

    private static void sumarColumnas() {
        int fila = getSelectedRow();

        double aporte1 = 0;
        double examenInterCiclo = 0;
        double totalInterciclo = 0;

        double aporte2 = 0;
        double examenFinal = 0;
        double examenSupletorio = 0;

        double notaFinal = 0;
        String estado = "";

        aporte1 = Middlewares.conversor(tablaNotas.getValueAt(fila, 6).toString());
        examenInterCiclo = Middlewares.conversor(tablaNotas.getValueAt(fila, 7).toString());
        totalInterciclo = aporte1 + examenInterCiclo;
        tablaNotas.setValueAt(totalInterciclo, fila, 8);

        aporte2 = Middlewares.conversor(tablaNotas.getValueAt(fila, 9).toString());
        examenFinal = Middlewares.conversor(tablaNotas.getValueAt(fila, 10).toString());
        examenSupletorio = Middlewares.conversor(tablaNotas.getValueAt(fila, 11).toString());

        if (examenSupletorio != 0) {
            notaFinal = totalInterciclo + aporte2 + examenSupletorio;
        } else {
            notaFinal = totalInterciclo + aporte2 + examenFinal;
        }

        tablaNotas.setValueAt(Math.round(notaFinal), fila, 12);

    }

    private static Consumer<MateriaMD> setPorcentaje(int faltas) {
        return obj -> {
            int porcentaje = (faltas * obj.getTotalHoras()) / 100;
            vista.getTblNotas().setValueAt(porcentaje, getSelectedRow(), getSelectedRow());
            vista.getTblNotas().setValueAt(porcentaje, getSelectedRow(), 15);
            if (porcentaje >= 25) {
                vista.getTblNotas().setValueAt("Reprobado", getSelectedRow(), 13);
            } else {
                vista.getTblNotas().setValueAt("Aprobado", getSelectedRow(), 13);
            }
        };
    }

    private static void mensajeDeError() {
        JOptionPane.showMessageDialog(vista, "INGRESE UN NUMERO CORRECTO "
                + "\n       EJEMPLO (15.6)");
    }

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

            listaValidaciones = TipoDeNotaBD.selectValidaciones(getIdPeriodoLectivo());

            validarCombos();
        } catch (NullPointerException e) {
            Middlewares.bugProcessor(e, vista);
            vista.getCmbAsignatura().removeAllItems();
        }

    }

    private static void refreshTabla() {
        System.out.println("REFRESH");
        tablaNotas.setRowCount(0);
        new Thread(() -> {
            listaNotas.stream().forEach(VtnNotas::agregarFilas);
        }).start();
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
    private static void editar() {
        vista.getTblNotas().setEnabled(false);
        int fila = getSelectedRow();

        AlumnoCursoBD alumno = listaNotas.get(fila);

        alumno.setEstado(vista.getTblNotas().getValueAt(fila, 13).toString());
        alumno.setNumFalta(Integer.valueOf(vista.getTblNotas().getValueAt(fila, 14).toString()));
        alumno.setAsistencia(vista.getTblNotas().getValueAt(fila, 16).toString());

        alumno.getNotas().stream()
                .filter(buscar("APORTE 1"))
                .collect(Collectors.toList())
                .forEach(editarNota(6));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN INTERCICLO"))
                .collect(Collectors.toList())
                .forEach(editarNota(7));
        alumno.getNotas().stream()
                .filter(buscar("NOTA INTERCICLO"))
                .collect(Collectors.toList())
                .forEach(editarNota(8));
        alumno.getNotas().stream()
                .filter(buscar("APORTE 2"))
                .collect(Collectors.toList())
                .forEach(editarNota(9));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN FINAL"))
                .collect(Collectors.toList())
                .forEach(editarNota(10));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN SUPLETORIO"))
                .collect(Collectors.toList())
                .forEach(editarNota(11));

        alumno.setNotaFinal(Middlewares.conversor(vista.getTblNotas().getValueAt(fila, 12).toString()));

        alumno.editar();
        vista.getTblNotas().setEnabled(true);
    }

    private static TipoDeNotaMD getRango(String nombreNota) {

        System.out.println(listaValidaciones);

        List<TipoDeNotaMD> listaTemporal = listaValidaciones
                .stream()
                .filter(item -> item.getNombre().equals(nombreNota))
                .collect(Collectors.toList());

        return listaTemporal.get(0);

    }

    private static Predicate<NotasBD> buscar(String busqueda) {
        return item -> item.getTipoDeNota().getNombre().equals(busqueda);
    }

    private static Consumer<NotasBD> editarNota(int columna) {
        return obj -> {
            String text = vista.getTblNotas().getValueAt(getSelectedRow(), columna).toString();
            obj.setNotaValor(Middlewares.conversor(text));
            obj.editar();
        };
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

    private static int getSelectedRow() {
        return vista.getTblNotas().getSelectedRow();
    }

    private static int getSelectedColum() {
        return vista.getTblNotas().getSelectedColumn();
    }

    private void activarForm(boolean estado) {
        vista.getTxtBuscar().setEnabled(estado);
        vista.getBtnBuscar().setEnabled(estado);
        vista.getCmbDocente().setEnabled(estado);
        vista.getCmbPeriodoLectivo().setEnabled(estado);
        vista.getCmbCiclo().setEnabled(estado);
        vista.getCmbAsignatura().setEnabled(estado);
        vista.getTblNotas().setEnabled(estado);
    }

    private void cargarTabla() {
        new Thread(() -> {

            if (cargarTabla) {
                RowStyle row = new RowStyle(13);

                vista.getTblNotas().setDefaultRenderer(Object.class, row);

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

    private static Consumer<NotasBD> agregar(Vector<Object> row, int posicion) {
        return (objNota) -> {
            row.add(posicion, objNota.getNotaValor());
        };
    }

    private static void agregarFilas(AlumnoCursoBD obj) {

        Vector<Object> row = new Vector<>();

        row.add(0, tablaNotas.getDataVector().size() + 1);
        row.add(1, obj.getAlumno().getIdentificacion());
        row.add(2, obj.getAlumno().getPrimerApellido());
        row.add(3, obj.getAlumno().getSegundoApellido());
        row.add(4, obj.getAlumno().getPrimerNombre());
        row.add(5, obj.getAlumno().getSegundoNombre());

        obj.getNotas().stream().filter(buscar("APORTE 1")).forEach(agregar(row, 6));
        obj.getNotas().stream().filter(buscar("EXAMEN INTERCICLO")).forEach(agregar(row, 7));
        obj.getNotas().stream().filter(buscar("NOTA INTERCICLO")).forEach(agregar(row, 8));
        obj.getNotas().stream().filter(buscar("APORTE 2")).forEach(agregar(row, 9));
        obj.getNotas().stream().filter(buscar("EXAMEN FINAL")).forEach(agregar(row, 10));
        obj.getNotas().stream().filter(buscar("EXAMEN SUPLETORIO")).forEach(agregar(row, 11));

        row.add(12, obj.getNotaFinal());

        int faltas = obj.getNumFalta();

        String materia = vista.getCmbAsignatura().getSelectedItem().toString();

        listaMaterias.stream().filter(item -> item.getNombre().equals(materia))
                .forEach(setPorcentajeVetor(row, faltas, obj));

        row.add(16, obj.getAsistencia());
        tablaNotas.addRow(row);

    }

    private static Consumer<MateriaMD> setPorcentajeVetor(Vector<Object> row, int faltas, AlumnoCursoBD alumno) {
        return obj -> {
            int porcentaje = (faltas * 100) / obj.getTotalHoras();

            List<TipoDeNotaMD> listaTemporal = listaValidaciones
                    .stream()
                    .filter(item -> item.getNombre().equals("NOTA FINAL"))
                    .collect(Collectors.toList());
            TipoDeNotaMD valid = listaTemporal.get(0);

            if (porcentaje >= 25 || alumno.getNotaFinal() < valid.getValorMinimo()) {
                row.add(13, "REPROBADO");
            } else {
                row.add(13, "APROBADO");
            }
            row.add(14, faltas);
            row.add(15, porcentaje);
        };
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