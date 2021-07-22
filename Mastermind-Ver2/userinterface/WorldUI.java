package userinterface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.AboveAlignImage;
import javalib.worldimages.AlignModeX;
import javalib.worldimages.BesideImage;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.FontStyle;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;
import model.IGuess;
import model.IModel;

/**
 * Represents a WorldUI (an interactive user interface using features from World)
 * for a Mastermind game.
 */
public class WorldUI extends World implements IView {

  private final IModel model;
  private final int radius;
  private int width;
  private int height;
  private List<WorldImage> rowList;
  private IGuess currentGuess;
  private List<Color> updateColorList;
  private List<OutlineMode> updateOutlineList;
  private WorldImage answerImg;
  private WorldImage resultImg;
  private int numColorsInGuess;
  private int numGuessesAllowed;
  private List<Color> colorBank;
  private List<Color> answerKey;

  /**
   * Constructs a WorldUI with 4 parameters. Offers a more flexible and customizable appearance.
   *
   * @param model  the game model
   * @param width  width of the board
   * @param height height of the board
   * @param radius radius of each circle
   * @throws IllegalArgumentException if model is null or the numeric arguments are invalid
   */
  public WorldUI(IModel model, int width, int height, int radius)
      throws IllegalArgumentException {
    if (model == null || width <= 0 || height <= 0 || radius <= 0) {
      throw new IllegalArgumentException("view-model can't be null or invalid numeric arguments!");
    }
    this.model = model;
    this.radius = radius;
    this.width = width;
    this.height = height;
    this.initializeView();
  }

  /**
   * Default constructor with model parameter.
   *
   * @param model the given game model
   * @throws IllegalArgumentException if the model is null
   */
  public WorldUI(IModel model) throws IllegalArgumentException {
    this(model, 1, 1, 20);
    this.width = this.findWidth(this.numColorsInGuess, this.colorBank);
    this.height = (this.numGuessesAllowed + 2) * this.radius * 2;
    this.resultImg = this.drawResultBlock();
  }

  /**
   * Initializes important data (what we initially see).
   */
  private void initializeView() {
    this.currentGuess = this.model.getCurrentGuess();
    this.numColorsInGuess = this.model.getNumColorsInGuess();
    this.numGuessesAllowed = this.model.getNumGuessesAllowed();
    this.colorBank = this.model.getColorBank();
    this.answerKey = this.model.getAnswerKey();
    this.setEmptyCircleOutlines();
    this.rowList = this.generateRowList();
    this.answerImg = this.drawAnswerBlock();
    this.resultImg = this.drawResultBlock();
  }

  /**
   * Calculates the width of the board depending on the contents.
   *
   * @param numColorsInGuess number of colors allowed in a single guess
   * @param colorBank        the provided color options
   * @return the width of the board
   */
  private int findWidth(int numColorsInGuess, List<Color> colorBank)
      throws IllegalArgumentException {
    if (numColorsInGuess <= 0 || colorBank.size() <= 0) {
      throw new IllegalArgumentException("invalid arguments");
    }
    int colorBankLength = colorBank.size();
    int maxLength = Integer.max(numColorsInGuess, colorBankLength);
    int minLength = Integer.min(numColorsInGuess, colorBankLength);
    if (maxLength - minLength == 1) {
      return (maxLength + 1) * this.radius * 2;
    } else if (maxLength == minLength) {
      return (maxLength + 2) * this.radius * 2;
    } else {
      return maxLength * this.radius * 2;
    }
  }

  @Override
  public WorldImage drawCircle(int radius, OutlineMode outline, Color color)
      throws IllegalArgumentException {
    if (radius <= 0 || outline == null || color == null) {
      throw new IllegalArgumentException("invalid draw-circle arguments!");
    }
    return new CircleImage(radius, outline, color);
  }

  /**
   * Draws a row of circles.
   *
   * @param outlineList outline of each circle
   * @param colorList   color of each circle
   * @return an image of a row of circles
   * @throws IllegalArgumentException if any of the arguments are invalid
   */
  private WorldImage drawCircleRow(List<OutlineMode> outlineList, List<Color> colorList)
      throws IllegalArgumentException {
    if (outlineList == null || colorList == null || outlineList.isEmpty() || colorList.isEmpty()
        || outlineList.size() != colorList.size()) {
      throw new IllegalArgumentException("invalid draw-circle-row arguments!");
    }
    WorldImage circleRows = new EmptyImage();
    for (int i = 0; i < outlineList.size(); i++) {
      circleRows = new BesideImage(circleRows,
          this.drawCircle(this.radius, outlineList.get(i), colorList.get(i)));
    }
    return circleRows;
  }

