package features.ui.infrastructure;

import features.ui.domain.model.Label;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class LabelRenderer {

    private final Label label;
    private final AffineTransform transform = new AffineTransform();

    private BufferedImage cachedTextImage;
    private boolean cacheValid = false;

    public LabelRenderer(Label label) {
        this.label = label;
    }

    public void render(Graphics2D g2d) {
        if (isTextEmpty()) {
            return;
        }

        prepareGraphicsForRendering(g2d);

        if (label.isMetricsNeedUpdate()) {
            updateTextMetrics(g2d);
            invalidateCache();
            label.setMetricsNeedUpdate(false);
        }

        if (!cacheValid) {
            regenerateCache(g2d);
            cacheValid = true;
        }

        if (cachedTextImage != null) {
            renderCachedText(g2d);
        }
    }

    private boolean isTextEmpty() {
        return label.getText() == null || label.getText().trim().isEmpty();
    }

    private void prepareGraphicsForRendering(Graphics2D g2d) {
        g2d.setFont(label.getFont());
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF
        );
    }

    private void updateTextMetrics(Graphics2D g2d) {
        FontMetrics fm = g2d.getFontMetrics(label.getFont());
        label.setCachedTextWidth(fm.stringWidth(label.getText()));
        label.setCachedTextHeight(fm.getHeight());
    }

    private void regenerateCache(Graphics2D g2d) {
        FontMetrics fm = g2d.getFontMetrics(label.getFont());
        int textWidth = fm.stringWidth(label.getText());
        int textHeight = fm.getHeight();
        int padding = label.hasOutline() ? label.getOutlineSize() * 2 : 0;

        if (cachedTextImage != null) {
            cachedTextImage.flush();
        }

        cachedTextImage = new BufferedImage(
                textWidth + padding * 2,
                textHeight + padding * 2,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D imgG2d = cachedTextImage.createGraphics();
        imgG2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF
        );
        imgG2d.setFont(label.getFont());

        int baseX = padding;
        int baseY = fm.getAscent() + padding;

        if (label.hasOutline()) {
            imgG2d.setColor(label.getOutlineColor());
            int outlineSize = label.getOutlineSize();

            for (int[] offset : Label.OUTLINE_OFFSETS) {
                imgG2d.drawString(
                        label.getText(),
                        baseX + offset[0] * outlineSize,
                        baseY + offset[1] * outlineSize
                );
            }
        }

        imgG2d.setColor(label.getColor());
        imgG2d.drawString(label.getText(), baseX, baseY);
        imgG2d.dispose();
    }

    private void renderCachedText(Graphics2D g2d) {
        FontMetrics fm = g2d.getFontMetrics(label.getFont());
        int padding = label.hasOutline() ? label.getOutlineSize() * 2 : 0;

        int x = label.calculateTextX() - padding;
        int y = (int) label.getPosition().getY() - (fm.getAscent() + padding);

        transform.setToTranslation(x, y);
        g2d.drawImage(cachedTextImage, transform, null);
    }

    public void invalidateCache() {
        cacheValid = false;
    }

    public void dispose() {
        if (cachedTextImage != null) {
            cachedTextImage.flush();
            cachedTextImage = null;
        }
        cacheValid = false;
    }
}