/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConexionBD;
import modelo.PlanClases.JornadasDB;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.jornada.JornadaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursoMDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.PeriodoLectivoBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.UsuarioBD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCRUDPlanClase;
import vista.silabos.frmCargando1;

/**
 *
 * @author Daniel
 */
public class ControladorCRUDPlanClase {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private final VtnPrincipal principal;
    private frmCRUDPlanClase fCrud_plan_Clases;
    private List<SilaboMD> silabosDocente;
    private List<CarreraMD> carreras_docente;
    private List<JornadaMD> lista_jornadas;
    private List<PlandeClasesMD> lista_plan_clases;
    private List<PeriodoLectivoMD> periodosCarrera;
    private List<UnidadSilaboMD> unidadesSilabo;
    private List<CursoMD> lista_curso;
    private int id_periodo_lectivo = -1;

    public ControladorCRUDPlanClase(UsuarioBD usuario, ConexionBD conexion, VtnPrincipal principal) {
        this.usuario = usuario;
        this.conexion = conexion;
        this.principal = principal;
    }

    public void iniciaControlador() {
        conexion.conectar();

        fCrud_plan_Clases = new frmCRUDPlanClase();
        principal.getDpnlPrincipal().add(fCrud_plan_Clases);
        fCrud_plan_Clases.setTitle("PLANES DE CLASE");
        fCrud_plan_Clases.show();

        fCrud_plan_Clases.setLocation((principal.getDpnlPrincipal().getSize().width - fCrud_plan_Clases.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - fCrud_plan_Clases.getSize().height) / 2);

        fCrud_plan_Clases.getBtnNuevoPLC().addActionListener(a -> {
            fCrud_plan_Clases.dispose();
            ControladorConfiguracion_plan_clases cp = new ControladorConfiguracion_plan_clases(usuario, principal, conexion);
            cp.iniciarControlaador();
        });
        fCrud_plan_Clases.getCmb_Carreras().addActionListener(a -> cargarPlanesDeClaseProfesor());
        fCrud_plan_Clases.getCmb_Carreras().addActionListener(a -> CARGAR_COMBO_PERIODOS_CARRERA());
        fCrud_plan_Clases.getCmbJornadas().addActionListener(a -> cargarPlanesDeClaseProfesor());
        fCrud_plan_Clases.getCmb_periodos().addActionListener(a -> cargarPlanesDeClaseProfesor());
        fCrud_plan_Clases.getTxtBuscarPLC().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
               if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                cargarPlanesDeClaseProfesor();
                }
            }
        });

        fCrud_plan_Clases.getBtnEliminarPLC().addActionListener((ActionEvent ae) -> {
            int row = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
            if (row != -1) {
                eliminarPlanClase();
                cargarPlanesDeClaseProfesor();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un plan de clase", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
        fCrud_plan_Clases.getBtnEditarPLC().addActionListener((ActionEvent ae) -> {
            int row = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
            if (row != -1) {
                ControladorEditarPlanClases ce = new ControladorEditarPlanClases(usuario,plan_clas_selecc(), principal, conexion, curso_selecc(), silabo_seleccionado(), unidad_seleccionada());
                ce.iniciaControlador();
                fCrud_plan_Clases.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un plan de clase", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    
        
            
        CARGAR_COMBO_CARRERAS();
        CARGAR_JORNADAS();
        cargarPlanesDeClaseProfesor();
        CARGAR_COMBO_PERIODOS_CARRERA();
        fCrud_plan_Clases.getBtnImplimirPlan().addActionListener(e -> ejecutar(e));
    }

    private void cargarPlanesDeClaseProfesor() {
        try {
            DefaultTableModel modelotabla;
            modelotabla = (DefaultTableModel) fCrud_plan_Clases.getTlbTablaPLC().getModel();
            String[] parametros = {fCrud_plan_Clases.getCmb_Carreras().getSelectedItem().toString(), fCrud_plan_Clases.getCmbJornadas().getSelectedItem().toString(), fCrud_plan_Clases.getTxtBuscarPLC().getText(), String.valueOf(usuario.getPersona().getIdPersona()),
                String.valueOf(getid_periodo())};
            lista_plan_clases = PlandeClasesBD.consultarPlanClase(conexion, parametros);
            for (int j = fCrud_plan_Clases.getTlbTablaPLC().getModel().getRowCount() - 1; j >= 0; j--) {
                modelotabla.removeRow(j);
            }

            for (PlandeClasesMD plc : lista_plan_clases) {
                modelotabla.addRow(new Object[]{
                    plc.getId_plan_clases(), plc.getId_persona().getPrimerApellido() + " " + plc.getId_persona().getPrimerNombre(), plc.getId_materia().getNombre(), plc.getId_curso().getNombre(), plc.getId_unidad().getIdUnidad()
                });
            }

            fCrud_plan_Clases.getTlbTablaPLC().setModel(modelotabla);
        } catch (Exception e) {
        }

    }

    private void CARGAR_COMBO_PERIODOS_CARRERA() {
        fCrud_plan_Clases.getCmb_periodos().removeAllItems();
        List<PeriodoLectivoMD> periodos = PeriodoLectivoBDS.consultarPeriodosPlanDeClse(conexion, fCrud_plan_Clases.getCmb_Carreras().getSelectedItem().toString());
        if (periodos == null) {
            JOptionPane.showMessageDialog(null, "No existen Periodos");
        } else {

            periodos.forEach((prd) -> {
                fCrud_plan_Clases.getCmb_periodos().addItem(prd.getNombre_PerLectivo());
            });
        }

    }

    private void CARGAR_COMBO_CARRERAS() {
        fCrud_plan_Clases.getCmb_Carreras().removeAllItems();
        carreras_docente = CarrerasBDS.consultar(conexion, usuario.getUsername());

        if (carreras_docente == null) {
            JOptionPane.showMessageDialog(null, "No tiene carreras asignadas");
        } else {
            carreras_docente.forEach((cmd) -> {
                fCrud_plan_Clases.getCmb_Carreras().addItem(cmd.getNombre());
            });
        }
        fCrud_plan_Clases.getCmb_Carreras().setSelectedIndex(0);
    }

    private void CARGAR_JORNADAS() {
        fCrud_plan_Clases.getCmbJornadas().removeAllItems();
        lista_jornadas = JornadasDB.consultarJornadas(conexion);
        lista_jornadas.forEach((lj) -> {
            fCrud_plan_Clases.getCmbJornadas().addItem(lj.getNombre());
        });

    }

    private PlandeClasesMD plan_clas_selecc() {
        int seleccion = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
        Optional<PlandeClasesMD> plan_clase_selec = lista_plan_clases.stream().
                filter(pl -> pl.getId_plan_clases() == Integer.parseInt(fCrud_plan_Clases.getTlbTablaPLC().getValueAt(seleccion, 0).toString())).findFirst();

        return plan_clase_selec.get();
    }

    private CursoMD curso_selecc() {
        int seleccion = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
        lista_curso = CursosBDS.Consultarcursos(conexion, usuario.getPersona().getIdPersona(), getid_periodo(), fCrud_plan_Clases.getTlbTablaPLC().getValueAt(seleccion, 2).toString());
        Optional<CursoMD> curso_selecccionado = lista_curso.stream().filter(lc -> lc.getNombre().equals(fCrud_plan_Clases.getTlbTablaPLC().getValueAt(seleccion, 3).toString())).findFirst();
        return curso_selecccionado.get();
    }

   
   
   
   private void eliminarPlanClase(){
       int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este plan de clase?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            new PlandeClasesBD(conexion).eliminarPlanClase(plan_clas_selecc());
            JOptionPane.showMessageDialog(null, "Plan de clase eliminado correctamente");
        }
    }

    private List<PeriodoLectivoMD> cargarPeriodos() {
        List<PeriodoLectivoMD> periodos = PeriodoLectivoBDS.consultarPeriodosPlanDeClse(conexion, fCrud_plan_Clases.getCmb_Carreras().getSelectedItem().toString());
        return periodos;
    }

    public void imprimir_plan() {
        int seleccion = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
        if (seleccion >= 0) {
            try {
                System.out.println(plan_clas_selecc().getId_plan_clases() + " soy un plan CON CURSO :"
                        + curso_selecc().getId() + " UNIDAD: " + unidad_seleccionada().getIdUnidad());
                System.out.println("Imprimiendo.......");
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/plan_de_clase/planClasePagPrincipal.jasper"));
                Map parametro = new HashMap();
             
                
                parametro.put("id_unidad", String.valueOf(unidad_seleccionada().getIdUnidad()));
                parametro.put("id_curso", String.valueOf(curso_selecc().getId()));
                parametro.put("id_plan_clase", String.valueOf(plan_clas_selecc().getId_plan_clases()));

                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
                JasperViewer pv = new JasperViewer(jp, false);
                pv.setVisible(true);
                pv.setTitle("PLAN DE CLASES");
                //principal.add(pv);
            } catch (JRException ex) {
                Logger.getLogger(ControladorCRUDPlanClase.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "DEBE SELECIONAR EL DOCUMENTO PARA IMPRIMIR");
        }

    }

    private int getid_periodo() {
        String nombre_periodo = fCrud_plan_Clases.getCmb_periodos().getSelectedItem().toString();
        periodosCarrera = cargarPeriodos();
        periodosCarrera
                .stream()
                .filter(item -> item.getNombre_PerLectivo().equals(nombre_periodo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    id_periodo_lectivo = obj.getId_PerioLectivo();
                });

     return id_periodo_lectivo;
  }
   
   public List<SilaboMD> cargar_silabo(){
         String[] parametros = {fCrud_plan_Clases.getCmb_Carreras().getSelectedItem().toString(), String.valueOf(usuario.getPersona().getIdPersona())};
         List<SilaboMD> silabosdocente= SilaboBD.consultarSilabo1(conexion, parametros);
         
//         System.out.println(silabosdocente.get(0).getIdSilabo()+" CARGAR_SILABOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO_IDDDDDD");
         return silabosdocente;
         
    }


    private SilaboMD silabo_seleccionado() {
        int seleccion = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();

        silabosDocente = cargar_silabo();
        Optional<SilaboMD> silaboSeleccionado = silabosDocente.stream().filter(s -> s.getIdMateria().getNombre().equals(fCrud_plan_Clases.getTlbTablaPLC().getValueAt(seleccion, 2).toString())).
                findFirst();
        return silaboSeleccionado.get();
    }

    private UnidadSilaboMD unidad_seleccionada() {
        int seleccion = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
        unidadesSilabo = UnidadSilaboBD.consultar(conexion, silabo_seleccionado().getIdSilabo(),1);
        Optional<UnidadSilaboMD> unidadSeleccionada = unidadesSilabo.stream().
                filter(s -> s.getNumeroUnidad() == Integer.parseInt(fCrud_plan_Clases.getTlbTablaPLC().getValueAt(seleccion, 4).toString())).
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
}
