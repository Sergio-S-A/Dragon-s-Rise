package features.entities.base.infrastructure;

import features.entities.base.domain.animation.*;
import features.entities.base.domain.ports.SpriteRepository;
import platform.desktop.assets.AssetLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public record AssetSpriteRepository(AssetLoader assetLoader) implements SpriteRepository {

    private static final int DIRECTION_SIZE = Direction.values().length;

    @Override
    public AnimationSystem loadAnimation(String spriteId, long[] arrayDurationNanos, int[] arrayTotalFrames, int width, int height) {

        String spriteIdle = spriteId + "/" + spriteId + "_idle.png";
        String spriteRun = spriteId + "/" + spriteId + "_run.png";
        String spriteAttack = spriteId + "/" + spriteId + "_attack.png";

        EnumMap<AnimationState, EnumMap<Direction, AnimationTrack>> tracks = new EnumMap<>(AnimationState.class);
        tracks.put(AnimationState.IDLE, loadTracks(spriteIdle, arrayDurationNanos[0], arrayTotalFrames[0]));
        tracks.put(AnimationState.RUNNING, loadTracks(spriteRun, arrayDurationNanos[1], arrayTotalFrames[1]));
        tracks.put(AnimationState.ATTACKING, loadTracks(spriteAttack, arrayDurationNanos[2], arrayTotalFrames[2]));

        loadSprite(tracks, width, height);
        return new AnimationSystem(tracks, Direction.NORTH, width, height);
    }

    @Override
    public EnumMap<Direction, AnimationTrack> loadTracks(String spritePath, long durationNanos, int totalFrames) {
        EnumMap<Direction, AnimationTrack> tracks = new EnumMap<>(Direction.class);
        tracks.put(Direction.NORTH, loadAnimationTrack(spritePath, Direction.NORTH, totalFrames, durationNanos));
        tracks.put(Direction.SOUTH, loadAnimationTrack(spritePath, Direction.SOUTH, totalFrames, durationNanos));
        tracks.put(Direction.EAST, loadAnimationTrack(spritePath, Direction.EAST, totalFrames, durationNanos));
        tracks.put(Direction.WEST, loadAnimationTrack(spritePath, Direction.WEST, totalFrames, durationNanos));
        return tracks;
    }

    @Override
    public AnimationTrack loadAnimationTrack(String spritePath, Direction direction, int totalFrames, long durationNanos) {
        List<AnimationFrame> frames = new ArrayList<>(totalFrames);
        for (int i = 0; i < totalFrames; i++) {
            String spriteId = spritePath + "_" + direction.ordinal() + "_" + i;
            frames.add(new AnimationFrame(spritePath, spriteId, i, direction, durationNanos));
        }
        return new AnimationTrack(frames);
    }

    private void loadSprite(EnumMap<AnimationState, EnumMap<Direction, AnimationTrack>> tracks, int widthSize, int heightSize) {
        int height = heightSize * DIRECTION_SIZE;
        for (AnimationState animationState : AnimationState.values()) {

            BufferedImage bufferedImage = null;
            int j = 0;
            boolean isLoaded = false;

            for (Direction direction : Direction.values()) {

                int n = tracks.get(animationState).get(direction).getFrameCount();
                int width = widthSize * n;

                for (int i = 0; i < n; i++) {
                    String spritePath = tracks.get(animationState).get(direction).getFrame(i).spritePath();
                    if (!isLoaded) {
                        bufferedImage = assetLoader.getImage(spritePath, width, height);
                        isLoaded = true;
                    }
                    if (bufferedImage != null) {
                        BufferedImage subImage = bufferedImage.getSubimage(
                                i * widthSize,
                                j * heightSize,
                                widthSize,
                                heightSize);
                        String spriteId = tracks.get(animationState).get(direction).getFrame(i).spriteId();
                        SpriteMemoryCache.registerImage(spriteId, subImage);
                    }
                }
                j++;
            }
        }
    }
}

