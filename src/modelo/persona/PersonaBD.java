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
import modelo.ConnDBPool;
import modelo.lugar.LugarBD;
import modelo.lugar.LugarMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author Lina
 */
public class PersonaBD extends CONBD {

    //Esto se usara para cargar las fotos 
    InputStream is;
    private final ConnDBPool pool;
    private Connection conn;
    private ResultSet rst;
    private static PersonaBD PBD;

    // Para consultar lugar  
    private LugarBD LBD = LugarBD.single();

    public static PersonaBD single() {
        if (PBD == null) {
            PBD = new PersonaBD();
        }
        return PBD;
    }

    {
        pool = new ConnDBPool();
    }

    /**
     * Este método guarda a la Persona en la Base de Datos con todos estos
     * atributos
     *
     * @param p
     * @return
     */
    public boolean insertarPersona(PersonaMD p) {
        LugarMD lg = new LugarMD();
        String nsql = "INSERT INTO public.\"Personas\"(\n"
                + "id_lugar_natal, "
                + "id_lugar_residencia, "
                + "persona_identificacion, "
                + "persona_primer_apellido, "
                + "persona_segundo_apellido, "
                + "persona_primer_nombre, "
                + "persona_segundo_nombre, "
                + "persona_genero, "
                + "persona_sexo, "
                + "persona_estado_civil, "
                + "persona_etnia, "
                + "persona_idioma_raiz, "
                + "persona_tipo_sangre, "
                + "persona_telefono, "
                + "persona_celular, "
                + "persona_correo, "
                + "persona_fecha_registro, "
                + "persona_discapacidad, "
                + "persona_tipo_discapacidad, "
                + "persona_porcenta_discapacidad, "
                + "persona_carnet_conadis, "
                + "persona_calle_principal, "
                + "persona_numero_casa, "
                + "persona_calle_secundaria, "
                + "persona_referencia, "
                + "persona_sector, "
                + "persona_idioma, "
                + "persona_tipo_residencia, "
                + "persona_fecha_nacimiento, "
                + "persona_categoria_migratoria ) \n"
                + "VALUES ("
                + p.getLugarNatal().getId() + ", "
                + p.getLugarResidencia().getId() + " , "
                + "'" + p.getIdentificacion() + "', "
                + "'" + p.getPrimerApellido() + "', "
                + "'" + p.getSegundoApellido() + "', "
                + "'" + p.getPrimerNombre() + "', "
                + "'" + p.getSegundoNombre() + "', "
                + "'" + p.getGenero() + "', "
                + "'" + p.getSexo() + "', "
                + "'" + p.getEstadoCivil() + "', "
                + "'" + p.getEtnia() + "', "
                + "'" + p.getIdiomaRaiz() + "', "
                + "'" + p.getTipoSangre() + "', "
                + "'" + p.getTelefono() + "', "
                + "'" + p.getCelular() + "', "
                + "'" + p.getCorreo() + "', "
                + "'" + p.getFechaRegistro() + "', "
                + "'" + p.isDiscapacidad() + "', "
                + "'" + p.getTipoDiscapacidad() + "', "
                + "'" + p.getPorcentajeDiscapacidad() + "', "
                + "'" + p.getCarnetConadis() + "', "
                + "'" + p.getCallePrincipal() + "', "
                + "'" + p.getNumeroCasa() + "', "
                + "'" + p.getCalleSecundaria() + "', "
                + "'" + p.getReferencia() + "', "
                + "'" + p.getSector() + "', "
                + "'" + p.getIdioma() + "', "
                + "'" + p.getTipoResidencia() + "', "
                + "'" + p.getFechaNacimiento() + "', "
                + "'" + p.getCategoriaMigratoria() + "');";

        if (p.isDiscapacidad()) {
            nsql = "INSERT INTO public.\"Personas\"(\n"
                    + "id_lugar_natal, "
                    + "id_lugar_residencia, "
                    + "persona_identificacion, "
                    + "persona_primer_apellido, "
                    + "persona_segundo_apellido, "
                    + "persona_primer_nombre, "
                    + "persona_segundo_nombre, "
                    + "persona_genero, "
                    + "persona_sexo, "
                    + "persona_estado_civil, "
                    + "persona_etnia, "
                    + "persona_idioma_raiz, "
                    + "persona_tipo_sangre, "
                    + "persona_telefono, "
                    + "persona_celular, "
                    + "persona_correo, "
                    + "persona_fecha_registro, "
                    + "persona_discapacidad, "
                    + "persona_tipo_discapacidad, "
                    + "persona_porcenta_discapacidad, "
                    + "persona_carnet_conadis, "
                    + "persona_calle_principal, "
                    + "persona_numero_casa, "
                    + "persona_calle_secundaria, "
                    + "persona_referencia, "
                    + "persona_sector, "
                    + "persona_idioma, "
                    + "persona_tipo_residencia, "
                    + "persona_fecha_nacimiento, "
                    + "persona_categoria_migratoria) \n"
                    + "VALUES ("
                    + p.getLugarNatal().getId() + ", "
                    + p.getLugarResidencia().getId() + ", "
                    + "'" + p.getIdentificacion() + "', "
                    + "'" + p.getPrimerApellido() + "', "
                    + "'" + p.getSegundoApellido() + "', "
                    + "'" + p.getPrimerNombre() + "', "
                    + "'" + p.getSegundoNombre() + "', "
                    + "'" + p.getGenero() + "', "
                    + "'" + p.getSexo() + "', "
                    + "'" + p.getEstadoCivil() + "', "
                    + "'" + p.getEtnia() + "', "
                    + "'" + p.getIdiomaRaiz() + "', "
                    + "'" + p.getTipoSangre() + "', "
                    + "'" + p.getTelefono() + "', "
                    + "'" + p.getCelular() + "', "
                    + "'" + p.getCorreo() + "', "
                    + "'" + p.getFechaRegistro() + "', "
                    + "'" + p.isDiscapacidad() + "', "
                    + "'" + p.getTipoDiscapacidad() + "', "
                    + "'" + p.getPorcentajeDiscapacidad() + "', "
                    + "'" + p.getCarnetConadis() + "', "
                    + "'" + p.getCallePrincipal() + "', "
                    + "'" + p.getNumeroCasa() + "', "
                    + "'" + p.getCalleSecundaria() + "', "
                    + "'" + p.getReferencia() + "', "
                    + "'" + p.getSector() + "', "
                    + "'" + p.getIdioma() + "', "
                    + "'" + p.getTipoResidencia() + "', "
                    + "'" + p.getFechaNacimiento() + "', "
                    + "'" + p.getCategoriaMigratoria() + "');";
        }
        return CON.executeNoSQL(nsql);
    }

