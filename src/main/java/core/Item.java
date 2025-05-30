// core/Item.java - Base class and all item types in one

papackage core;

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
}

class Trap extends Item {
    public Trap(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'T';
    }
}

class Gold extends Item {
    public Gold(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'G';
    }
}

class Potion extends Item {
    public Potion(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'H';
    }
}

class Ladder extends Item {
    public Ladder(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'L';
    }
}

class Wall extends Item {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return '#';
    }
}

class Mutant extends Item {
    public Mutant(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'M';
    }
}
