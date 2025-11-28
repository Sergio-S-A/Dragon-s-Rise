package platform.desktop.graphics;

import core.config.GameConfig;
import features.gamestates.base.domain.service.GameStateManager;
import platform.desktop.assets.AssetCache;
import platform.desktop.assets.AssetPreloader;
import platform.desktop.input.InputHandler;
import platform.desktop.input.KeyboardListener;
import platform.desktop.input.MouseListener;

import javax.swing.*;

public class DesktopWindow extends JFrame {

    // --- Constants ---
    private static final String WINDOW_TITLE = "Dragon's Rise";

    // --- Components ---
    private RenderCanvas renderCanvas;

    // --- Input Handlers ---
    private MouseListener mouseListener;
    private KeyboardListener keyboardListener;

    public DesktopWindow() {
        configureFrameSettings();
    }

    // --- Initialization Lifecycle ---

    public void init() {
        setupInputs();
        loadResources();
        mountCanvas();
    }

    // --- Game Loop Delegation ---

    public void update() {
        renderCanvas.update();
    }

    public void draw() {
        renderCanvas.draw();
    }

    // --- Internal Configuration ---

    private void configureFrameSettings() {
        this.setTitle(WINDOW_TITLE);
        this.setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    private void setupInputs() {
        this.mouseListener = new MouseListener();
        this.keyboardListener = new KeyboardListener();

        InputHandler.getInstance().setKeyboard(keyboardListener);
        InputHandler.getInstance().setMouse(mouseListener);
    }

    private void loadResources() {
        AssetPreloader assetPreloader = new AssetPreloader();
        assetPreloader.loadAssets();
    }

    private void mountCanvas() {
        this.renderCanvas = new RenderCanvas(
                AssetCache.getInstance(),
                InputHandler.getInstance(),
                GameStateManager.getInstance()
        );

        // Attach Listeners
        this.renderCanvas.addMouseListener(mouseListener);
        this.renderCanvas.addMouseMotionListener(mouseListener);
        this.renderCanvas.addMouseWheelListener(mouseListener);
        this.renderCanvas.addKeyListener(keyboardListener);

        // Finalize UI
        this.add(renderCanvas);
        this.setVisible(true);
        this.renderCanvas.requestFocus();
    }
}