/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.avance;

import controlador.silabo.planesDeClase.ControladorConfiguracion_plan_clases;
import com.placeholder.PlaceHolder;
import java.util.List;
import modelo.silabo.CursoMDS;
import vista.silabos.frmAvanceSilabo;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Daniel
 */
public class ControladorAvanceSilabo {

    private frmAvanceSilabo avance;
    private final VtnPrincipal vtnPrincipal;
    private final UsuarioBD usuario;

    public ControladorAvanceSilabo(frmAvanceSilabo avance, VtnPrincipal vtnPrincipal, UsuarioBD usuario) {
        this.avance = avance;
        this.vtnPrincipal = vtnPrincipal;
        this.usuario = usuario;
    }

    public void iniciaControlador() {

        avance = new frmAvanceSilabo();
        vtnPrincipal.getDpnlPrincipal().add(avance);
        avance.show();
        avance.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - avance.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - avance.getSize().height) / 2);
        avance.getBtnCancelar().addActionListener(a1 -> {
            avance.dispose();
            ControladorConfiguracion_plan_clases cp = new ControladorConfiguracion_plan_clases(usuario, vtnPrincipal);
            cp.iniciarControlaador();

        });
        PlaceHolder holder = new PlaceHolder(avance.getTxrObservaciones(), "Escriba su Observacion aqui...");

    }

    public void cargarCamposCursoCarreraDocente(List<CursoMDS> lista) {
        for (CursoMDS cursoMDS : lista) {
            avance.getTxtCarrera().setText(cursoMDS.getId_carrera().getNombre());
            avance.getTxtCarrera().setEnabled(false);
            avance.getTxtDocente().setText(cursoMDS.getId_persona().getPrimerNombre() + " " + cursoMDS.getId_persona().getPrimerApellido());
            avance.getTxtDocente().setEnabled(false);
            avance.getTxtAsignatura().setText(cursoMDS.getId_materia().getNombre());
            avance.getTxtAsignatura().setEnabled(false);
            avance.getTxtAsignatura().setText(cursoMDS.getId_materia().getCodigo());
            avance.getTxtAsignatura().setEnabled(false);
            avance.getTxtParalelo().setText(cursoMDS.getCurso_nombre());
            avance.getTxtParalelo().setEnabled(false);

        }
    }
}
