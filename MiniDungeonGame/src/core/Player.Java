// core/Player.java - Represents the player entity

package core;

import java.io.Serializable;

public class Player implements Serializable {
    private int x, y;
    private int hp;
    private int score;
    private int steps;
    private final int MAX_HP = 10;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.hp = MAX_HP;
        this.score = 0;
        this.steps = 0;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
        steps++;
    }

    public void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
        steps++;
    }

    public void adjustHP(int delta) {
        hp = Math.max(0, Math.min(MAX_HP, hp + delta));
    }

    public void adjustScore(int delta) {
        score += delta;
    }

    public void resetSteps() {
        steps = 0;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHP() {
        return hp;
    }

    public int getScore() {
        return score;
    }

    public int getSteps() {
        return steps;
    }

    public int getMaxHP() {
        return MAX_HP;
    }
}
