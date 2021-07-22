package model;

import java.awt.Color;
import java.util.List;

/**
 * Represents the public (and mutable) functionalities of our model.
 */
public interface IModel extends IViewModel {

  /**
   * Starts the Mastermind game. This must be called before any other method in our model.
   * @param providedColorBank the provided color options
   * @param numGuessesAllowed the number of guesses the user is allowed to take
   * @param numColorsInGuess the length of each guess (number of colors)
   * @param duplicates whether the answer key contains duplicate colors
   * @throws IllegalArgumentException if any of the arguments are invalid
   */
  void startGame(List<Color> providedColorBank, int numGuessesAllowed, int numColorsInGuess,
      boolean duplicates) throws IllegalArgumentException;

  /**
   * Generates the answer key (color list).
   * @param duplicates whether duplicates are allowed
   * @throws IllegalStateException if the game has not started yet
   */
  void generateAnswerKey(boolean duplicates) throws IllegalStateException;

  /**
   * Adds a color to a guess.
   * @param color the color we are adding
   * @throws IllegalArgumentException if color is null
   * @throws IllegalStateException if the game has not started yet
   */
  void addColorToGuess(Color color) throws IllegalArgumentException, IllegalStateException;

  /**
   * Calculates the number of exact color matches in this guess.
   * @return number of exact color matches
   * @throws IllegalStateException if the game has not started yet
   */
  int calcNumExact() throws IllegalStateException;

  /**
   * Calculates the number of inexact color matches in this guess.
   * @return number of inexact color matches
   * @throws IllegalStateException if the game has not started yet
   */
  int calcNumInExact() throws IllegalStateException;

  /**
   * Sets the row index for a guess (sets where a guess is located in the stack).
   * The row index at the very bottom is 0 and is incremented by 1 for each row on top.
   * @param index row index
   * @throws IllegalArgumentException if index is invalid
   * @throws IllegalStateException if the game has not started yet
   */
  void setRowIndex(int index) throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets the reference of the current guess
   * (will directly mutate the current guess even if it's altered outside).
   * @throws IllegalStateException if the game has not started yet
   */
  IGuess getCurrentGuess() throws IllegalStateException;

  /**
   * Updates the inexact and exact match values for a guess.
   * @throws IllegalStateException if the game has not started yet
   */
  void updateGuessMatchInfo() throws IllegalStateException;

  /**
   * Points to a new row on top by incrementing the row index
   * and sets the current guess to a new empty guess.
   * @throws IllegalStateException if the game has not started yet
   */
  void setNewCurrentGuessRow() throws IllegalStateException;
}