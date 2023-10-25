package ru.mipt.bit.platformer.instructions;

import ru.mipt.bit.platformer.actions.Action;

import java.util.HashMap;
import java.util.Map;

public class InstructionGenerator {
    private final Map<Action, Instruction> actionInstructionMap = new HashMap<>();

    public void addMapping(Action action, Instruction instruction) {
        actionInstructionMap.put(action, instruction);
    }

    public Instruction generate(Action action) {
        return actionInstructionMap.get(action);
    }
}
