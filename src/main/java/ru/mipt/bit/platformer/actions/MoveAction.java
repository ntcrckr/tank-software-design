package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.level.CollisionLevel;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.List;

public class MoveAction implements Action {

    private final Direction direction;
    private final CollisionLevel collisionLevel;

    public MoveAction(Direction direction, CollisionLevel collisionLevel) {
        this.direction = direction;
        this.collisionLevel = collisionLevel;
    }

    @Override
    public void apply(GameObject gameObject) {
        if (!(gameObject instanceof Movable movable)) return;
        if (movable.isMoving()) return;
        List<Coordinates> futureCoordinates = direction.apply(movable.getCoordinates());
        if (!collisionLevel.isNotGoingToCollide(futureCoordinates)) {
            movable.changeDirection(direction);
            return;
        }
        movable.startMovement(direction);
    }
}
