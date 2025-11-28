package features.gamestates.base.domain.service;

import features.gamestates.base.domain.GameState;

public class GameStateManager {
    private static GameStateManager instance;
    private GameState nowGameState;

    public GameStateManager() {
    }

    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    public GameState getMode() {
        return this.nowGameState;
    }

    public void setMode(GameState gameState) {
        this.nowGameState = gameState;
    }
}
