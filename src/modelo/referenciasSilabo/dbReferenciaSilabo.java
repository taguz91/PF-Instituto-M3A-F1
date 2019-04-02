/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.referenciasSilabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.referenciasSilabo.ReferenciaSilabo;
import modelo.referencias.Referencias;
import modelo.silabo.Silabo;
import modelo.pgConect;

/**
 *
 * @author Andres Ullauri
 */
public class dbReferenciaSilabo extends ReferenciaSilabo {

    pgConect con = new pgConect();

    public dbReferenciaSilabo() {

    }

    public dbReferenciaSilabo(Referencias idReferencia, Silabo idSilabo) {
        super(idReferencia, idSilabo);
    }

    public List<ReferenciaSilabo> recuperarReferencias(int aguja) {

        try {
            List<ReferenciaSilabo> referencias = new ArrayList<>();
            String sql = "SELECT \"ReferenciaSilabo\".id_referencia, \"Referencias\".tipo_referencia, \"Referencias\".descripcion_referencia, \"Referencias\".existe_en_biblioteca FROM \"ReferenciaSilabo\",\"Referencias\"\n"
                    + "WHERE id_silabo="+aguja+"\n"
                    + "AND \"Referencias\".id_referencia=\"ReferenciaSilabo\".id_referencia";
            ResultSet rs = con.query(sql);
            while (rs.next()) {
                ReferenciaSilabo r = new ReferenciaSilabo();
                r.getIdReferencia().setIdReferencia(rs.getInt(1));
                r.getIdReferencia().setTipoReferencia(rs.getString(2));
                r.getIdReferencia().setDescripcionReferencia(rs.getString(3));
                r.getIdReferencia().setExisteEnBiblioteca(rs.getBoolean(4));

                //e.getIdUnidad().;

                referencias.add(r);
            }
            rs.close();
            return referencias;

        } catch (SQLException ex) {
            Logger.getLogger(dbReferenciaSilabo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
