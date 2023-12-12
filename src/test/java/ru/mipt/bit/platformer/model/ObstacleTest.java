package ru.mipt.bit.platformer.model;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    void testObstacleRotationOnCreation() {
        Obstacle obstacle = new Obstacle(new Coordinates(0, 0));
        float obstacleRotation = obstacle.getRotation();
        float obstacleExpectedRotation = Direction.RIGHT.getRotation();
        assertEquals(obstacleRotation, obstacleExpectedRotation);
    }
}