package features.entities.player.domain;

import core.config.GameConfig;
import core.math.Vector2d;
import core.memory.Vector2dPool;
import core.physics.Position;
import features.entities.base.domain.model.Actor;

public class PlayerAvatar extends Actor {

    private final Vector2d isometricCenter;

    public PlayerAvatar(PlayerBuilder playerBuilder) {
        super(playerBuilder);
        float offsetX = playerBuilder.offsetX;
        float offsetY = playerBuilder.offsetY;
        this.isometricCenter = Vector2dPool.acquire();
        isometricCenter.set(offsetX - (float) getTileSize() / 2, offsetY - getTileSize());
    }

    public void update() {
        getPosition().limit();
    }

    public Vector2d getIsometricCenter() {
        return isometricCenter;
    }

    public static class PlayerBuilder extends ActorBuilder<PlayerBuilder> {

        private float offsetX;
        private float offsetY;

        public PlayerBuilder(Position position, String playerId) {
            super(position, playerId);
            this.offsetX = GameConfig.WINDOW_WIDTH_MEDIUM;
            this.offsetY = GameConfig.WINDOW_HEIGHT_MEDIUM;
        }

        public PlayerBuilder setOffsetXY(float offsetX, float offsetY) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            return this;
        }

        private void validateOffsetXY() {
            if (offsetX < 0 || offsetY < 0) {
                throw new IllegalArgumentException("OffsetX and OffsetY must be greater than 0");
            }
        }

        @Override
        protected PlayerBuilder self() {
            return this;
        }

        @Override
        public PlayerAvatar build() {
            validateOffsetXY();
            return new PlayerAvatar(this);
        }
    }
}
