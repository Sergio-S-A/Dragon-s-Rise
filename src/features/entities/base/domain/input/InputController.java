package features.entities.base.domain.input;

public interface InputController {
    boolean isMoveNorthRequested();

    boolean isMoveSouthRequested();

    boolean isMoveEastRequested();

    boolean isMoveWestRequested();

    boolean isAttackRequested();
}