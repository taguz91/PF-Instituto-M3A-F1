package controlador.usuario.forms;

import controlador.Libraries.Effects;
import controlador.usuario.VtnUsuarioCTR;
import javax.swing.JOptionPane;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmUsuarioUpdt extends AbstracForm {

    private String Pk;

    public FrmUsuarioUpdt(VtnPrincipal desktop, VtnUsuarioCTR vtnPadre) {
        super(desktop, vtnPadre);
    }

    public void setModelo(UsuarioBD modelo) {
        this.modelo = modelo;
        this.Pk = modelo.getUsername();
        setForm();
    }

    public synchronized void setForm() {
        if (Pk.equals("ROOT")) {
            vista.getTxtUsername().setEnabled(false);
        }
        vista.setTitle("EDITAR USUARIOS");
        vista.getTxtUsername().setText(modelo.getUsername());
        vista.getCmbPersona().setSelectedItem(listaPersonas
                .entrySet()
                .stream()
                .filter(item -> item.getValue().getIdentificacion().equals(modelo.getPersona().getIdentificacion()))
                .findAny()
                .get()
                .getKey());
    }

    @Override
    public void guardar() {
        if (validarFormulario()) {
            if (getObj().editar(Pk)) {
                vtnPadre.cargarTabla(UsuarioBD.SelectAll());
                String message = "SE HA EDITADO AL USUARIO" + modelo.getUsername();
                Effects.setTextInLabel(vtnPadre.getVista().getLblEstado(), message, Effects.SUCCESS_COLOR, 3);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "HA OCURRIDO UN ERROR");
            }
        }
    }

}
