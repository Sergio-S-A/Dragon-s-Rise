package features.tile.domain;

import features.tile.domain.model.Tile;
import features.tile.domain.model.TileType;

public interface TileRepository {

    void loadTiles(String tilePathConfigString);

    Tile getTile(TileType tileType);
}
