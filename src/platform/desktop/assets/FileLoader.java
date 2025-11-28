package platform.desktop.assets;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class FileLoader {

    private FileLoader() {
    }

    public static BufferedImage loadImage(String path) {
        try (InputStream is = FileLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Image not found: " + path);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Error loading image: " + path, e);
        }
    }

    public static Font loadFont(String path, float size) {
        try (InputStream is = FileLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Font not found: " + path);
            }
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Error loading font: " + path, e);
        }
    }

    public static Clip loadAudio(String path) {
        try (InputStream is = FileLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Audio not found: " + path);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException("Error loading audio: " + path, e);
        }
    }
}
