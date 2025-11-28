package features.entities.player.application;

import core.loop.GameLoop;
import core.physics.PhysicsSystem;
import core.physics.impl.PhysicsBehavior;
import features.entities.base.domain.animation.AnimationState;
import features.entities.base.domain.animation.Direction;
import features.entities.base.domain.input.InputController;
import features.entities.base.domain.model.GameEntity;
import features.entities.player.domain.PlayerAvatar;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PlayerController extends PhysicsBehavior {

    private static final Map<Predicate<InputController>, Direction> DIRECTION_STRATEGIES = Map.of(
            InputController::isMoveNorthRequested, Direction.NORTH,
            InputController::isMoveSouthRequested, Direction.SOUTH,
            InputController::isMoveEastRequested, Direction.EAST,
            InputController::isMoveWestRequested, Direction.WEST
    );
    private final InputController inputController;
    private float forceX;
    private float forceY;

    public PlayerController(PhysicsSystem physicsSystem, InputController inputController) {
        super(physicsSystem);
        this.inputController = inputController;
    }

    @Override
    public void update(GameEntity gameEntity) {
        PlayerAvatar playerAvatar = (PlayerAvatar) gameEntity;
        handleMovementInput(playerAvatar);
        boolean isMoving = forceX != 0 || forceY != 0;
        boolean isAttacking = !isMoving && inputController.isAttackRequested();
        AnimationState newState = isAttacking ? AnimationState.ATTACKING : isMoving ? AnimationState.RUNNING : AnimationState.IDLE;
        playerAvatar.getAnimation().update(newState, determineDirectionFromInput(playerAvatar));
        updatePhysics(playerAvatar, GameLoop.getDeltaSeconds());
    }

    private void handleMovementInput(GameEntity gameEntity) {
        forceX = 0;
        forceY = 0;

        if (inputController.isMoveNorthRequested()) {
            forceY += -1;
        }
        if (inputController.isMoveSouthRequested()) {
            forceY += 1;
        }
        if (inputController.isMoveWestRequested()) {
            forceX += -1;
        }
        if (inputController.isMoveEastRequested()) {
            forceX += 1;
        }
        if (forceX != 0 || forceY != 0) {
            gameEntity.getForce().set(forceX, forceY);
            gameEntity.getForce().normalize().scale(gameEntity.getForceScale());
        } else {
            gameEntity.getForce().set(0, 0);
        }

    }

    private Direction determineDirectionFromInput(PlayerAvatar playerAvatar) {
        List<Direction> directionHistory = DIRECTION_STRATEGIES.entrySet().stream()
                .filter(entry -> entry.getKey().test(inputController))
                .map(Map.Entry::getValue)
                .toList();

        if (directionHistory.isEmpty()) {
            return playerAvatar.getCurrentDirection();
        }

        if (directionHistory.size() == 1) {
            return directionHistory.getFirst();
        }
        return playerAvatar.getCurrentDirection();
    }
}
