/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.materia;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ConectarDB;
import modelo.carrera.CarreraMD;
import modelo.materia.EjeFormacionMD;
import modelo.materia.MateriaBD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVLetras;
import modelo.validaciones.Validar;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.carrera.CarreraBD;
import modelo.lugar.LugarMD;
import modelo.materia.EjeFormacionBD;
import modelo.materia.MateriaMD;
import modelo.validaciones.TxtVNumeros;
import modelo.validaciones.TxtVNumeros_2;
import vista.materia.FrmMaterias;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class FrmMateriasCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmMaterias frmMaterias;
    private final MateriaBD materiaBD;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private boolean guardar = false, siguiente = false, anterior = false;
    private int acceso = 0;
    private boolean editar = false;
    private ArrayList<CarreraMD> listaCarrera;
    private ArrayList<EjeFormacionMD> listaEje;
    private final CarreraBD carBD = null;
    private final EjeFormacionBD ejeBD = null;

    public FrmMateriasCTR(VtnPrincipal vtnPrin, FrmMaterias frmMaterias, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmMaterias = frmMaterias;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.materiaBD = new MateriaBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmMaterias);
        frmMaterias.show();
        //this.carBD = new CarreraBD(conecta); 
        //this.ejeBD = new EjeFormacionBD(conecta);
    }

    public void iniciar() {

        frmMaterias.getCbCarrera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceso = 0;
                    String nombre = frmMaterias.getCbCarrera().getSelectedItem().toString();
                    List<EjeFormacionMD> ejes = materiaBD.cargarEjes(materiaBD.filtrarIdCarrera(nombre, 0).getId());
                    frmMaterias.getCbEjeFormacion().removeAllItems();
                    frmMaterias.getCbEjeFormacion().addItem("SELECCIONE");
                    if (nombre.equals("SELECCIONE") == false) {
                        frmMaterias.getCbEjeFormacion().setEnabled(true);
                        for (int i = 0; i < ejes.size(); i++) {
                            frmMaterias.getCbEjeFormacion().addItem(ejes.get(i).getNombre());
                        }
                    }
                
                
                int pos = frmMaterias.getCbCarrera().getSelectedIndex();
                if (pos > 0) {
                    frmMaterias.getCbCarrera().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                    if (frmMaterias.getLblErrorCarrera() != null) {
                        frmMaterias.getLblErrorCarrera().setVisible(false);
                    }

                } else {
                    frmMaterias.getCbCarrera().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                    if (frmMaterias.getLblErrorCarrera() != null) {
                        frmMaterias.getLblErrorCarrera().setVisible(true);
                    }
                }
                habilitarGuardar();
            }
        });

        frmMaterias.getCbEjeFormacion().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acceso++;
                if (acceso > 1) {
                    String nombre = frmMaterias.getCbEjeFormacion().getSelectedItem().toString();
                    if (nombre.equals("UNIDAD BÁSICA")) {
                        frmMaterias.getCbx_OrgCurricular().setSelectedIndex(1);
                    } else if (nombre.equals("UNIDAD PROFESIONAL")) {
                        frmMaterias.getCbx_OrgCurricular().setSelectedIndex(2);
                    } else if (nombre.equals("UNIDAD DE TITULACIÓN")) {
                        frmMaterias.getCbx_OrgCurricular().setSelectedIndex(3);
                    }
                    habilitarGuardar();
                }

                int pos = frmMaterias.getCbCarrera().getSelectedIndex();
                if (pos > 0) {
                    frmMaterias.getCbCarrera().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                    if (frmMaterias.getLblErrorCarrera() != null) {
                        frmMaterias.getLblErrorCarrera().setVisible(false);
                    }

                } else {
                    frmMaterias.getCbCarrera().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                    if (frmMaterias.getLblErrorCarrera() != null) {
                        frmMaterias.getLblErrorCarrera().setVisible(true);
                    }
                }

            }

        });

        frmMaterias.getBtn_Anterior().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anterior = true;
                accesoPestanas();
//                habilitarGuardar();
            }
        });

        frmMaterias.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siguiente = true;
                accesoPestanas();
                if (guardar == true) {
                    guardarMateria();
                } 
