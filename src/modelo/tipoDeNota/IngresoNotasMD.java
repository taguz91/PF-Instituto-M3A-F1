/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tipoDeNota;

import modelo.curso.CursoMD;

/**
 *
 * @author MrRainx
 */
public class IngresoNotasMD {

    private int idIngresoNotas;

    private boolean notaPrimerInterCiclo;
    private boolean notaExamenInteCiclo;
    private boolean notaSegundoInterCiclo;
    private boolean notaExamenFinal;
    private boolean notaExamenDeRecuperacion;

    private CursoMD curso;

    public IngresoNotasMD(int idIngresoNotas, boolean notaPrimerInterCiclo, boolean notaExamenInteCiclo, boolean notaSegundoInterCicli, boolean examenFinal, boolean examenDeRecuperacion, CursoMD curso) {
        this.idIngresoNotas = idIngresoNotas;
        this.notaPrimerInterCiclo = notaPrimerInterCiclo;
        this.notaExamenInteCiclo = notaExamenInteCiclo;
        this.notaSegundoInterCiclo = notaSegundoInterCicli;
        this.notaExamenFinal = examenFinal;
        this.notaExamenDeRecuperacion = examenDeRecuperacion;
        this.curso = curso;
    }

    public IngresoNotasMD() {
    }

    public int getIdIngresoNotas() {
        return idIngresoNotas;
    }

    public void setIdIngresoNotas(int idIngresoNotas) {
        this.idIngresoNotas = idIngresoNotas;
    }

    public boolean isNotaPrimerInterCiclo() {
        return notaPrimerInterCiclo;
    }

    public void setNotaPrimerInterCiclo(boolean notaPrimerInterCiclo) {
        this.notaPrimerInterCiclo = notaPrimerInterCiclo;
    }

    public boolean isNotaExamenInteCiclo() {
        return notaExamenInteCiclo;
    }

    public void setNotaExamenInteCiclo(boolean notaExamenInteCiclo) {
        this.notaExamenInteCiclo = notaExamenInteCiclo;
    }

    public boolean isNotaSegundoInterCiclo() {
        return notaSegundoInterCiclo;
    }

    public void setNotaSegundoInterCiclo(boolean notaSegundoInterCiclo) {
        this.notaSegundoInterCiclo = notaSegundoInterCiclo;
    }

    public boolean isNotaExamenFinal() {
        return notaExamenFinal;
    }

    public void setNotaExamenFinal(boolean notaExamenFinal) {
        this.notaExamenFinal = notaExamenFinal;
    }

    public boolean isNotaExamenDeRecuperacion() {
        return notaExamenDeRecuperacion;
    }

    public void setNotaExamenDeRecuperacion(boolean notaExamenDeRecuperacion) {
        this.notaExamenDeRecuperacion = notaExamenDeRecuperacion;
    }

    public CursoMD getCurso() {
        return curso;
    }

    public void setCurso(CursoMD curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "IngresoNotasMD{" + "idIngresoNotas=" + idIngresoNotas + ", notaPrimerInterCiclo=" + notaPrimerInterCiclo + ", notaExamenInteCiclo=" + notaExamenInteCiclo + ", notaSegundoInterCiclo=" + notaSegundoInterCiclo + ", notaExamenFinal=" + notaExamenFinal + ", notaExamenDeRecuperacion=" + notaExamenDeRecuperacion + ", curso=" + curso + '}';
    }

}
