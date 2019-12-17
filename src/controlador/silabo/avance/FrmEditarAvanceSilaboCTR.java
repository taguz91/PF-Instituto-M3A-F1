/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.avance;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import modelo.AvanceSilabo.AvanzeUnidadesBDA;
import modelo.AvanceSilabo.AvanzeUnidadesMDA;
import modelo.AvanceSilabo.SeguimientoSilaboBD;
import modelo.AvanceSilabo.SeguimientoSilaboMD;

import modelo.curso.CursoMD;
import modelo.silabo.CursoMDS;
import modelo.silabo.CursosBDS;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmAvanceSilabo;

public class FrmEditarAvanceSilaboCTR {

    private UsuarioBD usuario;
    private SeguimientoSilaboMD seguimientoS;
    private final VtnPrincipal vtnPrincipal;
    private CursoMD curso;
    private frmAvanceSilabo avanceSi;
    private List<CursoMDS> lista_curso;
    private List<AvanzeUnidadesMDA> lista_avanUnidades;
    private SeguimientoSilaboMD seguimientoSilaboMD;
    private List<SeguimientoSilaboMD> count;

    public FrmEditarAvanceSilaboCTR(UsuarioBD usuario, SeguimientoSilaboMD seguimientoS, VtnPrincipal vtnPrincipal, CursoMD curso) {
        this.usuario = usuario;
        this.seguimientoS = seguimientoS;
        this.vtnPrincipal = vtnPrincipal;
        this.curso = curso;
    }

