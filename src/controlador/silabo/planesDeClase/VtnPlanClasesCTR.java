/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planesDeClase;

import controlador.principal.VtnPrincipalCTR;
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
import utils.CONS;
import modelo.ConexionBD;
import modelo.PlanClases.JornadasDB;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.jornada.JornadaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.PeriodoLectivoBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.principal.VtnPrincipal;
import vista.silabos.planesDeClase.VtnPlanClase;
import vista.silabos.frmCargando1;

/**
 *
 * @author Daniel
 */
public class VtnPlanClasesCTR {

    private final UsuarioBD usuario;
    private final ConexionBD conexion;
    private final VtnPrincipal principal;
    private VtnPlanClase vista;
    private List<SilaboMD> silabosDocente;
    private List<CarreraMD> carrerasDocente;
    private List<JornadaMD> jornadas;
    private List<PlandeClasesMD> planes;
    private List<PeriodoLectivoMD> periodosCarrera;
    private List<UnidadSilaboMD> unidadesSilabo;
    private List<CursoMD> lista_curso;
    private int idPeriodo = -1;
    private boolean esCordinador = false;
    private PlandeClasesMD planMD;
    private final RolBD rol;

    private VtnPrincipalCTR desktop;

    private final PlandeClasesBD CON = PlandeClasesBD.single();

    public VtnPlanClasesCTR(UsuarioBD usuario, RolBD rol, ConexionBD conexion, VtnPrincipal principal) {
        this.usuario = usuario;
        this.conexion = conexion;
        this.principal = principal;
        this.rol = rol;
    }

    public VtnPrincipalCTR getDesktop() {
        return desktop;
    }

    public void setDesktop(VtnPrincipalCTR desktop) {
        this.desktop = desktop;
    }

