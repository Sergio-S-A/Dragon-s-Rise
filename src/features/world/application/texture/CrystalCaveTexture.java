package features.world.application.texture;

import features.tile.domain.model.TileType;
import features.world.domain.model.RiverProvider;
import features.world.domain.model.TextureStrategy;

public class CrystalCaveTexture implements TextureStrategy {

    private final RiverProvider riverProvider;

    public CrystalCaveTexture(RiverProvider riverProvider) {
        this.riverProvider = riverProvider;
    }

    @Override
    public TileType getTextureid(int x, int y, int z) {
        if (riverProvider.isRiver(x, y)) {
            return TileType.WATER;
        }

        float chance = (float) Math.random();

        // Chance to PILAR
        if (chance < 0.006) {
            return ((x + y) % 2 == 1) ? TileType.PILAR_BIG1
                    : TileType.PILAR_BIG2;
        }

        // Chance to CRYSTAL MEDIUM
        if (chance < 0.015) {
            return (x + y) % 2 == 0 ? TileType.CRYSTAL_MEDIUM1
                    : TileType.CRYSTAL_MEDIUM2;

        }
        // Chance to CRYSTAL SMALL
        if (chance < 0.04) {
            return ((x + y) % 2 == 1) ? TileType.CRYSTAL_SMALL3
                    : TileType.CRYSTAL_SMALL4;
        }

        // Chance to STALAGMITE
        if (chance < 0.06) {
            return TileType.STALAGMITE;
        }


        if (chance < 0.08) {
            return ((x + y) % 2 == 1) ? TileType.CRYSTAL_SMALL1
                    : TileType.CRYSTAL_SMALL2;
        }

        // Patrón simple para el resto
        return ((x + y) % 3 == 0) ? TileType.STONE1
                : ((x + y) % 3 == 1) ? TileType.STONE2
                : TileType.STONE3;
    }
}