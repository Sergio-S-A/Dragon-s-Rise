package features.world.domain.model;

import features.tile.domain.model.TileType;

public interface TextureStrategy {
    TileType getTextureid(int x, int y, int z);
}
