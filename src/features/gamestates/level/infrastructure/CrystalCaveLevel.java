package features.gamestates.level.infrastructure;

import core.config.GameConfig;
import core.physics.Position;
import features.entities.base.domain.animation.AnimationSystem;
import features.entities.base.infrastructure.AssetSpriteRepository;
import features.entities.base.infrastructure.CharacterRenderer;
import features.entities.player.application.PlayerController;
import features.entities.player.domain.PlayerAvatar;
import features.entities.player.infrastructure.PlayerRenderer;
import features.gamestates.base.domain.TypeState;
import features.gamestates.base.domain.ports.RendererState;
import features.gamestates.level.domain.LevelState;
import features.tile.application.IsometricTileRenderer;
import features.tile.infrastructure.TileMapRepository;
import features.view.application.IsometricCamera;
import features.view.domain.Camera;
import features.world.application.TerrainMapRepository;
import features.world.infrastructure.WorldRendererState;
import platform.desktop.assets.AssetLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CrystalCaveLevel extends LevelState implements RendererState<Graphics2D> {

    // Configuration Constants
    private static final String CHARACTER_ID = "wolf";
    private static final String MAP_ID = "backgroundData1";
    private static final Position START_POS = new Position(36, 36);
    private static final int RATIO_OF_RENDERED_TILES = 30;

    // Physics Config
    private static final float PLAYER_MASS = 60f;
    private static final float PLAYER_FRICTION = 5f;
    private static final float PLAYER_ACCELERATION = 10f;
    private static final float PLAYER_SPEED = 10f;
    private static final float PLAYER_FORCE = 500f;

    // Renderer and Assets
    private WorldRendererState worldRenderer;
    private final AssetSpriteRepository spriteRepo;

    public CrystalCaveLevel(AssetLoader assetLoader, PlayerController playerController, TypeState typeState) {
        super(typeState, playerController);
        spriteRepo = new AssetSpriteRepository(assetLoader);

        PlayerAvatar localPlayerAvatar = configurePlayer(assetLoader);

        PlayerRenderer playerRenderer = new PlayerRenderer(localPlayerAvatar);

        configureWorld(assetLoader, localPlayerAvatar, playerRenderer);
    }


    @Override
    public void render(Graphics2D graphics2D) {
        worldRenderer.render(graphics2D);
    }


    private PlayerAvatar configurePlayer(AssetLoader assetLoader) {

        AnimationSystem animationSystem = spriteRepo.loadAnimation(
                CHARACTER_ID,
                new long[]{128_000_000L, 64_000_000L, 50_000_000L},
                new int[]{4, 8, 15},
                GameConfig.TILE_SIZE_PLAYER,
                GameConfig.TILE_SIZE_PLAYER
        );

        return new PlayerAvatar.PlayerBuilder(START_POS, CHARACTER_ID)
                .setAnimation(animationSystem)
                .setMass(PLAYER_MASS)
                .setFrictionCoefficient(PLAYER_FRICTION)
                .setMaxAcceleration(PLAYER_ACCELERATION)
                .setMaxSpeed(PLAYER_SPEED)
                .setForceScale(PLAYER_FORCE)
                .setOffsetXY(GameConfig.WINDOW_WIDTH_MEDIUM, GameConfig.WINDOW_HEIGHT_MEDIUM)
                .setTileSize(GameConfig.TILE_SIZE_PLAYER)
                .build();
    }

    private void configureWorld(AssetLoader assetLoader, PlayerAvatar targetPlayerAvatar, PlayerRenderer playerRenderer) {
        // Load Tiles
        TileMapRepository tileRepo = new TileMapRepository(assetLoader);
        tileRepo.loadTiles(GameConfig.TILE_PATH_CONFIG_STRING);

        TerrainMapRepository.getInstance().loadBackgroundData();

        // Setup Camera & Isometric Renderer
        Camera camera = new IsometricCamera(targetPlayerAvatar, tileRepo.getTileSize(), RATIO_OF_RENDERED_TILES);

        IsometricTileRenderer isoRenderer = new IsometricTileRenderer(
                TerrainMapRepository.getInstance().getBackgroundData(MAP_ID),
                camera,
                tileRepo
        );

        // Link to Parent LevelState
        setIsometricBackGround(isoRenderer);

        List<CharacterRenderer> characterRendererList = new ArrayList<>();
        characterRendererList.add(playerRenderer);

        this.worldRenderer = new WorldRendererState(isoRenderer, characterRendererList);
    }
}