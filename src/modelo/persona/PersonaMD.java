package modelo.persona;

import java.awt.Image;
import java.io.FileInputStream;
import java.time.LocalDate;
import modelo.lugar.LugarBD;
import modelo.lugar.LugarMD;

/**
 *
 * @author Lina
 */
public class PersonaMD {

    //Atributos para la foto
    private FileInputStream file;
    private int logBytes;
    //Atributos de la clase Persona
    private int idPersona;
    private TipoPersonaMD tipo;
    private LugarMD lugarNatal;
    private LugarMD lugarResidencia;
    private Image foto;
    private String identificacion;
    private String primerApellido;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;
    private LocalDate fechaNacimiento;
    private String genero;
    private char sexo;
    private String estadoCivil;
    private String etnia;
    private String idiomaRaiz;
    private String tipoSangre;
    private String telefono;
    private String celular;
    private String correo;
    private LocalDate fechaRegistro;
    private boolean discapacidad;
    private String tipoDiscapacidad;
    private int porcentajeDiscapacidad;
    private String carnetConadis;
    private String callePrincipal;
    private String numeroCasa;
    private String calleSecundaria;
    private String referencia;
    private String sector;
    private String idioma;
    private String tipoResidencia;
    private boolean personaActiva;

    //33 atributos
    //Constructor vacio
    public PersonaMD() {
    }

    //Constructor con todos los atributos de la clase Persona
    public PersonaMD(int idPersona, TipoPersonaBD tipo, LugarBD lugarNatal, LugarBD lugarResidencia, Image foto, String identificacion, String primerApellido, String segundoApellido, String primerNombre, String segundoNombre, LocalDate fechaNacimiento, String genero, char sexo, String estadoCivil, String etnia, String idiomaRaiz, String tipoSangre, String telefono, String celular, String correo, LocalDate fechaRegistro, boolean discapacidad, String tipoDiscapacidad, byte porcentajeDiscapacidad, String carnetConadis, String callePrincipal, String numeroCasa, String calleSecundaria, String referencia, String sector, String idioma, String tipoResidencia, boolean personaActiva) {
        this.idPersona = idPersona;
        this.tipo = tipo;
        this.lugarNatal = lugarNatal;
        this.lugarResidencia = lugarResidencia;
        this.foto = foto;
        this.identificacion = identificacion;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.etnia = etnia;
        this.idiomaRaiz = idiomaRaiz;
        this.tipoSangre = tipoSangre;
        this.telefono = telefono;
        this.celular = celular;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.discapacidad = discapacidad;
        this.tipoDiscapacidad = tipoDiscapacidad;
        this.porcentajeDiscapacidad = porcentajeDiscapacidad;
        this.carnetConadis = carnetConadis;
        this.callePrincipal = callePrincipal;
        this.numeroCasa = numeroCasa;
        this.calleSecundaria = calleSecundaria;
        this.referencia = referencia;
        this.sector = sector;
        this.idioma = idioma;
        this.tipoResidencia = tipoResidencia;
        this.personaActiva = personaActiva;
    }

    public PersonaMD(TipoPersonaMD tipo, LugarMD lugarNatal, LugarMD lugarResidencia, String identificacion, String primerApellido, String segundoApellido, String primerNombre, String segundoNombre, LocalDate fechaNacimiento, String genero, char sexo, String estadoCivil, String etnia, String idiomaRaiz, String tipoSangre, String telefono, String celular, String correo, LocalDate fechaRegistro, boolean discapacidad, String tipoDiscapacidad, byte porcentajeDiscapacidad, String carnetConadis, String callePrincipal, String numeroCasa, String calleSecundaria, String referencia, String sector, String idioma, String tipoResidencia, boolean personaActiva) {
        this.tipo = tipo;
        this.lugarNatal = lugarNatal;
        this.lugarResidencia = lugarResidencia;
        this.identificacion = identificacion;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.etnia = etnia;
        this.idiomaRaiz = idiomaRaiz;
        this.tipoSangre = tipoSangre;
        this.telefono = telefono;
        this.celular = celular;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.discapacidad = discapacidad;
        this.tipoDiscapacidad = tipoDiscapacidad;
        this.porcentajeDiscapacidad = porcentajeDiscapacidad;
        this.carnetConadis = carnetConadis;
        this.callePrincipal = callePrincipal;
        this.numeroCasa = numeroCasa;
        this.calleSecundaria = calleSecundaria;
        this.referencia = referencia;
        this.sector = sector;
        this.idioma = idioma;
        this.tipoResidencia = tipoResidencia;
        this.personaActiva = personaActiva;
    }

    public FileInputStream getFile() {
        return file;
    }

    public void setFile(FileInputStream file) {
        this.file = file;
    }

    public int getLogBytes() {
        return logBytes;
    }

    public void setLogBytes(int logBytes) {
        this.logBytes = logBytes;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public TipoPersonaMD getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersonaMD tipo) {
        this.tipo = tipo;
    }

    public LugarMD getLugarNatal() {
        return lugarNatal;
    }

    public void setLugarNatal(LugarMD lugarNatal) {
        this.lugarNatal = lugarNatal;
    }

    public LugarMD getLugarResidencia() {
        return lugarResidencia;
    }

    public void setLugarResidencia(LugarMD lugarResidencia) {
        this.lugarResidencia = lugarResidencia;
    }

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public boolean isPersonaActiva() {
        return personaActiva;
    }

    public void setPersonaActiva(boolean personaActiva) {
        this.personaActiva = personaActiva;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public String getIdiomaRaiz() {
        return idiomaRaiz;
    }

    public void setIdiomaRaiz(String idiomaRaiz) {
        this.idiomaRaiz = idiomaRaiz;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    public int getPorcentajeDiscapacidad() {
        return porcentajeDiscapacidad;
    }

    public void setPorcentajeDiscapacidad(int porcentajeDiscapacidad) {
        this.porcentajeDiscapacidad = porcentajeDiscapacidad;
    }

    public String getCarnetConadis() {
        return carnetConadis;
    }

    public void setCarnetConadis(String carnetConadis) {
        this.carnetConadis = carnetConadis;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCalleSecundaria() {
        return calleSecundaria;
    }

    public void setCalleSecundaria(String calleSecundaria) {
        this.calleSecundaria = calleSecundaria;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTipoResidencia() {
        return tipoResidencia;
    }

    public void setTipoResidencia(String tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
    }
    
    //Guardamos todos los datos de una persona 
    public void setPersona(PersonaMD per) {
        this.primerApellido = per.getPrimerApellido();
        this.primerNombre = per.getPrimerNombre();
        this.callePrincipal = per.getCallePrincipal();
        
    }
}
