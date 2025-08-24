package arbre_TRAN_THUY;

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

	// Cette m�thode renvoie une copie de this c�d une nouvelle instance de Tree ayant
	// les m�mes �l�ments au m�me endroit que l'objet courant
	public Tree clone() {
		//TODO
		//On crée un nouveau tableau pour stocker les clones des enfants
		Tree[] newChildren = new Tree[children.length];

		// Pour chaque enfant du nœud courant, on crée une copie récursive
		for (int i = 0; i < children.length; i++) {
			newChildren[i] = children[i].clone(); // appel récursif pour cloner l’enfant
		}

		// On retourne un nouveau nœud avec la même valeur et les enfants clonés
		// Le constructeur remettra automatiquement les bons liens parent/enfant
		return new Tree(value, newChildren);
	}

	//TODO
	// Cette m�thode imprime � la sortie standard tous les noeuds 
	// suivi de leurs anc�tres jusqu�� la racine.

	/*VERSION 1 (en 2 méthodes) */
	public void afficherNoeudsAvecAncetres() {
		afficherNoeudsAvecAncetresRec(this); // On lance la récursion à partir du nœud courant (this)
	}

	// Méthode récursive qui affiche les ancêtres d’un nœud donné
	private void afficherNoeudsAvecAncetresRec(Tree t) {
		// On commence à partir du nœud t et on remonte jusqu’à la racine
		Tree current = t;
		while (current != null) {
			System.out.print(current.getValue() + " "); // Affiche la valeur du nœud courant
			current = current.getParent(); // Remonte d’un niveau dans l’arbre

		}
		System.out.println(); // Nouvelle ligne après chaque nœud traité

		// Appel récursif sur chacun des enfants de t
		for (Tree child : t.getChildren()) {
			afficherNoeudsAvecAncetresRec(child);
		}
	}

	/*VERSION 2 (en 1 méthode)
	public void afficherNoeudsAvecAncetres() {
		//TODO
		// Si le nœud courant est la racine (il n’a pas de parent)
		if (this.getParent() == null) {
			System.out.println(this.value); // On affiche juste sa propre valeur
		} else {
			// Sinon, on remonte jusqu’à la racine en affichant la lignée
			Tree parent = this.getParent();
			System.out.print(this.value + " " + parent.value); // Affiche le nœud et son parent immédiat

			// Continue à remonter jusqu’à la racine
			while (parent.getParent() != null) {
				parent = parent.getParent();
				System.out.print(" " + parent.value);  // Affiche les ancêtres suivants
			}
			System.out.println(); // Fin de la ligne pour ce nœud
		}

		// Appel récursif sur tous les enfants du nœud courant
		for (Tree child : children) {
			child.afficherNoeudsAvecAncetres();
		}
	}
	*/
	

	public static void main(String[] args) {
		Tree l6 = new Tree(6);
		Tree l1 = new Tree(1);
		Tree t9 = new Tree(9, new Tree[] { l6, l1 });
		Tree l3 = new Tree(3);
		Tree l8 = new Tree(8);
		Tree t8 = new Tree(8, new Tree[] { l3, l8 });
		Tree l4 = new Tree(4);
		Tree t4 = new Tree(4, new Tree[] { t8, t9, l4});
		Tree t4bis= t4.clone();
		t4.afficherNoeudsAvecAncetres();
		System.out.println("-----------");
		t4bis.afficherNoeudsAvecAncetres();
	}
}

