package modelo.persona;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import modelo.ConectarDB;
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

    public PersonaBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.lugar = new LugarBD(conecta);
    }

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
                + "persona_tipo_residencia, persona_fecha_nacimiento )\n"
                + "VALUES (" + getLugarNatal().getId() + ", "
                + getLugarResidencia().getId() + " , '" + getIdentificacion() + "', '"
                + getPrimerApellido() + "', '" + getSegundoApellido() + "', '" + getPrimerNombre() + "', '"
                + getSegundoNombre() + "', '" + getGenero() + "', '" + getSexo() + "', '" + getEstadoCivil() + "', '"
                + getEtnia() + "', '" + getIdiomaRaiz() + "', '" + getTipoSangre() + "', '" + getTelefono() + "', '"
                + getCelular() + "', '" + getCorreo() + "', '" + getFechaRegistro() + "', '" + isDiscapacidad() + "', '"
                + getTipoDiscapacidad() + "', '" + getPorcentajeDiscapacidad() + "', '" + getCarnetConadis() + "', '"
                + getCallePrincipal() + "', '" + getNumeroCasa() + "', '" + getCalleSecundaria() + "', '"
                + getReferencia() + "', '" + getSector() + "', '" + getIdioma() + "', '" + getTipoResidencia() + "', '"
                + getFechaNacimiento() + "');";

        if (conecta.nosql(nsql) == null) {
            System.out.println("Se guardo correctamente");
        }
    }

    public void insertarPersonaConFoto() {
        //Aqui id_persona ya no va porque es autoincrementable
        //TipoPersona si porque necesitamos saber si es estudiante
        //docente u otro 
        String nsql = "INSERT INTO public.\"Personas\"(\n"
                + "id_lugar_natal, id_lugar_residencia, persona_foto,"
                + "persona_identificacion, persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre,persona_genero, persona_sexo, "
                + "persona_estado_civil, persona_etnia, persona_idioma_raiz, persona_tipo_sangre, "
                + "persona_telefono, persona_celular, persona_correo, persona_fecha_registro, "
                + "persona_calle_principal, persona_numero_casa, "
                + "persona_calle_secundaria, persona_referencia, persona_sector, persona_idioma, "
                + "persona_tipo_residencia, persona_fecha_nacimiento )\n"
                + "VALUES (" + getLugarNatal().getId() + ", "
                + getLugarResidencia().getId() + ", ? , '" + getIdentificacion() + "', '"
                + getPrimerApellido() + "', '" + getSegundoApellido() + "', '" + getPrimerNombre() + "', '"
                + getSegundoNombre() + "', '" + getGenero() + "', '" + getSexo() + "', '" + getEstadoCivil() + "', '"
                + getEtnia() + "', '" + getIdiomaRaiz() + "', '" + getTipoSangre() + "', '" + getTelefono() + "', '"
                + getCelular() + "', '" + getCorreo() + "', '" + getFechaRegistro() + "', '"
                + getCallePrincipal() + "', '" + getNumeroCasa() + "', '" + getCalleSecundaria() + "', '"
                + getReferencia() + "', '" + getSector() + "', '" + getIdioma() + "', '" + getTipoResidencia() + "', '"
                + getFechaNacimiento() + "');";

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
                    + "persona_tipo_residencia, persona_fecha_nacimiento )\n"
                    + "VALUES (" + getLugarNatal().getId() + ", "
                    + getLugarResidencia().getId() + ", ? , '" + getIdentificacion() + "', '"
                    + getPrimerApellido() + "', '" + getSegundoApellido() + "', '" + getPrimerNombre() + "', '"
                    + getSegundoNombre() + "', '" + getGenero() + "', '" + getSexo() + "', '" + getEstadoCivil() + "', '"
                    + getEtnia() + "', '" + getIdiomaRaiz() + "', '" + getTipoSangre() + "', '" + getTelefono() + "', '"
                    + getCelular() + "', '" + getCorreo() + "', '" + getFechaRegistro() + "', '" + isDiscapacidad() + "', '"
                    + getTipoDiscapacidad() + "', '" + getPorcentajeDiscapacidad() + "', '" + getCarnetConadis() + "', '"
                    + getCallePrincipal() + "', '" + getNumeroCasa() + "', '" + getCalleSecundaria() + "', '"
                    + getReferencia() + "', '" + getSector() + "', '" + getIdioma() + "', '" + getTipoResidencia() + "', '"
                    + getFechaNacimiento() + "');";
        }

        PreparedStatement ps = conecta.sqlPS(nsql);
        if (ps != null) {
            try {
                ps.setBinaryStream(1, getFile(), getLogBytes());
                ps.execute();
                ps.close();
            } catch (SQLException e) {
                System.out.println("No se pudo guardar persona con foto");
                System.out.println(e.getMessage());
            }
        }
    }

    //Sentencia para editar una Persona
    public boolean editarPersona(int aguja) {
        String sql = "UPDATE public.\"Personas\" SET\n"
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
                + "', persona_fecha_registro = '" + getFechaRegistro()
                + "', persona_calle_principal = '"
                + getCallePrincipal() + "', persona_numero_casa = '" + getNumeroCasa()
                + "', persona_calle_secundaria = '" + getCalleSecundaria() + "', persona_referencia = '"
                + getReferencia() + "', persona_sector = '" + getSector() + "', persona_idioma = '"
                + getIdioma() + "', persona_tipo_residencia = '" + getTipoResidencia()
                + "', persona_fecha_nacimiento = '" + getFechaNacimiento() + "'\n"
                + " WHERE id_persona = " + aguja + ";";

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
                    + "', persona_fecha_nacimiento = '" + getFechaNacimiento() + "'\n"
                    + " WHERE id_persona = " + aguja + ";";
        }
        
        System.out.println(sql);

        if (conecta.nosql(sql) == null) {
            System.out.println("Se edito correctamente");
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    // Utilizamos la sentencia update para modificar el estado de una persona
    // y no eliminarla por completo con parametro por identidicacion
    public boolean eliminarPersonaIdentificacion(String identificacion) {
        String sql = "UPDATE public.\"Personas\"\n"
                + "SET persona_activa='false'"
                + "WHERE persona_identificacion = '" + identificacion + "';";

        if (conecta.nosql(sql) == null) {
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

        if (conecta.nosql(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    //Consultamos todos las personas en nuestro sistema 
    //que no esten eliminadas
    public ArrayList<PersonaMD> cargarPersonas() {
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
                + "persona_fecha_nacimiento, persona_activa\n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true';";
        return consultar(sql);
    }

    public ArrayList<PersonaMD> buscar(String aguja) {
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
                + "persona_fecha_nacimiento, persona_activa\n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' AND( "
                + "persona_identificacion ILIKE '%" + aguja + "%' OR "
                + "persona_primer_apellido ILIKE '%" + aguja + "%' OR  "
                + "persona_segundo_apellido ILIKE '%" + aguja + "%' OR "
                + "persona_primer_nombre ILIKE '%" + aguja + "%' OR "
                + "persona_segundo_nombre ILIKE '%" + aguja + "%');";
        return consultar(sql);
    }

    //Consultamos unicamente a las personas que son alumnos  
    public ArrayList<PersonaMD> cargarPorTipo(int idTipoPersona) {
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
                + "persona_fecha_nacimiento, persona_activa\n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' "
                + "AND id_tipo_persona = " + idTipoPersona + ";";
        return consultar(sql);
    }

    //Buscar Persona con aguja
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
                + "persona_fecha_nacimiento, persona_activa\n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' AND"
                + " id_persona = " + idPersona + ";";

        return consultarPor(sql);
    }

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
                + "persona_fecha_nacimiento, persona_activa\n"
                + "FROM public.\"Personas\" WHERE persona_activa = 'true' AND"
                + " persona_identificacion ='" + identificacion + "'";

        return consultarPor(sql);
    }

    //Este meotod nos devolvera una gran cantidad de personas 
    //de nuestra base de datos dependiendo de la setencia sql 
    //que se le envie
    private ArrayList<PersonaMD> consultar(String sql) {
        ArrayList<PersonaMD> pers = new ArrayList();
        PersonaMD p;
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    p = obtenerPersona(rs);
                    if (p != null) {
                        pers.add(p);
                    }
                }
                rs.close();
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
        PersonaMD p = new PersonaMD();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    p = obtenerPersona(rs);
                }
                rs.close();
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

            if (rs.wasNull()) {
                persona.setCallePrincipal(null);
            } else {
                persona.setCallePrincipal(rs.getString("persona_calle_principal"));
            }

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

            return persona;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener persona");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
