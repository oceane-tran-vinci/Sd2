package minimax;// Cette interface repr�sente un des deux joueur du jeu.

public interface Player extends Spectator {
  // Cette m�thode est appel�e pour connaitre le coup de ce joueur :
  // 1) Elle renvoie vrai si ce joueur joue la barre de gauche; et
  // 2) Elle renvoie faux si ce joueur joue la barre de droite.
  boolean nextPlay();
}
