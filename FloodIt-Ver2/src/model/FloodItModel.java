package model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a model for the game Flood-It.
 */
public class FloodItModel implements IModel {

  private final List<List<ICell>> board;
  private final ICell clickedCell;
  private final List<ICell> floodedCells;
  private ICell leftCornerCell;
  private int dimensions;
  private List<Color> providedColors;
  private int maxNumClicks;
  private int curNumClicks;
  private Status status;

  /**
   * Default constructor to create a new game.
   */
  public FloodItModel() {
    this.dimensions = -1;
    this.providedColors = new ArrayList<>();
    this.maxNumClicks = -1;
    this.curNumClicks = -1;
    this.board = new ArrayList<>();
    this.leftCornerCell = new Cell();
    this.clickedCell = new Cell();
    this.floodedCells = new ArrayList<>();
    this.status = Status.NOTSTARTED;
  }

  @Override
  public void startGame(int dimensions, int numColors, int maxNumClicks)
      throws IllegalArgumentException {
    if (dimensions <= 0 || numColors <= 0 || numColors > 10 || maxNumClicks <= 0) {
      throw new IllegalArgumentException("invalid start-game arguments!");
    }
    this.status = Status.NOTFLOODING;
    this.dimensions = dimensions;
    this.initializeGame(numColors);
    this.maxNumClicks = maxNumClicks;
  }

  @Override
  public void initializeGame(int numColors) throws IllegalStateException {
    this.didNotStartException();
    this.status = Status.NOTFLOODING;
    this.providedColors = this.selectColors(numColors);
    this.curNumClicks = 0;
    this.board.clear();
    this.generateCells();
    this.leftCornerCell = this.board.get(0).get(0);
  }

  /**
   * Ensures that certain methods are not called before the game officially starts.
   *
   * @throws IllegalStateException if the game did not start yet
   */
  private void didNotStartException() throws IllegalStateException {
    if (this.status == Status.NOTSTARTED) {
      throw new IllegalStateException("game did not start yet!");
    }
  }

