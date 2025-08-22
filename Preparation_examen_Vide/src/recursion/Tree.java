package recursion;

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

  // *******************************************************
  // METHODES (à ajouter)
  // *******************************************************



  public static void main(String[] args) {
    Tree l6 = new Tree(6);
    Tree l1 = new Tree(1);
    Tree t9 = new Tree(9, new Tree[] { l6, l1 });
    Tree l3 = new Tree(3);
    Tree l7 = new Tree(7);
    Tree t8 = new Tree(8, new Tree[] { l3, l7 });
    Tree l4 = new Tree(4);
    Tree t1 = new Tree(1, new Tree[] { t8, t9, l4 });
    //AJOUTER LES SOUT DES MÉTHODES
  }
}
