package controlador.pagos;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import modelo.pagos.ComprobantePagoMD;
import vista.pagos.VtnComprobantes;

/**
 *
 * @author MrRainx
 */
public class VtnComprobantesCTR extends AbstractVTN<VtnComprobantes, ComprobantePagoMD> {

    public VtnComprobantesCTR(VtnPrincipalCTR desktop) {
        super(desktop);

    }
    /*
    @Override
    public void Init() {
        setModelo(new ComprobanteMD());
        setVista(new VtnComprobantes());
        setTable(getVista().getTbl());
        setLista(ComprobanteBD.selectAll());
        cargarTabla(cargador());
        super.Init();
    }

    private Consumer<ComprobanteMD> cargador() {
        return obj -> {
            getTableM().addRow(new Object[]{
                obj.getId(),
                // obj.getAlumno().getIdentificacion() + " " + obj.getAlumno().getPrimerNombre() + " " + obj.getAlumno().getPrimerApellido(),
                obj.getPeriodo().getNombre(),
                obj.getCodigo(),
                obj.getFechaPago()
            });
        };
    }
     */

}
