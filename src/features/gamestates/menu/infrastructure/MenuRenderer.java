package features.gamestates.menu.infrastructure;

import features.ui.domain.model.Button;
import features.ui.domain.model.Label;
import features.ui.infrastructure.ButtonRenderer;
import features.ui.infrastructure.LabelRenderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MenuRenderer {

    // assets
    private final BufferedImage backgroundImage;
    private final AffineTransform affineTransform = new AffineTransform();

    // renderers
    private final List<LabelRenderer> labelRenderers = new ArrayList<>();
    private final List<ButtonRenderer> buttonRenderers = new ArrayList<>();

    // dimensions
    private final int width, height;

    // Buffer to render in batch
    private BufferedImage renderBuffer;
    private Graphics2D bufferGraphics;
    private int lastBufferWidth = -1;
    private int lastBufferHeight = -1;

    public MenuRenderer(BufferedImage backgroundImage, int width, int height) {
        this.backgroundImage = backgroundImage;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics2D graphicsContext) {
        ensureBufferSize();
        renderBackgroundToBuffer();
        renderLabelsToBuffer();
        renderButtonsToBuffer();
        affineTransform.setToTranslation(0, 0);
        graphicsContext.drawImage(renderBuffer, affineTransform, null);
    }

    private void ensureBufferSize() {

        if (renderBuffer == null || lastBufferWidth != width || lastBufferHeight != height) {
            if (bufferGraphics != null) {
                bufferGraphics.dispose();
            }
            renderBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferGraphics = renderBuffer.createGraphics();
            bufferGraphics.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );
            bufferGraphics.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );
            lastBufferWidth = width;
            lastBufferHeight = height;
        }
    }

    private void renderBackgroundToBuffer() {

        if (backgroundImage != null) {
            bufferGraphics.drawImage(backgroundImage, 0, 0, null);
        }
    }

    private void renderLabelsToBuffer() {
        for (LabelRenderer labelRenderer : labelRenderers) {
            labelRenderer.render(bufferGraphics);
        }
    }

    private void renderButtonsToBuffer() {
        for (ButtonRenderer buttonRenderer : buttonRenderers) {
            buttonRenderer.render(bufferGraphics);
        }
    }

    public void dispose() {
        if (bufferGraphics != null) {
            bufferGraphics.dispose();
        }
        labelRenderers.clear();
        buttonRenderers.clear();
    }

    public void setButtonList(List<Button> buttonList) {
        buttonRenderers.clear();
        buttonList.forEach(btn -> buttonRenderers.add(new ButtonRenderer(btn)));
    }

    public void setLabelList(List<Label> labelList) {
        labelRenderers.clear();
        labelList.forEach(label -> labelRenderers.add(new LabelRenderer(label)));
    }
}