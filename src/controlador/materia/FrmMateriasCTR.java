/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.materia;

import controlador.principal.VtnPrincipalCTR;
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
import java.util.List;
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
    private int numAccion;

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

        frmMaterias.getCbCarrera().addMouseListener(new MouseAdapter(){
            
            public void mouseClicked(){
                String nombre = frmMaterias.getCbCarrera().getSelectedItem().toString();
                if(nombre.equals("SELECCIONE") == false){
                    frmMaterias.getCbEjeFormacion().setEnabled(true);
                    List<EjeFormacionMD> ejes = materiaBD.cargarEjes(materiaBD.filtrarIdCarrera(nombre).getId());
                    for (int i = 0; i < ejes.size(); i++) {
                        frmMaterias.getCbEjeFormacion().addItem(ejes.get(i).getNombre());
                    }
                }
            }
        });
        
        frmMaterias.getBtnGuardar().addActionListener(e -> guardarMateria());
        frmMaterias.getBtnCancelar().addActionListener(e -> cancelar());
        iniciarValidaciones();
        iniciarComponentes();
        iniciarCarreras();

    }
    
    public void iniciarCarreras(){
        List<CarreraMD> carreras = materiaBD.cargarCarreras();
        for (int i = 0; i < carreras.size(); i++) {
            frmMaterias.getCbCarrera().addItem(carreras.get(i).getNombre());
        }
    }
    
    public void iniciarComponentes(){
        
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

        frmMaterias.getCbCarrera().addActionListener(new CmbValidar(
                frmMaterias.getCbCarrera(), frmMaterias.getLblErrorCarrera()));
        frmMaterias.getCbCategoria().addActionListener(new CmbValidar(
                frmMaterias.getCbCategoria(), frmMaterias.getLblErrorCategoria()));
        frmMaterias.getCbEjeFormacion().addActionListener(new CmbValidar(
                frmMaterias.getCbEjeFormacion(), frmMaterias.getLblErrorEjeFormacion()));
        frmMaterias.getCbMateriaTipo().addActionListener(new CmbValidar(
                frmMaterias.getCbMateriaTipo(), frmMaterias.getLblErrorMateriaTipo()));

        //Validar el codigo de materias con letras, numeros y _ - 
        frmMaterias.getTxtCodigoMateria().addKeyListener(new TxtVLetras(
                frmMaterias.getTxtCodigoMateria(), frmMaterias.getLblErrorCodigoMateria()));
        frmMaterias.getTxtCreditos().addKeyListener(new TxtVNumeros(
                frmMaterias.getTxtCreditos(), frmMaterias.getLblErrorCreditos()));
        frmMaterias.getTxtHorasAutoEstudio().addKeyListener(new TxtVNumeros(
                frmMaterias.getTxtHorasAutoEstudio(), frmMaterias.getLblErrorHorasAutoEstudio()));
        

//Descripocion materia, campo formacion, organizacion curricular, objetivo general y especifico
        frmMaterias.getTxtCampoFormacion().addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                if (modelo.validaciones.Validar.esLetras(frmMaterias.getTxtCampoFormacion().getText()) == false
                        && frmMaterias.getTxtCampoFormacion().getText().equals("") == false) {
                    frmMaterias.getLblErrorCampoFormacion().setVisible(true);
                } else {
                    frmMaterias.getLblErrorCampoFormacion().setVisible(false);
                }
            }
        });
        frmMaterias.getTxtOrganizacionCurricular().addKeyListener(new KeyAdapter() {

            public void keyRealesed(KeyEvent e) {

                if (modelo.validaciones.Validar.esNumeros(frmMaterias.getTxtOrganizacionCurricular().getText()) == false
                        && frmMaterias.getTxtOrganizacionCurricular().getText().equals("") == false) {
                    frmMaterias.getLblErrorOrganizacionCurricular().setVisible(true);
                } else {
                    frmMaterias.getLblErrorOrganizacionCurricular().setVisible(false);
                }
            }
        });

        frmMaterias.getTxtObjetivoGeneral().addKeyListener(new KeyAdapter() {

            public void keyRealesed(KeyEvent e) {

                if (modelo.validaciones.Validar.esNumeros(frmMaterias.getTxtObjetivoGeneral().getText()) == false
                        && frmMaterias.getTxtObjetivoGeneral().getText().equals("") == false) {
                    frmMaterias.getLblErrorObjetivoGeneral().setVisible(true);
                } else {
                    frmMaterias.getLblErrorObjetivoGeneral().setVisible(false);
                }
            }
        });

        frmMaterias.getTxtObjetivoEspecifico().addKeyListener(new KeyAdapter() {

            public void keyRealesed(KeyEvent e) {

                if (modelo.validaciones.Validar.esNumeros(frmMaterias.getTxtObjetivoEspecifico().getText()) == false
                        && frmMaterias.getTxtObjetivoEspecifico().getText().equals("") == false) {
                    frmMaterias.getLblErrorObjetivoEspecifico().setVisible(true);
                } else {
                    frmMaterias.getLblErrorObjetivoEspecifico().setVisible(false);
                }
            }
        });

        frmMaterias.getTxtDescripcionMateria().addKeyListener(new KeyAdapter() {

            public void KeyRealesed(KeyEvent e) {
                if (modelo.validaciones.Validar.esLetras(frmMaterias.getTxtDescripcionMateria().getText()) == false
                        && frmMaterias.getTxtDescripcionMateria().equals("") == false) {
                    frmMaterias.getLblErrorDescripcionMateria().setVisible(true);
                } else {
                    frmMaterias.getLblErrorDescripcionMateria().setVisible(false);
                }
            }
        });

