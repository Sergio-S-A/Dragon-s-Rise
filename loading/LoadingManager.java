package loading;

import core.main.GameConstants;
import resources.ResourceManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LoadingManager {

    private final Map<String, Dimension> assetsImages;
    private final Map<String, Integer> assetsFonts;
    ResourceManager resourceManager;

    public LoadingManager() {
        resourceManager = ResourceManager.getInstance();
        assetsImages = new HashMap<>();
        assetsFonts = new HashMap<>();
        initAssetsImages();
        initAssetsFonts();
    }

    private void initAssetsImages() {
        assetsImages.put("camaraSubterranea.jpg", new Dimension(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT));
        assetsImages.put("wolf/wolf-bite.png", new Dimension(GameConstants.TILE_SIZE_BIG * 15, GameConstants.TILE_SIZE_BIG * 4));
        assetsImages.put("wolf/wolf-run.png", new Dimension(GameConstants.TILE_SIZE_BIG * 8, GameConstants.TILE_SIZE_BIG * 4));
        assetsImages.put("wolf/wolf-idle.png", new Dimension(GameConstants.TILE_SIZE_BIG * 4, GameConstants.TILE_SIZE_BIG * 4));
        for (int i = 0; i <= GameConstants.DEFAULT_TILE_NUMBER; i++) {
            assetsImages.put(String.format(GameConstants.TILE_PATH_CONFIG_STRING, i), new Dimension(GameConstants.TILE_SIZE, GameConstants.TILE_SIZE));
        }
        assetsImages.put(GameConstants.DEFAULT_PRESSED_IMAGE_PATH, new Dimension(GameConstants.DEFAULT_BUTTON_WIDTH, GameConstants.DEFAULT_BUTTON_HEIGHT));
        assetsImages.put(GameConstants.DEFAULT_UNPRESSED_IMAGE_PATH, new Dimension(GameConstants.DEFAULT_BUTTON_WIDTH, GameConstants.DEFAULT_BUTTON_HEIGHT));
    }

    private void initAssetsFonts() {
        assetsFonts.put(GameConstants.DEFAULT_FONT_PATH, GameConstants.DEFAULT_FONT_SIZE);
    }

    public void loadAssets() {
        loadImages();
        loadFonts();
    }

    private void loadImages() {
        resourceManager.preloadImage(assetsImages);
    }

    private void loadFonts() {
        resourceManager.preloadFont(assetsFonts);
    }
}


