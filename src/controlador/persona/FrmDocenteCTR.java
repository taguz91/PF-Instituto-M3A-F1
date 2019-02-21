package controlador.persona;

import java.awt.Color;
import java.util.ArrayList;
import modelo.persona.DocenteBD;
import vista.persona.FrmDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmDocenteCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmDocente frmDocente;
    private DocenteBD docente;
    private ArrayList<String> info = new ArrayList();

    public FrmDocenteCTR(VtnPrincipal vtnPrin, FrmDocente frmDocente, DocenteBD docente) {
        this.vtnPrin = vtnPrin;
        this.frmDocente = frmDocente;
        this.docente = docente;

        vtnPrin.getDpnlPrincipal().add(frmDocente);
        frmDocente.show();
    }

    public void iniciar() {

        frmDocente.getBtnBuscarPersona().addActionListener(e -> buscarPersona(frmDocente.getTxtIdentificacion().getText()));
    }

    public void insertarDocente() {

    }

    public void buscarPersona(String identificacion) {
        try {
            info = docente.buscarPersona(identificacion);
            if (info != null) {
                //Llenamos los datos en el lbl 
                frmDocente.getLblDatosPersona().setForeground(Color.black);
                frmDocente.getLblDatosPersona().setText(info.get(0) + " " + info.get(1));
                frmDocente.getBtnRegistrarPersona().setVisible(false);
                System.out.println("Numero de datos: " + info.size());
            }
            try {
                info = docente.buscarPersonaDocente(identificacion);
                if (info != null) {
                    //Llenamos los datos en el lbl 
                    frmDocente.getLblDatosPersona().setText(info.get(0) + " " + info.get(1));
                    System.out.println("Numero de datos: " + info.size());
                    
                }
            } catch (Exception w) {
                frmDocente.getLblDatosPersona().setForeground(Color.red);
                frmDocente.getLblDatosPersona().setText("No se encuentra dentro de la lista Docentes");
                frmDocente.getBtnRegistrarPersona().setVisible(false);
            }
        } catch (Exception e) {

            frmDocente.getLblDatosPersona().setForeground(Color.red);
            frmDocente.getLblDatosPersona().setText("No existe");
            frmDocente.getBtnRegistrarPersona().setVisible(true);

        }

    }
}
