package modelo.persona;

import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConectarDB;
import modelo.lugar.LugarBD;

public class AlumnoBD extends AlumnoMD {

    ConectarDB conecta = new ConectarDB("Alumno");
    private PersonaBD per = new PersonaBD();

    public AlumnoBD() {
    }

    public AlumnoBD(int id_Alumno, String nombre, String tipo_Colegio, String tipo_Bachillerato, String nivel_Academico, String titulo_Superior, String ocupacion, String anio_graduacion, String observacion, String sector_Economico, String formacion_Padre, String formacion_Madre, String nom_Contacto, String parentesco_Contacto, String contacto_Emergencia, int id_SecEconomico, boolean educacion_Superior, boolean pension, boolean trabaja, boolean activo, int idPersona, TipoPersonaBD tipo, LugarBD lugarNatal, LugarBD lugarResidencia, Image foto, String identificacion, String primerApellido, String segundoApellido, String primerNombre, String segundoNombre, LocalDate fechaNacimiento, String genero, char sexo, String estadoCivil, String etnia, String idiomaRaiz, String tipoSangre, String telefono, String celular, String correo, LocalDate fechaRegistro, boolean discapacidad, String tipoDiscapacidad, byte porcentajeDiscapacidad, String carnetConadis, String callePrincipal, String numeroCasa, String calleSecundaria, String referencia, String sector, String idioma, String tipoResidencia, boolean personaActiva) {
        super(id_Alumno, nombre, tipo_Colegio, tipo_Bachillerato, nivel_Academico, titulo_Superior, ocupacion, anio_graduacion, observacion, sector_Economico, formacion_Padre, formacion_Madre, nom_Contacto, parentesco_Contacto, contacto_Emergencia, id_SecEconomico, educacion_Superior, pension, trabaja, activo, idPersona, tipo, lugarNatal, lugarResidencia, foto, identificacion, primerApellido, segundoApellido, primerNombre, segundoNombre, fechaNacimiento, genero, sexo, estadoCivil, etnia, idiomaRaiz, tipoSangre, telefono, celular, correo, fechaRegistro, discapacidad, tipoDiscapacidad, porcentajeDiscapacidad, carnetConadis, callePrincipal, numeroCasa, calleSecundaria, referencia, sector, idioma, tipoResidencia, personaActiva);
    }

