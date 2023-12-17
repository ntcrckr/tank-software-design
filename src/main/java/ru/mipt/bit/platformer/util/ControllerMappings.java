package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.controller.ControllerProvider;
import ru.mipt.bit.platformer.controller.artificial.AdapterAIControllerProvider;
import ru.mipt.bit.platformer.controller.artificial.DefaultAIControllerProvider;
import ru.mipt.bit.platformer.controller.input.GUIControllerProvider;
import ru.mipt.bit.platformer.controller.input.PlayerControllerProvider;
import ru.mipt.bit.platformer.level.GameLevel;

import java.util.HashMap;
import java.util.Map;

import static ru.mipt.bit.platformer.util.GameEntityType.*;

public class ControllerMappings {
    public final Map<GameEntityType, ControllerProvider> controllerProviderMap = new HashMap<>();

    public ControllerMappings(GameLevel gameLevel) {
        controllerProviderMap.put(PLAYER_TANK, new PlayerControllerProvider());
//        controllerProviderMap.put(ENEMY_TANK, new AdapterAIControllerProvider(gameLevel));
        controllerProviderMap.put(ENEMY_TANK, new DefaultAIControllerProvider());
        controllerProviderMap.put(GUI, new GUIControllerProvider());
    }
}
