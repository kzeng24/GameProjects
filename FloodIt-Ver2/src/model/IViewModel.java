package model;

import java.awt.Color;
import java.util.List;

/**
 * Represents the public features that we can observe (and can't mutate) from our model.
 */
public interface IViewModel {

  /**
   * Gets the dimension size of the square board (size X size).
   *
   * @return the size of the grid dimension
   * @throws IllegalStateException if the game has not started yet
   */
  int getDimensions() throws IllegalStateException;

  /**
   * Retrieves a list of provided colors that the grid will use.
   *
   * @return a list of provided colors
   * @throws IllegalStateException if the game has not started yet
   */
  List<Color> getProvidedColors() throws IllegalStateException;

  /**
   * Gets the maximum number of clicks allowed for the user to win the game.
   *
   * @return the maximum number of clicks allowed
   * @throws IllegalStateException if the game has not started yet
   */
  int getMaxNumClicks() throws IllegalStateException;

  /**
   * Gets the current number of clicks on the game board.
   *
   * @return the current number of clicks
   * @throws IllegalStateException if the game has not started yet
   */
  int getCurNumClicks() throws IllegalStateException;

  /**
   * Retrieves the cell that was just clicked by the user.
   *
   * @return the clicked cell
   * @throws IllegalStateException if the game has not started yet
   */
  ICell getClickedCell() throws IllegalStateException;

  /**
   * Retrieves the playing status of the game.
   *
   * @return the playing status
   * @throws IllegalStateException if the game has not started yet
   */
  Status getStatus() throws IllegalStateException;

  /**
   * Checks whether all the cells in the board are flooded - the entire board is the same color.
   *
   * @return true if all the cells in the board are flooded, otherwise false
   * @throws IllegalStateException if the game has not started yet
   */
  boolean allFlooded() throws IllegalStateException;

  /**
   * Determines whether the user wins a game. If the user is able to successfully flood the entire
   * board before/when the maximum click is reached, they will win. If winGame returns false, it
   * means that the user can either continue playing or that they lost, depending on if they still
   * have clicks remaining.
   *
   * @return true if the user wins the game, false otherwise
   * @throws IllegalStateException if the game has not started yet
   */
  boolean winGame() throws IllegalStateException;
}
