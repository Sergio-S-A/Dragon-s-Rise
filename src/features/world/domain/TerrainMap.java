package features.world.domain;

import features.tile.domain.model.TileType;
import features.world.domain.model.TerrainFunction;
import features.world.domain.model.TextureStrategy;

public class TerrainMap {

    private final byte[] terrainData;
    private final byte[] idTerrain;
    private final int width;
    private final int amplitude;
    private final int length;

    public TerrainMap(int width, int length, int amplitude) {
        if (width <= 0 || length <= 0 || amplitude <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }
        this.width = width;
        this.amplitude = amplitude;
        this.length = length;
        this.terrainData = new byte[width * length];
        this.idTerrain = new byte[width * length];
    }

    public void loadTerrain(TerrainFunction function, TextureStrategy textureStrategy) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < length; y++) {

                int z = function.apply(x, y);

                TileType texture = textureStrategy.getTextureid(x, y, z);

                z += texture.variationHeight;

                terrainData[y * width + x] = (byte) z;


                idTerrain[y * width + x] = texture.id;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getAmplitude() {
        return amplitude;
    }

    public int getLength() {
        return length;
    }

    public byte getTileId(int x, int y) {
        return idTerrain[y * width + x];
    }

    public int getHeight(int x, int y) {
        return terrainData[y * width + x];
    }
}