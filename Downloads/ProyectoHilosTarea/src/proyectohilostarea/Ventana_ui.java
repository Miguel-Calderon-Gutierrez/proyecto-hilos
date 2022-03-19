/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohilostarea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author USER
 */
public class Ventana_ui extends JFrame implements ActionListener, KeyListener {

    JTabbedPane pestañas;
    JPanel panelIngreso, panelPrincipal, panelSillas, panelProceso, panelTabla,
            panelDatos, panelBotones, panelsillasInterno, panelResultados, panelDatosResultados;
    public static JTextField tiempoFuncionamiento, cantDosis;
    JLabel logoFizer, iconoCantidad, iconoTiempo, tituloProceso,
            tituloSillas, tituloDastosUsuario,
            logoResultados, tituloAtendidos, tituloRechazados;
    JButton botonAbrir, botonTerminar, botonSuspender, botonReanudar;
    JLabel sillas[];
    JTextArea areaResultados;
    JScrollPane scrollPane;
    Proceso p;
    GeneradorPersonas gp;
    public static ImageIcon sillaverde = new ImageIcon("src/imagenes/sillaVerde.png");
    public static ImageIcon sillaRoja = new ImageIcon("src/imagenes/sillaRoja.png");
    public static JLabel cantAtendidos, cantRechazados, tiempoRestante, dosisDisponibles;

