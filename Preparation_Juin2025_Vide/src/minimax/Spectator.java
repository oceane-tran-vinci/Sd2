package minimax;
// Cette interface repr�sente un spectacteur du jeu.

// 1) La m�thode start est appel�e � chaque d�but de partie.
// 2) La m�thode play est appel�e chaque fois qu'un coup est jou�.
// 3) La m�thode end est appel�e � chaque fin de partie. 
public interface Spectator {

  // Cette m�thode est appel�e � chaque d�but de partie.
  // state repr�sente l'�tat initial de la partie.
  void start(State state);

  // Cette m�thode est appel�e chaque fois qu'un coup est jou�.
  // isLeftMove vaut true si le coup jou� concerne la barre de gauche.
  // isLeftMove vaut false si le coup jou� concerne la barre de droite.
  // state repr�sente l'�tat apr�s que le coup ait �t� jou�.
  void play(boolean isLeftMove, State state);

  // Cette m�thode est appel�e � chaque fin de partie.
  // state repr�sente l'�tat � la fin de la partie.
  void end(State state);

  // Getter de l'�tat courant.
  State getCurrentState();
}
