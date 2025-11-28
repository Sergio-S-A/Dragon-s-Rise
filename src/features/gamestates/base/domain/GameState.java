package features.gamestates.base.domain;

public abstract class GameState {

    private final TypeState typeState;

    public GameState(TypeState typeState) {
        this.typeState = typeState;
    }

    public TypeState getModeType() {
        return typeState;
    }

    public abstract void update();

}
