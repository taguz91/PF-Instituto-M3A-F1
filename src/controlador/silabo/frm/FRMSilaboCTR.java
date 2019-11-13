package controlador.silabo.frm;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import java.util.List;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.referencias.ReferenciasMD;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.silabo.NEWEstrategiaUnidadBD;
import modelo.silabo.NEWReferenciaSilaboBD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.NEWTipoActividadBD;
import modelo.silabo.NEWUnidadSilaboBD;
import modelo.silabo.SilaboMD;
import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboMD;
import vista.silabos.NEWFrmSilabo;

/**
 *
 * @author gus
 */
public class FRMSilaboCTR extends DCTR {

    private final NEWFrmSilabo FRM_GESTION = new NEWFrmSilabo();
    // Conexiones a base de datos
    private final NEWSilaboBD SBD = NEWSilaboBD.single();
    private final NEWUnidadSilaboBD USBD = NEWUnidadSilaboBD.single();
    private final NEWEstrategiaUnidadBD EUBD = NEWEstrategiaUnidadBD.single();
    private final NEWReferenciaSilaboBD RSBD = NEWReferenciaSilaboBD.single();
    private final NEWTipoActividadBD TABD = NEWTipoActividadBD.single();

    // Listas para guardar los contenidos del formulario 
    private List<UnidadSilaboMD> unidades;
    protected List<EstrategiasUnidadMD> estrategias;
    protected List<EvaluacionSilaboMD> evaluaciones;
    protected List<TipoActividadMD> tiposActividad;
    protected List<ReferenciasMD> biblioteca;
    protected List<ReferenciaSilaboMD> referenciasSilabo;

    // Para tener referencia de un silabo 
    private final SilaboMD silaboReferencia;
    // Para saber la cantidad de unidades que tendra el silabo  
    private final int numUnidades;

    public FRMSilaboCTR(
            VtnPrincipalCTR ctrPrin,
            SilaboMD silaboRefencia,
            int numUnidades
    ) {
        super(ctrPrin);
        this.silaboReferencia = silaboRefencia;
        this.numUnidades = numUnidades;
    }

    public void iniciar() {
        // Siempre iniciamos el estado del boton en falso para que 
        // no pueda guardar hasta realizar cambios
        estadoBtnGuardar(false);
        // Comprobamos si existe o no un silabo anterior para iniciar
        // el formulario
        existeSilabo();
        iniciarVentana();
    }

    private void iniciarVentana() {
        FRM_GESTION.setTitle(silaboReferencia.getMateria().getNombre());
        ctrPrin.agregarVtn(FRM_GESTION);
        iniciarCMBUnidad();
    }

    private void iniciarCMBUnidad() {
        unidades.forEach(u -> {
            FRM_GESTION.getCmbUnidad()
                    .addItem("Unidad " + u.getNumeroUnidad());
        });
        FRM_GESTION.getCmbUnidad().setSelectedIndex(0);
        FRM_GESTION.getCmbUnidad().addActionListener(e -> mostrarUnidad());
    }

    private void existeSilabo() {
        if (silaboReferencia == null) {
            crearSilaboNuevo();
        } else {
            crearSilaboReferenciado(silaboReferencia);
        }
        // Iniciamo los arrays para guardar las diferentes cosas
        evaluaciones = new ArrayList<>();
        biblioteca = new ArrayList<>();
        tiposActividad = TABD.getAll();
        // Actualizamos el total de gestion que tenemos
        mostrarTotalGestion();
    }

    private void crearSilaboNuevo() {
        unidades = new ArrayList<>();
        estrategias = new ArrayList<>();
        referenciasSilabo = new ArrayList<>();
        // Agregamos el numero de unidades que indico 
        // en el formulario de configuracion
        for (int i = 1; i <= numUnidades; i++) {
            UnidadSilaboMD us = new UnidadSilaboMD();
            us.setNumeroUnidad(i);
            unidades.add(us);
        }
    }

    private void crearSilaboReferenciado(SilaboMD sr) {
        unidades = USBD.getBySilaboSinFecha(sr.getID());
        estrategias = EUBD.getBySilabo(sr.getID());
        referenciasSilabo = RSBD.getBySilabo(sr.getID());
    }

    private void estadoBtnGuardar(boolean estado) {
        FRM_GESTION.getBtnGuardar().setEnabled(estado);
    }

    /**
     * Al seleccionar una unidad del combo realizamos todas las siguientes
     * acciones.
     */
    private void mostrarUnidad() {

    }

    /**
     * Mostramos el total de las evaluaciones de gestion
     */
    public void mostrarTotalGestion() {
        double total = 0;
        total = evaluaciones.stream()
                .map((emd) -> emd.getValoracion())
                .reduce(
                        total,
                        (accumulator, _item)
                        -> accumulator + _item
                );
        FRM_GESTION.getLblAcumuladoGestion().setText(total + "/60");
    }
}
