package modelo.persona;

import java.awt.Image;
import java.io.FileInputStream;
import java.time.LocalDate;
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
    //private TipoPersonaMD tipo;//Se elimino de la base de datos
    private LugarMD lugarNatal;
    private LugarMD lugarResidencia;
    private ProfesionMD profesion;
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
    private String categoriaMigratoria;
    private boolean personaActiva;

    //33 atributos
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

    public String getCategoriaMigratoria() {
        return categoriaMigratoria;
    }

    public void setCategoriaMigratoria(String categoriaMigratoria) {
        this.categoriaMigratoria = categoriaMigratoria;
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
        this.idPersona = per.getIdPersona();
        this.lugarNatal = per.getLugarNatal();
        this.lugarResidencia = per.getLugarResidencia();
        this.foto = per.getFoto();
        this.identificacion = per.getIdentificacion();
        this.primerApellido = per.getPrimerApellido();
        this.segundoApellido = per.getSegundoApellido();
        this.primerNombre = per.getPrimerNombre();
        this.segundoNombre = per.getSegundoNombre();
        this.fechaNacimiento = per.getFechaNacimiento();
        this.genero = per.getGenero();
        this.sexo = per.getSexo();
        this.estadoCivil = per.getEstadoCivil();
        this.etnia = per.getEtnia();
        this.idiomaRaiz = per.getIdiomaRaiz();
        this.tipoSangre = per.getTipoSangre();
        this.telefono = per.getTelefono();
        this.celular = per.getCelular();
        this.correo = per.getCorreo();
        this.fechaRegistro = per.getFechaRegistro();
        this.discapacidad = per.isDiscapacidad();
        this.tipoDiscapacidad = per.getTipoDiscapacidad();
        this.porcentajeDiscapacidad = per.getPorcentajeDiscapacidad();
        this.carnetConadis = per.getCarnetConadis();
        this.callePrincipal = per.getCallePrincipal();
        this.numeroCasa = per.getNumeroCasa();
        this.calleSecundaria = per.getCalleSecundaria();
        this.referencia = per.getReferencia();
        this.sector = per.getSector();
        this.idioma = per.getIdioma();
        this.tipoResidencia = per.getTipoResidencia();
        this.categoriaMigratoria = per.getCategoriaMigratoria();
        this.personaActiva = per.isPersonaActiva();

    }

    public String getSoloNombres() {
        return getPrimerNombre() + " " + getSegundoNombre();
    }

    public String getSoloApellidos() {
        return getPrimerApellido() + " " + getSegundoApellido();
    }

    public String getNombreCompleto() {
        return getPrimerNombre() + " " + getSegundoNombre() + " " + getPrimerApellido() + " " + getSegundoApellido();
    }

    public String getApellidosNombres() {
        return getPrimerApellido() + " "
                + getSegundoApellido() + " "
                + getPrimerNombre() + " "
                + getSegundoNombre();
    }

    public String getNombreCorto() {
        return getPrimerNombre() + " " + getPrimerApellido();
    }

    public String getInfo() {
        return identificacion + " " + primerApellido + " " + primerNombre;
    }

    public String getInfoCompleta() {
        return String.format(
                "%s %s %s %s %s",
                this.identificacion,
                this.primerNombre,
                this.segundoNombre,
                this.primerApellido,
                this.segundoApellido
        );
    }

    @Override
    public String toString() {
        return "PersonaMD{" + "file=" + file + ", logBytes=" + logBytes + ", idPersona="
                + idPersona + ", lugarNatal=" + lugarNatal + ", lugarResidencia="
                + lugarResidencia + ", foto=" + foto + ", identificacion="
                + identificacion + ", primerApellido=" + primerApellido + ", segundoApellido="
                + segundoApellido + ", primerNombre=" + primerNombre + ", segundoNombre="
                + segundoNombre + ", fechaNacimiento=" + fechaNacimiento + ", genero="
                + genero + ", sexo=" + sexo + ", estadoCivil=" + estadoCivil + ", etnia="
                + etnia + ", idiomaRaiz=" + idiomaRaiz + ", tipoSangre=" + tipoSangre + ", telefono="
                + telefono + ", celular=" + celular + ", correo=" + correo + ", fechaRegistro="
                + fechaRegistro + ", discapacidad=" + discapacidad + ", tipoDiscapacidad="
                + tipoDiscapacidad + ", porcentajeDiscapacidad=" + porcentajeDiscapacidad + ", carnetConadis="
                + carnetConadis + ", callePrincipal=" + callePrincipal + ", numeroCasa="
                + numeroCasa + ", calleSecundaria=" + calleSecundaria + ", referencia="
                + referencia + ", sector=" + sector + ", idioma=" + idioma + ", tipoResidencia="
                + tipoResidencia + ", categoriaMigratoria=" + categoriaMigratoria + ", personaActiva="
                + personaActiva + '}';
    }

    public ProfesionMD getProfesion() {
        return profesion;
    }

    public void setProfesion(ProfesionMD profesion) {
        this.profesion = profesion;
    }

}
