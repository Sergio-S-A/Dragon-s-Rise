package modes.levels;

import entities.entity.Entity;
import modes.Mode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IsometricLevel extends Mode {

    private final List<Entity> enemies = new ArrayList<>();
    private final List<Entity> items = new ArrayList<>();
    private final List<Entity> npcs = new ArrayList<>();
    private final List<Entity> obstacles = new ArrayList<>();
    private final IsometricBackGround background;
    private Entity player;

    public IsometricLevel() {
        super();
        background = new IsometricBackGround();
    }

    @Override
    public void update() {
        updateEntities();
        if (background != null) {
            background.update(player);
        }
    }

    private void updateEntities() {
        if (player != null) {
            player.update();
            player.updatePhysics();
        }
        updateEntityList(enemies);
        updateEntityList(items);
        updateEntityList(npcs);
        updateEntityList(obstacles);
    }

    private void updateEntityList(List<Entity> entities) {
        for (Entity entity : entities) {
            entity.update();
            entity.updatePhysics();
        }
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        if (background != null) {
            drawBackground(graphics2d);
        }
        drawEntities(graphics2d);
    }

    private void drawBackground(Graphics2D graphics2d) {
        background.draw(graphics2d);
    }

    private void drawEntities(Graphics2D graphics2d) {
        if (player != null) {
            player.draw(graphics2d);
        }
        for (Entity enemy : enemies) {
            enemy.draw(graphics2d);
        }
        for (Entity item : items) {
            item.draw(graphics2d);
        }
        for (Entity npc : npcs) {
            npc.draw(graphics2d);
        }
        for (Entity obstacle : obstacles) {
            obstacle.draw(graphics2d);
        }
    }

    public void addPlayer(Entity player) {
        this.player = player;
    }

    public void addEnemy(Entity enemy) {
        enemies.add(enemy);
    }

    public void addItem(Entity item) {
        items.add(item);
    }

    public void addNpc(Entity npc) {
        npcs.add(npc);
    }

    public void addObstacle(Entity obstacle) {
        obstacles.add(obstacle);
    }

    public void removePlayer() {
        player = null;
    }

    public void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }

    public void removeItem(Entity item) {
        items.remove(item);
    }

    public void removeNpc(Entity npc) {
        npcs.remove(npc);
    }

    public void removeObstacle(Entity obstacle) {
        obstacles.remove(obstacle);
    }

    public IsometricBackGround getBackground() {
        return background;
    }
}
