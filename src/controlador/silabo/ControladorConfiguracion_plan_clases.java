package controlador.silabo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.materia.MateriaMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.MateriasBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.silabo.dbCarreras;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCRUDHorarios;
import vista.silabos.frmConfiguraciónPlanClase;
import vista.silabos.frmPlanClase;
import vista.silabos.frmCRUDPlanClase;

public class ControladorConfiguracion_plan_clases {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private frmConfiguraciónPlanClase frm_cong_PlanClase;
    private frmPlanClase frm_Plan_Clase;
    private final VtnPrincipal vtnPrincipal;
    private List<CarreraMD> carrerasDocente;
    private List<MateriaMD> materiasDocente;
    private List<SilaboMD> silabosDocente;
    private SilaboMD silabo;
    private List<UnidadSilaboMD> unidadesSilabo;
    private frmCRUDPlanClase plan;

    public ControladorConfiguracion_plan_clases(UsuarioBD usuario, VtnPrincipal vtnPrincipal) {
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = new ConexionBD();
    }

    public void iniciarControlaador() {
        conexion.conectar();

        // ControladorCRUDPlanClase ccpc= new ControladorCRUDPlanClase(plan, usuario);
        frm_cong_PlanClase = new frmConfiguraciónPlanClase();
        vtnPrincipal.getDpnlPrincipal().add(frm_cong_PlanClase);
        frm_cong_PlanClase.setTitle("Configuración Plan de Clases");
        frm_cong_PlanClase.show();

        frm_cong_PlanClase.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - frm_cong_PlanClase.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - frm_cong_PlanClase.getSize().height) / 2);

        frm_cong_PlanClase.getBtn_cancelar().addActionListener((e) -> {
            frm_cong_PlanClase.dispose();
        });
        frm_cong_PlanClase.getBtn_siguiente().addActionListener(a1 -> {
            frm_cong_PlanClase.dispose();
            Controlador_plan_clases cpc = new Controlador_plan_clases(usuario, vtnPrincipal);
            cpc.iniciaControlador();
        });
        frm_cong_PlanClase.getCmb_carreras().addActionListener(a1 -> cargarSilabosDocentes());
        frm_cong_PlanClase.getCmb_silabos().addActionListener(a1 -> iniciarSilabo(unidades()));
        cargarComboCarreras();
        cargarSilabosDocentes();
        iniciarSilabo(unidades());
//     materiaseleccionada();

    }

    public List<CarreraMD> cargarComboCarreras() {
        List<CarreraMD> carrerasDocentes = CarrerasBDS.consultar(conexion, usuario.getUsername());
        carrerasDocentes.forEach((cmd) -> {
            frm_cong_PlanClase.getCmb_carreras().addItem(cmd.getNombre());
        });

        return carrerasDocentes;
    }

    public void cargarSilabosDocentes() {
        String[] parametros = {frm_cong_PlanClase.getCmb_carreras().getSelectedItem().toString(), String.valueOf(usuario.getPersona().getIdPersona())};
        silabosDocente = SilaboBD.consultarSilabo1(conexion, parametros);
        frm_cong_PlanClase.getCmb_silabos().removeAllItems();
        for (SilaboMD smd : silabosDocente) {
            String estado = null;
            if (smd.getEstadoSilabo() == 0) {
                estado = "Por aprobar";
            }
            frm_cong_PlanClase.getCmb_silabos().addItem(smd.getIdMateria().getNombre());
        }
    }

    public void iniciarSilabo(SilaboMD silabo) {
        System.out.println("--------------------->>>>>>>>>>>>>>");
        if (silabo != null) {
            unidadesSilabo = UnidadSilaboBD.consultar(conexion, silabo.getIdSilabo());
            unidadesSilabo.forEach((umd) -> {
                frm_cong_PlanClase.getCmb_unidades().addItem("Unidad " + umd.getNumeroUnidad());
            });
        } else {
            JOptionPane.showMessageDialog(null, "NO EXISTEN SILABOS");
        }
    }

    public SilaboMD unidades() {
        System.out.println("...............................................");

        Optional<SilaboMD> silabounidad = null;
        if (silabounidad == null) {

            // Optional<SilaboMD> silabounidad = null;
            if (silabounidad == null) {

                return null;
            } else {
                silabounidad = silabosDocente.stream().filter(s -> s.getIdMateria().getNombre().equals(
                        frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString())).findFirst();

                //   return silabounidad.get();
                //sale un error...revisaras
//
//        if (silabounidad.equals("")) {
//            return silabounidad.get();
//        }
//        return null;
//
//        if (silabounidad.equals(" ")) {
//            return silabounidad.get();
//        }
//
//        return silabounidad.get();
//        }
            }
        }
        return null;

    }

    void materiaseleccionada() {
        silabo = (SilaboMD) frm_cong_PlanClase.getCmb_silabos().getSelectedItem();
        unidadesSilabo = UnidadSilaboBD.consultar(conexion, silabo.getIdSilabo());
        unidadesSilabo.forEach((umd) -> {
            frm_cong_PlanClase.getCmb_unidades().addItem("Unidad " + umd.getNumeroUnidad());
        });

    }

}
