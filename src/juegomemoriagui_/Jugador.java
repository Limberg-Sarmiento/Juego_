/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegomemoriagui_;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Jugador {
    private String nombre;
    private int paresEncontrados;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.paresEncontrados = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getParesEncontrados() {
        return paresEncontrados;
    }

    public void incrementarPares() {
        paresEncontrados++;
    }

    public void reiniciarPares() {
        paresEncontrados = 0;
    }
}