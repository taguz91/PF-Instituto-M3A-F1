package modelo.ReferenciasB;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.periodolectivo.PeriodoLectivoMD;
import utils.CONBD;
import utils.M;

public class ReferenciaBD extends CONBD {
    
    
    private static ReferenciaBD RBD; 
    
    public static ReferenciaBD single(){
        if (RBD == null) {
            RBD = new ReferenciaBD();
        }
        return RBD;
    }

    public boolean insertarReferencia(ReferenciasMD re) {
        String nsql = "INSERT INTO public.\"Referencias\"( "
                + "codigo_referencia,"
                + "descripcion_referencia,"
                + "tipo_referencia,"
                + "existe_en_biblioteca,"
                + "observaciones,"
                + "codigo_isbn,"
                + "numero_de_paginas,"
                + "codigo_koha,"
                + "codigo_dewey,"
                + "area_referencia,"
                + "autor2,"
                + "autor3, "
                + "autor1, "
                + "editor, "
                + "ciudad, "
                + "año, "
                + "titulo)\n"
                + " values ("
                + "'" + re.getCodigo_referencia() + "',"
                + "'" + re.getDescripcion_referencia() + "',"
                + "'" + re.getTipo_referencia() + "',"
                + "" + re.isExiste_en_biblioteca() + ","
                + "'" + re.getObservaciones() + "',"
                + "'" + re.getCodigo_isbn() + "',"
                + "'" + re.getNumero_de_paginas() + "',"
                + "'" + re.getCodigo_koha() + "',"
                + "'" + re.getCodigo_dewey() + "',"
                + "'" + re.getArea_referencias() + "',"
                + "'" + re.getAutor2() + "',"
                + "'" + re.getAutor3() + "',"
                + "'" + re.getAutor1() + "',"
                + "'" + re.getEditor() + "',"
                + "'" + re.getCiudad() + "',"
                + "'" + re.getAño() + "',"
                + "'" + re.getTitulo() + "');";
        return CON.executeNoSQL(nsql);
    }

