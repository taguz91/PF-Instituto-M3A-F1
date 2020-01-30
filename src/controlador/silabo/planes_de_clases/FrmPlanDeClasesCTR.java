/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planes_de_clases;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import modelo.PlanClases.PlandeClasesMD;
import vista.silabos.new_planes_de_clase.FrmPlanDeClase;

/**
 *
 * @author MrRainx
 */
public class FrmPlanDeClasesCTR extends AbstractVTN<FrmPlanDeClase, PlandeClasesMD> {

    public FrmPlanDeClasesCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new FrmPlanDeClase();
    }

    @Override
    public void Init() {

        setInformacion();
        super.Init(); //To change body of generated methods, choose Tools | Templates.
    }

    private void setInformacion() {

        String lblTitulo = this.vista.getLblTitulo()
                .getText()
                .replace("{numero}", String.valueOf(this.modelo.getUnidad().getNumeroUnidad()))
                .replace("{titulo}", this.modelo.getUnidad().getTituloUnidad());

        String lblInfo1 = this.vista.getLblInfo1()
                .getText()
                .replace("{fecha_inicio}", this.modelo.getUnidad().getFechaInicioUnidad().toString())
                .replace("{fecha_fin}", this.modelo.getUnidad().getFechaFinUnidad().toString())
                .replace("{duracion}", "");

        String lblInfo2 = this.vista.getLblInfo2()
                .getText()
                .replace("{carrera}", this.modelo.getCurso().getPeriodo().getCarrera().getNombre())
                .replace("{asignatura}", this.modelo.getCurso().getMateria().getNombre())
                .replace("{codigoAsignatura}", this.modelo.getCurso().getMateria().getCodigo());

        String lblInfo3 = this.vista.getLblInfo3()
                .getText()
                .replace("{curso}", this.modelo.getCurso().getNombre())
                .replace("{docente}", this.modelo.getCurso().getDocente().getInfoCompleta());

        this.vista.getLblTitulo().setText(lblTitulo);
        this.vista.getLblInfo1().setText(lblInfo1);
        this.vista.getLblInfo2().setText(lblInfo2);
        this.vista.getLblInfo3().setText(lblInfo3);

    }

}
