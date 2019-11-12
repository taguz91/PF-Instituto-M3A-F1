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
import modelo.periodolectivo.PeriodoLectivoMD;

public class ReferenciaBD extends ReferenciasMD {

    private ConectarDB conectar;
    private ConexionBD conexion;

   /* public ReferenciaBD(int id_referencia, String codigo_referencia, String descripcion_referencia,
            String tipo_referencia, boolean existe_en_biblioteca,
            String observaciones, String codigo_ibsn, String numero_de_paginas, String codigo_koha,
            String codigo_dewey, String area_referencias, String autor2, String autor3,String autor1,String ciudad, String año, String editor, String titulo) {
        super(id_referencia, codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca, observaciones, codigo_ibsn, numero_de_paginas, codigo_koha, codigo_dewey, area_referencias, autor2, autor3,editor, ciudad);
    }*/

    public ReferenciaBD(int id_referencia, String codigo_referencia, String descripcion_referencia, String tipo_referencia, boolean existe_en_biblioteca, String observaciones, String codigo_ibsn, String numero_de_paginas, String codigo_koha, String codigo_dewey, String area_referencias, String autor2, String autor3, String titulo, String autor1, String año, String editor, String ciudad) {
        super(id_referencia, codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca, observaciones, codigo_ibsn, numero_de_paginas, codigo_koha, codigo_dewey, area_referencias, autor2, autor3, titulo, autor1, año, editor, ciudad);
    }
    

    public ReferenciaBD(ConectarDB conectar) {
        this.conectar = conectar;
    }

    public ReferenciaBD() {
    }

    public boolean insertarReferencia() {
        String nsql = "INSERT INTO public.\"Referencias\"(\n"
                + "codigo_referencia,descripcion_referencia,tipo_referencia,existe_en_biblioteca,observaciones,codigo_isbn,numero_de_paginas,codigo_koha,codigo_dewey,area_referencia,autor2,autor3, autor1, editor, ciudad, año, titulo)\n"
                + " values ('" + getCodigo_referencia() + "','" + getDescripcion_referencia() + "','" + getTipo_referencia() + "'," + isExiste_en_biblioteca() + ",'" + getObservaciones()
                + "','" + getCodigo_isbn() + "','" + getNumero_de_paginas() + "','" + getCodigo_koha() + "','" + getCodigo_dewey() + "','" + getArea_referencias() + "','" + getAutor2() + "','" + getAutor3() + "','" + getAutor1()+ "','" + getEditor()+ "','" + getCiudad()+ "','" + getAño()+ "','" + getTitulo()+ "');";
        if (conectar.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public void actualizar(ConexionBD conexion,int id) throws SQLException {
        String editar = getDescripcion_referencia();
        String sql = "update \"Referencias\" set descripcion_referencia='"+getDescripcion_referencia()+"',codigo_referencia='"+getCodigo_referencia()+"',"
                + "existe_en_biblioteca='"+isExiste_en_biblioteca()+"',observaciones='"+getObservaciones()+"',codigo_isbn='"+getCodigo_isbn()+"' , "
                + "numero_de_paginas='"+getNumero_de_paginas()+"',codigo_koha='"+getCodigo_koha()+"',codigo_dewey='"+getCodigo_dewey()+"',"
                + "area_referencia='"+getArea_referencias()+"',autor2='"+getAutor2()+"',autor3='"+getAutor3()+"',autor1='"+getAutor1()+"',"
                + "titulo='"+getTitulo()+"',año='"+getAño()+"',ciudad='"+getCiudad()+"',editor='"+getEditor()+"' where id_referencia=?";
        PreparedStatement st = conexion.getCon().prepareStatement(sql);
        st.setInt(1, id);
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
                    + "numero_de_paginas,codigo_koha,codigo_dewey,area_referencia,autor1,autor2,autor3,editor,ciudad,año,titulo\n"
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
                tmp.setEditor(rs.getString(15));
                tmp.setCiudad(rs.getString(16));
                tmp.setAño(rs.getString(17));
                tmp.setTitulo(rs.getString(18));
                tmp.setAutor1(rs.getString(12));
                referencias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciasMD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referencias;

    }
public List<PeriodoLectivoMD> mostrarDatos1(ConexionBD conexion) {
        List<PeriodoLectivoMD> lista = new ArrayList<>();
        try {

            String sql = "select id_prd_lectivo,prd_lectivo_nombre from \"PeriodoLectivo\" where current_date<prd_lectivo_fecha_fin;";
            PreparedStatement st = conexion.getCon().prepareStatement(sql);

            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                PeriodoLectivoMD m = new PeriodoLectivoMD();
                m.setID(rs.getInt(1));
                m.setNombre(rs.getString(2));
                lista.add(m);
            }
            // rs.close();//cerrar conexion cn la bd 

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return lista;
    }
  public List<ReferenciasMD> consultarBporperiodo(ConexionBD conexion, int clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT  distinct r.codigo_referencia,r.descripcion_referencia FROM \"ReferenciaSilabo\" rs join \"Referencias\" r on r.id_referencia=rs.id_referencia \n" +
"join \"Silabo\" s on rs.id_silabo=s.id_silabo join \"PeriodoLectivo\" pl on pl.id_prd_lectivo=s.id_prd_lectivo where r.tipo_referencia='Base' and \n" +
"r.existe_en_biblioteca=true and s.id_prd_lectivo=?");
            st.setInt(1, clave);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReferenciasMD tmp = new ReferenciasMD();
               // tmp.setId_referencia(rs.getInt(1));
                tmp.setCodigo_referencia(rs.getString(1));
                tmp.setDescripcion_referencia(rs.getString(2));
                
                referencias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciasMD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referencias;

    }
  public PeriodoLectivoMD retornaPRDlectivo(ConexionBD conexion, String nombre) {
        int id_periodo = 0;
        PeriodoLectivoMD m = new PeriodoLectivoMD();
        try {

            String sql = "select id_prd_lectivo from \"PeriodoLectivo\" where prd_lectivo_nombre=?;";
            PreparedStatement st = conexion.getCon().prepareStatement(sql);
            st.setString(1, nombre);

            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                m.setID(rs.getInt(1));
                //return m;
            }
            // rs.close();//cerrar conexion cn la bd 

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaBD.class.getName()).log(Level.SEVERE, null, ex);

        }
        return m;
    }
}
