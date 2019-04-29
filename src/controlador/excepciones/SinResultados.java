/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.excepciones;

/**
 *
 * @author MrRainx
 */
public class SinResultados extends RuntimeException {

    public SinResultados() {
    }

    public SinResultados(String message) {
        super(message);
    }

}
