package modelo.persona;

import java.awt.Image;
import java.time.LocalDate;
import modelo.lugar.LugarBD;

public class AlumnoMD extends PersonaMD {

    private String nombre;
    private String tipo_Colegio, tipo_Bachillerato, nivel_Academico, titulo_Superior, ocupacion, anio_graduacion, observacion;
    private String formacion_Padre, formacion_Madre, nom_Contacto, parentesco_Contacto, contacto_Emergencia;
    private int id_Alumno;
    private SectorEconomicoMD sectorEconomico;
    private boolean educacion_Superior, pension, trabaja, activo;

    public String getTipo_Colegio() {
        return tipo_Colegio;
    }

    public void setTipo_Colegio(String tipo_Colegio) {
        this.tipo_Colegio = tipo_Colegio;
    }

    public String getTipo_Bachillerato() {
        return tipo_Bachillerato;
    }

    public void setTipo_Bachillerato(String tipo_Bachillerato) {
        this.tipo_Bachillerato = tipo_Bachillerato;
    }

    public String getNivel_Academico() {
        return nivel_Academico;
    }

    public void setNivel_Academico(String nivel_Academico) {
        this.nivel_Academico = nivel_Academico;
    }

    public String getTitulo_Superior() {
        return titulo_Superior;
    }

    public void setTitulo_Superior(String titulo_Superior) {
        this.titulo_Superior = titulo_Superior;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getFormacion_Padre() {
        return formacion_Padre;
    }

    public void setFormacion_Padre(String formacion_Padre) {
        this.formacion_Padre = formacion_Padre;
    }

    public String getFormacion_Madre() {
        return formacion_Madre;
    }

    public void setFormacion_Madre(String formacion_Madre) {
        this.formacion_Madre = formacion_Madre;
    }

    public String getNom_Contacto() {
        return nom_Contacto;
    }

    public void setNom_Contacto(String num_Contacto) {
        this.nom_Contacto = num_Contacto;
    }

    public String getParentesco_Contacto() {
        return parentesco_Contacto;
    }

    public void setParentesco_Contacto(String parentesco_Contacto) {
        this.parentesco_Contacto = parentesco_Contacto;
    }

    public String getContacto_Emergencia() {
        return contacto_Emergencia;
    }

    public void setContacto_Emergencia(String contacto_Emergencia) {
        this.contacto_Emergencia = contacto_Emergencia;
    }

    public boolean isEducacion_Superior() {
        return educacion_Superior;
    }

    public void setEducacion_Superior(boolean educacion_Superior) {
        this.educacion_Superior = educacion_Superior;
    }

    public boolean isPension() {
        return pension;
    }

    public void setPension(boolean pension) {
        this.pension = pension;
    }

    public boolean isTrabaja() {
        return trabaja;
    }

    public void setTrabaja(boolean trabaja) {
        this.trabaja = trabaja;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_Alumno() {
        return id_Alumno;
    }

    public void setId_Alumno(int id_Alumno) {
        this.id_Alumno = id_Alumno;
    }

    public String getAnio_graduacion() {
        return anio_graduacion;
    }

    public void setAnio_graduacion(String anio_graduacion) {
        this.anio_graduacion = anio_graduacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public SectorEconomicoMD getSectorEconomico() {
        return sectorEconomico;
    }

    public void setSectorEconomico(SectorEconomicoMD sectorEconomico) {
        this.sectorEconomico = sectorEconomico;
    }

}
