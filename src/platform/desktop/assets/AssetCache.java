package platform.desktop.assets;

import core.config.GameConfig;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AssetCache implements AssetLoader {

    private static AssetCache instance;

    private final Map<String, BufferedImage> imageCache = new HashMap<>(256);
    private final Map<String, Font> fontCache = new HashMap<>(16);
    private final Map<String, Clip> audioCache = new HashMap<>(32);

    private AssetCache() {
    }

    public static AssetCache getInstance() {
        if (instance == null) {
            instance = new AssetCache();
        }
        return instance;
    }

    public void preloadImage(Map<String, Dimension> assets) {
        assets.forEach(
                (filename, dimension) -> {
                    String key = buildImageKey(filename, dimension.width, dimension.height);
                    BufferedImage originalImage = FileLoader.loadImage(GameConfig.IMAGE_PATH + filename);
                    BufferedImage resizeImage = resizeImage(originalImage, dimension.width, dimension.height);
                    originalImage.flush();
                    imageCache.put(key, resizeImage);
                }
        );
    }

    private String buildImageKey(String fileName, int width, int height) {
        return fileName + "_" + width + "x" + height;
    }

    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        boolean hasAlpha = original.getColorModel().hasAlpha();
        int imageType = hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;

        BufferedImage resized = new BufferedImage(width, height, imageType);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );
        g.drawImage(original, 0, 0, width, height, null);
        g.dispose();
        return resized;
    }

    public void preloadFont(Map<String, Integer> assets) {
        assets.forEach(
                (filname, size) -> {
                    String key = buildFontKey(filname, size);
                    Font font = FileLoader.loadFont(GameConfig.FONT_PATH + filname, size);
                    fontCache.put(key, font);
                }
        );
    }

    private String buildFontKey(String fileName, float size) {
        return "s" + fileName + "_" + size;
    }

    public void preloadAudio(Map<String, String> assets) {
        assets.forEach(
                (audioPath, fileName) -> {
                    String key = buildAudioKey(audioPath, fileName);
                    Clip clip = FileLoader.loadAudio(GameConfig.AUDIO_PATH + fileName);
                    audioCache.put(key, clip);
                }
        );
    }

    private String buildAudioKey(String audioPath, String fileName) {
        return audioPath + fileName;
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

    @Override
    public BufferedImage getImage(String path, int width, int height) {
        return cacheResource(imageCache, buildImageKey(path, width, height),
                () -> FileLoader.loadImage(GameConfig.IMAGE_PATH + path));
    }

    @Override
    public Font getFont(String fontName, float size) {
        return cacheResource(fontCache, fontName,
                () -> FileLoader.loadFont(GameConfig.FONT_PATH + fontName, size));
    }

    @Override
    public Clip getAudio(String audioName) {
        return cacheResource(audioCache, audioName,
                () -> FileLoader.loadAudio(GameConfig.AUDIO_PATH + audioName));
    }
}
