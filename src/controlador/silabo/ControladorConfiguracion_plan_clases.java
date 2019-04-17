package controlador.silabo;

import java.util.List;
import java.util.Optional;
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

public class ControladorConfiguracion_plan_clases {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private frmConfiguraciónPlanClase frm_cong_PlanClase;
    private final VtnPrincipal vtnPrincipal;
    private List<CarreraMD> carrerasDocente;
    private List<MateriaMD> materiasDocente;
    private List<SilaboMD> silabosDocente;
    private SilaboMD silabo;
    private List<UnidadSilaboMD> unidadesSilabo;

    public ControladorConfiguracion_plan_clases(UsuarioBD usuario, VtnPrincipal vtnPrincipal) {
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = new ConexionBD();
    }

    public void iniciarControlaador() {
        conexion.conectar();
        frm_cong_PlanClase = new frmConfiguraciónPlanClase();
        vtnPrincipal.getDpnlPrincipal().add(frm_cong_PlanClase);
        frm_cong_PlanClase.setTitle("Configuración Plan de Clases");
        frm_cong_PlanClase.show();

        frm_cong_PlanClase.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - frm_cong_PlanClase.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - frm_cong_PlanClase.getSize().height) / 2);

        frm_cong_PlanClase.getBtn_cancelar().addActionListener((e) -> {
            frm_cong_PlanClase.dispose();
        });
        frm_cong_PlanClase.getCmb_carreras().addActionListener(a1 -> cargarSilabosDocentes());
        frm_cong_PlanClase.getCmb_silabos().addActionListener(a1 -> iniciarSilabo(unidades()));
        cargarComboCarreras();
        cargarSilabosDocentes();
        iniciarSilabo(unidades());

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

        unidadesSilabo = UnidadSilaboBD.consultar(conexion, silabo.getIdSilabo());
        unidadesSilabo.forEach((umd) -> {
            frm_cong_PlanClase.getCmb_unidades().addItem("Unidad " + umd.getNumeroUnidad());
        });

    }

    public SilaboMD unidades() {

        Optional<SilaboMD> silabounidad = silabosDocente.stream().filter(s -> s.getIdMateria().getNombre().equals(
                frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString())).findFirst();
        return silabounidad.get();

    }

}
