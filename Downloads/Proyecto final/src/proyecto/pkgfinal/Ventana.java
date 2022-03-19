/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.pkgfinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author USER
 */
public class Ventana extends JFrame implements ActionListener, KeyListener {

    JTabbedPane pestañas;
    JPanel panelinicio, panelproceso, panelBotones, panelvelocidad, panelsur, panelProduccion;
    JPanel panelMotos, panelCarros, panelregistro, panelreporte;
    JLabel partesMoto[], partesCarro[], cintaMotos, cintaCarros, fabrica1, fabrica2, carro, moto;
    Timer time;
    public static int contTime = 0;
    JLabel labelTiempo, tituloinicio, imageninicio, tituloregistro, tituloreporte;
    JButton botoniniciar, btninciar, btnpausa, btnreanudar, btnterminar;
    ButtonGroup velocidades;
    JRadioButton v1, v2, v3;
    JTextField txttiempo;
    ImageIcon falta = new ImageIcon("src/img/falta.png"), ok = new ImageIcon("src/img/ok.png");
    JTextArea areaRegistro, areaReporte;
    JLabel partemoto, partecarro;
    int x = 0, y = 320;
    public static int velocidad = 1000;
    boolean primeraEjecucion = true;
    HiloGeneracionPartesMoto hgpm;
    HiloGeneracionPartesCarro hgpc;
    HiloProceso hiloProceso1, hiloProceso2;
    ActionListener accion = new ActionListener() {
        /*es una clase anonima para el tiempo, según la documentacion..
        .estoy aprendiendo más sobre eso*/
        @Override
        public void actionPerformed(ActionEvent e) {

            labelTiempo.setText("Tiempo restante: " + contTime);
            contTime--;
            if (contTime < 0) {
                hgpm.stop();
                hgpc.stop();
                hiloProceso1.stop();
                hiloProceso2.stop();
                time.stop();
                JOptionPane.showMessageDialog(null, "Se ha finalizado por tiempo");
            }

        }
    };

    public Ventana() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1350, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        UIManager.put("TabbedPane.selected", new Color(207, 219, 245));
        setResizable(false);

        pestañas = new JTabbedPane();
        panelinicio = new JPanel();
        panelinicio.setBackground(new Color(16, 28, 42));
        panelinicio.setLayout(null);
        //-------------Inicio-------------------------------------------------
        tituloinicio = new JLabel("FABRICA VEHICULAR MAC", SwingConstants.CENTER);
        tituloinicio.setForeground(Color.WHITE);
        tituloinicio.setFont(new Font(Font.SERIF, 1, 36));
        tituloinicio.setBounds(410, 40, 500, 80);

        imageninicio = new JLabel(new ImageIcon("src/img/imagenInicio.png"));
        imageninicio.setBounds(405, 110, 500, 320);

        txttiempo = new JTextField("");
        txttiempo.addKeyListener(this);
        TitledBorder titledBorder = new TitledBorder(new LineBorder(new Color(207, 219, 245)),
                "Ingrese el tiempo de producccion");
        titledBorder.setTitleFont(new Font(Font.SERIF, 1, 15));
        titledBorder.setTitleColor(new Color(207, 219, 245));
        txttiempo.setBorder(titledBorder);
        txttiempo.setBackground(new Color(16, 28, 42));
        txttiempo.setBounds(410, 460, 500, 60);
        txttiempo.setFont(new Font(Font.SERIF, 1, 25));
        txttiempo.setForeground(Color.WHITE);

        time = new Timer(1000, accion);
        contTime = 0;

        botoniniciar = new JButton("Ingresar");
        botoniniciar.setBounds(500, 550, 300, 30);
        botoniniciar.setFont(new Font(Font.SERIF, 1, 25));
        botoniniciar.addActionListener(this);
        botoniniciar.setForeground(Color.BLACK);
        botoniniciar.setBackground(new Color(207, 219, 245));

        panelinicio.add(tituloinicio);
        panelinicio.add(imageninicio);
        panelinicio.add(txttiempo);
        panelinicio.add(botoniniciar);

        //-------------------PESTAÑA PROCESO------------------------------------
        panelproceso = new JPanel(new BorderLayout());
        panelproceso.setBackground(new Color(16, 28, 42));

