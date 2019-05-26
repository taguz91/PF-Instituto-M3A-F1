package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Effects;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.periodolectivo.PeriodoLectivoBD;
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
    @Override
    public void Init() {
        System.out.println("--------THREAD-->" + Thread.activeCount());
        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());

        tabla = (DefaultTableModel) vista.getTblTipoNota().getModel();

        ID_PERIODO = modelo.getPeriodoLectivo().getId_PerioLectivo();

        vista.getCmbPeriodoLectivo().addItem(modelo.getPeriodoLectivo().getNombre_PerLectivo());

        listaPeriodos = new PeriodoLectivoBD().selectWhere(modelo.getPeriodoLectivo().getNombre_PerLectivo());

        setlblCarrera();

        listaTipos = modelo.selectWhere(ID_PERIODO);
        vista.getCmbPeriodoLectivo().setEditable(false);
        vista.setTitle("Editar Tipo De Nota");
        InitEventos();
        InitTablas();
        InitListas();
        cargarUpdate();
    }

    public void cargarUpdate() {
        tabla.setRowCount(0);
        listaTipos.forEach(obj -> {
            tabla.addRow(new Object[]{
                obj.getNombre(),
                obj.getValorMinimo(),
                obj.getValorMaximo(),
                obj.getId()
            });
        });
    }

    @Override
    protected void setObjs() {
        IntStream.range(0, tabla.getDataVector().size())
                .forEach(i -> {
                    int id = Integer.valueOf(tabla.getValueAt(i, 3).toString());
                    TipoDeNotaBD tipo = listaTipos.stream()
                            .filter(item -> item.getId() == id)
                            .findFirst()
                            .get();
                    tipo.setValorMinimo(Double.valueOf(tabla.getValueAt(i, 1).toString()));
                    tipo.setValorMaximo(Double.valueOf(tabla.getValueAt(i, 2).toString()));
                });

    }

    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {

        Effects.setText(vtnPadre.getVista().getLblEstado(), "SE ESTA EDITANDO LAS NOTAS", 2);

        setObjs();

        try {
            CONS.getPool(10).submit(() -> listaTipos
                    .parallelStream()
                    .forEach(obj -> {
                        obj.editar(obj.getId());
                    })
            ).get();
            CONS.THREAD_POOL.shutdown();
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }
        listaTipos = null;
        System.out.println("--------THREAD-->" + Thread.activeCount());
        Effects.setTextInLabel(vtnPadre.getVista().getLblEstado(), "SE HA EDITADO LOS TIPOS DE NOTA PARA " + vista.getCmbPeriodoLectivo().getSelectedItem().toString(), Effects.SUCCESS_COLOR, 4);

        vista.dispose();
    }

}
