package modelo.silabo.mbd;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import modelo.silabo.SilaboMD;

/**
 *
 * @author gus
 */
public interface ISilaboBD {

    public int guardar(SilaboMD s);

    public SilaboMD getByCarreraPersonaPeriodo(
            String nombreCarrera,
            int idPersona,
            String nombrePeriodo
    );

    public List<SilaboMD> getByCarreraPersona(
            String nombreCarrera,
            int idPersona
    );

    public List<SilaboMD> getAnterioresByMateriaPersona(
            int idPersona,
            int idMateria
    );

    public void setEstado(int idSilabo, int estado);

    public SilaboMD getSilaboById(int idSilabo);

    public void guardarPDFAnalitico(
            int idSilabo,
            FileInputStream fis,
            File f
    );

    public void guardarPDFSilabo(
            int idSilabo,
            FileInputStream fis,
            File f
    );

}
