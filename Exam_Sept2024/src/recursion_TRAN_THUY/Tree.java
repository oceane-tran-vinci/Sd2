package recursion_TRAN_THUY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Tree implements Iterable<Tree> {

	private int value;

	private Tree parent;

	private Tree[] children;

	// *******************************************************
	// CONSTRUCTEURS
	// *******************************************************
	public Tree(int v, Tree[] chd) {
		value = v;
		children = chd;

		for (Tree child : chd) {
			child.parent = this;
		}
	}

	public Tree(int v) {
		this(v, new Tree[0]);
	}

	// *******************************************************
	// GETTERS
	// *******************************************************
	public int getValue() {
		return value;
	}

	public Tree getParent() {
		return parent;
	}

	public Tree[] getChildren() {
		return children;
	}

	// *******************************************************
	// ITERATEUR
	// *******************************************************
	public Iterator<Tree> iterator() {
		return Arrays.asList(children).iterator();
	}

	public int nbrChildren() {
		return children.length;
	}

	public boolean isLeaf() {
		return children.length == 0;
	}

	// Renvoie vrai si tous les noeuds ont la m�me valeur, false sinon.
	/*VERSION 1 (appelle de la méthode dans la méthode)*/
	public boolean tousLesNoeudsOntLaMemeValeur() {
		//TODO
		//On parcourt tous les enfants du nœud courant
		for (Tree enfant : children) {
			// Si un enfant a une valeur différente de celle du parent → l'arbre ne respecte pas la condition
			if (enfant.value != this.value) return false;
			// Appel récursif : on vérifie que le sous-arbre de cet enfant respecte aussi la condition
			if (!enfant.tousLesNoeudsOntLaMemeValeur()) return false;
		}
		// Si tous les enfants (et sous-arbres) ont la même valeur, on retourne true
		return true;
	}

	// Renvoie vrai si tous les noeuds ont la m�me valeur, false sinon.
	/*VERSION 2 (en méthode)
	public boolean tousLesNoeudsOntLaMemeValeur() {
    return tousLesNoeudsOntLaMemeValeur(this.value); // Appel initial : on passe la valeur de la racine comme référence
	}

	private boolean tousLesNoeudsOntLaMemeValeur(int reference) {
			// Si un enfant a une valeur différente → false
			if (this.value != reference) {
					return false;
			}
			// Vérifie récursivement tous les enfants
			for (Tree enfant : children) {
					if (!enfant.tousLesNoeudsOntLaMemeValeur(reference)) {
							return false;
					}
			}
			return true;
	}
	*/

	//TODO
	//Renvoie tous les niveaux d'une valeur donn�e
	public HashSet<Integer> niveauxDeLaValeur(int valeur) {
		HashSet<Integer> niveaux = new HashSet<>(); // Crée un HashSet vide pour stocker les niveaux où la valeur apparaît
		remplirNiveaux(this, valeur, 0, niveaux); // Appelle la méthode récursive pour remplir le set, en partant du niveau 0
		return niveaux; // Retourne le set des niveaux trouvés
	}

	private void remplirNiveaux(Tree node, int valeur, int niveau, HashSet<Integer> niveaux) {
		if (node == null) return; // Si le nœud est null (cas de sécurité), on ne fait rien

		// Si la valeur du nœud correspond à la valeur, on ajoute le niveau
		if (node.value == valeur) {
			niveaux.add(niveau);
		}

		// On parcourt récursivement les enfants du nœud avec niveau + 1
		for (Tree enfant : node.children) {
			remplirNiveaux(enfant, valeur, niveau + 1, niveaux);
		}
	}

	public static void main(String[] args) {
		Tree l6 = new Tree(6);
		Tree l1 = new Tree(1);
		Tree t9 = new Tree(9, new Tree[] { l6, l1 });
		Tree l3 = new Tree(3);
		Tree l7 = new Tree(7);
		Tree t8 = new Tree(8, new Tree[] { l3, l7 });
		Tree l4 = new Tree(4);
		Tree t1 = new Tree(1, new Tree[] { t8, t9, l4 });
		System.out.println(t1.tousLesNoeudsOntLaMemeValeur());
		System.out.println(t1.niveauxDeLaValeur(1));
	}
}
