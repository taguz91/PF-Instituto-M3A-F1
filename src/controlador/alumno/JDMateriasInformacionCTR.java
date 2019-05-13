package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCarreraMD;
import modelo.alumno.MallaAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.estilo.TblEstilo;
import vista.alumno.JDMateriasInformacion;

/**
 *
 * @author Johnny
 */
public class JDMateriasInformacionCTR extends DCTR {

    private final AlumnoCarreraMD alumno;
    private final MallaAlumnoBD mallaAlm;
    private final JDMateriasInformacion jd;
    private final String estado;

    private ArrayList<MallaAlumnoMD> materiasAlmn;
    private DefaultTableModel mdTbl;

    /**
     * Dialogo en la que nos muestra informacion de las materias de un
     * estudiante.
     *
     * @param alumno
     * @param mallaAlm
     * @param estado Estado por el cual cargaran las materias.
     * @param ctrPrin
     */
    public JDMateriasInformacionCTR(AlumnoCarreraMD alumno,
            MallaAlumnoBD mallaAlm, String estado, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.alumno = alumno;
        this.mallaAlm = mallaAlm;
        this.estado = estado;
        this.jd = new JDMateriasInformacion(ctrPrin.getVtnPrin(), false);
        jd.setLocationRelativeTo(ctrPrin.getVtnPrin());

        jd.setVisible(true);
    }

    /**
     * Iniciamos todas las dependencias de la ventana. Eventos. Formato de la
     * tabla.
     */
    public void iniciar() {
        String[] titulo = {"Materia"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        jd.getTblMaterias().setModel(mdTbl);
        TblEstilo.formatoTblConColor(jd.getTblMaterias());

        jd.getLblAlumno().setText(alumno.getAlumno().getPrimerNombre() + " "
                + alumno.getAlumno().getPrimerApellido());

        jd.getTblMaterias().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickTbl();
            }
        });

        ctrPrin.eventoJDCerrar(jd);
    }

    /**
     * Mostramos terceras matriculas
     */
    public void cargarTercerasMatriculas() {
        materiasAlmn = mallaAlm.cargarMallaAlumnoPorEstado(alumno.getId(), "R");
        ArrayList<MallaAlumnoMD> terceras = new ArrayList<>();
        materiasAlmn.forEach(m -> {
            if (m.getMallaNumMatricula() == 2) {
                terceras.add(m);
            }
        });
        materiasAlmn = terceras;
        llenarTbl(materiasAlmn);
    }

    /**
     * Consulta en la base de datos, en la tabla de malla. Las materias con el
     * estado pasado por filtro.
     */
    public void cargarMateriasEstado() {
        materiasAlmn = mallaAlm.cargarMallaAlumnoPorEstado(alumno.getId(), estado);
        llenarTbl(materiasAlmn);
    }

    /**
     * Lenamos la tabla con la informacion obtenida.
     *
     * @param materiasAlmn
     */
    private void llenarTbl(ArrayList<MallaAlumnoMD> materiasAlmn) {
        mdTbl.setRowCount(0);
        if (materiasAlmn != null) {
            materiasAlmn.forEach(m -> {
                Object[] valores = {m.getMateria().getNombre()};
                mdTbl.addRow(valores);
            });
        }
    }

    /**
     * Al hacer click en una de las materias se carga toda la informacion
     * referente a la misma.
     */
    private void clickTbl() {
        int pos = jd.getTblMaterias().getSelectedRow();
        if (pos >= 0) {
            jd.getLblCiclo().setText(materiasAlmn.get(pos).getMallaCiclo() + "");
            jd.getLblNota1().setText(materiasAlmn.get(pos).getNota1() + "");
            jd.getLblNota2().setText(materiasAlmn.get(pos).getNota2() + "");
            jd.getLblNota3().setText(materiasAlmn.get(pos).getNota3() + "");
            jd.getLblNumMatricula().setText(materiasAlmn.get(pos).getMallaNumMatricula() + "");
        }
    }
}
