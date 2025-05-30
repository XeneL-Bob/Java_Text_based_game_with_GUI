package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Manages the top 5 game scores.
 */
public class ScoreManager {

    private static final String SCORE_FILE = "scores.dat";
    private static final int MAX_SCORES = 5;

    /**
     * Represents a score entry with score value and date.
     */
    public static class ScoreEntry implements Serializable, Comparable<ScoreEntry> {
        private final int score;
        private final Date date;

        public ScoreEntry(int score) {
            this.score = score;
            this.date = new Date();
        }

        public int getScore() {
            return score;
        }

        public Date getDate() {
            return date;
        }

        @Override
        public int compareTo(ScoreEntry other) {
            return Integer.compare(other.score, this.score); // Sort descending
        }

        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return score + " " + sdf.format(date);
        }
    }

    /**
     * Loads the score list from file.
     * @return A list of ScoreEntry, or an empty list if the file is missing.
     */
    public static List<ScoreEntry> loadScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SCORE_FILE))) {
            return (List<ScoreEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Saves a score list to file.
     * @param scores A list of ScoreEntry to store.
     */
    public static void saveScores(List<ScoreEntry> scores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORE_FILE))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            System.err.println("Error saving scores: " + e.getMessage());
        }
    }

    /**
     * Attempts to add a score to the top list.
     * @param score The score to be added if valid.
     */
    public static void tryAddScore(int score) {
        if (score < 0) return; // Ignore losing scores

        List<ScoreEntry> scores = loadScores();
        scores.add(new ScoreEntry(score));
        Collections.sort(scores);

        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }

        saveScores(scores);
    }

    /**
     * Displays the top 5 scores to the console.
     */
    public static void displayTopScores() {
        List<ScoreEntry> scores = loadScores();
        int rank = 1;
        for (ScoreEntry entry : scores) {
            System.out.println("#" + rank + " " + entry);
            rank++;
        }
    }
}
