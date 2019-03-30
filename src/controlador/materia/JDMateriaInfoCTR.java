package controlador.materia;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaMD;
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import vista.materia.JDMateriaInfo;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDMateriaInfoCTR {

    private final VtnPrincipal vtnPrin;
    private final JDMateriaInfo vtnInfo;
    private final ConectarDB conecta;
    private final MateriaRequisitoBD matRe;
    private final MateriaMD m;

    //Para las tablas 
    DefaultTableModel mdTblPre, mdTblCo;

    public JDMateriaInfoCTR(VtnPrincipal vtnPrin, ConectarDB conecta, MateriaMD m) {
        this.vtnPrin = vtnPrin;
        this.conecta = conecta;
        this.m = m;

        this.vtnInfo = new JDMateriaInfo(vtnPrin, false);
        this.matRe = new MateriaRequisitoBD(conecta);
        vtnInfo.setLocationRelativeTo(vtnPrin);
    }

    public void iniciar() {
        vtnInfo.getLblMateria().setText(m.getNombre());
        vtnInfo.getLblCarrera().setText(m.getCarrera().getNombre());

        //Inciiamos las tablas 
        String titulo1[] = {"Co-Requisitos"};
        String titulo2[] = {"Pre-Requisitos"};
        String[][] datos1 = {};

        mdTblCo = TblEstilo.modelTblSinEditar(datos1, titulo1);
        mdTblPre = TblEstilo.modelTblSinEditar(datos1, titulo2);

        TblEstilo.formatoTblConColor(vtnInfo.getTblCoRequisitos());
        TblEstilo.formatoTblConColor(vtnInfo.getTblPreRequisitos());

        vtnInfo.getTblCoRequisitos().setModel(mdTblCo);
        vtnInfo.getTblPreRequisitos().setModel(mdTblPre);

        llenarTblCoRequisitos();
        llenarTblPreRequisitos();
        this.vtnInfo.setVisible(true);
    }

    private void llenarTblCoRequisitos() {
        ArrayList<MateriaRequisitoMD> requisitos = matRe.buscarCoRequisitos(m.getId());
        mdTblCo.setRowCount(0);
        if (requisitos != null) {
            requisitos.forEach(mt -> {
                Object[] valores = {mt.getMateriaRequisito().getNombre()};
                mdTblCo.addRow(valores);
            });
        }
    }

    private void llenarTblPreRequisitos() {
        ArrayList<MateriaRequisitoMD> requisitos = matRe.buscarPreRequisitos(m.getId());
        mdTblPre.setRowCount(0);
        if (requisitos != null) {
            requisitos.forEach(mt -> {
                Object[] valores = {mt.getMateriaRequisito().getNombre()};
                mdTblPre.addRow(valores);
            });
        }
    }

}
