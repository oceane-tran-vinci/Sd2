package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

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

  // *******************************************************
  // METHODES (à ajouter)
  // *******************************************************
  /*5 méthodes à prioriser */

  //Implémentez une méthode qui retourne la valeur maximale présente dans l’arbre.
  public int valeurMax() {
    int max = this.value;
    for (Tree enfant : children) {
      max = Math.max(max, enfant.valeurMax());
    }
    return max;
  }

  //Implémentez une méthode qui retourne un ensemble contenant les valeurs de toutes les feuilles de l’arbre.

  /**
   * Retourne un ensemble contenant les valeurs de toutes les feuilles de l’arbre.
   */
  public HashSet<Integer> valeursFeuilles() {
    HashSet<Integer> valeurs = new HashSet<>();
    if (this.isLeaf()) {
      valeurs.add(this.value);
    }
    for (Tree enfant : children) {
      valeurs.addAll(enfant.valeursFeuilles());
    }
    return valeurs;
  }

  //Implémentez une méthode qui retourne une map contenant, pour chaque valeur présente dans l’arbre, le nombre de fois où elle apparaît.

  /**
   * Retourne une map indiquant combien de fois chaque valeur apparaît dans l’arbre.
   */
  public HashMap<Integer, Integer> nbApparitionsParValeur() {
    HashMap<Integer, Integer> map = new HashMap<>();
    remplirMap(this, map);
    return map;
  }

  private void remplirMap(Tree node, HashMap<Integer, Integer> map) {
    if (node == null) {
      return;
    }
    map.put(node.value, map.getOrDefault(node.value, 0) + 1);
    for (Tree enfant : node.children) {
      remplirMap(enfant, map);
    }
  }

  //Implémentez une méthode qui retourne la profondeur de la première occurrence de la valeur x. »
  //(La racine est à profondeur 0. Retourne -1 si la valeur n’est pas trouvée.)
  public int profondeurValeur(int x) {
    return chercherProfondeur(this, x, 0); // Démarre la recherche à profondeur 0
  }

  private int chercherProfondeur(Tree node, int x, int niveau) {
    if (node.value == x) {
      return niveau; // Si on trouve la valeur, on retourne sa profondeur
    }

    for (Tree enfant : node.children) {
      int res = chercherProfondeur(enfant, x, niveau + 1); // Recherche dans les enfants
      if (res != -1) {
        return res; // Si trouvé dans un sous-arbre, on renvoie la profondeur
      }
    }

    return -1; // Si la valeur n’a pas été trouvée
  }

  // Implémentez une méthode qui retourne le chemin menant à la première occurrence d’un nœud ayant la valeur x.
  public List<Integer> cheminVers(int x) {
    List<Integer> chemin = new ArrayList<>(); // Liste du chemin trouvé
    if (trouverChemin(this, x, chemin)) {
      return chemin; // Si trouvé, on retourne le chemin
    }
    return new ArrayList<>(); // Sinon, on retourne une liste vide
  }

  private boolean trouverChemin(Tree node, int x, List<Integer> chemin) {
    chemin.add(node.value); // Ajoute le nœud courant au chemin

    if (node.value == x) {
      return true; // Si c’est la bonne valeur, on a trouvé
    }

    for (Tree enfant : node.children) {
      if (trouverChemin(enfant, x, chemin)) {
        return true; // Recherche récursive
      }
    }

    chemin.remove(chemin.size() - 1); // Backtrack si la valeur n’est pas trouvée dans ce sous-arbre
    return false;
  }


  /* -----------------------------------
   * 10 Autres méthodes : entrainement
   * ------------------------------------ */
  //Implémentez une méthode qui retourne la somme des valeurs des nœuds de l’arbre
  public int sommeDesValeurs() {
    int somme = this.value;
    for (Tree enfant : children) {
      somme += enfant.sommeDesValeurs();
    }
    return somme;
  }


  // Implémentez une méthode qui retourne le nombre de feuilles dans l’arbre.
  public int nbFeuilles() {
    if (this.isLeaf()) {
      return 1;
    }
    int total = 0;
    for (Tree enfant : children) {
      total += enfant.nbFeuilles();
    }
    return total;
  }


  //Compter les occurrences d’une valeur donnée.
  public int nbNoeudEgalA(int x) {
    int count = (this.value == x) ? 1 : 0;
    for (Tree enfant : children) {
      count += enfant.nbNoeudEgalA(x);
    }
    return count;
  }


  //Implémentez une méthode qui retourne true si la valeur x est présente dans l’arbre, false sinon.
  /**
   * Renvoie true si la valeur x existe dans l’arbre, false sinon.
   */
  public boolean existeValeur(int x) {
    if (this.value == x) {
      return true;
    }
    for (Tree enfant : children) {
      if (enfant.existeValeur(x)) {
        return true;
      }
    }
    return false;
  }


  //Implémentez une méthode qui retourne true si tous les enfants d’un nœud ont une valeur inférieure ou égale à leur parent.
  /**
   * Renvoie true si la valeur x existe dans l’arbre, false sinon.
   */
  public boolean tousInferieursOuEgaux() {
    for (Tree enfant : children) {
      if (enfant.value > this.value || !enfant.tousInferieursOuEgaux()) {
        return false;
      }
    }
    return true;
  }


  //Implémentez une méthode qui retourne le nombre total de niveaux de l’arbre
  public int nbNiveaux() {
    if (this.isLeaf()) {
      return 1;
    }
    int max = 0;
    for (Tree enfant : children) {
      max = Math.max(max, enfant.nbNiveaux());
    }
    return 1 + max;
  }


  //Implémentez une méthode qui retourne toutes les valeurs de l’arbre triées dans un TreeSet.
  public TreeSet<Integer> valeursTriees() {
    TreeSet<Integer> set = new TreeSet<>();
    remplirSetTrie(set);
    return set;
  }
  private void remplirSetTrie(TreeSet<Integer> set) {
    set.add(this.value);
    for (Tree enfant : children) {
      enfant.remplirSetTrie(set);
    }
  }


  //Vérifier l’uniformité par rapport à la racine.
  public boolean tousLesNoeudsOntLaMemeValeur() {
    return tousEgauxA(this.value);
  }

  private boolean tousEgauxA(int cible) {
    if (this.value != cible) {
      return false;
    }
    for (Tree enfant : children) {
      if (!enfant.tousEgauxA(cible)) {
        return false;
      }
    }
    return true;
  }


  //Implémentez une méthode qui retourne une map associant chaque valeur de l’arbre à sa profondeur dans l’arbre. Si une valeur apparaît plusieurs fois, on ne garde que la première profondeur.
  public HashMap<Integer, Integer> valeursAvecProfondeur() {
    HashMap<Integer, Integer> map = new HashMap<>(); // valeur → profondeur
    remplirValeursProfondeur(this, 0, map); // Lance la récursion à partir du niveau 0
    return map;
  }

  private void remplirValeursProfondeur(Tree node, int profondeur, HashMap<Integer, Integer> map) {
    // Si la valeur n’est pas encore enregistrée, on l’ajoute avec sa profondeur
    map.putIfAbsent(node.value, profondeur);

    // Appel récursif pour les enfants avec profondeur + 1
    for (Tree enfant : node.children) {
      remplirValeursProfondeur(enfant, profondeur + 1, map);
    }
  }


  //Implémentez une méthode qui retourne le nombre de valeurs différentes dans l’arbre.
  public int nbValeursDifferentes() {
    HashSet<Integer> set = new HashSet<>(); // Contiendra les valeurs uniques
    remplirSetValeurs(set); // Remplit le set avec toutes les valeurs
    return set.size(); // Retourne le nombre de valeurs différentes
  }

  private void remplirSetValeurs(HashSet<Integer> set) {
    set.add(this.value); // Ajoute la valeur du nœud courant
    for (Tree enfant : children) {
      enfant.remplirSetValeurs(set); // Récursion pour chaque enfant
    }
  }



  public static void main(String[] args) {
    Tree l6 = new Tree(6);
    Tree l1 = new Tree(1);
    Tree t9 = new Tree(9, new Tree[]{l6, l1});
    Tree l3 = new Tree(3);
    Tree l7 = new Tree(7);
    Tree t8 = new Tree(8, new Tree[]{l3, l7});
    Tree l4 = new Tree(4);
    Tree t1 = new Tree(1, new Tree[]{t8, t9, l4});
    //AJOUTER LES SOUT DES MÉTHODES
    // -------- valeurMax()
    System.out.println("valeurMax = " + t1.valeurMax()); // attendu: 9
    System.out.println("--------------------------");

    // -------- valeursFeuilles()
    // (affichage trié pour stabilité visuelle)
    System.out.println("valeursFeuilles = " + new java.util.TreeSet<>(t1.valeursFeuilles()));
    // attendu: [1, 3, 4, 6, 7]
    System.out.println("--------------------------");

    // -------- nbApparitionsParValeur()
    System.out.println("nbApparitionsParValeur = " + t1.nbApparitionsParValeur());
    // attendu (ordre non garanti): {1=2, 3=1, 4=1, 6=1, 7=1, 8=1, 9=1}
    System.out.println("--------------------------");

    // -------- profondeurValeur(int x)
    System.out.println("profondeurValeur(7) = " + t1.profondeurValeur(7)); // attendu: 2
    System.out.println("profondeurValeur(1) = " + t1.profondeurValeur(1)); // attendu: 0 (la racine)
    System.out.println("profondeurValeur(5) = " + t1.profondeurValeur(5)); // attendu: -1 (absent)
    System.out.println("--------------------------");

    // -------- cheminVers(int x)
    System.out.println("cheminVers(7) = " + t1.cheminVers(7));             // attendu: [1, 8, 7]
    System.out.println("cheminVers(6) = " + t1.cheminVers(6));             // attendu: [1, 9, 6]
    System.out.println("cheminVers(1) = " + t1.cheminVers(1));             // attendu: [1]
    System.out.println("cheminVers(5) = " + t1.cheminVers(5));             // attendu: []
    System.out.println("--------------------------");

    /*10 autres méthodes */
    System.out.println(" ");
    System.out.println(" ");
    System.out.println("10 Autres méthodes : entrainement");
    System.out.println("--------------------------");
    System.out.println("sommeDesValeurs = " + t1.sommeDesValeurs());           // attendu: 39
    System.out.println("--------------------------");
    System.out.println("nbFeuilles = " + t1.nbFeuilles());                     // attendu: 5
    System.out.println("--------------------------");

    System.out.println("nbNoeudEgalA(1) = " + t1.nbNoeudEgalA(1));             // attendu: 2
    System.out.println("nbNoeudEgalA(5) = " + t1.nbNoeudEgalA(5));             // attendu: 0
    System.out.println("--------------------------");

    System.out.println("existeValeur(7) = " + t1.existeValeur(7));             // attendu: true
    System.out.println("existeValeur(5) = " + t1.existeValeur(5));             // attendu: false
    System.out.println("--------------------------");

    System.out.println("tousInferieursOuEgaux = " + t1.tousInferieursOuEgaux());// attendu: false
    System.out.println("--------------------------");

    System.out.println("nbNiveaux = " + t1.nbNiveaux());                       // attendu: 3
    System.out.println("--------------------------");

    System.out.println("valeursTriees = " + t1.valeursTriees());               // attendu: [1, 3, 4, 6, 7, 8, 9]
    System.out.println("--------------------------");

    System.out.println("tousLesNoeudsOntLaMemeValeur = " + t1.tousLesNoeudsOntLaMemeValeur());
    // attendu: false
    System.out.println("--------------------------");

    System.out.println("valeursAvecProfondeur = " + t1.valeursAvecProfondeur());
    // attendu (ordre non garanti): {1=0, 8=1, 3=2, 7=2, 9=1, 6=2, 4=1}
    System.out.println("--------------------------");

    System.out.println("nbValeursDifferentes = " + t1.nbValeursDifferentes()); // attendu: 7
  }
}
