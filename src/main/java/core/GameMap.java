// core/GameMap.java - Maze logic + Fog Memory

package core;

import java.io.Serializable;
import java.util.*;

public class GameMap implements Serializable {
    private char[][] grid;
    private boolean[][] revealed;
    private List<Item> items;
    private Player player;
    private int level;

    public GameMap(int level, int difficulty) {
        this.level = level;
        this.grid = new char[10][10];
        this.revealed = new boolean[10][10];
        this.items = new ArrayList<>();
        for (char[] row : grid) Arrays.fill(row, ' ');
        generateMap(difficulty);
    }

    private void place(Item item) {
        items.add(item);
        grid[item.getY()][item.getX()] = item.getSymbol();
    }

    private boolean isOccupied(int x, int y) {
        if (grid[y][x] == 'W') return true;
        for (Item item : items)
            if (item.getX() == x && item.getY() == y)
                return true;
        return player != null && player.getX() == x && player.getY() == y;
    }

    private void generateMap(int difficulty) {
        Random rand = new Random();

        if (level >= 2) {
            generateMaze();
        } else {
            for (char[] row : grid) Arrays.fill(row, 'E');
        }

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

    private void generateMaze() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                grid[y][x] = (Math.random() < 0.3) ? 'W' : 'E';
            }
        }
        grid[9][0] = 'E'; // Entry point
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

    public char[][] getGrid() {
        return grid;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Item> getItems() {
        return items;
    }

    public void updatePlayerPosition(int newX, int newY) {
        if (grid[newY][newX] == 'W') return;
        grid[player.getY()][player.getX()] = 'E';
        player.setPosition(newX, newY);
        grid[newY][newX] = 'P';
        revealAround(newX, newY);
    }

    private void revealAround(int x, int y) {
        for (int dy = -2; dy <= 2; dy++) {
            for (int dx = -2; dx <= 2; dx++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < 10 && ny >= 0 && ny < 10) {
                    if (lineOfSightClear(x, y, nx, ny)) {
                        revealed[ny][nx] = true;
                    }
                }
            }
        }
    }

    private boolean lineOfSightClear(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1);
        int sx = Integer.compare(x2, x1), sy = Integer.compare(y2, y1);
        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            if (grid[y1][x1] == 'W') return false;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        return true;
    }

    public char[][] getVisibleGridWithMemory(int px, int py) {
        char[][] visible = new char[10][10];
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                visible[y][x] = revealed[y][x] ? grid[y][x] : '?';
            }
        }
        return visible;
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