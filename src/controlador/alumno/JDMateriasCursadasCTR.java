package controlador.alumno;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCarreraMD;
import modelo.alumno.MallaAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.estilo.TblEstilo;
import vista.alumno.JDMateriasCursadas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDMateriasCursadasCTR {

    private final VtnPrincipal vtnPrin;
    private final AlumnoCarreraMD alumno;
    private final MallaAlumnoBD mallaAlm;
    private final JDMateriasCursadas jd;
    private final String estado;

    private ArrayList<MallaAlumnoMD> materiasAlmn;
    DefaultTableModel mdTbl;

    public JDMateriasCursadasCTR(VtnPrincipal vtnPrin, AlumnoCarreraMD alumno, MallaAlumnoBD mallaAlm, String estado) {
        this.vtnPrin = vtnPrin;
        this.alumno = alumno;
        this.mallaAlm = mallaAlm;
        this.estado = estado;
        this.jd = new JDMateriasCursadas(vtnPrin, false);
        jd.setLocationRelativeTo(vtnPrin);
        
        jd.setVisible(true);
    }

    public void iniciar() {
        String[] titulo = {"Materia"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        jd.getTblMaterias().setModel(mdTbl);
        TblEstilo.fomatoTblConColor(jd.getTblMaterias());

        jd.getLblAlumno().setText(alumno.getAlumno().getPrimerNombre() + " "
                + alumno.getAlumno().getPrimerApellido());
        
        cargarMateriasCursadas();

        jd.getTblMaterias().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                clickTbl();
            }
        });
        
        eventoJDCerrar(jd);
        vtnPrin.setEnabled(false);
        
    }

    private void cargarMateriasCursadas() {
        materiasAlmn = mallaAlm.cargarMallaAlumnoPorEstado(alumno.getId(), estado);
        llenarTbl(materiasAlmn);
    }

    private void llenarTbl(ArrayList<MallaAlumnoMD> materiasAlmn) {
        mdTbl.setRowCount(0);
        if (materiasAlmn != null) {
            materiasAlmn.forEach(m -> {
                Object[] valores = {m.getMateria().getNombre()};
                mdTbl.addRow(valores);
            });
        }
    }

    private void clickTbl() {
        int pos = jd.getTblMaterias().getSelectedRow();
        if (pos >= 0) {
            jd.getLblCarrera().setText(alumno.getCarrera().getNombre());
            jd.getLblCiclo().setText(materiasAlmn.get(pos).getMallaCiclo() + "");
            jd.getLblNota1().setText(materiasAlmn.get(pos).getNota1() + "");
            jd.getLblNota2().setText(materiasAlmn.get(pos).getNota2() + "");
            jd.getLblNota3().setText(materiasAlmn.get(pos).getNota3() + "");
            jd.getLblNumMatricula().setText(materiasAlmn.get(pos).getMallaNumMatricula() + "");
        }
    }
    
        //Se desactivara la ventana al abrir un jdialog
    private void eventoJDCerrar(JDialog jd) {
        jd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                vtnPrin.setEnabled(true);
            }
        });
    }


}
