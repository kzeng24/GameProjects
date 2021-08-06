package userinterface;

import java.awt.Color;
import java.util.List;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.AboveImage;
import javalib.worldimages.BesideImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.FontStyle;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;
import model.Cell;
import model.ICell;
import model.IModel;
import model.Status;

/**
 * Represents a WorldUI (an interactive user interface using features from World) for a Flood-It
 * game.
 */
public class WorldUI extends World implements IView {

  private final IModel model;
  private final int cellSize;
  private final List<List<ICell>> board;
  private final int dimensions;
  private final ICell clickedCell;
  private final int maxNumClicks;
  private int width;
  private int height;
  private WorldImage result;

  /**
   * Constructs a WorldUI with 4 parameters. Offers a more flexible and customizable appearance.
   *
   * @param model    the game model
   * @param cellSize the size of each cell
   * @param width    width of the board
   * @param height   height of the board
   * @throws IllegalArgumentException if model is null or the numeric arguments are invalid
   */
  public WorldUI(IModel model, int cellSize, int width, int height)
      throws IllegalArgumentException {
    if (model == null || cellSize <= 0 || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("invalid view constructor arguments!");
    }
    this.model = model;
    this.cellSize = cellSize;
    this.width = width;
    this.height = height;
    this.board = model.getBoard();
    this.dimensions = model.getDimensions();
    this.clickedCell = model.getClickedCell();
    this.maxNumClicks = model.getMaxNumClicks();
    this.result = this.emptyResult();
  }

  /**
   * Default constructor with model parameter.
   *
   * @param model the given game model
   * @throws IllegalArgumentException if the model is null
   */
  public WorldUI(IModel model) throws IllegalArgumentException {
    this(model, 24, 1, 1);
    this.width = this.calcWidth();
    this.height = this.calcHeight();
  }

  /**
   * Calculates the width of the board.
   *
   * @return the width of the board
   */
  private int calcWidth() {
    return this.dimensions * this.cellSize;
  }

  /**
   * Calculates the height of the game display to account for the grid and the score/result labels.
   *
   * @return the height of the board
   */
  private int calcHeight() {
    return (int) (this.calcWidth() + this.drawScore().getHeight() + this.drawResult().getHeight());
  }

  @Override
  public WorldImage drawCell(ICell cell) throws IllegalArgumentException {
    if (cell == null) {
      throw new IllegalArgumentException("cell can't be null!");
    }
    return new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, cell.getColor());
  }

  @Override
  public WorldImage drawRow(int index) throws IllegalArgumentException {
    if (index < 0 || index >= this.dimensions) {
      throw new IllegalArgumentException("invalid row index arguments!");
    }
    WorldImage row = new EmptyImage();
    for (int i = 0; i < this.dimensions; i++) {
      row = new BesideImage(row, this.drawCell(this.board.get(index).get(i)));
    }
    return row;
  }

  @Override
  public WorldImage drawGrid() {
    WorldImage grid = new EmptyImage();
    for (int i = 0; i < this.dimensions; i++) {
      grid = new AboveImage(grid, this.drawRow(i));
    }
    return grid;
  }

  /**
   * Helper method that draws text labels.
   *
   * @param text  the string text
   * @param color the color of the text
   * @return a text image
   */
  private WorldImage drawText(String text, Color color) {
    return new TextImage(text, 22, FontStyle.REGULAR, color);
  }

  @Override
  public WorldImage drawScore() {
    return this.drawText("Score: " + this.model.getCurNumClicks() + " / " + this.maxNumClicks,
        Color.BLACK);
  }

  /**
   * Draws a placeholder for the result block for when there is no result yet.
   *
   * @return an empty result image
   */
  private WorldImage emptyResult() {
    return new RectangleImage(this.width, 20, OutlineMode.SOLID, Color.WHITE);
  }

  @Override
  public WorldImage drawResult() {
    return this.result;
  }

  @Override
  public WorldScene makeScene() {
    WorldScene ws = this.getEmptyScene();
    return ws.placeImageXY(
        new AboveImage(
            this.drawGrid(),
            new OverlayImage(new AboveImage(this.drawScore(), this.drawResult()),
                new RectangleImage(this.width, 45, OutlineMode.SOLID, Color.WHITE))),
        this.width / 2, this.height / 2);
  }

  @Override
  public World onTick() {
    this.startFlooding();
    this.determineResult();
    return this;
  }

  /**
   * Finds each cell's matching neighbors and starts the flooding process.
   */
  private void startFlooding() {
    if (!this.clickedCell.equals(new Cell()) && this.model.getStatus() == Status.FLOODING) {
      this.model.findMatchingNeighborsForAllCells();
      this.model.floodCells();
    }
  }

  /**
   * Displays the result for when the user wins or loses the game.
   */
  private void determineResult() {
    if (this.model.winGame()) {
      this.result = this.drawText("You won!", Color.GREEN);
    } else if (this.model.getCurNumClicks() == this.maxNumClicks) {
      this.result = this.drawText("You lost!", Color.RED);
    }
  }

  @Override
  public World onKeyEvent(String s) {
    if (s.equals("r")) {
      this.model.initializeGame(this.model.getProvidedColors().size());
      this.result = this.emptyResult();
    }
    return this;
  }

  @Override
  public World onMouseClicked(Posn mouse) {
    int x = mouse.x / this.cellSize;
    int y = mouse.y / this.cellSize;
    // do nothing if click is out of bounds
    if (x < 0 || x >= this.dimensions || y < 0 || y >= this.dimensions) {
      return this;
    }
    ICell cell = this.board.get(y).get(x);
    this.model.setClickedCell(cell);
    this.model.updateClickCount();
    this.model.getFloodedCells().clear();
    this.model.setStatus(Status.FLOODING);
    return this;
  }
}