    public void init() {
        avanceSi = new frmAvanceSilabo();
        vtnPrincipal.getDpnlPrincipal().add(avanceSi);
        avanceSi.setTitle("CREAR UN AVANCE DE SILABO");
        avanceSi.show();
        avanceSi.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - avanceSi.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - avanceSi.getSize().height) / 2);
        lista_curso = CursosBDS.ConsultarCursoCarreraDocente(curso.getId());
        lista_avanUnidades = AvanzeUnidadesBDA.consultarUnidadAvanze(curso.getId(), seguimientoS.getId_seguimientoS());
        cargarCampos(lista_curso);

        avanceSi.getBntGuardar().addActionListener(e -> ejecutar(e));
        lista_avanUnidades.forEach(uas -> {
            avanceSi.getCbxUnidad().addItem(uas.getUnidad().getTituloUnidad());
        });
        avanceSi.getCbxUnidad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarUnidad();
            }
        });
        avanceSi.getTxrObservaciones().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                AvanzeUnidadesMDA unidadSeleccionadaAvanz = SeleccionarUnidadAvance();
                unidadSeleccionadaAvanz.setObservaciones(avanceSi.getTxrObservaciones().getText());
            }
        });

        avanceSi.getSpnCumplimiento().addChangeListener((ChangeEvent e) -> {
            AvanzeUnidadesMDA unidadSeleccionadaAvanz = SeleccionarUnidadAvance();
            unidadSeleccionadaAvanz.setPortecentaje((int) avanceSi.getSpnCumplimiento().getValue());
        });
        avanceSi.getTxrObservaciones().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                AvanzeUnidadesMDA unidadSeleccionadaAvanz = SeleccionarUnidadAvance();
                unidadSeleccionadaAvanz.setObservaciones(avanceSi.getTxrObservaciones().getText());
            }
        });
        avanceSi.getCbxUnidad().setSelectedIndex(0);
        avanceSi.getBtnCancelar().addActionListener(e -> {
            avanceSi.dispose();
            vtnPrincipal.getMnCAvanceSilabo().doClick();
        });
    }

    public void cargarCampos(List<CursoMDS> lista) {
        for (CursoMDS cursoMDS : lista) {
            avanceSi.getTxtCarrera().setText(cursoMDS.getId_carrera().getNombre());
            avanceSi.getTxtCarrera().setEnabled(false);
            avanceSi.getTxtDocente().setText(cursoMDS.getId_persona().getPrimerNombre() + " " + cursoMDS.getId_persona().getPrimerApellido());
            avanceSi.getTxtDocente().setEnabled(false);
            avanceSi.getTxtAsignatura().setText(cursoMDS.getId_materia().getNombre());
            avanceSi.getTxtAsignatura().setEnabled(false);

            avanceSi.getTxtParalelo().setText(cursoMDS.getCurso_nombre());
            avanceSi.getTxtParalelo().setEnabled(false);

            int numeroAlm = CursosBDS.numero(curso.getId());
            avanceSi.getTxtNumeroAlumnos().setText(String.valueOf(numeroAlm));
            for (AvanzeUnidadesMDA aus : lista_avanUnidades) {
                avanceSi.getDchFechaEntrega().setDate(Date.from(aus.getSeguimiento().getFecha_entrega_informe().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                if (aus.getSeguimiento().isEsInterciclo() == true) {
                    avanceSi.getCbxTipoReporte().setSelectedIndex(1);

                } else {
                    avanceSi.getCbxTipoReporte().removeAllItems();
                    avanceSi.getCbxTipoReporte().addItem("Fin de Ciclo");
                    avanceSi.getCbxTipoReporte().setEnabled(false);

                }
            }
        }
    }

    private void MostrarUnidad() {
        AvanzeUnidadesMDA unidadSeleccionada = SeleccionarUnidadAvance();
        avanceSi.getTxrContenidos().setText(unidadSeleccionada.getUnidad().getContenidosUnidad());
        avanceSi.getTxrObservaciones().setText(unidadSeleccionada.getObservaciones());
        avanceSi.getSpnCumplimiento().setValue(unidadSeleccionada.getPortecentaje());
    }

    private AvanzeUnidadesMDA SeleccionarUnidadAvance() {
        Optional<AvanzeUnidadesMDA> unidad_avanSelecc = lista_avanUnidades.stream()
                .filter(uas -> uas.getUnidad().getTituloUnidad().equals(avanceSi.getCbxUnidad().
                getSelectedItem().toString())).findFirst();
        return unidad_avanSelecc.get();
    }

    private boolean guardar_SeguimientoSilabo() {
        boolean valid = true;
        boolean esInterciclo = true;
        try {
            if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Fin de ciclo")) {
                seguimientoSilaboMD = new SeguimientoSilaboMD(curso);
                seguimientoSilaboMD.getCurso().setId(curso.getId());
                seguimientoSilaboMD.setFecha_entrega_informe(avanceSi.getDchFechaEntrega().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Fin de ciclo")) {
                    esInterciclo = false;
                }
                seguimientoSilaboMD.setEsInterciclo(esInterciclo);
                if (new SeguimientoSilaboBD().insertarSeguimiento(seguimientoSilaboMD)) {
                    insertarAvanceUnidades();
                    valid = true;
                }
            } else if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Fin de Ciclo")) {
                new SeguimientoSilaboBD().eliminarSeguimientoSilabo(seguimientoS);
                seguimientoSilaboMD = new SeguimientoSilaboMD(curso);
                seguimientoSilaboMD.getCurso().setId(curso.getId());
                seguimientoSilaboMD.setFecha_entrega_informe(avanceSi.getDchFechaEntrega().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Fin de Ciclo")) {
                    esInterciclo = false;
                }
                seguimientoSilaboMD.setEsInterciclo(esInterciclo);
                if (new SeguimientoSilaboBD().insertarSeguimiento(seguimientoSilaboMD)) {
                    insertarAvanceUnidades();
                    valid = true;
                }

            } else {
                new SeguimientoSilaboBD().eliminarSeguimientoSilabo(seguimientoS);
                seguimientoSilaboMD = new SeguimientoSilaboMD(curso);
                seguimientoSilaboMD.getCurso().setId(curso.getId());
                seguimientoSilaboMD.setFecha_entrega_informe(avanceSi.getDchFechaEntrega().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Interciclo")) {
                    esInterciclo = true;
                }
                seguimientoSilaboMD.setEsInterciclo(esInterciclo);
                if (new SeguimientoSilaboBD().insertarSeguimiento(seguimientoSilaboMD)) {
                    insertarAvanceUnidades();
                    valid = true;
                }

            }

        } catch (Exception e) {
            System.out.println("Fallo al guardar ULTIMO");

        }
        return valid;
    }

    private void insertarAvanceUnidades() {
        seguimientoSilaboMD = SeguimientoSilaboBD.consultarUltimoSegumiento(curso.getId(), seguimientoSilaboMD.isEsInterciclo());
        seguimientoSilaboMD.setId_seguimientoS(seguimientoSilaboMD.getId_seguimientoS());
        for (AvanzeUnidadesMDA avanceU : lista_avanUnidades) {
            avanceU.getSeguimiento().setId_seguimientoS(seguimientoSilaboMD.getId_seguimientoS());
            AvanzeUnidadesBDA aus = new AvanzeUnidadesBDA();
            aus.insertarAvanzeUnidades(avanceU, avanceU.getSeguimiento().getId_seguimientoS());
        }
    }

    private boolean accion = true;

    private void ejecutar(ActionEvent e) {
        if (accion) {
            new Thread(() -> {
                accion = false;
                if (validarCampos() == true) {
                    boolean aux = false;
                    avanceSi.getBtnCancelar().setEnabled(false);
                    avanceSi.getBntGuardar().setEnabled(false);

                    vtnPrincipal.getLblEstado().setText("                                  Guardando su Seguimiento de Sílabo! Espere por favor...........");

                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FrmEditarAvanceSilaboCTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    aux = guardar_SeguimientoSilabo();

                    if (aux == true) {
                        JOptionPane.showMessageDialog(avanceSi, "Se guardó correctamente!");
                        avanceSi.dispose();
                        vtnPrincipal.getMnCAvanceSilabo().doClick();
                    } else {
                        JOptionPane.showMessageDialog(null, "Falló al guardar! Intente de nuevo! ", "Aviso", JOptionPane.ERROR_MESSAGE);
                        avanceSi.getBtnCancelar().setEnabled(true);
                        avanceSi.getBntGuardar().setEnabled(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "     REVISE INFORMACION INCOMPLETA\nY/O REPORTE YA EXISTENTE O APROBADO", "Aviso", JOptionPane.ERROR_MESSAGE);
                }

                vtnPrincipal.getLblEstado().setText("");
                accion = true;

            }).start();
        }

    }

    private boolean validarCampos() {
        boolean valid = true;
        if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Reporte Correspondiente a")
                || avanceSi.getDchFechaEntrega().getDate() == null) {
            valid = false;
        }

        for (int i = 0; i < lista_avanUnidades.size(); i++) {

            if (lista_avanUnidades.get(i).getObservaciones() == null) {
                valid = false;
            }

        }
        if (avanceSi.getCbxTipoReporte().getSelectedIndex() == 2) {
            count = SeguimientoSilaboBD.consultarSeguimientoEsInterciclo(curso.getId());
            for (SeguimientoSilaboMD ss : count) {
                seguimientoSilaboMD = new SeguimientoSilaboMD();
                boolean esInterciclo = true;
                if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Fin de ciclo")) {
                    esInterciclo = false;
                }
                seguimientoSilaboMD.setEsInterciclo(esInterciclo);
                if (ss.isEsInterciclo() == seguimientoSilaboMD.isEsInterciclo()) {
                    valid = false;
                }
            }
        }
        if (avanceSi.getCbxTipoReporte().getSelectedIndex() == 1) {
            count = SeguimientoSilaboBD.consultarSeguimientoAprobacion(seguimientoS.getId_seguimientoS(), curso.getId());
            for (SeguimientoSilaboMD ss : count) {
                if (ss.getEstado_seguimiento() == 1) {
                    valid = false;
                }
            }
        }
        if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Fin de Ciclo")) {
            count = SeguimientoSilaboBD.consultarSeguimientoAprobacion(seguimientoS.getId_seguimientoS(), curso.getId());
            for (SeguimientoSilaboMD ss : count) {
                if (ss.getEstado_seguimiento() == 1) {
                    valid = false;
                }
            }
        }

        return valid;

    }
}
