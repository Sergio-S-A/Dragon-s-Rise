package features.gamestates.menu.infrastructure;

import core.config.GameConfig;
import core.physics.impl.NewtonianPhysics;
import features.entities.base.domain.input.InputController;
import features.entities.player.application.PlayerController;
import features.gamestates.base.domain.GameState;
import features.gamestates.base.domain.TypeState;
import features.gamestates.base.domain.ports.RendererState;
import features.gamestates.base.domain.service.GameStateManager;
import features.gamestates.level.infrastructure.CrystalCaveLevel;
import features.ui.domain.builder.ButtonBuilder;
import features.ui.domain.builder.LabelBuilder;
import features.ui.domain.model.Button;
import features.ui.domain.model.Label;
import platform.desktop.assets.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MainMenuState extends GameState implements RendererState<Graphics2D> {

    // --- Layout Constants ---
    private static final int TITLE_FONT_SIZE = 82;
    private static final int BUTTON_FONT_SIZE = 32;
    private static final int TITLE_Y_POS = 200;
    private static final int BUTTON_START_Y = GameConfig.WINDOW_HEIGHT_MEDIUM - 100;
    private static final int BUTTON_SPACING = 75;
    private static final int BUTTON_WIDTH_OFFSET = 100;
    private static final Color FOREGROUND_COLOR = Color.WHITE;

    // --- Text Constants ---
    private static final String GAME_TITLE = "Dragon's Rise";
    private static final String TEXT_PLAY = "Jugar";
    private static final String TEXT_OPTIONS = "Opciones";
    private static final String TEXT_ABOUT = "Acerca De";
    private static final String TEXT_EXIT = "Salir";

    // --- Dependencies ---
    private final AssetLoader assetLoader;
    private final InputController inputController;
    private final MenuRenderer renderer;

    // --- UI State ---
    private final List<Button> buttonList = new ArrayList<>();
    private final List<Label> labelList = new ArrayList<>();

    // --- Resources ---
    private Font titleFont;
    private Font buttonFont;
    private BufferedImage backgroundImage;

    public MainMenuState(AssetLoader assetLoader, InputController inputController) {
        super(TypeState.CLASSIC);
        this.assetLoader = assetLoader;
        this.inputController = inputController;

        loadResources();

        this.renderer = new MenuRenderer(
                backgroundImage,
                GameConfig.WINDOW_WIDTH,
                GameConfig.WINDOW_HEIGHT
        );

        createUIComponents();
    }

    // --- Lifecycle & Rendering ---

    @Override
    public void update() {
        buttonList.forEach(Button::update);
        labelList.forEach(Label::update);
    }

    @Override
    public void render(Graphics2D graphicsContext) {
        renderer.render(graphicsContext);
    }

    public void dispose() {
        renderer.dispose();
    }

    // --- Initialization Logic ---

    private void loadResources() {
        titleFont = assetLoader.getFont(GameConfig.DEFAULT_FONT_PATH, GameConfig.DEFAULT_FONT_SIZE)
                .deriveFont((float) TITLE_FONT_SIZE);
        buttonFont = assetLoader.getFont(GameConfig.DEFAULT_FONT_PATH, GameConfig.DEFAULT_FONT_SIZE)
                .deriveFont((float) BUTTON_FONT_SIZE);
        backgroundImage = assetLoader.getImage("Camaras_Subterraneas.jpg",
                GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
    }

    private void createUIComponents() {
        createTitleLabel();
        createMenuButtons();

        // Bind to renderer
        renderer.setLabelList(labelList);
        renderer.setButtonList(buttonList);
    }

    private void createTitleLabel() {
        Label titleLabel = new LabelBuilder()
                .setText(GAME_TITLE)
                .setPosition(GameConfig.WINDOW_WIDTH_MEDIUM, TITLE_Y_POS)
                .setColor(FOREGROUND_COLOR)
                .setFont(titleFont)
                .setAlignment(Label.Alignment.CENTER)
                .setOutline(true)
                .setOutlineColor(Color.BLACK)
                .build();
        labelList.add(titleLabel);
    }

    private void createMenuButtons() {
        int currentY = BUTTON_START_Y;

        createPlayButton(currentY);
        currentY += BUTTON_SPACING;

        createOptionsButton(currentY);
        currentY += BUTTON_SPACING;

        createAboutButton(currentY);
        currentY += BUTTON_SPACING;

        createExitButton(currentY);
    }

    // --- Button Factories ---

    private void createPlayButton(int yPos) {
        Button btn = createBaseButton(TEXT_PLAY, yPos);
        btn.setActionButton(() -> {
            CrystalCaveLevel testLevel = new CrystalCaveLevel(
                    assetLoader,
                    new PlayerController(new NewtonianPhysics(), inputController),
                    TypeState.ISOMETRIC
            );
            GameStateManager.getInstance().setMode(testLevel);
        });
        buttonList.add(btn);
    }

    private void createOptionsButton(int yPos) {
        Button btn = createBaseButton(TEXT_OPTIONS, yPos);
        btn.setActionButton(() -> {

        });
        buttonList.add(btn);
    }

    private void createAboutButton(int yPos) {
        Button btn = createBaseButton(TEXT_ABOUT, yPos);
        btn.setActionButton(() -> {

        });
        buttonList.add(btn);
    }

    private void createExitButton(int yPos) {
        Button btn = createBaseButton(TEXT_EXIT, yPos);
        btn.setActionButton(() -> System.exit(0));
        buttonList.add(btn);
    }

    private Button createBaseButton(String text, float yPosition) {
        Label buttonLabel = new LabelBuilder()
                .setText(text)
                .setColor(FOREGROUND_COLOR)
                .setFont(buttonFont)
                .setAlignment(Label.Alignment.CENTER)
                .setOutline(true)
                .setOutlineColor(Color.BLACK)
                .build();

        float buttonX = GameConfig.WINDOW_WIDTH_MEDIUM - BUTTON_WIDTH_OFFSET;

        return new ButtonBuilder()
                .setButtonPosition(buttonX, yPosition)
                .setTextComponent(buttonLabel)
                .build();
    }

    // --- Getters ---

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public int getWidth() {
        return GameConfig.WINDOW_WIDTH;
    }

    public int getHeight() {
        return GameConfig.WINDOW_HEIGHT;
    }
}