    public boolean guardarAlumno(SectorEconomicoMD s) {
        String nsql = "INSERT INTO public.\"Alumnos\"(\n"
                + "	 id_persona, id_sec_economico, alumno_tipo_colegio, alumno_tipo_bachillerato, alumno_anio_graduacion,"
                + " alumno_educacion_superior, alumno_titulo_superior, alumno_nivel_academico, alumno_pension, alumno_ocupacion, alumno_trabaja,"
                + " alumno_nivel_formacion_padre, alumno_nivel_formacion_madre, alumno_nombre_contacto_emergencia, alumno_parentesco_contacto, alumno_numero_contacto, alumno_activo)\n"
                + "	VALUES ( " + getIdPersona() + "', " + s.getId_SecEconomico() + ", '" + getTipo_Colegio() + "', '" + getTipo_Bachillerato() + "', "
                + "'" + getAnio_graduacion() + "', " + isEducacion_Superior() + ", '" + getTitulo_Superior() + "', '" + getNivel_Academico() + "', " + isPension() + ", "
                + "'" + getOcupacion() + "', " + isTrabaja() + ", '" + getSector_Economico() + "', '" + getFormacion_Padre() + "', '" + getFormacion_Madre() + "', "
                + " '" + getContacto_Emergencia() + "', '" + getParentesco_Contacto() + "', '" + getNom_Contacto() + "', true);";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean editarAlumno(int aguja) {
        String nsql = "UPDATE public.\"Alumnos\" SET\n"
                + " id_sec_economico = " + getId_SecEconomico() + ", alumno_tipo_colegio = '" + getTipo_Colegio() + "', alumno_tipo_bachillerato = '" + getTipo_Bachillerato() + "', alumno_anio_graduacion = '" + getAnio_graduacion()
                + "', alumno_educacion_superior = " + isEducacion_Superior() + ", alumno_titulo_superior = '" + getTitulo_Superior() + "', alumno_nivel_academico = '" + getNivel_Academico()
                + "', alumno_pension = " + isPension() + ", alumno_ocupacion = '" + getOcupacion() + "', alumno_trabaja = " + isTrabaja()
                + ", alumno_nivel_formacion_padre = '" + getFormacion_Padre() + "', alumno_nivel_formacion_madre = '" + getFormacion_Madre() + "', alumno_nombre_contacto_emergencia = '" + getNom_Contacto()
                + "', alumno_parentesco_contacto = '" + getParentesco_Contacto() + "', alumno_numero_contacto = '" + getContacto_Emergencia() + "'\n"
                + " WHERE id_persona = " + aguja + ";";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean eliminarAlumno(int aguja) {
        AlumnoMD m = new AlumnoMD();
        String nsql = "UPDATE public.\"Alumnos\" SET\n"
                + "alumno_activo = false, alumno_observacion = '" + getObservacion()
                + "' WHERE id_persona = " + aguja + ";";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public AlumnoMD capturarDatos_Alumno(String aguja) {
        String sql = "SELECT * FROM public.\"Alumnos\" WHERE id_persona = '" + aguja + "' AND alumno_activo = true;";
        ResultSet rs = conecta.sql(sql);
        try {
            AlumnoMD a = new AlumnoMD();
            while (rs.next()) {
                a.setTipo_Colegio(rs.getString("alumno_tipo_colegio"));
                a.setTipo_Bachillerato(rs.getString("alumno_tipo_bachillerato"));
                a.setAnio_graduacion(rs.getString("alumno_anio_graduacion"));
                a.setEducacion_Superior(rs.getBoolean("alumno_educacion_superior"));
                a.setTitulo_Superior(rs.getString("alumno_educacion_superior"));
                a.setNivel_Academico(rs.getString("alumno_nivel_academico"));
                a.setPension(rs.getBoolean("alumno_pension"));
                a.setOcupacion(rs.getString("alumno_ocupacion"));
                a.setTrabaja(rs.getBoolean("alumno_trabaja"));
                a.setSector_Economico(rs.getString("alumno_sector_economico"));
                a.setFormacion_Padre(rs.getString("alumno_nivel_formacion_padre"));
                a.setFormacion_Madre(rs.getString("alumno_nivel_formacion_madre"));
                a.setContacto_Emergencia(rs.getString("alumno_nombre_contacto_emergencia"));
                a.setParentesco_Contacto(rs.getString("alumno_parentesco_contacto"));
                a.setNom_Contacto(rs.getString("alumno_numero_contacto"));
            }
            rs.close();
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<PersonaMD> llenarTabla() {
        List<PersonaMD> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.persona_identificacion, "
                + " p.persona_primer_nombre, p.persona_segundo_nombre,"
                + " p.persona_primer_apellido, p.persona_segundo_apellido,"
                + " p.persona_correo"
                + " FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona) WHERE a.alumno_activo = true;";
        //Esto estaba mal WHERE alumno_activo = 'true'
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                PersonaMD m = new PersonaMD();
                m.setIdPersona(rs.getInt("id_persona"));
                m.setIdentificacion(rs.getString("persona_identificacion"));
                m.setPrimerNombre(rs.getString("persona_primer_nombre"));
                m.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                m.setPrimerApellido(rs.getString("persona_primer_apellido"));
                m.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                m.setCorreo(rs.getString("persona_correo"));

                lista.add(m);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<PersonaMD> capturarPersona(String aguja) {
        List<PersonaMD> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.persona_identificacion, p.persona_primer_nombre, p.persona_segundo_nombre, p.persona_primer_apellido, p.persona_segundo_apellido, p.persona_correo"
                + " FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona) WHERE p.persona_identificacion LIKE '%" + aguja + "%' OR  p.persona_primer_nombre LIKE '%"
                + aguja + "%' OR p.persona_segundo_nombre LIKE '%" + aguja + "%' OR p.persona_primer_apellido LIKE '%"
                + aguja + "%' OR p.persona_segundo_apellido LIKE '%" + aguja + "%' OR p.persona_correo LIKE '%"
                + aguja + "%' AND a.alumno_activo = true;";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                PersonaMD m = new PersonaMD();
                m.setIdPersona(rs.getInt("id_persona"));
                m.setIdentificacion(rs.getString("persona_identificacion"));
                m.setPrimerNombre(rs.getString("persona_primer_nombre"));
                m.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                m.setPrimerApellido(rs.getString("persona_primer_apellido"));
                m.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                m.setCorreo(rs.getString("persona_correo"));
                lista.add(m);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public AlumnoMD buscarPersona(int aguja) {
        String sql = "SELECT p.id_persona, p.persona_identificacion, p.persona_primer_apellido,\n"
                + "p.persona_segundo_apellido, p.persona_primer_nombre, p.persona_segundo_nombre,\n"
                + "a.id_alumno, a.id_sec_economico, a.alumno_tipo_colegio, a.alumno_tipo_bachillerato,\n"
                + "a.alumno_anio_graduacion, a.alumno_educacion_superior, a.alumno_titulo_superior, a.alumno_nivel_academico,\n"
                + "a.alumno_pension, a.alumno_ocupacion, a.alumno_trabaja, a.alumno_nivel_formacion_padre, a.alumno_nivel_formacion_madre,\n"
                + "a.alumno_nombre_contacto_emergencia, a.alumno_parentesco_contacto, a.alumno_numero_contacto\n"
                + "FROM public.\"Personas\" p, public.\"Alumnos\" a\n"
                + "WHERE p.id_persona = a.id_persona AND a.id_persona = " + aguja + " AND a.alumno_activo = true AND p.persona_activa = true;";
        ResultSet rs = conecta.sql(sql);
        try {
            AlumnoMD a = new AlumnoMD();
            while (rs.next()) {
                a.setIdPersona(rs.getInt("id_persona"));
                a.setIdentificacion(rs.getString("persona_identificacion"));
                a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                a.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                a.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                a.setId_Alumno(rs.getInt("id_alumno"));
                a.setId_SecEconomico(rs.getInt("id_sec_economico"));
                a.setTipo_Colegio(rs.getString("alumno_tipo_colegio"));
                a.setTipo_Bachillerato(rs.getString("alumno_tipo_bachillerato"));
                a.setAnio_graduacion(rs.getString("alumno_anio_graduacion"));
                a.setEducacion_Superior(rs.getBoolean("alumno_educacion_superior"));
                a.setTitulo_Superior(rs.getString("alumno_educacion_superior"));
                a.setNivel_Academico(rs.getString("alumno_nivel_academico"));
                a.setPension(rs.getBoolean("alumno_pension"));
                a.setOcupacion(rs.getString("alumno_ocupacion"));
                a.setTrabaja(rs.getBoolean("alumno_trabaja"));
                a.setFormacion_Padre(rs.getString("alumno_nivel_formacion_padre"));
                a.setFormacion_Madre(rs.getString("alumno_nivel_formacion_madre"));
                a.setContacto_Emergencia(rs.getString("alumno_numero_contacto"));
                a.setParentesco_Contacto(rs.getString("alumno_parentesco_contacto"));
                a.setNom_Contacto(rs.getString("alumno_nombre_contacto_emergencia"));
            }
            rs.close();
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public AlumnoMD buscarAlumno(int idAlumno) {
        AlumnoMD al = new AlumnoMD();
        String sql = "SELECT id_alumno, id_persona\n"
                + "	FROM public.\"Alumnos\" WHERE alumno_activo = 'true' AND "
                + "id_alumno = " + idAlumno + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {

                    al.setId_Alumno(rs.getInt("id_alumno"));
                    PersonaMD p = per.buscarPersona(rs.getInt("id_persona"));
                    al.setPersona(p);
                }
                return al;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar alumnos");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<AlumnoMD> cargarAlumnos() {
        ArrayList<AlumnoMD> almns = new ArrayList();
        String sql = "SELECT id_alumno, id_persona\n"
                + "	FROM public.\"Alumnos\" WHERE alumno_activo = 'true';";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    AlumnoMD al = new AlumnoMD();
                    al.setId_Alumno(rs.getInt("id_alumno"));
                    PersonaMD p = per.buscarPersona(rs.getInt("id_persona"));
                    al.setPersona(p);
                    
                    almns.add(al); 
                }
                return almns;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar alumnos");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
