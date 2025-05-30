// core/GameEngine.java - Central game logic engine

package core;

import java.io.Serializable;

public class GameEngine implements Serializable {
    private GameMap map;
    private int level = 1;
    private int difficulty = 3;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private final int MAX_STEPS = 100;

    public GameEngine() {
        map = new GameMap(level, difficulty);
    }

    public String movePlayer(String direction) {
        if (gameOver) return "Game over.";

        int dx = 0, dy = 0;
        switch (direction.toLowerCase()) {
            case "u": dy = -1; break;
            case "d": dy = 1; break;
            case "l": dx = -1; break;
            case "r": dx = 1; break;
            default: return "Invalid direction.";
        }

        Player p = map.getPlayer();
        int newX = p.getX() + dx;
        int newY = p.getY() + dy;

        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10) return "You tried to move outside the map.";

        Item item = map.getItemAt(newX, newY);
        StringBuilder log = new StringBuilder();

        if (item instanceof Wall) {
            log.append("You tried to move into a wall.");
        } else {
            map.updatePlayerPosition(newX, newY);

            if (item instanceof Trap) {
                p.adjustHP(-2);
                log.append("You fell into a trap.");
            } else if (item instanceof Gold) {
                p.adjustScore(2);
                map.removeItem(item);
                log.append("You picked up a gold.");
            } else if (item instanceof Potion) {
                p.adjustHP(4);
                map.removeItem(item);
                log.append("You used a health potion.");
            } else if (item instanceof MeleeMutant) {
                p.adjustHP(-2);
                p.adjustScore(2);
                map.removeItem(item);
                log.append("You attacked a melee mutant and won.");
            } else if (item instanceof RangedMutant) {
                p.adjustScore(2);
                map.removeItem(item);
                log.append("You attacked a ranged mutant and won.");
            } else if (item instanceof Ladder) {
                if (level == 2) {
                    gameWon = true;
                    gameOver = true;
                    log.append("You escaped the dungeon. Congratulations!");
                } else {
                    level = 2;
                    difficulty += 2;
                    map = new GameMap(level, difficulty);
                    log.append("You found the ladder. Advancing to level 2...");
                    return log.toString();
                }
            } else {
                log.append("You moved.");
            }

            checkRangedMutants(p, log);
        }

        if (p.getHP() <= 0 || p.getSteps() >= MAX_STEPS) {
            gameOver = true;
            p.adjustScore(-p.getScore()); // Set score to -1
            log.append(" Game Over.");
        }

        return log.toString();
    }

    private void checkRangedMutants(Player p, StringBuilder log) {
        for (Item item : map.getItems()) {
            if (item instanceof RangedMutant) {
                int dx = Math.abs(item.getX() - p.getX());
                int dy = Math.abs(item.getY() - p.getY());
                if ((dx == 0 && dy <= 2) || (dy == 0 && dx <= 2)) {
                    if (Math.random() < 0.5) {
                        p.adjustHP(-2);
                        log.append(" A ranged mutant attacked and you lost 2 HP.");
                    } else {
                        log.append(" A ranged mutant attacked, but missed.");
                    }
                }
            }
        }
    }

    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }
    public GameMap getMap() { return map; }
    public Player getPlayer() { return map.getPlayer(); }
}