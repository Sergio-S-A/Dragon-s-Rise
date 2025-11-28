package features.entities.base.domain.animation;

import java.util.EnumMap;

public class AnimationSystem {

    private final EnumMap<AnimationState, EnumMap<Direction, AnimationTrack>> tracks;
    private final Direction fallbackDirection;
    private final int width;
    private final int height;
    private AnimationState currentState;
    private Direction currentDirection;
    private int currentFrameIndex;
    private long lastFrameTime;

    public AnimationSystem(EnumMap<AnimationState, EnumMap<Direction, AnimationTrack>> tracks,
                           Direction fallbackDirection, int width, int height) {
        this.tracks = tracks;
        this.fallbackDirection = fallbackDirection;
        this.currentState = AnimationState.IDLE;
        this.currentDirection = fallbackDirection;
        this.currentFrameIndex = 0;
        this.lastFrameTime = System.nanoTime();
        this.width = width;
        this.height = height;

        validateTracks();
    }

    private void validateTracks() {
        if (tracks.isEmpty())
            throw new IllegalArgumentException("AnimationSystem tracks required");
        if (!tracks.containsKey(AnimationState.IDLE))
            throw new IllegalArgumentException("IDLE state required");
        if (!tracks.get(AnimationState.IDLE).containsKey(fallbackDirection))
            throw new IllegalArgumentException("Fallback direction required for IDLE");
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Width and height must be greater than 0");
    }

    public void update(AnimationState newState, Direction newDirection) {
        long now = System.nanoTime();

        if (currentState != newState || currentDirection != newDirection) {
            currentFrameIndex = 0;
            lastFrameTime = now;
        }

        currentState = newState;

        if (hasTrack(newState, newDirection)) {
            currentDirection = newDirection;
        }
        advanceFrame(now);
    }

    private void advanceFrame(long now) {
        AnimationTrack track = getCurrentTrack();
        if (track == null) return;

        AnimationFrame frame = track.getFrame(currentFrameIndex);
        long elapsed = now - lastFrameTime;

        if (elapsed >= frame.durationNanos()) {
            currentFrameIndex = (currentFrameIndex + 1) % track.frameCount();
            lastFrameTime = now;
        }
    }

    private AnimationTrack getCurrentTrack() {
        EnumMap<Direction, AnimationTrack> stateTracks = tracks.get(currentState);
        if (stateTracks == null) return null;

        AnimationTrack track = stateTracks.get(currentDirection);

        return track != null ? track : stateTracks.get(fallbackDirection);
    }

    private boolean hasTrack(AnimationState state, Direction direction) {
        EnumMap<Direction, AnimationTrack> stateTracks = tracks.get(state);
        return stateTracks != null && stateTracks.containsKey(direction);
    }

    public AnimationFrame getCurrentFrame() {
        AnimationTrack track = getCurrentTrack();
        return track != null ? track.getFrame(currentFrameIndex) : null;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public AnimationState getCurrentState() {
        return currentState;
    }

    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }
}