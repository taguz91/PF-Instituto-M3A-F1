/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planesDeClase;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.curso.CursoMD;
import vista.silabos.planesDeClase.VtnCopiarPlan;

/**
 *
 * @author MrRainx
 */
public class VtnCopiarPlanCTR extends AbstractVTN<VtnCopiarPlan, PlandeClasesMD> {

    private final PlandeClasesBD CON = PlandeClasesBD.single();
    private List<CursoMD> cursos = null;

    public VtnCopiarPlanCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnCopiarPlan();
    }

    public List<CursoMD> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoMD> cursos) {
        this.cursos = cursos;
    }

    @Override
    public void Init() {

        this.vista.getLblEstado().setText(modelo.descripcion());

        cargarCmb();
        super.Init();

        InitEventos();

    }

    private void InitEventos() {

        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());

    }

    private void cargarCmb() {
        this.cursos.stream().map(c -> c.getNombre())
                .forEach(vista.getCmbCursos()::addItem);
    }

    private void btnGuardar(ActionEvent e) {

        CursoMD curso = cursos.stream()
                .filter(item -> item.getNombre().equals(vista.getCmbCursos().getSelectedItem().toString()))
                .findFirst()
                .get();

        CON.copiarPlabes(modelo.getID(), curso.getId());

        vista.dispose();

        JOptionPane.showMessageDialog(null, "SE HA COPIADO CORRECTAMENTE", "AVISO", JOptionPane.INFORMATION_MESSAGE);

    }

}
