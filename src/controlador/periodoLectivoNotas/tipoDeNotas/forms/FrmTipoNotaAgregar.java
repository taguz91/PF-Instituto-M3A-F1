package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Effects;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
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
    @Override
    public void Init() {
        InitListas();
        InitTablas();

        tabla = (DefaultTableModel) vista.getTblTipoNota().getModel();

        listaPeriodos = periodoBD.selectPeriodosFaltantes();

        if (listaPeriodos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO HAY PERIODOS PENDIENTES HA INGRESAR NOTAS!!");
        } else {

            Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());
            vista.setTitle("Agregar Tipos De Nota");
            cargarComboCarreras();
            listaNombres = modelo.selectNombreWhere(getIdPeriodo());
            setlblCarrera();
            cargarTabla();
            InitEventos();

        }
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
            Effects.setTextInLabel(vtnPadre.getVista().getLblEstado(), "SE HA AGREGADO  LOS TIPOS DE NOTA PARA " + vista.getCmbPeriodoLectivo().getSelectedItem().toString(), CONS.SUCCESS_COLOR, 3);
        }).start();
        vista.dispose();
    }

}
