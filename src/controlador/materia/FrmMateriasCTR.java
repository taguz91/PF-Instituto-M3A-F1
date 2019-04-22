/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.materia;

import controlador.principal.VtnPrincipalCTR;
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
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import modelo.materia.MateriaMD;
import modelo.validaciones.TxtVNumeros;
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

    public FrmMateriasCTR(VtnPrincipal vtnPrin, FrmMaterias frmMaterias, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmMaterias = frmMaterias;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.materiaBD = new MateriaBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmMaterias);
        frmMaterias.show();

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
                ejeFormacion, tipoAcreditacion,
                objetivoGeneral, descripcionMateria, Carrera, Eje, materiaCiclo, creditos,
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
                materiaCiclo = frmMaterias.getTxtMateriaCiclo().getText();
                tipoAcreditacion = frmMaterias.getCbTipoAcreditacion().getSelectedItem().toString();
                creditos = frmMaterias.getTxtCreditos().getText();

                if (Carrera.equals("SELECCIONE") == false && Eje.equals("SELECCIONE") == false
                        && materiaCodigo.equals("") == false && materiaNombre.equals("") == false
                        && materiaCiclo.equals("") == false && tipoAcreditacion.equals("SELECCIONE") == false
                        && creditos.equals("") == false) {
                    if (frmMaterias.getLblErrorCarrera().isVisible() == false && frmMaterias.getLblErrorEjeFormacion().isVisible() == false
                            && frmMaterias.getLblErrorMateriaTipo().isVisible() == false && frmMaterias.getLblErrorCategoria().isVisible() == false
                            && frmMaterias.getLblErrorTipoAcreditacion().isVisible() == false) {
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

                if (objetivoGeneral.equals("") == false) {
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

                if (descripcionMateria.equals("") == false) {
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

    }

    private void guardarMateria() {

        String materiaCarrera, materiaCodigo, materiaNombre,
                ejeFormacion, materiaTipo, categoria, tipoAcreditacion,
                objetivoGeneral, objetivoEspecifico, descripcionMateria,
                organizacionCurricular, campoFormacion;

        int id_Carrera, id_Eje, materiaCiclo, creditos,
                horasDocencia, horasPracticas, horasPresenciales, horasAutoEstudio,
                totalHoras;

        boolean materiaNucleo;
        CarreraMD carrera = new CarreraMD();
        EjeFormacionMD eje = new EjeFormacionMD();

//        carrera.setId(frmMaterias.getCbCarrera());
//        eje.setId(frmMaterias.getCbEjeFormacion());
        materiaCodigo = frmMaterias.getTxtCodigoMateria().getText().trim().toUpperCase();
        materiaNombre = frmMaterias.getTxtNombreMateria().getText().trim().toUpperCase();
        materiaCiclo = Integer.parseInt(frmMaterias.getTxtMateriaCiclo().getText().trim());
        creditos = Integer.parseInt(frmMaterias.getTxtCreditos().getText());
        materiaTipo = frmMaterias.getCbMateriaTipo().getSelectedItem().toString();
        categoria = frmMaterias.getCbCategoria().getSelectedItem().toString();
        tipoAcreditacion = frmMaterias.getCbTipoAcreditacion().getSelectedItem().toString();
        materiaNucleo = frmMaterias.getChBNucleo().isSelected();
        horasDocencia = Integer.parseInt(frmMaterias.getTxtHorasDocencia().getText().trim());
        horasPracticas = Integer.parseInt(frmMaterias.getTxtHorasPracticas().getText().trim());
        horasPresenciales = Integer.parseInt(frmMaterias.getTxtHorasPresenciales().getText().trim());
        horasAutoEstudio = Integer.parseInt(frmMaterias.getTxtHorasAutoEstudio().getText().trim());
        totalHoras = Integer.parseInt(frmMaterias.getTxtTotalHoras().getText().trim());
        objetivoGeneral = frmMaterias.getTxtObjetivoGeneral().getText().trim().toUpperCase();
        objetivoEspecifico = frmMaterias.getTxtObjetivoEspecifico().getText().trim().toUpperCase();
        descripcionMateria = frmMaterias.getTxtDescripcionMateria().getText().trim().toUpperCase();
        organizacionCurricular = frmMaterias.getTxtOrganizacionCurricular().getText().trim().toUpperCase();
        campoFormacion = frmMaterias.getTxtCampoFormacion().getText().trim().toUpperCase();

        MateriaBD materia = new MateriaBD(conecta);

        materia.setCarrera(carrera);
        materia.setEje(eje);
        materia.setCodigo(materiaCodigo);
        materia.setNombre(materiaNombre);
        materia.setCiclo(materiaCiclo);
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
        materia.setOrganizacioncurricular(organizacionCurricular);
        materia.setMateriacampoformacion(campoFormacion);

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

        //Validar el codigo de materias con letras, numeros y _ - 
        frmMaterias.getTxtCodigoMateria().addKeyListener(new TxtVLetras(
                frmMaterias.getTxtCodigoMateria(), frmMaterias.getLblErrorCodigoMateria()));
        frmMaterias.getTxtCodigoMateria().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtNombreMateria().addKeyListener(new TxtVLetras(frmMaterias.getTxtNombreMateria(),
                frmMaterias.getLblErrorNombreMateria()));
        frmMaterias.getTxtNombreMateria().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtMateriaCiclo().addKeyListener(new TxtVLetras(
                frmMaterias.getTxtMateriaCiclo(), frmMaterias.getLblErrorMateriaCiclo()));
        frmMaterias.getTxtMateriaCiclo().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtCreditos().addKeyListener(new TxtVNumeros(
                frmMaterias.getTxtCreditos(), frmMaterias.getLblErrorCreditos()));
        frmMaterias.getTxtCreditos().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtHorasDocencia().addKeyListener(new TxtVNumeros(
                frmMaterias.getTxtHorasDocencia(), frmMaterias.getLblErrorHorasDocencia()));
        frmMaterias.getTxtHorasDocencia().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtHorasPracticas().addKeyListener(new TxtVNumeros(
                frmMaterias.getTxtHorasPracticas(), frmMaterias.getLblErrorHorasPracticas()));
        frmMaterias.getTxtHorasPracticas().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtHorasPresenciales().addKeyListener(new TxtVNumeros(
                frmMaterias.getTxtHorasPresenciales(), frmMaterias.getLblErrorHorasPresenciales()));
        frmMaterias.getTxtHorasPresenciales().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtHorasAutoEstudio().addKeyListener(new TxtVNumeros(
                frmMaterias.getTxtHorasAutoEstudio(), frmMaterias.getLblErrorHorasAutoEstudio()));
        frmMaterias.getTxtHorasAutoEstudio().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtTotalHoras().addKeyListener(new TxtVNumeros(
                frmMaterias.getTxtHorasAutoEstudio(), frmMaterias.getLblErrorHorasAutoEstudio()));
        frmMaterias.getTxtTotalHoras().addPropertyChangeListener(habilitar);
        //Permitir insertar comas y puntos
        frmMaterias.getTxtCampoFormacion().addKeyListener(new TxtVLetras(
                frmMaterias.getTxtMateriaCiclo(), frmMaterias.getLblErrorMateriaCiclo()));
        frmMaterias.getTxtCampoFormacion().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtOrganizacionCurricular().addKeyListener(new TxtVLetras(
                frmMaterias.getTxtMateriaCiclo(), frmMaterias.getLblErrorMateriaCiclo()));
        frmMaterias.getTxtOrganizacionCurricular().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtObjetivoGeneral().addKeyListener(new TxtVLetras(
                frmMaterias.getTxtMateriaCiclo(), frmMaterias.getLblErrorMateriaCiclo()));
        frmMaterias.getTxtObjetivoGeneral().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtObjetivoEspecifico().addKeyListener(new TxtVLetras(
                frmMaterias.getTxtMateriaCiclo(), frmMaterias.getLblErrorMateriaCiclo()));
        frmMaterias.getTxtObjetivoEspecifico().addPropertyChangeListener(habilitar);
        frmMaterias.getTxtDescripcionMateria().addKeyListener(new TxtVLetras(
                frmMaterias.getTxtMateriaCiclo(), frmMaterias.getLblErrorMateriaCiclo()));
        frmMaterias.getTxtDescripcionMateria().addPropertyChangeListener(habilitar);

    }

    void editarMaterias(MateriaMD matEditar) {

        idMateria = matEditar.getId();

        frmMaterias.getTxtCodigoMateria().setText(matEditar.getCodigo());
        frmMaterias.getTxtNombreMateria().setText(matEditar.getNombre());
  
        
        
        if (matEditar.getCarrera() == null) {
            frmMaterias.getCbCarrera().setSelectedItem("SELECCIONE");
        } else {
            frmMaterias.getCbCarrera().setSelectedItem(matEditar.getCarrera());
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
        }else{
            tipoAcreditacion = "C...";
        }
        
        
        if(matEditar.getCategoria() == null){
            frmMaterias.getCbCategoria().setSelectedItem("SELECCIONE");
        }else{
            frmMaterias.getCbCategoria().setSelectedItem(matEditar.getCategoria());
        }
        
        
        
    }

}
