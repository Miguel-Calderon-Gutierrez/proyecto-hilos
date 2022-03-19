/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohilostarea;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author USER
 */
public class Proceso extends Thread {

    int rechazados;
    int atendidos;
    JLabel sillas[], labeltiempo = Ventana_ui.tiempoRestante, labelDosis = Ventana_ui.dosisDisponibles;
    ImageIcon sillaVerde = Ventana_ui.sillaverde;
    ImageIcon sillaRoja = Ventana_ui.sillaRoja;
    Persona[] personas;
    boolean personasnuevas;
    JTextArea area;

    public static int tiempo = Integer.parseInt(Ventana_ui.tiempoFuncionamiento.getText());
    int dosis = Integer.parseInt(Ventana_ui.cantDosis.getText());

    public Proceso(JLabel sillas[], JTextArea area) {
        this.sillas = sillas;
        this.area = area;
        personasnuevas = true;
        personas = new Persona[6];
    }

    public boolean haysillaDisponible() {
        for (int i = 0; i < 6; i++) {
            if (sillas[i].getIcon() == sillaVerde) {
                return true;
            }
        }
        return false;
    }

    public boolean haysillaCupo() {
        for (int i = 0; i < 6; i++) {
            if (personas[i] == null) {
                return true;
            }
        }
        return false;
    }

    public void asignarCupo(Persona p) {
        for (int i = 0; i < 6; i++) {
            if (personas[i] == null) {
                sillas[i].setIcon(sillaRoja);
                personas[i] = p;
                break;
            }
        }
    }

    public void recibirPersonas(Persona[] personas) {
        if (dosis == 0) {
            JOptionPane.showMessageDialog(null, "Proceso detenido por agotamiento de dosis", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            stop();
        }
        if (!personasnuevas) {
//            try {
//                System.out.println("Esperando notificacion");
//                wait();
//            } catch (InterruptedException ex) {
//            }
        } else {
//            System.out.println("----------------------------------------------------------");
//            System.out.println("cantidad de personas que llegaron " + personas.length);

            for (int i = 0; i < personas.length; i++) {

                if (haysillaCupo()) {
                    asignarCupo(personas[i]);
                    area.setText(area.getText() + "\nAceptado y en espera: " + personas[i] + "\n");
                    atendidos += 1;
                    Ventana_ui.cantAtendidos.setText(atendidos + "");
                    // System.out.println("Aceptado");
                } else {

                    rechazados += 1;
                    Ventana_ui.cantRechazados.setText(rechazados + "");
                    //  System.out.println("Rechazado");
                    area.setText(area.getText() + "\nRechazado: " + personas[i] + "\n");

                }
                tiempo -= 1;
                if (tiempo <= 0) {
                    JOptionPane.showMessageDialog(null, "Proceso detenido por tiempo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    stop();
                    labeltiempo.setText("Faltan: " + 0 + "seg para cerrar");
                    break;
                } else {
                    labeltiempo.setText("Faltan: " + tiempo + "seg para cerrar");
                }

                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("no espero en ciclo recibir personas");
                }
            }

            if (!haysillaCupo()) {
                personasnuevas = false;
                vacunar();
            }
        }
    }

    public synchronized void vacunar() {
        if (dosis > 0) {
            int aux = dosis * 6;
            if (tiempo > 0) {
                ReproducirSonido("src/musica/tonoHospital.wav");
                Paciente paciente;
                boolean pararalacabar = false;
                long tiempoaux=System.currentTimeMillis();
                for (int i = 0; i < 6; i++) {
                    
                    sillas[i].setIcon(sillaVerde);

                    paciente = new Paciente(sillas[i].getText(), personas[i]);
                    paciente.getPersona().setTiemposalida(System.currentTimeMillis());
                    paciente.setVacunado(true);
                    personas[i] = null;
                    personasnuevas = true;
                    aux -= 1;
                    labelDosis.setText("Dosis disponibles: " + aux);

                    area.setText(area.getText() + "\n" + paciente.toString() + "\n");
                    tiempo -= 1;
                    if (tiempo <= 0) {
                        pararalacabar = true;
                        labeltiempo.setText("Faltan: " + 0 + "seg para cerrar");
                    } else {
                        labeltiempo.setText("Faltan: " + tiempo + "seg para cerrar");
                    }

                    try {
                        sleep(833,33);
                    } catch (InterruptedException ex) {
                        System.out.println("error en vacunacion");
                    }

                }
                System.out.println("Duracion de vacuna = "+(System.currentTimeMillis()-tiempoaux));
                if (pararalacabar) {
                    JOptionPane.showMessageDialog(null, "Proceso detenido por tiempo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    stop();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Proceso detenido por tiempo", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                stop();
            }
            dosis -= 1;
        } else {
            JOptionPane.showMessageDialog(null, "Proceso detenido por agotamiento de dosis", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            stop();
        }

    }

    public void ReproducirSonido(String nombreSonido) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al reproducir el sonido.");
        }
    }
}
