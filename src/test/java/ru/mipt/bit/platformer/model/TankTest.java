package ru.mipt.bit.platformer.model;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.controller.Action;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    void testTankMovement() {
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, 1f);
        tank.startMovement(Direction.UP);
        assertEquals(tank.getDestinationCoordinates(), new Coordinates(0, 1));
        assertTrue(tank.isMoving());
        tank.updateState(0.5f);
        assertEquals(tank.getMovementProgress(), 0.5f);
        tank.updateState(0.5f);
        assertEquals(tank.getCoordinates(), new Coordinates(0, 1));
        assertEquals(tank.getRotation(), Direction.UP.getRotation());
        assertFalse(tank.isMoving());
    }

    @Test
    void testActionApply() {
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, 1f);
        Coordinates coordinates = tank.tryToApply(Action.MOVE_RIGHT);
        assertEquals(coordinates, new Coordinates(1, 0));
        coordinates = tank.tryToApply(Action.MOVE_DOWN);
        assertEquals(coordinates, new Coordinates(0, -1));
        coordinates = tank.tryToApply(Action.MOVE_LEFT);
        assertEquals(coordinates, new Coordinates(-1, 0));
        coordinates = tank.tryToApply(Action.MOVE_UP);
        assertEquals(coordinates, new Coordinates(0, 1));
        coordinates = tank.tryToApply(null);
        assertNull(coordinates);
        tank.startMovement(Direction.RIGHT);
        coordinates = tank.tryToApply(Action.MOVE_UP);
        assertNull(coordinates);
    }
}