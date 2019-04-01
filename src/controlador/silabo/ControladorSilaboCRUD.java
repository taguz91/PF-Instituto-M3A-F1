/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import com.placeholder.PlaceHolder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.time.LocalDate;
import java.time.ZoneId;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import javax.swing.JList;
import javax.swing.JOptionPane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import modelo.estrategiasAprendizaje.EstrategiasAprendizaje;
import modelo.estrategiasUnidad.EstrategiasUnidad;
import modelo.evaluacionSilabo.EvaluacionSilabo;

import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.referenciasSilabo.ReferenciaSilabo;
import modelo.referencias.Referencias;
import modelo.unidadSilabo.UnidadSilabo;

import modelo.carrera.CarreraMD;
import modelo.estrategiasAprendizaje.dbEstrategiasAprendizaje;
import modelo.estrategiasUnidad.dbEstrategiasUnidad;
import modelo.evaluacionSilabo.dbEvaluacionSilabo;

import modelo.materia.MateriaMD;

import modelo.referenciasSilabo.dbReferenciaSilabo;
import modelo.referencias.dbReferencias;
import modelo.silabo.dbSilabo;
import modelo.tipoActividad.dbTipoActividad;
import modelo.unidadSilabo.dbUnidadSilabo;

import modelo.pgConect;
import modelo.silabo.Silabo;
import modelo.silabo.dbCarreras;
import modelo.silabo.dbMaterias;
import modelo.silabo.dbPeriodoLectivo;
import modelo.usuario.UsuarioBD;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import vista.silabos.frmConfiguracionSilabo;
import vista.silabos.frmGestionSilabo;
import vista.silabos.frmGestionSilabo.CheckListRenderer;

