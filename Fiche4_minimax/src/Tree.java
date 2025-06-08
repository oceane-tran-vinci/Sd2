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
  // Notez que les valeurs Minimax seront calcul�e dans la m�thode computeMinimaxValues
  // et non dans ce constructeur.
  public Tree(State state) {
    // TODO
    this.state = state;
    //Si l'état est une feuille (cad que les || st au mm endroit), on ne fait rien
    if (state.getLeftBar() == state.getRightBar()) {
      return;
    }
    //Sinon, on crée les fils gauche et droit
    leftChild = new Tree(state.playLeft());
    rightChild = new Tree(state.playRight());
  }

  // Renvoie la valeur Minimax du joueur bleu en fonction des valeurs Minimax de
  // ses fils.
  //On choisit le fils qui rapporte le maximum de points.
  private static Triplet minBlue(Triplet leftRes, Triplet rightRes) {
    // TODO
    //On compare les deux résultats des fils gauche et droit du bleu
    boolean isLeftMove = leftRes.getMinBlue() > rightRes.getMinBlue();

    //Si le fils gauche est le max, on renvoie le résultat du fils gauche
    if (isLeftMove) {
      return new Triplet(isLeftMove, leftRes.getMinBlue(), leftRes.getMinOrange());
    } else {
      //Sinon, on renvoie le résultat du fils droit
      return new Triplet(isLeftMove, rightRes.getMinBlue(), rightRes.getMinOrange());
    }
  }

  // Renvoie la valeur Minimax du joueur orange en fonction des valeurs Minimax de
  // ses fils.
  //On choisit le fils qui rapporte le minimum de points.
  private static Triplet minOrange(Triplet leftRes, Triplet rightRes) {
    // TODO
    //On compare les deux résultats des fils gauche et droit du bleu
    boolean isLeftMove = leftRes.getMinBlue() < rightRes.getMinBlue();

    //Si le fils gauche est le min, on renvoie le résultat du fils gauche
    if (isLeftMove) {
      return new Triplet(isLeftMove, leftRes.getMinBlue(), leftRes.getMinOrange());
    } else {
      //Sinon, on renvoie le résultat du fils droit
      return new Triplet(isLeftMove, rightRes.getMinBlue(), rightRes.getMinOrange());
    }
  }

  // Calcule les valeurs Minimax de tout l'arbre.
  // En pratique, cette m�thode calcule pour chaque noeud de l'arbre un nouveau
  // Triplet repr�sentant les valeurs Minimax de chaque noeud.
  public void computeMinimaxValues() {
    // TODO
    if (this.isLeaf()) {
      // Cas de base : feuille → on stocke les points actuels du jeu
      minimaxValue = new Triplet(false, state.getBluePoints(), state.getOrangePoints());
    } else {
      // Étape 1 : on calcule récursivement les valeurs minimax des deux enfants
      leftChild.computeMinimaxValues();
      rightChild.computeMinimaxValues();

      // Étape 2 : on détermine la valeur minimax du nœud courant
      if (state.isBlueToPlay()) {
        minimaxValue = minBlue(leftChild.getMinimaxValue(), rightChild.getMinimaxValue());
      } else {
        minimaxValue = minOrange(leftChild.getMinimaxValue(), rightChild.getMinimaxValue());
      }
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
