package ru.mipt.bit.platformer.level.generator;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;

public class LevelInfo {
    private final GameLevel gameLevel;
    private final GameGraphics gameGraphics;
    private final ActionGenerator actionGenerator;

    public LevelInfo(GameLevel gameLevel, GameGraphics gameGraphics, ActionGenerator actionGenerator) {
        this.gameLevel = gameLevel;
        this.gameGraphics = gameGraphics;
        this.actionGenerator = actionGenerator;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public GameGraphics getGameGraphics() {
        return gameGraphics;
    }

    public ActionGenerator getActionGenerator() {
        return actionGenerator;
    }
}
