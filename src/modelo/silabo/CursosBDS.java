
package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.curso.CursoMD;


public class CursosBDS extends CursoMD{

    
    
    private ConexionBD conexion;

    public CursosBDS(ConexionBD conexion) {
        this.conexion = conexion;
    }
    
     public static List<CursoMD> Consultarcursos(ConexionBD conexion, String [] clave){
         List<CursoMD> cursos=new ArrayList<>();
        try {
            PreparedStatement st = conexion.getCon().prepareCall("SELECT id_curso,curso_nombre FROM \"Cursos\" "
                    + "where id_materia=(select id_materia from \"Silabo\" where id_silabo=?) \n" +
       "and id_prd_lectivo=(select id_prd_lectivo from \"Silabo\" where id_silabo=?)\n" +
       "and id_docente=?");
            st.setInt(1, Integer.parseInt(clave[0]));
            st.setInt(2, Integer.parseInt(clave[1]));
            st.setInt(3, Integer.parseInt(clave[2]));
            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                CursoMD cur=new CursoMD();
                cur.setId_curso(rs.getInt(2));
                cur.setCurso_nombre(rs.getString(1));
                
                cursos.add(cur);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  cursos;
     }
}
