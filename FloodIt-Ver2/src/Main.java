import model.FloodItModel;
import model.IModel;
import userinterface.WorldUI;

/**
 * Runs the program.
 */
public class Main {

  public static void main(String[] args) {
    // game default
    IModel game = new FloodItModel();
    game.startGame(10, 8, 30);
    WorldUI view = new WorldUI(game);
    view.bigBang(300, 330, 0.01);
  }
}
