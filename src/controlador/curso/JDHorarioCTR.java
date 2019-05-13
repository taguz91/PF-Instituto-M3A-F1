package controlador.curso;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import modelo.curso.CursoMD;
import modelo.curso.SesionClaseBD;
import modelo.curso.SesionClaseMD;
import modelo.validaciones.TxtVHora;
import modelo.validaciones.Validar;
import vista.curso.JDHorario;
import vista.curso.PnlHorarioClase;

/**
 *
 * @author Johnny
 */
public class JDHorarioCTR extends DVtnCTR {

    private final JDHorario jd;
    private final CursoMD curso;
    private final SesionClaseBD bd;
    //Variables para el horario
    private int idSesion, posFil, posColum, dia, horaC, horaT, minutoC, minutoT;
    //Cargamos datos
    private String jornada, horaIni, horaFin;
    private String[] hs;
    private boolean guardar, editar;
    private PnlHorarioClase pnl, pnlCurso;
    private PnlHorarioClaseCTR ctrHClase;
    private PnlHorarioCursoCTR ctrHCurso;
    private LocalTime inicio, fin;
    private SesionClaseMD sesion;

    public JDHorarioCTR(VtnPrincipalCTR ctrPrin,
            CursoMD curso) {
        super(ctrPrin);
        this.curso = curso;
        this.bd = new SesionClaseBD(ctrPrin.getConecta());
        this.jd = new JDHorario(ctrPrin.getVtnPrin(), false);
        jd.setLocationRelativeTo(ctrPrin.getVtnPrin());

    }

    public void iniciar() {
        cargarDatos();
        horarioClase();
        iniciarValidaciones();
        ctrPrin.eventoJDCerrar(jd);

        llenarCmbDias();

        jd.setVisible(true);
        jd.getBtnCancelar().setVisible(false);
        jd.getBtnCancelar().addActionListener(e -> clickCancelar());

        jd.getBtnGuardar().addActionListener(e -> guardar());
        clickTbl();
        horarioCurso();
        jd.getTbpHorario().addChangeListener(e -> clickTbp());

    }

    /**
     * Dar click al contenedor de panles de curso
     */
    private void clickTbp() {
        System.out.println("Cambiamos de panle");
        if (jd.getTbpHorario().getSelectedIndex() == 1) {
            //Inciamos el horario del curso completo  
            ctrHCurso.iniciar();
        }
    }

    private void clickCancelar() {
        editar = false;
        idSesion = 0;
        limpiarFrm();
    }

    private void llenarCmbDias() {
        jd.getCmbDia().removeAllItems();
        jd.getCmbDia().addItem("Seleccione");
        for (int i = 1; i < ctrHClase.getjSelec().length; i++) {
            jd.getCmbDia().addItem(ctrHClase.getjSelec()[i]);
        }
    }

    private void iniciarValidaciones() {
        jd.getTxtHoraInicio().addKeyListener(new TxtVHora(jd.getTxtHoraInicio()));
        jd.getTxtHoraFin().addKeyListener(new TxtVHora(jd.getTxtHoraFin()));
    }

    private void guardar() {
        guardar = true;
        dia = jd.getCmbDia().getSelectedIndex();
        horaIni = jd.getTxtHoraInicio().getText().trim();
        horaFin = jd.getTxtHoraFin().getText().trim();

        if (dia == 0) {
            guardar = false;
            jd.getLblError().setText("Debe seleccionar un dia.");
        } else {
            jd.getLblError().setText("");
        }

        if (!Validar.esHora(horaIni) || !Validar.esHora(horaFin)) {
            guardar = false;
            jd.getLblError().setText("<html>Debe ingresar horas validas <br>08:00 - 20:00</html>");
        } else {
            jd.getLblError().setText("");

            hs = horaIni.split(":");
            horaC = Integer.parseInt(hs[0]);
            minutoC = Integer.parseInt(hs[1]);
            hs = horaFin.split(":");
            horaT = Integer.parseInt(hs[0]);
            minutoT = Integer.parseInt(hs[1]);
            //System.out.println(horaC + " " + horaT + " " + minutoC + " " + minutoT);
            if (horaC > 22 || horaC < 7 || horaT > 22 || horaC < 7 || minutoC != 0 || minutoT != 0) {
                guardar = false;
                jd.getLblError().setText("<html>Esta fuera de rango recuerde el formato: <br>08:00 - 20:00</html>");
            } else {
                jd.getLblError().setText("");
                if (horaC > horaT) {
                    jd.getLblError().setText("<html>La clase debe empezar antes <br>" + horaIni + " - " + horaFin + "</html>");
                } else {
                    jd.getLblError().setText("");
                    todosLosCamposLlenos();
                }
            }

            if ((horaT - horaC) > 1 && editar) {
                jd.getLblError().setText("<html>Debe ingresar solamente una hora: <br>08:00 - 09:00</html>");
                guardar = false;
            }
        }
        if (guardar) {
            guardar = false;
            for (String hSelec : ctrHClase.gethSelec()) {
                if (hSelec.equals(horaIni)) {
                    guardar = true;
                }
            }

            for (String hSelec : ctrHClase.gethSelec()) {
                if (hSelec.equals(horaFin)) {
                    guardar = true;
                }
            }
            if (!guardar) {
                jd.getLblError().setText("<html>Recuerde que esta en " + jornada + "<br>  Horario: "
                        + ctrHClase.gethSelec()[0] + " - "
                        + ctrHClase.gethSelec()[ctrHClase.gethSelec().length - 1] + "</html>");
            } else {
                jd.getLblError().setText("");
            }
        }

        if (guardar) {
            String nsql = "";
            for (int i = horaC; i < horaT; i++) {
                inicio = LocalTime.of(i, minutoC);
                fin = LocalTime.of((i + 1), minutoT);
                bd.setCurso(curso);
                bd.setDia(dia);
                bd.setHoraIni(inicio);
                bd.setHoraFin(fin);
                nsql += bd.obtenerInsert() + "\n";
            }

            if (editar) {
                bd.editar(idSesion);
                idSesion = 0;
                editar = false;
                jd.getBtnCancelar().setVisible(false);
            } else {
                bd.ingresarHorarios(nsql);
            }

            //Actualizamos el horario 
            ctrHClase.actualizar(dia);
            limpiarFrm();
        }
    }

