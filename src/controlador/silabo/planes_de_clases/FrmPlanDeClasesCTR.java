/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planes_de_clases;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import modelo.PlanClases.PlandeClasesMD;
import modelo.PlanClases.RecursosBD;
import modelo.PlanClases.RecursosMD;
import vista.silabos.new_planes_de_clase.FrmPlanDeClase;

/**
 *
 * @author MrRainx
 */
public class FrmPlanDeClasesCTR extends AbstractVTN<FrmPlanDeClase, PlandeClasesMD> {

    private List<RecursosMD> recursos;

    private DefaultTableModel tableRecursosM;

    public FrmPlanDeClasesCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new FrmPlanDeClase();
    }

    @Override
    public void Init() {

        this.recursos = RecursosBD.consultarRecursos();
        this.tableRecursosM = (DefaultTableModel) this.vista.getTblRecursos().getModel();
        cargarRecursos();
        setInformacionUnidad();
        InitEventos();
        super.Init(); //To change body of generated methods, choose Tools | Templates.
    }

    private void InitEventos() {
        this.vista.getTblRecursos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onTableRecursosClicked(e);
            }

        });
    }

    //FACTORIZACION
    private void setInformacionUnidad() {

        String lblTitulo = this.vista.getLblTitulo()
                .getText()
                .replace("{numero}", String.valueOf(this.modelo.getUnidad().getNumeroUnidad()))
                .replace("{titulo}", this.modelo.getUnidad().getTituloUnidad());

        String lblInfo1 = this.vista.getLblInfo1()
                .getText()
                .replace("{fecha_inicio}", this.modelo.getUnidad().getFechaInicioUnidad().toString())
                .replace("{fecha_fin}", this.modelo.getUnidad().getFechaFinUnidad().toString())
                .replace("{duracion}", "");

        String lblInfo2 = this.vista.getLblInfo2()
                .getText()
                .replace("{carrera}", this.modelo.getCurso().getPeriodo().getCarrera().getNombre())
                .replace("{asignatura}", this.modelo.getCurso().getMateria().getNombre())
                .replace("{codigoAsignatura}", this.modelo.getCurso().getMateria().getCodigo());

        String lblInfo3 = this.vista.getLblInfo3()
                .getText()
                .replace("{curso}", this.modelo.getCurso().getNombre())
                .replace("{docente}", this.modelo.getCurso().getDocente().getInfoCompleta());

        this.vista.getLblTitulo().setText(lblTitulo);
        this.vista.getLblInfo1().setText(lblInfo1);
        this.vista.getLblInfo2().setText(lblInfo2);
        this.vista.getLblInfo3().setText(lblInfo3);

        this.vista.getTxtObjetivos().setText(this.modelo.getUnidad().getObjetivoEspecificoUnidad());
        this.vista.getTxtResultadosAprendizaje().setText(this.modelo.getUnidad().getResultadosAprendizajeUnidad());
        this.vista.getTxtContenidos().setText(this.modelo.getUnidad().getContenidosUnidad());

        DefaultListModel<String> listModel = new DefaultListModel<>();

        this.modelo.getUnidad().getEstrategias()
                .stream()
                .map(c -> c.getEstrategia().getDescripcionEstrategia())
                .forEach(listModel::addElement);
        this.vista.getTxtEstrategiasUnidad().setModel(listModel);
    }

    private RecursosMD getRecurso() {
        int row = this.vista.getTblRecursos().getSelectedRow();
        RecursosMD recurso = null;
        if (row != -1) {
            int id = Integer.valueOf(tableRecursosM.getValueAt(row, 0).toString());
            recurso = this.recursos.stream()
                    .filter(item -> item.getId_recurso() == id)
                    .findFirst()
                    .get();
        }

        return recurso;

    }

    private void cargarRecursos() {
        this.tableRecursosM.setRowCount(0);
        this.recursos.stream()
                .forEach(obj -> {

                    boolean check = this.modelo.getRecursos().stream()
                            .map(c -> c.getId_recurso())
                            .collect(Collectors.toList())
                            .contains(obj.getId_recurso());
                    obj.setChecked(check);
                    this.tableRecursosM.addRow(new Object[]{
                        obj.getId_recurso(),
                        obj.getNombre_recursos(),
                        check
                    });
                });
    }

    private void onTableRecursosClicked(MouseEvent e) {
        RecursosMD recurso = getRecurso();
        if (recurso != null) {
            boolean removed = this.modelo.getRecursos()
                    .removeIf(item -> item.getId_recurso() == recurso.getId_recurso());
            if (!removed) {
                this.modelo.getRecursos().add(recurso);
                cargarRecursos();
            }

        }


    }

}
