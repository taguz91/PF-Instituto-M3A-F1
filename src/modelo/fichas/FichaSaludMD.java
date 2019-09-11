/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.fichas;

import java.time.LocalDate;
import modelo.persona.PersonaMD;

/**
 *
 * @author MrRainx
 */
public class FichaSaludMD {

    private int id;
    private boolean estadoRevision;
    private boolean estadoEnvio;
    private PrdIngresoFichaSaludMD periodo;
    private PersonaMD persona;
    private LocalDate ingresoInicioExtend;
    private LocalDate ingresoFinExtend;

    public FichaSaludMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEstadoRevision() {
        return estadoRevision;
    }

    public void setEstadoRevision(boolean estadoRevision) {
        this.estadoRevision = estadoRevision;
    }

    public boolean isEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(boolean estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public PrdIngresoFichaSaludMD getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PrdIngresoFichaSaludMD periodo) {
        this.periodo = periodo;
    }

    public PersonaMD getPersona() {
        return persona;
    }

    public void setPersona(PersonaMD persona) {
        this.persona = persona;
    }

    public LocalDate getIngresoInicioExtend() {
        return ingresoInicioExtend;
    }

    public void setIngresoInicioExtend(LocalDate ingresoInicioExtend) {
        this.ingresoInicioExtend = ingresoInicioExtend;
    }

    public LocalDate getIngresoFinExtend() {
        return ingresoFinExtend;
    }

    public void setIngresoFinExtend(LocalDate ingresoFinExtend) {
        this.ingresoFinExtend = ingresoFinExtend;
    }

}
