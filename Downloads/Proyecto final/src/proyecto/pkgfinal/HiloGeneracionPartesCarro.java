/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.pkgfinal;

import java.awt.Color;
import java.awt.Image;
import static java.lang.Thread.sleep;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author USER
 */
public class HiloGeneracionPartesCarro extends Thread {

    JTextArea arearegistro;
    JLabel parte;
    String arregloImagenes[] = {"bomper.png", "chasis.png", "llanta delantera.png", "llanta trasera.png", "parte superior.png"};

    public HiloGeneracionPartesCarro(JTextArea arearegistro, JLabel parte) {
        this.arearegistro = arearegistro;
        this.parte = parte;
    }

    @Override
    public void run() {
        while (true) {
            int numparte = (int) (Math.random() * 5);
            ImageIcon imagen = new ImageIcon("src/img/carro/" + arregloImagenes[numparte]);
            Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(parte.getWidth(), parte.getHeight(), Image.SCALE_DEFAULT));
            parte.setIcon(icono);

            String registro = "Se genero " + arregloImagenes[numparte].replaceAll(".png", " para carro") + "\n";
            parte.setText(arregloImagenes[numparte].replaceAll(".png", " para carro"));
            arearegistro.setText(arearegistro.getText() + registro);
            arearegistro.select(arearegistro.getText().length() - registro.length(), arearegistro.getText().length());
            arearegistro.setSelectedTextColor(new Color(21, 170, 0));

            int x = 0;
            boolean limite = true;
            while (limite) {
                x += 50;
                if (x >= 500) {
                    limite = false;
                }
                parte.setLocation(x, 345);
                try {
                    sleep(Ventana.velocidad / 10);
                } catch (InterruptedException ex) {

                }
            }

        }
    }
}
