package features.view.domain;

import features.entities.base.domain.model.GameEntity;

public abstract class Camera {

    protected int ratio;
    protected int tileSizeWidth;
    protected float offsetX;
    protected float offsetY;
    protected GameEntity target;

    public Camera(GameEntity target, int tileSizeWidth, int ratio) {
        this.tileSizeWidth = tileSizeWidth;
        this.ratio = ratio;
        this.target = target;
    }

    public abstract void update();

    public GameEntity getTarget() {
        return target;
    }

    public void setTarget(GameEntity target) {
        this.target = target;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public int getTileSizeWidth() {
        return tileSizeWidth;
    }
}
