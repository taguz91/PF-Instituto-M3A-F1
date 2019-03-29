package modelo.unidadSilabo;

import java.sql.ResultSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.estrategiasUnidad.EstrategiasUnidad;
import modelo.evaluacionSilabo.EvaluacionSilabo;
import modelo.pgConect;


public class dbUnidadSilabo extends UnidadSilabo {

    pgConect con = new pgConect();

    public dbUnidadSilabo() {
    }

    public dbUnidadSilabo(Integer idUnidad) {
        super(idUnidad);
    }

    public dbUnidadSilabo(Integer idUnidad, int numeroUnidad, String objetivoEspecificoUnidad, String resultadosAprendizajeUnidad, String contenidosUnidad, LocalDate fechaInicioUnidad, LocalDate fechaFinUnidad, int horasDocenciaUnidad, int horasPracticaUnidad, int horasAutonomoUnidad, String estrategiasUnidad) {
        super(idUnidad, numeroUnidad, objetivoEspecificoUnidad, resultadosAprendizajeUnidad, contenidosUnidad, fechaInicioUnidad, fechaFinUnidad, horasDocenciaUnidad, horasPracticaUnidad, horasAutonomoUnidad, estrategiasUnidad);
    }

    /*public boolean InsertarUnidadSilabo() {
        String sql = "INSERT INTO public.\"UnidadSilabo\"(\n"
                + "	id_unidad, numero_unidad, objetivo_especifico_unidad, resultados_aprendizaje_unidad, contenidos_unidad, fecha_inicio_unidad, fecha_fin_unidad, horas_docencia_unidad, horas_practica_unidad, horas_autonomo_unidad)\n"
                + "	VALUES (" + getIdUnidad() + ", " + getNumeroUnidad() + ",'" + getObjetivoEspecificoUnidad() + "', '" + getResultadosAprendizajeUnidad() + "', "
                + "'" + getContenidosUnidad() + "', '" + getFechaInicioUnidad() + "', '" + getFechaFinUnidad() + "'," + getHorasDocenciaUnidad() + " ," + getHorasPracticaUnidad() + ", " + getHorasAutonomoUnidad() + "')";
        if (con.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        }
    }*/

 /* public boolean ModificarUnidadSilabo(int id) {
        String sql = "UPDATE public.\"UnidadSilabo\"\n"
                + "	SET id_unidad=" + getIdUnidad() + ", numero_unidad=" + getNumeroUnidad() + ", objetivo_especifico_unidad='" + getObjetivoEspecificoUnidad() + "', resultados_aprendizaje_unidad= '" + getResultadosAprendizajeUnidad() + "', contenidos_unidad="
                + "'" + getContenidosUnidad() + "', fecha_inicio_unidad= '" + getFechaInicioUnidad() + "', fecha_fin_unidad='" + getFechaFinUnidad() + "', horas_docencia_unidad=" + getHorasDocenciaUnidad() + ", horas_practica_unidad=" + getHorasPracticaUnidad() + ", horas_autonomo_unidad= " + getHorasAutonomoUnidad() + ", estrategias_unidad= '" + getEstrategiasUnidad() + "'\n"
                + "	WHERE id_unidad=" + id + "";
        if (con.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        }
    }*/
    public boolean EliminarUnidadSilabo(int id) {
        String sql = "DELETE FROM public.\"UnidadSilabo\"\n"
                + "	WHERE id_unidad=" + id + "";

        if (con.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        }
    }

    public List<UnidadSilabo> mostrarDatosUnidadSilabo(int aguja) {
        List<UnidadSilabo> lista = new ArrayList<UnidadSilabo>();
        String sql = "SELECT * FROM \"UnidadSilabo\"\n"
                + "WHERE id_silabo="+aguja+"";

        ResultSet rs = con.query(sql);
        System.out.println(sql);
        try {
            while (rs.next()) {
                UnidadSilabo us = new UnidadSilabo();
                us.setIdUnidad(rs.getInt(2));
                us.setNumeroUnidad(rs.getInt(2));
                us.setObjetivoEspecificoUnidad(rs.getString(3));
                us.setResultadosAprendizajeUnidad(rs.getString(4));
                us.setContenidosUnidad(rs.getString(5));
                us.setFechaInicioUnidad(rs.getDate(6).toLocalDate());
                us.setFechaFinUnidad(rs.getDate(7).toLocalDate());
                us.setHorasDocenciaUnidad(rs.getInt(8));
                us.setHorasPracticaUnidad(rs.getInt(9));
                us.setHorasAutonomoUnidad(rs.getInt(10));
                us.setTituloUnidad(rs.getString(12));
                

                lista.add(us);
            }
            rs.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /*public UnidadSilabo retornaEstrategia(String aguja) {

       try {
            UnidadSilabo unidad= null;
            String sql = "SELECT * FROM \"EstrategiasAprendizaje\" WHERE estrategia_descripcion='"+aguja+"'";
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                unidad =new UnidadSilabo();
                
                
            }

            rs.close();
            return unidad;
        } catch (SQLException ex) {
            Logger.getLogger(dbCarreras.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }*/
    public boolean insertarDatos(UnidadSilabo u) {

        String nSql = "INSERT INTO public.\"UnidadSilabo\"(\n"
                + "  numero_unidad, objetivo_especifico_unidad, resultados_aprendizaje_unidad, contenidos_unidad, fecha_inicio_unidad, fecha_fin_unidad, horas_docencia_unidad, horas_practica_unidad, horas_autonomo_unidad, titulo_unidad, id_silabo)\n"
                + "	VALUES ( " + u.getIdUnidad() + ",'" + u.getObjetivoEspecificoUnidad() + "','" + u.getResultadosAprendizajeUnidad()
                + "','" + u.getContenidosUnidad() + "','" + u.getFechaInicioUnidad() + "','" + u.getFechaFinUnidad() + "',"
                + u.getHorasDocenciaUnidad() + "," + u.getHorasPracticaUnidad() + "," + u.getHorasAutonomoUnidad() + ",'" + u.getTituloUnidad() + "'," + "(SELECT MAX(id_silabo) FROM \"Silabo\"))";

        if (con.noQuery(nSql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }

    }

    public boolean insertarDatos2(EstrategiasUnidad eu) {

        String nSql = "INSERT INTO public.\"EstrategiasUnidad\"(\n"
                + "	id_unidad, id_estrategia)\n"
                + "	VALUES ((SELECT MAX(id_unidad) FROM \"UnidadSilabo\")" + "," + eu.getIdEstrategia().getIdEstrategia() + ")";

        if (con.noQuery(nSql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }

    }

    public boolean insertarDatos3(EvaluacionSilabo es) {

        String nSql = "INSERT INTO public.\"EvaluacionSilabo\"(\n"
                + "	 id_unidad, indicador, id_tipo_actividad, instrumento, valoracion, fecha_envio, fecha_presentacion)\n"
                + "	VALUES ( (SELECT MAX(id_unidad) FROM \"UnidadSilabo\")" + ",'" + es.getIndicador() + "'," + es.getIdTipoActividad().getIdTipoActividad()
                + ",'" + es.getInstrumento() + "'," + es.getValoracion() + ",'" + es.getFechaEnvio() + "','" + es.getFechaPresentacion() + "')";

        if (con.noQuery(nSql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
}
