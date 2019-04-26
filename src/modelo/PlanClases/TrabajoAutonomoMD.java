/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

import modelo.evaluacionSilabo.EvaluacionSilaboMD;


public class TrabajoAutonomoMD {
    private EvaluacionSilaboMD id_evaluacion;
    private PlandeClasesMD id_plan_clases;
    private String autonomo_plan_descripcion;

    public TrabajoAutonomoMD() {
        this.id_evaluacion=new EvaluacionSilaboMD();
        this.id_plan_clases=new PlandeClasesMD();
    }

    public TrabajoAutonomoMD(EvaluacionSilaboMD id_evaluacion, PlandeClasesMD id_plan_clases) {
        this.id_evaluacion = id_evaluacion;
        this.id_plan_clases = id_plan_clases;
    }

    public EvaluacionSilaboMD getId_evaluacion() {
        return id_evaluacion;
    }

    public void setId_evaluacion(EvaluacionSilaboMD id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }

    public PlandeClasesMD getId_plan_clases() {
        return id_plan_clases;
    }

    public void setId_plan_clases(PlandeClasesMD id_plan_clases) {
        this.id_plan_clases = id_plan_clases;
    }

    public String getAutonomo_plan_descripcion() {
        return autonomo_plan_descripcion;
    }

    public void setAutonomo_plan_descripcion(String autonomo_plan_descripcion) {
        this.autonomo_plan_descripcion = autonomo_plan_descripcion;
    }
    
    
    
    
    
}