//        frmMaterias.getTxtCodigoMateria().addKeyListener(new KeyAdapter() {
//
//            public void KeyRealesed(KeyEvent e) {
//                if (modelo.validaciones.Validar.esLetras(frmMaterias.getTxtCodigoMateria().getText()) == false
//                        && frmMaterias.getTxtCodigoMateria().getText().equals("") == false) {
//                    frmMaterias.getLblErrorCodigoMateria().setVisible(true);
//                } else {
//                    frmMaterias.getLblErrorCodigoMateria().setVisible(false);
//                }
//            }
//        });
//
//        frmMaterias.getTxtCreditos().addKeyListener(new KeyAdapter() {
//            public void KeyRealesed(KeyEvent e) {
//                if (modelo.validaciones.Validar.esLetras(frmMaterias.getTxtCreditos().getText()) == false
//                        && frmMaterias.getTxtCreditos().getText().equals("") == false) {
//                    frmMaterias.getLblErrorCreditos().setVisible(true);
//                } else {
//                    frmMaterias.getLblErrorCreditos().setVisible(false);
//                }
//            }
//        });
//
//        
//        frmMaterias.getTxtHorasAutoEstudio().addKeyListener(new KeyAdapter() {
//
//            public void keyRealesed(KeyEvent e) {
//                if (modelo.validaciones.Validar.esNumeros(frmMaterias.getTxtHorasAutoEstudio().getText()) == false
//                        && frmMaterias.getTxtHorasAutoEstudio().getText().equals("") == false) {
//                    frmMaterias.getLblErrorHorasAutoEstudio().setVisible(true);
//                } else {
//                    frmMaterias.getLblErrorHorasAutoEstudio().setVisible(false);
//                }
//            }
//        });
//
//        frmMaterias.getTxtHorasDocencia().addKeyListener(new KeyAdapter() {
//
//            public void keyRealesed(KeyEvent e) {
//
//                if (modelo.validaciones.Validar.esNumeros(frmMaterias.getTxtHorasDocencia().getText()) == false
//                        && frmMaterias.getTxtHorasDocencia().getText().equals("") == false) {
//                    frmMaterias.getLblErrorHorasDocencia().setVisible(true);
//                } else {
//                    frmMaterias.getLblErrorHorasDocencia().setVisible(false);
//                }
//            }
//        });
//
//        frmMaterias.getTxtHorasPracticas().addKeyListener(new KeyAdapter() {
//
//            public void keyRealesed(KeyEvent e) {
//
//                if (modelo.validaciones.Validar.esNumeros(frmMaterias.getTxtHorasPracticas().getText()) == false
//                        && frmMaterias.getTxtHorasPracticas().getText().equals("") == false) {
//                    frmMaterias.getLblErrorHorasPracticas().setVisible(true);
//                } else {
//                    frmMaterias.getLblErrorHorasPracticas().setVisible(false);
//                }
//            }
//        });
//
//        frmMaterias.getTxtHorasPresenciales().addKeyListener(new KeyAdapter() {
//
//            public void keyRealesed(KeyEvent e) {
//
//                if (modelo.validaciones.Validar.esNumeros(frmMaterias.getTxtHorasPresenciales().getText()) == false
//                        && frmMaterias.getTxtHorasPresenciales().getText().equals("") == false) {
//                    frmMaterias.getLblErrorHorasPresenciales().setVisible(true);
//                } else {
//                    frmMaterias.getLblErrorHorasPresenciales().setVisible(false);
//                }
//            }
//        });
//
//        frmMaterias.getTxtMateriaCiclo().addKeyListener(new KeyAdapter() {
//
//            public void keyRealesed(KeyEvent e) {
//
//                if (modelo.validaciones.Validar.esNumeros(frmMaterias.getTxtMateriaCiclo().getText()) == false
//                        && frmMaterias.getTxtMateriaCiclo().getText().equals("") == false) {
//                    frmMaterias.getLblErrorMateriaCiclo().setVisible(true);
//                } else {
//                    frmMaterias.getLblErrorMateriaCiclo().setVisible(false);
//                }
//            }
//        });
    }

}