    private void todosLosCamposLlenos() {
        if (horaIni.equals("") || horaFin.equals("") || dia == 0) {
            guardar = false;
            jd.getLblError().setText("<html>Todos los campos son obligatorios <br> Formato hora: 08:00 - 20:00 </html>");
        } else {
            jd.getLblError().setText("");
        }
    }

    private void horarioClase() {
        pnl = new PnlHorarioClase();
        ctrHClase = new PnlHorarioClaseCTR(pnl, curso, bd);
        ctrHClase.iniciar();
        jd.getTbpHorario().addTab("Horario Clase", pnl);
    }

    private void horarioCurso() {
        pnlCurso = new PnlHorarioClase();
        ctrHCurso = new PnlHorarioCursoCTR(pnlCurso, curso, bd);
        jd.getTbpHorario().addTab("Horario Curso", pnlCurso);
    }

    private void cargarDatos() {
        //Titulo de la ventana 
        jd.setTitle("Horario - " + curso.getMateria().getNombre() + " - " + curso.getNombre());
        jd.getLblPrd().setText(curso.getPeriodo().getNombre_PerLectivo());
        jd.getLblMateria().setText(curso.getMateria().getNombre());
        switch (curso.getNombre().charAt(0)) {
            case 'M':
                jornada = "MATUTINA";
                break;
            case 'V':
                jornada = "VESPERTINA";
                break;
            case 'N':
                jornada = "NOCTURNA";
                break;
            default:
                jornada = "NA";
                break;
        }
        jd.getLblJornada().setText(jornada);
        jd.getLblDocente().setText(curso.getDocente().getNombreCorto());
        jd.getLblCurso().setText(curso.getNombre());
        jd.getLblCapacidad().setText(curso.getCapacidad() + "");
    }

    private void clickTbl() {
        pnl.getTblHorario().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                posFil = pnl.getTblHorario().getSelectedRow();
                posColum = pnl.getTblHorario().getSelectedColumn();
                if (pnl.getTblHorario().getValueAt(posFil, posColum) != null) {
                    System.out.println(pnl.getTblHorario().getValueAt(posFil, posColum));
                    System.out.println(pnl.getTblHorario().getValueAt(posFil, posColum).toString().split("%")[0]);

                    idSesion = Integer.parseInt(
                            pnl.getTblHorario().getValueAt(posFil, posColum).toString().split("%")[0]);
                    sesion = bd.buscarSesion(idSesion);
                    int r = JOptionPane.showOptionDialog(ctrPrin.getVtnPrin(), "Selecciono: " + idSesion + " "
                            + jd.getCmbDia().getItemAt(sesion.getDia()) + "\nHora inicio: " + sesion.getHoraIni() + "\n"
                            + "Hora fin: " + sesion.getHoraFin(), "Sesion Clase",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, new Object[]{"Editar", "Eliminar", "Cancelar"}, "Cancelar");
                    switch (r) {
                        case 0:
                            jd.getCmbDia().setSelectedIndex(sesion.getDia());
                            jd.getTxtHoraInicio().setText(sesion.getHoraIni().toString());
                            jd.getTxtHoraFin().setText(sesion.getHoraFin().toString());
                            editar = true;
                            jd.getBtnCancelar().setVisible(true);
                            break;
                        case 1:
                            idSesion = sesion.getId();
                            bd.eliminar(idSesion);
                            pnl.getTblHorario().setValueAt(null, posFil, posColum);
                            ctrHClase.actualizar(sesion.getDia());
                            break;
                        default:
                            System.out.println("Desidio cancelar");
                            break;
                    }
                }
            }
        });
    }

    private void limpiarFrm() {
        jd.getCmbDia().setSelectedIndex(0);
        jd.getTxtHoraFin().setText("");
        jd.getTxtHoraInicio().setText("");
        jd.getLblError().setText("");
    }

}
