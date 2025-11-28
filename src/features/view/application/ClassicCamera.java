package features.view.application;

import core.config.GameConfig;
import features.entities.base.domain.model.GameEntity;
import features.view.domain.Camera;

public class ClassicCamera extends Camera {

    public ClassicCamera(GameEntity target, int tileSizeWidth, int ratio) {
        super(target, tileSizeWidth, ratio);
    }

    @Override
    public void update() {
        float targetX = target.getPosition().getX();
        float targetY = target.getPosition().getY();

        offsetX = targetX - (GameConfig.WINDOW_WIDTH_MEDIUM - tileSizeWidth / 2.0f);
        offsetY = targetY - (GameConfig.WINDOW_HEIGHT_MEDIUM - tileSizeWidth / 2.0f);
    }
}
