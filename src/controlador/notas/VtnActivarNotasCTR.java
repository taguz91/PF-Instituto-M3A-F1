package controlador.notas;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.tipoDeNota.IngresoNotasBD;
import modelo.tipoDeNota.IngresoNotasMD;
import modelo.usuario.RolBD;
import vista.notas.VtnActivarNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Alejandro
 */
public class VtnActivarNotasCTR {

    private final VtnPrincipal desktop;
    private final VtnActivarNotas vista;
    private IngresoNotasBD modelo;
    private final RolBD permisos;

    //Conexion
    private final ConectarDB conexion;

    //TABLA
    private static DefaultTableModel tablaActivarNotas;

    //LISTA
    private List<IngresoNotasBD> listaNotasActivadas;

    private boolean cargarTabla = true;
    private Thread thread;

    public VtnActivarNotasCTR(VtnPrincipal desktop, VtnActivarNotas vista, RolBD permisos, ConectarDB conexion) {
        this.desktop = desktop;
        this.vista = vista;
        this.permisos = permisos;
        this.conexion = conexion;
    }

    //INIT
    public void Init() {
        tablaActivarNotas = (DefaultTableModel) vista.getTblCursoTipoNotas().getModel();
        InitEventos();
        cargarTabla();

        try {
            vista.show();
            desktop.getDpnlPrincipal().add(vista);

            Effects.centerFrame(vista, desktop.getDpnlPrincipal());

            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnActivarNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        tablaActivarNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    System.out.println("--------->");

                    active = false;
                }

            }

        });

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarOnKeyReleased(e);
            }

        });

        vista.getBtnActualizar().addActionListener(e -> btnActualizar(e));

    }

    //METODOS DE APOYO
    private void setObj(int fila) {
        if (fila != -1) {

            modelo = new IngresoNotasBD();

            modelo.setIdIngresoNotas(Integer.parseInt(vista.getTblCursoTipoNotas().getValueAt(fila, 1).toString()));

            modelo.setNotaPrimerInterCiclo(Boolean.parseBoolean(vista.getTblCursoTipoNotas().getValueAt(fila, 7).toString()));

        }

    }

    private void cargarTabla() {

        if (cargarTabla) {
            thread = new Thread() {
                @Override
                public void run() {
                    Effects.setLoadCursor(vista);
                    cargarTabla = false;

                    desktop.getLblEstado().setText("CARGANDO...");

                    tablaActivarNotas.setRowCount(0);
                    listaNotasActivadas = IngresoNotasBD.selectAll();
                    listaNotasActivadas
                            .stream()
                            .forEach(VtnActivarNotasCTR::agregarFila);

                    vista.getLblResultados().setText(listaNotasActivadas.size() + " Resultados");

                    cargarTabla = true;

                    Effects.setDefaultCursor(vista);

                    desktop.getLblEstado().setText("COMPLETADO");

                    try {
                        sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(VtnActivarNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    desktop.getLblEstado().setText("");
                    stopThread(this);
                }

            };
            thread.start();

        } else {

            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!!");

        }

    }

    private static void stopThread(Thread thread) {
        thread = null;
        System.gc();
    }

    private static void agregarFila(IngresoNotasMD obj) {
        tablaActivarNotas.addRow(new Object[]{
            tablaActivarNotas.getDataVector().size() + 1,
            obj.getCurso().getId_curso(),
            obj.getCurso().getId_prd_lectivo().getNombre_PerLectivo(),
            obj.getCurso().getCurso_nombre(),
            obj.getCurso().getId_materia().getNombre(),
            obj.getCurso().getId_docente().getPrimerNombre() + " " + obj.getCurso().getId_docente().getSegundoNombre(),
            obj.getCurso().getId_docente().getPrimerApellido() + " " + obj.getCurso().getId_docente().getSegundoApellido(),
            obj.isNotaPrimerInterCiclo(),
            obj.isNotaExamenInteCiclo(),
            obj.isNotaSegundoInterCiclo(),
            obj.isNotaExamenFinal(),
            obj.isNotaExamenDeRecuperacion()
        });
    }

    //EVENTOS
    private void txtBuscarOnKeyReleased(KeyEvent e) {

    }

    private void btnActualizar(ActionEvent e) {

        cargarTabla();

    }
}
