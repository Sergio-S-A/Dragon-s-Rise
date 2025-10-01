package resources;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {

    private ResourceLoader() {
    }

    public static BufferedImage loadImage(String path) {
        try (InputStream is = ResourceLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Ruta invalida: " + path);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando imagen: " + path, e);
        }
    }

    public static Font loadFont(String path, float size) {
        try (InputStream is = ResourceLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Ruta invalida: " + path);
            }
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Error cargando fuente: " + path, e);
        }
    }

    public static Clip loadAudio(String path) {
        try (InputStream is = ResourceLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("No se encontró el audio en: " + path);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException("Error cargando audio: " + path, e);
        }
    }
}
