package features.world.application;

import features.world.application.generation.ProceduralTerrain;
import features.world.application.texture.CrystalCaveTexture;
import features.world.domain.TerrainMap;
import features.world.domain.model.RiverProvider;
import features.world.domain.model.TextureStrategy;

import java.util.HashMap;
import java.util.Map;

public class TerrainMapRepository {

    private static TerrainMapRepository instance;
    private final Map<String, TerrainMap> dataMap = new HashMap<>(8);

    private TerrainMapRepository() {
    }

    public static TerrainMapRepository getInstance() {
        if (instance == null) {
            instance = new TerrainMapRepository();
        }
        return instance;
    }

    public void loadBackgroundData() {
        dataMap.put("backgroundData1", createTerrain());
    }


    private TerrainMap createTerrain() {

        RiverProvider riverProvider = new RiverProvider();
        TextureStrategy textureFn = new CrystalCaveTexture(riverProvider);

        int amplitude = 10;

        ProceduralTerrain terrainFunction = new ProceduralTerrain(10, amplitude, 0.3f);

        TerrainMap terrainMap = new TerrainMap(256, 256, amplitude);
        terrainMap.loadTerrain(terrainFunction, textureFn);
        return terrainMap;
    }

    public TerrainMap getBackgroundData(String key) {
        return dataMap.get(key);
    }

}
