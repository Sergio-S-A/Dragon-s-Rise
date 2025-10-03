package modes.levels;

import core.main.GameConstants;
import entities.entity.Entity;
import modes.menus.LevelData;
import resources.ResourceManager;

import java.awt.*;


public class IsometricBackGround {
    private int[][] imagesData;
    private int TILE_SIZE;
    private Camera camera;
    private int screenX;
    private int screenY;
    private ResourceManager resourceManager;

    public IsometricBackGround(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.imagesData = LevelData.testLevel;
        TILE_SIZE = GameConstants.TILE_SIZE;
        camera = new Camera(GameConstants.TILE_SIZE);
    }

    public void update(Entity player) {
        camera.update(player);
    }

    public void draw(Graphics2D graphics2d) {
        for (int y = 0; y < imagesData.length; y++) {
            for (int x = 0; x < imagesData[y].length; x++) {
                calculateScreenPosition(x, y);
                if (isVisible(screenX, screenY)) {
                    drawTile(graphics2d, imagesData[y][x]);
                }
            }
        }
    }

    private void calculateScreenPosition(int x, int y) {
        screenX = (int) ((x - y) * (TILE_SIZE / 2.0) - camera.getOffsetX());
        screenY = (int) ((x + y) * (TILE_SIZE / 4.0) - camera.getOffsetY());
    }

    private void drawTile(Graphics2D graphics2d, int tileId) {
        String tilePath = createTilePath(tileId);
        graphics2d.drawImage(resourceManager.loadImage(tilePath, TILE_SIZE, TILE_SIZE), screenX, screenY, null);
    }

    public boolean isVisible(int screenX, int screenY) {
        return screenX > -TILE_SIZE && screenX < GameConstants.WINDOW_WIDTH &&
                screenY > -TILE_SIZE && screenY < GameConstants.WINDOW_HEIGHT;
    }

    private String createTilePath(int tileId) {
        return String.format(GameConstants.TILE_PATH_CONFIG_STRING, tileId);
    }

    public void setImagesData(int[][] imagesData) {
        this.imagesData = imagesData;
    }
}
