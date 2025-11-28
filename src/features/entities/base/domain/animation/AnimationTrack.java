package features.entities.base.domain.animation;

import java.util.List;

public record AnimationTrack(List<AnimationFrame> frames) {
    public AnimationTrack {
        if (frames == null || frames.isEmpty()) {
            throw new IllegalArgumentException("frames required");
        }
        frames = List.copyOf(frames);
    }

    public AnimationFrame getFrame(int index) {
        return frames.get(index % frames.size());
    }

    public int getFrameCount() {
        return frames.size();
    }

    public int frameCount() {
        return frames.size();
    }
}