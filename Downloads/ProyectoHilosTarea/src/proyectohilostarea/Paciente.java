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
public class Paciente {

    private String silla;
    private boolean vacunado;
    private Persona persona;

    public Paciente(String silla, Persona persona) {
        this.silla = silla;
        this.vacunado = false;
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getSilla() {
        return silla;
    }

    public void setSilla(String silla) {
        this.silla = silla;
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    @Override
    public String toString() {
        return "Paciente(" + persona.getId() + ", Duracion: " + ((persona.getTiemposalida() - persona.getTiempollegada()) / 1000.0) + " segundos, silla:" + silla + ", vacunado:" + vacunado + ')';
    }

}
