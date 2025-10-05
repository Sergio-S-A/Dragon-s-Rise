package ui.components;

import core.inputs.InputManager;
import core.inputs.Mouse;
import core.main.Core;
import core.main.GameConstants;
import core.physics.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends Component {

    // Constants
    private static final int LEFT_MOUSE_BUTTON = 1;
    // Dependencies
    private final Text textComponent;
    // Visual resources
    private final BufferedImage pressedImage;
    private final BufferedImage unpressedImage;
    // Position and dimensions
    private final Vector2D buttonPosition;
    private final int width;
    private final int height;
    private final Vector2D mousePosition;
    private final Mouse mouse;
    private ActionButton actionButton;
    // State
    private boolean isPressed;
    private boolean isHovered;

    // Constructor
    private Button(Builder builder) {
        this.textComponent = builder.textComponent;
        this.buttonPosition = builder.buttonPosition;
        this.pressedImage = builder.pressedImage;
        this.unpressedImage = builder.unpressedImage;
        this.actionButton = builder.actionButton;
        this.width = pressedImage.getWidth();
        this.height = pressedImage.getHeight();
        this.mouse = InputManager.getInstance().getMouse();
        this.mousePosition = new Vector2D(mouse.getPointX(), mouse.getPointY());
        centerTextInButton();
    }

    @Override
    public void update() {
        updateMouseInteraction();
    }

    private void updateMouseInteraction() {
        mousePosition.set(mouse.getPointX(), mouse.getPointY());
        if (isMouseInsideButton()) {
            isHovered = true;
            isPressed = mouse.isButtonPressed(LEFT_MOUSE_BUTTON);
            if (mouse.isButtonClicked(LEFT_MOUSE_BUTTON)) {
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
        return isPointInBounds(mousePosition, buttonPosition, width, height);
    }

    private boolean isPointInBounds(Vector2D point, Vector2D bounds, int width, int height) {
        return point.x() >= bounds.x() &&
                point.x() <= bounds.x() + width &&
                point.y() >= bounds.y() &&
                point.y() <= bounds.y() + height;
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        renderButtonBackground(graphics2d);
        renderButtonText(graphics2d);
    }

    private void renderButtonBackground(Graphics2D graphics2d) {
        BufferedImage currentImage = shouldShowPressedState() ? pressedImage : unpressedImage;
        graphics2d.drawImage(currentImage, (int) buttonPosition.x(), (int) buttonPosition.y(), null);
    }

    private void renderButtonText(Graphics2D graphics2d) {
        textComponent.draw(graphics2d);
    }

    private boolean shouldShowPressedState() {
        return isHovered && isPressed;
    }

    private void centerTextInButton() {
        Vector2D centeredTextPosition = calculateCenteredTextPosition();
        textComponent.setPosition(centeredTextPosition.x(), centeredTextPosition.y());
        textComponent.setAlignment(Text.Alignment.CENTER);
    }

    private Vector2D calculateCenteredTextPosition() {
        double centeredX = buttonPosition.x() + (double) width * GameConstants.BUTTON_TEXT_HORIZONTAL_CENTER_RATIO;
        double centeredY = buttonPosition.y() + (double) height * GameConstants.BUTTON_TEXT_VERTICAL_CENTER_RATIO
                + (double) textComponent.getHeight() / GameConstants.BUTTON_TEXT_BASELINE_ADJUSTMENT;
        return new Vector2D(centeredX, centeredY);
    }

    // Setters and Getters
    public Vector2D getButtonPosition() {
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

    // Builder
    public static class Builder {

        // Required components
        private Text textComponent;
        private Vector2D buttonPosition;

        // Optional components with defaults
        private BufferedImage pressedImage;
        private BufferedImage unpressedImage;
        private ActionButton actionButton;

        // Constructor
        public Builder() {
            initializeDefaultsValues();
        }

        private void initializeDefaultsValues() {
            this.buttonPosition = new Vector2D(0, 0);
            this.actionButton = createNoOpAction();
            this.pressedImage = loadDefaultPressedImage();
            this.unpressedImage = loadDefaultUnpressedImage();
        }

        private ActionButton createNoOpAction() {
            return () -> {
            };
        }

        public Builder setTextComponent(Text textComponent) {
            this.textComponent = textComponent;
            return this;
        }

        public Builder setButtonPosition(double x, double y) {
            buttonPosition.set(x, y);
            return this;
        }


        public Builder setPressedImage(BufferedImage pressedImage) {
            this.pressedImage = pressedImage;
            return this;
        }

        public Builder setUnpressedImage(BufferedImage unpressedImage) {
            this.unpressedImage = unpressedImage;
            return this;
        }

        public Builder setButtonImages(BufferedImage unpressedImage, BufferedImage pressedImage) {
            this.unpressedImage = unpressedImage;
            this.pressedImage = pressedImage;
            return this;
        }

        public Builder setActionButton(ActionButton actionButton) {
            this.actionButton = actionButton != null ? actionButton : createNoOpAction();
            return this;
        }

        public Button build() {
            validateBuilderState();
            ensureImageConsistency();
            return new Button(this);
        }

        private void validateBuilderState() {
            validateRequiredComponents();
            validateImages();
        }

        private void validateRequiredComponents() {
            if (textComponent == null) {
                throw new IllegalStateException("Text component must be set before building button");
            }
            if (buttonPosition == null) {
                throw new IllegalStateException("Position must be set before building button");
            }
        }

        private void validateImages() {
            if (pressedImage == null) {
                throw new IllegalStateException("Pressed image cannot be null");
            }
            if (unpressedImage == null) {
                throw new IllegalStateException("Unpressed image cannot be null");
            }
        }

        private void ensureImageConsistency() {
            if (pressedImage.getWidth() != unpressedImage.getWidth() ||
                    pressedImage.getHeight() != unpressedImage.getHeight()) {

                throw new IllegalStateException(
                        String.format("Button images must have the same dimensions. " +
                                        "Pressed: %dx%d, Unpressed: %dx%d",
                                pressedImage.getWidth(), pressedImage.getHeight(),
                                unpressedImage.getWidth(), unpressedImage.getHeight())
                );
            }
        }

        private BufferedImage loadDefaultPressedImage() {
            return Core.getResourceManager().loadImage(
                    GameConstants.DEFAULT_PRESSED_IMAGE_PATH,
                    GameConstants.DEFAULT_BUTTON_WIDTH,
                    GameConstants.DEFAULT_BUTTON_HEIGHT
            );
        }

        private BufferedImage loadDefaultUnpressedImage() {
            return Core.getResourceManager().loadImage(
                    GameConstants.DEFAULT_UNPRESSED_IMAGE_PATH,
                    GameConstants.DEFAULT_BUTTON_WIDTH,
                    GameConstants.DEFAULT_BUTTON_HEIGHT
            );
        }
    }
}