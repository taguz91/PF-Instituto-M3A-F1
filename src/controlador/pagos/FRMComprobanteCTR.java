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
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.CMBAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.estilo.TblEstilo;
import modelo.pagos.ComprobantePagoBD;
import modelo.pagos.ComprobantePagoMD;
import modelo.pagos.PagoMateriaBD;
import modelo.pagos.PagoMateriaMD;
import modelo.pagos.UtilComprobanteBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.periodolectivo.CMBPeriodoLectivoBD;
import modelo.persona.AlumnoMD;
import modelo.validaciones.Validar;
import vista.pagos.FrmComprobantes;

/**
 *
 * @author gus
 */
public class FRMComprobanteCTR extends DCTR {

    private final FrmComprobantes FRM = new FrmComprobantes();
    // Para guardar 
    private ComprobantePagoMD cp;
    private List<PagoMateriaMD> pms;
    // Lista de objetos  
    private List<PeriodoLectivoMD> pls;
    private List<AlumnoMD> as;
    private List<MallaAlumnoMD> ms;
    // Conexiones a la base de datos  
    private final CMBPeriodoLectivoBD PLBD = CMBPeriodoLectivoBD.single();
    private final CMBAlumnoBD CABD = CMBAlumnoBD.single();
    private final UtilComprobanteBD UCBD = UtilComprobanteBD.single();
    private final ComprobantePagoBD CPBD = ComprobantePagoBD.single();
    private final PagoMateriaBD PMBD = PagoMateriaBD.single();
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
        FRM.getBtnGuardar().addActionListener(e -> guardar());
        FRM.getBtnAdd().addActionListener(e -> agregarMateria());
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
        cp = new ComprobantePagoMD();
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
                cargarMateriasAlumno();
                existeComprobantePago();
            }
        });
        FRM.getCmbMaterias().addActionListener(e -> {
            if (vtnCargada) {
                selectMateria();
            }
        });
    }

    private void cargarMateriasAlumno() {
        int posAlmn = FRM.getTblAlumnos().getSelectedRow();
        if (posAlmn >= 0) {
            FRM.getLblAlumno().setText(as.get(posAlmn).getApellidosNombres());
            cursorCarga(FRM);
            ms = UCBD.getByAlumno(as.get(posAlmn).getId_Alumno());
            llenarCmbMateria(ms);
            cursorNormal(FRM);
            selectMateria();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No selecciono un alumno."
            );
        }
    }

    private void existeComprobantePago() {
        int posAlmn = FRM.getTblAlumnos().getSelectedRow();
        int posPeriodo = FRM.getCmbPeriodo().getSelectedIndex();
        if (posAlmn >= 0 && posPeriodo > 0) {
            cp = CPBD.getByAlumnoPeriodo(
                    as.get(posAlmn).getId_Alumno(),
                    pls.get(posPeriodo - 1).getID()
            );
            if (cp.getComprobante() != null) {
                Image icono = cp.getComprobante().getScaledInstance(
                        FRM.getLblImagen().getWidth(),
                        FRM.getLblImagen().getHeight(),
                        Image.SCALE_SMOOTH
                );
                FRM.getLblImagen().setIcon(new ImageIcon(icono));
                FRM.getLblImagen().updateUI();
            }
            FRM.getTxtCodigo().setText(cp.getCodigo());
            FRM.getTxtObservaciones().setText(cp.getObservaciones());
            FRM.getTxtMontoTotal().setText(cp.getTotal() + "");
            fis = null;
            lonBytes = 0;
            // Malla 
            if (cp.getId() != 0) {
                pms = PMBD.getByComprobante(cp.getId());
                llenarTblMateria(pms);
            } else {
                pms = null;
                pms = new ArrayList<>();
                mdTblMate.setRowCount(0);
            }
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

    private void guardar() {
        int posAlmn = FRM.getTblAlumnos().getSelectedRow();
        int posPeriodo = FRM.getCmbPeriodo().getSelectedIndex();
        if (posAlmn >= 0 && posPeriodo > 0 && frmValido()) {
            cp.setPeriodo(pls.get(posPeriodo - 1));
            cp.setAlumno(as.get(posAlmn));
            cp.setTotal(
                    Double.parseDouble(FRM.getTxtMontoTotal().getText())
            );
            cp.setObservaciones(
                    FRM.getTxtObservaciones().getText()
            );
            cp.setFile(fis);
            cp.setLongBytes(lonBytes);
            cp.setCodigo(
                    FRM.getTxtCodigo().getText()
            );
            cursorCarga(FRM);
            if (cp.getId() == 0) {
                int idGenerado = CPBD.guardar(cp);
                if (idGenerado > 0) {
                    cp.setId(idGenerado);
                    guardarPagos();
                    JOptionPane.showMessageDialog(
                            FRM,
                            "Guardamos correctamente el comprobante."
                    );
                }
            } else {
                if (CPBD.editar(cp) > 0) {
                    guardarPagos();
                    JOptionPane.showMessageDialog(
                            FRM,
                            "Editamos correctamente el comprobante."
                    );
                }
                if (fis != null && lonBytes != 0) {
                    CPBD.editarFoto(cp);
                }
            }
            cursorNormal(FRM);
        } else {
            JOptionPane.showMessageDialog(
                    FRM,
                    "No tenemos datos que guardar, "
                    + "debe llenar el formulario."
            );
        }
    }

    private void guardarPagos() {
        pms.forEach(p -> {
            if (p.getId() != 0) {
                PMBD.editar(p);
            } else {
                PMBD.guardar(p);
            }
        });
    }

    private boolean frmValido() {
        boolean valido = !FRM.getTxtCodigo().getText().equals("");
        if (!Validar.esNumerosDecimales(FRM.getTxtMontoTotal().getText())) {
            FRM.getLblEstado().setText("Debe ingresar un monto valido");
            valido = false;
        }
        return valido;
    }

    private void agregarMateria() {
        PagoMateriaMD pm = new PagoMateriaMD();
        int posMateria = FRM.getCmbMaterias().getSelectedIndex();
        if (posMateria >= 0
                && !FRM.getCmbMaterias().getSelectedItem().toString().equals("No tiene materias pendientes.")
                && frmMateriaValida()) {

            pm.setComprobante(cp);
            pm.setMallaAlumno(ms.get(posMateria));
            pm.setNumMatricula(
                    Integer.parseInt(FRM.getTxtNoMatricula().getText())
            );
            pm.setPago(
                    Double.parseDouble(FRM.getTxtMonto().getText())
            );
            pms.add(pm);
            ms.remove(posMateria);
            FRM.getTxtMonto().setText("");
            FRM.getTxtNoMatricula().setText("");
            llenarCmbMateria(ms);
            llenarTblMateria(pms);
        } else {
            JOptionPane.showMessageDialog(
                    FRM,
                    "No tenemos todos los datos "
                    + "necesarios para agregar la materia."
            );
        }
    }

    private boolean frmMateriaValida() {
        return Validar.esNumeros(FRM.getTxtNoMatricula().getText())
                && Validar.esNumerosDecimales(FRM.getTxtMonto().getText());
    }

    private void llenarCmbMateria(List<MallaAlumnoMD> ms) {
        FRM.getCmbMaterias().removeAllItems();
        ms.forEach(m -> {
            FRM.getCmbMaterias().addItem(
                    m.getMateria().getCodigo() + " | "
                    + m.getMateria().getNombre()
            );
        });
        // Si no tenemos agregamos que no existen materias
        if (ms.isEmpty()) {
            FRM.getCmbMaterias().addItem("No tiene materias pendientes.");
        }
    }

    private void llenarTblMateria(List<PagoMateriaMD> pms) {
        mdTblMate.setRowCount(0);
        // "Materia", "# Matricula", "Monto"
        pms.forEach(pm -> {
            Object[] r = {
                pm.getMallaAlumno().getMateria().getNombre(),
                pm.getNumMatricula(),
                pm.getPago()
            };
            mdTblMate.addRow(r);
        });
    }

}
