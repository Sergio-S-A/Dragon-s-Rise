package features.entities.base.infrastructure;


import features.entities.base.domain.animation.AnimationFrame;
import features.entities.base.domain.model.Actor;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class CharacterRenderer<T extends Actor> {
    protected static final float ISOMETRIC_HEIGHT_FACTOR = 0.125f;
    protected final AffineTransform reusableTransform = new AffineTransform();
    protected final T character;
    protected int cacheIsometricHeight;

    public CharacterRenderer(T character) {
        this.character = character;
        this.cacheIsometricHeight = 0;
    }

    public abstract void render(Graphics2D graphics2D);

    public void updateIsometricHeight(int cacheIsometricHeight) {
        this.cacheIsometricHeight = cacheIsometricHeight;
    }

    public int getIsometricHeight() {
        return cacheIsometricHeight;
    }

    public T getCharacter() {
        return character;
    }

    public AnimationFrame getCurrentAnimationFrame() {
        return character.getCurrentAnimationFrame();
    }
}
