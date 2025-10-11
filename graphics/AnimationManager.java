package graphics;

import java.awt.image.BufferedImage;
import java.util.Map;

public class AnimationManager {

    private BufferedImage[][] idleFrames;
    private BufferedImage[][] runFrames;
    private BufferedImage[][] attackFrames;
    private AnimationState currentState = AnimationState.IDLE;
    private Map<AnimationState, Long> animationSpeeds;
    private int currentFrameIndex = 0;
    private Direction currentDirection = Direction.NORTH;
    private long nowTime, lastTime;

    public AnimationManager(Map<AnimationState, Long> animationSpeeds) {
        this.animationSpeeds = animationSpeeds;
        nowTime = 0;
        lastTime = System.nanoTime();
    }

    public void loadIdleAnimations(BufferedImage[][] frames) {
        this.idleFrames = frames;
    }

    public void loadRunAnimations(BufferedImage[][] frames) {
        this.runFrames = frames;
    }

    public void loadAttackAnimations(BufferedImage[][] frames) {
        this.attackFrames = frames;
    }

    public void updateAnimation(boolean isMoving, boolean isAttacking) {
        AnimationState newState = isAttacking ? AnimationState.ATTACKING : isMoving ? AnimationState.RUNNING : AnimationState.IDLE;

        if (shouldResetAnimation(newState)) {
            resetAnimationCounters();
        }

        currentState = newState;
        advanceAnimationFrame();
    }

    private boolean shouldResetAnimation(AnimationState newState) {
        return newState != currentState;
    }

    private void resetAnimationCounters() {
        currentFrameIndex = 0;
    }

    private void advanceAnimationFrame() {
        nowTime += System.nanoTime() - lastTime;
        lastTime = System.nanoTime();
        long currentAnimationSpeed = animationSpeeds.get(currentState);
        if (nowTime > currentAnimationSpeed) {
            nowTime = 0;
            currentFrameIndex = (currentFrameIndex + 1) % getCurrentAnimationLength();
        }
    }

    private int getCurrentAnimationLength() {
        BufferedImage[] currentAnimation = getCurrentAnimationFrames();
        return currentAnimation.length;
    }

    private BufferedImage[] getCurrentAnimationFrames() {
        int directionIndex = currentDirection.getOrientationIndex();
        return currentState == AnimationState.ATTACKING ?
                attackFrames[directionIndex] : currentState == AnimationState.RUNNING ?
                runFrames[directionIndex] : idleFrames[directionIndex];
    }

    public BufferedImage getCurrentFrame() {
        BufferedImage[] animationFrames = getCurrentAnimationFrames();
        return animationFrames[currentFrameIndex];
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public void setAnimationSpeeds(Map<AnimationState, Long> animationSpeeds) {
        this.animationSpeeds = animationSpeeds;
    }

    public enum AnimationState {
        IDLE,
        RUNNING,
        ATTACKING
    }

    public enum Direction {
        NORTH(3),
        SOUTH(0),
        EAST(1),
        WEST(2);

        private final int orientationIndex;

        Direction(int orientationIndex) {
            this.orientationIndex = orientationIndex;
        }

        int getOrientationIndex() {
            return orientationIndex;
        }
    }
}