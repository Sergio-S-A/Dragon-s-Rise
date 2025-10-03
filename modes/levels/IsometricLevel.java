package modes.levels;

import entities.entity.Entity;
import modes.Mode;
import resources.ResourceManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IsometricLevel extends Mode {

    private Entity player;
    private List<Entity> enemies = new ArrayList<>();
    private List<Entity> items = new ArrayList<>();
    private List<Entity> npcs = new ArrayList<>();
    private List<Entity> obstacles = new ArrayList<>();
    private IsometricBackGround background;

    public IsometricLevel(ResourceManager resourceManager) {
        super(resourceManager);
        background = new IsometricBackGround(resourceManager);
    }

    @Override
    public void update() {
        updateEntities();
        updatePhysics();
        if (background != null) {
            background.update(player);
        }
    }

    public void updateEntities() {
        if (player != null) {
            player.update();
        }
        for (Entity enemy : enemies) {
            enemy.update();
        }
        for (Entity item : items) {
            item.update();
        }
        for (Entity npc : npcs) {
            npc.update();
        }
        for (Entity obstacle : obstacles) {
            obstacle.update();
        }
    }

    public void updatePhysics() {
        if (player != null) {
            player.updatePhysics();
        }
        for (Entity enemy : enemies) {
            enemy.updatePhysics();
        }
        for (Entity item : items) {
            item.updatePhysics();
        }
        for (Entity npc : npcs) {
            npc.updatePhysics();
        }
        for (Entity obstacle : obstacles) {
            obstacle.updatePhysics();
        }
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        if (background != null) {
            drawBackground(graphics2d);
        }
        drawEntities(graphics2d);
    }

    public void drawBackground(Graphics2D graphics2d) {
        background.draw(graphics2d);
    }

    public void drawEntities(Graphics2D graphics2d) {
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
