/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AvanceSilabo.SeguimientoSilaboBD;
import modelo.AvanceSilabo.SeguimientoSilaboMD;
import modelo.ConexionBD;
import modelo.PlanClases.JornadasDB;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.jornada.JornadaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.PeriodoLectivoBDS;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCRUDAvanceSilabo;

/**
 *
 * @author Daniel
 */
public class ControladorCRUDAvanceSilabo {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private boolean esCordinador = false;
    private List<CarreraMD> carreras_docente;
    private frmCRUDAvanceSilabo seguimiento;
    private VtnPrincipal vtnPrincipal;
    private int id_periodo_lectivo = -1;
    private List<CursoMD> lista_curso;
    private RolBD rol;
    private List<SeguimientoSilaboMD> lista_seguimiento;
    private List<JornadaMD> lista_jornadas;
    private List<PeriodoLectivoMD> periodosCarrera;
    private SeguimientoSilaboMD segui;

    public ControladorCRUDAvanceSilabo(UsuarioBD usuario, RolBD rol, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        this.usuario = usuario;
        this.conexion = conexion;
        this.vtnPrincipal = vtnPrincipal;
        this.rol = rol;
    }

    public void initCrud() {
        conexion.conectar();

        seguimiento = new frmCRUDAvanceSilabo();
        if (rol.getNombre().equalsIgnoreCase("COORDINADOR")) {
            seguimiento.getTlbAvanceSilabo().removeColumn(seguimiento.getTlbAvanceSilabo().getColumnModel().getColumn(4));

        } else {
            seguimiento.getTlbAvanceSilabo().removeColumn(seguimiento.getTlbAvanceSilabo().getColumnModel().getColumn(5));

        }
        if (rol.getNombre().equalsIgnoreCase("COORDINADOR")) {
            esCordinador = true;
            seguimiento.getBtnEditar().setEnabled(false);
            seguimiento.getBtnNuevo().setEnabled(false);
            seguimiento.getBtnEliminar().setEnabled(false);

        }
        vtnPrincipal.getDpnlPrincipal().add(seguimiento);
        seguimiento.setTitle("Avances de Sílabos");
        seguimiento.show();

        seguimiento.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - seguimiento.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - seguimiento.getSize().height) / 2);
        seguimiento.getBtnNuevo().addActionListener(e -> insertar());
        seguimiento.getBtnEditar().addActionListener((ActionEvent ae) -> {
            int row = seguimiento.getTlbAvanceSilabo().getSelectedRow();
            if (row != -1) {

                seguimiento.dispose();
                FrmEditarAvanceSilaboCTR ce = new FrmEditarAvanceSilaboCTR(usuario, seguimientoSilabo(), vtnPrincipal, curso_selecc(), conexion);
                ce.init();

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un avance de silabo", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
        seguimiento.getTlbAvanceSilabo().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int fila = seguimiento.getTlbAvanceSilabo().getSelectedRow();
                int columna = seguimiento.getTlbAvanceSilabo().getSelectedColumn();
                if (esCordinador && columna == 4) {
                    if (seguimiento.getTlbAvanceSilabo().getValueAt(fila, columna).equals(true)) {
                        new SeguimientoSilaboBD(conexion).aprobarSeguimientoSilabo(Integer.parseInt(seguimiento.getTlbAvanceSilabo().getValueAt(fila, columna - 4).toString()), 1);
                    } else {
                        new SeguimientoSilaboBD(conexion).aprobarSeguimientoSilabo(Integer.parseInt(seguimiento.getTlbAvanceSilabo().getValueAt(fila, columna - 4).toString()), 0);
                    }
                }

            }
        });
        seguimiento.getBtnEliminar().addActionListener(a -> {
            int row = seguimiento.getTlbAvanceSilabo().getSelectedRow();
            if (row != -1) {
                eliminarSeguimientos();
                cargarAvanceSilaboDocentes_Coordinador();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un seguimiento de silabo", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
        CARGAR_COMBO_CARRERAS();
        CARGAR_COMBO_PERIODOS_CARRERA();
        CARGAR_JORNADAS();
        cargarAvanceSilaboDocentes_Coordinador();

        seguimiento.getCmb_Carreras().addActionListener(a -> cargarAvanceSilaboDocentes_Coordinador());
        seguimiento.getCmb_Carreras().addActionListener(a -> CARGAR_COMBO_PERIODOS_CARRERA());
        seguimiento.getCmbJornadas().addActionListener(a -> cargarAvanceSilaboDocentes_Coordinador());
        seguimiento.getCmb_periodos().addActionListener(a -> cargarAvanceSilaboDocentes_Coordinador());
        seguimiento.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    cargarAvanceSilaboDocentes_Coordinador();
                }
            }
        });
        seguimiento.getBtn_Imprimir().addActionListener(e -> imprimir());

    }

    public void insertar() {

        seguimiento.dispose();
        ControladorConfiguracionAvanceSilabo AS = new ControladorConfiguracionAvanceSilabo(usuario, vtnPrincipal, conexion);
        AS.init();
    }

    private void cargarAvanceSilaboDocentes_Coordinador() {
        try {
            DefaultTableModel modelotabla;
            modelotabla = (DefaultTableModel) seguimiento.getTlbAvanceSilabo().getModel();
            String[] parametros = {seguimiento.getCmb_Carreras().getSelectedItem().toString(), seguimiento.getCmbJornadas().getSelectedItem().toString(), seguimiento.getTxtBuscar().getText(), String.valueOf(usuario.getPersona().getIdPersona()),
                String.valueOf(getid_periodo())};
            if (esCordinador) {
                String[] parametros1 = {seguimiento.getCmb_Carreras().getSelectedItem().toString(), seguimiento.getCmbJornadas().getSelectedItem().toString(), seguimiento.getTxtBuscar().getText(),
                    String.valueOf(getid_periodo())};
                lista_seguimiento = SeguimientoSilaboBD.consultarSeguimientoSilaboCoordinador(conexion, parametros1);
            } else {

                lista_seguimiento = SeguimientoSilaboBD.consultarSeguimientoSilaboDocentes(conexion, parametros);
            }
            for (int j = seguimiento.getTlbAvanceSilabo().getModel().getRowCount() - 1; j >= 0; j--) {
                modelotabla.removeRow(j);
            }
            for (SeguimientoSilaboMD ss : lista_seguimiento) {
                String estado = null;
                Boolean estadoB = null;
                String corresponde = null;

                if (ss.getEstado_seguimiento() == 0) {
                    estado = "Por Aprobar";
                    estadoB = false;
                } else {
                    estado = "Aprobado";
                    estadoB = true;
                }
                if (ss.isEsInterciclo() == true) {
                    corresponde = "Interciclo";
                } else {
                    corresponde = "Fin de Ciclo";
                }
                modelotabla.addRow(new Object[]{
                    ss.getId_seguimientoS(), ss.getPersona().getPrimerApellido() + " "
                    + ss.getPersona().getPrimerNombre(), ss.getMateria().getNombre(),
                    ss.getCurso().getNombre(), estado, estadoB, ss.getFecha_entrega_informe(), corresponde
                });

            }
            seguimiento.getTlbAvanceSilabo().setModel(modelotabla);
        } catch (Exception e) {
        }

    }

    private void CARGAR_COMBO_CARRERAS() {
        seguimiento.getCmb_Carreras().removeAllItems();
        carreras_docente = new ArrayList<>();
        if (esCordinador) {
            carreras_docente.add(new CarrerasBDS(conexion).retornaCarreraCoordinador(usuario.getUsername()));
        } else {
            carreras_docente = CarrerasBDS.consultar(conexion, usuario.getUsername());
        }

        carreras_docente.forEach((cmd) -> {
            seguimiento.getCmb_Carreras().addItem(cmd.getNombre());
        });

        seguimiento.getCmb_Carreras().setSelectedIndex(0);
    }

    private void CARGAR_JORNADAS() {
        seguimiento.getCmbJornadas().removeAllItems();
        lista_jornadas = JornadasDB.consultarJornadas(conexion);
        lista_jornadas.forEach((lj) -> {
            seguimiento.getCmbJornadas().addItem(lj.getNombre());
        });

    }

    private void CARGAR_COMBO_PERIODOS_CARRERA() {
        seguimiento.getCmb_periodos().removeAllItems();
        List<PeriodoLectivoMD> periodos = PeriodoLectivoBDS.consultarPeriodos(conexion, seguimiento.getCmb_Carreras().getSelectedItem().toString());
        if (periodos == null) {
            JOptionPane.showMessageDialog(null, "No existen Periodos");
        } else {

            periodos.forEach((prd) -> {
                seguimiento.getCmb_periodos().addItem(prd.getNombre());
            });
        }

    }

    private List<PeriodoLectivoMD> cargarPeriodos() {
        List<PeriodoLectivoMD> periodos = PeriodoLectivoBDS.consultarPeriodos(conexion, seguimiento.getCmb_Carreras().getSelectedItem().toString());
        return periodos;
    }

    private int getid_periodo() {
        String nombre_periodo = seguimiento.getCmb_periodos().getSelectedItem().toString();
        periodosCarrera = cargarPeriodos();
        periodosCarrera
                .stream()
                .filter(item -> item.getNombre().equals(nombre_periodo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    id_periodo_lectivo = obj.getID();
                });

        return id_periodo_lectivo;
    }

    //UTILIZA ESTE METODO PARA EL IMPRIMIR
    private SeguimientoSilaboMD seguimientoSilabo() {
        int seleccion = seguimiento.getTlbAvanceSilabo().getSelectedRow();
        segui = new SeguimientoSilaboMD();
        segui = SeguimientoSilaboBD.consultarIDsegui_IdCurso(conexion, Integer.parseInt(
                seguimiento.getTlbAvanceSilabo().getValueAt(seleccion, 0).toString()));
        return segui;
    }

    private CursoMD curso_selecc() {
        int seleccion = seguimiento.getTlbAvanceSilabo().getSelectedRow();
        lista_curso = CursosBDS.Consultarcursos(conexion, usuario.getPersona().getIdPersona(), getid_periodo(), seguimiento.getTlbAvanceSilabo().getValueAt(seleccion, 2).toString());
        Optional<CursoMD> curso_selecccionado = lista_curso.stream().filter(lc -> lc.getNombre().equals(seguimiento.getTlbAvanceSilabo().getValueAt(seleccion, 3).toString())).findFirst();
        return curso_selecccionado.get();
    }

    private void eliminarSeguimientos() {
        int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este seguimiento de silabo?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            new SeguimientoSilaboBD(conexion).eliminarSeguimientoSilabo(seguimientoSilabo());
            JOptionPane.showMessageDialog(null, "Seguimiento de silabo eliminado correctamente");
        }
    }

    public void imprimir() {
        int seleccion = seguimiento.getTlbAvanceSilabo().getSelectedRow();

        if (seleccion >= 0) {
            CarreraMD ca = new CarrerasBDS(conexion).retornaModalidad(seguimientoSilabo().getCurso().getId());
            if (ca.getModalidad().equalsIgnoreCase("PRESENCIAL")) {
                if (seguimientoSilabo().isEsInterciclo() == false) {
                    try {

                        JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/avance_silabo_fin/avance_principal.jasper"));
                        Map parametro = new HashMap();

                        parametro.put("id_curso", String.valueOf(seguimientoSilabo().getCurso().getId()));
                        parametro.put("id_seguimiento", String.valueOf(seguimientoSilabo().getId_seguimientoS()));

                        JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());   
                        JasperViewer pv = new JasperViewer(jp, false);
                        pv.setVisible(true);
                        pv.setTitle("Avance de silabo (fin de ciclo)");
                        //principal.add(pv);
                    } catch (JRException ex) {
                        Logger.getLogger(ControladorCRUDAvanceSilabo.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                    try {


                        JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/avance_de_silabo/avance_principal.jasper"));
                        Map parametro = new HashMap();

                        parametro.put("id_curso", String.valueOf(seguimientoSilabo().getCurso().getId()));
                        parametro.put("id_seguimiento", String.valueOf(seguimientoSilabo().getId_seguimientoS()));

                        JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
                        JasperViewer pv = new JasperViewer(jp, false);
                        pv.setVisible(true);
                        pv.setTitle("Avance de silabo(interciclo)");
                        //principal.add(pv);
                    } catch (JRException ex) {
                        Logger.getLogger(ControladorCRUDAvanceSilabo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (ca.getModalidad().equalsIgnoreCase("DUAL")) {
                if (seguimientoSilabo().isEsInterciclo() == false) {
                
                
                 try {

                        JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/avance_silabo_fin/avance_principal.jasper"));
                        Map parametro = new HashMap();

                        parametro.put("id_curso", String.valueOf(seguimientoSilabo().getCurso().getId()));
                        parametro.put("id_seguimiento", String.valueOf(seguimientoSilabo().getId_seguimientoS()));

                        JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
                        JasperViewer pv = new JasperViewer(jp, false);
                        pv.setVisible(true);
                        pv.setTitle("Avance de silabo (fin de ciclo)");
                        //principal.add(pv);
                    } catch (JRException ex) {
                        Logger.getLogger(ControladorCRUDAvanceSilabo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 
                
                
                }
                else{
                try {

                    JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/avance_silabo_inter_dual/avance_principal.jasper"));
                    Map parametro = new HashMap();

                    parametro.put("id_curso", String.valueOf(seguimientoSilabo().getCurso().getId()));
                    parametro.put("id_seguimiento", String.valueOf(seguimientoSilabo().getId_seguimientoS()));

                    JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
                    JasperViewer pv = new JasperViewer(jp, false);
                    pv.setVisible(true);
                    pv.setTitle("Avance de silabo(interciclo)");
                    //principal.add(pv);
                } catch (JRException ex) {
                    Logger.getLogger(ControladorCRUDAvanceSilabo.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "DEBE SELECIONAR EL DOCUMENTO PARA IMPRIMIR");
        }

    }

}
