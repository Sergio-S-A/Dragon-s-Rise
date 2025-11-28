package platform.desktop.assets;

import core.config.GameConfig;
import features.tile.domain.model.TileType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AssetPreloader {

    // --- Asset Paths ---
    private static final String BACKGROUND_MAIN = "Camaras_Subterraneas.jpg";
    private static final String WOLF_ATTACK = "wolf/wolf_attack.png";
    private static final String WOLF_RUN = "wolf/wolf_run.png";
    private static final String WOLF_IDLE = "wolf/wolf_idle.png";
    // --- Dependencies ---
    private final AssetCache assetCache;

    // --- Asset Configurations ---
    private final Map<String, Dimension> assetsImages;
    private final Map<String, Integer> assetsFonts;

    public AssetPreloader() {
        this.assetCache = AssetCache.getInstance();
        this.assetsImages = new HashMap<>(256);
        this.assetsFonts = new HashMap<>(16);

        configureImages();
        configureFonts();
    }

    public void loadAssets() {
        assetCache.preloadImage(assetsImages);
        assetCache.preloadFont(assetsFonts);
    }

    private void configureImages() {
        // 1. Backgrounds
        assetsImages.put(BACKGROUND_MAIN, new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));

        // 2. Characters (Wolf)
        int playerTile = GameConfig.TILE_SIZE_PLAYER;
        assetsImages.put(WOLF_IDLE, new Dimension(playerTile * 4, playerTile * 4));
        assetsImages.put(WOLF_RUN, new Dimension(playerTile * 8, playerTile * 4));
        assetsImages.put(WOLF_ATTACK, new Dimension(playerTile * 15, playerTile * 4));

        // 3. Tiles (Procedural Generation)
        int mapTileWidth;
        int mapTileHeight;
        for (int i = GameConfig.TILE_ORIGIN; i < GameConfig.DEFAULT_TILE_NUMBER; i++) {

            String path = String.format(GameConfig.TILE_PATH_CONFIG_STRING, i);

            mapTileWidth = TileType.values()[i].width;
            mapTileHeight = TileType.values()[i].height;


            assetsImages.put(path, new Dimension(mapTileWidth, mapTileHeight));
        }

        // 4. UI Elements
        Dimension btnDim = new Dimension(GameConfig.DEFAULT_BUTTON_WIDTH, GameConfig.DEFAULT_BUTTON_HEIGHT);
        assetsImages.put(GameConfig.DEFAULT_PRESSED_IMAGE_PATH, btnDim);
        assetsImages.put(GameConfig.DEFAULT_UNPRESSED_IMAGE_PATH, btnDim);
    }

    private void configureFonts() {
        assetsFonts.put(GameConfig.DEFAULT_FONT_PATH, GameConfig.DEFAULT_FONT_SIZE);
    }
}