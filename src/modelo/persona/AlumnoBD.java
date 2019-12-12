package modelo.persona;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.alumno.MallaAlumnoMD;
import modelo.materia.MateriaMD;
import utils.CONBD;

public class AlumnoBD extends CONBD {

    private final ConectarDB conecta;

    /**
     *
     * @param conecta
     */
    public AlumnoBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    /**
     * Este método guarda el Alumno en la Base de Datos con todos estos
     * atributos
     *
     * @param a
     * @param s. Se tiene que pasar un objeto de la Clase de SectorEconomicoMD
     * @return Retorna una boolean dependiendo del guardado del Alumno
     */
    public boolean guardarAlumno(AlumnoMD a, SectorEconomicoMD s) {
        String nsql = "INSERT INTO public.\"Alumnos\"(\n"
                + "id_persona, "
                + "id_sec_economico, "
                + "alumno_codigo, "
                + "alumno_tipo_colegio, "
                + "alumno_tipo_bachillerato, "
                + "alumno_anio_graduacion, "
                + "alumno_educacion_superior, "
                + "alumno_titulo_superior, "
                + "alumno_nivel_academico, "
                + "alumno_pension, "
                + "alumno_ocupacion, "
                + "alumno_trabaja, "
                + "alumno_nivel_formacion_padre, "
                + "alumno_nivel_formacion_madre, "
                + "alumno_nombre_contacto_emergencia, "
                + "alumno_parentesco_contacto, "
                + "alumno_numero_contacto, "
                + "alumno_activo) "
                + "VALUES ( " + a.getIdPersona() + ", "
                + s.getId_SecEconomico() + ", '"
                + a.getIdentificacion() + "', '"
                + a.getTipo_Colegio() + "', '"
                + a.getTipo_Bachillerato() + "', "
                + "'" + a.getAnio_graduacion() + "', "
                + a.isEducacion_Superior() + ", '"
                + a.getTitulo_Superior() + "', null, "
                + a.isPension() + ", "
                + "'" + a.getOcupacion() + "', "
                + a.isTrabaja() + ", '"
                + a.getFormacion_Padre() + "', '"
                + a.getFormacion_Madre() + "', "
                + " '" + a.getNom_Contacto() + "', '"
                + a.getParentesco_Contacto() + "', '"
                + a.getContacto_Emergencia() + "', "
                + "true );";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean guardarTitulo(ProfesionMD p) {
        String nsql = "INSERT INTO public.\"Profesiones\"( "
                + "titulo_nombre, "
                + "titulo_abrev, "
                + "titulo_institucion, "
                + "titulo_estado) "
                + " VALUES ( '" + p.getTitulo_nombre() + "', '"
                + p.getTitulo_abreviatura() + "', '"
                + p.getTitulo_institucion() + "', "
                + " true);";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean guardarTituloAuxiliar(ProfesionMD p, int id) {
        String sql = "INSERT INTO public.\"PersonaProfesiones\"(\n"
                + "	 id_persona, id_titulo, persona_profesion_observacion)\n"
                + "	VALUES ( " + id + ", " + p.getId_Titulo() + ", '" + p.getTitulo_observacion() + "');";
        PreparedStatement ps = conecta.getPS(sql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    /**
     * Este método edita a un Alumno de la Base de Datos, pueden ser todos estos
     * atributos
     *
     * @param a
     * @param aguja. Se tiene que pasar un int como la Id de persona
     * @return Retorna un boolean según el resultado de la Edición
     */
    public boolean editarAlumno(AlumnoMD a, int aguja) {
        String nsql = "UPDATE public.\"Alumnos\" SET\n"
                + " id_sec_economico = " + a.getSectorEconomico().getId_SecEconomico()
                + ", alumno_tipo_colegio = '" + a.getTipo_Colegio() + "', "
                + "alumno_tipo_bachillerato = '" + a.getTipo_Bachillerato()
                + "', alumno_anio_graduacion = '" + a.getAnio_graduacion()
                + "', alumno_educacion_superior = " + a.isEducacion_Superior()
                + ", alumno_titulo_superior = '" + a.getTitulo_Superior()
                + "', alumno_nivel_academico = null, "
                + "alumno_pension = " + a.isPension()
                + ", alumno_ocupacion = '" + a.getOcupacion()
                + "', alumno_trabaja = " + a.isTrabaja()
                + ", alumno_nivel_formacion_padre = '" + a.getFormacion_Padre()
                + "', alumno_nivel_formacion_madre = '" + a.getFormacion_Madre()
                + "', alumno_nombre_contacto_emergencia = '" + a.getNom_Contacto()
                + "', alumno_parentesco_contacto = '" + a.getParentesco_Contacto()
                + "', alumno_numero_contacto = '" + a.getContacto_Emergencia() + "' "
                + " WHERE id_persona = " + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    /**
     * Este metodo Elimina al Alumno, bueno basándose en el campo Activo
     *
     * @param a. Se pasa un objeto de la Clase AlumnoMD
     * @param aguja. Se para un int como Id de la Persona
     * @return Retorna un boolean dependiendo de que si la acción es correcta o
     * no
     */
    public boolean eliminarAlumno(AlumnoMD a, int aguja) {
        String nsql = "UPDATE public.\"Alumnos\" SET\n"
                + " alumno_activo = false, alumno_observacion = '" + a.getObservacion()
                + "' WHERE id_persona = " + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            return false;
        }
    }

    public ProfesionMD idProfesion(String nombre) {
        String sql = "SELECT id_titulo FROM public.\"Profesiones\" WHERE titulo_nombre LIKE '" + nombre + "';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        ProfesionMD p = new ProfesionMD();
        try {
            while (rs.next()) {
                p.setId_Titulo(rs.getInt("id_titulo"));
            }
            rs.close();
            ps.getConnection().close();
            return p;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ProfesionMD existeProfesion(int idPersona) {
        String sql = "SELECT id_titulo FROM public.\"PersonaProfesiones\" WHERE id_persona = " + idPersona + ";";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        ProfesionMD p = new ProfesionMD();
        try {
            while (rs.next()) {
                p.setId_Titulo(rs.getInt("id_titulo"));
            }
            rs.close();
            ps.getConnection().close();
            return p;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ProfesionMD capturarProfesiones(int id_persona) {
        String sql = "SELECT p.titulo_nombre, p.titulo_abrev FROM public.\"Profesiones\" p JOIN public.\"PersonaProfesiones\" a USING(id_titulo)\n"
                + "WHERE a.id_persona = " + id_persona + ";";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        ProfesionMD p = new ProfesionMD();
        try {
            while (rs.next()) {
                p.setTitulo_nombre(rs.getString("titulo_nombre"));
                p.setTitulo_abreviatura(rs.getString("titulo_abrev"));
            }
            rs.close();
            ps.getConnection().close();
            return p;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Este método filtra todos los Alumnos activos que están en la Base de
     * Datos
     *
     * @return Retorna una Lista con los Alumnos filtrados
     */
    public List<AlumnoMD> llenarTabla() {
        List<AlumnoMD> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.persona_identificacion,"
                + " p.persona_primer_nombre, p.persona_segundo_nombre,"
                + " p.persona_primer_apellido, p.persona_segundo_apellido,"
                + " p.persona_correo"
                + " FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona)"
                + " WHERE a.alumno_activo = 'true' AND p.persona_activa = 'true'"
                + " ORDER BY p.persona_primer_apellido ASC;";
        System.out.println("------");
        System.out.println(sql);
        System.out.println("------");
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                AlumnoMD m = new AlumnoMD();
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
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al consultar para tabla "
                    + "alumnos: "
                    + ex.getMessage()
            );
            return null;
        }
    }

    /**
     * Este método captura una Persona dependiendo del parametro que se envie
     *
     * @param aguja. Se envia un String como forma de búsqueda para filtrar a
     * esa Persona
     * @return Retorna una Lista con los datos de la Persona filtrada
     */
    public List<PersonaMD> capturarPersona(String aguja) {
        List<PersonaMD> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.persona_identificacion, p.persona_primer_nombre, p.persona_segundo_nombre, p.persona_primer_apellido, p.persona_segundo_apellido, p.persona_correo"
                + " FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona) "
                + "WHERE (p.persona_identificacion LIKE '%" + aguja + "%' OR  p.persona_primer_nombre LIKE '%"
                + aguja + "%' OR p.persona_segundo_nombre LIKE '%" + aguja + "%' OR p.persona_primer_apellido LIKE '%"
                + aguja + "%' OR p.persona_segundo_apellido LIKE '%" + aguja + "%' OR p.persona_correo LIKE '%"
                + aguja + "%') AND p.persona_activa = true AND a.alumno_activo = true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No pudimos realizar la consulta.");
            return null;
        }
    }

    /**
     * Este método filtra una Persona activa pasando una cédula como parámetro
     *
     * @param aguja. Debe pasarse un String como la cédula de la Persona a
     * filtrar
     * @return Retorna un objeto de la Clase PersonaMD con los datos filtrados
     */
    public PersonaMD filtrarPersona(String aguja) {
        //List<PersonaMD> lista = new ArrayList();
        String sql = "SELECT id_persona, persona_identificacion, persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido"
                + " FROM public.\"Personas\" WHERE persona_identificacion LIKE '%" + aguja + "%' AND persona_activa = true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        PersonaMD m = new PersonaMD();
        try {
            while (rs.next()) {
                m.setIdPersona(rs.getInt("id_persona"));
                m.setIdentificacion(rs.getString("persona_identificacion"));
                m.setPrimerNombre(rs.getString("persona_primer_nombre"));
                m.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                m.setPrimerApellido(rs.getString("persona_primer_apellido"));
                m.setSegundoApellido(rs.getString("persona_segundo_apellido"));
            }
            rs.close();
            ps.getConnection().close();
            return m;
        } catch (SQLException ex) {
            System.out.println("No pudimos realizar la consulta. " + ex.getMessage());
            return null;
        }
    }

    /**
     * Este método filtra en un Lista todos los Alumnos eliminados en la Base de
     * Datos
     *
     * @return Retorna una Lista de la Clase PersonaMD con los datos filtrados
     */
    public List<PersonaMD> llenarEliminados() {
        String nsql = "SELECT p.id_persona, p.persona_identificacion,\n"
                + "                p.persona_primer_nombre, p.persona_segundo_nombre,\n"
                + "                p.persona_primer_apellido, p.persona_segundo_apellido,\n"
                + "                p.persona_correo FROM public.\"Personas\" p JOIN public.\"Alumnos\" USING(id_persona)\n"
                + "				WHERE persona_activa = 'false';";
        List<PersonaMD> lista = new ArrayList<>();
        PreparedStatement ps = conecta.getPS(nsql);
        ResultSet rs = conecta.sql(ps);
        PersonaMD m = new PersonaMD();
        try {
            while (rs.next()) {
                m.setIdPersona(rs.getInt("id_persona"));
                m.setIdentificacion(rs.getString("persona_identificacion"));
                m.setPrimerNombre(rs.getString("persona_primer_nombre"));
                m.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                m.setPrimerApellido(rs.getString("persona_primer_apellido"));
                m.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                lista.add(m);
            }
            rs.close();
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No pudimos llenar alumnos eliminados " + ex);
            return null;
        }
    }

    /**
     * Este método extrae los datos personales y datos de Alumno de una Persona
     * en específico
     *
     * @param aguja. Se debe insertar un int como la Id de la Persona
     * @return Retorna un objeto de la Clase AlumnoMD con los datos filtrados
     */
    public AlumnoMD buscarPersona(int aguja) {
        String sql = "SELECT p.id_persona, p.persona_identificacion, p.persona_primer_nombre,\n"
                + "p.persona_segundo_nombre, p.persona_primer_apellido, p.persona_segundo_apellido,\n"
                + "a.id_alumno, a.id_sec_economico, a.alumno_tipo_colegio, a.alumno_tipo_bachillerato,\n"
                + "a.alumno_anio_graduacion, a.alumno_educacion_superior, a.alumno_titulo_superior, a.alumno_nivel_academico,\n"
                + "a.alumno_pension, a.alumno_ocupacion, a.alumno_trabaja, a.alumno_nivel_formacion_padre, a.alumno_nivel_formacion_madre,\n"
                + "a.alumno_numero_contacto, a.alumno_nombre_contacto_emergencia, a.alumno_parentesco_contacto\n"
                + "FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona)\n"
                + "WHERE a.id_persona = " + aguja + " AND a.alumno_activo = true AND p.persona_activa = true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                numero = rs.getInt("id_alumno");
                if (numero == 0) {
                    a.setId_Alumno(0);
                } else {
                    a.setId_Alumno(numero);
                }
                numero = rs.getInt("id_sec_economico");
                if (numero == 0) {
                    sector.setId_SecEconomico(0);
                    a.setSectorEconomico(sector);
                } else {
                    sector.setId_SecEconomico(numero);
                    a.setSectorEconomico(sector);
                }
                palabra = rs.getString("alumno_tipo_colegio");
                if (palabra == null) {
                    a.setTipo_Colegio("|SELECCIONE|");
                } else {
                    a.setTipo_Colegio(rs.getString("alumno_tipo_colegio"));
                }
                palabra = rs.getString("alumno_tipo_bachillerato");
                if (palabra == null) {
                    a.setTipo_Bachillerato("|SELECCIONE|");
                } else {
                    a.setTipo_Bachillerato(palabra);
                }
                palabra = rs.getString("alumno_anio_graduacion");
                if (palabra == null) {
                    a.setAnio_graduacion("1980");
                } else {
                    a.setAnio_graduacion(palabra);
                }
                palabra = String.valueOf(rs.getBoolean("alumno_educacion_superior"));
                if (palabra == null) {
                    a.setEducacion_Superior(false);
                } else {
                    a.setEducacion_Superior(rs.getBoolean("alumno_educacion_superior"));
                }
//                if (rs.wasNull()) {
//                    a.setEducacion_Superior(false);
//                } else {
//                    a.setEducacion_Superior(rs.getBoolean("alumno_educacion_superior"));
//                }
                palabra = rs.getString("alumno_titulo_superior");
                if (palabra == null) {
                    a.setTitulo_Superior(null);
                } else {
                    a.setTitulo_Superior(palabra);
                }
                palabra = rs.getString("alumno_nivel_academico");
                if (palabra == null) {
                    a.setNivel_Academico("|SELECCIONE|");
                } else {
                    a.setNivel_Academico(palabra);
                }
                palabra = String.valueOf(rs.getBoolean("alumno_pension"));
                if (palabra == null) {
                    a.setPension(false);
                } else {
                    a.setPension(rs.getBoolean("alumno_pension"));
                }
//                if (rs.wasNull()) {
//                    a.setPension(false);
//                } else {
//                    a.setPension(rs.getBoolean("alumno_pension"));
//                }
                palabra = rs.getString("alumno_ocupacion");
                if (palabra == null) {
                    a.setOcupacion(null);
                } else {
                    a.setOcupacion(palabra);
                }
                palabra = String.valueOf(rs.getBoolean("alumno_trabaja"));
                if (palabra == null) {
                    a.setTrabaja(false);
                } else {
                    a.setTrabaja(rs.getBoolean("alumno_trabaja"));
                }
//                if (rs.wasNull()) {
//                    a.setTrabaja(false);
//                } else {
//                    a.setTrabaja(rs.getBoolean("alumno_trabaja"));
//                }
                palabra = rs.getString("alumno_nivel_formacion_padre");
                if (palabra == null) {
                    a.setFormacion_Padre("|SELECCIONE|");
                } else {
                    a.setFormacion_Padre(palabra);
                }
                palabra = rs.getString("alumno_nivel_formacion_madre");
                if (palabra == null) {
                    a.setFormacion_Madre("|SELECCIONE|");
                } else {
                    a.setFormacion_Madre(palabra);
                }
                palabra = rs.getString("alumno_numero_contacto");
                if (palabra == null) {
                    a.setContacto_Emergencia(null);
                } else {
                    a.setContacto_Emergencia(palabra);
                }
                palabra = rs.getString("alumno_parentesco_contacto");
                if (palabra == null) {
                    a.setParentesco_Contacto("|SELECCIONE|");
                } else {
                    a.setParentesco_Contacto(palabra);
                }
                palabra = rs.getString("alumno_nombre_contacto_emergencia");
                if (palabra == null) {
                    a.setNom_Contacto(null);
                } else {
                    a.setNom_Contacto(palabra);
                }
            }
            rs.close();
            ps.getConnection().close();
            return a;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar. " + ex.getMessage());
            return null;
        }
    }

    /**
     * Este método extrae los datos personales y datos de Alumno de una Persona
     * en específico
     *
     * @param cedula. Se debe insertar un String como Cédula para filtrar a la
     * Persona
     * @return Retorna un objeto de la Clase AlumnoMD con los datos filtrados
     */
    public AlumnoMD buscarPersonaxCedula(String cedula) {
        String sql = "SELECT p.id_persona, p.persona_identificacion, p.persona_primer_nombre,\n"
                + "p.persona_segundo_nombre, p.persona_primer_apellido, p.persona_segundo_apellido,\n"
                + "a.id_alumno, a.id_sec_economico, a.alumno_tipo_colegio, a.alumno_tipo_bachillerato,\n"
                + "a.alumno_anio_graduacion, a.alumno_educacion_superior, a.alumno_titulo_superior, a.alumno_nivel_academico,\n"
                + "a.alumno_pension, a.alumno_ocupacion, a.alumno_trabaja, a.alumno_nivel_formacion_padre, a.alumno_nivel_formacion_madre,\n"
                + "a.alumno_numero_contacto, a.alumno_nombre_contacto_emergencia, a.alumno_parentesco_contacto\n"
                + "FROM public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona)\n"
                + "WHERE p.persona_identificacion LIKE '" + cedula + "' AND a.alumno_activo = true AND p.persona_activa = true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
            ps.getConnection().close();
            return a;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar alumnos: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Este método filtra a los Alumnos que se han retidado en alguna materia
     *
     * @return Retorna una Lista de la Clase AlumnoMD con los datos filtrados
     */
    public List<AlumnoMD> filtrarRetirados() {
        String nsql = "SELECT DISTINCT p.id_persona, p.persona_identificacion, p.persona_primer_nombre, p.persona_segundo_nombre,\n"
                + "p.persona_primer_apellido, p.persona_segundo_apellido, p.persona_correo, a.id_alumno\n"
                + "FROM ((public.\"Personas\" p JOIN public.\"Alumnos\" a USING(id_persona)) JOIN public.\"AlumnosCarrera\" c USING(id_alumno))\n"
                + "JOIN public.\"MallaAlumno\" m USING(id_almn_carrera)\n"
                + "WHERE malla_almn_estado LIKE 'A' AND a.alumno_activo = 'true' AND p.persona_activa = 'true' "
                + "ORDER BY p.persona_primer_apellido ASC;";
        PreparedStatement ps = conecta.getPS(nsql);
        List<AlumnoMD> lista = new ArrayList();
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                AlumnoMD m = new AlumnoMD();
                m.setIdPersona(rs.getInt("id_persona"));
                m.setIdentificacion(rs.getString("persona_identificacion"));
                m.setPrimerNombre(rs.getString("persona_primer_nombre"));
                m.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                m.setPrimerApellido(rs.getString("persona_primer_apellido"));
                m.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                m.setCorreo(rs.getString("persona_correo"));
                m.setId_Alumno(rs.getInt("id_alumno"));
                lista.add(m);
            }
            ps.getConnection().close();
            rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Este método consulta las Materias en las un Alumno esta Ausentado
     *
     * @param ID. Se debe insertar un int como la Id de la Persona a filtrar
     * @return Retorna una Lista de la clase MallaAlumnoMD con las Materias
     * filtradas
     */
    public List<MallaAlumnoMD> consultarMaterias(int ID) {
        List<MallaAlumnoMD> lista = new ArrayList();
        String nsql = "SELECT m.materia_nombre, d.malla_almn_estado FROM ((public.\"Alumnos\" a JOIN public.\"AlumnosCarrera\" c USING(id_alumno)) \n"
                + "JOIN public.\"MallaAlumno\" d USING(id_almn_carrera)) JOIN public.\"Materias\" m USING(id_materia)\n"
                + "WHERE a.id_persona = " + ID + " AND malla_almn_estado LIKE 'A';";
        PreparedStatement ps = conecta.getPS(nsql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                MallaAlumnoMD m = new MallaAlumnoMD();
                MateriaMD mat = new MateriaMD();
                mat.setNombre(rs.getString("materia_nombre"));
                m.setMateria(mat);
                m.setEstado(rs.getString("malla_almn_estado"));
                lista.add(m);
            }
            rs.close();
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Este método filtra todos los Alumnos activos
     *
     * @return Retorna un ArrayList de la Clase AlumnoMD con los datos filtrados
     */
    public ArrayList<AlumnoMD> cargarAlumnos() {
        String sql = "SELECT id_alumno, id_persona\n"
                + "	FROM public.\"Alumnos\" WHERE alumno_activo = 'true';";
        return consultarAlumnos(sql);
    }

    /**
     * Este método busca a las Personas Activas según el parámetro que se le
     * mande
     *
     * @param aguja. Se debe insertar un String según el atributo de búsqueda
     * para filtrar al Alumno
     * @return Retorna un ArrayList de la Clase AlumnoMD con los datos filtrados
     */
    public ArrayList<AlumnoMD> buscarAlumnos(String aguja) {
        String sql = "SELECT id_alumno, a.id_persona, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Alumnos\" a, public.\"Personas\" p \n"
                + "WHERE p.id_persona = a.id_persona AND (\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%" + aguja + "%' OR "
                + "     persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%" + aguja + "%' OR \n"
                + "	persona_identificacion ILIKE '%" + aguja + "%') "
                + "AND persona_activa = true;";
        return consultarAlumnos(sql);
    }

    /**
     * Este método extrae los datos de la consulta mandada como parámetro
     *
     * @param sql. Debe insertar un String como parámetro con la consulta
     * requerida
     * @return Retorna una Lista de la clase AlumnoMD con los datos extraídos
     */
    private ArrayList<AlumnoMD> consultarAlumnos(String sql) {
        ArrayList<AlumnoMD> almns = new ArrayList();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar alumnos");
            System.out.println(e.getMessage());
        }
        return almns;
    }

    public List<List<String>> getAllTable() {
        return mapearForTable(getBaseQueryTBL(""));
    }

    // Nuevas consultas  
    public List<List<String>> mapearForTable(String sql) {
        PreparedStatement ps = CON.getPSPOOL(sql);
        List<List<String>> alumnos = new ArrayList<>();
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                List<String> cols = new ArrayList<>();

                cols.add(res.getInt(1) + "");
                cols.add(res.getString(2));
                cols.add(res.getString(3));
                cols.add(res.getString(4) == null ? "" : res.getString(4));
                cols.add(res.getString(5) == null ? "" : res.getString(5));
                cols.add(res.getString(6) == null ? "" : res.getString(6));
                cols.add(res.getString(7) == null ? "" : res.getString(7));
                cols.add(res.getString(8) == null ? "" : res.getString(8));
                
                alumnos.add(cols);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos alumnos para tabla.\n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return alumnos;
    }

    private String getBaseQueryTBL(String where) {
        return "SELECT\n"
                + "p.id_persona,\n"
                + "p.persona_identificacion,\n"
                + "p.persona_primer_nombre || ' ' ||\n"
                + "p.persona_segundo_nombre AS nombres,\n"
                + "p.persona_primer_apellido || ' ' ||\n"
                + "p.persona_segundo_apellido AS apellidos,\n"
                + "p.persona_correo,\n"
                + "p.persona_celular, (\n"
                + "  SELECT carrera_nombre\n"
                + "  FROM public.\"AlumnosCarrera\" ac\n"
                + "  JOIN public.\"Carreras\" c USING(id_carrera)\n"
                + "  WHERE ac.id_alumno = a.id_alumno\n"
                + "  ORDER BY id_carrera DESC\n"
                + "  LIMIT 1\n"
                + ") AS carrera, (\n"
                + "  SELECT curso_nombre\n"
                + "  FROM public.\"AlumnoCurso\" ac\n"
                + "  JOIN public.\"Cursos\" c USING(id_curso)\n"
                + "  WHERE ac.id_alumno = a.id_alumno\n"
                + "  ORDER BY curso_nombre DESC\n"
                + "  LIMIT 1\n"
                + ") AS curso\n"
                + "FROM public.\"Personas\" p\n"
                + "JOIN public.\"Alumnos\" a USING(id_persona)\n"
                + "WHERE a.alumno_activo = 'true'\n"
                + "AND p.persona_activa = 'true' "
                + where + " ORDER BY p.persona_primer_apellido,\n"
                + "p.persona_segundo_apellido;";
    }

}
