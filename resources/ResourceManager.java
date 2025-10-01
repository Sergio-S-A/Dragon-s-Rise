package resources;

import core.main.GameConstants;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ResourceManager {

    private static final int SCALE_HINT = Image.SCALE_REPLICATE;

    private final Map<String, BufferedImage> imageCache = new HashMap<>();
    private final Map<String, Font> fontCache = new HashMap<>();
    private final Map<String, Clip> audioCache = new HashMap<>();


    public BufferedImage loadImage(String fileName, int width, int height) {
        String key = buildImageKey(fileName, width, height);
        return cacheResource(imageCache, key,
                () -> resizeImage(ResourceLoader.loadImage(GameConstants.IMAGE_PATH + fileName), width, height));
    }

    private String buildImageKey(String fileName, int width, int height) {
        return String.format("%s_%dx%d", fileName, width, height);
    }

    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(original, 0, 0, width, height, null);
        g.dispose();
        return resized;
    }


    public Font loadFont(String fileName, float size) {
        String key = buildFontKey(fileName, size);
        return cacheResource(fontCache, key,
                () -> ResourceLoader.loadFont(GameConstants.FONT_PATH + fileName, size));
    }

    private String buildFontKey(String fileName, float size) {
        return String.format("%s_%f", fileName, size);
    }


    public Clip loadAudio(String fileName) {
        return cacheResource(audioCache, fileName,
                () -> ResourceLoader.loadAudio(GameConstants.AUDIO_PATH + fileName));
    }


    private <T> T cacheResource(Map<String, T> cache, String key, Supplier<T> loader) {
        return cache.computeIfAbsent(key, k -> {
            try {
                return loader.get();
            } catch (Exception e) {
                throw new RuntimeException("Error loading resource: " + key, e);
            }
        });
    }
}
