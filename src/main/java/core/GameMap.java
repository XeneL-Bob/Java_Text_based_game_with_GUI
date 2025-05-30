// core/GameMap.java - Now includes wall collision and vision blocking

package core;

import java.io.Serializable;
import java.util.*;

public class GameMap implements Serializable {
    private char[][] grid;
    private List<Item> items;
    private Player player;
    private int level;

    public GameMap(int level, int difficulty) {
        this.level = level;
        this.grid = new char[10][10];
        this.items = new ArrayList<>();
        for (char[] row : grid) Arrays.fill(row, ' ');
        generateMaze();
        generateMap(difficulty);
    }

    private void place(Item item) {
        items.add(item);
        grid[item.getY()][item.getX()] = item.getSymbol();
    }

    private boolean isOccupied(int x, int y) {
        for (Item item : items)
            if (item.getX() == x && item.getY() == y)
                return true;
        return player != null && player.getX() == x && player.getY() == y;
    }

    private void generateMaze() {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            if ((x == 0 && y == 9) || (x == 9 && y == 0)) continue; // Keep entry & ladder path clear
            grid[y][x] = 'W';
        }
    }

    private void generateMap(int difficulty) {
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
        } while (isOccupied(x, y) || grid[y][x] == 'W');
        item.setPosition(x, y);
        place(item);
    }

    public boolean isWall(int x, int y) {
        return isInBounds(x, y) && grid[y][x] == 'W';
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public char[][] getGrid() {
        return grid;
    }

    public char[][] getVisibleGrid(int px, int py) {
        char[][] vis = new char[10][10];
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                vis[y][x] = ' ';
            }
        }

        for (int dy = -2; dy <= 2; dy++) {
            for (int dx = -2; dx <= 2; dx++) {
                int nx = px + dx;
                int ny = py + dy;
                if (isInBounds(nx, ny)) {
                    // block sight beyond wall
                    if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
                        vis[ny][nx] = grid[ny][nx];
                    } else if (grid[py + Integer.signum(dy)][px + Integer.signum(dx)] != 'W') {
                        vis[ny][nx] = grid[ny][nx];
                    }
                }
            }
        }
        return vis;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Item> getItems() {
        return items;
    }

    public void updatePlayerPosition(int newX, int newY) {
        if (!isWall(newX, newY)) {
            grid[player.getY()][player.getX()] = 'E';
            player.setPosition(newX, newY);
            grid[newY][newX] = 'P';
        }
    }

    public Item getItemAt(int x, int y) {
        for (Item item : items)
            if (item.getX() == x && item.getY() == y)
                return item;
        return null;
    }

    public void removeItem(Item item) {
        items.remove(item);
        grid[item.getY()][item.getX()] = 'E';
    }

    public int getLevel() {
        return level;
    }
}
