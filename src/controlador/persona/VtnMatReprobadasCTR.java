package controlador.persona;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.MallaAlumnoMD;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import vista.persona.VtnAlumno;
import vista.persona.VtnMatRetiradas;

public class VtnMatReprobadasCTR extends DVtnCTR {

    private final VtnMatRetiradas vtnMaterias;
    private final VtnAlumnoCTR vtnAlumnoCtr;
    private final VtnAlumno vtnAlumno;
    private AlumnoBD ALBD;
    private int idPersona;

    public VtnMatReprobadasCTR(VtnAlumnoCTR vtnAlumnoCtr, VtnAlumno vtnAlumno, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnMaterias = new VtnMatRetiradas(ctrPrin.getVtnPrin(), false);
        this.vtnAlumnoCtr = vtnAlumnoCtr;
        this.vtnAlumno = vtnAlumno;
        vtnMaterias.setTitle("Materias Ausentes");
        vtnMaterias.setLocationRelativeTo(null);
        vtnMaterias.setVisible(true);
    }

    public void iniciarVentana() {
        AlumnoMD alumno = VtnAlumnoCTR.mdAlumno;
        Font negrita = new Font("Tahoma", Font.BOLD, 13);
        vtnMaterias.getTxt_Cedula().setFont(negrita);
        vtnMaterias.getTxt_Nombre().setFont(negrita);
        vtnMaterias.getTxt_Cedula().setText(alumno.getIdentificacion());
        vtnMaterias.getTxt_Nombre().setText(alumno.getPrimerNombre() + " " + alumno.getSegundoNombre() + " "
                + alumno.getPrimerApellido() + " " + alumno.getSegundoApellido());
        vtnMaterias.getBtn_Salir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vtnMaterias.dispose();
            }
        });
        llenarMaterias(alumno.getIdPersona());
        iniciarComponentes();
    }

    public void iniciarComponentes() {
        vtnMaterias.getTxt_Cedula().setEnabled(false);
        vtnMaterias.getTxt_Nombre().setEnabled(false);
    }

    public void llenarMaterias(int idPersona) {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnMaterias.getTbl_Materias().getModel();
        for (int i = vtnMaterias.getTbl_Materias().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        ALBD = AlumnoBD.single();
        List<MallaAlumnoMD> malla = ALBD.consultarMaterias(idPersona);
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < malla.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnMaterias.getTbl_Materias().setValueAt(malla.get(i).getMateria().getNombre(), i, 0);
            if (malla.get(i).getEstado().equals("A")) {
                vtnMaterias.getTbl_Materias().setValueAt("AUSENTE", i, 1);
            }
        }
    }
}
