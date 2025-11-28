package platform.desktop.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private final int numberOfKeys = 256;
    private final boolean[] keysStates;

    public KeyboardListener() {
        keysStates = new boolean[numberOfKeys];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code < numberOfKeys) {
            keysStates[code] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code < numberOfKeys) {
            keysStates[code] = false;
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < numberOfKeys) {
            return keysStates[keyCode];
        }
        return false;
    }
}
