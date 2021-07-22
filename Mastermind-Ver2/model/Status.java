package model;

/**
 * Status represents the two possible playing statuses. NotStarted means that the game did not start
 * yet. Playing means that the user is currently playing the game. There is no GameEnded status
 * because the user can always reset the game after they won/lost. If they want to exit, they can
 * just click on the exit button on the top left of the canvas.
 */
public enum Status {
  NOTSTARTED, PLAYING
}
