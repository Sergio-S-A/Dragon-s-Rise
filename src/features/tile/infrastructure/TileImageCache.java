package features.tile.infrastructure;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TileImageCache {

    private static final Map<String, BufferedImage> registry = new HashMap<>(128);

    public static void registerImage(String key, BufferedImage image) {
        registry.put(key, image);
    }

    public static BufferedImage getImage(String key) {
        return registry.get(key);
    }

    public static void clear() {
        registry.clear();
    }

    public static int size() {
        return registry.size();
    }

    public static Map<String, BufferedImage> getRegistry() {
        return registry;
    }
}