  /**
   * Selects a list of random colors.
   *
   * @param num the number of colors allowed
   * @return a list of random colors
   */
  private List<Color> selectColors(int num) {
    List<Color> colorBank = new ArrayList<>(Arrays
        .asList(Color.BLACK, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.PINK,
            Color.GRAY, Color.ORANGE, Color.RED, Color.YELLOW));
    Collections.shuffle(colorBank);
    List<Color> list = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      list.add(colorBank.get(i));
    }
    return list;
  }

  /**
   * Picks a random color out of the given colors.
   *
   * @return a random color
   */
  private Color pickRandomColor() {
    List<Color> copyColors = new ArrayList<>(this.providedColors);
    Collections.shuffle(copyColors);
    return copyColors.get(0);
  }

  @Override
  public void generateCells() throws IllegalStateException {
    this.didNotStartException();
    for (int row = 0; row < this.dimensions; row++) {
      List<ICell> rowlist = new ArrayList<>();
      for (int col = 0; col < this.dimensions; col++) {
        rowlist.add(new Cell(this.pickRandomColor(), row, col));
      }
      this.board.add(rowlist);
    }
  }

  @Override
  public void floodCells() throws IllegalStateException {
    this.didNotStartException();
    Color chosenColor = this.clickedCell.getColor();
    this.floodedCells.add(this.leftCornerCell);
    List<ICell> floodedCellsCopy = new ArrayList<>(this.floodedCells);
    this.floodIteration(chosenColor, floodedCellsCopy);
    this.resetFloodingStatus();
  }

  /**
   * Floods the cells in the given list by changing their color and checks to see if the neighbors
   * of those cells can also be flooded.
   *
   * @param chosenColor     the chosen color that the flooded cells are changing to
   * @param curFloodedCells the current list of cells that are being flooded
   */
  private void floodIteration(Color chosenColor, List<ICell> curFloodedCells) {
    for (ICell cell : curFloodedCells) {
      if (!cell.getColor().toString().equals(chosenColor.toString())) {
        cell.setColor(chosenColor);
      }
      for (ICell neighbor : cell.getMatchingNeighbors()) {
        if (!this.floodedCells.contains(neighbor)) {
          this.floodedCells.add(neighbor);
        }
      }
    }
  }

  /**
   * Resets the status to NotFlooding after a round of flooding is finished.
   */
  private void resetFloodingStatus() {
    if (this.allSameColor(this.floodedCells)) {
      this.status = Status.NOTFLOODING;
    }
  }

  /**
   * Checks whether all the cells in the provided list have the same color.
   *
   * @param list the given list of cells
   * @return true if all the cells in the list have the same color, otherwise false
   */
  private boolean allSameColor(List<ICell> list) {
    Color chosenColor = this.leftCornerCell.getColor();
    for (ICell c : list) {
      if (!c.getColor().toString().equals(chosenColor.toString())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean allFlooded() throws IllegalStateException {
    this.didNotStartException();
    Color chosenColor = this.leftCornerCell.getColor();
    for (int row = 0; row < this.dimensions; row++) {
      for (int col = 0; col < this.dimensions; col++) {
        Color cellColor = this.board.get(row).get(col).getColor();
        if (!cellColor.toString().equals(chosenColor.toString())) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checks if a cell exists within the game grid - this applies only if the given row and column
   * are within the grid's range.
   *
   * @param row the cell's row location
   * @param col the cell's column location
   * @return true if the cell's coordinates are valid, otherwise false
   */
  private boolean cellExists(int row, int col) {
    return row >= 0 && row < this.dimensions && col >= 0 && col < this.dimensions;
  }

  /**
   * Creates a list of all the valid/existing cells that are neighbors to a certain cell.
   *
   * @param potentialNeighborPoints an array of points representing the potential top, left, bottom,
   *                                right neighbor locations of a cell
   * @return a list of valid neighbors
   */
  private List<ICell> createNeighborList(Point[] potentialNeighborPoints) {
    List<ICell> list = new ArrayList<>();
    for (Point p : potentialNeighborPoints) {
      if (this.cellExists(p.y, p.x)) {
        list.add(this.board.get(p.y).get(p.x));
      }
    }
    return list;
  }

  @Override
  public void findMatchingNeighborsForAllCells() throws IllegalStateException {
    this.didNotStartException();
    for (int row = 0; row < this.dimensions; row++) {
      for (int col = 0; col < this.dimensions; col++) {
        Point[] potentialNeighborPoints = this.createPotentialNeighborPoints(row, col);
        // find all valid neighbors to current cell
        List<ICell> neighborList = this.createNeighborList(potentialNeighborPoints);
        ICell currentCell = this.board.get(row).get(col);
        // check if neighbors have same color as current cell
        this.filterNeighborsWithSameColor(currentCell, neighborList);
      }
    }
  }

  /**
   * Creates an array of all the 4 possible locations of a cell's neighbors.
   *
   * @param row the possible row number of the neighbor
   * @param col the possible column number of the neighbor
   * @return an array of the 4 possible neighbor locations
   */
  private Point[] createPotentialNeighborPoints(int row, int col) {
    // top
    Point top = new Point(col, row + 1);
    // left
    Point left = new Point(col - 1, row);
    // bottom
    Point bottom = new Point(col, row - 1);
    // right
    Point right = new Point(col + 1, row);
    return new Point[]{top, left, bottom, right};
  }

  /**
   * Adds neighboring cells to the current cell's list of matching neighbors if they have same color
   * as the current cell.
   *
   * @param currentCell  the current cell
   * @param neighborList the list of the current cell's neighbors
   */
  private void filterNeighborsWithSameColor(ICell currentCell, List<ICell> neighborList) {
    for (ICell c : neighborList) {
      if (currentCell.hasSameColor(c)) {
        currentCell.addMatchingNeighbor(c);
      }
    }
  }

  @Override
  public List<List<ICell>> getBoard() throws IllegalStateException {
    this.didNotStartException();
    return this.board;
  }

  @Override
  public void updateClickCount() throws IllegalStateException {
    this.didNotStartException();
    this.curNumClicks += 1;
  }

  @Override
  public boolean winGame() throws IllegalStateException {
    this.didNotStartException();
    return this.allFlooded() && this.curNumClicks <= this.maxNumClicks;
  }

  @Override
  public int getDimensions() throws IllegalStateException {
    this.didNotStartException();
    return this.dimensions;
  }

  @Override
  public List<Color> getProvidedColors() throws IllegalStateException {
    this.didNotStartException();
    return new ArrayList<>(this.providedColors);
  }

  @Override
  public int getMaxNumClicks() throws IllegalStateException {
    this.didNotStartException();
    return this.maxNumClicks;
  }

  @Override
  public int getCurNumClicks() throws IllegalStateException {
    this.didNotStartException();
    return this.curNumClicks;
  }

  @Override
  public ICell getClickedCell() throws IllegalStateException {
    this.didNotStartException();
    return this.clickedCell;
  }

  @Override
  public void setClickedCell(ICell cell) throws IllegalArgumentException, IllegalStateException {
    this.didNotStartException();
    if (cell == null) {
      throw new IllegalArgumentException("given cell can't be null!");
    }
    this.clickedCell.setCell(cell);
  }

  @Override
  public Status getStatus() throws IllegalStateException {
    this.didNotStartException();
    return this.status;
  }

  @Override
  public void setStatus(Status status) throws IllegalArgumentException, IllegalStateException {
    this.didNotStartException();
    if (status == null) {
      throw new IllegalArgumentException("status can't be null!");
    }
    this.status = status;
  }

  @Override
  public List<ICell> getFloodedCells() throws IllegalStateException {
    this.didNotStartException();
    return this.floodedCells;
  }
}