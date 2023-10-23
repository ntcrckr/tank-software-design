package ru.mipt.bit.platformer.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankMovingToCoordinatesOnMovement(Direction direction) {
        Tank tank = new Tank(List.of(new Coordinates(0, 0)), Direction.RIGHT, 1f);
        tank.startMovement(direction);
        List<Coordinates> tankDestinationCoordinates = tank.getDestinationCoordinates();
        List<Coordinates> tankExpectedDestinationCoordinates = direction.apply(tank.getCoordinates());
        assertEquals(tankDestinationCoordinates, tankExpectedDestinationCoordinates);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankChangeDirectionOnMovement(Direction direction) {
        Tank tank = new Tank(List.of(new Coordinates(0, 0)), Direction.RIGHT, 1f);
        tank.startMovement(direction);
        float tankRotation = tank.getRotation();
        float tankExpectedRotation = direction.getRotation();
        assertEquals(tankRotation, tankExpectedRotation);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankIsMovingOnMovement(Direction direction) {
        Tank tank = new Tank(List.of(new Coordinates(0, 0)), Direction.RIGHT, 1f);
        tank.startMovement(direction);
        boolean tankIsMoving = tank.isMoving();
        boolean tankExpectedIsMoving = true;
        assertEquals(tankIsMoving, tankExpectedIsMoving);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankIsNotMovingOnMovementAndUpdateState(Direction direction) {
        float movementSpeed = 1f;
        Tank tank = new Tank(List.of(new Coordinates(0, 0)), Direction.RIGHT, movementSpeed);
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
        Tank tank = new Tank(List.of(new Coordinates(0, 0)), Direction.RIGHT, movementSpeed);
        tank.startMovement(direction);
        List<Coordinates> tankExpectedCoordinates = direction.apply(tank.getCoordinates());
        float deltaTime = 1 / movementSpeed;
        tank.updateState(deltaTime);
        List<Coordinates> tankCoordinates = tank.getCoordinates();
        assertEquals(tankCoordinates, tankExpectedCoordinates);
    }
}