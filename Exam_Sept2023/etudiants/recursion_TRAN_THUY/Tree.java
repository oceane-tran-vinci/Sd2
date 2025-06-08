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

	// renvoie le nombre de fois que la valeur en parametre apparait dans l�arbre
	public int nbNoeudEgalA(int x) {
		//TODO
		int cpt = 0; // Compteur pour le nombre d'occurrences de x

		// Si la valeur du noeud courant est égale à x, on incrémente le compteur
		if (this.value == x) {
			cpt++;
		}

		// Pour chaque enfant de ce noeud...
		for (Tree tree : children) {
			// ...on ajoute récursivement les occurrences de x dans ses sous-arbres
			cpt += tree.nbNoeudEgalA(x);
		}
		return cpt; // Retourne le total trouvé
	}
	
	// Renvoie un HashSet contenant les entiers presents dans l'arbre, sans doublons
	public HashSet<Integer> toSet() {
		//TODO
		HashSet<Integer> set = new HashSet<>(); // Crée un ensemble vide
		set.add(this.value); // Ajoute la valeur du noeud courant

		// Pour chaque enfant...
		for (Tree child : children) {
			// ...on ajoute récursivement tous les éléments du sous-arbre de cet enfant
			set.addAll(child.toSet());
		}
		return set; // Retourne l’ensemble contenant toutes les valeurs uniques
	}

	public static void main(String[] args) {
		Tree l1 = new Tree(3);
		Tree l2 = new Tree(3);
		Tree l3 = new Tree(5);
		Tree l4 = new Tree(6);
		Tree t3 = new Tree(3, new Tree[] { l2, l3, l4 });
		Tree t2 = new Tree(1, new Tree[] { l1 });
		Tree t1 = new Tree(3, new Tree[] { t2, t3 });
		System.out.println(t1.nbNoeudEgalA(3));
		System.out.println("------------");
		System.out.println(t1.toSet());
	}
}
