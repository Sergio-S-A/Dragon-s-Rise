package ui.components;

import core.main.GameConstants;
import core.physics.Vector2D;
import resources.ResourceManager;

import java.awt.*;

public class Text extends Component {

    private final Vector2D position;
    // Text properties
    private String text;
    private Color color;
    private Font font;
    private Alignment alignment;

    // Outline properties
    private boolean hasOutline;
    private Color outlineColor;
    private int outlineSize;

    // Cached metrics for performance
    private FontMetrics cachedFontMetrics;
    private int cachedTextWidth;
    private boolean metricsNeedUpdate = true;

    // Constructor
    private Text(Builder builder) {
        this.text = builder.text;
        this.position = new Vector2D(builder.position.x(), builder.position.y());
        this.color = builder.color;
        this.font = builder.font;
        this.alignment = builder.alignment;
        this.hasOutline = builder.hasOutline;
        this.outlineColor = builder.outlineColor;
        this.outlineSize = calculateOutlineSize(font.getSize());
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        if (isTextEmpty()) {
            return;
        }

        prepareGraphicsForRendering(graphics2d);
        updateMetricsIfNeeded(graphics2d);

        if (hasOutline) {
            renderOutlineText(graphics2d);
        }
        renderMainText(graphics2d);
    }

    private boolean isTextEmpty() {
        return text == null || text.trim().isEmpty();
    }

    private void prepareGraphicsForRendering(Graphics2D graphics2d) {
        graphics2d.setFont(font);
        enableTextAntiAliasing(graphics2d);
    }

    private void enableTextAntiAliasing(Graphics2D graphics2d) {
        graphics2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
    }

    private void updateMetricsIfNeeded(Graphics2D graphics2d) {
        if (metricsNeedUpdate) {
            updateTextMetrics(graphics2d);
            metricsNeedUpdate = false;
        }
    }

    private void updateTextMetrics(Graphics2D graphics2d) {
        cachedFontMetrics = graphics2d.getFontMetrics(font);
        cachedTextWidth = cachedFontMetrics.stringWidth(text);
    }

    private void renderOutlineText(Graphics2D graphics2d) {
        graphics2d.setColor(outlineColor);

        for (int xOffset = -outlineSize; xOffset <= outlineSize; xOffset++) {
            for (int yOffset = -outlineSize; yOffset <= outlineSize; yOffset++) {
                if (xOffset != 0 || yOffset != 0) { // Skip center position
                    int outlineX = calculateTextX() + xOffset;
                    int outlineY = (int) position.y() + yOffset;
                    graphics2d.drawString(text, outlineX, outlineY);
                }
            }
        }
    }

    private void renderMainText(Graphics2D graphics2d) {
        graphics2d.setColor(color);
        graphics2d.drawString(text, calculateTextX(), (int) position.y());
    }

    private int calculateTextX() {
        int baseX = (int) position.x();

        return switch (alignment) {
            case LEFT -> baseX;
            case CENTER -> baseX - cachedTextWidth / 2;
            case RIGHT -> baseX - cachedTextWidth;
        };
    }

    private int calculateOutlineSize(int fontSize) {
        return Math.max(fontSize / GameConstants.OUTLINE_SIZE_DIVISOR, GameConstants.MIN_OUTLINE_SIZE);
    }

    // Setters and Getters
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void setOutline(boolean enableOutline) {
        this.hasOutline = enableOutline;
    }

    public void setOutlineSize(int customOutlineSize) {
        this.outlineSize = Math.max(customOutlineSize, 0);
    }

    public String getText() {
        return text;
    }

    public void setText(String newText) {
        if (!isTextEqual(this.text, newText)) {
            this.text = newText != null ? newText : "";
            invalidateMetrics();
        }
    }

    public Vector2D getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        this.color = newColor != null ? newColor : Color.WHITE;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font newFont) {
        if (newFont != null && !newFont.equals(this.font)) {
            this.font = newFont;
            this.outlineSize = calculateOutlineSize(newFont.getSize());
            invalidateMetrics();
        }
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment newAlignment) {
        this.alignment = newAlignment != null ? newAlignment : Alignment.LEFT;
    }

    public boolean hasOutline() {
        return hasOutline;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color newOutlineColor) {
        this.outlineColor = newOutlineColor != null ? newOutlineColor : Color.BLACK;
    }

    public int getHeight() {
        return font.getSize();
    }

    public int getTextWidth() {
        return cachedTextWidth;
    }

    private void invalidateMetrics() {
        metricsNeedUpdate = true;
    }

    private boolean isTextEqual(String text1, String text2) {
        if (text1 == null && text2 == null) return true;
        if (text1 == null || text2 == null) return false;
        return text1.equals(text2);
    }

    public enum Alignment {
        LEFT("Align text to the left"),
        CENTER("Center text horizontally"),
        RIGHT("Align text to the right");

        private final String description;

        Alignment(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    // Builder
    public static class Builder {

        // Required dependency

        // Text properties with defaults
        private String text;
        private Vector2D position;
        private Color color;
        private Alignment alignment;
        private Font font;

        // Outline properties with defaults
        private boolean hasOutline;
        private Color outlineColor;

        // Constructor
        public Builder() {
            initializeDefaultValues();
        }

        private void initializeDefaultValues() {
            this.text = "";
            this.position = new Vector2D(0, 0);
            this.color = Color.WHITE;
            this.alignment = Alignment.LEFT;
            this.hasOutline = false;
            this.outlineColor = Color.BLACK;
            this.font = createDefaultFont();
        }

        public Builder setText(String text) {
            this.text = text != null ? text : "";
            return this;
        }

        public Builder setPosition(float x, float y) {
            if (position != null) {
                this.position.set(x, y);
            }
            return this;
        }

        public Builder setColor(Color color) {
            this.color = color != null ? color : Color.WHITE;
            return this;
        }

        public Builder setColor(int red, int green, int blue) {
            return setColor(createColorSafely(red, green, blue));
        }

        public Builder setFont(Font font) {
            this.font = font != null ? font : createDefaultFont();
            return this;
        }


        public Builder setAlignment(Alignment alignment) {
            this.alignment = alignment != null ? alignment : Alignment.LEFT;
            return this;
        }

        public Builder setOutline(boolean hasOutline) {
            this.hasOutline = hasOutline;
            return this;
        }

        public Builder setOutlineColor(Color outlineColor) {
            this.outlineColor = outlineColor != null ? outlineColor : Color.BLACK;
            return this;
        }

        public Builder setOutlineColor(int red, int green, int blue) {
            return setOutlineColor(createColorSafely(red, green, blue));
        }

        public Text build() {
            validateBuilderState();
            return new Text(this);
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
                return ResourceManager.getInstance().getFont(GameConstants.DEFAULT_FONT_PATH, GameConstants.DEFAULT_FONT_SIZE);
            } catch (Exception e) {
                return new Font(Font.SANS_SERIF, Font.PLAIN, GameConstants.DEFAULT_FONT_SIZE);
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
    }
}