package userinterface;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;
import model.ICell;

/**
 * Represents all the public functionalities for the view and display of the game.
 */
public interface IView {

  /**
   * Draws the given cell.
   *
   * @param cell the cell
   * @return a cell image
   * @throws IllegalArgumentException if cell is null
   */
  WorldImage drawCell(ICell cell) throws IllegalArgumentException;

  /**
   * Draws a row of cells.
   *
   * @param index the row index
   * @return a row image
   * @throws IllegalArgumentException if the index is invalid
   */
  WorldImage drawRow(int index) throws IllegalArgumentException;

  /**
   * Draws the grid/board of the game.
   *
   * @return a grid image
   */
  WorldImage drawGrid();

  /**
   * Displays the current score.
   *
   * @return score image
   */
  WorldImage drawScore();

  /**
   * Displays the game result.
   *
   * @return result image
   */
  WorldImage drawResult();

  /**
   * Displays the present scene (determined by bigbang)
   *
   * @return present scene
   */
  WorldScene makeScene();

  /**
   * Handles the flooding animation with each tick. It is constantly called by big-bang.
   *
   * @return the new world after each tick
   */
  World onTick();

  /**
   * Handles and responds to key events.
   *
   * @param key the key pressed
   * @return the new world after a key is pressed
   */
  World onKeyEvent(String key);

  /**
   * Changes the flooding of the board based on the click position.
   *
   * @param mouse the position of the click
   * @return the new world after a cell has been clicked
   */
  World onMouseClicked(Posn mouse);
}