        labelTiempo = new JLabel("Tiempo restante: " + contTime, SwingConstants.CENTER);
        labelTiempo.setForeground(Color.WHITE);
        labelTiempo.setFont(new Font(Font.SERIF, 1, 25));

        panelvelocidad = new JPanel(new GridLayout(1, 3));
        panelvelocidad.setBorder(new TitledBorder(new LineBorder(new Color(16, 28, 42)),
                "Seleccione la velocidad"));

        velocidades = new ButtonGroup();
        v1 = new JRadioButton("Lenta", true);
        v1.addActionListener(this);
        v1.setIcon(new ImageIcon("src/img/velocimetro1.png"));
        v2 = new JRadioButton("Media");
        v2.addActionListener(this);
        v2.setIcon(new ImageIcon("src/img/velocimetro2.png"));
        v3 = new JRadioButton("Rapida");
        v3.addActionListener(this);
        v3.setIcon(new ImageIcon("src/img/velocimetro3.png"));
        velocidades.add(v1);
        velocidades.add(v2);
        velocidades.add(v3);
        panelvelocidad.add(v1);
        panelvelocidad.add(v2);
        panelvelocidad.add(v3);

        panelBotones = new JPanel();
        panelBotones.setBorder(new TitledBorder(new LineBorder(new Color(16, 28, 42)),
                "Opciones"));

        btninciar = new JButton(" Iniciar  ");
        btninciar.setFont(new Font(Font.SERIF, 1, 25));
        btninciar.addActionListener(this);
        btninciar.setForeground(Color.BLACK);
        btninciar.setBackground(new Color(207, 219, 245));

        btnpausa = new JButton(" Pausa    ");
        btnpausa.setFont(new Font(Font.SERIF, 1, 25));
        btnpausa.addActionListener(this);
        btnpausa.setForeground(Color.BLACK);
        btnpausa.setBackground(new Color(207, 219, 245));

        btnreanudar = new JButton(" Reanudar ");
        btnreanudar.setFont(new Font(Font.SERIF, 1, 25));
        btnreanudar.addActionListener(this);
        btnreanudar.setForeground(Color.BLACK);
        btnreanudar.setBackground(new Color(207, 219, 245));

        btnterminar = new JButton(" Terminar ");
        btnterminar.setFont(new Font(Font.SERIF, 1, 25));
        btnterminar.addActionListener(this);
        btnterminar.setForeground(Color.BLACK);
        btnterminar.setBackground(new Color(207, 219, 245));

        panelBotones.add(btninciar);
        panelBotones.add(btnpausa);
        panelBotones.add(btnreanudar);
        panelBotones.add(btnterminar);

        panelsur = new JPanel(new GridLayout(1, 2));

        panelBotones.setBackground(Color.WHITE);
        panelvelocidad.setBackground(Color.WHITE);

        panelsur.add(panelvelocidad);
        panelsur.add(panelBotones);

        panelProduccion = new JPanel(new GridLayout(1, 2));
        //-----------------------MOTOS-----------------------------------------
        panelMotos = new JPanel();
        panelMotos.setBackground(new Color(16, 28, 42));
        panelMotos.setBorder(new LineBorder(Color.white));
        panelMotos.setLayout(null);
        cintaMotos = new JLabel(new ImageIcon("src/img/cinta.jpg"));
        cintaMotos.setBounds(0, 415, 900, 80);
        partesMoto = new JLabel[5];

        for (int i = 0; i < partesMoto.length; i++) {
            partesMoto[i] = new JLabel("Parte moto " + (i + 1));
            partesMoto[i].setIcon(falta);
            partesMoto[i].setForeground(Color.WHITE);
            partesMoto[i].setBounds(0, i * 40, 200, 30);
            panelMotos.add(partesMoto[i]);
        }

        fabrica1 = new JLabel(new ImageIcon("src/img/fabrica(1).png"));
        fabrica1.setBounds(280, 80, 400, 125);

        moto = new JLabel(new ImageIcon("src/img/moto.png"));
        moto.setBounds(100, 100, 400, 125);

        partemoto = new JLabel("");
        partemoto.setBounds(x, y, 100, 100);

        panelMotos.add(partemoto);
        panelMotos.add(fabrica1);
        panelMotos.add(moto);
        panelMotos.add(cintaMotos);

