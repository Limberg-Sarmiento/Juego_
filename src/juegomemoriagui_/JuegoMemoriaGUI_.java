/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package juegomemoriagui_;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JuegoMemoriaGUI_ extends JFrame {
    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private JLabel statusLabel;
    private Carta primeraCarta;
    private Carta segundaCarta;
    private boolean esperandoVolteo = false;
    private Clip backgroundMusic;
    private int contadorPartidas = 1;

    public JuegoMemoriaGUI_(Clip backgroundMusic) {
        super("Memory Champions League");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.backgroundMusic = backgroundMusic;
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximizamos la ventana
        iniciarNuevaPartida();
    }

    private void iniciarNuevaPartida() {
        getContentPane().removeAll();  // Limpiar todo el contenido actual de la ventana
        setLayout(new BorderLayout());  // Resetear el layout de la ventana
    
        // Reiniciar el tablero y los jugadores
        int filas = 4;
        int columnas = 7;
        tablero = new Tablero(filas, columnas);
    
        if (contadorPartidas == 1) {
            jugador1 = new Jugador("Jugador 1");
            jugador2 = new Jugador("Jugador 2");
        } else {
            jugador1.reiniciarPares();
            jugador2.reiniciarPares();
        }
    
        jugadorActual = jugador1;
    
        primeraCarta = null;
        segundaCarta = null;
        esperandoVolteo = false;
    
        // Agregar el tablero y la etiqueta de estado
        add(tablero, BorderLayout.CENTER);
        statusLabel = new JLabel("Partida " + contadorPartidas + " - Turno de " + jugadorActual.getNombre(), SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(Color.BLACK);
        add(statusLabel, BorderLayout.SOUTH);
    
        // Inicializar los listeners de las cartas
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                final int fila = i;
                final int columna = j;
                tablero.getCarta(i, j).addActionListener(e -> manejarClickCarta(fila, columna));
            }
        }
    
        // Asegúrate de que la interfaz se actualice correctamente
        revalidate();
        repaint();
        setVisible(true);
    }
    

    private void manejarClickCarta(int fila, int columna) {
        if (esperandoVolteo) {
            return;
        }

        Carta cartaActual = tablero.getCarta(fila, columna);

        if (!cartaActual.estaVolteada()) {
            cartaActual.voltear();

            if (primeraCarta == null) {
                primeraCarta = cartaActual;
            } else {
                segundaCarta = cartaActual;
                esperandoVolteo = true;

                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        verificarPareja();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private void verificarPareja() {
        if (primeraCarta != null && segundaCarta != null) {
            if (primeraCarta.getNombreImagen().equals(segundaCarta.getNombreImagen())) {
                jugadorActual.incrementarPares();

                statusLabel.setText("Partida " + contadorPartidas + " - " + jugadorActual.getNombre() + " encontró un par. " +
                        "Pares encontrados: " + jugadorActual.getParesEncontrados());

                primeraCarta.setEnabled(true);
                segundaCarta.setEnabled(true);

                if (tablero.todasCartasVolteadas()) {
                    anunciarGanador();
                }
            } else {
                primeraCarta.voltear();
                segundaCarta.voltear();
                cambiarTurno();
            }

            primeraCarta = null;
            segundaCarta = null;
            esperandoVolteo = false;
        }
    }

    private void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
        statusLabel.setText("Partida " + contadorPartidas + " - Turno de " + jugadorActual.getNombre());
    }

    private void anunciarGanador() {
        String ganador;
        if (jugador1.getParesEncontrados() > jugador2.getParesEncontrados()) {
            ganador = jugador1.getNombre();
        } else if (jugador2.getParesEncontrados() > jugador1.getParesEncontrados()) {
            ganador = jugador2.getNombre();
        } else {
            ganador = "Empate";
        }
    
        String mensaje = "Fin de la partida " + contadorPartidas + "\n" +
                jugador1.getNombre() + ": " + jugador1.getParesEncontrados() + " pares\n" +
                jugador2.getNombre() + ": " + jugador2.getParesEncontrados() + " pares\n" +
                "Ganador: " + ganador;
    
        guardarPuntuaciones(ganador);
    
        int opcion = JOptionPane.showConfirmDialog(this, mensaje + "\n\n¿Desea jugar otra partida?", 
                     "Fin del Juego", JOptionPane.YES_NO_OPTION);
    
        if (opcion == JOptionPane.YES_OPTION) {
            contadorPartidas++;
            // Aquí reiniciamos la partida
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    iniciarNuevaPartida();  // Asegúrate de llamar correctamente al inicio de la nueva partida
                }
            });
        } else {
            if (backgroundMusic != null) {
                backgroundMusic.stop();
                backgroundMusic.close();
            }
            dispose();  // Cerrar la ventana actual
            new MenuPrincipal_();  // Mostrar el menú principal nuevamente
        }
    }
    

    private void guardarPuntuaciones(String ganador) {
        try {
            File archivo = new File("puntuaciones.txt");
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());

            FileWriter escritor = new FileWriter(archivo, true);

            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha = formatoFecha.format(new Date());

            String puntuaciones = "Partida " + contadorPartidas + " - " +
                    "Fecha: " + fecha + " - " +
                    jugador1.getNombre() + ": " + jugador1.getParesEncontrados() + " pares, " +
                    jugador2.getNombre() + ": " + jugador2.getParesEncontrados() + " pares - " +
                    "Ganador: " + ganador + "\n";

            escritor.write(puntuaciones);
            escritor.close();

            System.out.println("Puntuaciones guardadas exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar las puntuaciones", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}