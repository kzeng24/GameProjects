package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Guess represents a pattern of colors that the user inputs for a single turn (row). It contains
 * a list of colors, and number of exact and inexact inputs.
 */
public class Guess implements IGuess {

  private List<Color> colorList;
  private int exactMatches;
  private int inexactMatches;

  /**
   * Default constructor - an empty guess.
   */
  public Guess() {
    this.colorList = new ArrayList<>();
    this.exactMatches = -1;
    this.inexactMatches = -1;
  }

  @Override
  public int guessLengthSoFar() {
    return this.colorList.size();
  }

  @Override
  public int numExact() {
    return this.exactMatches;
  }

  @Override
  public int numInexact() {
    return this.inexactMatches;
  }

  @Override
  public void setExact(int num) throws IllegalArgumentException {
    if (num > this.colorList.size() || num < 0) {
      throw new IllegalArgumentException("invalid exact match input!");
    }
    this.exactMatches = num;
  }

  @Override
  public void setInexact(int num) throws IllegalArgumentException {
    if (num > this.colorList.size() || num < 0) {
      throw new IllegalArgumentException("invalid inexact match input!");
    }
    this.inexactMatches = num;
  }

  @Override
  public List<Color> getColorList() {
    return this.colorList;
  }

  @Override
  public void addColor(Color color) throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("color can't be null!");
    }
    this.colorList.add(color);
  }

  @Override
  public void setGuess(IGuess guess) throws IllegalArgumentException {
    if (guess == null) {
      throw new IllegalArgumentException("guess can't be null!");
    }
    this.colorList = guess.getColorList();
    this.exactMatches = guess.numExact();
    this.inexactMatches = guess.numInexact();
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof IGuess)) {
      return false;
    }
    return ((IGuess) that).getColorList().toString().equals(this.getColorList().toString())
        && ((IGuess) that).numExact() == this.numExact()
        && ((IGuess) that).numInexact() == this.numInexact();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.toString());
  }

  @Override
  public String toString() {
    return "new Guess: " + this.colorList.toString() + ", " + this.exactMatches + ", "
        + this.inexactMatches;
  }
}
