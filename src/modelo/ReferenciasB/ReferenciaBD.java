package modelo.ReferenciasB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ConexionBD;

public class ReferenciaBD extends ReferenciasMD {

    private ConectarDB conectar;
    private ConexionBD conexion;

    public ReferenciaBD(int id_referencia, String codigo_referencia, String descripcion_referencia, String tipo_referencia, boolean existe_en_biblioteca, String observaciones, String codigo_ibsn, String numero_de_paginas, String codigo_koha, String codigo_dewey, String area_referencias, String autor2, String autor3) {
        super(id_referencia, codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca, observaciones, codigo_ibsn, numero_de_paginas, codigo_koha, codigo_dewey, area_referencias, autor2, autor3);
    }

    public ReferenciaBD(ConectarDB conectar) {
        this.conectar = conectar;
    }

    public ReferenciaBD() {
    }

    public boolean insertarReferencia() {
        String nsql = "INSERT INTO public.\"Referencias\"(\n"
                + "codigo_referencia,descripcion_referencia,tipo_referencia,existe_en_biblioteca,observaciones,codigo_isbn,numero_de_paginas,codigo_koha,codigo_dewey,area_referencia,autor2,autor3)\n"
                + " values ('" + getCodigo_referencia() + "','" + getDescripcion_referencia() + "','" + getTipo_referencia() + "'," + isExiste_en_biblioteca() + ",'" + getObservaciones()
                + "','" + getCodigo_isbn() + "','" + getNumero_de_paginas() + "','" + getCodigo_koha() + "','" + getCodigo_dewey() + "','" + getArea_referencias() + "','" + getAutor2() + "','" + getAutor3() + "');";
        if (conectar.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public void actualizar(ConexionBD conexion,String id) throws SQLException {
        String editar = getDescripcion_referencia();
        String sql = "update \"Referencias\" set descripcion_referencia='"+getDescripcion_referencia()+"',codigo_referencia='"+getCodigo_referencia()+"',"
                + "existe_en_biblioteca='"+isExiste_en_biblioteca()+"',observaciones='"+getObservaciones()+"',codigo_isbn='"+getCodigo_isbn()+"' , "
                + "numero_de_paginas='"+getNumero_de_paginas()+"',codigo_koha='"+getCodigo_koha()+"',codigo_dewey='"+getCodigo_dewey()+"',"
                + "area_referencia='"+getArea_referencias()+"',autor2='"+getAutor2()+"',autor3='"+getAutor3()+"' where codigo_referencia=?";
        PreparedStatement st = conexion.getCon().prepareStatement(sql);
        st.setString(1, id);
        st.executeUpdate();
        st.close();
        JOptionPane.showMessageDialog(null, "Referencia actuaizada correctamente");

    }

    public List<ReferenciasMD> consultarBibliotecaTabla(ConexionBD conexion) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT  codigo_referencia, descripcion_referencia\n"
                    + "                     FROM public.\"Referencias\"\n"
                    + "                    WHERE tipo_referencia='Base'");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                ReferenciasMD tmp = new ReferenciasMD();
                tmp.setCodigo_referencia(rs.getString(1));
                tmp.setDescripcion_referencia(rs.getString(2));

                referencias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referencias;

    }

    public void eliminar(ConexionBD conexion, String codigo) {
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("DELETE FROM \"Referencias\" where codigo_referencia=?");
            st.setString(1, codigo);

            st.executeUpdate();

            System.out.println(st);
            st.close();
            JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ReferenciasMD> consultarBiblioteca(ConexionBD conexion, String clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_referencia, codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca\n"
                    + "FROM public.\"Referencias\"\n"
                    + "WHERE tipo_referencia='Base'\n"
                    + "AND descripcion_referencia ILIKE '%" + clave + "%'");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReferenciasMD tmp = new ReferenciasMD();
                tmp.setId_referencia(rs.getInt(1));
                tmp.setCodigo_referencia(rs.getString(2));
                tmp.setDescripcion_referencia(rs.getString(3));
                tmp.setTipo_referencia(rs.getString(4));
                tmp.setExiste_en_biblioteca(rs.getBoolean(5));

                referencias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciasMD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referencias;

    }

    public List<ReferenciasMD> obtenerdatos(ConexionBD conexion, String clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("select id_referencia,codigo_referencia,descripcion_referencia,tipo_referencia,existe_en_biblioteca,observaciones,codigo_isbn,\n"
                    + "numero_de_paginas,codigo_koha,codigo_dewey,area_referencia,autor1,autor2,autor3\n"
                    + "from \"Referencias\" where codigo_referencia=?");
            st.setString(1, clave);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReferenciasMD tmp = new ReferenciasMD();
                tmp.setId_referencia(rs.getInt(1));
                tmp.setCodigo_referencia(rs.getString(2));
                tmp.setDescripcion_referencia(rs.getString(3));
                tmp.setTipo_referencia(rs.getString(4));
                tmp.setExiste_en_biblioteca(rs.getBoolean(5));
                tmp.setObservaciones(rs.getString(6));
                tmp.setCodigo_isbn(rs.getString(7));
                tmp.setNumero_de_paginas(rs.getString(8));
                tmp.setCodigo_koha(rs.getString(9));
                tmp.setCodigo_dewey(rs.getString(10));
                tmp.setArea_referencias(rs.getString(11));
                tmp.setAutor2(rs.getString(13));
                tmp.setAutor3(rs.getString(14));
                referencias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciasMD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referencias;

    }

}
