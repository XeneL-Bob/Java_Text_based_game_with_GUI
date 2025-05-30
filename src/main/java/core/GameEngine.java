import java.util.*;

// 1. Define the base Item class and item types
abstract class Item {
    protected int x, y;
    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public abstract char getSymbol();
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Wall extends Item {
    public Wall(int x, int y) { super(x, y); }
    public char getSymbol() { return '#'; }
}

class Trap extends Item {
    public Trap(int x, int y) { super(x, y); }
    public char getSymbol() { return 'T'; }
}

class Gold extends Item {
    public Gold(int x, int y) { super(x, y); }
    public char getSymbol() { return 'G'; }
}

class Potion extends Item {
    public Potion(int x, int y) { super(x, y); }
    public char getSymbol() { return 'H'; }
}

class MeleeMutant extends Item {
    public MeleeMutant(int x, int y) { super(x, y); }
    public char getSymbol() { return 'M'; }
}

class RangedMutant extends Item {
    public RangedMutant(int x, int y) { super(x, y); }
    public char getSymbol() { return 'R'; }
}

class Ladder extends Item {
    public Ladder(int x, int y) { super(x, y); }
    public char getSymbol() { return 'L'; }
}

class Entry extends Item {
    public Entry(int x, int y) { super(x, y); }
    public char getSymbol() { return 'E'; }
}

// 2. Player class
class Player {
    private int x, y, hp = 10, score = 0, steps = 0;
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void move(int dx, int dy) {
        x += dx; y += dy; steps++;
    }
    public void setPosition(int x, int y) { this.x = x; this.y = y; steps++; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHP() { return hp; }
    public int getScore() { return score; }
    public int getSteps() { return steps; }
    public void adjustHP(int delta) { hp = Math.min(10, hp + delta); }
    public void adjustScore(int delta) { score += delta; }
    public void resetSteps() { steps = 0; }
    public boolean isAlive() { return hp > 0; }
}

// 3. GameMap class
class GameMap {
    private char[][] grid;
    private List<Item> items = new ArrayList<>();
    private int level;
    private Player player;

    public GameMap(int level, int difficulty) {
        this.level = level;
        grid = new char[10][10];
        for (char[] row : grid)
            Arrays.fill(row, ' ');
        generateMap(difficulty);
    }

    private void place(Item item) {
        items.add(item);
        grid[item.getY()][item.getX()] = item.getSymbol();
    }

    private boolean isOccupied(int x, int y) {
        for (Item item : items)
            if (item.getX() == x && item.getY() == y) return true;
        return false;
    }

    private void generateMap(int difficulty) {
        Random rand = new Random();
        player = new Player(0, 9);
        grid[9][0] = 'P';
        place(new Entry(0, 9));

        placeRandom(new Ladder(-1, -1));
        for (int i = 0; i < 5; i++) placeRandom(new Gold(-1, -1));
        for (int i = 0; i < 5; i++) placeRandom(new Trap(-1, -1));
        for (int i = 0; i < 2; i++) placeRandom(new Potion(-1, -1));
        for (int i = 0; i < 3; i++) placeRandom(new MeleeMutant(-1, -1));
        for (int i = 0; i < difficulty; i++) placeRandom(new RangedMutant(-1, -1));
    }

    private void placeRandom(Item item) {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
        } while (isOccupied(x, y) || (x == 0 && y == 9));
        item.setPosition(x, y);
        place(item);
    }

    public char[][] getGrid() { return grid; }
    public Player getPlayer() { return player; }
    public List<Item> getItems() { return items; }
    public void updatePlayerPosition(int newX, int newY) {
        grid[player.getY()][player.getX()] = ' ';
        player.setPosition(newX, newY);
        grid[newY][newX] = 'P';
    }
    public Item getItemAt(int x, int y) {
        for (Item item : items)
            if (item.getX() == x && item.getY() == y)
                return item;
        return null;
    }
    public void removeItem(Item item) {
        items.remove(item);
        grid[item.getY()][item.getX()] = ' ';
    }
}

// 4. GameEngine class
class GameEngine {
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
