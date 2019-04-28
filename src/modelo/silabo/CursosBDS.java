
package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.persona.PersonaMD;


public class CursosBDS extends CursoMDS{

    
    
    private ConexionBD conexion;
    
    public CursosBDS(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public CursosBDS(ConexionBD conexion, MateriaMD id_materia, CarreraMD id_carrera, PersonaMD id_persona) {
        super(id_materia, id_carrera, id_persona);
        this.conexion = conexion;
    }
    
     public static List<CursoMD> Consultarcursos(ConexionBD conexion, int id_docente_persona,int id_periodo,String nombre_materia ) {
         List<CursoMD> cursos=new ArrayList<>();
        try {
            PreparedStatement st = conexion.getCon().prepareCall("select distinct c.id_curso,c.curso_nombre from \"Cursos\" c join  \"Docentes\" d on c.id_docente=d.id_docente join \"Personas\" p \n" +
"on d.id_persona=p.id_persona join \"Materias\" m on c.id_materia=m.id_materia\n" +
"where p.id_persona=? and c.id_prd_lectivo=? and m.materia_nombre=?");
            st.setInt(1, id_docente_persona);
            st.setInt(2, id_periodo);
            st.setString(3, nombre_materia);
            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                CursoMD cur=new CursoMD();
                cur.setId(rs.getInt(1));
                cur.setNombre(rs.getString(2));
                
                cursos.add(cur);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  cursos;
     }
      public static List<CursoMDS> ConsultarCursoCarreraDocente(ConexionBD conexion, int id_curso){
         List<CursoMDS> cursos=new ArrayList<>();
        try {
            PreparedStatement st = conexion.getCon().prepareCall("select r.carrera_nombre,m.materia_nombre,m.materia_codigo, c.curso_nombre,\n" +
"p.persona_primer_nombre ,p.persona_primer_apellido\n" +
"as nombre_doc from \"Docentes\" as d join \"Personas\" as p on d.id_persona=p.id_persona join \"Cursos\" as c \n" +
"on c.id_docente=d.id_docente join \"Materias\" as  m on c.id_materia=m.id_materia join \n" +
"\"Carreras\" as r on m.id_carrera=r.id_carrera where c.id_curso=?");
            st.setInt(1, id_curso);
            
            System.out.println(st);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CursoMDS cur=new CursoMDS();
                cur.getId_carrera().setNombre(rs.getString(1));
                cur.getId_materia().setNombre(rs.getString(2));
                cur.getId_materia().setCodigo(rs.getString(3));
                cur.setCurso_nombre(rs.getString(4));
                cur.getId_persona().setPrimerNombre(rs.getString(5));
                cur.getId_persona().setPrimerApellido(rs.getString(6));
                cursos.add(cur);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  cursos;
     }
}
