package minimax;

// Cette classe repr�sente un joueur automatique.
// Elle maintient � jour un arbre des meileurs coups.
public class AutomaticPlayer extends SimpleSpectator implements Player {

	// Noeud courant de jeu. C'est le noeud qui correspond � l'�tat courant du jeu.
	private Tree currentNode;

	// En plus du contrat de Spectator, cette m�thode :
	// 1) Initialise un arbre des coups, et
	// 2) calcule les valeurs Minimax pour chaque noeud de l'arbre.
	@Override
	public void start(State state) {
		// TODO
		super.start(state); // appelle la méthode de la classe parente (Spectator)
		currentNode = new Tree(state); // crée la racine de l’arbre de jeu à partir de l’état initial
		currentNode.computeMinimaxValues(); // calcule les valeurs minimax pour tous les nœuds de l’arbre
	}

	// En plus du contrat de Spectator, cette m�thode maintient currentNode,
	// cad le noeud qui correspond � l'�tat courant du jeu.
	@Override
	public void play(boolean isLeftMove, State state) {
		// TODO
		super.play(isLeftMove, state); // met à jour l’état dans la classe parente
		if (isLeftMove) {
			currentNode = currentNode.getLeftChild(); // si le joueur a joué à gauche, on descend dans l’arbre à gauche
		} else {
			currentNode = currentNode.getRightChild(); // sinon, on descend à droite
		}
	}

	// Cette m�thode est appel�e pour connaitre le coup de ce joueur :
	// 1) Elle renvoie vrai si ce joueur joue la barre de gauche, et
	// 2) Elle renvoie faux si ce joueur joue la barre de droite.
	@Override
	public boolean nextPlay() {
		// TODO
		return currentNode.getMinimaxValue().isLeftMove(); // le choix optimal selon Minimax
	}
}
