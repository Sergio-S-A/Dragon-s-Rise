package platform.desktop.graphics;

import core.config.GameConfig;
import features.gamestates.base.domain.GameState;
import features.gamestates.base.domain.ports.RendererState;
import features.gamestates.base.domain.service.GameStateManager;
import features.gamestates.menu.infrastructure.MainMenuState;
import platform.desktop.assets.AssetLoader;
import platform.desktop.input.DesktopInputAdapter;
import platform.desktop.input.InputHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class RenderCanvas extends Canvas {

    // --- Constants ---
    private static final int SCREEN_WIDTH = GameConfig.WINDOW_WIDTH;
    private static final int SCREEN_HEIGHT = GameConfig.WINDOW_HEIGHT;
    private final GameStateManager gameStateManager;
    // --- Render State ---
    private BufferStrategy bufferStrategy;

    public RenderCanvas(AssetLoader assetLoader, InputHandler inputHandler, GameStateManager gameStateManager) {
        this.setFocusable(true);
        this.requestFocus();
        this.gameStateManager = gameStateManager;

        initializeGame(assetLoader, inputHandler);
    }

    // --- Game Loop ---

    public void update() {
        GameState currentGameState = gameStateManager.getMode();
        if (currentGameState != null) {
            currentGameState.update();
        }
    }

    public void draw() {
        if (!ensureBufferStrategy()) return;

        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        try {
            clearScreen(g2d);
            renderCurrentMode(g2d);
        } finally {
            g2d.dispose();
        }

        bufferStrategy.show();
    }

    // --- Internal Initialization ---

    private void initializeGame(AssetLoader assetLoader, InputHandler inputHandler) {
        MainMenuState menuMode = new MainMenuState(
                assetLoader,
                new DesktopInputAdapter(inputHandler.getKeyboard())
        );
        gameStateManager.setMode(menuMode);
    }

    // --- Rendering Helpers ---

    private boolean ensureBufferStrategy() {
        if (bufferStrategy == null) {
            createBufferStrategy(GameConfig.NUM_OF_BUFFERS);
            bufferStrategy = getBufferStrategy();
            return false; // Strategy created this frame, skip draw to sync
        }
        return true;
    }

    private void clearScreen(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @SuppressWarnings("unchecked")
    private void renderCurrentMode(Graphics2D g2d) {
        GameState currentGameState = GameStateManager.getInstance().getMode();

        if (currentGameState instanceof RendererState) {
            RendererState<Graphics2D> renderer = (RendererState<Graphics2D>) currentGameState;
            renderer.render(g2d);
        }
    }
}