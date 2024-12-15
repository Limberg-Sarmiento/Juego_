/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegomemoriagui_;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Tablero extends JPanel {
    private Carta[][] cartas;
    private int filas;
    private int columnas;

  public Tablero(int filas, int columnas) {
    this.filas = filas;
    this.columnas = columnas;
    setLayout(new GridLayout(filas, columnas, 0, 0)); // Espacio de 7px entre cartas
    cartas = new Carta[filas][columnas];
    inicializarTablero();
}

private void inicializarTablero() {
    List<String> imagenes = Arrays.asList(
        "carta1.jpg", "carta2.jpg", "carta3.jpg", "carta4.jpg",
        "carta5.jpg", "carta6.jpg", "carta7.jpg", "carta8.jpg",
        "carta9.jpg", "carta10.jpg", "carta11.jpg", "carta12.jpg", "carta13.jpg","carta14.jpg"
    );

    List<String> todasLasCartas = new ArrayList<>();
    for (String imagen : imagenes) {
        todasLasCartas.add(imagen);
        todasLasCartas.add(imagen);
    }
    Collections.shuffle(todasLasCartas);

    // Calcular el tamaño de las cartas según el tamaño de la pantalla
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int cardWidth = screenSize.width / columnas - 20; // Margen horizontal
    int cardHeight = screenSize.height / filas - 20; // Margen vertical

    int index = 0;
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            cartas[i][j] = new Carta(todasLasCartas.get(index++));
            cartas[i][j].setPreferredSize(new Dimension(cardWidth, cardHeight)); // Tamaño dinámico
            add(cartas[i][j]);
        }
    }
}

    public Carta getCarta(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return cartas[fila][columna];
        }
        return null;
    }

    public boolean todasCartasVolteadas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!cartas[i][j].estaVolteada()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(columnas * 200, filas * 200); // 105 para incluir el espacio entre cartas
    }
}