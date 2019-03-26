/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.estrategiasUnidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.estrategiasAprendizaje.EstrategiasAprendizaje;

import modelo.unidadSilabo.UnidadSilabo;
import modelo.pgConect;

/**
 *
 * @author Andres Ullauri
 */
public class dbEstrategiasUnidad extends EstrategiasUnidad {

    pgConect conecta = new pgConect();

    public dbEstrategiasUnidad() {
    }

    public dbEstrategiasUnidad(EstrategiasAprendizaje idEstrategia, UnidadSilabo idUnidad) {
        super(idEstrategia, idUnidad);
    }

    public dbEstrategiasUnidad(Integer idEstrategiaUnidad) {
        super(idEstrategiaUnidad);
    }

    public List<EstrategiasUnidad> cargarEstrategiasU(int aguja) {
        try {
            List<EstrategiasUnidad> lista = new ArrayList<>();
            String sql = "SELECT \"EstrategiasUnidad\".id_unidad,\"EstrategiasUnidad\".id_estrategia,\"EstrategiasAprendizaje\".descripcion_estrategia,\"UnidadSilabo\".numero_unidad  \n"
                    + "FROM \"EstrategiasUnidad\",\"UnidadSilabo\",\"EstrategiasAprendizaje\"\n"
                    + "WHERE \"EstrategiasUnidad\".id_unidad=\"UnidadSilabo\".id_unidad\n"
                    + "AND \"EstrategiasUnidad\".id_estrategia=\"EstrategiasAprendizaje\".id_estrategia\n"
                    + "AND id_silabo="+aguja+"";
            ResultSet rs = conecta.query(sql);

            while (rs.next()) {
                EstrategiasUnidad eu = new EstrategiasUnidad();

                eu.getIdUnidad().setIdUnidad(rs.getInt(4));
                eu.getIdEstrategia().setIdEstrategia(rs.getInt(2));
                eu.getIdEstrategia().setDescripcionEstrategia(rs.getString(3));

                lista.add(eu);
            }
            rs.close();
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(dbEstrategiasUnidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
