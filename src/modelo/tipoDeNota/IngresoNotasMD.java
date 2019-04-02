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
    private boolean notaSegundoInterCicli;
    private boolean examenFinal;
    private boolean examenDeRecuperacion;

    private CursoMD curso;

    public IngresoNotasMD(int idIngresoNotas, boolean notaPrimerInterCiclo, boolean notaExamenInteCiclo, boolean notaSegundoInterCicli, boolean examenFinal, boolean examenDeRecuperacion, CursoMD curso) {
        this.idIngresoNotas = idIngresoNotas;
        this.notaPrimerInterCiclo = notaPrimerInterCiclo;
        this.notaExamenInteCiclo = notaExamenInteCiclo;
        this.notaSegundoInterCicli = notaSegundoInterCicli;
        this.examenFinal = examenFinal;
        this.examenDeRecuperacion = examenDeRecuperacion;
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

    public boolean isNotaSegundoInterCicli() {
        return notaSegundoInterCicli;
    }

    public void setNotaSegundoInterCicli(boolean notaSegundoInterCicli) {
        this.notaSegundoInterCicli = notaSegundoInterCicli;
    }

    public boolean isExamenFinal() {
        return examenFinal;
    }

    public void setExamenFinal(boolean examenFinal) {
        this.examenFinal = examenFinal;
    }

    public boolean isExamenDeRecuperacion() {
        return examenDeRecuperacion;
    }

    public void setExamenDeRecuperacion(boolean examenDeRecuperacion) {
        this.examenDeRecuperacion = examenDeRecuperacion;
    }

    public CursoMD getCurso() {
        return curso;
    }

    public void setCurso(CursoMD curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "IngresoNotasMD{" + "idIngresoNotas=" + idIngresoNotas + ", notaPrimerInterCiclo=" + notaPrimerInterCiclo + ", notaExamenInteCiclo=" + notaExamenInteCiclo + ", notaSegundoInterCicli=" + notaSegundoInterCicli + ", examenFinal=" + examenFinal + ", examenDeRecuperacion=" + examenDeRecuperacion + ", curso=" + curso + '}';
    }

}