    // Revisar
    public void insertarIdentificacion(String identificacion) {
        String nsql = "INSERT INTO public. \"Personas\"(persona_identificacion) "
                + "VALUES ('" + identificacion + "');";
        CON.executeNoSQL(nsql);
    }

    //persona_categoria_migratoria
    public boolean insertarPersonaConFoto(PersonaMD p) {
        //Aqui id_persona ya no va porque es autoincrementable
        String nsql = "INSERT INTO public.\"Personas\"(\n"
                + "id_lugar_natal, "
                + "id_lugar_residencia, "
                + "persona_foto,"
                + "persona_identificacion, "
                + "persona_primer_apellido, "
                + "persona_segundo_apellido, "
                + "persona_primer_nombre, "
                + "persona_segundo_nombre, "
                + "persona_genero, "
                + "persona_sexo, "
                + "persona_estado_civil, "
                + "persona_etnia, "
                + "persona_idioma_raiz, "
                + "persona_tipo_sangre, "
                + "persona_telefono, "
                + "persona_celular, "
                + "persona_correo, "
                + "persona_fecha_registro, "
                + "persona_calle_principal, "
                + "persona_numero_casa, "
                + "persona_calle_secundaria, "
                + "persona_referencia, "
                + "persona_sector, "
                + "persona_idioma, "
                + "persona_tipo_residencia, "
                + "persona_fecha_nacimiento, "
                + "persona_categoria_migratoria) \n"
                + "VALUES ("
                + p.getLugarNatal().getId() + ", "
                + p.getLugarResidencia().getId() + ", "
                + "? , "
                + "'" + p.getIdentificacion() + "', "
                + "'" + p.getPrimerApellido() + "', "
                + "'" + p.getSegundoApellido() + "', "
                + "'" + p.getPrimerNombre() + "', "
                + "'" + p.getSegundoNombre() + "', "
                + "'" + p.getGenero() + "', "
                + "'" + p.getSexo() + "', "
                + "'" + p.getEstadoCivil() + "', "
                + "'" + p.getEtnia() + "', "
                + "'" + p.getIdiomaRaiz() + "', "
                + "'" + p.getTipoSangre() + "', "
                + "'" + p.getTelefono() + "', "
                + "'" + p.getCelular() + "', "
                + "'" + p.getCorreo() + "', "
                + "'" + p.getFechaRegistro() + "', "
                + "'" + p.getCallePrincipal() + "', "
                + "'" + p.getNumeroCasa() + "', "
                + "'" + p.getCalleSecundaria() + "', "
                + "'" + p.getReferencia() + "', "
                + "'" + p.getSector() + "', "
                + "'" + p.getIdioma() + "', "
                + "'" + p.getTipoResidencia() + "', "
                + "'" + p.getFechaNacimiento() + "', "
                + "'" + p.getCategoriaMigratoria() + "');";

        if (p.isDiscapacidad()) {
            nsql = "INSERT INTO public.\"Personas\"(\n"
                    + "id_lugar_natal, "
                    + "id_lugar_residencia, "
                    + "persona_foto, "
                    + "persona_identificacion, "
                    + "persona_primer_apellido, "
                    + "persona_segundo_apellido, "
                    + "persona_primer_nombre, "
                    + "persona_segundo_nombre, "
                    + "persona_genero, "
                    + "persona_sexo, "
                    + "persona_estado_civil, "
                    + "persona_etnia, "
                    + "persona_idioma_raiz, "
                    + "persona_tipo_sangre, "
                    + "persona_telefono, "
                    + "persona_celular, "
                    + "persona_correo, "
                    + "persona_fecha_registro, "
                    + "persona_discapacidad, "
                    + "persona_tipo_discapacidad, "
                    + "persona_porcenta_discapacidad, "
                    + "persona_carnet_conadis, "
                    + "persona_calle_principal, "
                    + "persona_numero_casa, "
                    + "persona_calle_secundaria, "
                    + "persona_referencia, "
                    + "persona_sector, persona_idioma, "
                    + "persona_tipo_residencia, "
                    + "persona_fecha_nacimiento, "
                    + "persona_categoria_migratoria) "
                    + "VALUES ("
                    + p.getLugarNatal().getId() + ", "
                    + p.getLugarResidencia().getId() + ", "
                    + "? , "
                    + "'" + p.getIdentificacion() + "', "
                    + "'" + p.getPrimerApellido() + "', "
                    + "'" + p.getSegundoApellido() + "', "
                    + "'" + p.getPrimerNombre() + "', "
                    + "'" + p.getSegundoNombre() + "', "
                    + "'" + p.getGenero() + "', "
                    + "'" + p.getSexo() + "', "
                    + "'" + p.getEstadoCivil() + "', "
                    + "'" + p.getEtnia() + "', "
                    + "'" + p.getIdiomaRaiz() + "', "
                    + "'" + p.getTipoSangre() + "', "
                    + "'" + p.getTelefono() + "', "
                    + "'" + p.getCelular() + "', "
                    + "'" + p.getCorreo() + "', "
                    + "'" + p.getFechaRegistro() + "', "
                    + "'" + p.isDiscapacidad() + "', "
                    + "'" + p.getTipoDiscapacidad() + "', "
                    + "'" + p.getPorcentajeDiscapacidad() + "', "
                    + "'" + p.getCarnetConadis() + "', "
                    + "'" + p.getCallePrincipal() + "', "
                    + "'" + p.getNumeroCasa() + "', "
                    + "'" + p.getCalleSecundaria() + "', "
                    + "'" + p.getReferencia() + "', "
                    + "'" + p.getSector() + "', "
                    + "'" + p.getIdioma() + "', "
                    + "'" + p.getTipoResidencia() + "', "
                    + "'" + p.getFechaNacimiento() + "', "
                    + "'" + p.getCategoriaMigratoria() + "');";
        }

        PreparedStatement ps = CON.getPSPOOL(nsql);
        try {
            ps.setBinaryStream(1, p.getFile(), p.getLogBytes());
            return CON.noSQLPOOL(ps);
        } catch (SQLException e) {
            M.errorMsg("No se pudo guardar con foto. " + e.getMessage());
            return false;
        }
    }

