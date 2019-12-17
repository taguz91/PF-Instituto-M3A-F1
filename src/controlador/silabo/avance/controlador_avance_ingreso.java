/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.avance;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.ZoneId;
import java.util.ArrayList;
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
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmAvanceSilabo;

/**
 *
 * @author HP
 */
public class controlador_avance_ingreso {

    private final VtnPrincipal vtnPrincipal;
    private CursoMD curso;
    private SilaboMD silabo;
    private List<CursoMDS> lista_curso;
    private frmAvanceSilabo avanceSi;
    private List<UnidadSilaboMD> unidadesSilabo;
    private List<AvanzeUnidadesMDA> lista_avanzeU;
    private SeguimientoSilaboMD seguimientoSilaboMD;
    private List<SeguimientoSilaboMD> count;

    public controlador_avance_ingreso(VtnPrincipal vtnPrincipal, CursoMD curso,
            SilaboMD silabo) {
        this.vtnPrincipal = vtnPrincipal;
        this.curso = curso;
        this.silabo = silabo;

    }

    public void init() {
        avanceSi = new frmAvanceSilabo();
        vtnPrincipal.getDpnlPrincipal().add(avanceSi);
        avanceSi.setTitle("CREAR UN AVANCE DE SILABO");
        avanceSi.show();
        avanceSi.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - avanceSi.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - avanceSi.getSize().height) / 2);
        lista_curso = CursosBDS.ConsultarCursoCarreraDocente(curso.getId());

        lista_avanzeU = new ArrayList<>();
        unidadesSilabo = UnidadSilaboBD.consultarUnidadesPlanClase(silabo.getID());
        cargarTemasUnidades();
        cargarCampos(lista_curso);
        avanceSi.getCbxTipoReporte().removeItemAt(2);
        avanceSi.getBntGuardar().addActionListener(e -> ejecutar(e));
        avanceSi.getBtnCancelar().addActionListener(a1 -> {
            avanceSi.dispose();
            vtnPrincipal.getMnCAvanceSilabo().doClick();
        });

        avanceSi.getCbxUnidad().addActionListener((ActionEvent e) -> {
            mostrarUnidad();
        });

        avanceSi.getSpnCumplimiento().addChangeListener((ChangeEvent e) -> {
            AvanzeUnidadesMDA unidadSeleccionadaAvanz = seleccionarUnidadA();
            unidadSeleccionadaAvanz.setPortecentaje((int) avanceSi.getSpnCumplimiento().getValue());
        });
        avanceSi.getTxrObservaciones().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                AvanzeUnidadesMDA unidadSeleccionadaAvanz = seleccionarUnidadA();
                unidadSeleccionadaAvanz.setObservaciones(avanceSi.getTxrObservaciones().getText());

            }
        });
        avanceSi.getCbxUnidad().setSelectedIndex(0);
    }

    private void cargarTemasUnidades() {
        unidadesSilabo.forEach((umd) -> {
            avanceSi.getCbxUnidad().addItem(umd.getTituloUnidad());
        });
        for (UnidadSilaboMD us : unidadesSilabo) {
            lista_avanzeU.add(new AvanzeUnidadesMDA(us));
        }
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
        }
    }

    private void mostrarUnidad() {
        AvanzeUnidadesMDA unidadesMDA = seleccionarUnidadA();

        avanceSi.getTxrContenidos().setText(unidadesMDA.getUnidad().getContenidosUnidad());
        avanceSi.getTxrObservaciones().setText(unidadesMDA.getObservaciones());
        avanceSi.getSpnCumplimiento().setValue(unidadesMDA.getPortecentaje());
    }

    private AvanzeUnidadesMDA seleccionarUnidadA() {
        System.out.println(lista_avanzeU.size() + " -------------------TAMAÑO------------------------->>>> DEL ARRAY AVANCE_UNIDADES");
        Optional<AvanzeUnidadesMDA> unidadSeleccionadaA = lista_avanzeU.stream().filter(uas
                -> uas.getUnidad().getTituloUnidad().equals(avanceSi.getCbxUnidad().getSelectedItem().toString())).findFirst();

        return unidadSeleccionadaA.get();
    }

    private boolean guardar_SeguimientoSilabo() {
        boolean esInterciclo = true;
        try {
            seguimientoSilaboMD = new SeguimientoSilaboMD(curso);
            seguimientoSilaboMD.getCurso().setId(curso.getId());
            seguimientoSilaboMD.setFecha_entrega_informe(avanceSi.getDchFechaEntrega().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            if (avanceSi.getCbxTipoReporte().getSelectedItem().equals("Interciclo")) {
                esInterciclo = true;
            }
            seguimientoSilaboMD.setEsInterciclo(esInterciclo);
            if (new SeguimientoSilaboBD().insertarSeguimiento(seguimientoSilaboMD)) {
                insertarAvanceUnidades();

                return true;
            }

        } catch (Exception e) {
            System.out.println("Fallo al guardar ULTIMO");

        }
        return false;
    }

    private void insertarAvanceUnidades() {
        seguimientoSilaboMD = SeguimientoSilaboBD.consultarUltimoSegumiento(curso.getId(), seguimientoSilaboMD.isEsInterciclo());
        seguimientoSilaboMD.setId_seguimientoS(seguimientoSilaboMD.getId_seguimientoS());
        for (AvanzeUnidadesMDA avanceU : lista_avanzeU) {
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
                        Logger.getLogger(controlador_avance_ingreso.class.getName()).log(Level.SEVERE, null, ex);
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
                    JOptionPane.showMessageDialog(null, "         REVISE INFORMACION INCOMPLETA\nY/O REPORTE DE CICLO O INTERCICLO YA EXISTENTE", "Aviso", JOptionPane.ERROR_MESSAGE);
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

        for (int i = 0; i < lista_avanzeU.size(); i++) {

            if (lista_avanzeU.get(i).getObservaciones() == null) {
                valid = false;
            }
        }
        return valid;

    }

}
