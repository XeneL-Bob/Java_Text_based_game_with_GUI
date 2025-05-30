// test/GameEngineTest.java - Unit Tests for Core Game Logic

package test;

import core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {

    private GameEngine engine;

    @BeforeEach
    public void setUp() {
        engine = new GameEngine();
    }

    @Test
    public void testInitialPlayerPosition() {
        Player player = engine.getPlayer();
        assertEquals(0, player.getX());
        assertEquals(9, player.getY());
    }

    @Test
    public void testPlayerMoveUp() {
        String result = engine.movePlayer("u");
        Player player = engine.getPlayer();
        assertEquals(8, player.getY());
        assertEquals(0, player.getX());
        assertTrue(result.contains("You"));
    }

    @Test
    public void testPlayerHPDecreasesOnTrap() {
        GameMap map = engine.getMap();
        Player player = map.getPlayer();
        Trap trap = new Trap(0, 8);
        map.getItems().add(trap);
        map.getGrid()[8][0] = 'T';

        engine.movePlayer("u");
        assertEquals(8, player.getY());
        assertTrue(player.getHP() < 10);
    }

    @Test
    public void testPlayerScoreIncreasesOnGold() {
        GameMap map = engine.getMap();
        Player player = map.getPlayer();
        Gold gold = new Gold(0, 8);
        map.getItems().add(gold);
        map.getGrid()[8][0] = 'G';

        int before = player.getScore();
        engine.movePlayer("u");
        assertEquals(before + 2, player.getScore());
    }

    @Test
    public void testGameOverOnZeroHP() {
        Player player = engine.getPlayer();
        player.adjustHP(-10);
        assertFalse(player.isAlive());
    }

    @Test
    public void testInvalidDirectionReturnsMessage() {
        String response = engine.movePlayer("z");
        assertEquals("Invalid direction.", response);
    }
}
