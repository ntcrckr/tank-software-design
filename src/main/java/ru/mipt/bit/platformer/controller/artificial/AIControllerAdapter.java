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
import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.List;

public class AIControllerAdapter {
    private final AI ai = new NotRecommendingAI();
    private final GameLevel gameLevel;
    private final ActionGenerator actionGenerator;

    public AIControllerAdapter(GameLevel gameLevel, ActionGenerator actionGenerator) {
        this.gameLevel = gameLevel;
        this.actionGenerator = actionGenerator;
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
        List<Movable> enemies = actionGenerator.getAdapter().getEnemies(gameLevel.getPlayer());
        List<GameObject> obstacles = gameLevel.getAdapter().getGameObjects().stream()
                .filter(go -> go != gameLevel.getPlayer())
                .filter(enemies::contains)
                .toList();
        return obstacles.stream()
                .map(go -> new Obstacle(go.getCoordinates().getX(), go.getCoordinates().getY()))
                .toList();
    }

    private List<Bot> getBots() {
        List<Movable> enemies = actionGenerator.getAdapter().getEnemies(gameLevel.getPlayer());
        return enemies.stream()
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

    public Controller getController(GameObject gameObject) {
        return new AIController(gameObject);
    }

    class AIController implements Controller {
        private final GameObject gameObject;

        AIController(GameObject gameObject) {
            this.gameObject = gameObject;
        }

        @Override
        public Action getAction() {
            List<Recommendation> recommendations = ai.recommend(getGameState());
            return recommendations.stream()
                    .filter(r -> r.getActor().getSource() == gameObject)
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
