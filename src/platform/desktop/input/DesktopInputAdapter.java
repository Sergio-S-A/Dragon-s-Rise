package platform.desktop.input;

import features.entities.base.domain.input.InputController;

import java.awt.event.KeyEvent;

public class DesktopInputAdapter implements InputController {

    private final KeyboardListener keyboardListener;

    public DesktopInputAdapter(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    @Override
    public boolean isMoveNorthRequested() {
        return keyboardListener.isKeyPressed(KeyEvent.VK_W) || keyboardListener.isKeyPressed(KeyEvent.VK_UP);
    }

    @Override
    public boolean isMoveSouthRequested() {
        return keyboardListener.isKeyPressed(KeyEvent.VK_S) || keyboardListener.isKeyPressed(KeyEvent.VK_DOWN);
    }

    @Override
    public boolean isMoveEastRequested() {
        return keyboardListener.isKeyPressed(KeyEvent.VK_D) || keyboardListener.isKeyPressed(KeyEvent.VK_RIGHT);
    }

    @Override
    public boolean isMoveWestRequested() {
        return keyboardListener.isKeyPressed(KeyEvent.VK_A) || keyboardListener.isKeyPressed(KeyEvent.VK_LEFT);
    }

    @Override
    public boolean isAttackRequested() {
        return keyboardListener.isKeyPressed(KeyEvent.VK_SPACE);
    }
}
