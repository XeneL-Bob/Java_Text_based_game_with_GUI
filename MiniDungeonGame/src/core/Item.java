// core/Item.java - Base class and all item types in one

package core;

import java.io.Serializable;

public abstract class Item implements Serializable {
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

class Wall extends Item {
    public Wall(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return '#';
    }
}

class Trap extends Item {
    public Trap(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'T';
    }
}

class Gold extends Item {
    public Gold(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'G';
    }
}

class Potion extends Item {
    public Potion(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'H';
    }
}

class MeleeMutant extends Item {
    public MeleeMutant(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'M';
    }
}

class RangedMutant extends Item {
    public RangedMutant(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'R';
    }
}

class Ladder extends Item {
    public Ladder(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'L';
    }
}

class Entry extends Item {
    public Entry(int x, int y) {
        super(x, y);
    }

    public char getSymbol() {
        return 'E';
    }
}