        panelProduccion.add(panelMotos);
        //-----------------------CARROS-----------------------------------------
        panelCarros = new JPanel();
        panelCarros.setLayout(null);
        panelCarros.setBackground(new Color(16, 28, 42));
        panelCarros.setBorder(new LineBorder(Color.white));
        cintaCarros = new JLabel(new ImageIcon("src/img/cinta.jpg"));
        cintaCarros.setBounds(0, 415, 900, 80);

        partesCarro = new JLabel[5];
        for (int i = 0; i < partesCarro.length; i++) {
            partesCarro[i] = new JLabel("Parte carro " + (i + 1));
            partesCarro[i].setIcon(falta);
            partesCarro[i].setForeground(Color.WHITE);
            partesCarro[i].setBounds(0, i * 40, 200, 30);
            panelCarros.add(partesCarro[i]);
        }

        carro = new JLabel(new ImageIcon("src/img/auto.png"));
        carro.setBounds(100, 90, 400, 125);

        fabrica2 = new JLabel(new ImageIcon("src/img/fabrica(1).png"));
        fabrica2.setBounds(280, 80, 400, 125);

        partecarro = new JLabel("parte");
        partecarro.setBounds(x, 380, 200, 100);

        panelCarros.add(partecarro);
        panelCarros.add(fabrica2);
        panelCarros.add(carro);
        panelCarros.add(cintaCarros);

        panelProduccion.add(panelCarros);
        //---------------------------------------------------------------------
        panelproceso.add(panelProduccion, BorderLayout.CENTER);
        panelproceso.add(labelTiempo, BorderLayout.NORTH);
        panelproceso.add(panelsur, BorderLayout.SOUTH);
        //-------------------------Registro------------------------------------
        /*
        Incluya un componente para mostrar en algún lugar las piezas que van saliendo, el momento
        en que se arma el carro y las piezas que se desechan (que permita realizar seguimiento al
        ejercicio).            
         */
        panelregistro = new JPanel(new BorderLayout());
        panelregistro.setBackground(new Color(16, 28, 42));
        tituloregistro = new JLabel("Registro", SwingConstants.CENTER);
        tituloregistro.setForeground(Color.WHITE);
        tituloregistro.setFont(new Font(Font.SERIF, 1, 25));

