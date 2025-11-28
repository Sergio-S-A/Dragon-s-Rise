package platform.desktop.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener, MouseWheelListener {

    private final int numberOfButtons = 4;
    private final byte[] buttonsPressed = new byte[numberOfButtons];
    private final byte[] buttonsClicked = new byte[numberOfButtons];
    private float pointX;
    private float pointY;
    private boolean isDragging;
    private float scrollNotches;

    public MouseListener() {
        pointX = 0;
        pointY = 0;
        isDragging = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int buttonCode = e.getButton();
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            buttonsClicked[buttonCode] = 1;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isDragging = true;
        int buttonCode = e.getButton();
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            buttonsPressed[buttonCode] = 1;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
        int buttonCode = e.getButton();
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            buttonsPressed[buttonCode] = 0;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        pointX = e.getX();
        pointY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        pointX = e.getX();
        pointY = e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public float getPointX() {
        return pointX;
    }

    public float getPointY() {
        return pointY;
    }


    public boolean isButtonPressed(int buttonCode) {
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            return buttonsPressed[buttonCode] != 0;
        }
        return false;
    }

    public boolean isButtonClicked(int buttonCode) {
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            byte buttonClicked = buttonsClicked[buttonCode];
            buttonsClicked[buttonCode] = 0;
            return buttonClicked != 0;
        }
        return false;
    }

    public boolean isDragging() {
        return isDragging;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollNotches = e.getWheelRotation();
    }

    public float getScrollNotches() {
        return scrollNotches;
    }

}
