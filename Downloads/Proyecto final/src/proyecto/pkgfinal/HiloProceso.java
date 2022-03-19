/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.pkgfinal;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author USER
 */
public class HiloProceso extends Thread {

    JLabel vehiculo, parte, partesLlegada[], aux;
    ArrayList<String> partessvehiculo;
    public static ArrayList<String> reporteMoto;
    public static ArrayList<String> reporteCarro;
    ImageIcon falta = new ImageIcon("src/img/falta.png"), ok = new ImageIcon("src/img/ok.png");
    JTextArea arearesgistro, areaReporte;
    public static int cantMotos, cantAutos;
    long inicio, fin, inicio2, fin2;
    boolean nuevoCarro = true, nuevaMoto = true;

    public HiloProceso(JLabel vehiculo, JLabel parte, JTextArea arearesgistro, JTextArea areaReporte, JLabel[] partes) {
        this.vehiculo = vehiculo;
        this.parte = parte;
        this.arearesgistro = arearesgistro;
        partessvehiculo = new ArrayList<String>();
        reporteCarro = new ArrayList<String>();
        reporteMoto = new ArrayList<String>();
        partesLlegada = partes;
        this.areaReporte = areaReporte;

        areaReporte.setText("                                                                    >>>>ficha con información gerencial<<<<\n"
                + "Fecha y hora de expedicion de reporte: " + (LocalDateTime.now()) + "\n"
                + "Reporte motos:\n"
                + "     » Cantidad de motos fabricadas: " + cantMotos + "\n"
                + "\nReporte Autos:\n"
                + "\n" + "    » Cantidad de autos fabricados: " + cantAutos + "\n");

    }

    @Override
    public void run() {
        while (true) {
            evaluar();
        }
    }

    private synchronized void evaluar() {

        if (parte.getX() == 500) {
            if (nuevaMoto) {

                inicio = System.currentTimeMillis();
                nuevaMoto = false;
            }
            if (nuevoCarro) {

                inicio2 = System.currentTimeMillis();
                nuevoCarro = false;
            }

            if (!partessvehiculo.contains(parte.getText())) {
                partessvehiculo.add(parte.getText());
                partesLlegada[partessvehiculo.size() - 1].setText(parte.getText());
                partesLlegada[partessvehiculo.size() - 1].setIcon(ok);
                ReproducirSonido("src/sonidos/bien.wav");
                arearesgistro.setText(arearesgistro.getText() + "Parte " + parte.getText() + " aceptada\n");
                parte.setLocation(10, parte.getY());
            } else {
                arearesgistro.setText(arearesgistro.getText() + "Parte " + parte.getText() + " rechazada\n");
                ReproducirSonido("src/sonidos/mal.wav");
                parte.setBackground(Color.red);
                parte.setLocation(10, parte.getY());
            }
            if (partessvehiculo.size() == 5) {

                String nombre = vehiculo.getIcon().toString().replaceAll("src/img/", "").replaceAll(".png", "");

                if (nombre.equalsIgnoreCase("moto")) {

                    arearesgistro.setText(arearesgistro.getText()
                            + "\n------------------------------------------\n"
                            + nombre + " completada \n"
                            + "------------------------------------------\n\n");
                    fin = System.currentTimeMillis();
                    reporteMoto.add("Tiempo fabricacion: " + ((fin - inicio) / 1000));
                    cantMotos++;
                    nuevaMoto = true;

                } else {
                    arearesgistro.setText(arearesgistro.getText()
                            + "\n------------------------------------------\n"
                            + nombre + " completado \n"
                            + "------------------------------------------\n\n");
                    fin2 = System.currentTimeMillis();
                    reporteCarro.add("Tiempo fabricacion: " + ((fin2 - inicio2) / 1000));
                    cantAutos++;
                    nuevoCarro = true;
                }
                areaReporte.setText("");
                areaReporte.setText("                                                                    >>>>ficha con información gerencial<<<<\n"
                        + "Fecha y hora de expedicion de reporte: " + (LocalDateTime.now()) + "\n"
                        + "Reporte motos:\n"
                        + "     » Cantidad de motos fabricadas: " + cantMotos + "\n"
                );
                for (int i = 0; i < reporteMoto.size(); i++) {
                    areaReporte.setText(areaReporte.getText() + "\n" + "        ■ Moto " + (i + 1) + ", " + reporteMoto.get(i) + " segundos\n");
                }

                areaReporte.setText(areaReporte.getText() + "\nReporte Autos:\n");
                areaReporte.setText(areaReporte.getText() + "\n" + "    » Cantidad de autos fabricados: " + cantAutos + "\n");

                for (int j = 0; j < reporteCarro.size(); j++) {
                    areaReporte.setText(areaReporte.getText() + "\n" + "        ■ Auto " + (j + 1) + ", " + reporteCarro.get(j) + "segundos\n");
                }

                for (int i = 100; i < 250; i += 10) {
                    vehiculo.setLocation(i, 90);
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {

                    }
                }

                vehiculo.setLocation(100, 90);
                partessvehiculo = new ArrayList<String>();

                for (int i = 0; i < 5; i++) {
                    partesLlegada[i].setText("parte " + nombre + " " + (1 + i));
                    partesLlegada[i].setIcon(falta);
                }
            }
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
