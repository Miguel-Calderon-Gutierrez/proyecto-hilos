/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohilostarea;

/**
 *
 * @author USER
 */
public class Persona {

    private static int asignadorID;
    private int id;
    private long tiempollegada;
    private long tiemposalida;

    public Persona() {
        asignadorID += 1;
        this.id = asignadorID;
        this.tiempollegada = System.currentTimeMillis();
        this.tiemposalida = 0;
    }

    public long getTiempollegada() {
        return tiempollegada;
    }

    public void setTiempollegada(long tiempollegada) {
        this.tiempollegada = tiempollegada;
    }

    public long getTiemposalida() {
        return tiemposalida;
    }

    public void setTiemposalida(long tiemposalida) {
        this.tiemposalida = tiemposalida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Persona(" + " id: " + id + ", tiempo llegada: " + tiempollegada + ", tiempo salida: " + tiemposalida + ')';
    }

}
