package features.tile.infrastructure;

import core.config.GameConfig;
import features.tile.domain.TileRepository;
import features.tile.domain.model.Tile;
import features.tile.domain.model.TileType;
import platform.desktop.assets.AssetLoader;

import java.util.HashMap;
import java.util.Map;

public class TileMapRepository implements TileRepository {

    private final AssetLoader assetLoader;
    private final Map<TileType, Tile> tileMap = new HashMap<>(128);
    private final Tile defaultTile;


    public TileMapRepository(AssetLoader assetLoader) {
        this.assetLoader = assetLoader;
        this.defaultTile = new Tile(
                String.format(GameConfig.TILE_PATH_CONFIG_STRING, TileType.values()[0].id),
                TileType.values()[0],
                false,
                true
        );
    }

    @Override
    public void loadTiles(String tilePathConfigString) {
        for (int i = GameConfig.TILE_ORIGIN; i < GameConfig.DEFAULT_TILE_NUMBER; i++) {

            TileType tileType = TileType.values()[i];
            String key = String.format(tilePathConfigString, tileType.id);
            Tile tile = new Tile(key, tileType, false, true);

            tileMap.put(tileType, tile);

            TileImageCache.registerImage(key, assetLoader.getImage(key, tileType.width, tileType.height));
        }
    }


    @Override
    public Tile getTile(TileType tileType) {
        return tileMap.get(tileType) != null ? tileMap.get(tileType) : defaultTile;
    }


    public int getTileSize() {
        return defaultTile.tileType().width;
    }

}