//                habilitarGuardar();
            }
        });
        frmMaterias.getBtnCancelar().addActionListener(e -> cancelar());
        iniciarValidaciones();
        iniciarComponentes();
        iniciarCarreras();

    }

    public void accesoPestanas() {
        int pos = frmMaterias.getjTPMaterias().getSelectedIndex();
        switch (pos) {
            case 0:
                if (siguiente == true) {
                    frmMaterias.getjTPMaterias().setSelectedIndex(1);
                    habilitarGuardar();
                }
                break;
            case 1:
                if (anterior == true) {
                    frmMaterias.getjTPMaterias().setSelectedIndex(0);
                    habilitarGuardar();
                } else if (siguiente == true) {
                    frmMaterias.getjTPMaterias().setSelectedIndex(2);
                    habilitarGuardar();
                }
                break;
            case 2:
                if (anterior == true) {
                    frmMaterias.getjTPMaterias().setSelectedIndex(1);
                    habilitarGuardar();
                } else if (siguiente == true) {
                    frmMaterias.getjTPMaterias().setSelectedIndex(3);
                    habilitarGuardar();
                }
                break;
            case 3:
                if (anterior == true) {
                    frmMaterias.getjTPMaterias().setSelectedIndex(2);
                    habilitarGuardar();
                }
                break;
        }
    }

    public void iniciarCarreras() {
        List<CarreraMD> carreras = materiaBD.cargarCarreras();
        for (int i = 0; i < carreras.size(); i++) {
            frmMaterias.getCbCarrera().addItem(carreras.get(i).getNombre());
        }
    }

    public void habilitarGuardar() {
        String materiaCarrera, materiaCodigo, materiaNombre,
                ejeFormacion, tipoAcreditacion, organizacionCurri, campoFormacion,
                objetivoGeneral, objetivoEspecifico, descripcionMateria, Carrera = "SELECCIONE", Eje = "SELECCIONE", materiaCiclo, creditos,
                horasDocencia, horasPracticas, horasPresenciales, horasAutoEstudio,
                totalHoras;
        int pos = frmMaterias.getjTPMaterias().getSelectedIndex();
        switch (pos) {
            case 0:
                anterior = false;
                siguiente = false;
                    Carrera = frmMaterias.getCbCarrera().getSelectedItem().toString();
                    Eje = frmMaterias.getCbEjeFormacion().getSelectedItem().toString();
                
                materiaCodigo = frmMaterias.getTxtCodigoMateria().getText();
                materiaNombre = frmMaterias.getTxtNombreMateria().getText();
                materiaCiclo = frmMaterias.getCbx_Ciclo().getSelectedItem().toString();
                tipoAcreditacion = frmMaterias.getCbTipoAcreditacion().getSelectedItem().toString();
                creditos = frmMaterias.getTxtCreditos().getText();

                if (Carrera.equals("SELECCIONE") == false && Eje.equals("SELECCIONE") == false
                        && materiaCodigo.equals("") == false && materiaNombre.equals("") == false
                        && materiaCiclo.equals("SELECCIONE") == false && tipoAcreditacion.equals("SELECCIONE") == false
                        && creditos.equals("") == false) {
                    if (frmMaterias.getLblErrorCarrera().isVisible() == false && frmMaterias.getLblErrorEjeFormacion().isVisible() == false
                            && frmMaterias.getLblErrorCodigoMateria().isVisible() == false && frmMaterias.getLblErrorNombreMateria().isVisible() == false
                            && frmMaterias.getLblErrorTipoAcreditacion().isVisible() == false && frmMaterias.getLblErrorMateriaCiclo().isVisible() == false
                            && frmMaterias.getLblErrorCreditos().isVisible() == false) {
                        frmMaterias.getBtnGuardar().setText("Siguiente");
                        frmMaterias.getBtnGuardar().setEnabled(true);

                    } else {
                        frmMaterias.getBtnGuardar().setEnabled(false);
                    }
                } else {
                    frmMaterias.getBtnGuardar().setEnabled(false);
                }

                break;
            case 1:
                anterior = false;
                siguiente = false;
                horasDocencia = frmMaterias.getTxtHorasDocencia().getText();
                horasPracticas = frmMaterias.getTxtHorasPracticas().getText();
                horasPresenciales = frmMaterias.getTxtHorasPresenciales().getText();
                horasAutoEstudio = frmMaterias.getTxtHorasAutoEstudio().getText();
                totalHoras = frmMaterias.getTxtTotalHoras().getText();

                if (horasDocencia.equals("") == false && horasPracticas.equals("") == false
                        && horasPresenciales.equals("") == false && horasAutoEstudio.equals("") == false
                        && totalHoras.equals("") == false) {
                    frmMaterias.getBtn_Anterior().setEnabled(true);
                    frmMaterias.getBtnGuardar().setText("Siguiente");
                    frmMaterias.getBtnGuardar().setEnabled(true);
                } else {
                    frmMaterias.getBtn_Anterior().setEnabled(true);
                    frmMaterias.getBtnGuardar().setText("Siguiente");
                    frmMaterias.getBtnGuardar().setEnabled(false);
                }

                break;
            case 2:
                anterior = false;
                siguiente = false;
                objetivoGeneral = frmMaterias.getTxtObjetivoGeneral().getText();
                objetivoEspecifico = frmMaterias.getTxtObjetivoEspecifico().getText();

                if (objetivoGeneral.equals("") == false && objetivoEspecifico.equals("") == false) {
                    frmMaterias.getBtn_Anterior().setEnabled(true);
                    frmMaterias.getBtnGuardar().setText("Siguiente");
                    frmMaterias.getBtnGuardar().setEnabled(true);
                } else {
                    frmMaterias.getBtn_Anterior().setEnabled(true);
                    frmMaterias.getBtnGuardar().setText("Siguiente");
                    frmMaterias.getBtnGuardar().setEnabled(false);
                }

                break;
            case 3:
                anterior = false;
                siguiente = false;
                descripcionMateria = frmMaterias.getTxtDescripcionMateria().getText();
                organizacionCurri = frmMaterias.getCbx_OrgCurricular().getSelectedItem().toString();
                campoFormacion = frmMaterias.getCbx_CamFormacion().getSelectedItem().toString();

                if (descripcionMateria.equals("") == false && organizacionCurri.equals("SELECCIONE") == false
                        && campoFormacion.equals("SELECCIONE") == false) {
                    if(frmMaterias.getLblErrorCampoFormacion().isVisible() == false &&
                            frmMaterias.getLblErrorOrganizacionCurricular().isVisible() == false){
                        frmMaterias.getBtn_Anterior().setEnabled(true);
                        frmMaterias.getBtnGuardar().setText("Guardar");
                        frmMaterias.getBtnGuardar().setEnabled(true);
                        guardar = true;
                    } else{
                        frmMaterias.getBtn_Anterior().setEnabled(true);
                        frmMaterias.getBtnGuardar().setText("Guardar");
                        frmMaterias.getBtnGuardar().setEnabled(false);
//                        guardar = false;
                    }
                    
                } else {
                    frmMaterias.getBtn_Anterior().setEnabled(true);
                    frmMaterias.getBtnGuardar().setText("Guardar");
                    frmMaterias.getBtnGuardar().setEnabled(false);
//                    guardar = false;
                }

                break;
        }
    }

    public void iniciarComponentes() {

        frmMaterias.getLblErrorCarrera().setVisible(false);
        frmMaterias.getLblErrorEjeFormacion().setVisible(false);
        frmMaterias.getLblErrorCodigoMateria().setVisible(false);
        frmMaterias.getLblErrorMateriaTipo().setVisible(false);
        frmMaterias.getLblErrorNombreMateria().setVisible(false);
        frmMaterias.getLblErrorMateriaCiclo().setVisible(false);
        frmMaterias.getLblErrorTipoAcreditacion().setVisible(false);
        frmMaterias.getLblErrorCreditos().setVisible(false);
        frmMaterias.getLblErrorHorasDocencia().setVisible(false);
        frmMaterias.getLblErrorHorasPracticas().setVisible(false);
        frmMaterias.getLblErrorHorasPresenciales().setVisible(false);
        frmMaterias.getLblErrorHorasAutoEstudio().setVisible(false);
        frmMaterias.getLblErrorTotalHoras().setVisible(false);
        frmMaterias.getLblErrorObjetivoGeneral().setVisible(false);
        frmMaterias.getLblErrorObjetivoEspecifico().setVisible(false);
        frmMaterias.getLblErrorDescripcionMateria().setVisible(false);
        frmMaterias.getLblErrorOrganizacionCurricular().setVisible(false);
        frmMaterias.getLblErrorCampoFormacion().setVisible(false);
        frmMaterias.getBtnGuardar().setText("Siguiente");
        frmMaterias.getCbEjeFormacion().setEnabled(false);
        frmMaterias.getBtnGuardar().setEnabled(false);
        frmMaterias.getBtn_Anterior().setEnabled(false);
        frmMaterias.getjTPMaterias().setEnabledAt(0, false);
        frmMaterias.getjTPMaterias().setEnabledAt(1, false);
        frmMaterias.getjTPMaterias().setEnabledAt(2, false);
        frmMaterias.getjTPMaterias().setEnabledAt(3, false);
    }

    public void borrarCampos() {

        frmMaterias.getTxtCodigoMateria().setText("");
        frmMaterias.getTxtCreditos().setText("");
        frmMaterias.getTxtDescripcionMateria().setText("");
        frmMaterias.getTxtHorasAutoEstudio().setText("");
        frmMaterias.getTxtHorasDocencia().setText("");
        frmMaterias.getTxtHorasPracticas().setText("");
        frmMaterias.getTxtHorasPresenciales().setText("");
        frmMaterias.getTxtNombreMateria().setText("");
        frmMaterias.getTxtObjetivoEspecifico().setText("");
        frmMaterias.getTxtObjetivoGeneral().setText("");
        frmMaterias.getTxtTotalHoras().setText("");
        frmMaterias.getCbCarrera().setSelectedIndex(0);
        frmMaterias.getCbEjeFormacion().setSelectedIndex(0);
        frmMaterias.getCbMateriaTipo().setSelectedIndex(0);
        frmMaterias.getCbTipoAcreditacion().setSelectedIndex(0);
        frmMaterias.getCbx_CamFormacion().setSelectedIndex(0);
        frmMaterias.getCbx_Ciclo().setSelectedIndex(0);
        frmMaterias.getCbx_OrgCurricular().setSelectedIndex(0);
        frmMaterias.getChBNucleo().setSelected(false);

    }

    private void guardarMateria() {

        String materiaCodigo, materiaNombre,
                ejeFormacion, materiaTipo = null, categoria = null, tipoAcreditacion = null,
                objetivoGeneral, objetivoEspecifico, descripcionMateria,
                organizacionCurricular, campoFormacion, carrera, eje;

        int materiaCarrera, creditos,
                horasDocencia, horasPracticas, horasPresenciales, horasAutoEstudio,
                totalHoras, materiaCiclo = 0;
        boolean materiaNucleo;
        CarreraMD carreraMD = new CarreraMD();
        EjeFormacionMD ejeMD = new EjeFormacionMD();

        carrera = frmMaterias.getCbCarrera().getSelectedItem().toString();
        eje = frmMaterias.getCbEjeFormacion().getSelectedItem().toString();
        materiaCodigo = frmMaterias.getTxtCodigoMateria().getText().trim().toUpperCase();
        materiaNombre = frmMaterias.getTxtNombreMateria().getText().trim().toUpperCase();

        switch (frmMaterias.getCbx_Ciclo().getSelectedItem().toString()) {
            case "SELECCIONE":
                materiaCiclo = 0;
                break;
            case "Ciclo 1":
                materiaCiclo = 1;
                break;
            case "Ciclo 2":
                materiaCiclo = 2;
                break;
            case "Ciclo 3":
                materiaCiclo = 3;
                break;
            case "Ciclo 4":
                materiaCiclo = 4;
                break;
            case "Ciclo 5":
                materiaCiclo = 5;
                break;

        }

        materiaTipo = frmMaterias.getCbMateriaTipo().getSelectedItem().toString();
        tipoAcreditacion = frmMaterias.getCbTipoAcreditacion().getSelectedItem().toString();
        creditos = Integer.parseInt(frmMaterias.getTxtCreditos().getText());
        materiaNucleo = frmMaterias.getChBNucleo().isSelected();
        horasDocencia = Integer.parseInt(frmMaterias.getTxtHorasDocencia().getText().trim());
        horasPracticas = Integer.parseInt(frmMaterias.getTxtHorasPracticas().getText().trim());
        horasPresenciales = Integer.parseInt(frmMaterias.getTxtHorasPresenciales().getText().trim());
        horasAutoEstudio = Integer.parseInt(frmMaterias.getTxtHorasAutoEstudio().getText().trim());
        totalHoras = Integer.parseInt(frmMaterias.getTxtTotalHoras().getText().trim());
        objetivoGeneral = frmMaterias.getTxtObjetivoGeneral().getText().trim().toUpperCase();
        objetivoEspecifico = frmMaterias.getTxtObjetivoEspecifico().getText().trim().toUpperCase();
        descripcionMateria = frmMaterias.getTxtDescripcionMateria().getText().trim().toUpperCase();
        organizacionCurricular = frmMaterias.getCbx_OrgCurricular().getSelectedItem().toString();
        campoFormacion = frmMaterias.getCbx_CamFormacion().getSelectedItem().toString();

        if (guardar) {

            MateriaBD materia = new MateriaBD(conecta);
            carreraMD.setId(materiaBD.filtrarIdCarrera(carrera, 0).getId());
            ejeMD.setId(materiaBD.filtrarIdEje(eje, 0).getId());
            materia.setCarrera(carreraMD);
            materia.setEje(ejeMD);
            materia.setCodigo(materiaCodigo);
            materia.setNombre(materiaNombre);
            materia.setCiclo(materiaCiclo);
            materia.setCreditos(creditos);

            if (materiaTipo.equals("SELECCIONE")) {
                materia.setTipo(' ');
            } else {
                materia.setTipo(materiaTipo.charAt(0));
            }

            materia.setTipoAcreditacion(tipoAcreditacion.charAt(0));
            materia.setHorasDocencia(horasDocencia);
            materia.setHorasPracticas(horasPracticas);
            materia.setHorasPresenciales(horasPresenciales);
            materia.setHorasAutoEstudio(horasAutoEstudio);
            materia.setTotalHoras(totalHoras);
            materia.setObjetivo(objetivoGeneral);
            materia.setObjetivoespecifico(objetivoEspecifico);
            materia.setDescripcion(descripcionMateria);
            materia.setOrganizacioncurricular(organizacionCurricular);
            materia.setMateriacampoformacion(campoFormacion);
            materia.setMateriaNucleo(materiaNucleo);

            if (editar) {
                
                if(materia.editarMateria(materiaBD.filtrarIdCarrera(frmMaterias.getCbCarrera().getSelectedItem().toString(), 0).getId())){
                    JOptionPane.showMessageDialog(vtnPrin, "Datos Editados Correctamente");
                    frmMaterias.dispose();
                } else{
                    JOptionPane.showMessageDialog(vtnPrin, "Los no se pudieron Editar Correctamente");
                }
                //Boton de reportes
                //borrarCampos();
                editar = false;

            } else {
                if (materia.insertarMateria()) {
                    JOptionPane.showMessageDialog(vtnPrin, "Datos Guardados Correctamente");
                    frmMaterias.dispose();
                } else {
                    JOptionPane.showMessageDialog(vtnPrin, "Los datos no se pudieron Guardar Correctamente");
                }

                //Boton de reportes
                //borrarCampos();
            }
//            frmMaterias.dispose();
            ctrPrin.cerradoJIF();
        } else {
            JOptionPane.showMessageDialog(null, "Existen errores en los campos\nRevise su información!!");
        }
    }

    public void cancelar() {
        frmMaterias.dispose();
    }

    public void iniciarValidaciones() {

        PropertyChangeListener habilitar = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                habilitarGuardar();
            }
        };

        KeyListener validarPalabras = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char car = e.getKeyChar();
                if (!Validar.esLetrasYNumeros2(car + "")) {
                    e.consume();
                }
                habilitarGuardar();
            }
        };
        
        KeyListener validarNombre = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char car = e.getKeyChar();
                if(!Validar.esLetras2(car+"")){
                    e.consume();
                }
                habilitarGuardar();
            }
        };

