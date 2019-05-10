package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Effects;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import modelo.tipoDeNota.TipoDeNotaBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaEditar extends AbstracForm {

    protected Integer PK = null;

    public FrmTipoNotaEditar(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        super(desktop, vista, modelo, vtnPadre);
    }

    //INITS
    public void InitEditar() {

        Init();

        PK = modelo.getIdTipoNota();

        vista.setTitle("Editar Tipo De Nota");

        setObjInForm();


    }

    private void setObjInForm() {

        String key = modelo.getPeriodoLectivo().getNombre_PerLectivo() + " " + modelo.getPeriodoLectivo().getCarrera().getNombre();
    }

    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {
    }

}
