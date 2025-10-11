package modes.levels;

import core.physics.NewtonianPhysics;
import core.physics.PlayerController;
import core.physics.Vector2D;
import entities.player.Player;
import graphics.AnimationManager;
import modes.menus.LevelData;

import java.util.HashMap;

public class TestLevel extends IsometricLevel {

    public TestLevel() {
        super();
        initPlayer();
        configBackground();
    }

    private void initPlayer() {
        Player player1 = new Player.PlayerBuilder(new Vector2D(12, 12))
                .setPathAttackFrames("wolf/wolf-bite.png")
                .setPathRunFrames("wolf/wolf-run.png")
                .setPathIdleFrames("wolf/wolf-idle.png")
                .setPhysicsUpdater(new PlayerController(new NewtonianPhysics()))
                .setMass(6)
                .setFrictionCoefficient(5)
                .setMaxAcceleration(10)
                .setMaxSpeed(5)
                .setForceScale(500)
                .setAnimationSpeeds(new HashMap<>() {
                    {
                        put(AnimationManager.AnimationState.IDLE, 80_000_000L);
                        put(AnimationManager.AnimationState.RUNNING, 64_000_000L);
                        put(AnimationManager.AnimationState.ATTACKING, 50_000_000L);
                    }
                })
                .build();

        addPlayer(player1);
    }

    private void configBackground() {
        getBackground().setImagesData(LevelData.testLevel);
    }
}