    /**
     * Este método edita a una Persona que no tiene foto de la Base de Datos con
     * todos estos atributos
     *
     * @param p
     * @return Retorna un boolean según el resultado de la Edición
     */
    public boolean editarPersona(PersonaMD p) {
        String sql;
        if (p.isDiscapacidad()) {
            sql = "UPDATE public.\"Personas\" SET\n"
                    + " id_lugar_natal = " + p.getLugarNatal().getId() + ", "
                    + "id_lugar_residencia = " + p.getLugarResidencia().getId() + ", "
                    + "persona_identificacion = '" + p.getIdentificacion() + "', "
                    + "persona_primer_apellido = '" + p.getPrimerApellido() + "', "
                    + "persona_segundo_apellido = '" + p.getSegundoApellido() + "', "
                    + "persona_primer_nombre = '" + p.getPrimerNombre() + "', "
                    + "persona_segundo_nombre = '" + p.getSegundoNombre() + "', "
                    + "persona_genero = '" + p.getGenero() + "', "
                    + "persona_sexo = '" + p.getSexo() + "', "
                    + "persona_estado_civil = '" + p.getEstadoCivil() + "', "
                    + "persona_etnia = '" + p.getEtnia() + "', "
                    + "persona_idioma_raiz = '" + p.getIdiomaRaiz() + "', "
                    + "persona_tipo_sangre = '" + p.getTipoSangre() + "', "
                    + "persona_telefono = '" + p.getTelefono() + "', "
                    + "persona_celular = '" + p.getCelular() + "', "
                    + "persona_correo = '" + p.getCorreo() + "', "
                    + "persona_fecha_registro = '" + p.getFechaRegistro() + "', "
                    + "persona_discapacidad = '" + p.isDiscapacidad() + "', "
                    + "persona_tipo_discapacidad = '" + p.getTipoDiscapacidad() + "', "
                    + "persona_porcenta_discapacidad = '" + p.getPorcentajeDiscapacidad() + "', "
                    + "persona_carnet_conadis = '" + p.getCarnetConadis() + "', "
                    + "persona_calle_principal = '" + p.getCallePrincipal() + "', "
                    + "persona_numero_casa = '" + p.getNumeroCasa() + "', "
                    + "persona_calle_secundaria = '" + p.getCalleSecundaria() + "', "
                    + "persona_referencia = '" + p.getReferencia() + "', "
                    + "persona_sector = '" + p.getSector() + "', "
                    + "persona_idioma = '" + p.getIdioma() + "', "
                    + "persona_tipo_residencia = '" + p.getTipoResidencia() + "', "
                    + "persona_fecha_nacimiento = '" + p.getFechaNacimiento() + "', "
                    + "persona_categoria_migratoria = '" + p.getCategoriaMigratoria() + "' "
                    + " WHERE id_persona = " + p.getIdPersona() + ";";
        } else {
            sql = "UPDATE public.\"Personas\" SET\n"
                    + " id_lugar_natal = " + p.getLugarNatal().getId() + ", "
                    + "id_lugar_residencia = " + p.getLugarResidencia().getId() + ", "
                    + "persona_identificacion = '" + p.getIdentificacion() + "', "
                    + "persona_primer_apellido = '" + p.getPrimerApellido() + "', "
                    + "persona_segundo_apellido = '" + p.getSegundoApellido() + "', "
                    + "persona_primer_nombre = '" + p.getPrimerNombre() + "', "
                    + "persona_segundo_nombre = '" + p.getSegundoNombre() + "', "
                    + "persona_genero = '" + p.getGenero() + "', "
                    + "persona_sexo = '" + p.getSexo() + "', "
                    + "persona_estado_civil = '" + p.getEstadoCivil() + "', "
                    + "persona_etnia = '" + p.getEtnia() + "', "
                    + "persona_idioma_raiz = '" + p.getIdiomaRaiz() + "', "
                    + "persona_tipo_sangre = '" + p.getTipoSangre() + "', "
                    + "persona_telefono = '" + p.getTelefono() + "', "
                    + "persona_celular = '" + p.getCelular() + "', "
                    + "persona_correo = '" + p.getCorreo() + "', "
                    + "persona_fecha_registro = '" + p.getFechaRegistro() + "', "
                    + "persona_discapacidad = false" + ", "
                    + "persona_tipo_discapacidad = null , "
                    + "persona_porcenta_discapacidad = 0 " + ", "
                    + "persona_carnet_conadis = null, "
                    + "persona_calle_principal = '" + p.getCallePrincipal() + "', "
                    + "persona_numero_casa = '" + p.getNumeroCasa() + "', "
                    + "persona_calle_secundaria = '" + p.getCalleSecundaria() + "', "
                    + "persona_referencia = '" + p.getReferencia() + "', "
                    + "persona_sector = '" + p.getSector() + "', "
                    + "persona_idioma = '" + p.getIdioma() + "', "
                    + "persona_tipo_residencia = '" + p.getTipoResidencia() + "', "
                    + "persona_fecha_nacimiento = '" + p.getFechaNacimiento() + "', "
                    + "persona_categoria_migratoria = '" + p.getCategoriaMigratoria() + "' "
                    + " WHERE id_persona = " + p.getIdPersona() + ";";
        }
        return CON.executeNoSQL(sql);
    }

