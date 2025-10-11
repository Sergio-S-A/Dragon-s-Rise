package modes.menus;

import core.main.GameConstants;
import core.physics.Vector2D;
import modes.Mode;
import modes.levels.TestLevel;
import ui.components.Button;
import ui.components.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Menu extends Mode {

    // Constants
    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 32;
    private static final int TITLE_Y_POSITION = 100;
    private static final int BUTTON_WIDTH_OFFSET = 100;
    private static final int FIRST_BUTTON_Y_OFFSET = 100;
    private static final int BUTTON_SPACING = 75;
    private static final Color FOREGROUND_COLOR = Color.WHITE;

    // Resource paths
    private static final String FONT_PATH = "Tiny5-Regular.ttf";

    // Button text constants
    private static final String PLAY_BUTTON_TEXT = "Jugar";
    private static final String OPTIONS_BUTTON_TEXT = "Opciones";
    private static final String EXIT_BUTTON_TEXT = "Salir";
    private static final String ABOUT_BUTTON_TEXT = "Acerca De";
    private static final String GAME_TITLE = "Dragon's Rise";

    // UI Components
    private Text titleText;
    private Button playButton;
    private Button optionsButton;
    private Button aboutButton;
    private Button exitButton;

    // Resources
    private Font titleFont;
    private Font buttonFont;
    private BufferedImage backgroundImage;

    private Map<String, Dimension> assets;

    public Menu() {
        loadResources();
        createUIComponents();
    }

    private void loadResources() {
        loadFonts();
        loadImages();
    }

    private void loadFonts() {
        titleFont = resourceManager.getFont(GameConstants.DEFAULT_FONT_PATH, GameConstants.DEFAULT_FONT_SIZE);
        titleFont = titleFont.deriveFont(TITLE_FONT_SIZE + 0f);
        buttonFont = resourceManager.getFont(GameConstants.DEFAULT_FONT_PATH, GameConstants.DEFAULT_FONT_SIZE);
        buttonFont = buttonFont.deriveFont(BUTTON_FONT_SIZE + 0f);
    }

    private void loadImages() {
        backgroundImage = resourceManager.getImage("camaraSubterranea.jpg", GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
    }

    private void createUIComponents() {
        createTitleText();
        createButtons();
    }

    private void createTitleText() {
        Vector2D titlePosition = createCenteredPosition();

        titleText = new Text.Builder()
                .setText(GAME_TITLE)
                .setPosition(titlePosition.x(), titlePosition.y())
                .setColor(FOREGROUND_COLOR)
                .setFont(titleFont)
                .setAlignment(Text.Alignment.CENTER)
                .setOutline(true)
                .setOutlineColor(Color.BLACK)
                .build();
    }

    private void createButtons() {
        playButton = createMenuButton(PLAY_BUTTON_TEXT, getPlayButtonYPosition());
        playButton.setActionButton(() -> {
            TestLevel testLevel = new TestLevel();
            Mode.changeNowMode(testLevel);
        });
        optionsButton = createMenuButton(OPTIONS_BUTTON_TEXT, getOptionsButtonYPosition());
        aboutButton = createMenuButton(ABOUT_BUTTON_TEXT, getAboutButtonYPosition());
        exitButton = createMenuButton(EXIT_BUTTON_TEXT, getExitButtonYPosition());
        exitButton.setActionButton(() -> System.exit(0));
    }

    private Button createMenuButton(String text, float yPosition) {
        Text buttonText = createButtonText(text);
        Vector2D buttonPosition = createButtonPosition(yPosition);

        return new Button.Builder()
                .setButtonPosition(buttonPosition.x(), buttonPosition.y())
                .setTextComponent(buttonText)
                .build();
    }

    private Text createButtonText(String text) {
        return new Text.Builder()
                .setText(text)
                .setColor(FOREGROUND_COLOR)
                .setFont(buttonFont)
                .setAlignment(Text.Alignment.CENTER)
                .setOutline(true)
                .setOutlineColor(Color.BLACK)
                .build();
    }

    private Vector2D createCenteredPosition() {
        float centeredX = GameConstants.WINDOW_WIDTH_MEDIUM;
        return new Vector2D(centeredX, TITLE_Y_POSITION);
    }

    private Vector2D createButtonPosition(float yPosition) {
        float buttonX = GameConstants.WINDOW_WIDTH_MEDIUM - BUTTON_WIDTH_OFFSET;
        return new Vector2D(buttonX, yPosition);
    }

    private float getPlayButtonYPosition() {
        return GameConstants.WINDOW_HEIGHT_MEDIUM - FIRST_BUTTON_Y_OFFSET;
    }

    private float getOptionsButtonYPosition() {
        return getPlayButtonYPosition() + BUTTON_SPACING;
    }

    private float getAboutButtonYPosition() {
        return getOptionsButtonYPosition() + BUTTON_SPACING;
    }

    private float getExitButtonYPosition() {
        return getAboutButtonYPosition() + BUTTON_SPACING;
    }

    @Override
    public void update() {
        updateUIComponents();
    }

    private void updateUIComponents() {
        titleText.update();
        playButton.update();
        optionsButton.update();
        aboutButton.update();
        exitButton.update();
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        renderBackground(graphics2d);
        renderUIComponents(graphics2d);
    }

    private void renderBackground(Graphics2D graphics2d) {
        graphics2d.drawImage(backgroundImage, 0, 0, null);
    }

    private void renderUIComponents(Graphics2D graphics2d) {
        titleText.draw(graphics2d);
        playButton.draw(graphics2d);
        optionsButton.draw(graphics2d);
        aboutButton.draw(graphics2d);
        exitButton.draw(graphics2d);
    }
}