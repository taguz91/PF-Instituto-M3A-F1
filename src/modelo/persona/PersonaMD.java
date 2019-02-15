package modelo.persona;

import java.awt.Image;
import java.io.FileInputStream;
import java.time.LocalDate;
import modelo.lugar.LugarBD;

/**
 *
 * @author Lina
 */
public class PersonaMD {

    private FileInputStream file;
    private int logBytes;
    

// En el diagrama de clases falta el atributo fecha de nacimiento..pero aqui ya lo incluyo
    
    private int idPersona;
    private TipoPersonaBD tipo;
    private LugarBD lugarNatal;
    private LugarBD lugarResidencia;
    private Image foto;
    private String identificacion;
    private String primerApellido;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;
    private LocalDate fechaNacimiento; //falta
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
    private byte porcentajeDiscapacidad;
    private String carnetConadis;
    private String callePrincipal;
    private String numeroCasa;
    private String calleSecundaria;
    private String referencia;
    private String sector;
    private String idioma;
    private String tipoResidencia;

    //32 atributos
    
    public PersonaMD() {
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

    public TipoPersonaBD getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersonaBD tipo) {
        this.tipo = tipo;
    }

    public LugarBD getLugarNatal() {
        return lugarNatal;
    }

    public void setLugarNatal(LugarBD lugarNatal) {
        this.lugarNatal = lugarNatal;
    }

    public LugarBD getLugarResidencia() {
        return lugarResidencia;
    }

    public void setLugarResidencia(LugarBD lugarResidencia) {
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

    public byte getPorcentajeDiscapacidad() {
        return porcentajeDiscapacidad;
    }

    public void setPorcentajeDiscapacidad(byte porcentajeDiscapacidad) {
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
}
