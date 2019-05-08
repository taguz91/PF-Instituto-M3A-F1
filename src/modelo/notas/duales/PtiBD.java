/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.notas.duales;

import java.util.ArrayList;
import java.util.List;
import modelo.ResourceManager;

/**
 *
 * @author MrRainx
 */
public class PtiBD extends PtiMD {

    public PtiBD(int id, double nota, double totalFaseTeorica) {
        super(id, nota, totalFaseTeorica);
    }

    public PtiBD() {
    }

    public boolean insertar() {
        String INSERT = "";

        return ResourceManager.Statement(INSERT) == null;
    }

    public static List<PtiMD> selectAll() {
        String SELECT = "";

        List<PtiMD> lista = new ArrayList<>();

        return lista;
    }
    
}
