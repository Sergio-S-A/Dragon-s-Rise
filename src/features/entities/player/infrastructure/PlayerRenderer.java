package features.entities.player.infrastructure;

import core.math.Vector2d;
import features.entities.base.domain.animation.AnimationFrame;
import features.entities.base.infrastructure.CharacterRenderer;
import features.entities.base.infrastructure.SpriteMemoryCache;
import features.entities.player.domain.PlayerAvatar;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerRenderer extends CharacterRenderer<PlayerAvatar> {

    public PlayerRenderer(PlayerAvatar playerAvatar) {
        super(playerAvatar);
    }

    @Override
    public void render(Graphics2D graphics2D) {

        AnimationFrame animationFrame = character.getCurrentAnimationFrame();

        if (animationFrame == null) return;

        BufferedImage image = SpriteMemoryCache.getImage(animationFrame.spriteId());

        Vector2d renderPos = character.getIsometricCenter();
        int renderX = (int) renderPos.getX();
        int renderY = (int) (renderPos.getY() - cacheIsometricHeight * image.getHeight() * ISOMETRIC_HEIGHT_FACTOR);

        reusableTransform.setToTranslation(renderX, renderY);
        graphics2D.drawImage(image, reusableTransform, null);
    }


    public PlayerAvatar getPlayer() {
        return character;
    }
}
