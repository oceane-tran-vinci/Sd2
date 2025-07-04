package minimax_TRAN_THUY;

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
		//TODO
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
		if (leftRes.getMinBlue() > rightRes.getMinBlue()) {
			return new Triplet(true, leftRes.getMinBlue(), leftRes.getMinOrange());
		} else {
			return new Triplet(false, rightRes.getMinBlue(), rightRes.getMinOrange());
		}
	}

	// Renvoie la valeur Minimax du joueur orange en fonction des valeurs Minimax de
	// ses fils.
	private static Triplet minOrange(Triplet leftRes, Triplet rightRes) {
		if (leftRes.getMinOrange() > rightRes.getMinOrange()) {
			return new Triplet(true, leftRes.getMinBlue(), leftRes.getMinOrange());
		} else {
			return new Triplet(false, rightRes.getMinBlue(), rightRes.getMinOrange());
		}
	}

	// Calcule les valeurs Minimax de tout l'arbre.
	// En pratique, cette m�thode calcule pour chaque noeud de l'arbre un nouveau
	// Triplet repr�sentant les valeurs Minimax de chaque noeud.
	public void computeMinimaxValues() {
		if (this.isLeaf()) {
			minimaxValue = new Triplet(this.state.isBlueToPlay(), state.getBluePoints(), state.getOrangePoints());
			return;
		}
		;
		leftChild.computeMinimaxValues();
		rightChild.computeMinimaxValues();
		if (state.isBlueToPlay()) {
			minimaxValue = minBlue(leftChild.minimaxValue, rightChild.minimaxValue);
		} else {
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
