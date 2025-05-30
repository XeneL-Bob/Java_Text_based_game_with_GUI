package utils;

import java.io.*;
import core.GameEngine;

/**
 * Handles saving and loading game state to and from a file.
 */
public class SaveLoadManager {

    private static final String SAVE_FILE = "savegame.dat";

    /**
     * Saves the current game state to a file.
     * @param engine The GameEngine instance to save.
     */
    public static void save(GameEngine engine) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(engine);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads the game state from file.
     * @return A GameEngine object if the file exists and is valid, otherwise null.
     */
    public static GameEngine load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            System.out.println("Game loaded successfully.");
            return (GameEngine) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
