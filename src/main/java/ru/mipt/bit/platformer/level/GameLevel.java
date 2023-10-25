package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.actions.*;
import ru.mipt.bit.platformer.instructions.Instruction;
import ru.mipt.bit.platformer.instructions.InstructionGenerator;
import ru.mipt.bit.platformer.instructions.MoveInstruction;
import ru.mipt.bit.platformer.model.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final CollisionLevel collisionLevel;
    private final InstructionGenerator instructionGenerator = new InstructionGenerator();

    public GameLevel(int startX, int endX, int startY, int endY) {
        this.collisionLevel = new CollisionLevel(startX, endX, startY, endY);
        instructionGenerator.addMapping(MoveAction.UP, new MoveInstruction(MoveAction.UP, collisionLevel));
        instructionGenerator.addMapping(MoveAction.DOWN, new MoveInstruction(MoveAction.DOWN, collisionLevel));
        instructionGenerator.addMapping(MoveAction.RIGHT, new MoveInstruction(MoveAction.RIGHT, collisionLevel));
        instructionGenerator.addMapping(MoveAction.LEFT, new MoveInstruction(MoveAction.LEFT, collisionLevel));
    }

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
        collisionLevel.add(gameObject);
    }

    public void applyActions(Map<GameObject, Action> actionMap) {
        for (Map.Entry<GameObject, Action> entry : actionMap.entrySet()) {
            GameObject gameObject = entry.getKey();
            Action action = entry.getValue();
            Instruction instruction = instructionGenerator.generate(action);
            if (instruction == null) return;
            instruction.apply(gameObject);
        }
    }

    public void updateState(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }
}
