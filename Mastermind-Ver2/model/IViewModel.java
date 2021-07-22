package model;

import java.awt.Color;
import java.util.List;

/**
 * Represents the public features that we can observe (and can't mutate) from our model.
 */
public interface IViewModel {

  /**
   * Retrieves a list of the provided color options.
   * @return list of the provided color options
   * @throws IllegalStateException if game did not start yet
   */
  List<Color> getColorBank() throws IllegalStateException;

  /**
   * Retrieves the number of guesses allowed (number of rows) for a game round.
   * @return the number of guesses allowed
   * @throws IllegalStateException if game did not start yet
   */
  int getNumGuessesAllowed() throws IllegalStateException;

  /**
   * Retrieves the required length in a guess.
   * It is the number of colors that the user must input to make a complete guess.
   * @return required number of colors the user must input in a guess
   * @throws IllegalStateException if game did not start yet
   */
  int getNumColorsInGuess() throws IllegalStateException;

  /**
   * Retrieves the answer key.
   * @return a list of colors representing the answer key
   * @throws IllegalStateException if game did not start yet
   */
  List<Color> getAnswerKey() throws IllegalStateException;

  /**
   * Gets the index of the current row.
   * @return the index of the current row
   * @throws IllegalStateException if game did not start yet
   */
  int getRowIndex() throws IllegalStateException;

  /**
   * Determines whether the user completed a guess by filling in all the circles in a row.
   * @return true if the user completed a guess or false otherwise
   * @throws IllegalStateException if game did not start yet
   */
  boolean isFullRow() throws IllegalStateException;

  /**
   * Determines whether the user wins a game.
   * If the user is able to successfully create a guess that matches the answer key before
   * they use up all their guesses, they will win.
   * If winGame returns false, it means that the user can either continue guessing or that they
   * lost, depending on if they still have guesses remaining.
   * @return true if the user wins the game, false otherwise
   * @throws IllegalStateException if game did not start yet
   */
  boolean winGame() throws IllegalStateException;
}