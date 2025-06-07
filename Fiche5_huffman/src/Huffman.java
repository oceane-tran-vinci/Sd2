import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Huffman {

  private static class Node {

    private char ch;
    private int freq;
    private final Node left, right;

    public Node(char ch, int freq, Node left, Node right) {
      this.ch = ch;
      this.freq = freq;
      this.left = left;
      this.right = right;
    }

    public boolean isLeaf() {
      return left == null && right == null;
    }

  }

  /***********************************************************
  M�thode computeFreq
   ***********************************************************/

//  //TODO : ajoutet main
  // main pour tester la m�thode computeFreq
//  public static void main(String[] args) {
//    String s = "Bonjour! Au revoir!";
//    Map<Character, Integer> freq = computeFreq(s);
//    System.out.println(freq);
//  }

  // renvoie une map qui a comme cl� les lettres de la chaine de
  // caract�re donn�e en param�tre et comme valeur la fr�quence de
  // ces lettres
  public static Map<Character, Integer> computeFreq(String s) {
    Map<Character, Integer> count = new HashMap<>();
    for (Character currentChar : s.toCharArray()) {
      if (!count.containsKey(currentChar)) {
        count.put(currentChar, 0);
      }
      count.put(currentChar, count.get(currentChar) + 1);
    }
    return count;
  }

  /***********************************************************
   M�thode buildTree
   ***********************************************************/
  //TODO : ajoutet main
  // main pour tester la m�thode buildTree
//  public static void main(String[] args) {
//    String s="Bonjour! Au revoir!";
//    Map<Character, Integer> freq = computeFreq(s);
//    Node root = buildTree(freq);
//    System.out.println("Nbre de lettre dans la chaine de caract�re � encoder: "+ root.freq );
//    System.out.println("Fr�quence des lettres dans le sous-arbre de gauche: "+ root.left.freq );
//    System.out.println("Fr�quence des lettres dans le sous-arbre de droite: "+ root.right.freq );
//  }

  // renvoie l'arbre de Huffman obtenu � partir de la map des fr�quences des lettres
  public static Node buildTree(Map<Character, Integer> freq) {
    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing(a -> a.freq));
    //on veut aller chercher les fr�quences des lettres dans la map du param�tre donc doit aller chercher dans Map.Entry
    for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
      queue.add(new Node(entry.getKey(), entry.getValue(), null, null));
    }

    while (queue.size() > 1){
      Node left = queue.poll();
      Node right = queue.poll();
      queue.add(new Node('\0', left.freq + right.freq, left, right));
    }
    return queue.poll();
  }


  /***********************************************************
   M�thode buildCode
   ***********************************************************/
  //TODO : ajoutet main
  // main pour tester la m�thode buildCode
  public static void main(String[] args) {
    String s="Bonjour! Au revoir!";
    Map<Character, Integer> freq = computeFreq(s);
    Node root = buildTree(freq);
    Map<Character, String> code= buildCode(root);
    System.out.println(code);
  }

  // renvoie une map qui associe chaque lettre � son code (suite de 0 et de 1).
  // Ce code est obtenu en parcourant l'arbre de Huffman donn� en param�tre
  public static Map<Character, String> buildCode(Node root) {

    return null;
  }

  /***********************************************************
   M�thode compress
   ***********************************************************/
  //TODO : ajoutet main
  // main pour tester la m�thode compress
//  public static void main(String[] args) {
//    String s="Bonjour! Au revoir!";
//    Map<Character, Integer> freq = computeFreq(s);
//    Node root = buildTree(freq);
//    Map<Character, String> code= buildCode(root);
//    String compress = compress(s, code);
//    System.out.println(compress);
//  }

  // encode la chaine de caract�re prise en param�tre en une chaine de
  // bit (0 et 1) en utilisant la map des codes codeMap
  public static String compress(String s, Map<Character, String> codeMap) {
    return null;
  }

  /***********************************************************
   M�thode expand
   ***********************************************************/
  //TODO : ajoutet main
  // main pour tester la m�thode expand
//  public static void main(String[] args) {
//    String s="Bonjour! Au revoir!";
//    Map<Character, Integer> freq = computeFreq(s);
//    Node root = buildTree(freq);
//    Map<Character, String> code= buildCode(root);
//    String compress = compress(s, code);
//    String texteOriginal = expand(root,compress);
//    System.out.println(texteOriginal);}

  // Cette m�thode d�code une chaine de 0 et de 1 cod� � l'aide de l'algorithme de Huffman
  // En param�tre, en plus de la chaine � d�coder, est sp�cifi� la racine de l'arbre de
  // Huffman
  public static String expand(Node root, String t) {
    return null;
  }

}
