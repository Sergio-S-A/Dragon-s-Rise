package features.world.application.generation;

import core.math.FastMath;
import features.world.domain.model.TerrainFunction;

public class ProceduralTerrain implements TerrainFunction {

    private final float scale;      // Frecuencia de las ondas
    private final float amplitude;  // Ramgp maximo de altura +/-
    private final float roughness;  // Intensidad del ruido

    public ProceduralTerrain(float scale, float amplitude, float roughness) {
        this.scale = scale;
        this.amplitude = amplitude;
        this.roughness = roughness;
    }

    /**
     * Devuelve la altura z = f(x, y)
     * Genera valores positivos y negativos (picos y valles/agua)
     * Solo se procesa si x, y > 0
     */
    @Override
    public int apply(int x, int y) {

        if (x <= 0 || y <= 0) return 0;

        // Ondas base: Rango [-2.0, 2.0]
        float base = FastMath.sin(x / scale) + FastMath.cos(y / scale);

        // Ruido determinista: Rango [-1.0, 1.0]
        float noise = FastMath.sin(x * 0.11f + y * 0.13f) * FastMath.cos(x * 0.07f - y * 0.09f);

        // Señal combinada normalizada:
        // Dividimos la base entre 2.0 para acercarla al rango unitario [-1, 1]
        // Sumamos el detalle de rugosidad
        float rawSignal = (base / 2.0f) + (noise * roughness);

        return (int) (amplitude * rawSignal);
    }
}