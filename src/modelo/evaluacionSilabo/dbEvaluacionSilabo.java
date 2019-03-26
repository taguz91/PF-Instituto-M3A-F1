package modelo.evaluacionSilabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.*;

public class dbEvaluacionSilabo extends EvaluacionSilabo {

    pgConect con = new pgConect();

    public dbEvaluacionSilabo() {

    }

    public boolean insertarDatos() {

        String nSql = "INSERT INTO public.\"EvaluacionSilabo\"(\n"
                + "	 id_unidad, indicador, id_tipo_actividad, instrumento, valoracion, fecha_envio, fecha_presentacion)\n"
                + "	VALUES (" + this.getIdUnidad() + ",'" + this.getIndicador() + "'," + this.getIdTipoActividad() + ",'" + this.getInstrumento() + "'," + this.getValoracion() + ",'" + this.getFechaEnvio() + "','" + this.getFechaPresentacion() + "')";

        if (con.noQuery(nSql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public List<EvaluacionSilabo> recuperarEvaluaciones(int aguja) {

        try {
            List<EvaluacionSilabo> evaluaciones = new ArrayList<>();
            String sql = "SELECT indicador,id_tipo_actividad,instrumento,valoracion,fecha_envio,fecha_presentacion,numero_unidad\n"
                    + "FROM \"EvaluacionSilabo\",\"Silabo\",\"UnidadSilabo\"\n"
                    + "WHERE \"EvaluacionSilabo\".id_unidad=\"UnidadSilabo\".id_unidad\n"
                    + "AND \"UnidadSilabo\".id_silabo=\"Silabo\".id_silabo\n"
                    + "AND \"Silabo\".id_silabo="+aguja+"";
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                EvaluacionSilabo e = new EvaluacionSilabo();
                e.setIndicador(rs.getString(1));
                e.getIdTipoActividad().setIdTipoActividad(rs.getInt(2));
                e.setInstrumento(rs.getString(3));
                e.setValoracion(rs.getInt(4));
                e.setFechaEnvio(rs.getDate(5).toLocalDate());
                e.setFechaPresentacion(rs.getDate(6).toLocalDate());
                e.getIdUnidad().setIdUnidad(rs.getInt(7));
                //e.getIdUnidad().;

                evaluaciones.add(e);
            }
            rs.close();
            return evaluaciones;

        } catch (SQLException ex) {
            Logger.getLogger(dbEvaluacionSilabo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