    public boolean editarIdentificacion(String identificacion, int aguja) {
        String sql = "UPDATE public. \"Personas\" "
                + "SET persona_identificacion ='" + identificacion + "'\n"
                + "WHERE id_persona= " + aguja + ";";
        return CON.executeNoSQL(sql);
    }

    /**
     * Este método edita a una Persona que si tiene foto de la Base de Datos con
     * todos estos atributos
     *
     * @param aguja Se tiene que pasar un int como la Id de persona
     * @return
     */
    public boolean editarPersonaConFoto(PersonaMD p) {
        String nsql;

        if (p.isDiscapacidad()) {
            nsql = "UPDATE public.\"Personas\" SET "
                    + "id_lugar_natal = " + p.getLugarNatal().getId() + ", "
                    + "id_lugar_residencia = " + p.getLugarResidencia().getId() + ", "
                    + "persona_foto = ? " + ", "
                    + "persona_identificacion = '" + p.getIdentificacion() + "', "
                    + "persona_primer_apellido = '" + p.getPrimerApellido() + "', "
                    + "persona_segundo_apellido = '" + p.getSegundoApellido() + "', "
                    + "persona_primer_nombre = '" + p.getPrimerNombre() + "', "
                    + "persona_segundo_nombre = '" + p.getSegundoNombre() + "', "
                    + "persona_genero = '" + p.getGenero() + "', "
                    + "persona_sexo = '" + p.getSexo() + "', "
                    + "persona_estado_civil = '" + p.getEstadoCivil() + "', "
                    + "persona_etnia = '" + p.getEtnia() + "', "
                    + "persona_idioma_raiz = '" + p.getIdiomaRaiz() + "', "
                    + "persona_tipo_sangre = '" + p.getTipoSangre() + "', "
                    + "persona_telefono = '" + p.getTelefono() + "', "
                    + "persona_celular = '" + p.getCelular() + "', "
                    + "persona_correo = '" + p.getCorreo() + "', "
                    + "persona_fecha_registro = '" + p.getFechaRegistro() + "', "
                    + "persona_discapacidad = '" + p.isDiscapacidad() + "', "
                    + "persona_tipo_discapacidad = '" + p.getTipoDiscapacidad() + "', "
                    + "persona_porcenta_discapacidad = '" + p.getPorcentajeDiscapacidad() + "', "
                    + "persona_carnet_conadis = '" + p.getCarnetConadis() + "', "
                    + "persona_calle_principal = '" + p.getCallePrincipal() + "', "
                    + "persona_numero_casa = '" + p.getNumeroCasa() + "', "
                    + "persona_calle_secundaria = '" + p.getCalleSecundaria() + "', "
                    + "persona_referencia = '" + p.getReferencia() + "', "
                    + "persona_sector = '" + p.getSector() + "', "
                    + "persona_idioma = '" + p.getIdioma() + "', "
                    + "persona_tipo_residencia = '" + p.getTipoResidencia() + "', "
                    + "persona_fecha_nacimiento = '" + p.getFechaNacimiento() + "', "
                    + "persona_categoria_migratoria = '" + p.getCategoriaMigratoria() + "' "
                    + "WHERE id_persona = " + p.getIdPersona() + ";";
        } else {
            nsql = "UPDATE public.\"Personas\" SET "
                    + "id_lugar_natal = " + p.getLugarNatal().getId() + ", "
                    + "id_lugar_residencia = " + p.getLugarResidencia().getId() + ", "
                    + "persona_foto = ? " + ", "
                    + "persona_identificacion = '" + p.getIdentificacion() + "', "
                    + "persona_primer_apellido = '" + p.getPrimerApellido() + "', "
                    + "persona_segundo_apellido = '" + p.getSegundoApellido() + "', "
                    + "persona_primer_nombre = '" + p.getPrimerNombre() + "', "
                    + "persona_segundo_nombre = '" + p.getSegundoNombre() + "', "
                    + "persona_genero = '" + p.getGenero() + "', "
                    + "persona_sexo = '" + p.getSexo() + "', "
                    + "persona_estado_civil = '" + p.getEstadoCivil() + "', "
                    + "persona_etnia = '" + p.getEtnia() + "', "
                    + "persona_idioma_raiz = '" + p.getIdiomaRaiz() + "', "
                    + "persona_tipo_sangre = '" + p.getTipoSangre() + "', "
                    + "persona_telefono = '" + p.getTelefono() + "', "
                    + "persona_celular = '" + p.getCelular() + "', "
                    + "persona_correo = '" + p.getCorreo() + "', "
                    + "persona_fecha_registro = '" + p.getFechaRegistro() + "', "
                    + "persona_discapacidad = false" + ", "
                    + "persona_tipo_discapacidad = null , "
                    + "persona_porcenta_discapacidad = 0, "
                    + "persona_carnet_conadis = null, "
                    + "persona_calle_principal = '" + p.getCallePrincipal() + "', "
                    + "persona_numero_casa = '" + p.getNumeroCasa() + "', "
                    + "persona_calle_secundaria = '" + p.getCalleSecundaria() + "', "
                    + "persona_referencia = '" + p.getReferencia() + "', "
                    + "persona_sector = '" + p.getSector() + "', "
                    + "persona_idioma = '" + p.getIdioma() + "', "
                    + "persona_tipo_residencia = '" + p.getTipoResidencia() + "', "
                    + "persona_fecha_nacimiento = '" + p.getFechaNacimiento() + "', "
                    + "persona_categoria_migratoria = '" + p.getCategoriaMigratoria() + "' "
                    + "WHERE id_persona = " + p.getIdPersona() + ";";
        }
        PreparedStatement ps = CON.getPSPOOL(nsql);
        try {
            ps.setBinaryStream(1, p.getFile(), p.getLogBytes());
            return CON.executeNoSQL(nsql);
        } catch (SQLException e) {
            M.errorMsg("No se pudo editar persona con foto" + e.getMessage());
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
        return CON.executeNoSQL(sql);
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
        return CON.executeNoSQL(sql);
    }

    // Utilizamos la sentencia update para modificar el estado de una persona
    // y no eliminarla por completo con parametro por id_persona
    public boolean eliminarPersonaId(int idpersona) {
        String sql = "UPDATE public.\"Personas\"\n"
                + "SET persona_activa='false'"
                + "WHERE id_persona = '" + idpersona + "';";
        return CON.executeNoSQL(sql);
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
                + "persona_segundo_nombre, persona_fecha_nacimiento, persona_correo, persona_celular, "
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

    private ArrayList<PersonaMD> consultarParaTabla(String sql) {
        ArrayList<PersonaMD> pers = new ArrayList();
        PersonaMD p;
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                p = new PersonaMD();
                p.setIdPersona(rs.getInt("id_persona"));
                p.setIdentificacion(rs.getString("persona_identificacion"));
                p.setPrimerApellido(rs.getString("persona_primer_apellido"));
                p.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                p.setPrimerNombre(rs.getString("persona_primer_nombre"));
                p.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                if (rs.getDate("persona_fecha_nacimiento") == null) {
                    p.setFechaNacimiento(null);
                } else {
                    p.setFechaNacimiento(rs.getDate("persona_fecha_nacimiento").toLocalDate());
                }
                p.setCelular(rs.getString("persona_celular"));
                p.setTelefono(rs.getString("persona_telefono"));
                p.setCorreo(rs.getString("persona_correo"));
                pers.add(p);
            }

        } catch (SQLException e) {
            M.errorMsg("NO consultamos personas. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return pers;
    }

    //Este metodo unicamente nos devolvera una persona dependiendo de la setencia sql que se envie
    private PersonaMD consultarPor(String sql) {
        PersonaMD p = null;
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                p = obtenerPersona(rs);
            }
            return p;
        } catch (SQLException e) {
            M.errorMsg("No conusltamos persona. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
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
            //Buscamos el lugar que corresponde a cada cosa
            persona.setLugarNatal(LBD.buscar(rs.getInt("id_lugar_natal")));
            persona.setLugarResidencia(LBD.buscar(rs.getInt("id_lugar_residencia")));

            //Consultamos foto y la cargamos si tiene foto
            if (rs.getBinaryStream("persona_foto") != null) {
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
            if (rs.wasNull()) {
                persona.setSexo(' ');
            } else {
                persona.setSexo(rs.getString("persona_sexo").charAt(0));
            }

            if (rs.wasNull()) {
                persona.setEstadoCivil(null);
            } else {
                persona.setEstadoCivil(rs.getString("persona_estado_civil"));
            }

            if (rs.wasNull()) {
                persona.setEtnia(null);
            } else {
                persona.setEtnia(rs.getString("persona_etnia"));
            }

            if (rs.wasNull()) {
                persona.setIdiomaRaiz(null);
            } else {
                persona.setIdiomaRaiz(rs.getString("persona_idioma_raiz"));
            }

            if (rs.wasNull()) {
                persona.setTipoSangre(null);
            } else {
                persona.setTipoSangre(rs.getString("persona_tipo_sangre"));
            }

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

            if (rs.wasNull()) {
                persona.setCorreo(null);
            } else {
                persona.setCorreo(rs.getString("persona_correo"));
            }

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

            if (rs.wasNull()) {
                persona.setCallePrincipal(null);
            } else {
                persona.setCallePrincipal(rs.getString("persona_calle_principal"));
            }

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

            if (rs.wasNull()) {
                persona.setIdioma(null);
            } else {
                persona.setIdioma(rs.getString("persona_idioma"));
            }

            if (rs.wasNull()) {
                persona.setTipoResidencia("SELECCIONE");
            } else {
                persona.setTipoResidencia(rs.getString("persona_tipo_residencia"));
            }

            //Solo se usa la funcion .toLocalDate cuando nos regresa un tipo date 
//            if (rs.wasNull()) {
//                persona.setFechaNacimiento(null);
//            } else {
//                persona.setFechaNacimiento(rs.getDate("persona_fecha_nacimiento").toLocalDate());
//            }
            persona.setFechaNacimiento(rs.getDate("persona_fecha_nacimiento").toLocalDate());

            persona.setPersonaActiva(rs.getBoolean("persona_activa"));

            if (rs.wasNull()) {
                persona.setCategoriaMigratoria(null);
            } else {
                persona.setCategoriaMigratoria(rs.getString("persona_categoria_migratoria"));
            }

            return persona;
        } catch (SQLException e) {
            M.errorMsg("No mapeamos personas. " + e.getMessage());
            return null;
        }
    }

    public List<PersonaMD> filtrarEliminados() {
        String nsql = "SELECT id_persona, persona_identificacion, persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido, persona_fecha_nacimiento FROM public.\"Personas\"\n"
                + "WHERE persona_activa = 'false' ORDER BY persona_primer_apellido ASC;";
        return consultarParaTabla(nsql);
    }

    public Map<String, PersonaMD> selectAll() {
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
            pool.closeStmt().close(rst).close(conn);
        }

        return map;
    }

}