//        frmMaterias.getCbCarrera().addActionListener(new CmbValidar(
////                frmMaterias.getCbCarrera(), frmMaterias.getLblErrorCarrera()));
//        frmMaterias.getCbEjeFormacion().addActionListener(new CmbValidar(
//                frmMaterias.getCbEjeFormacion(), frmMaterias.getLblErrorEjeFormacion()));
        frmMaterias.getCbEjeFormacion().addPropertyChangeListener(habilitar);
        frmMaterias.getCbMateriaTipo().addActionListener(new CmbValidar(
                frmMaterias.getCbMateriaTipo(), frmMaterias.getLblErrorMateriaTipo()));
        frmMaterias.getCbTipoAcreditacion().addActionListener(new CmbValidar(
                frmMaterias.getCbTipoAcreditacion(), frmMaterias.getLblErrorTipoAcreditacion()));
        frmMaterias.getCbTipoAcreditacion().addPropertyChangeListener(habilitar);
        frmMaterias.getCbx_Ciclo().addActionListener(new CmbValidar(
                frmMaterias.getCbx_Ciclo(), frmMaterias.getLblErrorMateriaCiclo()));
        frmMaterias.getCbTipoAcreditacion().addPropertyChangeListener(habilitar);
        frmMaterias.getCbx_OrgCurricular().addActionListener(new CmbValidar(
                frmMaterias.getCbx_OrgCurricular(), frmMaterias.getLblErrorOrganizacionCurricular()));
        frmMaterias.getCbx_OrgCurricular().addPropertyChangeListener(habilitar);
        frmMaterias.getCbx_CamFormacion().addActionListener(new CmbValidar(
                frmMaterias.getCbx_CamFormacion(), frmMaterias.getLblErrorCampoFormacion()));
        frmMaterias.getCbx_CamFormacion().addPropertyChangeListener(habilitar);

        //Validar el codigo de materias con letras, numeros y _ - 
        frmMaterias.getTxtCodigoMateria().addKeyListener(validarPalabras);
        frmMaterias.getTxtCodigoMateria().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtNombreMateria().addKeyListener(validarNombre);
        frmMaterias.getTxtNombreMateria().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtCreditos().addKeyListener(new TxtVNumeros_2(frmMaterias.getTxtCreditos()));
        frmMaterias.getTxtCreditos().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtHorasDocencia().addKeyListener(new TxtVNumeros_2(frmMaterias.getTxtHorasDocencia()));
        frmMaterias.getTxtHorasDocencia().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtHorasPracticas().addKeyListener(new TxtVNumeros_2(frmMaterias.getTxtHorasPracticas()));
        frmMaterias.getTxtHorasPracticas().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtHorasPresenciales().addKeyListener(new TxtVNumeros_2(frmMaterias.getTxtHorasPresenciales()));
        frmMaterias.getTxtHorasPresenciales().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtHorasAutoEstudio().addKeyListener(new TxtVNumeros_2(frmMaterias.getTxtHorasAutoEstudio()));
        frmMaterias.getTxtHorasAutoEstudio().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtTotalHoras().addKeyListener(new TxtVNumeros_2(frmMaterias.getTxtTotalHoras()));
        frmMaterias.getTxtTotalHoras().addPropertyChangeListener(habilitar);
        //Permitir insertar comas y puntos

        KeyListener validar = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char car = e.getKeyChar();
                if (!Validar.esObservacion(car + "")) {
                    e.consume();
                }
                habilitarGuardar();
            }
        };

        frmMaterias.getTxtObjetivoGeneral().addKeyListener(validar);
        frmMaterias.getTxtObjetivoEspecifico().addKeyListener(validar);
        frmMaterias.getTxtDescripcionMateria().addKeyListener(validar);

    }

    public void editarMaterias(MateriaMD matEditar) {

        editar = true;

        frmMaterias.getTxtCodigoMateria().setText(matEditar.getCodigo());
        frmMaterias.getTxtNombreMateria().setText(matEditar.getNombre());

        frmMaterias.getTxtCreditos().setText(matEditar.getCreditos() + "");
        frmMaterias.getTxtDescripcionMateria().setText(matEditar.getDescripcion());
        frmMaterias.getTxtHorasAutoEstudio().setText(matEditar.getHorasAutoEstudio() + "");

        frmMaterias.getTxtHorasDocencia().setText(matEditar.getHorasDocencia() + "");
        frmMaterias.getTxtHorasPracticas().setText(matEditar.getHorasPracticas() + "");
        frmMaterias.getTxtHorasPresenciales().setText(matEditar.getHorasPresenciales() + "");
        frmMaterias.getTxtObjetivoEspecifico().setText(matEditar.getObjetivoespecifico());
        frmMaterias.getTxtObjetivoGeneral().setText(matEditar.getObjetivo());

        frmMaterias.getTxtTotalHoras().setText(matEditar.getTotalHoras() + "");
        frmMaterias.getChBNucleo().setSelected(matEditar.isMateriaNucleo());

        if (matEditar.getCarrera() == null) {
            frmMaterias.getCbCarrera().setSelectedItem("SELECCIONE");
        } else {
            frmMaterias.getCbCarrera().setSelectedItem(materiaBD.filtrarIdCarrera("", matEditar.getCarrera().getId()).getNombre());
        }
        System.out.println("nombre " + materiaBD.filtrarIdCarrera("", matEditar.getCarrera().getId()).getNombre());
        //
//        if (matEditar.getEje() == null) {
//            frmMaterias.getCbEjeFormacion().setSelectedItem("SELECCIONE");
//        } else {
//            frmMaterias.getCbEjeFormacion().setSelectedItem(materiaBD.filtrarIdEje("", matEditar.getEje().getId()).getNombre());
//        }
        //
        int ciclo = matEditar.getCiclo();
        switch (ciclo) {

            case 1:
                frmMaterias.getCbx_Ciclo().setSelectedItem("Ciclo 1");
                break;
            case 2:
                frmMaterias.getCbx_Ciclo().setSelectedItem("Ciclo 2");
                break;
            case 3:
                frmMaterias.getCbx_Ciclo().setSelectedItem("Ciclo 3");
                break;
            case 4:
                frmMaterias.getCbx_Ciclo().setSelectedItem("Ciclo 4");
                break;
            case 5:
                frmMaterias.getCbx_Ciclo().setSelectedItem("Ciclo 5");
                break;

        }

        String materiaTipo = matEditar.getTipo() + "";
        if (null == materiaTipo) {
            materiaTipo = "H";
        } else {
            switch (materiaTipo) {
                case "M":
                    materiaTipo = "M";
                    break;
                case "C":
                    materiaTipo = "C";
                    break;
                case "H":
                    materiaTipo = "H";
                    break;
                default:
                    materiaTipo = "SELECCIONE";
                    break;
            }
        }
        frmMaterias.getCbMateriaTipo().setSelectedItem(materiaTipo);

        String tipoAcreditacion = matEditar.getTipoAcreditacion() + "";
        if (null == tipoAcreditacion) {
            tipoAcreditacion = "T";
        } else {
            switch (tipoAcreditacion) {
                case "H":
                    tipoAcreditacion = "H";
                    break;
                case "C":
                    tipoAcreditacion = "C";
                    break;
                case "T":
                    tipoAcreditacion = "T";
                    break;
                default:
                    tipoAcreditacion = "SELECCIONE";
                    break;
            }
        }
        frmMaterias.getCbTipoAcreditacion().setSelectedItem(tipoAcreditacion);
        frmMaterias.getCbx_OrgCurricular().setSelectedItem(matEditar.getOrganizacioncurricular());
        frmMaterias.getCbx_CamFormacion().setSelectedItem(matEditar.getMateriacampoformacion());

        iniciarValidaciones();
    }

}
