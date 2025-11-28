package features.ui.domain.builder;

import core.config.GameConfig;
import core.math.Vector2d;
import core.memory.Vector2dPool;
import features.ui.domain.ActionButton;
import features.ui.domain.model.Button;
import features.ui.domain.model.Label;

public class ButtonBuilder {

    // Required components
    private Label labelComponent;
    private Vector2d buttonPosition;
    private int width;
    private int height;

    // Optional components with defaults
    private ActionButton actionButton;

    // Constructor
    public ButtonBuilder() {
        initializeDefaultsValues();
    }

    private void initializeDefaultsValues() {
        this.buttonPosition = Vector2dPool.acquire();
        this.actionButton = createNoOpAction();
        this.width = GameConfig.DEFAULT_BUTTON_WIDTH;
        this.height = GameConfig.DEFAULT_BUTTON_HEIGHT;
    }

    private ActionButton createNoOpAction() {
        return () -> {
        };
    }

    public ButtonBuilder setTextComponent(Label labelComponent) {
        this.labelComponent = labelComponent;
        return this;
    }

    public ButtonBuilder setButtonPosition(float x, float y) {
        buttonPosition.set(x, y);
        return this;
    }

    public ButtonBuilder setButtonSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Button size must be positive");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    public Button build() {
        validateBuilderState();
        return new Button(this);
    }

    private void validateBuilderState() {
        validateRequiredComponents();
    }

    private void validateRequiredComponents() {
        if (labelComponent == null) {
            throw new IllegalStateException("Text component must be set before building button");
        }
        if (buttonPosition == null) {
            throw new IllegalStateException("Position must be set before building button");
        }
    }

    public Label getLabelComponent() {
        return labelComponent;
    }

    public Vector2d getButtonPosition() {
        return buttonPosition;
    }

    public ActionButton getActionButton() {
        return actionButton;
    }

    public ButtonBuilder setActionButton(ActionButton actionButton) {
        this.actionButton = actionButton != null ? actionButton : createNoOpAction();
        return this;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
