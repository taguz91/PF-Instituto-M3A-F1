/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.Interfaces;

import java.util.List;

/**
 *
 * @author diego
 */
public interface IVtn<M> extends Icontroller {

    public void cargarTabla(List<M> ListModel);

    public void cargarTablaFilter(List<M> ListModel);

    public void agregarFila(M model);
}
