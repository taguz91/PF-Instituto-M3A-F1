/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planes_de_clases;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.EstrategiasMetodologicas.EstrategiasMetodologicasMD;
import modelo.PlanClases.PlandeClasesBD;
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
    private DefaultTableModel tableEstrategiasM;

    private String accion;

    public FrmPlanDeClasesCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new FrmPlanDeClase();
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @Override
    public void Init() {

        this.recursos = RecursosBD.consultarRecursos();
        this.tableRecursosM = (DefaultTableModel) this.vista.getTblRecursos().getModel();
        this.tableEstrategiasM = (DefaultTableModel) this.vista.getTblEstrategias().getModel();
        cargarRecursos();
        setInformacionUnidad();
        cargarEstrategias();
        InitEventos();
        super.Init(); //To change body of generated methods, choose Tools | Templates.
        try {
            this.vista.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmPlanDeClasesCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void InitEventos() {
        this.vista.getTblRecursos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onTableRecursosClicked(e);
            }

        });

        this.vista.getBtnAgregar().addActionListener(this::btnAgregar);
        this.vista.getBtnQuitar().addActionListener(this::btnQuitar);

        this.vista.getBtnCancelar().addActionListener(e -> this.vista.dispose());
        this.vista.getBtnGuardar().addActionListener(this::btnGuardar);

        this.vista.getTxtTrabajoAutonomo().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtTrabajoAutonomo(e);
            }

        });
        this.vista.getTxtObservaciones().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtObservaciones(e);
            }

        });
    }

    //FACTORIZACION
    private void setInformacionUnidad() {

        this.vista.getTxtTrabajoAutonomo().setText(this.modelo.getTrabajoAutonomo());
        this.vista.getTxtObservaciones().setText(this.modelo.getObservaciones());

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

    private void cargarEstrategias() {
        this.tableEstrategiasM.setRowCount(0);
        this.modelo.getEstrategias()
                .forEach(obj -> {
                    String uuid = UUID.randomUUID().toString();
                    obj.setUUID(uuid);
                    this.tableEstrategiasM.addRow(new Object[]{
                        uuid,
                        obj.getNombre_estrategia(),
                        obj.getTipo_estrategias_metodologicas()
                    });
                }
                );
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

    private void btnAgregar(ActionEvent e) {
        String descripcion = this.vista.getTxtDescripcion().getText();

        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE BIEN LA DESCRIPCION");
        } else {

            String tipo = this.vista.getCmbTipo().getSelectedItem().toString();
            EstrategiasMetodologicasMD estrategia = new EstrategiasMetodologicasMD();
            estrategia.setNombre_estrategia(descripcion);
            estrategia.setTipo_estrategias_metodologicas(tipo);

            this.modelo.getEstrategias().add(estrategia);

            this.vista.getTxtDescripcion().setText("");
            this.vista.getCmbTipo().setSelectedIndex(0);

            cargarEstrategias();

        }
    }

    private void btnQuitar(ActionEvent e) {
        int row = this.vista.getTblEstrategias().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UNA FILA DE LA TABLA!!");
        } else {
            String uuid = this.tableEstrategiasM.getValueAt(row, 0).toString();
            this.modelo.getEstrategias()
                    .removeIf(item -> item.getUUID().equals(uuid));

            cargarEstrategias();
        }
    }

    private void txtTrabajoAutonomo(KeyEvent e) {

        String trabajoAutonomo = this.vista.getTxtTrabajoAutonomo().getText();
        this.modelo.setTrabajoAutonomo(trabajoAutonomo);

    }

    private void txtObservaciones(KeyEvent e) {

        String observaciones = this.vista.getTxtObservaciones().getText();
        this.modelo.setObservaciones(observaciones);

    }

    private void btnGuardar(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "SE VA A GUARDAR EL PLAN DE CLASES ESPERE UN MOMENTO");
        if ("add".equals(accion)) {
            boolean guardado = PlandeClasesBD.crear(this.modelo);
            if (guardado) {
                JOptionPane.showMessageDialog(null, "SE HA GUARDADO CORRECTAMENTE");
            }
        }
        if ("edit".equals(accion)) {

            boolean guardado = PlandeClasesBD.editar(this.modelo);
            if (guardado) {
                JOptionPane.showMessageDialog(null, "SE HA GUARDADO CORRECTAMENTE");
            }
        }
    }
}