    public void iniciaControlador() {
        conexion.conectar();

        vista = new VtnPlanClase();
        if (rol.getNombre().equalsIgnoreCase("COORDINADOR")) {
            vista.getTbl().removeColumn(vista.getTbl().getColumnModel().getColumn(5));
            vista.getBtn_editar_fecha().setVisible(true);
        } else {
            vista.getTbl().removeColumn(vista.getTbl().getColumnModel().getColumn(6));
            vista.getBtn_editar_fecha().setVisible(false);

        }
        if (rol.getNombre().equalsIgnoreCase("COORDINADOR")) {
            esCordinador = true;
            vista.getBtnNuevoPLC().setEnabled(false);
            vista.getBtnEditarPLC().setEnabled(false);
            vista.getBtnEliminarPLC().setEnabled(false);
        }
        vista.getTbl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int fila = vista.getTbl().getSelectedRow();
                int columna = vista.getTbl().getSelectedColumn();
                if (esCordinador && columna == 5) {
                    if (vista.getTbl().getValueAt(fila, columna).equals(true)) {
                        new PlandeClasesBD(conexion).aprobarPlanClase(Integer.parseInt(vista.getTbl().getValueAt(fila, columna - 5).toString()), 1);
                    } else {
                        new PlandeClasesBD(conexion).aprobarPlanClase(Integer.parseInt(vista.getTbl().getValueAt(fila, columna - 5).toString()), 0);
                    }
                }

            }

        });
        principal.getDpnlPrincipal().add(vista);
        vista.setTitle("PLANES DE CLASE");
        vista.show();
        CARGAR_COMBO_CARRERAS();
        CARGAR_COMBO_PERIODOS_CARRERA();
        CARGAR_JORNADAS();
        cargarPlanesDeClaseProfesor();
        getid_periodo();

        vista.setLocation((principal.getDpnlPrincipal().getSize().width - vista.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - vista.getSize().height) / 2);

        vista.getBtnNuevoPLC().addActionListener(a -> {
            vista.dispose();
            ControladorConfiguracion_plan_clases cp = new ControladorConfiguracion_plan_clases(usuario, principal, conexion);
            cp.iniciarControlaador();
        });
        vista.getCmb_Carreras().addActionListener(a -> cargarPlanesDeClaseProfesor());
        vista.getCmb_Carreras().addActionListener(a -> CARGAR_COMBO_PERIODOS_CARRERA());
        vista.getCmbJornadas().addActionListener(a -> cargarPlanesDeClaseProfesor());
        vista.getCmb_periodos().addActionListener(a -> cargarPlanesDeClaseProfesor());

        vista.getBtnCopiar().addActionListener(this::btnCopiar);

        vista.getTxtBuscarPLC().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    cargarPlanesDeClaseProfesor();
                }
            }
        });

        vista.getBtnEliminarPLC().addActionListener((ActionEvent ae) -> {
            int row = vista.getTbl().getSelectedRow();
            if (row != -1) {
                eliminarPlanClase();
                cargarPlanesDeClaseProfesor();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un plan de clase", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
        vista.getBtnEditarPLC().addActionListener((ActionEvent ae) -> {
            int row = vista.getTbl().getSelectedRow();
            if (row != -1) {
                if (!vista.getTbl().getValueAt(row, 5).equals("Aprobado")) {
                    ControladorEditarPlanClases ce = new ControladorEditarPlanClases(usuario, plan_clas_selecc(),
                            principal, conexion, curso_selecc(), silabo_seleccionado(), unidad_seleccionada());
                    ce.iniciaControlador();
                    vista.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "No puede editar planes de clase aprobados!", "Aviso", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un plan de clase", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
        vista.getBtn_editar_fecha().addActionListener((ActionEvent ae) -> {
            int row = vista.getTbl().getSelectedRow();
            if (row != -1) {
                ControladorEditarFechaGenPlanClase cep = new ControladorEditarFechaGenPlanClase(conexion, principal, plan_clas_id_c_u());
                cep.iniciaControlador();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un plan de clase", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });

        vista.getBtnImplimirPlan().addActionListener(e -> ejecutar(e));
        InitPermisos();
    }

    private void cargarPlanesDeClaseProfesor() {
        try {
            DefaultTableModel modelotabla;
            modelotabla = (DefaultTableModel) vista.getTbl().getModel();
            String[] parametros = {vista.getCmb_Carreras().getSelectedItem().toString(), vista.getCmbJornadas().getSelectedItem().toString(), vista.getTxtBuscarPLC().getText(), String.valueOf(usuario.getPersona().getIdPersona()),
                String.valueOf(getid_periodo())};
            if (esCordinador) {
                String[] parametros1 = {vista.getCmb_Carreras().getSelectedItem().toString(), vista.getCmbJornadas().getSelectedItem().toString(), vista.getTxtBuscarPLC().getText(),
                    String.valueOf(getid_periodo())};
                planes = PlandeClasesBD.consultarPlanClaseCoordinador(conexion, parametros1);
            } else {

                planes = PlandeClasesBD.consultarPlanClase(conexion, parametros);
            }
            for (int j = vista.getTbl().getModel().getRowCount() - 1; j >= 0; j--) {
                modelotabla.removeRow(j);
            }

            for (PlandeClasesMD plc : planes) {
                String estado = null;
                Boolean estadoB = null;

                if (plc.getEstado_plan() == 0) {
                    estado = "Por Aprobar";
                    estadoB = false;
                } else {
                    estado = "Aprobado";
                    estadoB = true;
                }
                modelotabla.addRow(new Object[]{
                    plc.getID(), plc.getId_persona().getPrimerApellido() + " " + plc.getId_persona().getPrimerNombre(), plc.getId_materia().getNombre(), plc.getId_curso().getNombre(), plc.getId_unidad().getIdUnidad(),
                    estado, estadoB, plc.getFecha_generacion()
                });
            }

            vista.getTbl().setModel(modelotabla);
        } catch (Exception e) {
        }

    }

    private void CARGAR_COMBO_PERIODOS_CARRERA() {
        vista.getCmb_periodos().removeAllItems();
        List<PeriodoLectivoMD> periodos = PeriodoLectivoBDS.consultarPeriodos(conexion, vista.getCmb_Carreras().getSelectedItem().toString());
        if (periodos == null) {
            JOptionPane.showMessageDialog(null, "No existen Periodos");
        } else {

            periodos.forEach((prd) -> {
                vista.getCmb_periodos().addItem(prd.getNombre());
            });
        }

    }

    private void CARGAR_COMBO_CARRERAS() {
        vista.getCmb_Carreras().removeAllItems();
        carrerasDocente = new ArrayList<>();
        if (esCordinador) {
            carrerasDocente.add(new CarrerasBDS(conexion).retornaCarreraCoordinador(usuario.getUsername()));
        } else {
            carrerasDocente = CarrerasBDS.consultar(conexion, usuario.getUsername());
        }

        carrerasDocente.forEach((cmd) -> {
            vista.getCmb_Carreras().addItem(cmd.getNombre());
        });

        vista.getCmb_Carreras().setSelectedIndex(0);
    }

    private void CARGAR_JORNADAS() {
        vista.getCmbJornadas().removeAllItems();
        jornadas = JornadasDB.consultarJornadas(conexion);
        jornadas.forEach((lj) -> {
            vista.getCmbJornadas().addItem(lj.getNombre());
        });

    }

    private PlandeClasesMD plan_clas_selecc() {
        int seleccion = vista.getTbl().getSelectedRow();
        Optional<PlandeClasesMD> plan_clase_selec = planes.stream().
                filter(pl -> pl.getID() == Integer.parseInt(vista.getTbl().getValueAt(seleccion, 0).toString())).findFirst();

        return plan_clase_selec.get();
    }

    private PlandeClasesMD plan_clas_id_c_u() {
        int seleccion = vista.getTbl().getSelectedRow();
        planMD = new PlandeClasesMD();
        planMD = PlandeClasesBD.consultarIDCURSO_ID_UNIDAD(conexion, Integer.parseInt(vista.getTbl().getValueAt(seleccion, 0).toString()));
        return planMD;
    }

    private CursoMD curso_selecc() {
        int seleccion = vista.getTbl().getSelectedRow();
        lista_curso = CursosBDS.Consultarcursos(conexion, usuario.getPersona().getIdPersona(), getid_periodo(), vista.getTbl().getValueAt(seleccion, 2).toString());
        Optional<CursoMD> curso_selecccionado = lista_curso.stream().filter(lc -> lc.getNombre().equals(vista.getTbl().getValueAt(seleccion, 3).toString())).findFirst();
        return curso_selecccionado.get();
    }

    private void eliminarPlanClase() {
        int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este plan de clase?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            new PlandeClasesBD(conexion).eliminarPlanClase(plan_clas_selecc());
            JOptionPane.showMessageDialog(null, "Plan de clase eliminado correctamente");
        }
    }

    private List<PeriodoLectivoMD> cargarPeriodos() {
        List<PeriodoLectivoMD> periodos = PeriodoLectivoBDS.consultarPeriodos(conexion, vista.getCmb_Carreras().getSelectedItem().toString());
        return periodos;
    }

    public void imprimir_plan() {
        int seleccion = vista.getTbl().getSelectedRow();
        if (seleccion >= 0) {
            try {

                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/plan_de_clase/planClasePagPrincipal.jasper"));
                Map parametro = new HashMap();

                parametro.put("id_unidad", String.valueOf(plan_clas_id_c_u().getId_unidad().getIdUnidad()));
                parametro.put("id_curso", String.valueOf(plan_clas_id_c_u().getId_curso().getId()));
                parametro.put("id_plan_clase", String.valueOf(plan_clas_selecc().getID()));

                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
                JasperViewer pv = new JasperViewer(jp, false);
                pv.setVisible(true);
                pv.setTitle("PLAN DE CLASES");
                //principal.add(pv);
            } catch (JRException ex) {
                Logger.getLogger(VtnPlanClasesCTR.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "DEBE SELECIONAR EL DOCUMENTO PARA IMPRIMIR");
        }

    }

    private int getid_periodo() {
        String nombre_periodo = vista.getCmb_periodos().getSelectedItem().toString();
        periodosCarrera = cargarPeriodos();
        periodosCarrera
                .stream()
                .filter(item -> item.getNombre().equals(nombre_periodo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    idPeriodo = obj.getID();
                });

        return idPeriodo;
    }

    public List<SilaboMD> cargar_silabo() {
        String[] parametros = {vista.getCmb_Carreras().getSelectedItem().toString(), String.valueOf(usuario.getPersona().getIdPersona()),
            vista.getCmb_periodos().getSelectedItem().toString()};
        List<SilaboMD> silabosdocente = SilaboBD.consultarSilabo1(conexion, parametros);

        return silabosdocente;

    }

    private SilaboMD silabo_seleccionado() {
        int seleccion = vista.getTbl().getSelectedRow();

        silabosDocente = cargar_silabo();
        Optional<SilaboMD> silaboSeleccionado = silabosDocente.stream().filter(s -> s.getMateria().getNombre().equals(vista.getTbl().getValueAt(seleccion, 2).toString())).
                findFirst();
        return silaboSeleccionado.get();
    }

    private UnidadSilaboMD unidad_seleccionada() {
        int seleccion = vista.getTbl().getSelectedRow();
        unidadesSilabo = UnidadSilaboBD.consultar(conexion, silabo_seleccionado().getID(), 1);
        Optional<UnidadSilaboMD> unidadSeleccionada = unidadesSilabo.stream().
                filter(s -> s.getNumeroUnidad() == Integer.parseInt(vista.getTbl().getValueAt(seleccion, 4).toString())).
                findFirst();
        return unidadSeleccionada.get();
    }
    private boolean accion = true;

    private void ejecutar(ActionEvent e) {
        if (accion) {
            new Thread(() -> {
                accion = false;

                principal.setEnabled(false);
                frmCargando1 frmCargando1 = new frmCargando1();

                frmCargando1.setVisible(true);

                imprimir_plan();

                accion = true;

                principal.setEnabled(true);

                frmCargando1.dispose();

            }).start();

        }

    }

    private void InitPermisos() {
        vista.getBtnNuevoPLC().getAccessibleContext().setAccessibleName("Plan-De-Clase-Nuevo");
        vista.getBtnEditarPLC().getAccessibleContext().setAccessibleName("Plan-De-Clase-Editar");
        vista.getBtnEliminarPLC().getAccessibleContext().setAccessibleName("Plan-De-Clase-Eliminar");
        vista.getBtnImplimirPlan().getAccessibleContext().setAccessibleName("Plan-De-Clase-Imprimir");

        CONS.activarBtns(vista.getBtnNuevoPLC(), vista.getBtnEditarPLC(),
                vista.getBtnEliminarPLC(), vista.getBtnImplimirPlan());

    }

    private void btnCopiar(ActionEvent e) {

        int row = vista.getTbl().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    vista,
                    "SELECCIONE UN PLAN DE CLASES PARA COPIAR",
                    "AVISO!!",
                    JOptionPane.OK_OPTION
            );
        } else {

            int idPlan = (Integer) vista.getTbl().getValueAt(row, 0);

            System.out.println("------------>" + idPlan);

            PlandeClasesMD plan = planes.stream()
                    .filter(item -> item.getID().equals(idPlan))
                    .findFirst()
                    .get();
            List<CursoMD> cursos = CON.cursosSinPlanes(plan.getID());

            if (cursos.isEmpty()) {
                JOptionPane.showMessageDialog(
                        vista,
                        "NO TIENE CURSOS PENDIENTES",
                        "AVISO!!",
                        JOptionPane.OK_OPTION
                );
            } else {
                VtnCopiarPlanCTR vtn = new VtnCopiarPlanCTR(desktop);
                vtn.setModelo(plan);
                vtn.setCursos(cursos);
                vtn.Init();
            }

        }
    }
}
