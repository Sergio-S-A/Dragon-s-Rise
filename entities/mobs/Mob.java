package entities.mobs;

import core.main.Core;
import core.main.GameConstants;
import core.physics.Vector2D;
import entities.entity.Entity;
import graphics.AnimationManager;
import resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Mob extends Entity {

    protected final AnimationManager animationManager;
    private final ResourceManager resourceManager;
    private final String PATH_ATTACK_FRAMES;
    private final String PATH_RUN_FRAMES;
    private final String PATH_IDLE_FRAMES;
    protected boolean isAttacking;
    protected boolean isMoving;
    private int tileSize;

    public Mob(MobBuilder mobBuilder) {
        super(mobBuilder);
        this.animationManager = new AnimationManager();
        this.resourceManager = Core.getResourceManager();
        this.tileSize = mobBuilder.tileSize;
        this.PATH_IDLE_FRAMES = mobBuilder.PATH_IDLE_FRAMES;
        this.PATH_RUN_FRAMES = mobBuilder.PATH_RUN_FRAMES;
        this.PATH_ATTACK_FRAMES = mobBuilder.PATH_ATTACK_FRAMES;
        this.isAttacking = false;
        this.isMoving = false;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D graphics2d) {
    }

    public abstract void loadAnimations();

    protected BufferedImage[][] loadAnimation(String path, int width, int height) {
        BufferedImage image = resourceManager.loadImage(path, width, height);
        BufferedImage[][] animation = new BufferedImage[height / tileSize][width / tileSize];
        for (int i = 0; i < animation.length; i++) {
            for (int j = 0; j < animation[i].length; j++) {
                animation[i][j] = image.getSubimage(j * tileSize, i * tileSize, tileSize, tileSize);
            }
        }
        return animation;
    }

    protected String getPATH_ATTACK_FRAMES() {
        return PATH_ATTACK_FRAMES;
    }

    protected String getPATH_RUN_FRAMES() {
        return PATH_RUN_FRAMES;
    }

    protected String getPATH_IDLE_FRAMES() {
        return PATH_IDLE_FRAMES;
    }

    protected int getTileSize() {
        return tileSize;
    }

    public static abstract class MobBuilder<T extends MobBuilder<T>> extends EntityBuilder<T> {

        private int tileSize;
        private String PATH_ATTACK_FRAMES;
        private String PATH_RUN_FRAMES;
        private String PATH_IDLE_FRAMES;

        public MobBuilder(Vector2D position) {
            super(position);
            this.tileSize = GameConstants.TILE_SIZE;
            PATH_IDLE_FRAMES = "";
            PATH_RUN_FRAMES = "";
            PATH_ATTACK_FRAMES = "";
        }


        public T setTileSize(int tileSize) {
            this.tileSize = tileSize;
            return self();
        }

        public T setPATH_ATTACK_FRAMES(String PATH_ATTACK_FRAMES) {
            this.PATH_ATTACK_FRAMES = PATH_ATTACK_FRAMES;
            return self();
        }

        public T setPATH_RUN_FRAMES(String PATH_RUN_FRAMES) {
            this.PATH_RUN_FRAMES = PATH_RUN_FRAMES;
            return self();
        }

        public T setPATH_IDLE_FRAMES(String PATH_IDLE_FRAMES) {
            this.PATH_IDLE_FRAMES = PATH_IDLE_FRAMES;
            return self();
        }

        protected void validateBaseMob() {
            if (tileSize <= 0)
                throw new IllegalArgumentException("tileSize must be greater than 0");
        }
    }
}
