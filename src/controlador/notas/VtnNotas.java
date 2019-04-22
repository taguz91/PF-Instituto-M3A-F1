package controlador.notas;

import controlador.Libraries.Middlewares;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCursoBD;
import modelo.curso.CursoBD;
import modelo.jornada.JornadaBD;
import modelo.materia.MateriaBD;
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
            cargarComboParalelo();
            cargarComboJornadas();
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
            cargarComboParalelo();
        });

        vista.getCmbCiclo().addActionListener(e -> {
            cargarComboParalelo();
            cargarComboMaterias();
        });

        vista.getCmbParalelo().addActionListener(e -> cargarComboMaterias());

        vista.getCmbJornada().addActionListener(e -> cargarComboMaterias());

        vista.getBtnVerNotas().addActionListener(e -> btnVerNotas(e));

//        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));
//
//        vista.getBtnBuscar().addActionListener(e -> btnBuscar(e));
//
//        vista.getTblNotas().getTableHeader().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                SelectHeader(e);
//            }
//
//        });
//        vista.getTblNotas().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                SelectTextInRow(e);
//            }
//
//        });
        vista.getTblNotas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    //setObjFromTable(vista.getTblNotas().getSelectedRow()).editar();
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
            getIdPeriodoLectivo();
            CursoBD.selectCicloWhere(getIdDocente(), getIdPeriodoLectivo())
                    .stream()
                    .forEach(obj -> {
                        vista.getCmbCiclo().addItem(obj + "");
                    });
        } catch (NullPointerException e) {
            Middlewares.bugProcessor(e, vista);
        }
    }

    private void cargarComboParalelo() {

        try {
            vista.getCmbParalelo().removeAllItems();
            vista.getCmbAsignatura().removeAllItems();

            int ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());
            CursoBD.selectParaleloWhere(getIdDocente(), ciclo, getIdPeriodoLectivo())
                    .stream()
                    .forEach(vista.getCmbParalelo()::addItem);

        } catch (NullPointerException e) {
            Middlewares.bugProcessor(e, vista);
        }

    }

    private void cargarComboJornadas() {

        try {
            vista.getCmbJornada().removeAllItems();

            JornadaBD.selectJornadasWhere(getIdDocente(), getIdPeriodoLectivo())
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
    private AlumnoCursoBD setObjFromTable() {
        int fila = getSelectedRow();

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
    }

    private void cargarTabla() {
        new Thread(() -> {

            if (cargarTabla) {
                cargarTabla = false;

                Middlewares.setLoadCursor(vista);

                try {

                    tablaNotas.setRowCount(0);

                    desktop.getLblEstado().setText("CARGANDO NOTAS");
                    String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
                    String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();
                    String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
                    String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
                    Integer ciclo = Integer.parseInt(vista.getCmbCiclo().getSelectedItem().toString());
                    listaNotas = AlumnoCursoBD.selectWhere(paralelo, ciclo, nombreJornada, nombreMateria, idDocente, nombrePeriodo);

                    listaNotas.stream().forEach(VtnNotas::agregarFilas);

                    desktop.getLblEstado().setText("");
                    Middlewares.setDefaultCursor(vista);

                } catch (NullPointerException e) {
                    System.out.println(e);
                }
                cargarTabla = true;
                vista.getBtnImprimir().setEnabled(true);
            }

        }).start();

    }

    private static void agregarFilas(AlumnoCursoBD obj) {
        Vector<Object> row = new Vector<>();

        row.add(tablaNotas.getDataVector().size() + 1);
        row.add(obj.getAlumno().getIdentificacion());
        row.add(obj.getAlumno().getPrimerApellido() + obj.getAlumno().getSegundoApellido());
        row.add(obj.getAlumno().getPrimerNombre() + obj.getAlumno().getSegundoNombre());

        obj.getNotas().stream().forEach(objNota -> {
            row.add(objNota.getNotaValor());
        });

        row.add(obj.getEstado());
        row.add(obj.getNumFalta());
        row.add(0);
        row.add(obj.getAsistencia());

        tablaNotas.addRow(row);

    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="EVENTOS"> 
    private void btnVerNotas(ActionEvent e) {
        System.out.println("---------");
        if (cargarTabla) {
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!");
        }

    }
    // </editor-fold>  
}
