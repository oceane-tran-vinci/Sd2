package minimax;

// Cette classe repr�sente l'arbre du jeu.
public class Tree {

	// L'�tat du jeu correspondant � un noeud de l'arbre.
	private State state;

	// La valeur Minimax dans cet �tat.
	private Triplet minimaxValue;

	// null si le noeud courant est une feuille, le fils de gauche sinon.
	private Tree leftChild;

	// null si le noeud courant est une feuille, le fils de droite sinon.
	private Tree rightChild;

	// Ce constructeur construit l'arbre du jeu � partir de l'�tat state.
	// Notez que les valeurs Minimax seront calcul�e dans la m�thode
	// computeMinimaxValues
	// et non dans ce constructeur.
	public Tree(State state) {
		// TODO
		this.state = state; // On assigne l’état du jeu actuel au nœud courant

		// Si le jeu peut continuer (les deux barres sont différentes)
		if (state.getLeftBar() != state.getRightBar()) {
			// On simule un coup sur la barre de gauche → sous-état du jeu
			// et on construit récursivement le sous-arbre gauche
			leftChild = new Tree(state.playLeft());
			// Idem pour un coup sur la barre de droite → sous-arbre droit
			rightChild = new Tree(state.playRight());
		}
		// Sinon : pas de coup possible, c’est une feuille (pas d’enfant)
	}

	// Renvoie la valeur Minimax du joueur bleu en fonction des valeurs Minimax de
	// ses fils.
	private static Triplet minBlue(Triplet leftRes, Triplet rightRes) {
		// TODO
		// Si le score bleu est plus élevé dans le sous-arbre gauche, on garde celui-là
		if (leftRes.getMinBlue() > rightRes.getMinBlue()) {
			// Le paramètre 'true' indique que le meilleur choix vient du fils gauche
			return new Triplet(true, leftRes.getMinBlue(), leftRes.getMinOrange());
		} else {
			// Sinon, on prend le fils droit (minBlue est plus élevé à droite)
			return new Triplet(false, rightRes.getMinBlue(), rightRes.getMinOrange());
		}
	}

	// Renvoie la valeur Minimax du joueur orange en fonction des valeurs Minimax de
	// ses fils.
	private static Triplet minOrange(Triplet leftRes, Triplet rightRes) {
		// TODO
		// Si la valeur Minimax orange du fils gauche est meilleure (plus grande),
		// on choisit ce sous-arbre (fils gauche)
		if (leftRes.getMinOrange() > rightRes.getMinOrange()) {
			// true → le meilleur est à gauche
			return new Triplet(true, leftRes.getMinBlue(), leftRes.getMinOrange());
		} else {
			// Sinon, on prend le sous-arbre droit (fils droit)
			// false → le meilleur est à droite
			return new Triplet(false, rightRes.getMinBlue(), rightRes.getMinOrange());
		}
	}

	// Calcule les valeurs Minimax de tout l'arbre.
	// En pratique, cette m�thode calcule pour chaque noeud de l'arbre un nouveau
	// Triplet repr�sentant les valeurs Minimax de chaque noeud.
	public void computeMinimaxValues() {
		// TODO
		// Cas de base : si le nœud est une feuille, on y associe directement les scores du jeu
		// isLeft = state.isBlueToPlay() permet de garder l'information du joueur à ce moment-là
		if (this.isLeaf()) {
			minimaxValue = new Triplet(this.state.isBlueToPlay(), state.getBluePoints(), state.getOrangePoints());
			return;
		}

		// Étape récursive : on calcule d’abord les valeurs minimax des deux enfants
		leftChild.computeMinimaxValues();
		rightChild.computeMinimaxValues();

		// Selon le joueur actif, on choisit le sous-arbre le plus avantageux :
		// → si c’est au tour de Blue, on maximise les points bleus
		if (state.isBlueToPlay()) {
			minimaxValue = minBlue(leftChild.minimaxValue, rightChild.minimaxValue);
		} else {
			// → sinon, on maximise les points oranges
			minimaxValue = minOrange(leftChild.minimaxValue, rightChild.minimaxValue);
		}
	}

	// Renvoie true si le noeud est une feuille, false sinon.
	public boolean isLeaf() {
		return leftChild == null;
	}

	// Getter de la valeur Minimax
	public Triplet getMinimaxValue() {
		return minimaxValue;
	}

	// Getter de l'�tat courant
	public State getState() {
		return state;
	}

	// Getter du fils de gauche
	public Tree getLeftChild() {
		return leftChild;
	}

	// Getter du fils de droite
	public Tree getRightChild() {
		return rightChild;
	}

	public int nbrNode() {
		int res = 1;
		if (!isLeaf()) {
			res += leftChild.nbrNode() + rightChild.nbrNode();
		}
		return res;
	}
}
