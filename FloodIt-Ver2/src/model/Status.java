package model;

/**
 * Status represents the three possible playing statuses. NotStarted means that the game did not
 * start yet. NotFlooding means that the user is currently playing the game but the board is not
 * flooding (changing color) yet. Flooding means that the user clicked on a different colored cell
 * which will make the board do a ripple-effect of changing colors. There is no GameEnded status
 * because the user can always reset the game after they won/lost. If they want to exit, they can
 * just click on the exit button on the top left of the canvas. The status ensures that most methods
 * are not called before the game starts and to indicate when the onTick function should actually
 * perform the flooding (instead of calling it all the time).
 */
public enum Status {
  NOTSTARTED, NOTFLOODING, FLOODING
}