package model;

import java.awt.Color;
import java.util.List;

/**
 * Represents all the public functionalities that a Cell has.
 */
public interface ICell {

  /**
   * Retrieves the cell's color.
   *
   * @return the cell's color
   */
  Color getColor();

  /**
   * Sets a new color for the cell.
   *
   * @param color the color
   * @throws IllegalArgumentException if the color is null
   */
  void setColor(Color color) throws IllegalArgumentException;

  /**
   * Sets a cell to have the same characteristics as the given cell.
   *
   * @param cell the given cell
   * @throws IllegalArgumentException if the given cell is null
   */
  void setCell(ICell cell) throws IllegalArgumentException;

  /**
   * Checks whether the given cell has the same color as this cell.
   *
   * @param cell the given cell
   * @return true if both cells have the same color, otherwise false
   * @throws IllegalArgumentException if the given cell is null
   */
  boolean hasSameColor(ICell cell) throws IllegalArgumentException;

  /**
   * Retrieves a list of all the cells that are neighbors with and have the same color as the
   * indicated cell.
   *
   * @return a list of all this cell's matching neighbors
   */
  List<ICell> getMatchingNeighbors();

  /**
   * Adds a cell to this cell's list of matching neighbors.
   *
   * @param cell the given cell we are adding to our list
   * @throws IllegalArgumentException if the given cell is null
   */
  void addMatchingNeighbor(ICell cell) throws IllegalArgumentException;

  /**
   * Gets the row number of this cell.
   *
   * @return the row number
   */
  int getRow();

  /**
   * Gets the column number of this cell.
   *
   * @return the column number
   */
  int getCol();
}
