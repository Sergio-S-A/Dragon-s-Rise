package features.entities.base.domain.model;

import core.physics.Position;
import features.entities.base.domain.animation.AnimationFrame;
import features.entities.base.domain.animation.AnimationState;
import features.entities.base.domain.animation.AnimationSystem;
import features.entities.base.domain.animation.Direction;

public abstract class Actor extends GameEntity {

    protected final AnimationSystem animationSystem;
    private final String characterId;
    private final int tileSize;

    public Actor(ActorBuilder<?> actorBuilder) {
        super(actorBuilder);
        this.animationSystem = actorBuilder.animationSystem;
        this.characterId = actorBuilder.characterId;
        this.tileSize = actorBuilder.tileSize;
    }

    public abstract void update();

    public Direction getCurrentDirection() {
        return animationSystem.getCurrentDirection();
    }

    public AnimationState getCurrentState() {
        return animationSystem.getCurrentState();
    }

    public AnimationFrame getCurrentAnimationFrame() {
        return animationSystem.getCurrentFrame();
    }

    public AnimationSystem getAnimation() {
        return animationSystem;
    }

    public String getCharacterId() {
        return characterId;
    }

    public int getTileSize() {
        return tileSize;
    }

    public static abstract class ActorBuilder<T extends ActorBuilder<T>> extends EntityBuilder<T> {

        private final String characterId;
        private int tileSize;
        private AnimationSystem animationSystem;

        public ActorBuilder(Position position, String characterId) {
            super(position);
            this.characterId = characterId;
            this.tileSize = 64;
        }

        public T setTileSize(int tileSize) {
            this.tileSize = tileSize;
            return self();
        }

        public T setAnimation(AnimationSystem animationSystem) {
            this.animationSystem = animationSystem;
            return self();
        }

        protected void validateAnimation() {
            if (animationSystem == null)
                throw new IllegalArgumentException("animationSystem must not be null");
        }
    }
}