    public Ventana_ui() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(51, 153, 255));
        setSize(1350, 700);

        UIManager.put("TabbedPane.selected", new Color(0, 203, 182));

        pestañas = new JTabbedPane();
        pestañas.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        pestañas.setForeground(Color.BLACK);

        //--------------------------PANEL Inicio pestaña 1------------------------------
        Font fuente = new Font(Font.DIALOG_INPUT, Font.BOLD, 28);
        panelIngreso = new JPanel();
        panelIngreso.setLayout(null);
        panelIngreso.setBackground(Color.WHITE);

        logoFizer = new JLabel(new ImageIcon("src/imagenes/Tarritopfizer.png"), SwingConstants.CENTER);
        logoFizer.setBounds(380, 0, 500, 400);

        tiempoFuncionamiento = new JTextField();
        tiempoFuncionamiento.addKeyListener(this);
        tiempoFuncionamiento.setFont(fuente);

        iconoTiempo = new JLabel(new ImageIcon("src/imagenes/tiempo.png"));
        iconoTiempo.setBounds(428, 390, 72, 50);

        tiempoFuncionamiento.setBounds(500, 390, 250, 50);
        tiempoFuncionamiento.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Tiempo de funcionamiento(en segundos)"));

        cantDosis = new JTextField();
        cantDosis.addKeyListener(this);
        cantDosis.setFont(fuente);

        iconoCantidad = new JLabel(new ImageIcon("src/imagenes/numeral.png"));
        iconoCantidad.setBounds(428, 470, 72, 50);

        cantDosis.setBounds(500, 470, 250, 50);
        cantDosis.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Cantidad de paquetes de Dosis"));

        botonAbrir = new JButton("ABRIR EL CENTRO DE VACUNACION");
        botonAbrir.addActionListener(this);
        Font fuente2 = new Font(Font.DIALOG_INPUT, Font.BOLD, 12);

        botonAbrir.setBackground(new Color(51, 153, 255));
        botonAbrir.setFont(fuente2);
        botonAbrir.setBounds(500, 550, 250, 30);

        panelIngreso.add(logoFizer);
        panelIngreso.add(tiempoFuncionamiento);
        panelIngreso.add(iconoTiempo);
        panelIngreso.add(cantDosis);
        panelIngreso.add(iconoCantidad);
        panelIngreso.add(botonAbrir);

        //------------------PANEL PROCESO pestaña 2---------------------------------------
        panelProceso = new JPanel();
        panelProceso.setLayout(new BorderLayout());
        panelProceso.setBackground(Color.WHITE);

        //----TITULO
        tituloProceso = new JLabel("Proceso de vacunacion", SwingConstants.CENTER);
        tituloProceso.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 45));
        tituloProceso.setForeground(new Color(51, 153, 255));

        //------------------------Panel principal 
        //--SILLAS
        panelPrincipal = new JPanel(new GridLayout(1, 2));
        panelSillas = new JPanel(new BorderLayout());
        panelSillas.setBackground(Color.WHITE);

        tituloSillas = new JLabel("Sillas usuarios", SwingConstants.CENTER);
        tituloSillas.setFont(fuente);

        panelSillas.add(tituloSillas, BorderLayout.NORTH);
        panelsillasInterno = new JPanel(new GridLayout(3, 3));
        panelsillasInterno.setBackground(Color.WHITE);

        sillas = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            sillas[i] = new JLabel(sillaverde);
            sillas[i].setText("Silla #" + (1 + i));
            panelsillasInterno.add(sillas[i]);
        }
        panelSillas.add(panelsillasInterno, BorderLayout.CENTER);
        panelTabla = new JPanel(new BorderLayout());

        panelPrincipal.add(panelSillas);

        //--DATOS: TIEMPO Y DOSIS
        panelDatos = new JPanel(new GridLayout(1, 2));
        panelDatos.setBackground(Color.WHITE);

        dosisDisponibles = new JLabel("Dosis disponibles: 0", SwingConstants.CENTER);
        dosisDisponibles.setFont(fuente);

        tiempoRestante = new JLabel("Faltan: 0seg para cerrar", SwingConstants.CENTER);
        tiempoRestante.setFont(fuente);

        panelDatos.add(dosisDisponibles);
        panelDatos.add(tiempoRestante);

        //-------------BOTONES
        panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);
        BoxLayout box = new BoxLayout(panelBotones, BoxLayout.Y_AXIS);

        panelBotones.setLayout(box);
        botonTerminar = new JButton(new ImageIcon("src/imagenes/terminar.png"));
        botonSuspender = new JButton(new ImageIcon("src/imagenes/suspender.png"));
        botonReanudar = new JButton(new ImageIcon("src/imagenes/reanudar.png"));

        botonTerminar.setBackground(Color.WHITE);
        botonSuspender.setBackground(Color.WHITE);
        botonReanudar.setBackground(Color.WHITE);

        panelBotones.add(botonTerminar);
        panelBotones.add(botonSuspender);
        panelBotones.add(botonReanudar);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        scrollPane = new JScrollPane(areaResultados);

        tituloDastosUsuario = new JLabel("Datos de usuarios", SwingConstants.CENTER);
        tituloDastosUsuario.setFont(fuente);

        panelTabla.add(scrollPane, BorderLayout.CENTER);
        panelTabla.add(tituloDastosUsuario, BorderLayout.NORTH);

        panelTabla.setBackground(Color.WHITE);
        panelPrincipal.add(panelTabla);

        botonReanudar.addActionListener(this);
        botonReanudar.setEnabled(false);
        botonSuspender.addActionListener(this);
        botonTerminar.addActionListener(this);

        //---AGREGAR 
        panelProceso.add(tituloProceso, BorderLayout.NORTH);
        panelProceso.add(panelPrincipal, BorderLayout.CENTER);
        panelProceso.add(panelDatos, BorderLayout.SOUTH);
        panelProceso.add(panelBotones, BorderLayout.EAST);

        //---------------------------PANEL RESULTADOS pestaña 3---------------------------
        panelResultados = new JPanel(new GridLayout(1, 2));
        panelResultados.setBackground(Color.WHITE);

        logoResultados = new JLabel(new ImageIcon("src/imagenes/resultado.png"));
        panelDatosResultados = new JPanel(new GridLayout(2, 2));

        tituloAtendidos = new JLabel("Atendidos:", SwingConstants.RIGHT);
        tituloRechazados = new JLabel("Rechazados:", SwingConstants.RIGHT);
        cantAtendidos = new JLabel("0", SwingConstants.CENTER);
        cantRechazados = new JLabel("0", SwingConstants.CENTER);

        tituloAtendidos.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 50));
        tituloRechazados.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 50));
        cantAtendidos.setFont(fuente);
        cantRechazados.setFont(fuente);

        panelDatosResultados.add(tituloAtendidos);
        panelDatosResultados.add(cantAtendidos);
        panelDatosResultados.add(tituloRechazados);
        panelDatosResultados.add(cantRechazados);

        panelDatosResultados.setBackground(Color.WHITE);
        panelResultados.add(logoResultados);
        panelResultados.add(panelDatosResultados);

        //----------------------------------------------------------------------
        pestañas.addTab("Inicio", new ImageIcon("src/imagenes/iconoInicio.png"), panelIngreso);
        pestañas.addTab("Proceso", new ImageIcon("src/imagenes/proceso.png"), panelProceso);
        pestañas.addTab("Resultados", new ImageIcon("src/imagenes/resultados.png"), panelResultados);
        pestañas.setEnabledAt(1, false);
        pestañas.setEnabledAt(2, false);

        add(pestañas);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAbrir) {

            if (tiempoFuncionamiento.getText().length() > 0 && cantDosis.getText().length() > 0) {
                pestañas.setEnabledAt(1, true);
                pestañas.setEnabledAt(2, true);
                pestañas.setSelectedIndex(1);
                pestañas.setEnabledAt(0, false);
                dosisDisponibles.setText("Dosis disponibles " + (Integer.parseInt(cantDosis.getText()) * 6));
                p = new Proceso(sillas, areaResultados);
                gp = new GeneradorPersonas(p);

                gp.start();
                p.start();
            }
        }

        if (e.getSource() == botonTerminar) {
            p.stop();
            gp.stop();
            botonReanudar.setEnabled(false);
            botonSuspender.setEnabled(false);
            botonTerminar.setEnabled(false);
        }

        if (e.getSource() == botonSuspender) {
            p.suspend();
            gp.suspend();
            botonSuspender.setEnabled(false);
            botonReanudar.setEnabled(true);
            botonTerminar.setEnabled(true);
        }

        if (e.getSource() == botonReanudar) {
            p.resume();
            gp.resume();
            botonSuspender.setEnabled(true);
            botonReanudar.setEnabled(false);
            botonTerminar.setEnabled(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char aux = e.getKeyChar();
        if (!Character.isDigit(aux) || ((JTextField) e.getSource()).getText().length() >= 10) {
            if (excederangoint(((JTextField) e.getSource()).getText())) {
                ((JTextField) e.getSource()).setText("");
            }
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean excederangoint(String numero) {
        long Aux;
        if (numero.length() > 0) {
            Aux = Long.parseLong(numero);
        } else {
            Aux = 0;
        }
        if (Aux > Integer.MAX_VALUE) {
            JOptionPane.showMessageDialog(null, "Parece que el numero ingresado se sale del rango int aceptado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
}
