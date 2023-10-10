package ru.mipt.bit.platformer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    void testObstacleDestinationCoordinatesOnCreation() {
        Obstacle obstacle = new Obstacle(new Coordinates(0, 0));
        Coordinates obstacleCoordinates = obstacle.getCoordinates();
        Coordinates obstacleDestinationCoordinates = obstacle.getDestinationCoordinates();
        assertEquals(obstacleCoordinates, obstacleDestinationCoordinates);
    }

    @Test
    void testObstacleRotationOnCreation() {
        Obstacle obstacle = new Obstacle(new Coordinates(0, 0));
        float obstacleRotation = obstacle.getRotation();
        float obstacleExpectedRotation = Direction.RIGHT.getRotation();
        assertEquals(obstacleRotation, obstacleExpectedRotation);
    }

    @Test
    void testObstacleMovementProgressOnCreation() {
        Obstacle obstacle = new Obstacle(new Coordinates(0, 0));
        float obstacleMovementProgress = obstacle.getMovementProgress();
        float obstacleExpectedMovementProgress = 1f;
        assertEquals(obstacleMovementProgress, obstacleExpectedMovementProgress);
    }
}