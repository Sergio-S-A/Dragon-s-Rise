package features.entities.base.domain.ports;

import features.entities.base.domain.animation.AnimationSystem;
import features.entities.base.domain.animation.AnimationTrack;
import features.entities.base.domain.animation.Direction;

import java.util.EnumMap;

public interface SpriteRepository {

    AnimationSystem loadAnimation(
            String spriteId,
            long[] durations,
            int[] frames,
            int width,
            int height
    );

    EnumMap<Direction, AnimationTrack> loadTracks(
            String spritePath,
            long duration,
            int frames
    );

    AnimationTrack loadAnimationTrack(
            String spritePath,
            Direction direction,
            int frames,
            long duration
    );
}
