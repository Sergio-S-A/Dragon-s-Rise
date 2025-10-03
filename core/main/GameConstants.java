package core.main;

public class GameConstants {
    // Game settings
    public static final int FPS = 60;
    public static final double TIME_PER_TICK = 1_000_000_000.0 / FPS;
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 600;
    public static final int TILE_SIZE = 64;

    // Physics
    public static final String TILE_PATH_CONFIG_STRING = "isometric_tileset/tile_%03d.png";

    // Numbers
    public static final double ONE_MILLION = 1_000_000.0;

    public static final int NUM_OF_BUFFERS = 3;

    // Paths
    public static final String IMAGE_PATH = "/resources/images/";
    public static final String FONT_PATH = "/resources/fonts/";
    public static final String AUDIO_PATH = "/resources/audio/";

}
