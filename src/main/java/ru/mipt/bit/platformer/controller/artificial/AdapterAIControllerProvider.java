package ru.mipt.bit.platformer.controller.artificial;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.controller.ControllerProvider;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.GameEntity;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.List;

import static ru.mipt.bit.platformer.util.GameEntityType.ENEMY_TANK;
import static ru.mipt.bit.platformer.util.GameEntityType.TREE;

public class AdapterAIControllerProvider implements ControllerProvider {
    private final AI ai = new NotRecommendingAI();
    private final GameLevel gameLevel;

    public AdapterAIControllerProvider(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    public GameState getGameState() {
        List<Obstacle> obstacles = getObstacles();
        List<Bot> bots = getBots();
        Player player = getPlayer();
        return new GameState.GameStateBuilder()
                .obstacles(obstacles)
                .bots(bots)
                .player(player)
                .levelWidth(gameLevel.getSize().getX())
                .levelHeight(gameLevel.getSize().getY())
                .build();
    }

    private Player getPlayer() {
        Movable player = gameLevel.getPlayer();
        return new Player.PlayerBuilder()
                .source(player)
                .x(player.getCoordinates().getX())
                .y(player.getCoordinates().getY())
                .destX(player.getDestinationCoordinates().getX())
                .destY(player.getDestinationCoordinates().getY())
                .orientation(getOrientation(player.getDirection()))
                .build();
    }

    private List<Obstacle> getObstacles() {
        List<GameObject> obstacles = gameLevel.getAdapter().getGameEntities().stream()
                .filter(go -> go.getGameObjectType() == TREE)
                .map(ge -> (GameObject) ge)
                .toList();
        return obstacles.stream()
                .map(go -> new Obstacle(go.getCoordinates().getX(), go.getCoordinates().getY()))
                .toList();
    }

    private List<Bot> getBots() {
        return gameLevel.getAdapter().getGameEntities().stream()
                .filter(ge -> ge.getGameObjectType() == ENEMY_TANK)
                .map(ge -> (Movable) ge)
                .map(go -> new Bot.BotBuilder()
                        .source(go)
                        .x(go.getCoordinates().getX())
                        .y(go.getCoordinates().getY())
                        .destX(go.getDestinationCoordinates().getX())
                        .destY(go.getDestinationCoordinates().getY())
                        .orientation(getOrientation(go.getDirection()))
                        .build()
                )
                .toList();
    }

    private Orientation getOrientation(Direction direction) {
        return switch (direction) {
            case RIGHT -> Orientation.E;
            case UP -> Orientation.N;
            case LEFT -> Orientation.W;
            case DOWN -> Orientation.S;
        };
    }

    @Override
    public Controller getController(GameEntity gameEntity) {
        return new AIController(gameEntity);
    }

    class AIController implements Controller {
        private final GameEntity gameEntity;

        AIController(GameEntity gameEntity) {
            this.gameEntity = gameEntity;
        }

        @Override
        public Action getAction() {
            List<Recommendation> recommendations = ai.recommend(getGameState());
            return recommendations.stream()
                    .filter(r -> r.getActor().getSource() == gameEntity)
                    .findFirst()
                    .map(r -> switch (r.getAction()) {
                        case Shoot -> new ShootAction();
                        case MoveNorth -> MoveAction.UP;
                        case MoveEast -> MoveAction.RIGHT;
                        case MoveSouth -> MoveAction.DOWN;
                        case MoveWest -> MoveAction.LEFT;
                    })
                    .orElse(null);
        }
    }
}
