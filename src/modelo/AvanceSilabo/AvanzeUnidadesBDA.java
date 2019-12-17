/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AvanceSilabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexionBD;
import modelo.ConnDBPool;

/**
 *
 * @author Skull
 */
public class AvanzeUnidadesBDA extends AvanzeUnidadesMDA {

    private static final ConnDBPool CON = ConnDBPool.single();

    private ConexionBD conexion;

    public AvanzeUnidadesBDA() {
    }

    public AvanzeUnidadesBDA(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public static List<AvanzeUnidadesMDA> consultarUnidadAvanze(int id_curso, int id_segui) {

        List<AvanzeUnidadesMDA> unidades = new ArrayList<>();
        PreparedStatement st = CON.prepareStatement("SELECT us.id_unidad,us.titulo_unidad,us.contenidos_unidad,uss.id_seguimientosilabo,uss.cumplimiento_porcentaje,\n"
                + "uss.observaciones,ss.fecha_entrga_informe,es_interciclo\n"
                + "FROM public.\"UnidadSilabo\" us Join \"Unidad_Seguimiento\" uss on\n"
                + "us.id_unidad=uss.id_unidad Join \"SeguimientoSilabo\" ss on\n"
                + "					uss.id_seguimientosilabo=ss.id_seguimientosilabo\n"
                + "                    and ss.id_curso=?   and uss.id_seguimientosilabo=? ORDER BY us.numero_unidad");

        try {

            st.setInt(1, id_curso);
            st.setInt(2, id_segui);

            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                AvanzeUnidadesMDA tmp = new AvanzeUnidadesMDA();

                tmp.getUnidad().setIdUnidad(rs.getInt(1));
                tmp.getUnidad().setTituloUnidad(rs.getString(2));
                tmp.getUnidad().setContenidosUnidad(rs.getString(3));
                tmp.getSeguimiento().setId_seguimientoS(rs.getInt(4));
                tmp.setPortecentaje(rs.getInt(5));
                tmp.setObservaciones(rs.getString(6));
                tmp.getSeguimiento().setFecha_entrega_informe(rs.getDate(7).toLocalDate());
                tmp.getSeguimiento().setEsInterciclo(rs.getBoolean(8));

                unidades.add(tmp);
            }

        } catch (SQLException ex) {
            System.out.println("CONSULTA MALLLLLLLLLLLLLLLLLLLL");
        } finally {
            CON.close(st);
        }
        return unidades;
    }

    public boolean insertarAvanzeUnidades(AvanzeUnidadesMDA aus, int idsegui) {
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"Unidad_Seguimiento\"(\n"
                    + "	id_unidad, id_seguimientosilabo, cumplimiento_porcentaje, observaciones)\n"
                    + "	VALUES (?," + idsegui + " , ?, ?);");
            st.setInt(1, aus.getUnidad().getIdUnidad());
            st.setInt(2, aus.getPortecentaje());
            st.setString(3, aus.getObservaciones());
            st.executeUpdate();
            System.out.println(st);
            return true;
        } catch (Exception e) {
            System.out.println("fallo al insertarUnidadesAvance");
            return false;
        }

    }

}
