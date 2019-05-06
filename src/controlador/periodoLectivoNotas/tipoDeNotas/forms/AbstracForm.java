package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.tipoDeNota.TipoDeNotaBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public abstract class AbstracForm {

    protected VtnPrincipal desktop;
    protected FrmTipoNota vista;
    protected TipoDeNotaBD modelo;
    //Ventana Padre
    protected VtnTipoNotasCTR vtnPadre;
    //listas
    protected Map<String, PeriodoLectivoMD> listaPeriodos;

    //Combo
    protected String[] carrerasTradicionales = {
        "APORTE 1",
        "NOTA INTERCICLO",
        "EXAMEN INTERCICLO",
        "APORTE 2",
        "EXAMEN FINAL",
        "EXAMEN SUPLETORIO",
        "NOTA FINAL"
    };

    protected String[] carrerasDuales = {
        "G. DE AULA 1",
        "G. DE AULA 2",
        "TOTAL GESTION",
        "EXAMEN FINAL",
        "EXAMEN DE RECUPERACION",
        "NOTA FINAL CICLO",
        "D. G. I. F.",
        "C. T. E.",
        "C. T. A.",
        "S. T. F. P.",
        "NOTA FINAL",
    };

    protected boolean COMPLETED = false;

    public AbstracForm(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPadre = vtnPadre;
    }

    //INITS
    public void Init() {
        new Thread(() -> {
            try {
                Effects.centerFrame(vista, desktop.getDpnlPrincipal());
                desktop.getDpnlPrincipal().add(vista);
                vista.setSelected(true);
                vista.show();
            } catch (PropertyVetoException e) {
                System.out.println(e.getMessage());
            }
        }).start();
        activarFormulario(false);

        listaPeriodos = PeriodoLectivoBD.selectWhereEstadoAndActivo(true, true);

        cargarComboCarreras();
//        cargarCmbNombreNota(carrerasTradicionales);
        InitEventos();
        COMPLETED = true;
    }

    private void InitEventos() {
        vista.getBtnCancelar().addActionListener(e -> btnCancelar(e));

        String errorMessage = "ERROR INGRESE UN NUMERO EN ESTE FORMATO (15 o 15.66)";

        Validaciones.validarDecimalJtextField(vista.getTxtNotaMax(), errorMessage, vista, 0, 2);

        Validaciones.validarDecimalJtextField(vista.getTxtNotaMin(), errorMessage, vista, 0, 2);

        vista.getTxtNotaMin().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarValorMenor(e);
            }
        });
        vista.getTxtNotaMax().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarValorMenor(e);
            }
        });

        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));

        vista.getCmbPeriodoLectivo().addActionListener(e -> cargarTiposNotas(e));
    }

    //METODOS DE APOYO
    protected void cargarCmbNombreNota(String[] lista) {
        vista.getCmbTipoDeNota().removeAllItems();
        for (String obj : lista) {
            vista.getCmbTipoDeNota().addItem(obj);
        }
    }

    protected void activarFormulario(boolean estado) {
        vista.getCmbTipoDeNota().setEnabled(estado);
        vista.getTxtNotaMax().setEnabled(estado);
        vista.getTxtNotaMin().setEnabled(estado);
        vista.getCmbPeriodoLectivo().setEnabled(estado);
    }

    protected void cargarComboCarreras() {

        listaPeriodos.entrySet().stream().forEach(entry -> {
            vista.getCmbPeriodoLectivo().addItem(entry.getKey());
        });
    }

    protected void validarValorMenor(FocusEvent e) {
        if (!vista.getTxtNotaMax().getText().isEmpty() && !vista.getTxtNotaMin().getText().isEmpty()) {

            double minimo = Double.valueOf(vista.getTxtNotaMin().getText());
            double maximo = Double.valueOf(vista.getTxtNotaMax().getText());
            if (minimo > maximo) {
                JOptionPane.showMessageDialog(vista, "EL VALOR MINIMO NO PUEDE SER MAYOR AL VALOR MAXIMO!!");
                vista.getTxtNotaMin().setText("");
                vista.getTxtNotaMin().requestFocus();
            } else if (minimo == maximo) {
                JOptionPane.showMessageDialog(vista, "LOS VALORES NO PUEDEN SER IGUALES!!");
                vista.getTxtNotaMin().setText("");
                vista.getTxtNotaMin().requestFocus();
            }

        }
    }

    protected boolean validarFormulario() {
        if (!vista.getTxtNotaMax().getText().isEmpty()) {
            if (!vista.getTxtNotaMin().getText().isEmpty()) {
                return true;
            } else {
                JOptionPane.showMessageDialog(vista, "RELLENE EL CAMPO DE NOTA MINIMA!!");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "RELLENE EL CAMPO DE NOTA MAXIMA!!");
        }

        return false;
    }

    protected TipoDeNotaBD setObj() {
        modelo = new TipoDeNotaBD();

        modelo.setNombre(vista.getCmbTipoDeNota().getSelectedItem().toString());
        modelo.setValorMaximo(Double.valueOf(vista.getTxtNotaMax().getText()));
        modelo.setValorMinimo(Double.valueOf(vista.getTxtNotaMin().getText()));

        String key = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        Map<String, PeriodoLectivoMD> map = listaPeriodos
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(key))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
        map.entrySet()
                .stream()
                .forEach(entry -> {
                    modelo.setPeriodoLectivo(entry.getValue());
                });

        return modelo;
    }

    //PROCESADORES DE EVENTOS
    private void btnCancelar(ActionEvent e) {
        vista.dispose();
    }

    protected abstract void btnGuardar(ActionEvent e);

    private void cargarTiposNotas(ActionEvent e) {
        String busqueda = vista.getCmbPeriodoLectivo().getSelectedItem().toString();

        PeriodoLectivoMD periodo = listaPeriodos
                .entrySet()
                .stream()
                .filter(item -> item.getKey().equals(busqueda))
                .findAny()
                .get()
                .getValue();

        String modalidad = periodo.getCarrera().getModalidad();

        if (modalidad.equalsIgnoreCase("PRESENCIAL")) {
            cargarCmbNombreNota(carrerasTradicionales);
        } else {
            cargarCmbNombreNota(carrerasDuales);
        }

    }
}
