package controlador.silabo.frm;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import modelo.estilo.TblEstilo;
import modelo.referencias.NEWReferenciasBD;
import modelo.referencias.ReferenciasMD;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.silabo.NEWReferenciaSilaboBD;
import modelo.silabo.SilaboMD;
import vista.silabos.NEWFrmSilabo;
import vista.silabos.frmReferencias;

/**
 *
 * @author gus
 */
public class FRMReferenciaSilaboCTR extends DCTR {

    // Formulario del silabo 
    private final NEWFrmSilabo FRM_SILABO;

    // El formulario de referencias  
    private final frmReferencias FRM_REF = new frmReferencias();
    // Modelo del silabo que agregamos bibliografia  
    private final SilaboMD silabo;
    // Conexiones a la base de datos
    private final NEWReferenciasBD REBD = NEWReferenciasBD.single();
    private final NEWReferenciaSilaboBD RSBD = NEWReferenciaSilaboBD.single();
    // Para la biblioteca  
    private List<ReferenciasMD> todasReferenciasBiblioteca;
    private List<ReferenciasMD> referenciasBuscadas;
    private List<ReferenciasMD> todasReferenciasVirtual;
    // Se inicia las linkografias y complementaria 
    private final ReferenciasMD linkografia;
    private final ReferenciasMD complementaria;
    // Para guardar nuestras referencias 
    private List<ReferenciaSilaboMD> referenciasSilabo;
    // Para validar si existe o no la estrategia  
    private boolean existeReferencia = false;
    // Modelo de la lista  
    private final DefaultListModel mdlRS = new DefaultListModel();

    // Modelos de la tabla que usaremos  
    private final DefaultTableModel mdTblBiblio = TblEstilo.modelTblSinEditar(new String[]{
        "Código", "Referencia"
    });

    public FRMReferenciaSilaboCTR(
            VtnPrincipalCTR ctrPrin,
            SilaboMD silabo,
            NEWFrmSilabo FRM_SILABO
    ) {
        super(ctrPrin);
        this.silabo = silabo;
        linkografia = new ReferenciasMD(
                silabo.getID() + "",
                "",
                "Linkografia"
        );
        complementaria = new ReferenciasMD(
                silabo.getID() + "",
                "",
                "Complementaria"
        );
        this.FRM_SILABO = FRM_SILABO;
    }

    public void iniciar() {
        FRM_SILABO.setVisible(false);
        iniciarVentana();
        cargarReferenciasSilabo();
        ctrPrin.agregarVtn(FRM_REF);
    }

    private void cargarReferenciasSilabo() {
        referenciasSilabo = RSBD.getBySilabo(silabo.getID());
        if (referenciasSilabo.size() > 0) {
            // Si ya existe la linkografia y complementaria la obtenemos en nuestro modelos
            referenciasSilabo.forEach(r -> {
                if (r.getIdReferencia().getTipoReferencia().equals("Linkografia")) {
                    linkografia.setDescripcionReferencia(
                            r.getIdReferencia().getDescripcionReferencia()
                    );
                    linkografia.setIdReferencia(r.getIdReferencia().getIdReferencia());
                    r.setIdReferencia(linkografia);
                }
                if (r.getIdReferencia().getTipoReferencia().equals("Complementaria")) {
                    complementaria.setDescripcionReferencia(
                            r.getIdReferencia().getDescripcionReferencia()
                    );
                    complementaria.setIdReferencia(r.getIdReferencia().getIdReferencia());
                    r.setIdReferencia(complementaria);
                }
                if (r.getIdReferencia().getTipoReferencia().equals("Base")) {
                    mdlRS.addElement("• " + r.getIdReferencia().getDescripcionReferencia());
                }
            });
        } else {
            ReferenciaSilaboMD rl = new ReferenciaSilaboMD(linkografia, silabo);
            ReferenciaSilaboMD rc = new ReferenciaSilaboMD(complementaria, silabo);
            referenciasSilabo.add(rl);
            referenciasSilabo.add(rc);
        }
        FRM_REF.getTxrLinkografia().setText(
                linkografia.getDescripcionReferencia()
        );
        FRM_REF.getTxrBibliografiaComplementaria().setText(
                complementaria.getDescripcionReferencia()
        );
    }