        areaRegistro = new JTextArea();
        areaRegistro.setEditable(false);
        areaRegistro.setFont(new Font(Font.SERIF, 1, 25));
        JScrollPane scol1 = new JScrollPane(areaRegistro);
        panelregistro.add(tituloregistro, BorderLayout.NORTH);
        panelregistro.add(scol1, BorderLayout.CENTER);
        //------------------------Reporte--------------------------------------
        /*
        Reporte de producción: una ficha con información gerencial que muestre la cantidad de
        productos terminados (cada uno por aparte) y el tiempo empleado en cada caso
         */
        panelreporte = new JPanel(new BorderLayout());
        panelreporte.setBackground(new Color(16, 28, 42));
        tituloreporte = new JLabel("Reporte", SwingConstants.CENTER);
        tituloreporte.setForeground(Color.WHITE);
        tituloreporte.setFont(new Font(Font.SERIF, 1, 25));
        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font(Font.SERIF, 1, 25));
        JScrollPane scroll2 = new JScrollPane(areaReporte);
        panelreporte.add(tituloreporte, BorderLayout.NORTH);
        panelreporte.add(scroll2, BorderLayout.CENTER);
        //----------------------------------------------------------------------
        pestañas.addTab("Inicio", new ImageIcon("src/img/home.png"), panelinicio);
        pestañas.addTab("Proceso", new ImageIcon("src/img/proceso.png"), panelproceso);
        pestañas.addTab("Registro", new ImageIcon("src/img/registro.png"), panelregistro);
        pestañas.addTab("Reporte", new ImageIcon("src/img/reporte.png"), panelreporte);
        pestañas.setEnabledAt(1, false);
        pestañas.setEnabledAt(2, false);
        pestañas.setEnabledAt(3, false);

        add(pestañas);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botoniniciar) {
            if (txttiempo.getText().length() > 0) {
                if (Integer.parseInt(txttiempo.getText()) > 0) {
                    contTime = Integer.parseInt(txttiempo.getText());
                    pestañas.setSelectedIndex(1);
                    pestañas.setEnabledAt(0, false);
                    pestañas.setEnabledAt(1, true);
                    pestañas.setEnabledAt(2, true);
                    pestañas.setEnabledAt(3, true);
                    labelTiempo.setText("Tiempo restante: " + contTime);
                    btninciar.setEnabled(true);
                    btnpausa.setEnabled(false);
                    btnreanudar.setEnabled(false);
                    btnterminar.setEnabled(false);
                    primeraEjecucion = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Se debe ingresar un numero mayor a 0");
                }
            }
        }
        if (v1.isSelected()) {
            v1.setBackground(Color.GREEN);
            v2.setBackground(Color.white);
            v3.setBackground(Color.white);
            velocidad = 2500;
        }
        if (v2.isSelected()) {
            v2.setBackground(Color.GREEN);
            v1.setBackground(Color.white);
            v3.setBackground(Color.white);
            velocidad = 1800;
        }
        if (v3.isSelected()) {
            v3.setBackground(Color.GREEN);
            v1.setBackground(Color.white);
            v2.setBackground(Color.white);
            velocidad = 1000;
        }

        if (btninciar == e.getSource()) {
            if (primeraEjecucion) {
                hgpm = new HiloGeneracionPartesMoto(areaRegistro, partemoto);
                hgpc = new HiloGeneracionPartesCarro(areaRegistro, partecarro);
                hiloProceso1 = new HiloProceso(moto, partemoto, areaRegistro, areaReporte, partesMoto);
                hiloProceso2 = new HiloProceso(carro, partecarro, areaRegistro, areaReporte, partesCarro);
                time.start();
                hgpm.start();
                hgpc.start();
                hiloProceso1.start();
                hiloProceso2.start();
                btninciar.setEnabled(false);
                btnpausa.setEnabled(true);
                btnreanudar.setEnabled(false);
                btnterminar.setEnabled(true);
                primeraEjecucion = false;
            } else {
                reiniciarComponentes();
            }
        }

        if (btnpausa == e.getSource()) {
            hgpm.suspend();
            hgpc.suspend();
            hiloProceso1.suspend();
            hiloProceso2.suspend();
            time.stop();
            btninciar.setEnabled(false);
            btnpausa.setEnabled(false);
            btnreanudar.setEnabled(true);
            btnterminar.setEnabled(true);

        }

        if (btnreanudar == e.getSource()) {
            hgpm.resume();
            hgpc.resume();
            hiloProceso1.resume();
            hiloProceso2.resume();
            time.start();
            btninciar.setEnabled(false);
            btnpausa.setEnabled(true);
            btnreanudar.setEnabled(false);
            btnterminar.setEnabled(true);

        }

        if (btnterminar == e.getSource()) {
            hgpm.stop();
            hgpc.stop();
            hiloProceso1.stop();
            hiloProceso2.stop();
            time.stop();
            btninciar.setEnabled(true);
            btnpausa.setEnabled(false);
            btnreanudar.setEnabled(false);
            btnterminar.setEnabled(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char aux = e.getKeyChar();
        if (!Character.isDigit(aux) || ((JTextField) e.getSource()).getText().length() >= 6) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void reiniciarComponentes() {

        pestañas.setSelectedIndex(0);
        pestañas.setEnabledAt(0, true);
        pestañas.setEnabledAt(1, false);

        pestañas.setEnabledAt(2, false);

        pestañas.setEnabledAt(3, false);

        for (int i = 0; i < partesCarro.length; i++) {
            partesCarro[i].setText("Parte auto " + (i + 1));
            partesCarro[i].setIcon(falta);

            partesMoto[i].setText("Parte moto " + (i + 1));
            partesMoto[i].setIcon(falta);

        }

        partecarro.setBounds(x, 380, 200, 100);
        partecarro.setIcon(null);
        partecarro.setText("");

        partemoto.setBounds(x, y, 100, 100);
        partemoto.setIcon(null);
        partemoto.setText("");

        areaRegistro.setText("");
        areaReporte.setText("");

        txttiempo.setText("");

        v1.setSelected(true);
        
        HiloProceso.reporteCarro= new ArrayList<String>();
        HiloProceso.reporteMoto = new ArrayList<String>();
        HiloProceso.cantAutos=0;
        HiloProceso.cantMotos=0;
    }

}
