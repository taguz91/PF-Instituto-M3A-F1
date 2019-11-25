package controlador.pagos;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.CMBAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.estilo.TblEstilo;
import modelo.pagos.UtilComprobanteBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.periodolectivo.CMBPeriodoLectivoBD;
import modelo.persona.AlumnoMD;
import vista.pagos.FrmComprobantes;

/**
 *
 * @author gus
 */
public class FRMComprobanteCTR extends DCTR {

    private final FrmComprobantes FRM = new FrmComprobantes();
    // Lista de objetos  
    private List<PeriodoLectivoMD> pls;
    private List<AlumnoMD> as;
    private List<MallaAlumnoMD> ms;
    // Conexiones a la base de datos  
    private final CMBPeriodoLectivoBD PLBD = CMBPeriodoLectivoBD.single();
    private final CMBAlumnoBD CABD = CMBAlumnoBD.single();
    private final UtilComprobanteBD UCBD = UtilComprobanteBD.single();
    // Modelo de las tablas 
    private DefaultTableModel mdTblAlum, mdTblMate;
    // Para guardar la foto  
    private FileInputStream fis = null;
    private int lonBytes = 0;

    public FRMComprobanteCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void iniciar() {
        iniciarCmbPeriodo();
        inicarTbls();
        iniciarBuscarAlumno();
        iniciarAcciones();
        iniciarEventos();
        ctrPrin.agregarVtn(FRM);
    }

    private void iniciarAcciones() {
        FRM.getBtnBuscarImagen().addActionListener(e -> buscarImagen());
    }

    private void inicarTbls() {
        String[] T_TBLALUM = {"Nombre"};
        mdTblAlum = TblEstilo.modelTblSinEditar(T_TBLALUM);
        FRM.getTblAlumnos().setModel(mdTblAlum);
        String[] T_TBLMATERIA = {"Materia", "# Matricula", "Monto"};
        mdTblMate = TblEstilo.modelTblSinEditar(T_TBLMATERIA);
        FRM.getTblMaterias().setModel(mdTblMate);
    }

    private void iniciarCmbPeriodo() {
        pls = PLBD.getForCmbSoloAbiertos();
        FRM.getCmbPeriodo().removeAllItems();
        FRM.getCmbPeriodo().addItem("Seleccione");
        pls.forEach(pl -> {
            FRM.getCmbPeriodo().addItem(pl.getNombre());
        });
    }

    private void iniciarBuscarAlumno() {
        FRM.getBtnBuscarAlumno().addActionListener(e -> buscarAlumno(
                FRM.getTxtAlumno().getText().trim()
        ));
        FRM.getTxtAlumno().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    buscarAlumno(FRM.getTxtAlumno().getText().trim());
                }
            }
        });
    }

    private void buscarAlumno(String aguja) {
        int posPeriodo = FRM.getCmbPeriodo().getSelectedIndex();
        if (posPeriodo > 0) {
            cursorCarga(FRM);
            as = CABD.getForBusquedaPeriodo(
                    aguja,
                    pls.get(posPeriodo - 1).getID()
            );
            mdTblAlum.setRowCount(0);
            as.forEach(a -> {
                mdTblAlum.addRow(new Object[]{a.getApellidosNombres()});
            });

            cursorNormal(FRM);
        } else {
            JOptionPane.showMessageDialog(
                    FRM,
                    "Debe seleccionar un periodo antes de buscar."
            );
        }
    }

    // Para buscar el comprobante 
    private void buscarImagen() {
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = j.showOpenDialog(null);
        if (estado == JFileChooser.APPROVE_OPTION) {
            FRM.getLblImagen().setIcon(null);
            try {
                //Para guardar la foto  
                fis = new FileInputStream(j.getSelectedFile());
                lonBytes = (int) j.getSelectedFile().length();

                Image icono = ImageIO.read(j.getSelectedFile())
                        .getScaledInstance(
                                FRM.getLblImagen().getWidth(),
                                FRM.getLblImagen().getHeight(),
                                Image.SCALE_SMOOTH
                        );
                FRM.getLblImagen().setIcon(new ImageIcon(icono));
                FRM.getLblImagen().updateUI();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "No se puede cargar la imagen.\n"
                        + e.getMessage()
                );
            }
        }
    }

    private void iniciarEventos() {
        FRM.getTblAlumnos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarMateriasByAlumno();
            }
        });
        FRM.getCmbMaterias().addActionListener(e -> {
            if (vtnCargada) {
                selectMateria();
            }
        });
    }

    private void cargarMateriasByAlumno() {
        int posAlmn = FRM.getTblAlumnos().getSelectedRow();
        if (posAlmn >= 0) {
            FRM.getLblAlumno().setText(as.get(posAlmn).getApellidosNombres());
            cursorCarga(FRM);
            ms = UCBD.getByAlumno(as.get(posAlmn).getId_Alumno());
            FRM.getCmbMaterias().removeAllItems();
            ms.forEach(m -> {
                FRM.getCmbMaterias().addItem(
                        m.getMateria().getCodigo() + " | "
                        + m.getMateria().getNombre()
                );
            });
            cursorNormal(FRM);
            selectMateria();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No selecciono un alumno."
            );
        }
    }

    private void selectMateria() {
        int posMateria = FRM.getCmbMaterias().getSelectedIndex();
        if (posMateria >= 0 && ms.size() > 0) {
            FRM.getTxtNoMatricula().setText(
                    ms.get(posMateria).getMallaNumMatricula() + ""
            );
        }
    }

}
