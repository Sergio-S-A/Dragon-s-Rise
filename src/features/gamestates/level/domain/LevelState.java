package features.gamestates.level.domain;

import features.entities.base.domain.model.Actor;
import features.entities.base.domain.model.EntityType;
import features.entities.player.application.PlayerController;
import features.entities.player.domain.PlayerAvatar;
import features.gamestates.base.domain.GameState;
import features.gamestates.base.domain.TypeState;
import features.tile.application.IsometricTileRenderer;

import java.util.EnumMap;

public class LevelState extends GameState {

    // --- LevelState State (Entities) ---
    protected final EnumMap<EntityType, Actor> entities = new EnumMap<>(EntityType.class);
    // --- Dependencies ---
    private final PlayerController playerController;
    // --- World Context ---
    protected IsometricTileRenderer isometricTileRenderer;
    protected PlayerAvatar playerAvatar;

    public LevelState(TypeState typeState, PlayerController playerController) {
        super(typeState);
        this.playerController = playerController;
    }

    // --- Configuration ---

    public void setIsometricBackGround(IsometricTileRenderer isometricTileRenderer) {
        this.isometricTileRenderer = isometricTileRenderer;
        this.playerAvatar = (PlayerAvatar) isometricTileRenderer.getCamera().getTarget();

        // Configure boundaries
        int margin = 32;
        this.playerAvatar.getPosition().limitX(margin + 1, isometricTileRenderer.getTerrainWidth() - margin);
        this.playerAvatar.getPosition().limitY(margin + 1, isometricTileRenderer.getTerrainLength() - margin);
    }

    @Override
    public void update() {
        updateEntities();
        if (isometricTileRenderer != null) {
            isometricTileRenderer.update();
        }
    }

    // --- Internal Logic ---

    private void updateEntities() {
        // PlayerAvatar
        if (playerAvatar != null) {
            playerAvatar.update();
            playerController.update(playerAvatar);
        }

        entities.values().forEach(this::updateEntity);
    }

    private void updateEntity(Actor entity) {
        entity.update();
        //physicsUpdater.update(entity);
    }
}