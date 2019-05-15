package modelo.persona;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import modelo.ConectarDB;
import modelo.ConnDBPool;
import modelo.lugar.LugarBD;

/**
 *
 * @author Lina
 */
public class PersonaBD extends PersonaMD {

    private final ConectarDB conecta;
    //Se usaran estas clases para consultar
    private final LugarBD lugar;

    //Esto se usara para cargar las fotos 
    InputStream is;
    private static ConnDBPool pool;
    private static Connection conn;
    private static ResultSet rst;

    static {
        pool = new ConnDBPool();
    }

    /**
     *
     * @param conecta
     */
    public PersonaBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.lugar = new LugarBD(conecta);
    }

    /**
     * Este método guarda a la Persona en la Base de Datos con todos estos
     * atributos
     */
    public void insertarPersona() {

        //Aqui id_persona ya no va porque es autoincrementable
        //TipoPersona si porque necesitamos saber si es estudiante
        //docente u otro 
        String nsql = "INSERT INTO public.\"Personas\"(\n"
                + "id_lugar_natal, id_lugar_residencia,"
                + "persona_identificacion, persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre,persona_genero, persona_sexo, "
                + "persona_estado_civil, persona_etnia, persona_idioma_raiz, persona_tipo_sangre, "
                + "persona_telefono, persona_celular, persona_correo, persona_fecha_registro, "
                + "persona_discapacidad, persona_tipo_discapacidad, persona_porcenta_discapacidad, "
                + "persona_carnet_conadis, persona_calle_principal, persona_numero_casa, "
                + "persona_calle_secundaria, persona_referencia, persona_sector, persona_idioma, "
                + "persona_tipo_residencia, persona_fecha_nacimiento, persona_categoria_migratoria) \n"
                + "VALUES (" + getLugarNatal().getId() + ", "
                + getLugarResidencia().getId() + " , '" + getIdentificacion() + "', '"
                + getPrimerApellido() + "', '" + getSegundoApellido() + "', '" + getPrimerNombre() + "', '"
                + getSegundoNombre() + "', '" + getGenero() + "', '" + getSexo() + "', '" + getEstadoCivil() + "', '"
                + getEtnia() + "', '" + getIdiomaRaiz() + "', '" + getTipoSangre() + "', '" + getTelefono() + "', '"
                + getCelular() + "', '" + getCorreo() + "', '" + getFechaRegistro() + "', '" + isDiscapacidad() + "', '"
                + getTipoDiscapacidad() + "', '" + getPorcentajeDiscapacidad() + "', '" + getCarnetConadis() + "', '"
                + getCallePrincipal() + "', '" + getNumeroCasa() + "', '" + getCalleSecundaria() + "', '"
                + getReferencia() + "', '" + getSector() + "', '" + getIdioma() + "', '" + getTipoResidencia() + "', '"
                + getFechaNacimiento() + "', '" + getCategoriaMigratoria() + "');";

        if (isDiscapacidad()) {
            nsql = "INSERT INTO public.\"Personas\"(\n"
                    + "id_lugar_natal, id_lugar_residencia,"
                    + "persona_identificacion, persona_primer_apellido, persona_segundo_apellido, "
                    + "persona_primer_nombre, persona_segundo_nombre,persona_genero, persona_sexo, "
                    + "persona_estado_civil, persona_etnia, persona_idioma_raiz, persona_tipo_sangre, "
                    + "persona_telefono, persona_celular, persona_correo, persona_fecha_registro, "
                    + "persona_discapacidad, persona_tipo_discapacidad, persona_porcenta_discapacidad, "
                    + "persona_carnet_conadis, persona_calle_principal, persona_numero_casa, "
                    + "persona_calle_secundaria, persona_referencia, persona_sector, persona_idioma, "
                    + "persona_tipo_residencia, persona_fecha_nacimiento, persona_categoria_migratoria) \n"
                    + "VALUES (" + getLugarNatal().getId() + ", "
                    + getLugarResidencia().getId() + ", ? , '" + getIdentificacion() + "', '"
                    + getPrimerApellido() + "', '" + getSegundoApellido() + "', '" + getPrimerNombre() + "', '"
                    + getSegundoNombre() + "', '" + getGenero() + "', '" + getSexo() + "', '" + getEstadoCivil() + "', '"
                    + getEtnia() + "', '" + getIdiomaRaiz() + "', '" + getTipoSangre() + "', '" + getTelefono() + "', '"
                    + getCelular() + "', '" + getCorreo() + "', '" + getFechaRegistro() + "', '" + isDiscapacidad() + "', '"
                    + getTipoDiscapacidad() + "', '" + getPorcentajeDiscapacidad() + "', '" + getCarnetConadis() + "', '"
                    + getCallePrincipal() + "', '" + getNumeroCasa() + "', '" + getCalleSecundaria() + "', '"
                    + getReferencia() + "', '" + getSector() + "', '" + getIdioma() + "', '" + getTipoResidencia() + "', '"
                    + getFechaNacimiento() + "', '" + getCategoriaMigratoria() + "');";
        }
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            System.out.println("Se guardo correctamente");
        }
    }

    //persona_categoria_migratoria
    public void insertarPersonaConFoto() {
        //Aqui id_persona ya no va porque es autoincrementable
        String nsql = "INSERT INTO public.\"Personas\"(\n"
                + "id_lugar_natal, id_lugar_residencia, persona_foto,"
                + "persona_identificacion, persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre,persona_genero, persona_sexo, "
                + "persona_estado_civil, persona_etnia, persona_idioma_raiz, persona_tipo_sangre, "
                + "persona_telefono, persona_celular, persona_correo, persona_fecha_registro, "
                + "persona_calle_principal, persona_numero_casa, "
                + "persona_calle_secundaria, persona_referencia, persona_sector, persona_idioma, "
                + "persona_tipo_residencia, persona_fecha_nacimiento, persona_categoria_migratoria) \n"
                + "VALUES (" + getLugarNatal().getId() + ", "
                + getLugarResidencia().getId() + ", ? , '" + getIdentificacion() + "', '"
                + getPrimerApellido() + "', '" + getSegundoApellido() + "', '" + getPrimerNombre() + "', '"
                + getSegundoNombre() + "', '" + getGenero() + "', '" + getSexo() + "', '" + getEstadoCivil() + "', '"
                + getEtnia() + "', '" + getIdiomaRaiz() + "', '" + getTipoSangre() + "', '" + getTelefono() + "', '"
                + getCelular() + "', '" + getCorreo() + "', '" + getFechaRegistro() + "', '"
                + getCallePrincipal() + "', '" + getNumeroCasa() + "', '" + getCalleSecundaria() + "', '"
                + getReferencia() + "', '" + getSector() + "', '" + getIdioma() + "', '" + getTipoResidencia() + "', '"
                + getFechaNacimiento() + "', '" + getCategoriaMigratoria() + "');";

        if (isDiscapacidad()) {
            nsql = "INSERT INTO public.\"Personas\"(\n"
                    + "id_lugar_natal, id_lugar_residencia, persona_foto,"
                    + "persona_identificacion, persona_primer_apellido, persona_segundo_apellido, "
                    + "persona_primer_nombre, persona_segundo_nombre,persona_genero, persona_sexo, "
                    + "persona_estado_civil, persona_etnia, persona_idioma_raiz, persona_tipo_sangre, "
                    + "persona_telefono, persona_celular, persona_correo, persona_fecha_registro, "
                    + "persona_discapacidad, persona_tipo_discapacidad, persona_porcenta_discapacidad, "
                    + "persona_carnet_conadis, persona_calle_principal, persona_numero_casa, "
                    + "persona_calle_secundaria, persona_referencia, persona_sector, persona_idioma, "
                    + "persona_tipo_residencia, persona_fecha_nacimiento, persona_categoria_migratoria) \n"
                    + "VALUES (" + getLugarNatal().getId() + ", "
                    + getLugarResidencia().getId() + ", ? , '" + getIdentificacion() + "', '"
                    + getPrimerApellido() + "', '" + getSegundoApellido() + "', '" + getPrimerNombre() + "', '"
                    + getSegundoNombre() + "', '" + getGenero() + "', '" + getSexo() + "', '" + getEstadoCivil() + "', '"
                    + getEtnia() + "', '" + getIdiomaRaiz() + "', '" + getTipoSangre() + "', '" + getTelefono() + "', '"
                    + getCelular() + "', '" + getCorreo() + "', '" + getFechaRegistro() + "', '" + isDiscapacidad() + "', '"
                    + getTipoDiscapacidad() + "', '" + getPorcentajeDiscapacidad() + "', '" + getCarnetConadis() + "', '"
                    + getCallePrincipal() + "', '" + getNumeroCasa() + "', '" + getCalleSecundaria() + "', '"
                    + getReferencia() + "', '" + getSector() + "', '" + getIdioma() + "', '" + getTipoResidencia() + "', '"
                    + getFechaNacimiento() + "', '" + getCategoriaMigratoria() + "');";
        }

        PreparedStatement ps = conecta.getPS(nsql);
        if (ps != null) {
            try {
                ps.setBinaryStream(1, getFile(), getLogBytes());

                if (conecta.nosql(ps) == null) {
                    System.out.println("SE guarado correctamente.");
                }
            } catch (SQLException e) {
                System.out.println("No se pudo guardar persona con foto");
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Este método edita a una Persona que no tiene foto de la Base de Datos con
     * todos estos atributos
     *
     * @param aguja Se tiene que pasar un int como la Id de persona
     * @return Retorna un boolean según el resultado de la Edición
     */
    public boolean editarPersona(int aguja) {
        String sql;

        if (isDiscapacidad()) {
            sql = "UPDATE public.\"Personas\" SET\n"
                    + " id_lugar_natal = " + getLugarNatal().getId()
                    + ", id_lugar_residencia = " + getLugarResidencia().getId()
                    + ", persona_identificacion = '" + getIdentificacion() + "', persona_primer_apellido = '"
                    + getPrimerApellido() + "', persona_segundo_apellido = '" + getSegundoApellido()
                    + "', persona_primer_nombre = '" + getPrimerNombre() + "', persona_segundo_nombre = '"
                    + getSegundoNombre() + "', persona_genero = '" + getGenero()
                    + "', persona_sexo = '" + getSexo() + "', persona_estado_civil = '" + getEstadoCivil()
                    + "', persona_etnia = '" + getEtnia() + "', persona_idioma_raiz = '" + getIdiomaRaiz()
                    + "', persona_tipo_sangre = '" + getTipoSangre() + "', persona_telefono = '" + getTelefono()
                    + "', persona_celular = '" + getCelular() + "', persona_correo = '" + getCorreo()
                    + "', persona_fecha_registro = '" + getFechaRegistro() + "', persona_discapacidad = '"
                    + isDiscapacidad() + "', persona_tipo_discapacidad = '" + getTipoDiscapacidad()
                    + "', persona_porcenta_discapacidad = '" + getPorcentajeDiscapacidad()
                    + "', persona_carnet_conadis = '" + getCarnetConadis() + "', persona_calle_principal = '"
                    + getCallePrincipal() + "', persona_numero_casa = '" + getNumeroCasa()
                    + "', persona_calle_secundaria = '" + getCalleSecundaria() + "', persona_referencia = '"
                    + getReferencia() + "', persona_sector = '" + getSector() + "', persona_idioma = '"
                    + getIdioma() + "', persona_tipo_residencia = '" + getTipoResidencia()
                    + "', persona_fecha_nacimiento = '" + getFechaNacimiento()
                    + "', persona_categoria_migratoria = '" + getCategoriaMigratoria() + "'\n"
                    + " WHERE id_persona = " + aguja + ";";
        } else {
            sql = "UPDATE public.\"Personas\" SET\n"
                    + " id_lugar_natal = " + getLugarNatal().getId()
                    + ", id_lugar_residencia = " + getLugarResidencia().getId()
                    + ", persona_identificacion = '" + getIdentificacion() + "', persona_primer_apellido = '"
                    + getPrimerApellido() + "', persona_segundo_apellido = '" + getSegundoApellido()
                    + "', persona_primer_nombre = '" + getPrimerNombre() + "', persona_segundo_nombre = '"
                    + getSegundoNombre() + "', persona_genero = '" + getGenero()
                    + "', persona_sexo = '" + getSexo() + "', persona_estado_civil = '" + getEstadoCivil()
                    + "', persona_etnia = '" + getEtnia() + "', persona_idioma_raiz = '" + getIdiomaRaiz()
                    + "', persona_tipo_sangre = '" + getTipoSangre() + "', persona_telefono = '" + getTelefono()
                    + "', persona_celular = '" + getCelular() + "', persona_correo = '" + getCorreo()
                    + "', persona_fecha_registro = '" + getFechaRegistro() + "', persona_discapacidad = false"
                    + ", persona_tipo_discapacidad = null , persona_porcenta_discapacidad = 0 "
                    + ", persona_carnet_conadis = null, persona_calle_principal = '"
                    + getCallePrincipal() + "', persona_numero_casa = '" + getNumeroCasa()
                    + "', persona_calle_secundaria = '" + getCalleSecundaria() + "', persona_referencia = '"
                    + getReferencia() + "', persona_sector = '" + getSector() + "', persona_idioma = '"
                    + getIdioma() + "', persona_tipo_residencia = '" + getTipoResidencia()
                    + "', persona_fecha_nacimiento = '" + getFechaNacimiento()
                    + "', persona_categoria_migratoria = '" + getCategoriaMigratoria() + "'\n"
                    + " WHERE id_persona = " + aguja + ";";
        }
        PreparedStatement ps = conecta.getPS(sql);
        if (conecta.nosql(ps) == null) {
            System.out.println("Se edito correctamente");
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    /**
     * Este método edita a una Persona que si tiene foto de la Base de Datos con
     * todos estos atributos
     *
     * @param aguja Se tiene que pasar un int como la Id de persona
     * @return
     */
    public boolean editarPersonaConFoto(int aguja) {
        String nsql;

        if (isDiscapacidad()) {
            nsql = "UPDATE public.\"Personas\" SET\n"
                    + " id_lugar_natal = " + getLugarNatal().getId()
                    + ", id_lugar_residencia = " + getLugarResidencia().getId() + ", persona_foto = ? "
                    + ", persona_identificacion = '" + getIdentificacion() + "', persona_primer_apellido = '"
                    + getPrimerApellido() + "', persona_segundo_apellido = '" + getSegundoApellido()
                    + "', persona_primer_nombre = '" + getPrimerNombre() + "', persona_segundo_nombre = '"
                    + getSegundoNombre() + "', persona_genero = '" + getGenero()
                    + "', persona_sexo = '" + getSexo() + "', persona_estado_civil = '" + getEstadoCivil()
                    + "', persona_etnia = '" + getEtnia() + "', persona_idioma_raiz = '" + getIdiomaRaiz()
                    + "', persona_tipo_sangre = '" + getTipoSangre() + "', persona_telefono = '" + getTelefono()
                    + "', persona_celular = '" + getCelular() + "', persona_correo = '" + getCorreo()
                    + "', persona_fecha_registro = '" + getFechaRegistro() + "', persona_discapacidad = '"
                    + isDiscapacidad() + "', persona_tipo_discapacidad = '" + getTipoDiscapacidad()
                    + "', persona_porcenta_discapacidad = '" + getPorcentajeDiscapacidad()
                    + "', persona_carnet_conadis = '" + getCarnetConadis() + "', persona_calle_principal = '"
                    + getCallePrincipal() + "', persona_numero_casa = '" + getNumeroCasa()
                    + "', persona_calle_secundaria = '" + getCalleSecundaria() + "', persona_referencia = '"
                    + getReferencia() + "', persona_sector = '" + getSector() + "', persona_idioma = '"
                    + getIdioma() + "', persona_tipo_residencia = '" + getTipoResidencia()
                    + "', persona_fecha_nacimiento = '" + getFechaNacimiento()
                    + "', persona_categoria_migratoria = '" + getCategoriaMigratoria() + "'\n"
                    + " WHERE id_persona = " + aguja + ";";
        } else {
            nsql = "UPDATE public.\"Personas\" SET\n"
                    + " id_lugar_natal = " + getLugarNatal().getId()
                    + ", id_lugar_residencia = " + getLugarResidencia().getId() + ", persona_foto = ? "
                    + ", persona_identificacion = '" + getIdentificacion() + "', persona_primer_apellido = '"
                    + getPrimerApellido() + "', persona_segundo_apellido = '" + getSegundoApellido()
                    + "', persona_primer_nombre = '" + getPrimerNombre() + "', persona_segundo_nombre = '"
                    + getSegundoNombre() + "', persona_genero = '" + getGenero()
                    + "', persona_sexo = '" + getSexo() + "', persona_estado_civil = '" + getEstadoCivil()
                    + "', persona_etnia = '" + getEtnia() + "', persona_idioma_raiz = '" + getIdiomaRaiz()
                    + "', persona_tipo_sangre = '" + getTipoSangre() + "', persona_telefono = '" + getTelefono()
                    + "', persona_celular = '" + getCelular() + "', persona_correo = '" + getCorreo()
                    + "', persona_fecha_registro = '" + getFechaRegistro() + "', persona_discapacidad = false"
                    + ", persona_tipo_discapacidad = null , persona_porcenta_discapacidad = 0 "
                    + ", persona_carnet_conadis = null, persona_calle_principal = '"
                    + getCallePrincipal() + "', persona_numero_casa = '" + getNumeroCasa()
                    + "', persona_calle_secundaria = '" + getCalleSecundaria() + "', persona_referencia = '"
                    + getReferencia() + "', persona_sector = '" + getSector() + "', persona_idioma = '"
                    + getIdioma() + "', persona_tipo_residencia = '" + getTipoResidencia()
                    + "', persona_fecha_nacimiento = '" + getFechaNacimiento()
                    + "', persona_categoria_migratoria = '" + getCategoriaMigratoria() + "'\n"
                    + " WHERE id_persona = " + aguja + ";";
        }

        PreparedStatement ps = conecta.getPS(nsql);
        if (ps != null) {
            try {
                ps.setBinaryStream(1, getFile(), getLogBytes());
                if (conecta.nosql(ps) == null) {
                    System.out.println("Editamos correctamente.");
                }
                return true;
                //JOptionPane.showMessageDialog(null, "Persona editada correctamente");
            } catch (SQLException e) {
                System.out.println("No se pudo editar persona con foto" + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param identificacion
     * @return
     */
    public boolean activarPersonaIdentificacion(String identificacion) {
        String sql = "UPDATE public.\"Personas\"\n"
                + "SET persona_activa = 'true'"
                + "WHERE persona_identificacion = '" + identificacion + "';";
        PreparedStatement ps = conecta.getPS(sql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }

    }

    // Utilizamos la sentencia update para modificar el estado de una persona
    // y no eliminarla por completo con parametro por identidicacion
    /**
     *
     * @param identificacion
     * @return
     */
    public boolean eliminarPersonaIdentificacion(String identificacion) {
        String sql = "UPDATE public.\"Personas\"\n"
                + "SET persona_activa='false'"
                + "WHERE persona_identificacion = '" + identificacion + "';";
        PreparedStatement ps = conecta.getPS(sql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    // Utilizamos la sentencia update para modificar el estado de una persona
    // y no eliminarla por completo con parametro por id_persona
    public boolean eliminarPersonaId(int idpersona) {
        String sql = "UPDATE public.\"Personas\"\n"
                + "SET persona_activa='false'"
                + "WHERE id_persona = '" + idpersona + "';";
        PreparedStatement ps = conecta.getPS(sql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    //Consultamos todos las personas en nuestro sistema 
    //que no esten eliminadas
    /**
     *
     * @return
     */
    public ArrayList<PersonaMD> cargarPersonas() {
        String sql = "SELECT id_persona, persona_identificacion,"
                + " persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre, "
                + "persona_fecha_nacimiento, persona_correo, persona_celular, "
                + "persona_telefono, persona_activa\n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' ORDER BY persona_primer_apellido ASC;";
        return consultarParaTabla(sql);
    }

    public ArrayList<PersonaMD> buscar(String aguja) {
        String sql = "SELECT id_persona, persona_identificacion,"
                + " persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre, "
                + "persona_fecha_nacimiento, persona_correo, persona_celular, "
                + "persona_telefono, persona_activa \n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' AND (\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%" + aguja + "%' OR\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%');";
        return consultarParaTabla(sql);
    }

    //Consultamos unicamente a las personas que son alumnos  
    public ArrayList<PersonaMD> cargarAlumnos() {
        String sql = "SELECT \"Personas\".id_persona, \n"
                + "persona_identificacion, persona_primer_apellido, \n"
                + "persona_segundo_apellido, persona_primer_nombre, \n"
                + "persona_segundo_nombre, persona_fecha_nacimiento, persona_correo, persona_celular, "
                + "persona_telefono, persona_activa \n"
                + "	FROM public.\"Personas\", public.\"Alumnos\" \n"
                + "	WHERE \"Personas\".id_persona = \"Alumnos\".id_persona AND persona_activa = true;";
        return consultarParaTabla(sql);
    }

    public ArrayList<PersonaMD> cargarDocentes() {
        String sql = "SELECT \"Personas\".id_persona, \n"
                + "persona_identificacion, persona_primer_apellido, \n"
                + "persona_segundo_apellido, persona_primer_nombre, \n"
                + "persona_segundo_nombre, persona_fecha_nacimiento persona_correo, persona_celular, "
                + "persona_telefono, persona_activa \n"
                + "	FROM public.\"Personas\", public.\"Docentes\" \n"
                + "	WHERE \"Personas\".id_persona = \"Docentes\".id_persona AND persona_activa = true;";
        return consultarParaTabla(sql);
    }

    //Buscar Persona con aguja
    /**
     *
     * @param idPersona
     * @return
     */
    public PersonaMD buscarPersona(int idPersona) {
        String sql = "SELECT id_persona, id_lugar_natal, "
                + "id_lugar_residencia, persona_foto, persona_identificacion,"
                + " persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre, persona_genero,"
                + " persona_sexo, persona_estado_civil, persona_etnia, "
                + "persona_idioma_raiz, persona_tipo_sangre, persona_telefono,"
                + " persona_celular, persona_correo, persona_fecha_registro,"
                + " persona_discapacidad, persona_tipo_discapacidad,"
                + " persona_porcenta_discapacidad, persona_carnet_conadis,"
                + " persona_calle_principal, persona_numero_casa,"
                + " persona_calle_secundaria, persona_referencia, "
                + "persona_sector, persona_idioma, persona_tipo_residencia, "
                + "persona_fecha_nacimiento, persona_activa, persona_categoria_migratoria \n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' AND"
                + " id_persona = " + idPersona + ";";

        return consultarPor(sql);
    }

    /**
     *
     * @param identificacion
     * @return
     */
    public PersonaMD buscarPersona(String identificacion) {
        String sql = "SELECT id_persona, id_lugar_natal, "
                + "id_lugar_residencia, persona_foto, persona_identificacion,"
                + " persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre, persona_genero,"
                + " persona_sexo, persona_estado_civil, persona_etnia, "
                + "persona_idioma_raiz, persona_tipo_sangre, persona_telefono,"
                + " persona_celular, persona_correo, persona_fecha_registro,"
                + " persona_discapacidad, persona_tipo_discapacidad,"
                + " persona_porcenta_discapacidad, persona_carnet_conadis,"
                + " persona_calle_principal, persona_numero_casa,"
                + " persona_calle_secundaria, persona_referencia, "
                + "persona_sector, persona_idioma, persona_tipo_residencia, "
                + "persona_fecha_nacimiento, persona_activa, persona_categoria_migratoria \n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' AND"
                + " persona_identificacion ='" + identificacion + "'";

        return consultarPor(sql);
    }

    /**
     *
     * @param identificacion
     * @return
     */
    public PersonaMD buscarPersonaNoActiva(String identificacion) {
        String sql = "SELECT id_persona, id_lugar_natal, "
                + "id_lugar_residencia, persona_foto, persona_identificacion,"
                + " persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre, persona_genero,"
                + " persona_sexo, persona_estado_civil, persona_etnia, "
                + "persona_idioma_raiz, persona_tipo_sangre, persona_telefono,"
                + " persona_celular, persona_correo, persona_fecha_registro,"
                + " persona_discapacidad, persona_tipo_discapacidad,"
                + " persona_porcenta_discapacidad, persona_carnet_conadis,"
                + " persona_calle_principal, persona_numero_casa,"
                + " persona_calle_secundaria, persona_referencia, "
                + "persona_sector, persona_idioma, persona_tipo_residencia, "
                + "persona_fecha_nacimiento, persona_activa, persona_categoria_migratoria \n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'false' AND"
                + " persona_identificacion ='" + identificacion + "'";

        return consultarPor(sql);
    }

    public PersonaMD existePersona(String identificacion) {
        String sql = "SELECT id_persona, id_lugar_natal, "
                + "id_lugar_residencia, persona_foto, persona_identificacion,"
                + " persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre, persona_genero,"
                + " persona_sexo, persona_estado_civil, persona_etnia, "
                + "persona_idioma_raiz, persona_tipo_sangre, persona_telefono,"
                + " persona_celular, persona_correo, persona_fecha_registro,"
                + " persona_discapacidad, persona_tipo_discapacidad,"
                + " persona_porcenta_discapacidad, persona_carnet_conadis,"
                + " persona_calle_principal, persona_numero_casa,"
                + " persona_calle_secundaria, persona_referencia, "
                + "persona_sector, persona_idioma, persona_tipo_residencia, "
                + "persona_fecha_nacimiento, persona_activa, persona_categoria_migratoria \n"
                + "FROM public.\"Personas\" WHERE \n"
                + " persona_identificacion ='" + identificacion + "'";

        return consultarPor(sql);
    }

    //Buscar Persona con aguja
    public PersonaMD buscarPersonaParaReferencia(int idPersona) {
        String sql = "SELECT id_persona, persona_identificacion,"
                + " persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre, persona_correo,"
                + " persona_celular "
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' AND"
                + " id_persona = " + idPersona + ";";

        return consultarParaReferencia(sql);
    }

    public PersonaMD buscarPersonaParaReferencia(String identificacion) {
        String sql = "SELECT id_persona, id_lugar_natal, "
                + "id_lugar_residencia, persona_foto, persona_identificacion,"
                + " persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre, persona_correo,"
                + " persona_celular "
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' AND"
                + " persona_identificacion ='" + identificacion + "'";

        return consultarParaReferencia(sql);
    }

    private PersonaMD consultarParaReferencia(String sql) {
        PersonaMD p = new PersonaMD();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    p.setIdPersona(rs.getInt("id_persona"));
                    p.setIdentificacion(rs.getString("persona_identificacion"));
                    p.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    p.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                    p.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    p.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                    p.setCorreo(rs.getString("persona_correo"));
                    p.setCelular(rs.getString("persona_celular"));
                }
                rs.close();
                ps.getConnection().close();
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar personas");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ArrayList<PersonaMD> consultarParaTabla(String sql) {
        ArrayList<PersonaMD> pers = new ArrayList();
        PersonaMD p;
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    p = new PersonaMD();
                    p.setIdPersona(rs.getInt("id_persona"));
                    p.setIdentificacion(rs.getString("persona_identificacion"));
                    p.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    p.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                    p.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    p.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                    p.setFechaNacimiento(rs.getDate("persona_fecha_nacimiento").toLocalDate());
                    p.setCelular(rs.getString("persona_celular"));
                    p.setTelefono(rs.getString("persona_telefono"));
                    p.setCorreo(rs.getString("persona_correo"));
                    pers.add(p);
                }
                rs.close();
                ps.getConnection().close();
                return pers;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar personas");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Este metodo unicamente nos devolvera una persona dependiendo de la setencia sql que se envie
    private PersonaMD consultarPor(String sql) {
        PersonaMD p = null;
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    p = obtenerPersona(rs);
                }
                rs.close();
                ps.getConnection().close();
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar una persona");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param rs
     * @return
     */
    private PersonaMD obtenerPersona(ResultSet rs) {
        PersonaMD persona = new PersonaMD();
        try {
            persona.setIdPersona(rs.getInt("id_persona"));
            //Aqui igual se crear una clase lugar 

            //Buscamos el lugar que corresponde a cada cosa
            persona.setLugarNatal(lugar.buscar(rs.getInt("id_lugar_natal")));
            persona.setLugarResidencia(lugar.buscar(rs.getInt("id_lugar_residencia")));

            //Consultamos foto y la cargamos si tiene foto
            if (rs.wasNull()) {
                System.out.println("No tiene foto");
            } else {
                is = rs.getBinaryStream("persona_foto");
                //Pasamos la imagen
                if (is != null) {
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        ImageIcon foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        persona.setFoto(img);
                    } catch (IOException ex) {
                        System.out.println("Error al pasar la foto: " + ex.getMessage());
                    }
                }
            }

            persona.setIdentificacion(rs.getString("persona_identificacion"));
            persona.setPrimerApellido(rs.getString("persona_primer_apellido"));
            persona.setSegundoApellido(rs.getString("persona_segundo_apellido"));
            persona.setPrimerNombre(rs.getString("persona_primer_nombre"));
            persona.setSegundoNombre(rs.getString("persona_segundo_nombre"));

            if (rs.wasNull()) {
                persona.setGenero(null);
            } else {
                persona.setGenero(rs.getString("persona_genero"));
            }

            //Aqui solo cojemos la letra de la posicion 0 porque solo recibe un char
            persona.setSexo(rs.getString("persona_sexo").charAt(0));
            persona.setEstadoCivil(rs.getString("persona_estado_civil"));
            persona.setEtnia(rs.getString("persona_etnia"));
            persona.setIdiomaRaiz(rs.getString("persona_idioma_raiz"));
            persona.setTipoSangre(rs.getString("persona_tipo_sangre"));

            if (rs.wasNull()) {
                persona.setTelefono(null);
            } else {
                persona.setTelefono(rs.getString("persona_telefono"));
            }

            if (rs.wasNull()) {
                persona.setCelular(null);
            } else {
                persona.setCelular(rs.getString("persona_celular"));
            }

            persona.setCorreo(rs.getString("persona_correo"));
            persona.setFechaRegistro(rs.getDate("persona_fecha_registro").toLocalDate());
            persona.setDiscapacidad(rs.getBoolean("persona_discapacidad"));

            if (rs.wasNull()) {
                persona.setTipoDiscapacidad(rs.getString(null));
            } else {
                persona.setTipoDiscapacidad(rs.getString("persona_tipo_discapacidad"));
            }

            if (rs.wasNull()) {
                persona.setPorcentajeDiscapacidad(0);
            } else {
                persona.setPorcentajeDiscapacidad(rs.getInt("persona_porcenta_discapacidad"));
            }

            if (rs.wasNull()) {
                persona.setCarnetConadis(null);
            } else {
                persona.setCarnetConadis(rs.getString("persona_carnet_conadis"));
            }
            /*
            if (rs.wasNull()) {
                persona.setCallePrincipal(null);
            } else {
                persona.setCallePrincipal(rs.getString("persona_calle_principal"));
            }*/
            persona.setCallePrincipal(rs.getString("persona_calle_principal"));

            if (rs.wasNull()) {
                persona.setNumeroCasa(null);
            } else {
                persona.setNumeroCasa(rs.getString("persona_numero_casa"));
            }

            if (rs.wasNull()) {
                persona.setCalleSecundaria(null);
            } else {
                persona.setCalleSecundaria(rs.getString("persona_calle_secundaria"));
            }

            if (rs.wasNull()) {
                persona.setReferencia(null);
            } else {
                persona.setReferencia(rs.getString("persona_referencia"));
            }

            if (rs.wasNull()) {
                persona.setSector(null);
            } else {
                persona.setSector(rs.getString("persona_sector"));
            }

            persona.setIdioma(rs.getString("persona_idioma"));

            if (rs.wasNull()) {
                persona.setTipoResidencia(null);
            } else {
                persona.setTipoResidencia(rs.getString("persona_tipo_residencia"));
            }

            //Solo se usa la funcion .toLocalDate cuando nos regresa un tipo date 
            persona.setFechaNacimiento(rs.getDate("persona_fecha_nacimiento").toLocalDate());
            persona.setPersonaActiva(rs.getBoolean("persona_activa"));

            if (rs.wasNull()) {
                persona.setCategoriaMigratoria(null);
            } else {
                persona.setCategoriaMigratoria(rs.getString("persona_categoria_migratoria"));
            }

            return persona;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener persona");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<PersonaMD> filtrarEliminados() {
        String nsql = "SELECT id_persona, persona_identificacion, persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido, persona_fecha_nacimiento FROM public.\"Personas\"\n"
                + "WHERE persona_activa = 'false' ORDER BY persona_primer_apellido ASC;";
        return consultarParaTabla(nsql);
    }

    public static Map<String, PersonaMD> selectAll() {
        String SELECT = "SELECT\n"
                + "\"public\".\"Personas\".id_persona,\n"
                + "\"public\".\"Personas\".persona_identificacion,\n"
                + "\"public\".\"Personas\".persona_primer_apellido,\n"
                + "\"public\".\"Personas\".persona_segundo_apellido,\n"
                + "\"public\".\"Personas\".persona_primer_nombre,\n"
                + "\"public\".\"Personas\".persona_segundo_nombre\n"
                + "FROM\n"
                + "\"public\".\"Personas\"";
        Map<String, PersonaMD> map = new HashMap<>();

        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);
        try {
            while (rst.next()) {
                PersonaMD persona = new PersonaMD();
                persona.setIdPersona(rst.getInt("id_persona"));

                String identificacion = rst.getString("persona_identificacion");
                String primerApellido = rst.getString("persona_primer_apellido");
                String segundoApellido = rst.getString("persona_segundo_apellido");
                String primerNombre = rst.getString("persona_primer_nombre");
                String segundoNombre = rst.getString("persona_segundo_nombre");

                persona.setSegundoNombre(segundoNombre);
                persona.setIdentificacion(identificacion);
                persona.setPrimerApellido(primerApellido);
                persona.setSegundoApellido(segundoApellido);
                persona.setPrimerNombre(primerNombre);

                String key = identificacion + " " + primerApellido + " " + segundoApellido + " " + primerNombre + " " + segundoNombre;

                map.put(key, persona);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.close(conn);
        }

        return map;
    }

}
