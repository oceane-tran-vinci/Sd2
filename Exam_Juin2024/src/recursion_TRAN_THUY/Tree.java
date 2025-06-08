package recursion_TRAN_THUY;

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

	// Cette m�thode renvoie true si la valeur de chaque n�ud est strictement sup�rieure � la valeur de tous ses enfants, 
	// sinon elle renvoie false.
	public boolean parentPlusGrandQueSesEnfants() {
		//TODO
		//On parcourt tous les enfants directs de ce nœud
		for (Tree enfant : children) {
			// Si un enfant a une valeur >= au parent → retourne false
			if (enfant.getValue() >= this.value) {
				return false; // On arrête directement
			}
			// Appelle récursivement la méthode sur les sous-arbres
			if (!enfant.parentPlusGrandQueSesEnfants()) {
				return false; // Si un sous-arbre ne respecte pas la règle → on arrête
			}
		}
		return true; // Si tous les enfants et sous-arbres respectent la règle, on retourne true
	}
	
	// Cette m�thode renvoie une HashMap contenant le nombre de n�uds pour chaque niveau de l'arbre.
	// Le niveau de la racine est consid�r� comme le niveau 0.
	public HashMap<Integer, Integer> nbNoeudsParNiveau() {
		//TODO
		HashMap<Integer, Integer> map = new HashMap<>();//Crée une map vide pour stocker le nombre de nœuds par niveau
		remplirMapParNiveau(this, 0, map);// Lance le remplissage de la map à partir de ce nœud (racine), niveau 0
		return map;// Retourne la map remplie
	}

	private void remplirMapParNiveau(Tree node, int niveau, HashMap<Integer, Integer> map) {
		if (node == null) return;// Cas de base : si le nœud est null, on ne fait rien

		// Incrémente le compteur pour ce niveau :
		// si la clé existe déjà → +1, sinon → on démarre à 1
		map.put(niveau, map.getOrDefault(niveau, 0) + 1);

		// Appelle récursivement la méthode sur chaque enfant avec niveau+1
		for (Tree enfant : node.getChildren()) {
			remplirMapParNiveau(enfant, niveau + 1, map);
		}
	}



	public static void main(String[] args) {
		Tree l6 = new Tree(6);
		Tree l10 = new Tree(10);
		Tree t9 = new Tree(9, new Tree[] { l6, l10 });
		Tree l3 = new Tree(3);
		Tree l7 = new Tree(7);
		Tree t8 = new Tree(8, new Tree[] { l3, l7 });
		Tree l4 = new Tree(4);
		Tree t11 = new Tree(11, new Tree[] { t8, t9, l4});
		System.out.println(t11.parentPlusGrandQueSesEnfants());
		Tree l6b = new Tree(6);
		Tree l1b = new Tree(1);
		Tree t9b = new Tree(9, new Tree[] { l6b, l1b });
		Tree l3b = new Tree(3);
		Tree l7b = new Tree(7);
		Tree t8b = new Tree(8, new Tree[] { l3b, l7b });
		Tree l4b = new Tree(4);
		Tree t11b = new Tree(11, new Tree[] { t8b, t9b, l4b});
		System.out.println(t11b.parentPlusGrandQueSesEnfants());
		System.out.println(t11.nbNoeudsParNiveau());
	}
}

