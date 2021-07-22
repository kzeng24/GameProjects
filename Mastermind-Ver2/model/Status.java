package model;

/**
 * Status represents the three possible playing statuses. NotStarted means that the game did not
 * start yet. Playing means that the user is currently playing the game. GameEnded means that user
 * won/lost so the game ended.
 */
public enum Status {
  NOTSTARTED, PLAYING, GAMEENDED
}
