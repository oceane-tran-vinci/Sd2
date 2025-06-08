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
		//TO DO
		return false;
	}
	
	// Cette m�thode renvoie une HashMap contenant le nombre de n�uds pour chaque niveau de l'arbre.
	// Le niveau de la racine est consid�r� comme le niveau 0.
	public HashMap<Integer, Integer> nbNoeudsParNiveau() {
		//TO DO
		return null;
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

