package features.ui.infrastructure;

import core.config.GameConfig;
import features.ui.domain.model.Button;
import platform.desktop.assets.AssetCache;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ButtonRenderer {

    private final AffineTransform reusableTransform = new AffineTransform();
    private final LabelRenderer labelRenderer;
    private final Button button;
    private final BufferedImage pressedImage;
    private final BufferedImage unpressedImage;
    private BufferedImage currentImage;

    public ButtonRenderer(Button button, BufferedImage pressedImage, BufferedImage unpressedImage) {
        this.button = button;
        this.pressedImage = pressedImage;
        this.unpressedImage = unpressedImage;
        this.labelRenderer = new LabelRenderer(button.getLabelComponent());
    }

    public ButtonRenderer(Button button) {
        this.button = button;
        this.pressedImage = loadDefaultPressedImage();
        this.unpressedImage = loadDefaultUnpressedImage();
        this.labelRenderer = new LabelRenderer(button.getLabelComponent());
    }

    public void render(Graphics2D graphics2d) {
        renderButtonBackground(graphics2d);
        renderButtonText(graphics2d);
    }

    private void renderButtonBackground(Graphics2D graphics2d) {
        currentImage = button.shouldShowPressedState() ? pressedImage : unpressedImage;
        reusableTransform.setToTranslation(button.getButtonPosition().getX(), button.getButtonPosition().getY());
        graphics2d.drawImage(currentImage, reusableTransform, null);
    }

    private void renderButtonText(Graphics2D graphics2d) {
        labelRenderer.render(graphics2d);
    }

    private BufferedImage loadDefaultPressedImage() {
        return AssetCache.getInstance().getImage(
                GameConfig.DEFAULT_PRESSED_IMAGE_PATH,
                GameConfig.DEFAULT_BUTTON_WIDTH,
                GameConfig.DEFAULT_BUTTON_HEIGHT
        );
    }

    private BufferedImage loadDefaultUnpressedImage() {
        return AssetCache.getInstance().getImage(
                GameConfig.DEFAULT_UNPRESSED_IMAGE_PATH,
                GameConfig.DEFAULT_BUTTON_WIDTH,
                GameConfig.DEFAULT_BUTTON_HEIGHT
        );
    }

}
