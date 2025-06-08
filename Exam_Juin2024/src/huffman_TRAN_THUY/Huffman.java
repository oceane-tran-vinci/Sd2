package huffman_TRAN_THUY;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Huffman {

	private static class Node implements Comparable<Node> {
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

		@Override
		public int compareTo(Node that) {
			return this.freq - that.freq;
		}

	}
	
	// renvoie une map qui a comme cl� les lettres de la chaine de 
	// caract�re donn�e en param�tre et comme valeur la fr�quence de 
	// cette lettre dans cette chaine de caract�re
	public static Map<Character, Integer> computeFreq(String s) {
		char[] input = s.toCharArray();
		Map<Character, Integer> freq = new HashMap<Character, Integer>();
		for (int i = 0; i < input.length; i++) {
			int oldFreq = 0;
			if (freq.get(input[i]) != null) {
				oldFreq = freq.get(input[i]);
			}
			freq.put(input[i], oldFreq + 1);
		}
		return freq;
	}	
	
	// renvoie l'arbre de Huffman obtenu gr�ce � la map des fr�quences des lettres 
	public static Node buildTree(Map<Character, Integer> freq) {
		//TODO
		//Création d'une file de priorité (min-heap) pour trier les nœuds selon leur fréquence
		PriorityQueue<Node> p = new PriorityQueue<Node>();

		Set<Character> characters = freq.keySet();// Récupère tous les caractères de la map des fréquences
		// Pour chaque caractère, crée un nœud feuille et l'ajoute à la file
		for (Character c : characters) {
			// Pour chaque caractère, on crée une feuille (nœud sans enfant) et on l’ajoute dans la file
			p.add(new Node(c, freq.get(c), null, null));
		}

		// Tant qu’il y a au moins deux nœuds dans la file
		while (p.size() > 1) {
			// On retire les deux nœuds avec les plus petites fréquences
			Node x = p.poll();  // le plus petit
			Node y = p.poll();  // le deuxième plus petit

			// On crée un nouveau nœud parent (caractère vide '\0') avec les deux nœuds comme enfants
			// La fréquence du parent est la somme des fréquences des enfants
			Node parent = new Node('\0', x.freq + y.freq, x, y);
			p.add(parent); // On remet ce nœud dans la file pour qu’il soit à son tour utilisé plus tard
		}

		// À la fin, il ne reste qu’un seul nœud dans la file : la racine de l’arbre de Huffman
		return p.poll();
	}
	
	// renvoie une map qui associe chaque lettre � son code. Ce code est obtenu
	// en parcourant l'arbre de Huffman donn� en param�tre
	public static Map<Character, String> buildCode(Node root) {
		Map<Character, String> m = new HashMap<Character, String>();
		buildCode(m, root, "");
		return m;
	}

	public static void buildCode(Map<Character, String> m, Node x, String s) {
		if (x.isLeaf()) {
			m.put(x.ch, s);
			return;
		}
		buildCode(m, x.left, s + '0');
		buildCode(m, x.right, s + '1');
	}
	
	// encode la chaine de caract�re prise en param�tre en une chaine de 
	// bit 0 et 1 en utilisant la map des codes codeMap
	public static String compress(String s, Map<Character, String> codeMap) {
		char[] input = s.toCharArray();
		StringBuffer toReturn = new StringBuffer("");
		// encode
		for (int i = 0; i < input.length; i++) {
			String code = codeMap.get(input[i]);
			toReturn.append(code);
		}
		return toReturn.toString();
	}
	
	// Cette m�thode d�code une chaine de 0 et de 1 cod� � l'aide de l'algorithme de Huffman
	// En param�tre, en plus de la chaine � d�coder, est sp�cifi� la racine de l'arbre de 
	// Huffman 
	public static String expand(Node root, String t) {
		StringBuffer s = new StringBuffer("");
		char[] b = t.toCharArray();
		int i=0;
		int length=root.freq;
		for (int j=0; j<length;j++) {
			Node x = root;
			while (!x.isLeaf()) {
				if (b[i] == '1') {
					x = x.right;
				} else {
					x = x.left;
				}
				i++;
			}
			s = s.append(x.ch);
		}
		return s.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String s= HuffmanReadFile.loadFile(new File("Exam_Juin2024/11-0.txt"));
		Map<Character, Integer> freq = computeFreq(s);
		Node root = buildTree(freq);
		Map<Character, String> code= buildCode(root);
		String compress = compress(s, code);
		HuffmanWriteFile.write("Exam_Juin2024/fichier_compresse",compress);
		String texteOriginal = expand(root, HuffmanReadFile.read("Exam_Juin2024/fichier_compresse"));
		System.out.println(texteOriginal);
	}
}
