package ui.components;

import java.awt.*;

public abstract class Component {

    public Component() {
    }

    public abstract void update();

    public abstract void draw(Graphics2D graphics2d);
}
