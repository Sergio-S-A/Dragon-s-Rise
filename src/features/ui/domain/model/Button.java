package features.ui.domain.model;

import core.config.GameConfig;
import core.math.Vector2d;
import core.memory.Vector2dPool;
import features.ui.domain.ActionButton;
import features.ui.domain.builder.ButtonBuilder;
import platform.desktop.input.InputHandler;
import platform.desktop.input.MouseListener;

public class Button extends Component {

    // Constants
    private static final int LEFT_MOUSE_BUTTON = 1;
    // Dependencies
    private final Label labelComponent;
    // Position and dimensions
    private final Vector2d buttonPosition;
    private final int width;
    private final int height;
    private final Vector2d mousePosition;
    private final MouseListener mouseListener;
    private ActionButton actionButton;
    // State
    private boolean isPressed;
    private boolean isHovered;

    // Constructor
    public Button(ButtonBuilder builder) {
        this.labelComponent = builder.getLabelComponent();
        this.buttonPosition = builder.getButtonPosition();
        this.actionButton = builder.getActionButton();
        this.width = builder.getWidth();
        this.height = builder.getHeight();
        this.mouseListener = InputHandler.getInstance().getMouse();
        this.mousePosition = Vector2dPool.acquire();
        centerTextInButton();
    }

    @Override
    public void update() {
        float newX = mouseListener.getPointX();
        float newY = mouseListener.getPointY();
        if (mousePosition.getX() != newX || mousePosition.getY() != newY) {
            mousePosition.set(newX, newY);
        }
        if (isMouseInsideButton()) {
            isHovered = true;
            isPressed = mouseListener.isButtonPressed(LEFT_MOUSE_BUTTON);
            if (mouseListener.isButtonClicked(LEFT_MOUSE_BUTTON)) {
                executeButtonAction();
            }
        } else {
            resetButtonState();
        }
    }


    private void resetButtonState() {
        isHovered = false;
        isPressed = false;
    }

    private void executeButtonAction() {
        if (actionButton != null) {
            actionButton.action();
        }
    }

    private boolean isMouseInsideButton() {
        return mousePosition.getX() >= buttonPosition.getX() &&
                mousePosition.getX() <= buttonPosition.getX() + width &&
                mousePosition.getY() >= buttonPosition.getY() &&
                mousePosition.getY() <= buttonPosition.getY() + height;
    }

    public boolean shouldShowPressedState() {
        return isHovered && isPressed;
    }

    private void centerTextInButton() {
        Vector2d centeredTextPosition = Vector2dPool.acquire();
        calculateCenteredTextPosition(centeredTextPosition);
        labelComponent.setPosition(centeredTextPosition.getX(), centeredTextPosition.getY());
        labelComponent.setAlignment(Label.Alignment.CENTER);
    }

    private void calculateCenteredTextPosition(Vector2d result) {
        float centeredX = buttonPosition.getX() + width * GameConfig.BUTTON_TEXT_HORIZONTAL_CENTER_RATIO;
        float centeredY = buttonPosition.getY() + height * GameConfig.BUTTON_TEXT_VERTICAL_CENTER_RATIO
                + labelComponent.getHeight() / GameConfig.BUTTON_TEXT_BASELINE_ADJUSTMENT;
        result.set(centeredX, centeredY);
    }

    // Setters and Getters
    public Vector2d getButtonPosition() {
        return buttonPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setActionButton(ActionButton actionButton) {
        this.actionButton = actionButton;
    }

    public Label getLabelComponent() {
        return labelComponent;
    }
}