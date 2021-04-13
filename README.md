# Maze Game

JavaFX application generates Mazes using different algorithms, solves the maze using Breadth First Search algorithm and lets the user
play.

## Building this project ##

To build this project, the following dependencies must be met

* JDK 14 or above
* JavaFX 11 or above

### Compiling and Running ###

In **Run >> Edit Configurations** Add this line to **VM Options**:

``
--module-path "${path/to/JavaFX}/lib" --add-modules=javafx.controls,javafx.fxml
``
#

## The Maze is generated using different algorithms ##

* ### Randomized Iterative Depth First Search algorithm ###

<pre>
 - Pick a random cell and mark it as visited,then push that cell to a stack
 - While there are cells in the stack:
      - If the current cell has any valid neighbours:   
          - Pick a random neighbour,remove the wall that divdies between the current cell and that neighbour
          - Mark the chosen neighboring cell to be vistited 
          - Set the neighboring cell to be the current cell
      - Else delete the current cell from the stack then set the top cell on the stack to be the current cell  
</pre>

* ### Randomized Kruskal algorithm ###
<pre>
   - Create a list of all walls,and create a set for each cell,each containing just that one cell.
   - Pick a random wall then check: 
       - If the two cells that the wall divides belong to distinct sets 
             -Remove the wall and join the two sets
       - Else continue to the next wall without doing anything </pre>

* ### Randomized Prim algorithm ###
<pre>
 - Pick a random cell and mark it as visited then add all of its walls to a list of walls
 - While there are walls in the list:
      - Pick a random wall then check: 
         - if one of the two cells that the current wall divides is not marked visited:
             - remove the current wall then add all of the not visited cell's valid walls to the list of walls
         - Mark the cells that the wall divides as visited 
         - Delete the current wall from the list of walls
</pre>

## Bot Play ##
 * ### The Bot solves the Maze using Breadth First Search algorithm ### 

<pre>
 - Push the starting cell to a queue
 - While there are cells in the queue or the current cell is not equal to the distination cell:
      - Set the front cell of the queue to be the current cell
      - Add all of its valid neighbours to the queue
      - Mark each valid neighbour to be visisted 
      - Keep track of the parent cell of each neighbour
      - Delete the front cell from the queue 
</pre>
</br>

## Game Sample ##

***NOTE: Use the keyboard arrows to control the mouse movement **OR** watch the bot solves the Maze***

![](https://media.giphy.com/media/3WODRKSaoDa3K2Tv2v/giphy.gif)
![](https://media.giphy.com/media/HKcZmen25bb6dAd6GN/giphy.gif)