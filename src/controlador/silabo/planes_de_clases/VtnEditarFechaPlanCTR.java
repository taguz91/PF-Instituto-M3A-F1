/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planes_de_clases;

import com.toedter.calendar.JDateChooser;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import vista.principal.VtnPrincipal;
import vista.silabos.new_planes_de_clase.frmEditarFechaG;

public class VtnEditarFechaPlanCTR {

    private final VtnPrincipal principal;
    private frmEditarFechaG vista;
    private PlandeClasesMD planMD;

    public VtnEditarFechaPlanCTR(VtnPrincipalCTR principal, PlandeClasesMD planMD) {

        this.principal = principal.getVtnPrin();
        this.planMD = planMD;
    }

    public void Init() {
        vista = new frmEditarFechaG();
        vista.setLocation((principal.getDpnlPrincipal().getSize().width - vista.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - vista.getSize().height) / 2);
        principal.getDpnlPrincipal().add(vista);
        vista.show();
        vista.setTitle("Editar Fecha Generacion Plan de clase");
        CargarFecha(planMD);

        vista.getBtn_Cancelar().addActionListener((ActionEvent e) -> {
            vista.dispose();
            principal.getMnCtPlandeClase().doClick();
        });

        vista.getBtn_actualizar_fg().addActionListener((ActionEvent e) -> {
            if (validarNullo(vista.getDch_fecha_g()) == true) {

                vista.dispose();
                actualizarFecha();
                //principal.getMnCtPlandeClase().doClick();
            } else {
                JOptionPane.showMessageDialog(null, "Campo Vacío", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void CargarFecha(PlandeClasesMD pl) {
        vista.getDch_fecha_g().setDate(Date.from(pl.getFechaGeneracion().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private void actualizarFecha() {
        try {
            new PlandeClasesBD().editarFechageneracoion(planMD.getID(), vista.getDch_fecha_g().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            JOptionPane.showMessageDialog(null, "Se actualizó exitosamente", "Exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println();
            JOptionPane.showMessageDialog(null, "Falló al guardar", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarNullo(JDateChooser jd) {
        return jd.getDate() != null;
    }

}
