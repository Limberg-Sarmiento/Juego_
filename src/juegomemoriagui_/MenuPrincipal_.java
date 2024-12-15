/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegomemoriagui_;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class MenuPrincipal_ extends JFrame {
    private Image backgroundImage;
    private Clip backgroundMusic;

    public MenuPrincipal_() {
        super("Memory Champions League - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        iniciarMusicaDeFondo();

        try {
            backgroundImage = ImageIO.read(new File("src/resources/imagenes/fondo_menu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen de fondo", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("Memory Champions League");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(Color.WHITE);

        JButton playButton = new JButton("Play");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setFont(new Font("Arial", Font.PLAIN, 24));
        playButton.setPreferredSize(new Dimension(200, 60));
        playButton.setMaximumSize(new Dimension(200, 60));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });

        JButton exitButton = new JButton("Salir");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitButton.setPreferredSize(new Dimension(200, 60));
        exitButton.setMaximumSize(new Dimension(200, 60));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (backgroundMusic != null) {
                    backgroundMusic.stop();
                    backgroundMusic.close();
                }
                System.exit(0);
            }
        });

        panel.add(Box.createVerticalGlue());
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(playButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());

        setContentPane(panel);

        setVisible(true);
    }

    private void iniciarMusicaDeFondo() {
        try {
            File audioFile = new File("src/resources/audio/background_music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar la música de fondo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarJuego() {
        JLabel loadingLabel = new JLabel("Cargando...", JLabel.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        setContentPane(loadingLabel);
        revalidate();
        
    
        // Usar un SwingWorker o Timer para cargar el juego en segundo plano
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simular el tiempo de carga
                Thread.sleep(2000);
                return null;
            }
    
            @Override
            protected void done() {
                // Iniciar el juego una vez cargado
                new JuegoMemoriaGUI_(backgroundMusic);
                dispose();
            }
        }.execute();
        /* 
        setVisible(false);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JuegoMemoriaGUI_(backgroundMusic);
            }
        });
        dispose();
        */
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuPrincipal_();
            }
        });
    }
}