  @Override
  public WorldImage drawRow(List<OutlineMode> outlineList, List<Color> colorList)
      throws IllegalArgumentException {
    if (outlineList == null || colorList == null || outlineList.size() != colorList.size()) {
      throw new IllegalArgumentException("invalid draw-row arguments!");
    }
    if (this.currentGuess.numInexact() == -1 || this.currentGuess.numExact() == -1) {
      return this.drawCircleRow(outlineList, colorList);
    } else {
      return new BesideImage(this.drawCircleRow(outlineList, colorList),
          this.displayNumbers(this.currentGuess.numExact(), this.currentGuess.numInexact()));
    }
  }

  /**
   * Draws empty circles to initialize guess slots.
   */
  private void setEmptyCircleOutlines() {
    this.updateColorList = new ArrayList<>();
    this.updateOutlineList = new ArrayList<>();
    for (int j = 0; j < this.numColorsInGuess; j++) {
      this.updateColorList.add(Color.BLACK);
      this.updateOutlineList.add(OutlineMode.OUTLINE);
    }
  }

  /**
   * Creates a list containing only solid outline modes.
   *
   * @param listLength length of our updated list
   * @return a list containing only solid outline modes
   */
  private List<OutlineMode> buildSolidOutlineList(int listLength) {
    List<OutlineMode> list = new ArrayList<>();
    for (int i = 0; i < listLength; i++) {
      list.add(OutlineMode.SOLID);
    }
    return list;
  }

  /**
   * Creates list of rows with empty circles and empty number box.
   *
   * @return initial image for each row in board
   */
  private List<WorldImage> generateRowList() {
    List<WorldImage> rowList = new ArrayList<>();
    for (int i = 0; i < this.numGuessesAllowed; i++) {
      rowList.add(this.drawRow(this.updateOutlineList, this.updateColorList));
    }
    return rowList;
  }

  @Override
  public WorldImage stackRows(List<WorldImage> rowList)
      throws IllegalArgumentException {
    if (rowList == null || rowList.isEmpty()) {
      throw new IllegalArgumentException("row list can't be null or empty!");
    }
    WorldImage rowImgs = new EmptyImage();
    for (WorldImage row : rowList) {
      rowImgs = new AboveAlignImage(AlignModeX.LEFT, row, rowImgs);
    }
    return rowImgs;
  }

  /**
   * Updates the contents of each circle in a row
   *
   * @param index the index of the circle
   * @param color the color of the circle
   * @param mode  the outline mode of the circle
   */
  private void updateCircles(int index, Color color, OutlineMode mode) {
    this.updateColorList.remove(index);
    this.updateColorList.add(index, color);
    this.updateOutlineList.remove(index);
    this.updateOutlineList.add(index, mode);
  }

  @Override
  public void fillInCircles(int rowIndex) throws IllegalArgumentException {
    if (rowIndex < 0 || rowIndex >= this.numGuessesAllowed) {
      throw new IllegalArgumentException("invalid fill-in-circle arguments!");
    }
    List<Color> currentColors = this.currentGuess.getColorList();
    for (int i = 0; i < currentColors.size(); i++) {
      this.updateCircles(i, currentColors.get(i), OutlineMode.SOLID);
    }
    for (int j = currentColors.size(); j < this.numColorsInGuess; j++) {
      this.updateCircles(j, Color.BLACK, OutlineMode.OUTLINE);
    }
    this.rowList.remove(rowIndex);
    this.rowList.add(rowIndex, this.drawRow(this.updateOutlineList, this.updateColorList));
  }

  @Override
  public WorldImage drawAnswerBlock() {
    return new RectangleImage(this.numColorsInGuess * this.radius * 2, this.radius * 2,
        OutlineMode.SOLID, Color.BLACK);
  }

  @Override
  public WorldImage drawResultBlock() {
    return new RectangleImage((int) (this.width - this.answerImg.getWidth()),
        this.radius * 2, OutlineMode.SOLID, Color.WHITE);
  }

  /**
   * Draws an image containing a number.
   *
   * @param num the number
   * @return an image containing a number
   */
  private WorldImage buildTextImage(int num) {
    WorldImage text = new TextImage(String.valueOf(num), 24, FontStyle.REGULAR, Color.BLACK);
    WorldImage rect = new RectangleImage(this.radius * 2, this.radius * 2, OutlineMode.SOLID,
        Color.WHITE);
    return new OverlayImage(text, rect);
  }

