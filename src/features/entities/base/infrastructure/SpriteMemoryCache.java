package features.entities.base.infrastructure;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteMemoryCache {

    private static final Map<String, BufferedImage> registry = new HashMap<>(128);

    public static void registerImage(String key, BufferedImage image) {
        registry.put(key, image);
    }

    public static BufferedImage getImage(String key) {
        return registry.get(key);
    }

    public static int size() {
        return registry.size();
    }

    public static void clear() {
        registry.clear();
    }
}
