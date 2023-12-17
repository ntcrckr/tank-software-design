package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.GUIToggleAction;
import ru.mipt.bit.platformer.model.GameEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI implements GameEntity {
    private final Map<GUIToggleAction, Float> currentTimeouts = new HashMap<>();
    private final Map<GUIToggleAction, List<ToggleableGraphics>> actionToGraphics = new HashMap<>();
    private static final float maxTimeout = 0.5f;
    private static final float noTimeout = 0f;

    public GUI() {
        for (GUIToggleAction guiToggleAction : GUIToggleAction.values()) {
            currentTimeouts.put(guiToggleAction, noTimeout);
            actionToGraphics.put(guiToggleAction, new ArrayList<>());
        }
    }

    public void add(GUIToggleAction guiToggleAction, ToggleableGraphics graphics) {
        actionToGraphics.get(guiToggleAction).add(graphics);
    }

    public void remove(GUIToggleAction guiToggleAction, ToggleableGraphics graphics) {
        actionToGraphics.get(guiToggleAction).remove(graphics);
    }

    @Override
    public Action apply(Action action) {
        if (action instanceof GUIToggleAction guiToggleAction) {
            toggle(guiToggleAction);
        }
        return null;
    }

    private void toggle(GUIToggleAction guiToggleAction) {
        if (canToggle(guiToggleAction)) {
            currentTimeouts.put(guiToggleAction, maxTimeout);
            for (ToggleableGraphics toggleableGraphics : actionToGraphics.get(guiToggleAction)) {
                toggleableGraphics.toggle();
            }
        }
    }

    @Override
    public void updateState(float deltaTime) {
        for (GUIToggleAction guiToggleAction : actionToGraphics.keySet()) {
            if (!canToggle(guiToggleAction)) {
                float currentTimeout = currentTimeouts.get(guiToggleAction);
                currentTimeouts.put(guiToggleAction, Math.max(currentTimeout - deltaTime, noTimeout));
            }
        }
    }

    boolean canToggle(GUIToggleAction guiToggleAction) {
        return currentTimeouts.get(guiToggleAction) == noTimeout;
    }
}
