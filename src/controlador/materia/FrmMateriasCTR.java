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
    private int acceso;
    private int idMateria = 0;
    private boolean editar = false;
    private ArrayList<CarreraMD> listaCarrera;
    private ArrayList<EjeFormacionMD> listaEje;
    private final CarreraBD carBD;
    private final EjeFormacionBD ejeBD;

    public FrmMateriasCTR(VtnPrincipal vtnPrin, FrmMaterias frmMaterias, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmMaterias = frmMaterias;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.materiaBD = new MateriaBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmMaterias);
        frmMaterias.show();
        this.carBD = new CarreraBD(conecta); 
        this.ejeBD = new EjeFormacionBD(conecta);
    }

    public void iniciar() {

        frmMaterias.getCbCarrera().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = frmMaterias.getCbCarrera().getSelectedItem().toString();
                List<EjeFormacionMD> ejes = materiaBD.cargarEjes(materiaBD.filtrarIdCarrera(nombre).getId());
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

        frmMaterias.getBtn_Anterior().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                anterior = true;
                accesoPestanas();
                habilitarGuardar();
            }
        });

        frmMaterias.getBtnGuardar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                siguiente = true;
                accesoPestanas();
                if (guardar == true) {
                    guardarMateria();
                }
                habilitarGuardar();
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
                objetivoGeneral, objetivoEspecifico, descripcionMateria, Carrera, Eje, materiaCiclo, creditos,
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
                            && frmMaterias.getLblErrorMateriaTipo().isVisible() == false && frmMaterias.getLblErrorCategoria().isVisible() == false
                            && frmMaterias.getLblErrorTipoAcreditacion().isVisible() == false && frmMaterias.getLblErrorMateriaCiclo().isVisible() == false
                            && frmMaterias.getLblErrorCreditos().isVisible() == false){
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
                
                if(objetivoGeneral.equals("") == false && objetivoEspecifico.equals("") == false){
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
                
                if(descripcionMateria.equals("") == false && organizacionCurri.equals("SELECCIONE") == false &&
                        campoFormacion.equals("SELECCIONE") == false){
                    frmMaterias.getBtn_Anterior().setEnabled(true);
                    frmMaterias.getBtnGuardar().setText("Guardar");
                    frmMaterias.getBtnGuardar().setEnabled(true);
                    guardar = true;
                } else {
                    frmMaterias.getBtn_Anterior().setEnabled(true);
                    frmMaterias.getBtnGuardar().setText("Guardar");
                    frmMaterias.getBtnGuardar().setEnabled(false);
                    guardar = false;
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
        frmMaterias.getLblErrorCategoria().setVisible(false);
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
        frmMaterias.getCbCategoria().setSelectedIndex(0);
        frmMaterias.getCbEjeFormacion().setSelectedIndex(0);
        frmMaterias.getCbMateriaTipo().setSelectedIndex(0);
        frmMaterias.getCbTipoAcreditacion().setSelectedIndex(0);
        frmMaterias.getCbx_CamFormacion().setSelectedIndex(0);
        frmMaterias.getCbx_Ciclo().setSelectedIndex(0);
        frmMaterias.getCbx_OrgCurricular().setSelectedIndex(0);
        frmMaterias.getChBNucleo().setSelected(false);

    }

    private void guardarMateria() {
        boolean guardar = true;
        String materiaCodigo, materiaNombre, materiaCiclo = null,
                ejeFormacion, materiaTipo = null, categoria = null, tipoAcreditacion = null,
                objetivoGeneral, objetivoEspecifico, descripcionMateria,
                organizacionCurricular, campoFormacion, carrera, eje;

        int materiaCarrera, creditos,
                horasDocencia, horasPracticas, horasPresenciales, horasAutoEstudio,
                totalHoras;

        boolean materiaNucleo;
        CarreraMD carreraMD = new CarreraMD();
        EjeFormacionMD ejeMD = new EjeFormacionMD();

        carrera = frmMaterias.getCbCarrera().getSelectedItem().toString();
        eje = frmMaterias.getCbEjeFormacion().getSelectedItem().toString();
        materiaCodigo = frmMaterias.getTxtCodigoMateria().getText().trim().toUpperCase();
        materiaNombre = frmMaterias.getTxtNombreMateria().getText().trim().toUpperCase();

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

        //
        materiaCarrera = frmMaterias.getCbCarrera().getSelectedIndex();
        if (materiaCarrera > 0 && materiaCarrera <= listaCarrera.size()) {
            frmMaterias.getLblErrorCarrera().setVisible(false);
            carreraMD = listaCarrera.get(materiaCarrera - 1);
//            eje = frmMaterias.getCbEjeFormacion().getSelectedIndex();
//            if (eje > 0 && eje <= listaEje.size()) {
//                frmMaterias.getLblErrorEjeFormacion().setVisible(false);
//                ejeMD = listaEje.get(eje - 1);
//            } else {
//                frmMaterias.getLblErrorEjeFormacion().setVisible(true);
//            }
        } else {
            frmMaterias.getLblErrorCarrera().setVisible(true);
        }

        //
        if (frmMaterias.getCbx_Ciclo().getSelectedIndex() < 1) {
            guardar = false;
            frmMaterias.getLblErrorMateriaCiclo().setVisible(true);
        } else {
            materiaCiclo = frmMaterias.getCbx_Ciclo().getSelectedItem().toString();
            frmMaterias.getLblErrorMateriaCiclo().setVisible(false);
        }
        //
        if (frmMaterias.getCbCategoria().getSelectedIndex() < 1) {
            guardar = false;
            frmMaterias.getLblErrorCategoria().setVisible(true);
        } else {
            categoria = frmMaterias.getCbCategoria().getSelectedItem().toString();
            frmMaterias.getLblErrorCategoria().setVisible(false);
        }
        //
        if (frmMaterias.getCbMateriaTipo().getSelectedIndex() < 1) {
            guardar = false;
            frmMaterias.getLblErrorMateriaTipo().setVisible(true);
        } else {
            materiaTipo = frmMaterias.getCbMateriaTipo().getSelectedItem().toString();
            frmMaterias.getLblErrorMateriaTipo().setVisible(false);
        }
        //
        if (frmMaterias.getCbTipoAcreditacion().getSelectedIndex() < 1) {
            guardar = false;
            frmMaterias.getLblErrorTipoAcreditacion().setVisible(true);
        } else {
            tipoAcreditacion = frmMaterias.getCbTipoAcreditacion().getSelectedItem().toString();
            frmMaterias.getLblErrorTipoAcreditacion().setVisible(false);
        }
        //
        if (frmMaterias.getCbx_OrgCurricular().getSelectedIndex() < 1) {
            guardar = false;
            frmMaterias.getLblErrorOrganizacionCurricular().setVisible(true);
        } else {
            organizacionCurricular = frmMaterias.getCbx_OrgCurricular().getSelectedItem().toString();
            frmMaterias.getLblErrorOrganizacionCurricular().setVisible(false);
        }
        //
        if (frmMaterias.getCbx_CamFormacion().getSelectedIndex() < 1) {
            guardar = false;
            frmMaterias.getLblErrorCampoFormacion().setVisible(true);
        } else {
            campoFormacion = frmMaterias.getCbx_CamFormacion().getSelectedItem().toString();
            frmMaterias.getLblErrorCampoFormacion().setVisible(false);
        }
        //

        if (guardar) {

            MateriaBD materia = new MateriaBD(conecta);
            carreraMD.setId(materiaBD.filtrarIdCarrera(carrera).getId());
            ejeMD.setId(acceso);
            materia.setCarrera(carreraMD);
            materia.setEje(ejeMD);
            materia.setCodigo(materiaCodigo);
            materia.setNombre(materiaNombre);
            materia.setCiclo(Integer.parseInt(materiaCiclo));
            materia.setCreditos(creditos);
            materia.setTipo(materiaTipo.charAt(0));
            materia.setCategoria(categoria);
            materia.setTipoAcreditacion(tipoAcreditacion.charAt(0));
            materia.setHorasDocencia(horasDocencia);
            materia.setHorasPracticas(horasPracticas);
            materia.setHorasPresenciales(horasPresenciales);
            materia.setHorasAutoEstudio(horasAutoEstudio);
            materia.setTotalHoras(totalHoras);
            materia.setObjetivo(objetivoGeneral);
            materia.setObjetivoespecifico(objetivoEspecifico);
            materia.setDescripcion(descripcionMateria);
//            materia.setOrganizacioncurricular(organizacionCurricular);
//            materia.setMateriacampoformacion(campoFormacion);

            if (editar) {
                if (idMateria > 0) {
                    materia.editarMateria(idMateria);
                    JOptionPane.showMessageDialog(vtnPrin, "Datos Editados Correctamente.");
                    //Boton de reportes
                    borrarCampos();
                    frmMaterias.dispose();
                    editar = false;
                }
            } else {
                materia.insertarMateria();
                JOptionPane.showMessageDialog(vtnPrin, "Datos Guardados Correctamente.");
                //Boton de reportes
                borrarCampos();
                frmMaterias.dispose();
            }
            frmMaterias.dispose();
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
        
        KeyListener validarPalabras = new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                char car = e.getKeyChar();
                if (!Validar.esLetrasYNumeros2(car+"")) {
                    e.consume();
                }
                habilitarGuardar();
            }
        };

//        frmMaterias.getCbCarrera().addActionListener(new CmbValidar(
//                frmMaterias.getCbCarrera(), frmMaterias.getLblErrorCarrera()));
        frmMaterias.getCbCategoria().addActionListener(new CmbValidar(
                frmMaterias.getCbCategoria(), frmMaterias.getLblErrorCategoria()));
        frmMaterias.getCbEjeFormacion().addActionListener(new CmbValidar(
                frmMaterias.getCbEjeFormacion(), frmMaterias.getLblErrorEjeFormacion()));
//       frmMaterias.getCbEjeFormacion().addPropertyChangeListener(habilitar);
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

        //Validar el codigo de materias con letras, numeros y _ - 
        frmMaterias.getTxtCodigoMateria().addKeyListener(validarPalabras);
        frmMaterias.getTxtCodigoMateria().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtNombreMateria().addKeyListener(new TxtVLetras(frmMaterias.getTxtNombreMateria(),
                frmMaterias.getLblErrorNombreMateria()));
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

    void editarMaterias(MateriaMD matEditar) {

        editar = true;
        idMateria = matEditar.getId();

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
        frmMaterias.getCbCarrera().setSelectedItem(matEditar.getCarrera());
        frmMaterias.getCbEjeFormacion().setSelectedItem(matEditar.getEje());

        if (matEditar.getCarrera() == null) {
            frmMaterias.getCbCarrera().setSelectedItem("SELECCIONE");
        } else {
            frmMaterias.getCbCarrera().setSelectedItem(matEditar.getCarrera());
        }

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

        if (matEditar.getEje() == null) {
            frmMaterias.getCbEjeFormacion().setSelectedItem("SELECCIONE");
        } else {
            frmMaterias.getCbEjeFormacion().setSelectedItem(matEditar.getEje());
        }

        String materiaTipo = matEditar.getTipo() + "";
        if ("M".equals(materiaTipo)) {
            materiaTipo = "M...";
        } else {
            materiaTipo = "C...";
        }
        frmMaterias.getCbMateriaTipo().setSelectedItem(materiaTipo);

        String tipoAcreditacion = matEditar.getTipoAcreditacion() + "";
        if ("H".equals(tipoAcreditacion)) {
            tipoAcreditacion = "H...";
        } else {
            tipoAcreditacion = "C...";
        }
        frmMaterias.getCbTipoAcreditacion().setSelectedItem(tipoAcreditacion);

        if (matEditar.getCategoria() == null) {
            frmMaterias.getCbCategoria().setSelectedItem("SELECCIONE");
        } else {
            frmMaterias.getCbCategoria().setSelectedItem(matEditar.getCategoria());
        }

        if (matEditar.getOrganizacioncurricular() == null) {
            frmMaterias.getCbx_OrgCurricular().setSelectedItem("SELECCIONE");
        } else {
            frmMaterias.getCbx_OrgCurricular().setSelectedItem(matEditar.getOrganizacioncurricular());
        }

        if (matEditar.getMateriacampoformacion() == null) {
            frmMaterias.getCbx_CamFormacion().setSelectedItem("SELECCIONE");
        } else {
            frmMaterias.getCbx_CamFormacion().setSelectedItem(matEditar.getMateriacampoformacion());
        }

        iniciarValidaciones();
    }

}
