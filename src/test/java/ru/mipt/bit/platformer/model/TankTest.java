package ru.mipt.bit.platformer.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.mipt.bit.platformer.controller.MoveAction;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankMovingToCoordinatesOnMovement(Direction direction) {
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, 1f);
        tank.startMovement(direction);
        Coordinates tankDestinationCoordinates = tank.getDestinationCoordinates();
        Coordinates tankExpectedDestinationCoordinates = direction.apply(tank.getCoordinates());
        assertEquals(tankDestinationCoordinates, tankExpectedDestinationCoordinates);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankChangeDirectionOnMovement(Direction direction) {
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, 1f);
        tank.startMovement(direction);
        float tankRotation = tank.getRotation();
        float tankExpectedRotation = direction.getRotation();
        assertEquals(tankRotation, tankExpectedRotation);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankIsMovingOnMovement(Direction direction) {
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, 1f);
        tank.startMovement(direction);
        boolean tankIsMoving = tank.isMoving();
        boolean tankExpectedIsMoving = true;
        assertEquals(tankIsMoving, tankExpectedIsMoving);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankIsNotMovingOnMovementAndUpdateState(Direction direction) {
        float movementSpeed = 1f;
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, movementSpeed);
        tank.startMovement(direction);
        float deltaTime = 1 / movementSpeed;
        tank.updateState(deltaTime);
        boolean tankIsMoving = tank.isMoving();
        boolean tankExpectedIsMoving = false;
        assertEquals(tankIsMoving, tankExpectedIsMoving);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankCoordinatesOnMovementAndUpdateState(Direction direction) {
        float movementSpeed = 1f;
        Tank tank = new Tank(new Coordinates(0, 0), Direction.RIGHT, movementSpeed);
        tank.startMovement(direction);
        Coordinates tankExpectedCoordinates = direction.apply(tank.getCoordinates());
        float deltaTime = 1 / movementSpeed;
        tank.updateState(deltaTime);
        Coordinates tankCoordinates = tank.getCoordinates();
        assertEquals(tankCoordinates, tankExpectedCoordinates);
    }

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
        Controllable controllable = tank.afterAction(MoveAction.RIGHT);
        assertEquals(controllable, new Tank(new Coordinates(1, 0), Direction.RIGHT, 1f));
        controllable = tank.afterAction(MoveAction.DOWN);
        assertEquals(controllable, new Tank(new Coordinates(0, -1), Direction.RIGHT, 1f));
        controllable = tank.afterAction(MoveAction.LEFT);
        assertEquals(controllable, new Tank(new Coordinates(-1, 0), Direction.RIGHT, 1f));
        controllable = tank.afterAction(MoveAction.UP);
        assertEquals(controllable, new Tank(new Coordinates(0, 1), Direction.RIGHT, 1f));
        tank.startMovement(Direction.RIGHT);
        controllable = tank.afterAction(MoveAction.UP);
        assertEquals(controllable, tank);
    }
}