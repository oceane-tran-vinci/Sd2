package minimax;

// Cette classe repr�sente un spectateur qui m�morise l'�tat du jeu.
//
// Elle est destin�e � �tre �tendue.
public class SimpleSpectator implements Spectator {
  private State currentState;

  @Override
  public void start(State state) {
    this.currentState = state;
  }

  @Override
  public void play(boolean isLeftMove, State state) {
    this.currentState = state;
  }

  public void end(State state) {
  }

  @Override
  public State getCurrentState() {
    return this.currentState;
  }
}