    private void iniciarVentana() {
        FRM_REF.setTitle(
                silabo.getMateria().getNombre() + " | "
                + silabo.getPeriodo().getNombre()
        );
        iniciarAcciones();
        iniciarEventos();
        // Cargamos los datos  
        cargarBiblioteca();
        iniciarTbl();
        iniciarLista();
        // Eventos al cerrar la ventana  
        FRM_REF.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                FRM_SILABO.setVisible(true);
            }
        });
    }

    private void iniciarAcciones() {
        FRM_REF.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String a = FRM_REF.getTxtBuscar().getText()
                        .trim().toLowerCase();
                if (FRM_REF.getCmbBiblioteca().getSelectedIndex() == 0) {
                    buscarBiblioteca(a);
                } else {
                    buscarVirtual(a);
                }
            }
        });
        // Evento del combo  
        FRM_REF.getCmbBiblioteca().addActionListener(e -> {
            if (FRM_REF.getCmbBiblioteca().getSelectedIndex() == 0) {
                cargarTodosBiblioteca();
            } else {
                cargarTodosVirtual();
            }
        });
        // Evento de los botones  
        FRM_REF.getBtnAgregarBibliografiaBase().addActionListener(e -> agregarBiblioBase());
        FRM_REF.getBtnQuitarBibliografiaBase().addActionListener(e -> quitarBiblioBase());
        FRM_REF.getBtnFinalizar().addActionListener(e -> guardar());
        FRM_REF.getBtnAtras().addActionListener(e -> {
            FRM_SILABO.setVisible(true);
        });
        FRM_REF.getBtnCancelar().addActionListener(e -> {
            FRM_SILABO.setVisible(true);
        });
    }

    private void iniciarEventos() {
        FRM_REF.getTxrBibliografiaComplementaria().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                complementaria.setDescripcionReferencia(
                        FRM_REF.getTxrBibliografiaComplementaria().getText().trim()
                );
            }
        });

        FRM_REF.getTxrLinkografia().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                linkografia.setDescripcionReferencia(
                        FRM_REF.getTxrLinkografia().getText().trim()
                );
            }
        });
    }

    private void cargarBiblioteca() {
        todasReferenciasBiblioteca = REBD.getAllBiblioteca();
        todasReferenciasVirtual = REBD.getAllVirtual();
    }

    private void iniciarLista() {
        FRM_REF.getLstBibliografiaBase().setModel(mdlRS);
    }

    private void iniciarTbl() {
        FRM_REF.getTblBiblioteca().setModel(mdTblBiblio);
        mdTblBiblio.setRowCount(0);
        cargarTodosBiblioteca();
    }

    private void buscarBiblioteca(String aguja) {
        if (aguja.length() > 0) {
            mdTblBiblio.setRowCount(0);
            referenciasBuscadas = null;
            referenciasBuscadas = new ArrayList<>();
            todasReferenciasBiblioteca.forEach(r -> {
                if (r.getDescripcionReferencia()
                        .toLowerCase().contains(aguja)
                        || r.getCodigoReferencia()
                                .toLowerCase().contains(aguja)) {
                    referenciasBuscadas.add(r);
                    Object[] row = {
                        r.getCodigoReferencia(),
                        r.getDescripcionReferencia()
                    };
                    mdTblBiblio.addRow(row);
                }
            });
        } else {
            cargarTodosBiblioteca();
        }
    }

    private void buscarVirtual(String aguja) {
        if (aguja.length() > 0) {
            mdTblBiblio.setRowCount(0);
            referenciasBuscadas = null;
            referenciasBuscadas = new ArrayList<>();
            todasReferenciasVirtual.forEach(r -> {
                if (r.getDescripcionReferencia()
                        .toLowerCase().contains(aguja)
                        || r.getCodigoReferencia()
                                .toLowerCase().contains(aguja)) {
                    referenciasBuscadas.add(r);
                    Object[] row = {
                        r.getCodigoReferencia(),
                        r.getDescripcionReferencia()
                    };
                    mdTblBiblio.addRow(row);
                }
            });
        } else {
            cargarTodosVirtual();
        }
    }

    private void cargarTodosBiblioteca() {
        mdTblBiblio.setRowCount(0);
        todasReferenciasBiblioteca.forEach(r -> {
            Object[] row = {
                r.getCodigoReferencia(),
                r.getDescripcionReferencia()
            };
            mdTblBiblio.addRow(row);
        });
    }

    private void cargarTodosVirtual() {
        mdTblBiblio.setRowCount(0);
        todasReferenciasVirtual.forEach(r -> {
            Object[] row = {
                r.getCodigoReferencia(),
                r.getDescripcionReferencia()
            };
            mdTblBiblio.addRow(row);
        });
    }

    private void agregarBiblioBase() {
        int s = FRM_REF.getTblBiblioteca().getSelectedRow();
        if (s >= 0) {
            llenarListaReferencias(referenciasBuscadas.get(s));
        }
    }

    private void quitarBiblioBase() {
        int s = FRM_REF.getLstBibliografiaBase().getSelectedIndex();
        if (s >= 0) {
            if (referenciasSilabo.get(s).getIdReferenciaSilabo() > 0) {
                RSBD.eliminar(referenciasSilabo.get(s).getIdReferenciaSilabo());
            }
            referenciasSilabo.remove(s);
            llenarListaReferencias();
        }
    }

    private void llenarListaReferencias() {
        mdlRS.removeAllElements();
        referenciasSilabo.forEach(r -> {
            if (r.getIdReferencia().getTipoReferencia().equals("Base")) {
                mdlRS.addElement("• " + r.getIdReferencia().getDescripcionReferencia());
            }
        });
    }

    private void llenarListaReferencias(ReferenciasMD nuevaReferencia) {
        existeReferencia = false;
        mdlRS.removeAllElements();
        referenciasSilabo.forEach(r -> {
            if (r.getIdReferencia().getDescripcionReferencia()
                    .equals(nuevaReferencia.getDescripcionReferencia())) {
                existeReferencia = true;
            }
            if (r.getIdReferencia().getTipoReferencia().equals("Base")) {
                mdlRS.addElement("• " + r.getIdReferencia().getDescripcionReferencia());
            }
        });

        if (!existeReferencia) {
            ReferenciaSilaboMD rs = new ReferenciaSilaboMD();
            rs.setIdSilabo(silabo);
            rs.setIdReferencia(nuevaReferencia);
            referenciasSilabo.add(rs);
            mdlRS.addElement("• " + rs.getIdReferencia().getDescripcionReferencia());
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Ya agregamos esta referencia al silabo",
                    "Error en referencias",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void guardar() {
        if (referenciasSilabo.size() > 2) {
            referenciasSilabo.forEach(r -> {
                if (r.getIdReferenciaSilabo() == 0
                        && r.getIdReferencia()
                                .getTipoReferencia().equals("Base")) {
                    RSBD.guardar(silabo.getID(), r.getIdReferencia().getIdReferencia());
                }
                if (r.getIdReferencia()
                        .getTipoReferencia().equals("Linkografia")
                        || r.getIdReferencia()
                                .getTipoReferencia().equals("Complementaria")) {
                    if (r.getIdReferenciaSilabo() > 0) {
                        REBD.editarNoBase(r.getIdReferencia());
                    } else {
                        int idGenerado = REBD.guardarNoBase(r.getIdReferencia());
                        RSBD.guardar(silabo.getID(), idGenerado);
                    }
                }
            });
            JOptionPane.showConfirmDialog(FRM_REF, "Termino de crear su silabo.");
            FRM_REF.dispose();
            FRM_SILABO.dispose();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Por lo menos se debe agregar una referencia base a su silabo.",
                    "Error en referencias",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
