package features.entities.base.domain.animation;

public record AnimationFrame(
        String spritePath,
        String spriteId,
        int frameIndex,
        Direction direction,
        long durationNanos
) {
    public AnimationFrame {
        if (spriteId == null || spriteId.isEmpty())
            throw new IllegalArgumentException("spriteId required");
        if (frameIndex < 0)
            throw new IllegalArgumentException("frameIndex must be >= 0");
        if (direction == null)
            throw new IllegalArgumentException("direction required");
        if (durationNanos <= 0)
            throw new IllegalArgumentException("duration must be > 0");
    }
}