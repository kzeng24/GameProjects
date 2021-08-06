package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single square location in the game grid.
 */
public class Cell implements ICell {

  private Color color;
  private List<ICell> matchingNeighbors;
  private int row;
  private int col;

  /**
   * Default constructor for creating a new Cell.
   *
   * @param color the cell color
   * @param row   the cell's row coordinate in the grid
   * @param col   the cell's column coordinate in the grid
   * @throws IllegalArgumentException if the color is null or the row/col number is invalid
   */
  public Cell(Color color, int row, int col) throws IllegalArgumentException {
    if (color == null || row < 0 || col < 0) {
      throw new IllegalArgumentException("invalid cell arguments!");
    }
    this.initializeCell(color, row, col);
  }

  /**
   * Constructs an empty and nonexistent Cell (used as a placeholder and indicator).
   */
  public Cell() {
    this.initializeCell(Color.WHITE, -1, -1);
  }

  /**
   * Initializes the cell's properties.
   *
   * @param color the color
   * @param row   the row
   * @param col   the column
   */
  private void initializeCell(Color color, int row, int col) {
    this.matchingNeighbors = new ArrayList<>();
    this.color = color;
    this.row = row;
    this.col = col;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public void setColor(Color color) throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("given color can't be null!");
    }
    this.color = color;
  }

  @Override
  public void setCell(ICell cell) throws IllegalArgumentException {
    if (cell == null) {
      throw new IllegalArgumentException("given cell can't be null!");
    }
    this.color = cell.getColor();
    this.row = cell.getRow();
    this.col = cell.getCol();
  }

  @Override
  public boolean hasSameColor(ICell cell) throws IllegalArgumentException {
    if (cell == null) {
      throw new IllegalArgumentException("cell can't be null!");
    }
    return this.color.toString().equals(cell.getColor().toString());
  }

  @Override
  public List<ICell> getMatchingNeighbors() {
    return this.matchingNeighbors;
  }

  @Override
  public void addMatchingNeighbor(ICell cell) throws IllegalArgumentException {
    if (cell == null) {
      throw new IllegalArgumentException("cell can't be null!");
    }
    this.matchingNeighbors.add(cell);
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof ICell)) {
      return false;
    }
    return ((ICell) object).getCol() == this.getCol()
        && ((ICell) object).hasSameColor(this)
        && ((ICell) object).getRow() == this.getRow()
        && ((ICell) object).getMatchingNeighbors().equals(this.getMatchingNeighbors());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.toString());
  }
}
