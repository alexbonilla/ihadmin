/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.entidades;

/**
 *
 * @author Alex
 */
public class AutorizacionMensaje {
    String claveacceso;
    String identificador;
    String tipo;
    String mensaje;
    String infoadicional;

    public String getClaveacceso() {
        return claveacceso;
    }

    public void setClaveacceso(String claveacceso) {
        this.claveacceso = claveacceso;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getInfoadicional() {
        return infoadicional;
    }

    public void setInfoadicional(String infoadicional) {
        this.infoadicional = infoadicional;
    }
    
    
}
