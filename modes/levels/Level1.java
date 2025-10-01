package modes.levels;

import core.inputs.InputManager;
import core.main.GameConstants;
import core.math.PhysicsClasicToPlayer;
import core.math.Vector2D;
import entities.mobs.Wolf;
import graphics.AnimationManager;
import modes.Mode;
import modes.menus.LevelData;
import resources.ResourceManager;

import java.awt.*;

public class Level1 extends Mode {

    private final int DIMENSION_Y;
    private final int DIMENSION_X;
    private final int TILE_SIZE;
    private final int WOLF_SPRITE_SIZE;

    private int[][] levelData;
    private int offset_X;
    private int offset_Y;
    private Wolf wolf;


    public Level1(ResourceManager resourceManager) {
        super(resourceManager);
        levelData = LevelData.level1;
        DIMENSION_Y = levelData.length;
        DIMENSION_X = levelData[0].length;
        TILE_SIZE = 64;
        WOLF_SPRITE_SIZE = 128;


        wolf = new Wolf.WolfBuilder(resourceManager)
                .setTILE_SIZE(WOLF_SPRITE_SIZE)
                .setAnimationManager(new AnimationManager())
                .setKeyboard(InputManager.getInstance().getKeyboard())
                .setPhysicsUpdater(new PhysicsClasicToPlayer())
                .setMass(10)
                .setFrictionCoefficient(0.1)
                .setMaxSpeed(0.01)
                .setMaxAcceleration(0.01)
                .setForceScale(0.01)
                .build();
    }


    @Override
    public void update() {
        wolf.update();
        Vector2D wolfWorldPos = wolf.getPosition();

        double wolfScreenXWithoutOffset = changeIsometricViewX(wolfWorldPos);
        double wolfScreenYWithoutOffset = changeIsometricViewY(wolfWorldPos);

        calculateOffset(wolfScreenXWithoutOffset, wolfScreenYWithoutOffset);
    }

    private void calculateOffset(double wolfScreenXWithoutOffset, double wolfScreenYWithoutOffset) {
        offset_X = (int) (GameConstants.WINDOW_WIDTH / 2 - wolfScreenXWithoutOffset);
        offset_Y = (int) (GameConstants.WINDOW_HEIGHT / 2 - wolfScreenYWithoutOffset - TILE_SIZE / 2);
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        renderBackground(graphics2d);
        wolf.draw(graphics2d);
    }

    private void renderBackground(Graphics2D graphics2d) {
        int screenX;
        int screenY;

        for (int y = 0; y < DIMENSION_Y; y++) {
            for (int x = 0; x < DIMENSION_X; x++) {

                screenX = (int) ((x - y) * (TILE_SIZE / 2.0) + offset_X);
                screenY = (int) ((x + y) * (TILE_SIZE / 4.0) + offset_Y);

                if (isVisible(screenX, screenY)) {
                    String tilePath = createTilePath(levelData[y][x]);
                    graphics2d.drawImage(
                            resourceManager.loadImage(tilePath, TILE_SIZE, TILE_SIZE),
                            screenX,
                            screenY,
                            null);
                }
            }
        }
    }

    private boolean isVisible(int screenX, int screenY) {
        return screenX > -TILE_SIZE && screenX < GameConstants.WINDOW_WIDTH &&
                screenY > -TILE_SIZE && screenY < GameConstants.WINDOW_HEIGHT;
    }

    private String createTilePath(int tileType) {
        return String.format("isometric_tileset/tile_%03d.png", tileType);
    }

    private double changeIsometricViewX(Vector2D NaturalPos) {
        return (NaturalPos.x() - NaturalPos.y()) * (TILE_SIZE / 2.0);
    }

    private double changeIsometricViewY(Vector2D NaturalPos) {
        return (NaturalPos.x() + NaturalPos.y()) * (TILE_SIZE / 4.0);
    }
}