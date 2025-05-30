// core/GameMap.java - Handles map generation, item placement, and interactions

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
        } while (isOccupied(x, y));
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

    public int getLevel() {
        return level;
    }
}
