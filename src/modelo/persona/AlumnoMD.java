
package modelo.persona;


import java.awt.Image;
import java.time.LocalDate;
import modelo.lugar.LugarBD;

public class AlumnoMD extends PersonaMD{
    
    private String nombre;
    private String tipo_Colegio, tipo_Bachillerato, nivel_Academico, titulo_Superior, ocupacion, anio_graduacion, observacion;
    private String sector_Economico, formacion_Padre, formacion_Madre, nom_Contacto, parentesco_Contacto, contacto_Emergencia;
    private int id_Alumno,id_SecEconomico;
    private boolean educacion_Superior, pension, trabaja, activo;

    public AlumnoMD() {
    }

    public AlumnoMD(int id_Alumno, String nombre, String tipo_Colegio, String tipo_Bachillerato, String nivel_Academico, String titulo_Superior, String ocupacion, String anio_graduacion, String observacion, String sector_Economico, String formacion_Padre, String formacion_Madre, String nom_Contacto, String parentesco_Contacto, String contacto_Emergencia, int id_SecEconomico, boolean educacion_Superior, boolean pension, boolean trabaja, boolean activo, int idPersona, TipoPersonaBD tipo, LugarBD lugarNatal, LugarBD lugarResidencia, Image foto, String identificacion, String primerApellido, String segundoApellido, String primerNombre, String segundoNombre, LocalDate fechaNacimiento, String genero, char sexo, String estadoCivil, String etnia, String idiomaRaiz, String tipoSangre, String telefono, String celular, String correo, LocalDate fechaRegistro, boolean discapacidad, String tipoDiscapacidad, byte porcentajeDiscapacidad, String carnetConadis, String callePrincipal, String numeroCasa, String calleSecundaria, String referencia, String sector, String idioma, String tipoResidencia, boolean personaActiva) {
        super(idPersona, tipo, lugarNatal, lugarResidencia, foto, identificacion, primerApellido, segundoApellido, primerNombre, segundoNombre, fechaNacimiento, genero, sexo, estadoCivil, etnia, idiomaRaiz, tipoSangre, telefono, celular, correo, fechaRegistro, discapacidad, tipoDiscapacidad, porcentajeDiscapacidad, carnetConadis, callePrincipal, numeroCasa, calleSecundaria, referencia, sector, idioma, tipoResidencia, personaActiva);
        this.id_Alumno = id_Alumno;
        this.nombre = nombre;
        this.tipo_Colegio = tipo_Colegio;
        this.tipo_Bachillerato = tipo_Bachillerato;
        this.nivel_Academico = nivel_Academico;
        this.titulo_Superior = titulo_Superior;
        this.ocupacion = ocupacion;
        this.anio_graduacion = anio_graduacion;
        this.observacion = observacion;
        this.sector_Economico = sector_Economico;
        this.formacion_Padre = formacion_Padre;
        this.formacion_Madre = formacion_Madre;
        this.nom_Contacto = nom_Contacto;
        this.parentesco_Contacto = parentesco_Contacto;
        this.contacto_Emergencia = contacto_Emergencia;
        this.id_SecEconomico = id_SecEconomico;
        this.educacion_Superior = educacion_Superior;
        this.pension = pension;
        this.trabaja = trabaja;
        this.activo = activo;
    }

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

    public String getSector_Economico() {
        return sector_Economico;
    }

    public void setSector_Economico(String sector_Economico) {
        this.sector_Economico = sector_Economico;
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

    public int getId_SecEconomico() {
        return id_SecEconomico;
    }

    public void setId_SecEconomico(int id_SecEconomico) {
        this.id_SecEconomico = id_SecEconomico;
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
    
}