    public void actualizar(ReferenciasMD re) {
        String sql = "UPDATE \"Referencias\" SET "
                + "descripcion_referencia='" + re.getDescripcion_referencia() + "',"
                + "codigo_referencia='" + re.getCodigo_referencia() + "',"
                + "existe_en_biblioteca='" + re.isExiste_en_biblioteca() + "',"
                + "observaciones='" + re.getObservaciones() + "',"
                + "codigo_isbn='" + re.getCodigo_isbn() + "' , "
                + "numero_de_paginas='" + re.getNumero_de_paginas() + "',"
                + "codigo_koha='" + re.getCodigo_koha() + "',"
                + "codigo_dewey='" + re.getCodigo_dewey() + "',"
                + "area_referencia='" + re.getArea_referencias() + "',"
                + "autor2='" + re.getAutor2() + "',"
                + "autor3='" + re.getAutor3() + "',"
                + "autor1='" + re.getAutor1() + "',"
                + "titulo='" + re.getTitulo() + "',"
                + "año='" + re.getAño() + "',"
                + "ciudad='" + re.getCiudad() + "',"
                + "editor='" + re.getEditor() + "' "
                + "WHERE id_referencia = ?";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, re.getId_referencia());
            JOptionPane.showMessageDialog(null, "Referencia actuaizada correctamente");
            CON.noSQLPOOL(ps);
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar referencias");
        }
    }

    public List<ReferenciasMD> consultarBibliotecaTabla() {
        List<ReferenciasMD> referencias = new ArrayList<>();
        String sql = "SELECT codigo_referencia, "
                + " descripcion_referencia "
                + "FROM public.\"Referencias\" "
                + "WHERE tipo_referencia='Base' ";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReferenciasMD re = new ReferenciasMD();
                re.setCodigo_referencia(rs.getString(1));
                re.setDescripcion_referencia(rs.getString(2));
                referencias.add(re);
            }
        } catch (SQLException ex) {
            M.errorMsg(
                    "Error al consultar biblioteca para tabla. \n"
                    + ex.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return referencias;

    }

    public void eliminar(String codigo) {
        try {
            String sql = "DELETE FROM \"Referencias\" where codigo_referencia=?";
            PreparedStatement st = CON.getPSPOOL(sql);
            st.setString(1, codigo);
            CON.executeNoSQL(sql);
            JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
        } catch (SQLException ex) {
            M.errorMsg(
                    "Error al eliminar por codigo. \n"
                    + ex.getMessage()
            );
        }
    }

    public List<ReferenciasMD> consultarBiblioteca(String clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        String sql = "SELECT id_referencia, "
                + "codigo_referencia, "
                + "descripcion_referencia, "
                + "tipo_referencia, "
                + "existe_en_biblioteca "
                + "FROM public.\"Referencias\"\n"
                + "WHERE tipo_referencia='Base'\n"
                + "AND descripcion_referencia ILIKE '%" + clave + "%'";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReferenciasMD re = new ReferenciasMD();
                re.setId_referencia(rs.getInt(1));
                re.setCodigo_referencia(rs.getString(2));
                re.setDescripcion_referencia(rs.getString(3));
                re.setTipo_referencia(rs.getString(4));
                re.setExiste_en_biblioteca(rs.getBoolean(5));

                referencias.add(re);
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar por clave. \n" + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return referencias;

    }

    public List<ReferenciasMD> obtenerdatos(String clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        String sql = "select id_referencia,codigo_referencia,descripcion_referencia,tipo_referencia,existe_en_biblioteca,observaciones,codigo_isbn,\n"
                + "numero_de_paginas,codigo_koha,codigo_dewey,area_referencia,autor1,autor2,autor3,editor,ciudad,año,titulo\n"
                + "from \"Referencias\" where codigo_referencia=?";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, clave);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReferenciasMD re = new ReferenciasMD();
                re.setId_referencia(rs.getInt(1));
                re.setCodigo_referencia(rs.getString(2));
                re.setDescripcion_referencia(rs.getString(3));
                re.setTipo_referencia(rs.getString(4));
                re.setExiste_en_biblioteca(rs.getBoolean(5));
                re.setObservaciones(rs.getString(6));
                re.setCodigo_isbn(rs.getString(7));
                re.setNumero_de_paginas(rs.getString(8));
                re.setCodigo_koha(rs.getString(9));
                re.setCodigo_dewey(rs.getString(10));
                re.setArea_referencias(rs.getString(11));
                re.setAutor2(rs.getString(13));
                re.setAutor3(rs.getString(14));
                re.setEditor(rs.getString(15));
                re.setCiudad(rs.getString(16));
                re.setAño(rs.getString(17));
                re.setTitulo(rs.getString(18));
                re.setAutor1(rs.getString(12));
                referencias.add(re);
            }

        } catch (SQLException ex) {
            M.errorMsg("Error al cargar todo. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return referencias;
    }

    public List<PeriodoLectivoMD> mostrarDatos1() {
        List<PeriodoLectivoMD> lista = new ArrayList<>();
        String sql = "SELECT id_prd_lectivo,"
                + "prd_lectivo_nombre "
                + "FROM \"PeriodoLectivo\" "
                + "WHERE current_date < prd_lectivo_fecha_fin;";
        PreparedStatement st = CON.getPSPOOL(sql);
        try {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PeriodoLectivoMD m = new PeriodoLectivoMD();
                m.setID(rs.getInt(1));
                m.setNombre(rs.getString(2));
                lista.add(m);
            }
        } catch (SQLException ex) {
            M.errorMsg("Error al mostradar datos 1 " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(st);
        }
        return lista;
    }

    public List<ReferenciasMD> getBasePorPeriodo(int clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        String sql = "SELECT  distinct r.codigo_referencia,"
                + "r.descripcion_referencia "
                + "FROM \"ReferenciaSilabo\" rs "
                + "JOIN \"Referencias\" r ON r.id_referencia=rs.id_referencia \n"
                + "join \"Silabo\" s ON rs.id_silabo=s.id_silabo "
                + "JOIN \"PeriodoLectivo\" pl ON pl.id_prd_lectivo = s.id_prd_lectivo "
                + "WHERE r.tipo_referencia = 'Base' AND \n"
                + "r.existe_en_biblioteca=true AND s.id_prd_lectivo=?";
        PreparedStatement st = CON.getPSPOOL(sql);
        try {
            st.setInt(1, clave);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ReferenciasMD tmp = new ReferenciasMD();
                tmp.setCodigo_referencia(rs.getString(1));
                tmp.setDescripcion_referencia(rs.getString(2));

                referencias.add(tmp);
            }
        } catch (SQLException ex) {
            M.errorMsg("Error al consultar por periodo. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(st);
        }
        return referencias;
    }

    public PeriodoLectivoMD retornaPRDlectivo(String nombre) {
        PeriodoLectivoMD m = new PeriodoLectivoMD();
        String sql = "select id_prd_lectivo "
                + "from \"PeriodoLectivo\" "
                + "where prd_lectivo_nombre=?;";
            PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m.setID(rs.getInt(1));
            }
        } catch (SQLException ex) {
            M.errorMsg("Error al consultar el ID periodo. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return m;
    }
    
}
