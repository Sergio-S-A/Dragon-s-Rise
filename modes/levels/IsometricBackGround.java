package modes.levels;

import core.main.GameConstants;
import entities.entity.Entity;
import modes.menus.LevelData;
import resources.ResourceManager;

import java.awt.*;

public class IsometricBackGround {
    private final int TILE_SIZE;
    private final Camera camera;
    private final ResourceManager resourceManager;
    private final int ratio;
    private int[][] imagesData;
    private int playerTileX;
    private int playerTileY;
    private int xMin, yMin, xMax, yMax;
    private int screenX, screenY;

    public IsometricBackGround() {
        this.resourceManager = ResourceManager.getInstance();
        this.imagesData = LevelData.testLevel;
        this.TILE_SIZE = GameConstants.TILE_SIZE;
        this.camera = new Camera(TILE_SIZE);
        this.ratio = 11;
    }

    public void update(Entity player) {
        camera.update(player);
        playerTileX = (int) (player.getPosition().x());
        playerTileY = (int) (player.getPosition().y());

        xMin = Math.max(0, playerTileX - ratio);
        yMin = Math.max(0, playerTileY - ratio);
        xMax = Math.min(imagesData[0].length, playerTileX + ratio);
        yMax = Math.min(imagesData.length, playerTileY + ratio);
    }

    public void draw(Graphics2D graphics2d) {
        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {
                calculateScreenPosition(x, y);
                if (isVisible(x, y)) {
                    drawTile(graphics2d, imagesData[y][x]);
                }
            }
        }
    }

    public boolean isVisible(int screenX, int screenY) {
        return screenX > -TILE_SIZE
                && screenX < GameConstants.WINDOW_WIDTH
                && screenY > -TILE_SIZE
                && screenY < GameConstants.WINDOW_HEIGHT;
    }

    private void calculateScreenPosition(int x, int y) {
        screenX = (int) ((x - y) * (TILE_SIZE / 2.0) - camera.getOffsetX());
        screenY = (int) ((x + y) * (TILE_SIZE / 4.0) - camera.getOffsetY());
    }

    private void drawTile(Graphics2D graphics2d, int tileId) {
        String tilePath = createTilePath(tileId);
        graphics2d.drawImage(resourceManager.getImage(tilePath, TILE_SIZE, TILE_SIZE), screenX, screenY, null);
    }

    private String createTilePath(int tileId) {
        return String.format(GameConstants.TILE_PATH_CONFIG_STRING, tileId);
    }

    public void setImagesData(int[][] imagesData) {
        this.imagesData = imagesData;
    }
}
