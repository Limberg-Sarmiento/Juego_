/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegomemoriagui_;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

class Carta extends JButton {
    private ImageIcon imagen;
    private ImageIcon reverso;
    private boolean volteada;
    private String nombreImagen;

    public Carta(String nombreImagen) {
        this.nombreImagen = nombreImagen;
        try {
            String directorioActual = System.getProperty("user.dir");
            String rutaImagenes = directorioActual + File.separator + "src" + 
                                File.separator + "resources" + File.separator + 
                                "imagenes" + File.separator;
            
            File fileImagen = new File(rutaImagenes + nombreImagen);
            File fileReverso = new File(rutaImagenes + "reverso.png");
            
            if (!fileImagen.exists()) {
                throw new IllegalArgumentException("No se encuentra la imagen: " + nombreImagen);
            }
            
            if (!fileReverso.exists()) {
                throw new IllegalArgumentException("No se encuentra la imagen del reverso");
            }
            
            // Cargar imágenes usando BufferedImage para mejor calidad
            BufferedImage bufferedImagen = ImageIO.read(fileImagen);
            BufferedImage bufferedReverso = ImageIO.read(fileReverso);
            
            // Redimensionar manteniendo la calidad
            this.imagen = new ImageIcon(redimensionarImagen(bufferedImagen, 220, 220));
            this.reverso = new ImageIcon(redimensionarImagen(bufferedReverso, 220, 220));
            this.volteada = false;
            
            setPreferredSize(new Dimension(100, 100));
            setBorder(BorderFactory.createEmptyBorder());  // Elimina el borde de cada carta
            actualizar();
            
        } catch (Exception e) {
            e.printStackTrace();
            String mensaje = "Error al cargar la imagen: " + e.getMessage();
            System.err.println(mensaje);
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private BufferedImage redimensionarImagen(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Crear nueva imagen con tipo de color RGB para mantener la calidad
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        
        // Configurar la mejor calidad de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar la imagen redimensionada
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        
        return resizedImage;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public boolean estaVolteada() {
        return volteada;
    }

    public void voltear() {
        volteada = !volteada;
        actualizar();
    }

    private void actualizar() {
        if (volteada) {
            setIcon(imagen);
            setEnabled(true);
        } else {
            setIcon(reverso);
            setEnabled(true);
        }
    }
}

