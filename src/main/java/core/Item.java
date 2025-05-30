package core;

import java.io.Serializable;

// Abstract base class for all items on the map
abstract class Item implements Serializable {
    protected int x, y;

    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract char getSymbol();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

// --- Subclasses --- //

class Wall extends Item implements Serializable {
    public Wall(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return '#';
    }
}

class Trap extends Item implements Serializable {
    public Trap(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'T';
    }
}

class Gold extends Item implements Serializable {
    public Gold(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'G';
    }
}

class Potion extends Item implements Serializable {
    public Potion(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'H';
    }
}

class Entry extends Item implements Serializable {
    public Entry(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'E';
    }
}

class Ladder extends Item implements Serializable {
    public Ladder(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'L';
    }
}

class MeleeMutant extends Item implements Serializable {
    public MeleeMutant(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'M';
    }
}

class RangedMutant extends Item implements Serializable {
    public RangedMutant(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'R';
    }
}
