package ui.components;

import core.inputs.InputManager;
import core.physics.Vector2D;
import resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends Component {

    // Constants
    private static final int LEFT_MOUSE_BUTTON = 1;
    private static final int TEXT_VERTICAL_ADJUSTMENT = 4;
    private static final int DEFAULT_BUTTON_WIDTH = 200;
    private static final int DEFAULT_BUTTON_HEIGHT = 50;

    // Resource paths
    private static final String DEFAULT_PRESSED_IMAGE_PATH = "keney_ui_pack_rpg/buttonLong_blue_pressed.png";
    private static final String DEFAULT_UNPRESSED_IMAGE_PATH = "keney_ui_pack_rpg/buttonLong_blue.png";

    // Dependencies
    private final Text textComponent;

    // Visual resources
    private final BufferedImage pressedImage;
    private final BufferedImage unpressedImage;

    // Position and dimensions
    private final Vector2D position;
    private final int width;
    private final int height;
    private ActionButton actionButton;

    // State
    private boolean isPressed;
    private boolean isHovered;

    // Constructor
    private Button(Builder builder) {
        this.textComponent = builder.textComponent;
        this.position = builder.position;
        this.pressedImage = builder.pressedImage;
        this.unpressedImage = builder.unpressedImage;
        this.actionButton = builder.actionButton;
        this.width = pressedImage.getWidth();
        this.height = pressedImage.getHeight();
        centerTextInButton();
    }

    @Override
    public void update() {
        updateMouseInteraction();
    }

    private void updateMouseInteraction() {
        var mouse = InputManager.getInstance().getMouse();
        Vector2D mousePosition = new Vector2D(mouse.getPointX(), mouse.getPointY());

        if (isMouseInsideButton(mousePosition)) {
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

    private boolean isMouseInsideButton(Vector2D mousePosition) {
        return isPointInBounds(mousePosition, position, width, height);
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
        graphics2d.drawImage(currentImage, (int) position.x(), (int) position.y(), null);
    }

    private void renderButtonText(Graphics2D graphics2d) {
        textComponent.draw(graphics2d);
    }

    private boolean shouldShowPressedState() {
        return isHovered && isPressed;
    }

    private void centerTextInButton() {
        Vector2D centeredTextPosition = calculateCenteredTextPosition();
        textComponent.setPosition(centeredTextPosition);
        textComponent.setAlignment(Text.Alignment.CENTER);
    }

    private Vector2D calculateCenteredTextPosition() {
        double centeredX = position.x() + (double) width / 2;
        double centeredY = position.y() + (double) height / 2 + (double) textComponent.getHeight() / TEXT_VERTICAL_ADJUSTMENT;
        return new Vector2D(centeredX, centeredY);
    }

    // Setters and Getters
    public Vector2D getPosition() {
        return new Vector2D(position.x(), position.y());
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

        // Required dependencies
        private final ResourceManager resourceManager;

        // Required components
        private Text textComponent;
        private Vector2D position;

        // Optional components with defaults
        private BufferedImage pressedImage;
        private BufferedImage unpressedImage;
        private ActionButton actionButton;

        // Constructor
        public Builder(ResourceManager resourceManager) {
            this.resourceManager = validateResourceManager(resourceManager);
            initializeDefaultsValues();
        }

        private ResourceManager validateResourceManager(ResourceManager resourceManager) {
            if (resourceManager == null) {
                throw new IllegalArgumentException("ResourceManager cannot be null");
            }
            return resourceManager;
        }

        private void initializeDefaultsValues() {
            this.position = new Vector2D(0, 0);
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

        public Builder setPosition(Vector2D position) {
            this.position = position;
            return this;
        }

        public Builder setPosition(int x, int y) {
            return setPosition(new Vector2D(x, y));
        }

        public Builder setPosition(double x, double y) {
            return setPosition(new Vector2D(x, y));
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
            if (position == null) {
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
            return resourceManager.loadImage(
                    DEFAULT_PRESSED_IMAGE_PATH,
                    DEFAULT_BUTTON_WIDTH,
                    DEFAULT_BUTTON_HEIGHT
            );
        }

        private BufferedImage loadDefaultUnpressedImage() {
            return resourceManager.loadImage(
                    DEFAULT_UNPRESSED_IMAGE_PATH,
                    DEFAULT_BUTTON_WIDTH,
                    DEFAULT_BUTTON_HEIGHT
            );
        }
    }
}