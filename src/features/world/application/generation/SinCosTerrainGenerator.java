package features.world.application.generation;

import core.math.FastMath;
import features.world.domain.model.TerrainFunction;

public class SinCosTerrainGenerator implements TerrainFunction {

    @Override
    public int apply(int x, int y) {
        float sinValue = FastMath.sin(x);
        float cosValue = FastMath.cos(y);
        return (int) (sinValue + cosValue + 2);
    }
}
