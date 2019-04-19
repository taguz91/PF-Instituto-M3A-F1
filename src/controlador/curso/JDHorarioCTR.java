package controlador.curso;

import controlador.principal.DependenciasVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalTime;
import modelo.ConectarDB;
import modelo.curso.CursoMD;
import modelo.curso.SesionClaseBD;
import modelo.validaciones.TxtVHora;
import modelo.validaciones.Validar;
import vista.curso.JDHorario;
import vista.curso.PnlHorarioClase;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDHorarioCTR extends DependenciasVtnCTR {

    private final JDHorario jd;
    private final CursoMD curso;
    private final SesionClaseBD bd;
    //Variables para el horario
    private int idSesion, posFila, posColumna, dia, horaC, horaT, minutoC, minutoT;
    //Cargamos datos
    private String jornada, horaIni, horaFin;
    private String[] hs;
    private boolean guardar, editar;
    private PnlHorarioClase pnl;
    private PnlHorarioClaseCTR ctrHClase;
    private LocalTime inicio, fin;

    public JDHorarioCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin,
            CursoMD curso) {
        super(conecta, vtnPrin, ctrPrin);
        this.curso = curso;
        this.bd = new SesionClaseBD(conecta);
        this.jd = new JDHorario(vtnPrin, false);
        jd.setLocationRelativeTo(vtnPrin);

    }

    public void iniciar() {
        cargarDatos();
        horarioCurso();
        iniciarValidaciones();
        ctrPrin.eventoJDCerrar(jd);

        llenarCmbDias();

        jd.setVisible(true);

        jd.getBtnGuardar().addActionListener(e -> guardar());
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
            System.out.println(horaC + " " + horaT + " " + minutoC + " " + minutoT);
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
//            System.out.println(dia);
//            System.out.println(horaIni);
//            System.out.println(horaFin);
            inicio = LocalTime.of(horaC, minutoC);
            fin = LocalTime.of(horaT, minutoT);
            bd.setCurso(curso);
            bd.setDia(dia);
            bd.setHoraIni(inicio);
            bd.setHoraFin(fin);

            bd.ingresar();
            //Actualizamos el horario 
            ctrHClase.actualizar(dia);
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

    private void horarioCurso() {
        pnl = new PnlHorarioClase();
        ctrHClase = new PnlHorarioClaseCTR(pnl, curso, bd);
        ctrHClase.iniciar();
        jd.getTbpHorario().addTab("Horario Clase", pnl);
    }

    private void cargarDatos() {
        //Titulo de la ventana 
        jd.setTitle("Horario - " + curso.getId_materia().getNombre() + " - " + curso.getCurso_nombre());
        jd.getLblPrd().setText(curso.getId_prd_lectivo().getNombre_PerLectivo());
        jd.getLblMateria().setText(curso.getId_materia().getNombre());
        switch (curso.getCurso_nombre().charAt(0)) {
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
        jd.getLblDocente().setText(curso.getId_docente().getNombreCorto());
        jd.getLblCurso().setText(curso.getCurso_nombre());
        jd.getLblCapacidad().setText(curso.getCurso_capacidad() + "");
    }

}
