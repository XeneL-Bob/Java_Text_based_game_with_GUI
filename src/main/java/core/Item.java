// core/Item.java - Base class and all item types in one

package core;

public abstract class Item {
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

// ENTRY POINT
class Entry extends Item {
    public Entry(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'E';
    }
}

// WALL
class Wall extends Item {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return '#';
    }
}

// LADDER
class Ladder extends Item {
    public Ladder(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'L';
    }
}

// GOLD
class Gold extends Item {
    public Gold(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'G';
    }
}

// POTION
class Potion extends Item {
    public Potion(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'H';
    }
}

// TRAP
class Trap extends Item {
    public Trap(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'T';
    }
}

// MELEE MUTANT
class MeleeMutant extends Item {
    public MeleeMutant(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'M';
    }
}

// RANGED MUTANT
class RangedMutant extends Item {
    public RangedMutant(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'R';
    }
}
