package modelo.persona;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    ConectarDB conecta = new ConectarDB("Persona");
    //Se usaran estas clases para consultar
    LugarBD lugar = new LugarBD();
    TipoPersonaBD tipoPer = new TipoPersonaBD();

    //Esto se usara para cargar las fotos 
    InputStream is;

    public PersonaBD() {
    }

    public PersonaBD(int idPersona, TipoPersonaBD tipo, LugarBD lugarNatal, LugarBD lugarResidencia, Image foto, String identificacion, String primerApellido, String segundoApellido, String primerNombre, String segundoNombre, LocalDate fechaNacimiento, String genero, char sexo, String estadoCivil, String etnia, String idiomaRaiz, String tipoSangre, String telefono, String celular, String correo, LocalDate fechaRegistro, boolean discapacidad, String tipoDiscapacidad, byte porcentajeDiscapacidad, String carnetConadis, String callePrincipal, String numeroCasa, String calleSecundaria, String referencia, String sector, String idioma, String tipoResidencia, boolean personaActiva) {
        super(idPersona, tipo, lugarNatal, lugarResidencia, foto, identificacion, primerApellido, segundoApellido, primerNombre, segundoNombre, fechaNacimiento, genero, sexo, estadoCivil, etnia, idiomaRaiz, tipoSangre, telefono, celular, correo, fechaRegistro, discapacidad, tipoDiscapacidad, porcentajeDiscapacidad, carnetConadis, callePrincipal, numeroCasa, calleSecundaria, referencia, sector, idioma, tipoResidencia, personaActiva);
    }
    
    public boolean insertarPersona() {
        //Aqui id_persona ya no va porque es autoincrementable
        //TipoPersona si porque necesitamos saber si es estudiante
        //docente u otro 
        String sql = "INSERT INTO public.\"Personas\"(\n"
                + "id_tipo_persona, id_lugar_natal, id_lugar_residencia, persona_foto,"
                + "persona_identificacion, persona_primer_apellido, persona_segundo_apellido, "
                + "persona_primer_nombre, persona_segundo_nombre,persona_genero, persona_sexo, "
                + "persona_estado_civil, persona_etnia, persona_idioma_raiz, persona_tipo_sangre, "
                + "persona_telefono, persona_celular, persona_correo, persona_fecha_registro, "
                + "persona_discapacidad, persona_tipo_discapacidad, persona_porcenta_discapacidad, "
                + "persona_carnet_conadis, persona_calle_principal, persona_numero_casa, "
                + "persona_calle_secundaria, persona_referencia, persona_sector, persona_idioma, "
                + "persona_tipo_residencia, persona_fecha_nacimiento, persona_activa )\n"
                + "VALUES ('" + getTipo() + "', '" + getLugarNatal() + "', '"
                + getLugarResidencia() + "', ? , '" + getIdentificacion() + "', '"
                + getPrimerApellido() + "', " + getSegundoApellido() + ", " + getPrimerNombre() + "', '"
                + getSegundoNombre() + "', '" + getGenero() + "', '" + getSexo() + "', '" + getEstadoCivil() + "', '"
                + getEtnia() + "', '" + getIdiomaRaiz() + "', '" + getTipoSangre() + "', '" + getTelefono() + "', '"
                + getCelular() + "', '" + getCorreo() + "', '" + getFechaRegistro() + "', '" + isDiscapacidad() + "', '"
                + getTipoDiscapacidad() + "', '" + getPorcentajeDiscapacidad() + "', '" + getCarnetConadis() + "', '"
                + getCallePrincipal() + "', '" + getNumeroCasa() + "', '" + getCalleSecundaria() + "', '"
                + getReferencia() + "', '" + getSector() + "', '" + getIdioma() + "', '" + getTipoResidencia() + "', '"
                + getFechaNacimiento() + "', '" + isPersonaActiva() + ");";

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
        String sql = "SELECT id_persona, id_tipo_persona, id_lugar_natal, "
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

    //Consultamos unicamente a las personas que son alumnos  
    public ArrayList<PersonaMD> cargarPorTipo(int idTipoPersona) {
        String sql = "SELECT id_persona, id_tipo_persona, id_lugar_natal, "
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
        String sql = "SELECT id_persona, persona_identificacion, persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido"
                + " FROM public.\"Personas\" WHERE id_persona = " + idPersona + ";";
        return consultarPor(sql);
    }

    public PersonaMD buscarPersona(String identificacion) {
        String sql = "Select id_persona, persona_primer_nombre "
                + "from public.\"Personas\" "
                + "where persona_identificacion ='" + identificacion + "'";
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
            //Aqui se crea un tipo persona  
            persona.setTipo(tipoPer.buscar(rs.getInt("id_tipo_persona")));
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
