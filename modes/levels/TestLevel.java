package modes.levels;

import core.inputs.InputManager;
import core.physics.PlayerController;
import entities.mobs.Wolf;
import graphics.AnimationManager;
import modes.menus.LevelData;
import resources.ResourceManager;

public class TestLevel extends IsometricLevel {

    private int WOLF_SPRITE_SIZE = 128;

    public TestLevel(ResourceManager resourceManager) {
        super(resourceManager);
        initPlayer();
        configBackground();
    }

    private void initPlayer(){
        Wolf player = new Wolf.WolfBuilder(resourceManager)
                .setTILE_SIZE(WOLF_SPRITE_SIZE)
                .setAnimationManager(new AnimationManager())
                .setKeyboard(InputManager.getInstance().getKeyboard())
                .setPhysicsUpdater(new PlayerController())
                .setMass(10)
                .setFrictionCoefficient(0.001)
                .setMaxAcceleration(0.1)
                .setMaxSpeed(0.05)
                .setForceScale(0.1)
                .build();
        addPlayer(player);
    }

    private void configBackground(){
        getBackground().setImagesData(LevelData.testLevel);
    }
}
