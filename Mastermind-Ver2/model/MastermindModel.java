package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a model for a Mastermind game.
 */
public class MastermindModel implements IModel {

  private final List<Color> providedColorBank;
  private final List<Color> answerKey;
  private final IGuess currentGuess;
  private int numGuessesAllowed;
  private int numColorsInGuess;
  private Status status;
  private int rowIndex;

  /**
   * Default constructor to initialize game
   */
  public MastermindModel() {
    this.providedColorBank = new ArrayList<>();
    this.answerKey = new ArrayList<>();
    this.numGuessesAllowed = -1;
    this.numColorsInGuess = -1;
    this.status = Status.NOTSTARTED;
    this.currentGuess = new Guess();
    this.rowIndex = 0;
  }

  @Override
  public void startGame(List<Color> providedColorBank, int numGuessesAllowed, int numColorsInGuess,
      boolean allowDuplicates) throws IllegalArgumentException {
    if (providedColorBank == null || providedColorBank.isEmpty()) {
      throw new IllegalArgumentException("color bank can't be null or empty!");
    }
    if (numGuessesAllowed <= 0 || numColorsInGuess <= 0 ||
        (!allowDuplicates && numColorsInGuess > providedColorBank.size())) {
      throw new IllegalArgumentException("invalid guess restrictions!");
    }
    this.status = Status.PLAYING;
    this.removeDuplicateColors(providedColorBank);
    this.numGuessesAllowed = numGuessesAllowed;
    this.numColorsInGuess = numColorsInGuess;
    this.generateAnswerKey(allowDuplicates);
  }

  /**
   * Helper method to remove any duplicate colors that the user might have inputted
   *
   * @param providedColorBank the provided color options
   */
  private void removeDuplicateColors(List<Color> providedColorBank) {
    Set<Color> copyColors = new HashSet<>(providedColorBank);
    this.providedColorBank.addAll(copyColors);
  }

  /**
   * Throws an exception if the method was called before the game began.
   *
   * @throws IllegalStateException if the game did not start yet
   */
  private void didNotStartException() throws IllegalStateException {
    if (this.status != Status.PLAYING) {
      throw new IllegalStateException("Game did not start yet!");
    }
  }

  @Override
  public void generateAnswerKey(boolean allowDuplicates) throws IllegalStateException {
    this.didNotStartException();
    List<Color> colorSelection = new ArrayList<>();
    if (allowDuplicates) {
      colorSelection.addAll(this.duplicateEachColor());
    } else {
      colorSelection.addAll(this.providedColorBank);
    }
    Collections.shuffle(colorSelection);
    for (int i = 0; i < this.numColorsInGuess; i++) {
      this.answerKey.add(colorSelection.get(i));
    }
  }

  @Override
  public List<Color> getColorBank() throws IllegalStateException {
    this.didNotStartException();
    return new ArrayList<>(this.providedColorBank);
  }

  @Override
  public int getNumGuessesAllowed() throws IllegalStateException {
    this.didNotStartException();
    return this.numGuessesAllowed;
  }

  @Override
  public int getNumColorsInGuess() throws IllegalStateException {
    this.didNotStartException();
    return this.numColorsInGuess;
  }

  @Override
  public List<Color> getAnswerKey() throws IllegalStateException {
    this.didNotStartException();
    return new ArrayList<>(this.answerKey);
  }

  /**
   * Helper method that creates copies of each color x amount of times, where x is the number of
   * colors permitted for each guess.
   *
   * @return a list of colors containing repeating colors
   */
  private List<Color> duplicateEachColor() {
    List<Color> copy = new ArrayList<>();
    for (Color c : this.providedColorBank) {
      for (int i = 0; i < this.numColorsInGuess; i++) {
        copy.add(c);
      }
    }
    return copy;
  }

  @Override
  public void addColorToGuess(Color color) throws IllegalArgumentException, IllegalStateException {
    this.didNotStartException();
    if (color == null) {
      throw new IllegalArgumentException("color can't be null!");
    }
    this.currentGuess.addColor(color);
  }

  @Override
  public int calcNumExact() throws IllegalStateException {
    this.didNotStartException();
    int num = 0;
    for (int i = 0; i < this.numColorsInGuess; i++) {
      if (this.answerKey.get(i).toString()
          .equals(this.currentGuess.getColorList().get(i).toString())) {
        num = num + 1;
      }
    }
    return num;
  }

  @Override
  public int calcNumInExact() throws IllegalStateException {
    this.didNotStartException();
    int num = 0;
    List<Color> inexactColorsAlreadySeen = new ArrayList<>();
    for (int i = 0; i < this.numColorsInGuess; i++) {
      Color curGuessColor = this.currentGuess.getColorList().get(i);
      if (!this.answerKey.get(i).toString().equals(curGuessColor.toString())
          && !inexactColorsAlreadySeen.contains(curGuessColor)) {
        num = num + 1;
        inexactColorsAlreadySeen.add(curGuessColor);
      }
    }
    return num;
  }

  @Override
  public int getRowIndex() throws IllegalStateException {
    this.didNotStartException();
    return this.rowIndex;
  }

  @Override
  public void setRowIndex(int index) throws IllegalArgumentException, IllegalStateException {
    this.didNotStartException();
    if (index < 0 || index >= this.numGuessesAllowed) {
      throw new IllegalArgumentException("invalid index!");
    }
    this.rowIndex = index;
  }

  @Override
  public void updateGuessMatchInfo() throws IllegalStateException {
    this.didNotStartException();
    this.currentGuess.setExact(this.calcNumExact());
    this.currentGuess.setInexact(this.calcNumInExact());
  }

  @Override
  public void setNewCurrentGuessRow() throws IllegalStateException {
    this.didNotStartException();
    this.rowIndex += 1;
    this.currentGuess.setGuess(new Guess());
  }

  @Override
  public IGuess getCurrentGuess() throws IllegalStateException {
    this.didNotStartException();
    return this.currentGuess;
  }

  @Override
  public boolean isFullRow() throws IllegalStateException {
    this.didNotStartException();
    return this.currentGuess.guessLengthSoFar() == this.numColorsInGuess;
  }

  @Override
  public boolean winGame() throws IllegalStateException {
    this.didNotStartException();
    for (int i = 0; i < this.numColorsInGuess; i++) {
      if (!this.answerKey.get(i).toString()
          .equals(this.currentGuess.getColorList().get(i).toString())) {
        return false;
      }
    }
    this.status = Status.GAMEENDED;
    return true;
  }
}