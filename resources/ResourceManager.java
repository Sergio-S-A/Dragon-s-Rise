package resources;

import core.main.GameConstants;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ResourceManager {

    private static ResourceManager instance;

    private final Map<String, BufferedImage> imageCache = new HashMap<>();
    private final Map<String, Font> fontCache = new HashMap<>();
    private final Map<String, Clip> audioCache = new HashMap<>();

    private ResourceManager() {
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public void preloadImage(Map<String, Dimension> assets) {
        assets.forEach(
                (filname, dimension) -> {
                    String key = buildImageKey(filname, dimension.width, dimension.height);
                    BufferedImage bufferedImage = ResourceLoader.loadImage(GameConstants.IMAGE_PATH + filname);
                    bufferedImage = resizeImage(bufferedImage, dimension.width, dimension.height);
                    imageCache.put(key, bufferedImage);
                }
        );
    }

    private String buildImageKey(String fileName, int width, int height) {
        return fileName + "_" + width + "x" + height;
    }

    public BufferedImage getImage(String fileName, int width, int height) {
        return imageCache.get(buildImageKey(fileName, width, height));
    }

    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(original, 0, 0, width, height, null);
        g.dispose();
        return resized;
    }

    public void preloadFont(Map<String, Integer> assets) {
        assets.forEach(
                (filname, size) -> {
                    String key = buildFontKey(filname, size);
                    Font font = ResourceLoader.loadFont(GameConstants.FONT_PATH + filname, size);
                    fontCache.put(key, font);
                }
        );
    }

    public Font getFont(String fileName, float size) {
        return fontCache.get(buildFontKey(fileName, size));
    }

    private String buildFontKey(String fileName, float size) {
        return "s" + fileName + "_" + size;
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

    public void clearCache() {
        imageCache.clear();
        fontCache.clear();
        audioCache.clear();
    }
}
