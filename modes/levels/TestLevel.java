package modes.levels;

import core.physics.PlayerController;
import core.physics.Vector2D;
import entities.player.Player;
import modes.menus.LevelData;

public class TestLevel extends IsometricLevel {

    public TestLevel() {
        super();
        initPlayer();
        configBackground();
    }

    private void initPlayer() {
        int PLAYER_SPRITE_SIZE = 128;
        Player player1 = new Player.PlayerBuilder(new Vector2D(12, 12))
                .setPATH_ATTACK_FRAMES("wolf/wolf-bite.png")
                .setPATH_RUN_FRAMES("wolf/wolf-run.png")
                .setPATH_IDLE_FRAMES("wolf/wolf-idle.png")
                .setPhysicsUpdater(new PlayerController())
                .setTileSize(PLAYER_SPRITE_SIZE)
                .setMass(6)
                .setFrictionCoefficient(5)
                .setMaxAcceleration(10)
                .setMaxSpeed(5)
                .setForceScale(500)
                .build();

        addPlayer(player1);
    }

    private void configBackground() {
        getBackground().setImagesData(LevelData.testLevel);
    }
}
