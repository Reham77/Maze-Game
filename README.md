# Maze Game

JavaFX application generates Mazes using different algorithms, solves the maze using BFS algorithm and lets the user
play.

## Building this connector project ##

To build this project, the following dependencies must be met

* JDK 14 or above
* JavaFX 11 or above

### Compiling and Running ###

In **Run >> Edit Configurations** Add this line to **VM Options**:

``
--module-path "${path/to/JavaFX}/lib" --add-modules=javafx.controls,javafx.fxml
``


