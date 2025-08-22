package recursion_TRAN_THUY;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
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

	// Renvoie true si toutes les feuilles de l'arbre sont positives, false sinon.
	//TODO
	public boolean feuillesPositives() {
		// Cas de base : une feuille doit être > 0
		if (this.isLeaf()) return this.value > 0;

		// Sinon : toutes les feuilles des sous-arbres doivent être positives
		for (Tree child : this.children) {
			if (!child.feuillesPositives()) return false;
		}
		return true;
	}

	// Renvoie un map qui a chaque entier n, associe toutes les valeurs de l'arbre se trouvant au niveau n
	//TODO
	public  HashMap<Integer, ArrayDeque<Integer>> niveaux(){
		HashMap<Integer, ArrayDeque<Integer>> map = new HashMap<>();
		remplirNiveaux(this, 0, map);
		return map;
	}

	//Méthode récursive
	private void remplirNiveaux(Tree node, int niveau, HashMap<Integer, ArrayDeque<Integer>> map) {
		//AVEC computeIfAbsent
		map.computeIfAbsent(niveau, k -> new ArrayDeque<>()).add(node.value);

		//SANS computeIfAbsent
    /*
    if (!map.containsKey(niveau)) {
        map.put(niveau, new ArrayDeque<Integer>());
    }
    map.get(niveau).add(node.value);
		*/

		for (Tree enfant : node.children) {
			remplirNiveaux(enfant, niveau + 1, map);
		}
	}

	public static void main(String[] args) {
		Tree l6 = new Tree(1);
		Tree l1 = new Tree(1);
		Tree t9 = new Tree(-9, new Tree[] { l6, l1 });
		Tree l3 = new Tree(3);
		Tree l7 = new Tree(7);
		Tree t8 = new Tree(8, new Tree[] { l3, l7 });
		Tree l4 = new Tree(4);
		Tree t1 = new Tree(-1, new Tree[] { t8, t9, l4 });
		System.out.println(t1.feuillesPositives());
		System.out.println(t1.niveaux());
	}
}
