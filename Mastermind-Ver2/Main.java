import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import model.IModel;
import model.MastermindModel;
import userinterface.WorldUI;

/**
 * Runs the program.
 */
public class Main {

  public static void main(String[] args) {
    // game default
    IModel game = new MastermindModel();
    game.startGame(new ArrayList<>(Arrays.asList(Color.CYAN, Color.PINK, Color.BLUE, Color.GREEN)),
        10, 3, false);
    WorldUI view = new WorldUI(game);
    view.bigBang(200, 480, 0.1);
  }
}
