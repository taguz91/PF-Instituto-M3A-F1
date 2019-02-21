package modelo.persona;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import modelo.ConectarDB;
import modelo.lugar.LugarBD;

/**
 *
 * @author Lina
 */
public class PersonaBD extends PersonaMD {

    ConectarDB conecta = new ConectarDB();

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

    public PersonaBD consultarPersona(String idpersona) {

        PersonaBD persona = new PersonaBD();
        InputStream is;

        String sql = "Select * from public.\"Personas\"  where \"id_persona\" = '" + idpersona + "';";
        try {

            ResultSet rs = conecta.sql(sql);
            while (rs.next()) {
                if (rs.wasNull()) {
                    is = rs.getBinaryStream(null);

                } else {

                    is = rs.getBinaryStream("persona_foto");

                    //Pasamos la imagen
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        ImageIcon foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        persona.setFoto(img);
                    } catch (IOException ex) {
                        System.out.println("Error al pasar la foto: " + ex.getMessage());
                    }
                }
                persona.setIdPersona(rs.getInt("id_persona"));
                //Aqui se crea un tipo persona  
                TipoPersonaBD tipoPer = new TipoPersonaBD();
                persona.setTipo(tipoPer.buscar(rs.getInt("id_tipo_persona")));
                //Aqui igual se crear una clase lugar 
                LugarBD lugar = new LugarBD();
                //Buscamos el lugar que corresponde a cada cosa
                persona.setLugarNatal(lugar.buscar(rs.getInt("id_lugar_natal")));
                persona.setLugarResidencia(lugar.buscar(rs.getByte("id_lugar_residencia")));

                persona.setIdentificacion(rs.getString("persona_identificacion"));
                persona.setPrimerApellido(rs.getString("persona_primer_apellido"));
                persona.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                persona.setPrimerNombre(rs.getString("persona_primer_nombre"));
                persona.setSegundoNombre(rs.getString("persona_segundo_nombre"));

                if (rs.wasNull()) {
                    persona.setGenero(rs.getString(null));
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
                    persona.setTelefono(rs.getString(null));
                } else {

                    persona.setTelefono(rs.getString("persona_telefono"));

                }
                if (rs.wasNull()) {
                    persona.setCelular(rs.getString(null));
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
                    persona.setPorcentajeDiscapacidad(rs.getByte(null));
                } else {
                    persona.setPorcentajeDiscapacidad(rs.getByte("persona_porcenta_discapacidad"));

                }

                if (rs.wasNull()) {
                    persona.setCarnetConadis(rs.getString(null));
                } else {
                    persona.setCarnetConadis(rs.getString("persona_carnet_conadis"));

                }

                if (rs.wasNull()) {
                    persona.setCallePrincipal(null);
                } else {
                    persona.setCallePrincipal(rs.getString("persona_calle_principal"));

                }

                if (rs.wasNull()) {
                    persona.setNumeroCasa(rs.getString(null));
                } else {
                    persona.setNumeroCasa(rs.getString("persona_numero_casa"));

                }

                if (rs.wasNull()) {
                    persona.setCalleSecundaria(rs.getString(null));
                } else {
                    persona.setCalleSecundaria(rs.getString("persona_calle_secundaria"));

                }

                if (rs.wasNull()) {
                    persona.setReferencia(rs.getString(null));
                } else {
                    persona.setReferencia(rs.getString("persona_referencia"));

                }

                if (rs.wasNull()) {
                    persona.setSector(rs.getString(null));
                } else {
                    persona.setSector(rs.getString("persona_sector"));

                }

                persona.setIdioma(rs.getString("persona_idioma"));

                if (rs.wasNull()) {
                    persona.setTipoResidencia(rs.getString(null));
                } else {
                    persona.setTipoResidencia(rs.getString("persona_tipo_residencia"));

                }

                //Solo se usa la funcion .toLocalDate cuando nos regresa un tipo date 
                persona.setFechaNacimiento(rs.getDate("persona_fecha_nacimiento").toLocalDate());
                persona.setPersonaActiva(rs.getBoolean("persona_activa"));

            }
            return persona;

        } catch (SQLException e) {
            System.out.println("No se pudo consultar una persona " + idpersona);
            System.out.println(e.getMessage());
            System.out.println(sql);
            return null;
        }

    }

    
    //Buscar Persona con aguja
    public PersonaBD buscarPersona(String aguja) {
        String nsql = "SELECT id_persona, persona_identificacion, persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido"
                + " FROM public.\"Personas\" WHERE id_persona = '" + aguja + "';";
        ResultSet rs = conecta.sql(nsql);
        try {
            PersonaBD p = new PersonaBD();
            while (rs.next()) {
                p.setIdPersona(rs.getInt("id_persona"));
                p.setIdentificacion(rs.getString("persona_identificacion"));
                p.setPrimerNombre(rs.getString("persona_primer_nombre"));
                p.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                p.setPrimerApellido(rs.getString("persona_primer_apellido"));
                p.setSegundoApellido(rs.getString("persona_segundo_apellido"));
            }
            rs.close();
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(PersonaBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    //Metodo para cargar persona en un ArrayList
     public ArrayList<PersonaMD> cargarPersonas() throws SQLException {
        ArrayList<PersonaMD> personas = new ArrayList();
        String sql = "select * from \"Persona\"";
        ResultSet rs = conecta.sql(sql);
        System.out.println(sql);
        while (rs.next()) {
            PersonaMD p = new PersonaMD();
            p.setIdPersona(rs.getInt("id_persona"));
            p.setIdentificacion(rs.getString("persona_identificacion"));
            p.setPrimerNombre(rs.getString("persona_primer_nombre"));
            p.setSegundoNombre(rs.getString("persona_segundo_nombre"));
            p.setPrimerApellido(rs.getString("persona_primer_apellido"));
            p.setSegundoApellido(rs.getString("persona_segundo_apellido"));
            p.setFechaNacimiento(rs.getDate("persona_fecha_nacimiento").toLocalDate());
            
        }

        return personas;
    }

    //Metodo para mostrar Datos de Persona
    public List<PersonaMD> mostrarDatos() {
        try {
            List<PersonaMD> lista = new ArrayList<PersonaMD>();
            String sql = "select * from \"Persona\"";
            ResultSet rs = conecta.sql(sql);
            System.out.println(sql);
            while (rs.next()) {
                PersonaMD p = new PersonaMD();
                p.setIdPersona(rs.getInt("id_persona"));
                p.setPrimerNombre(rs.getString("persona_primer_nombre"));
                p.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                p.setPrimerApellido(rs.getString("persona_primer_apellido"));
                p.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                

                lista.add(p);

            }
            //rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }
    
    
    public PersonaBD buscarPersonaCedula (String identificacion){
        PersonaBD p = new PersonaBD();
        String sql = "Select id_persona, persona_primer_nombre "
                + "from public.\"Personas\" "
                + "where persona_identificacion ='"+identificacion+"'";
        
        ResultSet rs = conecta.sql(sql);
        try {
            if(rs!=null){
                while(rs.next()){
                    p.setIdPersona(rs.getInt("id_persona"));
                    p.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    
                }
                rs.close();
                return p;
            }else{
                return null;
            }
            
        } catch (Exception e) {
            System.out.println("No se pudo consultar la persona"+ e.getMessage());
            return null;
        }
        
        
    }
    
}
