/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.seguimientoSilabo;

import controlador.Libraries.Middlewares;
import java.time.LocalDateTime;
import modelo.curso.CursoMD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;

/**
 *
 * @author MrRainx
 */
public class SeguimientoEvaluacionMD {

    private Integer ID;

    private Integer formato;
    private String observacion;
    private LocalDateTime fechaEdicion;
    private LocalDateTime fechaCreacion;

    private CursoMD curso;
    private EvaluacionSilaboMD evaluacion;

    public static Integer FISICO = 0;
    public static Integer DIGITAL = 1;

    public SeguimientoEvaluacionMD() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getFormato() {
        return formato;
    }

    public void setFormato(Integer formato) {
        this.formato = formato;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDateTime getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(LocalDateTime fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CursoMD getCurso() {
        return curso;
    }

    public void setCurso(CursoMD curso) {
        this.curso = curso;
    }

    public EvaluacionSilaboMD getEvaluacion() {
        return evaluacion;
    }

    public String getTxtAreaDescripcion() {
        return "        DESCRIPCIÓN     \n"
                + "-UNIDAD " + evaluacion.getIdUnidad().getNumeroUnidad() + "\n"
                + "     " + evaluacion.getIdUnidad().getTituloUnidad() + "\n\n"
                + "CURSO:\n"
                + "     " + curso.getNombre() + "\n\n"
                + "EVALUACIÓN:\n"
                + " -Indicador:\n    " + evaluacion.getIndicador() + "\n"
                + " -Instrumento:\n    " + evaluacion.getInstrumento() + "\n"
                + " -Valoración:\n    " + evaluacion.getValoracion() + "\n\n"
                + "GESTIÓN ACADEMICA:\n"
                + " -Formato:\n    " + formatoToString(formato) + "\n"
                + " -Observaciones:\n    " + observacion + "\n\n"
                + " -Fecha de Creación:\n    " + Middlewares.simpleDateFormat(fechaCreacion) + "\n"
                + " -Ultima Edición:\n    " + Middlewares.simpleDateFormat(fechaEdicion)
                + ""
                + "";
    }

    public void setEvaluacion(EvaluacionSilaboMD evaluacion) {
        this.evaluacion = evaluacion;
    }

    public static String formatoToString(Integer formato) {
        if (formato.equals(FISICO)) {
            return "FÍSICO";
        } else if (formato.equals(DIGITAL)) {
            return "DIGITAL";
        }
        return "";
    }

    public static int formatoToInt(String formato) {

        if (formato.equalsIgnoreCase("FÍSICO")) {
            return 0;
        } else if (formato.equalsIgnoreCase("DIGITAL")) {
            return 1;
        }

        return 1;

    }

    public String getNombreCmb() {
        return ID + "| " + evaluacion.getInstrumento();
    }

    @Override
    public String toString() {
        return "SeguimientoEvaluacionMD{" + "ID=" + ID + ", formato=" + formato + ", observacion=" + observacion + ", fechaEdicion=" + fechaEdicion + ", fechaCreacion=" + fechaCreacion + ", curso=" + curso + ", evaluacion=" + evaluacion + '}';
    }

}
