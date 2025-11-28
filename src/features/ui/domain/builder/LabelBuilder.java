package features.ui.domain.builder;

import core.config.GameConfig;
import core.math.Vector2d;
import core.memory.Vector2dPool;
import features.ui.domain.model.Label;
import platform.desktop.assets.AssetCache;

import java.awt.*;

public class LabelBuilder {
    // Text properties with defaults
    private String text;
    private Vector2d position;
    private Color color;
    private features.ui.domain.model.Label.Alignment alignment;
    private Font font;

    // Outline properties with defaults
    private boolean hasOutline;
    private Color outlineColor;

    // Constructor
    public LabelBuilder() {
        initializeDefaultValues();
    }

    private void initializeDefaultValues() {
        this.text = "";
        this.position = Vector2dPool.acquire();
        this.color = Color.WHITE;
        this.alignment = features.ui.domain.model.Label.Alignment.LEFT;
        this.hasOutline = false;
        this.outlineColor = Color.BLACK;
        this.font = createDefaultFont();
    }

    public LabelBuilder setPosition(float x, float y) {
        this.position.set(x, y);
        return this;
    }

    public LabelBuilder setColor(int red, int green, int blue) {
        return setColor(createColorSafely(red, green, blue));
    }

    public LabelBuilder setOutline(boolean hasOutline) {
        this.hasOutline = hasOutline;
        return this;
    }

    public LabelBuilder setOutlineColor(int red, int green, int blue) {
        return setOutlineColor(createColorSafely(red, green, blue));
    }

    public Label build() {
        validateBuilderState();
        return new Label(this);
    }

    private void validateBuilderState() {
        if (text == null) {
            throw new IllegalStateException("Text cannot be null when building");
        }
        if (position == null) {
            throw new IllegalStateException("Position cannot be null when building");
        }
        if (font == null) {
            throw new IllegalStateException("Font cannot be null when building");
        }
    }

    private Font createDefaultFont() {
        try {
            return AssetCache.getInstance().getFont(GameConfig.DEFAULT_FONT_PATH, GameConfig.DEFAULT_FONT_SIZE);
        } catch (Exception e) {
            return new Font(Font.SANS_SERIF, Font.PLAIN, GameConfig.DEFAULT_FONT_SIZE);
        }
    }

    private Color createColorSafely(int red, int green, int blue) {
        try {
            int safeRed = Math.max(0, Math.min(255, red));
            int safeGreen = Math.max(0, Math.min(255, green));
            int safeBlue = Math.max(0, Math.min(255, blue));
            return new Color(safeRed, safeGreen, safeBlue);
        } catch (Exception e) {
            return Color.WHITE;
        }
    }

    public String getText() {
        return text;
    }

    public LabelBuilder setText(String text) {
        this.text = text != null ? text : "";
        return this;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public LabelBuilder setColor(Color color) {
        this.color = color != null ? color : Color.WHITE;
        return this;
    }

    public features.ui.domain.model.Label.Alignment getAlignment() {
        return alignment;
    }

    public LabelBuilder setAlignment(features.ui.domain.model.Label.Alignment alignment) {
        this.alignment = alignment != null ? alignment : features.ui.domain.model.Label.Alignment.LEFT;
        return this;
    }

    public Font getFont() {
        return font;
    }

    public LabelBuilder setFont(Font font) {
        this.font = font != null ? font : createDefaultFont();
        return this;
    }

    public boolean isHasOutline() {
        return hasOutline;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public LabelBuilder setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor != null ? outlineColor : Color.BLACK;
        return this;
    }
}
