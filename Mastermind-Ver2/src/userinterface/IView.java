package userinterface;

import java.awt.Color;
import java.util.List;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.WorldImage;

/**
 * Represents the view and display of the game.
 */
public interface IView {

  /**
   * Draws a circle.
   *
   * @param radius  the circle radius
   * @param outline outline of circle
   * @param color   color of circle
   * @return circle image
   * @throws IllegalArgumentException if any of the arguments are invalid
   */
  WorldImage drawCircle(int radius, OutlineMode outline, Color color)
      throws IllegalArgumentException;

  /**
   * Draws a row for each guess and match feedback (displays circles and space for numbers).
   *
   * @param outlineList the list of outlines for each circle in the row
   * @param colorList   the list of colors for each circle in the row
   * @return row image
   * @throws IllegalArgumentException if any of the arguments are invalid
   */
  WorldImage drawRow(List<OutlineMode> outlineList, List<Color> colorList)
      throws IllegalArgumentException;

  /**
   * Draws the rows stacked onto each other (from bottom to top), creating space for potential
   * guesses.
   *
   * @param rowList list of rows
   * @return image consisting of potential guess slots
   * @throws IllegalArgumentException if the rowList is null or empty
   */
  WorldImage stackRows(List<WorldImage> rowList) throws IllegalArgumentException;

  /**
   * Colors in empty circles within the given row, according to updated information.
   *
   * @param rowIndex the row index
   * @throws IllegalArgumentException if row index is invalid
   */
  void fillInCircles(int rowIndex) throws IllegalArgumentException;

  /**
   * Draws the answer block that hides and displays the answer key.
   *
   * @return answer block image
   */
  WorldImage drawAnswerBlock();

  /**
   * Draws the result block that hides and displays the result (win/lose).
   *
   * @return result block image
   */
  WorldImage drawResultBlock();

  /**
   * Displays the number of matches/dismatches for each guess.
   *
   * @param exact   number of exact matches
   * @param inexact number of inexact matches
   * @return image displaying matches and dismatches
   * @throws IllegalArgumentException if the match or dismatch value is invalid
   */
  WorldImage displayNumbers(int exact, int inexact) throws IllegalArgumentException;

  /**
   * Displays the color options that the user can choose from to form their guesses.
   *
   * @param colorList the list of color options
   * @return color bank image
   * @throws IllegalArgumentException if the colorList is null or empty
   */
  WorldImage displayColorBank(List<Color> colorList) throws IllegalArgumentException;

  /**
   * Displays the last scene in our game world (when the player wins/loses).
   *
   * @param msg win/lose message
   * @throws IllegalArgumentException if msg is null or empty
   */
  void setLastScene(String msg) throws IllegalArgumentException;

  /**
   * Displays the present scene (determined by bigbang)
   *
   * @return present scene
   */
  WorldScene makeScene();

  /**
   * Handles and responds to key events.
   *
   * @param key the key pressed
   * @return the new world after a key is pressed
   */
  World onKeyEvent(String key);
}