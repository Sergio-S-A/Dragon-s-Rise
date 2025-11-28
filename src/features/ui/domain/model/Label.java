package features.ui.domain.model;

import core.config.GameConfig;
import core.math.Vector2d;
import features.ui.domain.builder.LabelBuilder;

import java.awt.*;

public class Label extends Component {

    public static final int[][] OUTLINE_OFFSETS = {
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0}, {1, 0},
            {-1, 1}, {0, 1}, {1, 1}
    };
    private final Vector2d position;
    // Text properties
    private String text;
    private Color color;
    private Font font;
    private Alignment alignment;
    // Outline properties
    private boolean hasOutline;
    private Color outlineColor;
    private int outlineSize;
    private int cachedTextWidth;
    private int cachedTextHeight;
    private boolean metricsNeedUpdate = true;

    // Constructor
    public Label(LabelBuilder builder) {
        this.text = builder.getText();
        this.position = builder.getPosition();
        this.color = builder.getColor();
        this.font = builder.getFont();
        this.alignment = builder.getAlignment();
        this.hasOutline = builder.isHasOutline();
        this.outlineColor = builder.getOutlineColor();
        this.outlineSize = calculateOutlineSize(font.getSize());
    }

    public int calculateTextX() {
        int baseX = (int) position.getX();

        return (int) switch (alignment) {
            case LEFT -> baseX;
            case CENTER -> baseX - cachedTextWidth * 0.5;
            case RIGHT -> baseX - cachedTextWidth;
        };
    }

    private int calculateOutlineSize(int fontSize) {
        return Math.max(fontSize / GameConfig.OUTLINE_SIZE_DIVISOR, GameConfig.MIN_OUTLINE_SIZE);
    }

    // Setters and Getters
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void setOutline(boolean enableOutline) {
        this.hasOutline = enableOutline;
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

    public void setCachedTextWidth(int cachedTextWidth) {
        this.cachedTextWidth = cachedTextWidth;
    }

    public void setCachedTextHeight(int cachedTextHeight) {
        this.cachedTextHeight = cachedTextHeight;
    }

    public Vector2d getPosition() {
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

    public int getOutlineSize() {
        return outlineSize;
    }

    public void setOutlineSize(int customOutlineSize) {
        this.outlineSize = Math.max(customOutlineSize, 0);
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

    public boolean isMetricsNeedUpdate() {
        return metricsNeedUpdate;
    }

    public void setMetricsNeedUpdate(boolean metricsNeedUpdate) {
        this.metricsNeedUpdate = metricsNeedUpdate;
    }

    private void invalidateMetrics() {
        metricsNeedUpdate = true;
    }

    private boolean isTextEqual(String text1, String text2) {
        if (text1 == null && text2 == null) return true;
        if (text1 == null || text2 == null) return false;
        return text1.equals(text2);
    }

    @Override
    public void update() {
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

}