package modelo.persona;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConectarDB;

public class AlumnoBD extends AlumnoMD {

    private final ConectarDB conecta;
    private final PersonaBD per;

    public AlumnoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.per = new PersonaBD(conecta);
    }

    public boolean guardarAlumno(SectorEconomicoMD s) {
        String nsql = "INSERT INTO public.\"Alumnos\"(\n"
                + "	 id_persona, id_sec_economico, alumno_codigo,alumno_tipo_colegio, alumno_tipo_bachillerato, alumno_anio_graduacion,"
                + " alumno_educacion_superior, alumno_titulo_superior, alumno_nivel_academico, alumno_pension, alumno_ocupacion, alumno_trabaja,"
                + " alumno_nivel_formacion_padre, alumno_nivel_formacion_madre, alumno_nombre_contacto_emergencia, alumno_parentesco_contacto, alumno_numero_contacto, alumno_activo)\n"
                + "	VALUES ( " + getIdPersona() + ", " + s.getId_SecEconomico() + ", '" + getIdentificacion() + ", '" + getTipo_Colegio() + "', '" + getTipo_Bachillerato() + "', "
                + "'" + getAnio_graduacion() + "', " + isEducacion_Superior() + ", '" + getTitulo_Superior() + "', '" + getNivel_Academico() + "', " + isPension() + ", "
                + "'" + getOcupacion() + "', " + isTrabaja() + ", '" + getFormacion_Padre() + "', '" + getFormacion_Madre() + "', "
                + " '" + getNom_Contacto() + "', '" + getParentesco_Contacto() + "', '" + getContacto_Emergencia() + "', true);";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean editarAlumno(int aguja) {
        String nsql = "UPDATE public.\"Alumnos\" SET\n"
                + " id_sec_economico = " + getSectorEconomico().getId_SecEconomico() + ", alumno_tipo_colegio = '" + getTipo_Colegio() + "', alumno_tipo_bachillerato = '" + getTipo_Bachillerato() + "', alumno_anio_graduacion = '" + getAnio_graduacion()
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

    public boolean eliminarAlumno(AlumnoMD a, int aguja) {
        String nsql = "UPDATE public.\"Alumnos\" SET\n"
                + " alumno_activo = false, alumno_observacion = '" + a.getObservacion()
                + "' WHERE id_persona = " + aguja + ";";
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public List<PersonaMD> llenarTabla() {
        List<PersonaMD> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.persona_identificacion, "
                + " p.persona_primer_nombre, p.persona_segundo_nombre,"
                + " p.persona_primer_apellido, p.persona_segundo_apellido,"
                + " p.persona_correo"
                + " FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona) WHERE a.alumno_activo = true AND p.persona_activa = true;";
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
                + " FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona) "
                + "WHERE (p.persona_identificacion LIKE '%" + aguja + "%' OR  p.persona_primer_nombre LIKE '%"
                + aguja + "%' OR p.persona_segundo_nombre LIKE '%" + aguja + "%' OR p.persona_primer_apellido LIKE '%"
                + aguja + "%' OR p.persona_segundo_apellido LIKE '%" + aguja + "%' OR p.persona_correo LIKE '%"
                + aguja + "%') AND p.persona_activa = true AND a.alumno_activo = true;";
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

    public List<PersonaMD> filtrarPersona(String aguja) {
        List<PersonaMD> lista = new ArrayList();
        String sql = "SELECT id_persona, persona_identificacion, persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido"
                + " FROM public.\"Personas\" WHERE persona_identificacion LIKE '%" + aguja + "%' AND persona_activa = true";
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
        String sql = "SELECT p.id_persona, p.persona_identificacion, p.persona_primer_nombre,\n"
                + "p.persona_segundo_nombre, p.persona_primer_apellido, p.persona_segundo_apellido,\n"
                + "a.id_alumno, a.id_sec_economico, a.alumno_tipo_colegio, a.alumno_tipo_bachillerato,\n"
                + "a.alumno_anio_graduacion, a.alumno_educacion_superior, a.alumno_titulo_superior, a.alumno_nivel_academico,\n"
                + "a.alumno_pension, a.alumno_ocupacion, a.alumno_trabaja, a.alumno_nivel_formacion_padre, a.alumno_nivel_formacion_madre,\n"
                + "a.alumno_numero_contacto, a.alumno_nombre_contacto_emergencia, a.alumno_parentesco_contacto\n"
                + "FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona)\n"
                + "WHERE a.id_persona = " + aguja + " AND a.alumno_activo = true AND p.persona_activa = true;";
        ResultSet rs = conecta.sql(sql);
        try {
            AlumnoMD a = new AlumnoMD();
            String palabra;
            Integer numero;
            while (rs.next()) {
                SectorEconomicoMD sector = new SectorEconomicoMD();
                a.setIdPersona(rs.getInt("id_persona"));
                a.setIdentificacion(rs.getString("persona_identificacion"));
                a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                a.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                a.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                a.setId_Alumno(rs.getInt("id_alumno"));
                numero = rs.getInt("id_sec_economico");
                if(numero == null){
                    sector.setId_SecEconomico(0);
                    a.setSectorEconomico(sector);
                } else{
                    sector.setId_SecEconomico(numero);
                    a.setSectorEconomico(sector);
                }
                palabra = rs.getString("alumno_tipo_colegio");
                if(palabra == null){
                    a.setTipo_Colegio("|SELECCIONE|");
                } else{
                    a.setTipo_Colegio(rs.getString("alumno_tipo_colegio"));
                }
                palabra = rs.getString("alumno_tipo_bachillerato");
                if(palabra == null){
                    a.setTipo_Bachillerato("|SELECCIONE|");
                } else{
                    a.setTipo_Bachillerato(palabra);
                }
                palabra = rs.getString("alumno_anio_graduacion");
                if(palabra == null){
                    a.setAnio_graduacion("1980");
                } else{
                    a.setAnio_graduacion(palabra);
                }
                palabra = String.valueOf(rs.getBoolean("alumno_educacion_superior"));
                if(palabra == null){
                  a.setEducacion_Superior(false);  
                } else{
                    a.setEducacion_Superior(rs.getBoolean("alumno_educacion_superior"));
                }
//                if (rs.wasNull()) {
//                    a.setEducacion_Superior(false);
//                } else {
//                    a.setEducacion_Superior(rs.getBoolean("alumno_educacion_superior"));
//                }
                palabra = rs.getString("alumno_titulo_superior");
                if(palabra == null){
                    a.setTitulo_Superior(null);
                } else{
                    a.setTitulo_Superior(palabra);
                }
                palabra = rs.getString("alumno_nivel_academico");
                if(palabra == null){
                    a.setNivel_Academico("|SELECCIONE|");
                } else{
                    a.setNivel_Academico(palabra);
                }
                palabra = String.valueOf(rs.getBoolean("alumno_pension"));
                if(palabra == null){
                    a.setPension(false);
                } else{
                    a.setPension(rs.getBoolean("alumno_pension"));
                }
//                if (rs.wasNull()) {
//                    a.setPension(false);
//                } else {
//                    a.setPension(rs.getBoolean("alumno_pension"));
//                }
                palabra = rs.getString("alumno_ocupacion");
                if(palabra == null){
                    a.setOcupacion(null);
                } else{
                    a.setOcupacion(palabra);
                }
                palabra = String.valueOf(rs.getBoolean("alumno_trabaja"));
                if(palabra == null){
                    a.setTrabaja(false);
                } else{
                    a.setTrabaja(rs.getBoolean("alumno_trabaja"));
                }
//                if (rs.wasNull()) {
//                    a.setTrabaja(false);
//                } else {
//                    a.setTrabaja(rs.getBoolean("alumno_trabaja"));
//                }
                palabra = rs.getString("alumno_nivel_formacion_padre");
                if(palabra == null){
                    a.setFormacion_Padre("|SELECCIONE|");
                } else{
                    a.setFormacion_Padre(palabra);
                }
                palabra = rs.getString("alumno_nivel_formacion_madre");
                if(palabra == null){
                    a.setFormacion_Madre("|SELECCIONE|");
                } else{
                    a.setFormacion_Madre(palabra);
                }
                palabra = rs.getString("alumno_numero_contacto");
                if(palabra == null){
                    a.setContacto_Emergencia(null);
                } else{
                    a.setContacto_Emergencia(palabra);
                }
                palabra = rs.getString("alumno_parentesco_contacto");
                if(palabra == null){
                    a.setParentesco_Contacto("|SELECCIONE|");
                } else{
                    a.setParentesco_Contacto(palabra);
                }
                palabra = rs.getString("alumno_nombre_contacto_emergencia");
                if(palabra == null){
                   a.setNom_Contacto(null); 
                } else{
                    a.setNom_Contacto(palabra);
                }
            }
            rs.close();
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public AlumnoMD buscarPersonaxCedula(String cedula) {
        String sql = "SELECT p.id_persona, p.persona_identificacion, p.persona_primer_nombre,\n"
                + "p.persona_segundo_nombre, p.persona_primer_apellido, p.persona_segundo_apellido,\n"
                + "a.id_alumno, a.id_sec_economico, a.alumno_tipo_colegio, a.alumno_tipo_bachillerato,\n"
                + "a.alumno_anio_graduacion, a.alumno_educacion_superior, a.alumno_titulo_superior, a.alumno_nivel_academico,\n"
                + "a.alumno_pension, a.alumno_ocupacion, a.alumno_trabaja, a.alumno_nivel_formacion_padre, a.alumno_nivel_formacion_madre,\n"
                + "a.alumno_numero_contacto, a.alumno_nombre_contacto_emergencia, a.alumno_parentesco_contacto\n"
                + "FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona)\n"
                + "WHERE p.persona_identificacion LIKE '" + cedula + "' AND a.alumno_activo = true AND p.persona_activa = true;";
        ResultSet rs = conecta.sql(sql);
        try {
            AlumnoMD a = new AlumnoMD();
            
            while (rs.next()) {
                SectorEconomicoMD sector = new SectorEconomicoMD();
                a.setIdPersona(rs.getInt("id_persona"));
                a.setIdentificacion(rs.getString("persona_identificacion"));
                a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                a.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                a.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                a.setId_Alumno(rs.getInt("id_alumno"));
                System.out.println("Aguja: " + a.getId_Alumno());
                if (rs.wasNull()) {
                    sector.setId_SecEconomico(0);
                    a.setSectorEconomico(sector);
                } else {
                    sector.setId_SecEconomico(rs.getInt("id_sec_economico"));
                    a.setSectorEconomico(sector);
                }
                if (rs.wasNull()) {
                    a.setTipo_Colegio("|SELECCIONE|");
                } else {
                    a.setTipo_Colegio(rs.getString("alumno_tipo_colegio"));
                }
                if (rs.wasNull()) {
                    a.setTipo_Bachillerato("|SELECCIONE|");
                } else {
                    a.setTipo_Bachillerato(rs.getString("alumno_tipo_bachillerato"));
                }
                if (rs.wasNull()) {
                    a.setAnio_graduacion("1980");
                } else {
                    a.setAnio_graduacion(rs.getString("alumno_anio_graduacion"));
                }
                if (rs.wasNull()) {
                    a.setEducacion_Superior(false);
                } else {
                    a.setEducacion_Superior(rs.getBoolean("alumno_educacion_superior"));
                }
                if (rs.wasNull()) {
                    a.setTitulo_Superior(null);
                } else {
                    a.setTitulo_Superior(rs.getString("alumno_titulo_superior"));
                }
                if (rs.wasNull()) {
                    a.setNivel_Academico("|SELECCIONE|");
                } else {
                    a.setNivel_Academico(rs.getString("alumno_nivel_academico"));
                }
                if (rs.wasNull()) {
                    a.setPension(false);
                } else {
                    a.setPension(rs.getBoolean("alumno_pension"));
                }
                if (rs.wasNull()) {
                    a.setOcupacion(null);
                } else {
                    a.setOcupacion(rs.getString("alumno_ocupacion"));
                }
                if (rs.wasNull()) {
                    a.setTrabaja(false);
                } else {
                    a.setTrabaja(rs.getBoolean("alumno_trabaja"));
                }
                if (rs.wasNull()) {
                    a.setFormacion_Padre("|SELECCIONE|");
                } else {
                    a.setFormacion_Padre(rs.getString("alumno_nivel_formacion_padre"));
                }
                if (rs.wasNull()) {
                    a.setFormacion_Madre("|SELECCIONE|");
                } else {
                    a.setFormacion_Madre(rs.getString("alumno_nivel_formacion_madre"));
                }
                if (rs.wasNull()) {
                    a.setContacto_Emergencia(null);
                } else {
                    a.setContacto_Emergencia(rs.getString("alumno_numero_contacto"));
                }
                if (rs.wasNull()) {
                    a.setParentesco_Contacto("|SELECCIONE|");
                } else {
                    a.setParentesco_Contacto(rs.getString("alumno_parentesco_contacto"));
                }
                if (rs.wasNull()) {
                    a.setNom_Contacto(null);
                } else {
                    a.setNom_Contacto(rs.getString("alumno_nombre_contacto_emergencia"));
                }
            }
            rs.close();
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public AlumnoMD buscarAlumnoParaReferencia(int idAlumno) {
        AlumnoMD al = new AlumnoMD();
        String sql = "SELECT id_alumno, id_persona\n"
                + "	FROM public.\"Alumnos\" WHERE alumno_activo = true AND "
                + "id_alumno = " + idAlumno + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {

                    al.setId_Alumno(rs.getInt("id_alumno"));
                    PersonaMD p = per.buscarPersonaParaReferencia(rs.getInt("id_persona"));
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
        String sql = "SELECT id_alumno, id_persona\n"
                + "	FROM public.\"Alumnos\" WHERE alumno_activo = 'true';";
        return consultarAlumnos(sql);
    }

    public ArrayList<AlumnoMD> buscarAlumnos(String aguja) {
        String sql = "SELECT id_alumno, a.id_persona, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Alumnos\" a, public.\"Personas\" p \n"
                + "WHERE p.id_persona = a.id_persona AND (\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%"+aguja+"%' OR\n"
                + "	persona_identificacion ILIKE '%"+aguja+"%') "
                + "AND persona_activa = true;";
        return consultarAlumnos(sql);
    }

    private ArrayList<AlumnoMD> consultarAlumnos(String sql) {
        ArrayList<AlumnoMD> almns = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    AlumnoMD al = new AlumnoMD();
                    al.setId_Alumno(rs.getInt("id_alumno"));
                    al.setIdPersona(rs.getInt("id_persona"));
                    al.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    al.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                    al.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    al.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                    al.setIdentificacion(rs.getString("persona_identificacion"));

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
