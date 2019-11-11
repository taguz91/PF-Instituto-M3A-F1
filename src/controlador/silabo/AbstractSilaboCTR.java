package controlador.silabo;

import java.util.List;
import javax.swing.DefaultListModel;
import modelo.ConexionBD;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.referencias.ReferenciasMD;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.silabo.SilaboMD;
import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmGestionSilabo;
import vista.silabos.frmReferencias;

/**
 *
 * @author MrRainx, Johnny
 */
public abstract class AbstractSilaboCTR {

    protected SilaboMD modelo;
    protected frmGestionSilabo vista;
    protected VtnPrincipal vtnPrincipal;
    protected ConexionBD conexion;

    //ARRAYS DE CONSULTAS A LA BASE DE DATOS
    protected frmReferencias bibliografia;

    protected List<UnidadSilaboMD> unidades;

    protected List<EstrategiasUnidadMD> estrategias;

    protected List<EvaluacionSilaboMD> evaluaciones;

    protected List<TipoActividadMD> tiposActividad;

    protected List<ReferenciasMD> biblioteca;

    protected List<ReferenciaSilaboMD> referenciasSilabo;

    protected DefaultListModel tablaM;

    public AbstractSilaboCTR(SilaboMD modelo, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        if (modelo != null) {
            this.modelo = modelo;
        } else {
            this.modelo = modelo;
        }
        this.vista = new frmGestionSilabo();
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = conexion;
    }

    public void iniciarControlador() {
        bibliografia = new frmReferencias();

        vtnPrincipal.getDpnlPrincipal().add(vista);

        vista.setTitle(modelo.getMateria().getNombre());

        vista.show();

        vista.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - vista.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - vista.getSize().height) / 2);

        //iniciarSilabo(modelo);
    }

}
