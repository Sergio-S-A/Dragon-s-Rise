package core.main;

public class GameConstants {
    // Game settings
    public static final int FPS = 60;
    public static final double TIME_PER_TICK = 1_000_000_000.0 / FPS;
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH_MEDIUM = WINDOW_WIDTH / 2;
    public static final int WINDOW_HEIGHT_MEDIUM = WINDOW_HEIGHT / 2;
    public static final int WINDOW_WIDTH_QUARTER = WINDOW_WIDTH / 4;
    public static final int WINDOW_HEIGHT_QUARTER = WINDOW_HEIGHT / 4;
    public static final int TILE_SIZE = 64;

    // Physics
    public static final String TILE_PATH_CONFIG_STRING = "isometric_tileset/tile_%03d.png";

    // Numbers
    public static final double ONE_MILLION = 1_000_000.0;
    public static final int NUM_OF_BUFFERS = 3;

    // Text rendering
    public static final int OUTLINE_SIZE_DIVISOR = 12;
    public static final int MIN_OUTLINE_SIZE = 1;
    public static final int DEFAULT_FONT_SIZE = 32;

    // Button constans
    public static final double BUTTON_TEXT_VERTICAL_CENTER_RATIO = 0.5;
    public static final double BUTTON_TEXT_HORIZONTAL_CENTER_RATIO = 0.5;
    public static final double BUTTON_TEXT_BASELINE_ADJUSTMENT = 4.0;
    public static final int DEFAULT_BUTTON_WIDTH = 200;
    public static final int DEFAULT_BUTTON_HEIGHT = 50;

    // Paths
    public static final String IMAGE_PATH = "/resources/images/";
    public static final String FONT_PATH = "/resources/fonts/";
    public static final String AUDIO_PATH = "/resources/audio/";
    public static final String DEFAULT_FONT_PATH = "Tiny5-Regular.ttf";
    public static final String DEFAULT_PRESSED_IMAGE_PATH = "keney_ui_pack_rpg/buttonLong_blue_pressed.png";
    public static final String DEFAULT_UNPRESSED_IMAGE_PATH = "keney_ui_pack_rpg/buttonLong_blue.png";

}
