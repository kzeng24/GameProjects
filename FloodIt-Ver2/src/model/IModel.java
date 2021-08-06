package model;

import java.util.List;

/**
 * Represents the public (and mutable) functionalities of our model.
 */
public interface IModel extends IViewModel {

  /**
   * Starts the Flood-It game. This must be called before any other method in our model.
   *
   * @param dimensions   the dimensions of the square grid
   * @param numColors    the number of colors permitted in the game board
   * @param maxNumClicks the maximum number of clicks allowed to win the game
   * @throws IllegalArgumentException if any of the arguments are invalid
   */
  void startGame(int dimensions, int numColors, int maxNumClicks) throws IllegalArgumentException;

  /**
   * Initializes the game; sets up the game parameters for the beginning of the game.
   *
   * @param numColors the number of colors permitted in the game board
   * @throws IllegalStateException if the game has not started yet
   */
  void initializeGame(int numColors) throws IllegalStateException;

  /**
   * Generates all the cells that create the game board.
   *
   * @throws IllegalStateException if the game has not started yet
   */
  void generateCells() throws IllegalStateException;

  /**
   * Performs the flooding of the game. It does this by setting the color of the top left corner
   * cell to the color of the clicked cell, and then setting that new color to all of its matching
   * neighbors and their matching neighbors, etc. Finally, when all cells that can be flooded in
   * this round are flooded, it will reset the flooding status.
   *
   * @throws IllegalStateException if the game has not started yet
   */
  void floodCells() throws IllegalStateException;

  /**
   * Finds all the matching neighbors for every cell in the grid (aka neighbors have same color as
   * cell).
   *
   * @throws IllegalStateException if the game has not started yet
   */
  void findMatchingNeighborsForAllCells() throws IllegalStateException;

  /**
   * Sets the given cell as the clicked cell.
   *
   * @param cell the given cell
   * @throws IllegalArgumentException if the given cell is null
   * @throws IllegalStateException    if the game has not started yet
   */
  void setClickedCell(ICell cell) throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets the game board/grid as a double list of cells.
   *
   * @return the game board
   * @throws IllegalStateException if the game has not started yet
   */
  List<List<ICell>> getBoard() throws IllegalStateException;

  /**
   * Gets the list of currently flooded cells.
   *
   * @return the list of currently flooded cells
   * @throws IllegalStateException if the game has not started yet
   */
  List<ICell> getFloodedCells() throws IllegalStateException;

  /**
   * Increments the number of clicks by 1.
   *
   * @throws IllegalStateException if the game has not started yet
   */
  void updateClickCount() throws IllegalStateException;

  /**
   * Set the status of the game.
   *
   * @param status the given status
   * @throws IllegalArgumentException if the given status is null
   * @throws IllegalStateException    if the game has not started yet
   */
  void setStatus(Status status) throws IllegalArgumentException, IllegalStateException;
}
