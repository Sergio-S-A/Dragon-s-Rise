package features.view.application;

import core.config.GameConfig;
import features.entities.base.domain.model.GameEntity;
import features.view.domain.Camera;


public class IsometricCamera extends Camera {

    public IsometricCamera(GameEntity target, int tileSizeWidth, int ratio) {
        super(target, tileSizeWidth, ratio);
    }

    public void update() {
        float targetX = target.getPosition().getX();
        float targetY = target.getPosition().getY();

        offsetX = (targetX - targetY) * (tileSizeWidth / 2.0f)
                - (GameConfig.WINDOW_WIDTH_MEDIUM - tileSizeWidth / 2.0f);
        offsetY = (targetX + targetY) * (tileSizeWidth / 4.0f)
                - (GameConfig.WINDOW_HEIGHT_MEDIUM - tileSizeWidth / 2.0f);
    }
}
