package features.world.domain.model;

import core.math.FastMath;

public class RiverProvider {

    private static final float RIVER_WIDTH = 0.15f;

    /**
     * Devuelve un valor entre 0.0 (Sin río) y 1.0 (Centro del río)
     */
    public float getRiverFactor(int x, int y) {
        float val1 = FastMath.sin(x / 20.0f) + FastMath.cos(y / 20.0f);
        float val2 = FastMath.sin(y / 7.0f) * 0.4f;
        float noiseValue = val1 + val2;
        float distToCenter = Math.abs(noiseValue);

        if (distToCenter >= RIVER_WIDTH) {
            return 0.0f;
        }

        return (RIVER_WIDTH - distToCenter) / RIVER_WIDTH;
    }

    public boolean isRiver(int x, int y) {
        return getRiverFactor(x, y) > 0;
    }
}