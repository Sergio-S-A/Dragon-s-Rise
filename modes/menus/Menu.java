package modes.menus;

import core.main.GameConstants;
import core.math.Vector2D;
import modes.Mode;
import modes.levels.Level1;
import resources.ResourceManager;
import ui.components.Button;
import ui.components.Text;

import java.awt.*;

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
    private static final String GAME_TITLE = "Dragon's Rise";

    // UI Components
    private Text titleText;
    private Button playButton;
    private Button optionsButton;
    private Button exitButton;

    // Resources
    private Font titleFont;
    private Font buttonFont;


    public Menu(ResourceManager resourceManager) {
        super(resourceManager);
        initializeMenu();
    }

    private void initializeMenu() {
        loadResources();
        createUIComponents();
    }

    private void loadResources() {
        loadFonts();
    }

    private void loadFonts() {
        titleFont = resourceManager.loadFont(FONT_PATH, TITLE_FONT_SIZE);
        buttonFont = resourceManager.loadFont(FONT_PATH, BUTTON_FONT_SIZE);
    }

    private void createUIComponents() {
        createTitleText();
        createButtons();
    }

    private void createTitleText() {
        Vector2D titlePosition = createCenteredPosition(TITLE_Y_POSITION);

        titleText = new Text.Builder(resourceManager)
                .setText(GAME_TITLE)
                .setPosition(titlePosition)
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
            Level1 level1 = new Level1(resourceManager);
            Mode.changeNowMode(level1);
        });
        optionsButton = createMenuButton(OPTIONS_BUTTON_TEXT, getOptionsButtonYPosition());
        exitButton = createMenuButton(EXIT_BUTTON_TEXT, getExitButtonYPosition());
        exitButton.setActionButton(() -> System.exit(0));
    }

    private Button createMenuButton(String text, double yPosition) {
        Text buttonText = createButtonText(text);
        Vector2D buttonPosition = createButtonPosition(yPosition);

        return new Button.Builder(resourceManager)
                .setPosition(buttonPosition)
                .setTextComponent(buttonText)
                .build();
    }

    private Text createButtonText(String text) {
        return new Text.Builder(resourceManager)
                .setText(text)
                .setColor(FOREGROUND_COLOR)
                .setFont(buttonFont)
                .setAlignment(Text.Alignment.CENTER)
                .setOutline(true)
                .setOutlineColor(Color.BLACK)
                .build();
    }

    private Vector2D createCenteredPosition(double tileYPosition) {
        double centeredX = (double) GameConstants.WINDOW_WIDTH / 2;
        return new Vector2D(centeredX, tileYPosition);
    }

    private Vector2D createButtonPosition(double yPosition) {
        double buttonX = (double) GameConstants.WINDOW_WIDTH / 2 - BUTTON_WIDTH_OFFSET;
        return new Vector2D(buttonX, yPosition);
    }

    private double getPlayButtonYPosition() {
        return (double) GameConstants.WINDOW_HEIGHT / 2 - FIRST_BUTTON_Y_OFFSET;
    }

    private double getOptionsButtonYPosition() {
        return getPlayButtonYPosition() + BUTTON_SPACING;
    }

    private double getExitButtonYPosition() {
        return getOptionsButtonYPosition() + BUTTON_SPACING;
    }

    @Override
    public void update() {
        updateUIComponents();
    }

    private void updateUIComponents() {
        titleText.update();
        playButton.update();
        optionsButton.update();
        exitButton.update();
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        renderBackground(graphics2d);
        renderUIComponents(graphics2d);
    }

    private void renderBackground(Graphics2D graphics2d) {
        graphics2d.drawImage(
                resourceManager.loadImage("camaraSubterranea.jpg", GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT),
                0,
                0,
                null
        );
    }

    private void renderUIComponents(Graphics2D graphics2d) {
        titleText.draw(graphics2d);
        playButton.draw(graphics2d);
        optionsButton.draw(graphics2d);
        exitButton.draw(graphics2d);
    }
}