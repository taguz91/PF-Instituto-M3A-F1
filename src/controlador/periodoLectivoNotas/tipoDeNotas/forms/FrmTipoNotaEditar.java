package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import modelo.tipoDeNota.TipoDeNotaBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaEditar extends AbstracForm {

    protected int ID_PERIODO;

    public FrmTipoNotaEditar(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        super(desktop, vista, modelo, vtnPadre);
    }

    //INITS
    public void InitEditar() {

        Init();

        ID_PERIODO = modelo.getPeriodoLectivo().getId_PerioLectivo();

        vista.getCmbPeriodoLectivo().setSelectedItem(modelo.getPeriodoLectivo().getNombre_PerLectivo());

        System.out.println("---->" + modelo.getPeriodoLectivo().getNombre_PerLectivo());

        listaTipos = TipoDeNotaBD.selectWhere(ID_PERIODO);

        vista.setTitle("Editar Tipo De Nota");

        cargarUpdate();
    }

    public void cargarUpdate() {
        tabla.setRowCount(0);
        listaTipos.forEach(obj -> {
            tabla.addRow(new Object[]{
                obj.getNombre(),
                obj.getValorMinimo(),
                obj.getValorMaximo(),
                obj.getIdTipoNota()
            });
        });
    }

    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {

    }

}
