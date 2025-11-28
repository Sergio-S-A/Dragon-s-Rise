package core.config;

import java.awt.*;

public class GameConfig {
    // Game settings
    public static final int FPS = 30;
    public static final float TIME_PER_TICK = 1_000_000_000.0f / FPS;
    public static final int WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int WINDOW_WIDTH_MEDIUM = WINDOW_WIDTH / 2;
    public static final int WINDOW_HEIGHT_MEDIUM = WINDOW_HEIGHT / 2;
    public static final int WINDOW_WIDTH_QUARTER = WINDOW_WIDTH / 4;
    public static final int WINDOW_HEIGHT_QUARTER = WINDOW_HEIGHT / 4;

    public static final int TILE_SIZE = 64;
    public static final int TILE_SIZE_BIG = TILE_SIZE * 2;
    public static final int TILE_SIZE_PLAYER = TILE_SIZE_BIG;
    public static final int TILE_SIZE_MEDIUM = TILE_SIZE / 2;
    public static final int TILE_SIZE_QUARTER = TILE_SIZE / 4;
    public static final int TILE_SIZE_BOSS = 180;

    // Numbers
    public static final float ONE_MILLION = 1_000_000.0f;
    public static final int NUM_OF_BUFFERS = 2;

    // Text rendering
    public static final int OUTLINE_SIZE_DIVISOR = 12;
    public static final int MIN_OUTLINE_SIZE = 1;
    public static final int DEFAULT_FONT_SIZE = 32;

    // Button constants
    public static final float BUTTON_TEXT_VERTICAL_CENTER_RATIO = 0.5f;
    public static final float BUTTON_TEXT_HORIZONTAL_CENTER_RATIO = 0.5f;
    public static final float BUTTON_TEXT_BASELINE_ADJUSTMENT = 4.0f;
    public static final int DEFAULT_BUTTON_WIDTH = 200;
    public static final int DEFAULT_BUTTON_HEIGHT = 50;

    // Paths
    public static final String IMAGE_PATH = "/resources/images/";
    public static final String FONT_PATH = "/resources/fonts/";
    public static final String AUDIO_PATH = "/resources/audio/";
    public static final String DEFAULT_FONT_PATH = "Tiny5-Regular.ttf";
    public static final String DEFAULT_PRESSED_IMAGE_PATH = "keney_ui_pack_rpg/buttonLong_blue_pressed.png";
    public static final String DEFAULT_UNPRESSED_IMAGE_PATH = "keney_ui_pack_rpg/buttonLong_blue.png";


    // Tile constants
    public static final String TILE_PATH_CONFIG_STRING = "isometric_tileset/tile_%03d.png";
    public static final int DEFAULT_TILE_NUMBER = 13;
    public static final int DEFAULT_TILE_NUMBER_FOR_TERRAIN = 8;
    public static final int TILE_ORIGIN = 0;
}
