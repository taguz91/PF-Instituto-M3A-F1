package controlador.materia;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import vista.materia.FrmRequisitos;
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
    private final VtnPrincipalCTR ctrPrin;
    private ArrayList<MateriaRequisitoMD> preRequisitos, coRequisitos;
    private int posFila;
    private String tipo, materia;
    private MateriaBD materiabd;

    //Para las tablas 
    private DefaultTableModel mdTblPre, mdTblCo;

    public JDMateriaInfoCTR(VtnPrincipal vtnPrin, ConectarDB conecta, MateriaMD m,
            VtnPrincipalCTR ctrPrin, MateriaBD materiabd) {
        this.vtnPrin = vtnPrin;
        this.conecta = conecta;
        this.materiabd = materiabd;
        this.m = m;
        this.ctrPrin = ctrPrin;
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

        vtnInfo.getTblCoRequisitos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickCoRequisitos();
            }
        });

        vtnInfo.getTblPreRequisitos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickPreRequisitos();
            }
        });

        //ctrPrin.eventoJDCerrar(vtnInfo);
        //Eliminar
        vtnInfo.getBtnEliminar().addActionListener(e -> eliminar());
        vtnInfo.getBtnEditar().addActionListener(e -> editar());
    }

    private void editar() {
        
        try{
             if (posFila >= 0) {
            FrmRequisitos frmreq = new FrmRequisitos();
            VtnRequisitosCTR req = new VtnRequisitosCTR(conecta, ctrPrin, vtnPrin, frmreq, materiabd, m);
            req.iniciar();
            switch (tipo) {
                case "co-requisito":
                    req.editar(coRequisitos.get(posFila));
                    break;
                case "pre-requisito":
                    req.editar(preRequisitos.get(posFila));
                    break;
               
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una materia para continuar");
        }
        }catch(Exception e){
            System.out.println(e);
            
        
        }
       
    }

    private void eliminar() {
        try {
            
        if (posFila >= 0) {

            int r = JOptionPane.showConfirmDialog(vtnPrin, "Esta seguro que desea eliminar " + materia + "\n"
                    + "como " + tipo);
            if (r == JOptionPane.YES_OPTION) {
                switch (tipo) {
                    case "co-requisito":
                        matRe.eliminar(coRequisitos.get(posFila).getId());
                        mdTblCo.removeRow(posFila);
                        break;
                    case "pre-requisito":
                        matRe.eliminar(preRequisitos.get(posFila).getId());
                        mdTblPre.removeRow(posFila);
                        break;
                 
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una materia para continuar");
        }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void clickCoRequisitos() {
        posFila = vtnInfo.getTblCoRequisitos().getSelectedRow();
        if (posFila >= 0) {
            tipo = "co-requisito";
            materia = vtnInfo.getTblCoRequisitos().getValueAt(posFila, 0).toString();
        }
    }

    private void clickPreRequisitos() {
        posFila = vtnInfo.getTblPreRequisitos().getSelectedRow();
        if (posFila >= 0) {
            tipo = "pre-requisito";
            materia = vtnInfo.getTblPreRequisitos().getValueAt(posFila, 0).toString();
        }
    }

    private void llenarTblCoRequisitos() {
        coRequisitos = matRe.buscarCoRequisitosDe(m.getId());
        mdTblCo.setRowCount(0);
        if (coRequisitos != null) {
            coRequisitos.forEach(mt -> {
                Object[] valores = {mt.getMateriaRequisito().getNombre()};
                mdTblCo.addRow(valores);
            });
        }
    }

    private void llenarTblPreRequisitos() {
        preRequisitos = matRe.buscarPreRequisitosDe(m.getId());
        mdTblPre.setRowCount(0);
        if (preRequisitos != null) {
            preRequisitos.forEach(mt -> {
                Object[] valores = {mt.getMateriaRequisito().getNombre()};
                mdTblPre.addRow(valores);
            });
        }
    }

}
