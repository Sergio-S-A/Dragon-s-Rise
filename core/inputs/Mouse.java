package core.inputs;

import core.math.Vector2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

    private final int numberOfButtons = 4;
    private int pointX;
    private int pointY;
    private boolean buttonsPressed[];
    private boolean buttonsClicked[];
    private boolean isDragging;

    public Mouse() {
        pointX = 0;
        pointY = 0;
        buttonsPressed = new boolean[numberOfButtons];
        buttonsClicked = new boolean[numberOfButtons];
        isDragging = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int buttonCode = e.getButton();
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            buttonsClicked[buttonCode] = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isDragging = true;
        int buttonCode = e.getButton();
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            buttonsPressed[buttonCode] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
        int buttonCode = e.getButton();
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            buttonsPressed[buttonCode] = false;
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

    public int getPointX() {
        return pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public Vector2D getPoint() {
        return new Vector2D(pointX, pointY);
    }

    public boolean isButtonPressed(int buttonCode) {
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            return buttonsPressed[buttonCode];
        }
        return false;
    }

    public boolean isButtonClicked(int buttonCode) {
        if (buttonCode >= 1 && buttonCode < numberOfButtons) {
            boolean buttonClicked = buttonsClicked[buttonCode];
            buttonsClicked[buttonCode] = false;
            return buttonClicked;
        }
        return false;
    }

    public boolean isDragging() {
        return isDragging;
    }
}
