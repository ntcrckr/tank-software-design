package ru.mipt.bit.platformer.instructions;

import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.level.CollisionLevel;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.List;

public class MoveInstruction implements Instruction {
    private final MoveAction moveAction;
    private final CollisionLevel collisionLevel;

    public MoveInstruction(MoveAction moveAction, CollisionLevel collisionLevel) {
        this.moveAction = moveAction;
        this.collisionLevel = collisionLevel;
    }

    @Override
    public void apply(GameObject gameObject) {
        if (!(gameObject instanceof Movable movable)) return;
        if (movable.isMoving()) return;
        List<Coordinates> futureCoordinates = moveAction.getDirection().apply(movable.getCoordinates());
        if (!collisionLevel.isNotGoingToCollide(futureCoordinates)) {
            movable.changeDirection(moveAction.getDirection());
            return;
        }
        movable.apply(moveAction);
    }
}
