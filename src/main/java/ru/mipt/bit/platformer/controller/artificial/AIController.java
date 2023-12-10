package ru.mipt.bit.platformer.controller.artificial;

import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.controller.Controller;

import java.util.Random;

public class AIController implements Controller {
    private MoveAction last = MoveAction.UP;

    @Override
    public MoveAction getAction() {
        int randomInt = new Random().nextInt(0, 5);
        MoveAction moveAction = switch (randomInt) {
            case 0 -> MoveAction.RIGHT;
            case 1 -> MoveAction.UP;
            case 2 -> MoveAction.LEFT;
            case 3 -> MoveAction.DOWN;
            case 4 -> last;
            default -> throw new IllegalStateException("Unexpected value: " + randomInt);
        };
        last = moveAction;
        return moveAction;
    }
}
