package model;

import java.awt.Color;
import java.util.List;

/**
 * Represents the public functionalities of a Guess.
 */
public interface IGuess {

  /**
   * The number of colors the user inputted for this guess.
   * @return
   */
  int guessLengthSoFar();

  /**
   * The number of exact matches (same color and index as answer key) within this guess.
   * @return
   */
  int numExact();

  /**
   * The number of inexact matches within this guess.
   * @return
   */
  int numInexact();

  /**
   * Sets the exact match number.
   * @param num number we are setting
   * @throws IllegalArgumentException if the number is invalid
   */
  void setExact(int num) throws IllegalArgumentException;

  /**
   * Sets the inexact match number.
   * @param num number we are setting
   * @throws IllegalArgumentException if the number is invalid
   */
  void setInexact(int num) throws IllegalArgumentException;

  /**
   * Retrieves the list of colors that are inputted for this guess.
   * @return
   */
  List<Color> getColorList();

  /**
   * Adds a color to the guess color list.
   * @param color the color we are adding
   * @throws IllegalArgumentException if color is null
   */
  void addColor(Color color) throws IllegalArgumentException;

  /**
   * Sets a guess to the given guess (now they both have same qualities).
   * @param guess the given guess
   * @throws IllegalArgumentException if the given guess is null
   */
  void setGuess(IGuess guess) throws IllegalArgumentException;
}
