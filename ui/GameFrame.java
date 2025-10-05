package ui;

import core.inputs.InputManager;
import core.inputs.Keyboard;
import core.inputs.Mouse;
import core.main.GameConstants;
import graphics.Stage;

import javax.swing.*;

public class GameFrame extends JFrame {

    private Stage stage;
    private Mouse mouse;
    private Keyboard keyboard;

    public GameFrame() {
        configFrame();
    }

    public void update() {
        stage.update();
    }

    public void draw() {
        stage.draw();
    }

    private void configFrame() {
        this.setSize(
                GameConstants.WINDOW_WIDTH,
                GameConstants.WINDOW_HEIGHT
        );
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Dragon's Rise");
    }

    public void init() {
        mouse = new Mouse();
        keyboard = new Keyboard();

        InputManager.getInstance().setKeyboard(keyboard);
        InputManager.getInstance().setMouse(mouse);

        stage = new Stage();
        stage.addMouseListener(mouse);
        stage.addMouseMotionListener(mouse);
        stage.addKeyListener(keyboard);
        this.add(stage);
        this.setVisible(true);
    }
}
