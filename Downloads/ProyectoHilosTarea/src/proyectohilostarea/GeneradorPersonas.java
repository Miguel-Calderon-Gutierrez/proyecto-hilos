/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohilostarea;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static proyectohilostarea.Proceso.tiempo;

/**
 *
 * @author USER
 */
public class GeneradorPersonas extends Thread {

    Proceso proceso;
    int tiempo = Proceso.tiempo;
    JLabel labeltiempo = Ventana_ui.tiempoRestante;

    public GeneradorPersonas(Proceso proceso) {
        this.proceso = proceso;
    }

    @Override
    public void run() {

        while (true) {

            if (proceso.dosis == 0) {
                JOptionPane.showMessageDialog(null, "Proceso detenido por agotamiento de dosis", "Informacion", JOptionPane.INFORMATION_MESSAGE);

                proceso.stop();
                stop();

            }
            int cantidadGenerada = 1 + (int) (Math.random() * 6);
            Persona[] personas = new Persona[cantidadGenerada];

            for (int i = 0; i < personas.length; i++) {
                personas[i] = new Persona();
            }
            int duerme = 1 + (int) (Math.random() * 3);

            try {
                proceso.recibirPersonas(personas);
            } catch (Exception ex) {
            }

            for (int i = 0; i < duerme; i++) {
                try {
                    tiempo = Proceso.tiempo;
                    tiempo -= 1;
                    if (tiempo <= 0) {
                        proceso.stop();
                        stop();
                        break;
                    }
                    labeltiempo.setText("Faltan: " + tiempo + "seg para cerrar");
                    sleep(1 * 1000);
                } catch (InterruptedException ex) {
                    System.out.println("Esperando el generador");
                }
            }
        }

    }

}
