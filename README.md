===========================
MiniDungeon - README Guide
===========================

Author: XeneL BoB (Alex I)
University: University Of the SunshineCoast
Course: ICT221
Date: 30/05/2025

------------------------------------
Overview:
------------------------------------
MiniDungeon is a Java-based dungeon crawler game built with JavaFX for the GUI. 
Players navigate through a 10x10 grid-based dungeon, collecting gold, avoiding traps, 
and fighting mutants to reach the ladder and progress through 4 levels.

Key Features:
- JavaFX GUI with tile-based rendering
- Multi-level dungeon logic
- Score tracking and HP system
- Save/Load functionality
- Top score board
- Unit-tested game engine

------------------------------------
Requirements:
------------------------------------
To build and run this project, ensure the following are installed:

- Java 17+ (JDK)
- Gradle (or use the included wrapper)
- JavaFX SDK (added to module path or VM options)
- Compatible IDE (e.g., IntelliJ IDEA or VS Code with Java support)

------------------------------------
Folder Structure:
------------------------------------
/src
  /main/java
    /core       → Main game logic and entities
    /ui         → JavaFX interface code
    /utils      → Save/load and score managers
  /main/resources/images → All tile images (player, trap, mutant, etc.)
  /test/java/test → JUnit test classes for the core logic

------------------------------------
How to Build & Run (Using Gradle):
------------------------------------

▶ Option 1: Command Line
----------------------------
1. Navigate to the project directory:
   > cd MiniDungeon

2. Run the game:
   > gradle run

3. To build only:
   > gradle build

4. To run tests:
   > gradle test

▶ Option 2: Using IntelliJ IDEA
----------------------------
1. Open the project as a Gradle project.
2. Set JavaFX SDK in your Project Structure:
   - File > Project Structure > Libraries > Add JavaFX
3. Add VM options in Run Configuration:
   --module-path "path_to_javafx_lib" --add-modules javafx.controls,javafx.fxml
4. Run the `MiniDungeonApp` from `ui.MiniDungeonApp.java`.

------------------------------------
Controls:
------------------------------------
- Arrow buttons: Move player (↑ ↓ ← →)
- Save: Save current game state
- Load: Load previous game
- Help: Display tips
- Top Scores: View high scores

------------------------------------
Notes:
------------------------------------
- Game saves to `savegame.dat` in the project root.
- Top scores are saved to `scores.dat`.
- Debugging logs appear in the in-game console.

------------------------------------
Troubleshooting:
------------------------------------
✓ GUI Not Launching via Gradle?
   - Ensure JavaFX is correctly referenced in VM options.
   - On CLI, use:
     > gradle run --args='--module-path "path_to_fx_lib" --add-modules javafx.controls,javafx.fxml'

✓ Build Fails on Unit Tests?
   - Make sure all core entities (Item, Trap, etc.) are declared public.
   - Rebuild after correcting access modifiers.

✓ "Missing resource" errors?
   - Ensure your `/resources/images/` directory is correctly structured.
   - Files like `player.png`, `trap.png`, etc., must exist.


------------------------------------
For issues or contributions, please contact:
XeneL_BoB
