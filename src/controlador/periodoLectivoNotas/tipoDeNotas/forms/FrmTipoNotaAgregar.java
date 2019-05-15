package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Effects;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import modelo.tipoDeNota.TipoDeNotaBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaAgregar extends AbstracForm {

    public FrmTipoNotaAgregar(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        super(desktop, vista, modelo, vtnPadre);
    }

    //INITS
    public void InitAgregar() {

        Init();

        vista.setTitle("Agregar Nueva Nota");

    }

    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {
        new Thread(() -> {
            setObjs();
            listaTipos.stream()
                    .forEach(obj -> {
                        obj.insertar();
                    });
            Effects.setTextInLabel(vtnPadre.getVista().getLblEstado(), "SE HA AGREGADO  LOS TIPOS DE NOTA PARA " + vista.getCmbPeriodoLectivo().getSelectedItem().toString(), Effects.SUCCESS_COLOR, 3);
        }).start();
        vista.dispose();
    }

}
