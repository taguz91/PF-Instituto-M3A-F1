package controlador.pagos;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.util.function.Consumer;
import modelo.pagos.ComprobanteMD;
import vista.pagos.VtnComprobantes;

/**
 *
 * @author MrRainx
 */
public class VtnComprobantesCTR extends AbstractVTN<VtnComprobantes, ComprobanteMD> {

    public VtnComprobantesCTR(VtnPrincipalCTR desktop) {
        super(desktop);

    }

    @Override
    public void Init() {
        setModelo(new ComprobanteMD());
        setVista(new VtnComprobantes());
        setTable(getVista().getTbl());
        cargarTabla(cargador());

        super.Init();

    }

    private Consumer<ComprobanteMD> cargador() {
        return obj -> {
            getTableM().addRow(new Object[]{
                obj.getId(),
                obj.getAlumno().getIdentificacion() + " " + obj.getAlumno().getPrimerNombre() + " " + obj.getAlumno().getPrimerApellido(),
                obj.getPeriodo().getNombre_PerLectivo(),
                obj.getCodigo(),
                obj.getFechaPago()
            });
        };
    }

}
