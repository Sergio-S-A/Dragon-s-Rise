package features.tile.application;

import core.config.GameConfig;
import features.tile.domain.TileRepository;
import features.tile.domain.model.Tile;
import features.tile.domain.model.TileType;
import features.view.domain.Camera;
import features.world.domain.TerrainMap;

public class IsometricTileRenderer {

    private final TileRepository tileRepository;
    private final TerrainMap terrainMap;
    private final Camera camera;
    private final int length, width;
    private float targetX, targetY;

    private int startCol, endCol;
    private int startRow, endRow;


    public IsometricTileRenderer(TerrainMap terrainMap, Camera camera, TileRepository tileRepository) {
        this.terrainMap = terrainMap;
        this.camera = camera;
        this.targetX = 0;
        this.targetY = 0;
        length = terrainMap.getLength();
        width = terrainMap.getWidth();
        this.tileRepository = tileRepository;
    }

    private String createTilePath(int tileId) {
        return String.format(GameConfig.TILE_PATH_CONFIG_STRING, tileId);
    }

    public void update() {

        camera.update();

        float newX = camera.getTarget().getPosition().getX();
        float newY = camera.getTarget().getPosition().getY();

        if (targetX == newX && targetY == newY) return;

        targetX = newX;
        targetY = newY;

        int ratio = camera.getRatio();
        startCol = Math.max((int) (targetX - ratio), 0);
        startRow = Math.max((int) (targetY - ratio), 0);
        endCol = Math.min((int) (targetX + ratio), width - 1);
        endRow = Math.min((int) (targetY + ratio), length - 1);

    }

    public Camera getCamera() {
        return camera;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public int getEndRow() {
        return endRow;
    }


    public Tile getTile(int x, int y) {
        byte tileId = terrainMap.getTileId(x, y);
        TileType tileType = TileType.values()[tileId];
        return tileRepository.getTile(tileType);
    }

    public Tile getTileDefault() {
        return tileRepository.getTile(TileType.STONE1);
    }

    public int getHeight(int x, int y) {
        return terrainMap.getHeight(x, y);
    }

    public int getTerrainWidth() {
        return width;
    }

    public int getTerrainLength() {
        return length;
    }
}
