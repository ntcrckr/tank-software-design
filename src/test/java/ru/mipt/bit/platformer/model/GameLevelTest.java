package ru.mipt.bit.platformer.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.level.GameLevel;

import java.util.Map;
import java.util.HashMap;

class GameLevelTest {

    @Test
    void testGameObjectsUpdateStateOnUpdateState() {
        GameLevel gameLevel = new GameLevel();
        Tank mockTank = Mockito.mock(Tank.class);
        gameLevel.add(mockTank);
        Obstacle mockObstacle = Mockito.mock(Obstacle.class);
        gameLevel.add(mockObstacle);
        float deltaTime = 1f;
        gameLevel.updateState(deltaTime);
        Mockito.verify(mockTank, Mockito.times(1)).updateState(deltaTime);
        Mockito.verify(mockObstacle, Mockito.times(1)).updateState(deltaTime);
    }

    @Test
    void testTankNotMovingOnCollision() {
        GameLevel gameLevel = new GameLevel();
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, 1f);
        Coordinates initialCoordinates = tank.getCoordinates();
        gameLevel.add(tank);
        Obstacle obstacle = new Obstacle(new Coordinates(0, 1));
        gameLevel.add(obstacle);
        Map<Movable, MoveAction> actionMap = new HashMap<>();
        actionMap.put(tank, MoveAction.UP);
        gameLevel.applyActions(actionMap);
        assertFalse(tank.isMoving());
        assertEquals(tank.getCoordinates(), initialCoordinates);
    }

    @Test
    void testTankMovingOnCollision() {
        GameLevel gameLevel = new GameLevel();
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, 1f);
        Coordinates initialCoordinates = tank.getCoordinates();
        gameLevel.add(tank);
        Obstacle obstacle = new Obstacle(new Coordinates(2, 2));
        gameLevel.add(obstacle);
        Map<Movable, MoveAction> actionMap = new HashMap<>();
        MoveAction moveAction = MoveAction.UP;
        actionMap.put(tank, moveAction);
        gameLevel.applyActions(actionMap);
        assertTrue(tank.isMoving());
        assertEquals(tank.getDestinationCoordinates(), moveAction.getDirection().apply(initialCoordinates));
    }
}