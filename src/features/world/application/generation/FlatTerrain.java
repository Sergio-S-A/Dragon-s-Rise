package features.world.application.generation;

import features.world.domain.model.TerrainFunction;

public class FlatTerrain implements TerrainFunction {
    @Override
    public int apply(int x, int y) {
        return 0;
    }
}