  @Override
  public WorldImage displayNumbers(int exact, int inexact) throws IllegalArgumentException {
    if (exact < 0 || exact > this.numColorsInGuess || inexact < 0
        || inexact > this.numColorsInGuess) {
      throw new IllegalArgumentException("invalid number arguments!");
    }
    return new BesideImage(this.buildTextImage(exact), this.buildTextImage(inexact));
  }

  @Override
  public WorldImage displayColorBank(List<Color> colorList) throws IllegalArgumentException {
    if (colorList == null || colorList.isEmpty()) {
      throw new IllegalArgumentException("invalid colorlist arguments!");
    }
    return this.drawCircleRow(this.buildSolidOutlineList(colorList.size()), colorList);
  }

  @Override
  public WorldScene lastScene(String msg) throws IllegalArgumentException {
    if (msg == null || msg.isEmpty()) {
      throw new IllegalArgumentException("message can't be null or empty!");
    }
    this.answerImg = new OverlayImage(
        this.drawCircleRow(this.buildSolidOutlineList(this.answerKey.size()), this.answerKey),
        new RectangleImage(this.numColorsInGuess * this.radius * 2, this.radius * 2,
            OutlineMode.SOLID, Color.WHITE));
    this.resultImg =
        new OverlayImage(new TextImage(msg, 24, FontStyle.BOLD, Color.ORANGE),
            this.resultImg);
    return this.makeScene();
  }

  @Override
  public WorldScene makeScene() {
    WorldScene ws = this.getEmptyScene();
    return ws.placeImageXY(
        new AboveAlignImage(AlignModeX.LEFT,
            new BesideImage(this.answerImg, this.resultImg),
            this.stackRows(this.rowList),
            this.displayColorBank(this.colorBank)),
        this.width / 2, this.height / 2);
  }

  @Override
  public World onKeyEvent(String key) {
    List<Color> listCurrentColorsInGuess = this.currentGuess.getColorList();
    int numCurrentColorsInGuess = this.currentGuess.guessLengthSoFar();
    int rowIndex = this.model.getRowIndex();
    try {
      this.integerKey(key, numCurrentColorsInGuess, rowIndex);
    } catch (NumberFormatException e) {
      this.backspaceKey(key, listCurrentColorsInGuess, numCurrentColorsInGuess, rowIndex);
      this.enterKey(key, rowIndex);
      this.resetKey(key);
    }
    return this;
  }

  /**
   * Colors in the next circle if an integer between 1-9 is pressed
   *
   * @param key                     key pressed
   * @param numCurrentColorsInGuess number of current colors in the current guess row
   * @param rowIndex the row index of the current guess
   */
  private void integerKey(String key, int numCurrentColorsInGuess, int rowIndex) {
    int index = Integer.parseInt(key);
    if (index >= 1 && index <= 9 && numCurrentColorsInGuess < this.numColorsInGuess) {
      this.model.addColorToGuess(this.model.getColorBank().get(index - 1));
      this.fillInCircles(rowIndex);
    }
  }

  /**
   * Deletes the left color in the row if the backspace button is pressed
   *
   * @param key                      key pressed
   * @param listCurrentColorsInGuess the current colors in the current guess row
   * @param numCurrentColorsInGuess  number of current colors in the current guess row
   * @param rowIndex the row index of the current guess
   */
  private void backspaceKey(String key, List<Color> listCurrentColorsInGuess,
      int numCurrentColorsInGuess, int rowIndex) {
    if (key.equals("backspace") && !listCurrentColorsInGuess.isEmpty()) {
      listCurrentColorsInGuess.remove(numCurrentColorsInGuess - 1);
      this.fillInCircles(rowIndex);
    }
  }

  /**
   * Moves user input to the next top row in the board
   *
   * @param key      key pressed
   * @param rowIndex the row index of the current guess
   * @return the world after the enter key is pressed
   */
  private World enterKey(String key, int rowIndex) {
    if (key.equals("enter") && this.model.isFullRow() && rowIndex < this.numGuessesAllowed) {
      this.model.updateGuessMatchInfo();
      this.fillInCircles(rowIndex);
      if (this.model.winGame()) {
        return this.endOfWorld("Win!");
      } else if (rowIndex == this.numGuessesAllowed - 1) {
        return this.endOfWorld("Lose!");
      } else {
        this.model.setNewCurrentGuessRow();
      }
    }
    return null;
  }

  /**
   * Resets the board if "r" is pressed before game ends
   *
   * @param key key pressed
   */
  private void resetKey(String key) {
    if (key.equals("r")) {
      this.initializeView();
      this.model.setRowIndex(0);
    }
  }
}