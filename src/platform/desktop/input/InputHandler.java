package platform.desktop.input;

public class InputHandler {
    private static InputHandler instance;
    private KeyboardListener keyboardListener;
    private MouseListener mouseListener;

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    public KeyboardListener getKeyboard() {
        return keyboardListener;
    }

    public void setKeyboard(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    public MouseListener getMouse() {
        return mouseListener;
    }

    public void setMouse(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }
}