import vista.silabos.frmGestionSilabo.CheckListItem;
import vista.silabos.frmReferencias;
import vista.silabos.frmSilabos;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilaboCRUD {

    private dbSilabo silabo;
    //private opciones_de_impresion impresion = new opciones_de_impresion();

    private UsuarioBD usuario;

    private frmConfiguracionSilabo setup;

    private frmSilabos silabos;

    private frmGestionSilabo gestion;

    private frmReferencias bibliografia;

    private List<UnidadSilabo> unidades;

    private List<EvaluacionSilabo> evaluaciones;

    private List<Referencias> referencias;

    private List<ReferenciaSilabo> referenciasSilabo;

    private List<Silabo> materiasSilabo;

    private List<PeriodoLectivoMD> periodosSilabo;

    PeriodoLectivoMD pl = new dbPeriodoLectivo();

    static int x = 0;

    int fila;

    boolean editar;

    Referencias r1, r2;

    int id_silabo;

    static List<UnidadSilabo> ul = new ArrayList<>();

    boolean vineta;
    
    boolean vineta_;

    DefaultListModel<String> model3 = new DefaultListModel<>();

    private List<EstrategiasUnidad> estrategiasUnidad;

    DefaultListModel model = new DefaultListModel();

    List<EstrategiasAprendizaje> estrategiasAprendizaje;

    JList list = new JList();

    boolean[] check = new boolean[0];
    
    int horas[] = new int[3];

    public ControladorSilaboCRUD() {
    }

    public frmReferencias getBibliografia() {
        return bibliografia;
    }

    public frmConfiguracionSilabo getSetup() {
        return setup;
    }

    public frmGestionSilabo getGestion() {
        return gestion;
    }

    public ControladorSilaboCRUD(dbSilabo silabo, UsuarioBD usuario, frmConfiguracionSilabo setup) {
        this.silabo = silabo;
        this.usuario = usuario;
        this.setup = setup;
        this.gestion = new frmGestionSilabo();
        this.bibliografia = new frmReferencias();
        setup.setVisible(true);
        iniciarControlador();
    }

    public ControladorSilaboCRUD(dbSilabo silabo, UsuarioBD usuario, frmSilabos silabos) {
        this.silabo = silabo;
        this.usuario = usuario;
        this.silabos = silabos;
        this.setup = new frmConfiguracionSilabo();
        this.gestion = new frmGestionSilabo();
        this.bibliografia = new frmReferencias();
        silabos.setVisible(true);
        silabos.getBTNGENERAR().setVisible(false);
        silabos.getCHBPROGRAMA().setVisible(false);
        silabos.getCHBSILABO().setVisible(false);
        silabos.getjLabel1().setVisible(false);
        iniciarControlador();
    }

    public frmSilabos getSilabos() {
        return silabos;
    }

    public void iniciarControlador() {

        if (setup != null) {
            cargarCombo1();

            ActionListener a1 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                    setup.getCmbAsignatura().removeAllItems();
                    cargarCombo2();

                }

            };

            setup.getCmbCarrera().addActionListener(a1);

            setup.getBtnSiguiente().addActionListener(e -> iniciarSilabo(Integer.parseInt(setup.getSpnUnidades().getValue().toString()), setup.getCmbAsignatura().getSelectedItem().toString()));

        }

        if (silabos != null) {

            cargarMateriasSilabo();

            ActionListener as = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {

                    //recuperarInfo(fila);
                    iniciarSilabo(-1, setup.getCmbAsignatura().getSelectedItem().toString());
                    mostrarUnidades(gestion.getCmbUnidad().getSelectedIndex());
                }

            };

            silabos.getTblSilabos().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {

                    fila = silabos.getTblSilabos().rowAtPoint(me.getPoint());
                    seleccionarSilabo();
                    //System.out.println(bibliografia.getTblBiblioteca().getModel().getValueAt(fila, 0).toString());
                }

            });

            silabos.getTblSilabos().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {

                    fila = silabos.getTblSilabos().rowAtPoint(me.getPoint());

                    //System.out.println(bibliografia.getTblBiblioteca().getModel().getValueAt(fila, 0).toString());
                }

            });

            silabos.getBtnImprimir().addActionListener(e
                    -> //imprimirPrograma()
                    hacervisible()
            //imprimirSilabo()
            );
            silabos.getBTNGENERAR().addActionListener(e -> impresion());

            silabos.getBtnEditar().addActionListener(as);
            silabos.getBtnEliminar().addActionListener(d -> eliminarSilabo(id_silabo));
            bibliografia.getBtnAtras().addActionListener(b->bibliografia.setVisible(false));
             bibliografia.getBtnAtras().addActionListener(b->gestion.setVisible(true));

        }

        MouseListener m1 = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                agregarUnidad();
                JOptionPane.showMessageDialog(null, "Nueva unidad agregada");

                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };

        MouseListener m2 = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {

                if (gestion.getLblEliminarUnidad().isEnabled()) {

                    int reply = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta unidad?", "Eliminar", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        eliminarUnidad();
                        JOptionPane.showMessageDialog(null, "Unidad eliminada correctamente");

                    }

                }

                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override

            public void mousePressed(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };

        KeyListener k1 = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent ke) {

                // LocalDate fechaInicio=gestion.getDchFechaInicio().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
                int j = gestion.getCmbUnidad().getSelectedIndex();
                //unidades.get(j).setFechaInicioUnidad(fechaInicio);

                unidades.get(j).setTituloUnidad(gestion.getTxtTitulo().getText());
                unidades.get(j).setObjetivoEspecificoUnidad(gestion.getTxrObjetivos().getText());
                unidades.get(j).setContenidosUnidad(gestion.getTxrContenidos().getText());
                unidades.get(j).setResultadosAprendizajeUnidad(gestion.getTxrResultados().getText());

                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                /*if (validarCampos() && validarEvaluaciones()) {
                    gestion.getBtnSiguiente().setEnabled(true);
                }*/
            }

        };

        gestion.getLstEstrategiasPredeterminadas().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {

                check = new boolean[gestion.getLstEstrategiasPredeterminadas().getModel().getSize()];
                list = gestion.getLstEstrategiasPredeterminadas();
                // Get index of item clicked
                int index = list.locationToIndex(event.getPoint());
                CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
                // Toggle selected state
                item.setSelected(!item.isSelected());
                // Repaint cell
                list.repaint(list.getCellBounds(index, index));

                estrategiasUnidad.removeIf(w -> w.getIdUnidad().getIdUnidad() == gestion.getCmbUnidad().getSelectedIndex());

                for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
                    CheckListItem item2 = (CheckListItem) list.getModel().getElementAt(i);
                    check[i] = item2.isSelected();

                    if (item2.isSelected()) {
                        //estrategiasUnidad.add(new EstrategiasUnidad());
                        estrategiasUnidad.add(new EstrategiasUnidad(new dbEstrategiasAprendizaje().retornaEstrategia(model.get(i).toString()), unidades.get(gestion.getCmbUnidad().getSelectedIndex())));
                        /*List<EstrategiasUnidad> auxiliar = new ArrayList<>();
                        for (int k = 0; k < estrategiasUnidad.size(); k++) {
                            //System.out.println(k);
                            if (estrategiasUnidad.get(k).getIdUnidad().getIdUnidad() == gestion.getCmbUnidad().getSelectedIndex()) {
                                //System.out.println("entro");
                               
                                auxiliar.add(estrategiasUnidad.get(k));
                            }
                        }
                        
                        /*for (int j=0;j<auxiliar.size();j++){
                            System.out.println(auxiliar.get(j).getIdEstrategia().getDescripcionEstrategia());
                        }*/
                    }

                }
            }
        });

        gestion.getLblAgregarEstrategia().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                PlaceHolder holder = new PlaceHolder(gestion.getTxtNuevaEstrategia(), "Ingrese la nueva estrategia...");
                gestion.getTxtNuevaEstrategia().setEnabled(true);
                gestion.getLblGuardarEstrategia().setEnabled(true);

            }

        });

        gestion.getLblGuardarEstrategia().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                dbEstrategiasAprendizaje nueva = new dbEstrategiasAprendizaje();
                nueva.setDescripcionEstrategia(gestion.getTxtNuevaEstrategia().getText());
                estrategiasAprendizaje.add(nueva);
                insertarEstrategia(nueva);
                gestion.getTxtNuevaEstrategia().setText("");
                gestion.getTxtNuevaEstrategia().setEnabled(false);
                gestion.getLblGuardarEstrategia().setEnabled(false);
                cargarEstrategias(0);

            }

        });

        gestion.getDchFechaInicio().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                LocalDate fechaInicio = gestion.getDchFechaInicio().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                int j = gestion.getCmbUnidad().getSelectedIndex();
                if (unidades.get(j).getFechaFinUnidad().isAfter(fechaInicio)) {
                    unidades.get(j).setFechaInicioUnidad(fechaInicio);
                } else {
                    JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin", "Alerta", JOptionPane.WARNING_MESSAGE);
                    gestion.getDchFechaInicio().setDate(Date.from(unidades.get(j).getFechaFinUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));
                }

            }

        });

        gestion.getDchFechaFin().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                LocalDate fechaFin = gestion.getDchFechaFin().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                int j = gestion.getCmbUnidad().getSelectedIndex();
                if (unidades.get(j).getFechaInicioUnidad().isBefore(fechaFin)) {
                    unidades.get(j).setFechaFinUnidad(fechaFin);
                } else {
                    JOptionPane.showMessageDialog(null, "La fecha de fin no puede ser anterior a la fecha de inicio", "Alerta", JOptionPane.WARNING_MESSAGE);

                    gestion.getDchFechaFin().setDate(Date.from(unidades.get(j).getFechaInicioUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));
                }
            }

        });

        gestion.getDchFechaPresentacionAD().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                LocalDate fechaPresentacion = gestion.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaEnvio = gestion.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (fechaPresentacion.isBefore(fechaEnvio)) {

                    JOptionPane.showMessageDialog(null, "La fecha de fin no puede ser anterior a la fecha de inicio", "Alerta", JOptionPane.WARNING_MESSAGE);

                    gestion.getDchFechaPresentacionAD().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                }

            }

        });

        gestion.getDchFechaPresentacionAD().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                LocalDate fechaPresentacion = gestion.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaEnvio = gestion.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (fechaPresentacion.isBefore(fechaEnvio)) {

                    JOptionPane.showMessageDialog(null, "La fecha de fin no puede ser anterior a la fecha de inicio", "Alerta", JOptionPane.WARNING_MESSAGE);

                    gestion.getDchFechaPresentacionAD().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                }

            }

        });

        gestion.getBtnAgregarAD()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        System.out.println("entra");

                        String[] infoE = {"Gestión de Docencia", "Asistido por el Docente"};
                        agregarEvaluacion(infoE, 1);
                        limpiarEvaluacionesAD();

                    }
                }
                );

        gestion.getBtnQuitarAD()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        int fila = gestion.getTblAsistidaDocente().rowAtPoint(me.getPoint());
                        quitarEvaluacion(fila, (DefaultTableModel) gestion.getTblAsistidaDocente().getModel(), 1);

                    }

                }
                );

        gestion.getBtnAgregarAC()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        String[] infoE = {"Gestión de Docencia", "Aprendizaje Colaborativo"};

                        agregarEvaluacion(infoE, 2);
                        limpiarEvaluacionesAC();
                    }
                }
                );

        gestion.getBtnQuitarAC()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        int fila = gestion.getTblAprendizajeColaborativo().rowAtPoint(me.getPoint());
                        quitarEvaluacion(fila, (DefaultTableModel) gestion.getTblAprendizajeColaborativo().getModel(), 2);

                    }

                }
                );

        gestion.getBtnAgregarP()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        String[] infoE = {"Gestión de la Práctica", "Aprendizaje Colaborativo"};

                        agregarEvaluacion(infoE, 3);

                        limpiarEvaluacionesP();

                    }
                }
                );

        gestion.getBtnQuitarP()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        int fila = gestion.getTblPractica().rowAtPoint(me.getPoint());
                        quitarEvaluacion(fila, (DefaultTableModel) gestion.getTblPractica().getModel(), 3);

                    }

                }
                );

        gestion.getBtnAgregarA()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        String[] infoE = {"Gestión de Trabajo Autónomo", "Aprendizaje Colaborativo"};
                        agregarEvaluacion(infoE, 4);

                        limpiarEvaluacionesAD();
                    }
                }
                );

        gestion.getBtnQuitarA()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        int fila = gestion.getTblAutonoma().rowAtPoint(me.getPoint());
                        quitarEvaluacion(fila, (DefaultTableModel) gestion.getTblAutonoma().getModel(), 4);

                    }

                }
                );

        gestion.getSpnHorasDocencia()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent me
                    ) {
                        int total = 0;

                        for (int i = 0; i < unidades.size(); i++) {
                            total = total + unidades.get(i).getHorasDocenciaUnidad();

                        }

                        int limite = horas[0];

                        System.out.println(limite);

                        if (total > limite) {
                            gestion.getSpnHorasDocencia().setValue((int) gestion.getSpnHorasDocencia().getValue() - (total - limite));
                            JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + limite + " horas de gestión docente", "Aviso", JOptionPane.WARNING_MESSAGE);

                        }
                    }

                }
                );

        gestion.getSpnHorasAutonomas()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent me
                    ) {
                        int total = 0;

                        for (int i = 0; i < unidades.size(); i++) {
                            total = total + unidades.get(i).getHorasAutonomoUnidad();

                        }

                        int limite = horas[2];
                        if (total > limite) {
                            gestion.getSpnHorasAutonomas().setValue((int) gestion.getSpnHorasAutonomas().getValue() - (total - limite));
                            JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + limite + " horas de gestión autonoma", "Aviso", JOptionPane.WARNING_MESSAGE);

                        }
                    }

                }
                );

        gestion.getSpnHorasPracticas()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent me
                    ) {
                        int total = 0;

                        for (int i = 0; i < unidades.size(); i++) {
                            total = total + unidades.get(i).getHorasPracticaUnidad();

                        }

                        int limite = horas[1];
                        if (total > limite) {
                            gestion.getSpnHorasPracticas().setValue((int) gestion.getSpnHorasPracticas().getValue() - (total - limite));
                            JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + limite + " horas de gestión practica", "Aviso", JOptionPane.WARNING_MESSAGE);

                        }
                    }

                }
                );

        ActionListener val = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!silaboValido()) {

                    gestion.setVisible(true);
                    bibliografia.setVisible(false);
                    JOptionPane.showMessageDialog(null, "No ha completado de manera correcta todos los campos requeridos", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    cargarBiblioteca();
                }

            }

        };

        gestion.getBtnSiguiente()
                .addActionListener(val);

        ChangeListener cl = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                int j = gestion.getCmbUnidad().getSelectedIndex();
                unidades.get(j).setHorasDocenciaUnidad(Integer.parseInt(gestion.getSpnHorasDocencia().getValue().toString()));
            }

        };

        ChangeListener c2 = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                int j = gestion.getCmbUnidad().getSelectedIndex();
                unidades.get(j).setHorasPracticaUnidad(Integer.parseInt(gestion.getSpnHorasPracticas().getValue().toString()));
            }

        };

        ChangeListener c3 = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                int j = gestion.getCmbUnidad().getSelectedIndex();
                unidades.get(j).setHorasAutonomoUnidad(Integer.parseInt(gestion.getSpnHorasAutonomas().getValue().toString()));
            }

        };

        gestion.getSpnHorasDocencia()
                .addChangeListener(cl);
        gestion.getSpnHorasPracticas()
                .addChangeListener(c2);
        gestion.getSpnHorasAutonomas()
                .addChangeListener(c3);

        bibliografia.getTxtBuscar()
                .addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent ke
                    ) {
                        buscarBiblioteca(bibliografia.getTxtBuscar().getText());
                    }

                }
                );

        bibliografia.getTblBiblioteca()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        fila = bibliografia.getTblBiblioteca().rowAtPoint(me.getPoint());

                        System.out.println(fila);
                        //System.out.println(bibliografia.getTblBiblioteca().getModel().getValueAt(fila, 0).toString());

                    }

                }
                );

        bibliografia.getBtnAgregarBibliografiaBase()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        /*for (int m = 0; m < referencias.size(); m++) {
                    System.out.println(referencias.get(m).getIdReferencia()+"r");
                }
                
                for (int m = 0; m < referenciasSilabo.size(); m++) {
                    System.out.println(referenciasSilabo.get(m).getIdReferencia().getIdReferencia());
                }*/
                        boolean valido = true;
                        for (int i = 0; i < referencias.size(); i++) {

                            if (referencias.get(i).getCodigoReferencia().equals(bibliografia.getTblBiblioteca().getModel().getValueAt(fila, 0).toString())) {

                                for (int m = 0; m < referenciasSilabo.size(); m++) {

                                    if (Objects.equals(referencias.get(i).getIdReferencia(), referenciasSilabo.get(m).getIdReferencia().getIdReferencia())) {
                                        valido = false;
                                        System.out.println("no paso filtro");
                                    }
                                }

                                if (valido) {
                                    referenciasSilabo.add(new ReferenciaSilabo(referencias.get(i)));
                                    agregarBiblioBase();
                                    System.out.println("paso filtro");

                                }

                            }

                        }

                    }

                }
                );

        bibliografia.getTxrBibliografiaComplementaria()
                .addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent ke
                    ) {
                        String a=bibliografia.getTxrBibliografiaComplementaria().getText();
                        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {

                            if (vineta) {

                                a = "• " + bibliografia.getTxrBibliografiaComplementaria().getText() + System.lineSeparator() + "• ";
                                vineta = false;
                            } else {
                                a = bibliografia.getTxrBibliografiaComplementaria().getText() + System.lineSeparator() + "• ";

                            }
                            ke.consume();
                            bibliografia.getTxrBibliografiaComplementaria().setText(a);
                            // Enter was pressed. Your code goes here.
                        }

                        if (bibliografia.getTxrBibliografiaComplementaria().getText().isEmpty()) {
                            vineta = true;
                        }
                    }

                }
                );

        bibliografia.getTxrBibliografiaComplementaria()
                .addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent ke
                    ) {

                        r1 = new Referencias(bibliografia.getTxrBibliografiaComplementaria().getText(), "Complementaria", false);

                        //referenciasSilabo.add();
                    }

                }
                );

        bibliografia.getTxrLinkografia()
                .addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent ke
                    ) {

                        r2 = new Referencias(bibliografia.getTxrLinkografia().getText(), "Linkografia", false);

                        //referenciasSilabo.add();
                    }

                }
                );

        bibliografia.getTxrLinkografia()
                .addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent ke
                    ) {
                        String a=bibliografia.getTxrLinkografia().getText();
                        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {

                            if (vineta_) {

                                a = "• " + bibliografia.getTxrLinkografia().getText() + System.lineSeparator() + "• ";
                                vineta_ = false;
                            } else {
                                a = bibliografia.getTxrLinkografia().getText() + System.lineSeparator() + "• ";

                            }
                            ke.consume();
                            bibliografia.getTxrLinkografia().setText(a);
                            // Enter was pressed. Your code goes here.
                        }

                        if (bibliografia.getTxrLinkografia().getText().isEmpty()) {
                            vineta_ = true;
                        }
                    }

                }
                );

        bibliografia.getBtnQuitarBibliografiaBase()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        quitarBiblioBase();

                    }

                }
                );

        bibliografia.getBtnFinalizar()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me
                    ) {

                        if (editar) {

                            List<Referencias> old = new dbReferencias().retornaReferencia(id_silabo, referenciasSilabo.get(referenciasSilabo.size() - 2).getIdReferencia().getDescripcionReferencia());

                            old.add(new dbReferencias().retornaReferencia(id_silabo, referenciasSilabo.get(referenciasSilabo.size() - 1).getIdReferencia().getDescripcionReferencia()).get(0));

                            referenciasSilabo.removeIf(c -> Objects.equals(c.getIdReferencia().getIdReferencia(), old.get(0).getIdReferencia()) || Objects.equals(c.getIdReferencia().getIdReferencia(), old.get(1).getIdReferencia()));

                            for (int i = 0; i < old.size(); i++) {
                                new dbReferencias().EliminarReferencia(old.get(i).getIdReferencia());
                            }

                            new dbSilabo().EliminarSilabo(id_silabo);

                            silabos.dispose();

                        }
                        guardarSilabo();

                        bibliografia.dispose();
                        gestion.dispose();

                    }

                }
                );

        /*gestion.getBtnSiguiente()
                .addActionListener(al -> cargarBiblioteca());*/
        gestion.getTxtTitulo()
                .addKeyListener(k1);
        gestion.getTxrObjetivos()
                .addKeyListener(k1);
        gestion.getTxrContenidos()
                .addKeyListener(k1);

        gestion.getTxrResultados()
                .addKeyListener(k1);

        gestion.getLblAgregarUnidad()
                .addMouseListener(m1);
        gestion.getLblEliminarUnidad()
                .addMouseListener(m2);

        gestion.getCmbUnidad()
                .addActionListener(e -> mostrarUnidades(gestion.getCmbUnidad().getSelectedIndex()));

        gestion.getCmbUnidad()
                .addActionListener(w -> validar());

        /*silabos.getBTNGENERAR().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
 
                silabos.getBTNGENERAR().setVisible(false);
        silabos.getCHBPROGRAMA().setVisible(false);
        silabos.getCHBSILABO().setVisible(false);
        silabos.getjLabel1().setVisible(false);
               
                
        //bibliografia.getBtnFinalizar().addActionListener(e -> llamar_reporte(id_silabo));
            }
            

        });*/
    }

    public void hacervisible() {
        silabos.getBTNGENERAR().setVisible(true);
        silabos.getCHBPROGRAMA().setVisible(true);
        silabos.getCHBSILABO().setVisible(true);
        silabos.getjLabel1().setVisible(true);
    }

    public void impresion() {
        // opciones_de_impresion impresion=new opciones_de_impresion();

        if (silabos.getCHBPROGRAMA().isSelected() == true) {
            imprimirPrograma();
            silabos.getCHBPROGRAMA().setSelected(false);
        } else if (silabos.getCHBSILABO().isSelected() == true) {
            imprimirSilabo();
            silabos.getCHBSILABO().setSelected(false);
        } else if (silabos.getCHBPROGRAMA().isSelected() == false && silabos.getCHBSILABO().isSelected() == false) {
            JOptionPane.showMessageDialog(null, "DEBE SELECIONAR UNA OPCION");
        }
        //silabos.getCHBPROGRAMA().checked=false;
        //silabos.getCHBSILABO().setSelected(false);
    }

    private void validar() {

        if (gestion.getCmbUnidad().getModel().getSize() > 0) {
            if (unidades.size() == 1) {
                gestion.getLblEliminarUnidad().setEnabled(false);
            } else {
                gestion.getLblEliminarUnidad().setEnabled(true);
            }
        }
    }

    private void cargarCombo1() {

        List<CarreraMD> carreras;

        //System.out.println(usuario.getIdPersona().getIdPersona());
        carreras = new dbCarreras().buscarCarreras(usuario.getPersona().getIdPersona());

        /* for (int i = 0; i < carreras.size(); i++) {
            System.out.println(carreras.get(i).getCarreraNombre());
        }*/
        for (int i = 0; i < carreras.size(); i++) {
            setup.getCmbCarrera().addItem(carreras.get(i).getNombre());
        }

        cargarCombo2();
    }

    private void cargarCombo2() {

        List<MateriaMD> materias;

        int[] aguja = {usuario.getPersona().getIdPersona(), new dbCarreras().retornaCarrera(setup.getCmbCarrera().getSelectedItem().toString()).getId()};

        materias = new dbMaterias().buscarMateria(aguja);

        for (int i = 0; i < materias.size(); i++) {
            setup.getCmbAsignatura().addItem(materias.get(i).getNombre());
        }
    }

    public void cargarEstrategias(int p) {

        //String[] labels = {"a", "b", "c", "d", "e"};
        //JCheckBox[] ch = new JCheckBox[labels.length];
        model = new DefaultListModel();
        estrategiasAprendizaje = new dbEstrategiasAprendizaje().mostrarEstrategias();

        gestion.getLstEstrategiasPredeterminadas().setCellRenderer(new CheckListRenderer());
        gestion.getLstEstrategiasPredeterminadas().setModel(model);
        /*for (int i = 0; i < labels.length; i++) {
            //ch[i]=new JCheckBox("CheckBox"+i);
            model.addElement(new frmPruebas.CheckListItem("CheckBox" + i));
        }*/

        //String []lista_est=new String[estrategiasAprendizaje.size()];
        /* for (int i = 0; i < estrategiasAprendizaje.size(); i++) {
            lista_est[i]=estrategiasAprendizaje.get(i).getDescripcionEstrategia();
        }*/
        for (int i = 0; i < estrategiasAprendizaje.size(); i++) {
            model.addElement(new CheckListItem(estrategiasAprendizaje.get(i).getDescripcionEstrategia()));

        }

        if (p == 0) {

            if (check.length > 0) {
                CheckListItem nuevo = (CheckListItem) list.getModel().getElementAt(0);
                nuevo.setSelected(true);
                for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize() - 1; i++) {
                    CheckListItem item2 = (CheckListItem) list.getModel().getElementAt(i + 1);
                    item2.setSelected(check[i]);
                }

            }
        } else {

            for (int z = 0; z < estrategiasUnidad.size(); z++) {
                System.out.println(estrategiasUnidad.get(z).getIdUnidad().getIdUnidad() + " " + estrategiasUnidad.get(z).getIdEstrategia().getDescripcionEstrategia());
            }

            for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
                CheckListItem item2 = (CheckListItem) list.getModel().getElementAt(i);
                for (int j = 0; j < estrategiasUnidad.size(); j++) {
                    if (estrategiasUnidad.get(j).getIdUnidad().getIdUnidad() == gestion.getCmbUnidad().getSelectedIndex() && model.get(i).toString().equals(estrategiasUnidad.get(j).getIdEstrategia().getDescripcionEstrategia())) {
                        item2.setSelected(true);
                    }
                }

                /*List<EstrategiasUnidad> auxiliar = new ArrayList<>();
            for (int k = 0; k < estrategiasUnidad.size(); k++) {
                if (estrategiasUnidad.get(k).getIdUnidad().getIdUnidad() == gestion.getCmbUnidad().getSelectedIndex()) {
                    auxiliar.add(estrategiasUnidad.get(k));
                }
            }

            for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
                CheckListItem item2 = (CheckListItem) list.getModel().getElementAt(i);
                for (int j = 0; j < auxiliar.size(); j++) {
                    if (model.get(i).toString().equals(auxiliar.get(j).getIdEstrategia().getDescripcionEstrategia())) {
                        item2.setSelected(true);
                    }
                }

            }*/
            }

            // gestion.getLstEstrategiasPredeterminadas().setListData(lista_est);
        }

    }

    public void guardarSilabo() {

        silabo.setEstadoSilabo("Por aprobar");

        if (editar) {
            silabo.setIdMateria(new dbMaterias().retornaMateria(gestion.getTitle()));
        } else {
            silabo.setIdMateria(new dbMaterias().retornaMateria(setup.getCmbAsignatura().getSelectedItem().toString()));
        }

        silabo.insertarDatos();

        insertarUnidades();

        insertarReferencias();

    }

    public void insertarReferencias() {

        if (r1 == null) {
            r1 = new Referencias();
        }

        if (r2 == null) {
            r2 = new Referencias();
        }

        r1.setCodigoReferencia("N/A");
        r2.setCodigoReferencia("N/A");

        referencias.add(r1);
        referencias.add(r2);

        referenciasSilabo.add(new ReferenciaSilabo(r1));
        referenciasSilabo.add(new ReferenciaSilabo(r2));

        for (int j = 0; j < referenciasSilabo.size() - 2; j++) {
            //System.out.println(referenciasSilabo.get(j).getIdReferencia().getIdReferencia());
            silabo.insertarReferencias(referenciasSilabo.get(j));
        }
        new dbReferencias().insertarDatos(r1);
        silabo.insertarReferencias(referenciasSilabo.get(referenciasSilabo.size() - 2));
        new dbReferencias().insertarDatos(r2);
        silabo.insertarReferencias(referenciasSilabo.get(referenciasSilabo.size() - 1));

    }

    public void mostrarUnidades(int unidad) {

        //silabo.getEvaluacionSilaboList().get(unidad).get
        gestion.getTxtTitulo().setText(unidades.get(unidad).getTituloUnidad());
        gestion.getTxrObjetivos().setText(unidades.get(unidad).getObjetivoEspecificoUnidad());
        gestion.getTxrContenidos().setText(unidades.get(unidad).getContenidosUnidad());
        gestion.getTxrResultados().setText(unidades.get(unidad).getResultadosAprendizajeUnidad());
        gestion.getDchFechaInicio().setDate(Date.from(unidades.get(unidad).getFechaInicioUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getDchFechaFin().setDate(Date.from(unidades.get(unidad).getFechaFinUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getSpnHorasDocencia().setValue(unidades.get(unidad).getHorasDocenciaUnidad());
        gestion.getSpnHorasPracticas().setValue(unidades.get(unidad).getHorasPracticaUnidad());
        gestion.getSpnHorasAutonomas().setValue(unidades.get(unidad).getHorasAutonomoUnidad());
        gestion.getDchFechaEnvioAD().setDate(Date.from(pl.getFecha_Inicio().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getDchFechaPresentacionAD().setDate(Date.from(pl.getFecha_Fin().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getDchFechaEnvioAC().setDate(Date.from(pl.getFecha_Inicio().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getDchFechaPresentacionAC().setDate(Date.from(pl.getFecha_Fin().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getDchFechaEnvioP().setDate(Date.from(pl.getFecha_Inicio().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getDchFechaPresentacionP().setDate(Date.from(pl.getFecha_Fin().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getDchFechaEnvioA().setDate(Date.from(pl.getFecha_Inicio().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gestion.getDchFechaPresentacionA().setDate(Date.from(pl.getFecha_Fin().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        //

        if (estrategiasUnidad.size() > 0) {
            cargarEstrategias(1);

        }

        cargarEvaluaciones((DefaultTableModel) gestion.getTblAsistidaDocente().getModel(), 1);

        cargarEvaluaciones((DefaultTableModel) gestion.getTblAprendizajeColaborativo().getModel(), 2);

        cargarEvaluaciones((DefaultTableModel) gestion.getTblPractica().getModel(), 3);

        cargarEvaluaciones((DefaultTableModel) gestion.getTblAutonoma().getModel(), 4);

        //gestion.getCmbUnidad().setSelectedItem(gestion.getCmbUnidad().getModel().getSize() - 1);
    }

    public void iniciarSilabo(int n_unidades, String materia) {

        //silabo.getEvaluacionSilaboList()
        //silabo.setEvaluacionSilaboList(new ArrayList<>());
        editar = false;
        unidades = new ArrayList<>();
        estrategiasAprendizaje = new ArrayList<>();
        estrategiasUnidad = new ArrayList<>();
        evaluaciones = new ArrayList<>();
        referencias = new ArrayList<>();
        referenciasSilabo = new ArrayList<>();

        MateriaMD m = new dbMaterias().retornaMateria(gestion.getTitle());
        
        horas[0]=m.getHorasDocencia();
        horas[1]=m.getHorasPracticas();
        horas[2]=m.getHorasAutoEstudio();
        
        gestion.getCmbUnidad().removeAllItems();

        if (n_unidades == -1) {
            pl = new dbPeriodoLectivo().retornaPeriodoActual(new dbMaterias().retornaMateria(silabos.getTblSilabos().getValueAt(fila, 0).toString()).getCarrera().getId());

            seleccionarSilabo();
            n_unidades = unidades.size();
            System.out.println(n_unidades);
            for (int i = 0; i < n_unidades; i++) {
                gestion.getCmbUnidad().addItem("Unidad " + (i + 1));
            }
            list = gestion.getLstEstrategiasPredeterminadas();
            estrategiasUnidad = new dbEstrategiasUnidad().cargarEstrategiasU(id_silabo);
            evaluaciones = new dbEvaluacionSilabo().recuperarEvaluaciones(id_silabo);
            referenciasSilabo = new dbReferenciaSilabo().recuperarReferencias(id_silabo);
            cargarReferencias(referenciasSilabo);
            editar = true;

        }

        cargarEstrategias(0);

        if (!editar) {
            pl = new dbPeriodoLectivo().retornaPeriodoActual(new dbCarreras().retornaCarrera(setup.getCmbCarrera().getSelectedItem().toString()).getId());

            for (int i = 0; i < n_unidades; i++) {
                agregarUnidad();
            }

        }

        gestion.setTitle(materia);
        gestion.getCmbUnidad().setSelectedIndex(0);
        //agregarUnidad();
    }

    public void agregarUnidad() {

        //silabo.getEvaluacionSilaboList().add(new EvaluacionSilabo());
        unidades.add(new UnidadSilabo(unidades.size(), pl.getFecha_Inicio(), pl.getFecha_Fin()));

        gestion.getCmbUnidad().addItem("Unidad " + unidades.size());

    }

    public void eliminarUnidad() {

        List<EstrategiasUnidad> aux = new ArrayList<>();

        List<UnidadSilabo> tmp2 = new ArrayList<>();

        List<EvaluacionSilabo> tmp = new ArrayList<>();

        int d = 0;
        int v = 0;
        //silabo.getEvaluacionSilaboList().add(new EvaluacionSilabo());
        int indice = gestion.getCmbUnidad().getSelectedIndex();

        estrategiasUnidad.removeIf(n -> n.getIdUnidad().getIdUnidad() == indice);

        evaluaciones.removeIf(m -> m.getIdUnidad().getIdUnidad() == indice);

        for (int k = 0; k < evaluaciones.size(); k++) {
            if (evaluaciones.get(k).getIdUnidad().getIdUnidad() > indice) {
                int n = evaluaciones.get(k).getIdUnidad().getIdUnidad() - 1;
                EvaluacionSilabo e = new EvaluacionSilabo(new UnidadSilabo(n));
                tmp.add(new EvaluacionSilabo(evaluaciones.get(k).getIdEvaluacion(),
                        e.getIdUnidad(), evaluaciones.get(k).getIndicador(), evaluaciones.get(k).getIdTipoActividad(),
                        evaluaciones.get(k).getInstrumento(), evaluaciones.get(k).getValoracion(),
                        evaluaciones.get(k).getFechaEnvio(), evaluaciones.get(k).getFechaPresentacion()));
                v++;
            }

        }

        int w = 0;

        for (int l = evaluaciones.size() - v; l < evaluaciones.size(); l++) {

            evaluaciones.set(l, tmp.get(w));

            w++;

        }

        for (int j = 0; j < estrategiasUnidad.size(); j++) {
            if (estrategiasUnidad.get(j).getIdUnidad().getIdUnidad() > indice) {
                int n = estrategiasUnidad.get(j).getIdUnidad().getIdUnidad() - 1;

                UnidadSilabo u = new UnidadSilabo(n);
                aux.add(new EstrategiasUnidad(estrategiasUnidad.get(j).getIdEstrategia(), u));

                d++;
                //estrategiasUnidad.get(j).getIdUnidad().setIdUnidad(n);

                //estrategiasUnidad.set(j, aux);
                // System.out.println(estrategiasUnidad.get(j).getIdUnidad().getIdUnidad());
            }
        }

        //System.out.println(aux.size());
        //System.out.println((indice+1)-estrategiasUnidad.size());
        int c = 0;

        for (int l = estrategiasUnidad.size() - d; l < estrategiasUnidad.size(); l++) {

            estrategiasUnidad.set(l, aux.get(c));

            c++;

        }

        for (int j = 0; j < estrategiasUnidad.size(); j++) {
            System.out.println(estrategiasUnidad.get(j).getIdUnidad().getIdUnidad() + " " + estrategiasUnidad.get(j).getIdEstrategia().getDescripcionEstrategia());
        }

        /*for (int j=0;j<estrategiasUnidad.size();j++){
            if (estrategiasUnidad.get(j).getIdUnidad().getIdUnidad()>indice){
                System.out.println(estrategiasUnidad.get(j).getIdUnidad().getIdUnidad()+" "+(estrategiasUnidad.get(j).getIdUnidad().getIdUnidad()-1));
                System.out.println(indice);
                System.out.println(estrategiasUnidad.get(j).getIdUnidad().getIdUnidad());
                int n=estrategiasUnidad.get(j).getIdUnidad().getIdUnidad()-1;
                System.out.println(n);
                
                estrategiasUnidad.get(j).getIdUnidad().setIdUnidad(n);
            }
        }*/
        unidades.remove(indice);
        List<String> unidades2 = new ArrayList<>();

        gestion.getCmbUnidad().removeItemAt(indice);

        for (int i = 0; i < unidades.size(); i++) {
            if (unidades.get(i).getIdUnidad() > indice) {

                int n = unidades.get(i).getIdUnidad() - 1;
                System.out.println(n);
                UnidadSilabo u = new UnidadSilabo(n);
                tmp2.add(u);
            }
        }

        int x2 = 0;
        for (int i = indice; i < unidades.size(); i++) {

            unidades.get(i).setIdUnidad(tmp2.get(x2).getIdUnidad());
            x2++;

        }

        for (int z = 0; z < unidades.size(); z++) {
            System.out.println(unidades.get(z).getIdUnidad());
        }

        for (int i = 0; i < gestion.getCmbUnidad().getModel().getSize(); i++) {
            unidades2.add("Unidad " + (i + 1));
        }

        gestion.getCmbUnidad().setModel(new DefaultComboBoxModel(unidades2.toArray()));

        gestion.getCmbUnidad().setSelectedIndex(0);
    }

    public void insertarEstrategia(dbEstrategiasAprendizaje estrategia) {

        estrategia.setDescripcionEstrategia(gestion.getTxtNuevaEstrategia().getText());

        if (estrategia.insertar()) {
            JOptionPane.showMessageDialog(null, "Nueva estrategia guardada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar.");
        }

    }

    public void agregarEvaluacion(String infoE[], int p) {

        switch (p) {
            case 1:

                if (validarLimiteEvaluaciones(Integer.parseInt(gestion.getSpnValoracionAD().getValue().toString()))) {
                    evaluaciones.add(new EvaluacionSilabo(gestion.getTblAsistidaDocente().getRowCount(), unidades.get(gestion.getCmbUnidad().getSelectedIndex()), gestion.getTxtIndicadorAD().getText(), new dbTipoActividad().retornaTipo(infoE), gestion.getTxtInstrumentoAD().getText(), Integer.parseInt(gestion.getSpnValoracionAD().getValue().toString()), gestion.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAsistidaDocente().getModel(), 1);

                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 2:

                if (validarLimiteEvaluaciones(Integer.parseInt(gestion.getSpnValoracionAC().getValue().toString()))) {

                    evaluaciones.add(new EvaluacionSilabo(gestion.getTblAprendizajeColaborativo().getRowCount(), unidades.get(gestion.getCmbUnidad().getSelectedIndex()), gestion.getTxtIndicadorAC().getText(), new dbTipoActividad().retornaTipo(infoE), gestion.getTxtInstrumentoAC().getText(), Integer.parseInt(gestion.getSpnValoracionAC().getValue().toString()), gestion.getDchFechaEnvioAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAprendizajeColaborativo().getModel(), 2);

                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 3:
                if (validarLimiteEvaluaciones(Integer.parseInt(gestion.getSpnValoracionP().getValue().toString()))) {

                    evaluaciones.add(new EvaluacionSilabo(gestion.getTblPractica().getRowCount(), unidades.get(gestion.getCmbUnidad().getSelectedIndex()), gestion.getTxtIndicadorP().getText(), new dbTipoActividad().retornaTipo(infoE), gestion.getTxtInstrumentoP().getText(), Integer.parseInt(gestion.getSpnValoracionP().getValue().toString()), gestion.getDchFechaEnvioP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblPractica().getModel(), 3);

                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 4:
                if (validarLimiteEvaluaciones(Integer.parseInt(gestion.getSpnValoracionA().getValue().toString()))) {

                    evaluaciones.add(new EvaluacionSilabo(gestion.getTblAutonoma().getRowCount(), unidades.get(gestion.getCmbUnidad().getSelectedIndex()), gestion.getTxtIndicadorA().getText(), new dbTipoActividad().retornaTipo(infoE), gestion.getTxtInstrumentoA().getText(), Integer.parseInt(gestion.getSpnValoracionA().getValue().toString()), gestion.getDchFechaEnvioA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAutonoma().getModel(), 4);
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder el valor de 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }
                break;
        }

    }

    public void cargarEvaluaciones(DefaultTableModel modeloTabla, int p) {

        switch (p) {
            case 1:

                for (int j = gestion.getTblAsistidaDocente().getRowCount() - 1; j >= 0; j--) {

                    modeloTabla.removeRow(j);
                }
                break;
            case 2:
                for (int j = gestion.getTblAprendizajeColaborativo().getRowCount() - 1; j >= 0; j--) {

                    modeloTabla.removeRow(j);
                }

                break;
            case 3:
                for (int j = gestion.getTblPractica().getRowCount() - 1; j >= 0; j--) {

                    modeloTabla.removeRow(j);
                }

                break;
            case 4:
                for (int j = gestion.getTblAutonoma().getRowCount() - 1; j >= 0; j--) {

                    modeloTabla.removeRow(j);
                }

                break;
        }

        for (int i = 0; i < evaluaciones.size(); i++) {

            if (evaluaciones.get(i).getIdUnidad().getIdUnidad() == gestion.getCmbUnidad().getSelectedIndex() && evaluaciones.get(i).getIdTipoActividad().getIdTipoActividad() == p) {

                modeloTabla.addRow(new Object[]{
                    evaluaciones.get(i).getIndicador(),
                    evaluaciones.get(i).getInstrumento(),
                    evaluaciones.get(i).getValoracion(),
                    evaluaciones.get(i).getFechaEnvio(),
                    evaluaciones.get(i).getFechaPresentacion()
                });
            }

        }

    }

    public void quitarEvaluacion(int f, DefaultTableModel modeloTabla, int p) {

        evaluaciones.remove(f);
        cargarEvaluaciones(modeloTabla, p);
    }

    public void cargarBiblioteca() {
        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) bibliografia.getTblBiblioteca().getModel();

        referencias = new dbReferencias().mostrarReferencias();

        for (int j = bibliografia.getTblBiblioteca().getRowCount() - 1; j >= 0; j--) {

            modeloTabla.removeRow(j);
        }

        for (int i = 0; i < referencias.size(); i++) {
            modeloTabla.addRow(new Object[]{
                referencias.get(i).getCodigoReferencia(),
                referencias.get(i).getDescripcionReferencia()

            });

        }
    }

    public void buscarBiblioteca(String aguja) {

        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) bibliografia.getTblBiblioteca().getModel();

        List<Referencias> lista = new dbReferencias().buscarReferencias(aguja);

        for (int j = bibliografia.getTblBiblioteca().getRowCount() - 1; j >= 0; j--) {

            modeloTabla.removeRow(j);
        }

        for (int i = 0; i < lista.size(); i++) {
            modeloTabla.addRow(new Object[]{
                lista.get(i).getCodigoReferencia(),
                lista.get(i).getDescripcionReferencia()

            });

        }

    }

    public void agregarBiblioBase() {

        List<String> b = new ArrayList<>();

        for (int i = 0; i < referenciasSilabo.size(); i++) {

            if (referenciasSilabo.get(i).getIdReferencia().getExisteEnBiblioteca()) {
                b.add("• " + referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());
                System.out.println(referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());
                System.out.println("agrego");
            }
        }

        model3 = new DefaultListModel<>();

        b.forEach((s) -> {
            model3.addElement(s);
        });

        bibliografia.getLstBibliografiaBase().setModel(model3);

        for (int a = 0; a < referenciasSilabo.size(); a++) {
            System.out.println("--" + referenciasSilabo.get(a).getIdReferencia().getDescripcionReferencia());
        }
    }

    public void quitarBiblioBase() {

        if (bibliografia.getLstBibliografiaBase().getSelectedIndex() != -1) {

            String s = model3.getElementAt(bibliografia.getLstBibliografiaBase().getSelectedIndex()).substring(2);

            referenciasSilabo.removeIf(p -> p.getIdReferencia().getDescripcionReferencia().equals(s));

            model3.remove(bibliografia.getLstBibliografiaBase().getSelectedIndex());

            System.out.println("quito");
        }

    }

    public void insertarUnidades() {

        for (int i = 0; i < unidades.size(); i++) {
            if (new dbUnidadSilabo().insertarDatos(unidades.get(i))) {

                for (int j = 0; j < estrategiasUnidad.size(); j++) {
                    if (unidades.get(i).getIdUnidad() == estrategiasUnidad.get(j).getIdUnidad().getIdUnidad()) {
                        new dbUnidadSilabo().insertarDatos2(estrategiasUnidad.get(j));
                    }
                }

                for (int k = 0; k < evaluaciones.size(); k++) {
                    if (unidades.get(i).getIdUnidad() == evaluaciones.get(k).getIdUnidad().getIdUnidad()) {
                        new dbUnidadSilabo().insertarDatos3(evaluaciones.get(k));
                    }
                }

                //JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
            } else {
                //
            }

        }

    }

    public void cargarMateriasSilabo() {
        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) silabos.getTblSilabos().getModel();

        materiasSilabo = new dbSilabo().mostrarSilabos(usuario.getPersona().getIdPersona());
        periodosSilabo = new dbPeriodoLectivo().mostrarPeriodosSilabo(usuario.getPersona().getIdPersona());

        for (int j = silabos.getTblSilabos().getModel().getRowCount() - 1; j >= 0; j--) {

            modeloTabla.removeRow(j);
        }

        for (int i = 0; i < materiasSilabo.size(); i++) {
            modeloTabla.addRow(new Object[]{
                materiasSilabo.get(i).getIdMateria().getNombre(),
                periodosSilabo.get(i).getFecha_Inicio() + " / " + periodosSilabo.get(i).getFecha_Fin(),
                materiasSilabo.get(i).getEstadoSilabo(),});

        }

        silabos.getTblSilabos().setModel(modeloTabla);
    }

    public void imprimirSilabo() {
        int id_materia = 0;

        //System.out.println(silabos.getTblSilabos().getModel().getValueAt(fila, 1).toString());
        String[] fechas = silabos.getTblSilabos().getModel().getValueAt(fila, 1).toString().split(" / ");
        for (int i = 0; i < materiasSilabo.size(); i++) {
            if (silabos.getTblSilabos().getModel().getValueAt(fila, 0).equals(materiasSilabo.get(i).getIdMateria().getNombre())
                    && fechas[0].equals(periodosSilabo.get(i).getFecha_Inicio().toString())
                    && fechas[1].equals(periodosSilabo.get(i).getFecha_Fin().toString())) {

                id_materia = materiasSilabo.get(i).getIdMateria().getId();
                gestion.setTitle(materiasSilabo.get(i).getIdMateria().getNombre());

            }
        }
        String id_materia1 = String.valueOf(id_materia);
        int id_silabo = new dbSilabo().retornaSilabo(id_materia).getIdSilabo();
        String id_silabo1 = String.valueOf(id_silabo);
        pgConect con = new pgConect();
        String item = (String) setup.getCmbAsignatura().getSelectedItem();
        //String id_silabo = silabo.cod_sib();
        System.out.println(id_silabo);
        //dbMaterias nuevas = new dbMaterias();
        //Materias materia = nuevas.retornaMateria(item);

        try {

            System.out.println("Imprimiendo.......");
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/silabo2/primera_pag.jasper"));
            Map parametro = new HashMap();
            String par = "47";

            parametro.put("parameter1", id_materia1);
            parametro.put("id_silabo", id_silabo1);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, new pgConect().getCon());
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Sílabo");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "error" + e);
        }
    }

    public void imprimirPrograma() {
        int id_materia = 0;

        //System.out.println(silabos.getTblSilabos().getModel().getValueAt(fila, 1).toString());
        String[] fechas = silabos.getTblSilabos().getModel().getValueAt(fila, 1).toString().split(" / ");
        for (int i = 0; i < materiasSilabo.size(); i++) {
            if (silabos.getTblSilabos().getModel().getValueAt(fila, 0).equals(materiasSilabo.get(i).getIdMateria().getNombre())
                    && fechas[0].equals(periodosSilabo.get(i).getFecha_Inicio().toString())
                    && fechas[1].equals(periodosSilabo.get(i).getFecha_Fin().toString())) {

                id_materia = materiasSilabo.get(i).getIdMateria().getId();
                gestion.setTitle(materiasSilabo.get(i).getIdMateria().getNombre());

            }
        }
        String id_materia1 = String.valueOf(id_materia);
        int id_silabo = new dbSilabo().retornaSilabo(id_materia).getIdSilabo();
        String id_silabo1 = String.valueOf(id_silabo);
        pgConect con = new pgConect();
        String item = (String) setup.getCmbAsignatura().getSelectedItem();
        //String id_silabo = silabo.cod_sib();
        System.out.println(id_silabo);
        //dbMaterias nuevas = new dbMaterias();
        //Materias materia = nuevas.retornaMateria(item);

        try {

            System.out.println("Imprimiendo.......");
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/silabo2/formato2/primerapag.jasper"));
            Map parametro = new HashMap();
            String par = "47";

            parametro.put("parameter1", id_materia1);
            parametro.put("id_silabo", id_silabo1);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, new pgConect().getCon());
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Programa Analítico");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, " error" + e);
        }
    }

    public int recuperarInfo(int f) {

        int id_materia = 0;

        //System.out.println(silabos.getTblSilabos().getModel().getValueAt(fila, 1).toString());
        String[] fechas = silabos.getTblSilabos().getModel().getValueAt(fila, 1).toString().split(" / ");
        for (int i = 0; i < materiasSilabo.size(); i++) {
            if (silabos.getTblSilabos().getModel().getValueAt(fila, 0).equals(materiasSilabo.get(i).getIdMateria().getNombre())
                    && fechas[0].equals(periodosSilabo.get(i).getFecha_Inicio().toString())
                    && fechas[1].equals(periodosSilabo.get(i).getFecha_Fin().toString())) {

                id_materia = materiasSilabo.get(i).getIdMateria().getId();
                gestion.setTitle(materiasSilabo.get(i).getIdMateria().getNombre());

            }
        }

        int id_silabo = new dbSilabo().retornaSilabo(id_materia).getIdSilabo();

        unidades = new dbUnidadSilabo().mostrarDatosUnidadSilabo(new dbSilabo().retornaSilabo(id_materia).getIdSilabo());

        return id_silabo;
    }

    public void cargarReferencias(List<ReferenciaSilabo> referenciasSilabo) {

        List<String> b = new ArrayList<>();

        for (int i = 0; i < referenciasSilabo.size(); i++) {

            if (referenciasSilabo.get(i).getIdReferencia().getTipoReferencia().equals("Base")) {
                b.add("• " + referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());

            } else if (referenciasSilabo.get(i).getIdReferencia().getTipoReferencia().equals("Complementaria")) {

                bibliografia.getTxrBibliografiaComplementaria().setText(referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());

            } else if (referenciasSilabo.get(i).getIdReferencia().getTipoReferencia().equals("Linkografia")) {

                bibliografia.getTxrLinkografia().setText(referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());

            }

        }

        model3 = new DefaultListModel<>();
        b.forEach((s) -> {
            model3.addElement(s);
        });

        bibliografia.getLstBibliografiaBase().setModel(model3);

    }

    public void eliminarSilabo(int aguja) {

        int reply = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            if (new dbSilabo().EliminarSilabo(aguja)) {
                JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");

            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
        } else {

        }

        cargarMateriasSilabo();
    }

    public void seleccionarSilabo() {
        id_silabo = recuperarInfo(fila);
    }

    public void limpiarEvaluacionesAD() {

        gestion.getTxtInstrumentoAD().setText(null);
        gestion.getTxtIndicadorAD().setText(null);
        gestion.getSpnValoracionAD().setValue(0);

    }

    public void limpiarEvaluacionesAC() {

        gestion.getTxtInstrumentoAC().setText(null);
        gestion.getTxtIndicadorAC().setText(null);
        gestion.getSpnValoracionAC().setValue(0);

    }

    public void limpiarEvaluacionesP() {

        gestion.getTxtInstrumentoP().setText(null);
        gestion.getTxtIndicadorP().setText(null);
        gestion.getSpnValoracionP().setValue(0);

    }

    public void limpiarEvaluacionesA() {

        gestion.getTxtInstrumentoA().setText(null);
        gestion.getTxtIndicadorA().setText(null);
        gestion.getSpnValoracionA().setValue(0);

    }

    public boolean validarEvaluaciones() {

        int total = 0;

        for (int i = 0; i < evaluaciones.size(); i++) {

            total += evaluaciones.get(i).getValoracion();

        }

        if (total == 60) {
            return true;
        } else {
            return false;
        }

    }

    public boolean validarLimiteEvaluaciones(int valor) {

        int total = 0;

        for (int i = 0; i < evaluaciones.size(); i++) {

            total += evaluaciones.get(i).getValoracion();

        }

        if ((total + valor) <= 60) {
            return true;
        } else {
            return false;
        }

    }

    public boolean validarCampos() {

        boolean control = true;

        int contador = 0;

        for (int i = 0; i < unidades.size(); i++) {

            if (unidades.get(i).getTituloUnidad() == null) {
                control = false;
            }

            if (unidades.get(i).getObjetivoEspecificoUnidad() == null) {
                control = false;
            }

            if (unidades.get(i).getResultadosAprendizajeUnidad() == null) {
                control = false;
            }

            if (unidades.get(i).getContenidosUnidad() == null) {
                control = false;
            }

            for (int j = 0; j < estrategiasUnidad.size(); j++) {
                if (estrategiasUnidad.get(j).getIdUnidad().getIdUnidad() == unidades.get(i).getIdUnidad()) {
                    contador++;
                }
            }

            if (contador == 0) {
                control = false;
            }

        }

        return control;

    }

    public boolean validarHoras() {

        int docencia = 0;
        int practica = 0;
        int autonomo = 0;

        MateriaMD m = new dbMaterias().retornaMateria(gestion.getTitle());

        for (int i = 0; i < unidades.size(); i++) {

            docencia = docencia + unidades.get(i).getHorasDocenciaUnidad();

            practica = practica + unidades.get(i).getHorasPracticaUnidad();

            autonomo = autonomo + unidades.get(i).getHorasAutonomoUnidad();

        }

        /*System.out.println(m.getHorasDocencia() + "-" + docencia);
        System.out.println(m.getHorasPracticas() + "-" + practica);
        System.out.println(m.getHorasAutoEstudio() + "-" + autonomo);*/
        if (docencia == horas[0] && practica == horas[1] && autonomo == horas[2]) {

            return true;

        } else {
            return false;
        }
    }

    

    

   

    public boolean silaboValido() {

        if (validarCampos() && validarHoras() && validarEvaluaciones()) {
            return true;
        } else {
            return false;
        }

    }
    
     /*public void buscarSilabo(String aguja, ) {

        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) silabos.getTblSilabos().getModel();

        materiasSilabo = new dbSilabo().mostrarSilabos(usuario.getPersona().getIdPersona());
        periodosSilabo = new dbPeriodoLectivo().mostrarPeriodosSilabo(usuario.getPersona().getIdPersona());

        for (int j = silabos.getTblSilabos().getModel().getRowCount() - 1; j >= 0; j--) {

            modeloTabla.removeRow(j);
        }

        for (int i = 0; i < materiasSilabo.size(); i++) {
            modeloTabla.addRow(new Object[]{
                materiasSilabo.get(i).getIdMateria().getNombre(),
                periodosSilabo.get(i).getFecha_Inicio() + " / " + periodosSilabo.get(i).getFecha_Fin(),
                materiasSilabo.get(i).getEstadoSilabo(),});

        }

        silabos.getTblSilabos().setModel(modeloTabla);

    }*/

}
