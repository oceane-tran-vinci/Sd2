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

	/***********************************************************
	 M�thode computeFreq
	 ***********************************************************/
	// renvoie une map qui a comme cl� les lettres de la chaine de 
	// caract�re donn�e en param�tre et comme valeur la fr�quence de 
	// cette lettre dans cette chaine de caract�re
	public static Map<Character, Integer> computeFreq(String s) {
		//TODO
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

	/***********************************************************
	 M�thode buildTree
	 ***********************************************************/
	// renvoie l'arbre de Huffman obtenu gr�ce � la map des fr�quences des lettres 
	public static Node buildTree(Map<Character, Integer> freq) {
		//TODO
		PriorityQueue<Node> p = new PriorityQueue<Node>();
		Set<Character> characters = freq.keySet();
		for (Character c : characters) {
			p.add(new Node(c, freq.get(c), null, null));
		}
		while (p.size() > 1) {
			Node x = p.poll();
			Node y = p.poll();
			Node parent = new Node('\0', x.freq + y.freq, x, y);
			p.add(parent);
		}
		return p.poll();
	}

	/***********************************************************
	 M�thode buildCode
	 ***********************************************************/
	// renvoie une map qui associe chaque lettre � son code. Ce code est obtenu
	// en parcourant l'arbre de Huffman donn� en param�tre
	public static Map<Character, String> buildCode(Node root) {
		//TODO
		//On prépare une Map vide pour stocker les associations caractère → code binaire
		Map<Character, String> codeMap = new HashMap<>();
		buildCode(codeMap, root, ""); // On lance la version récursive avec une chaîne vide au départ
		return codeMap; // On retourne la map complète
	}

	//A Ajouter (récursion)
	// Méthode récursive utilisée pour remplir la map des codes Huffman
	private static void buildCode(Map<Character, String> codeMap, Node node, String t) {
		// Si on est sur une feuille → c’est un caractère (pas un nœud intermédiaire)
		if (node.isLeaf()) {
			codeMap.put(node.ch, t); // On ajoute ce caractère dans la map avec le chemin binaire actuel
			return;
		} else { // Sinon, on continue à parcourir les deux branches :
			buildCode(codeMap, node.left, t + 0); // gauche → on ajoute '0' au code
			buildCode(codeMap, node.right, t + '1'); // droite → on ajoute '1' au code
		}
	}

	/***********************************************************
	 M�thode compress
	 ***********************************************************/
	// encode la chaine de caract�re prise en param�tre en une chaine de 
	// bit 0 et 1 en utilisant la map des codes codeMap
	public static String compress(String s, Map<Character, String> codeMap) {
		//TODO
		char[] input = s.toCharArray();
		StringBuffer toReturn = new StringBuffer("");
		// encode
		for (int i = 0; i < input.length; i++) {
			String code = codeMap.get(input[i]);
			toReturn.append(code);
		}
		return toReturn.toString();
	}

	/***********************************************************
	 M�thode expand
	 ***********************************************************/
	// Cette m�thode d�code une chaine de 0 et de 1 cod� � l'aide de l'algorithme de Huffman
	// En param�tre, en plus de la chaine � d�coder, est sp�cifi� la racine de l'arbre de 
	// Huffman 
	public static String expand(Node root, String t) {
		//TODO
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

	/*MAIN GLOBAL*/
	public static void main(String[] args) throws IOException {
		String s= HuffmanReadFile.loadFile(new File("Exam_Juin2023/11-0.txt"));
		Map<Character, Integer> freq = computeFreq(s);
		Node root = buildTree(freq);
		Map<Character, String> code= buildCode(root);
		String compress = compress(s, code);
		HuffmanWriteFile.write("Exam_Juin2023/fichier_compresse",compress);
		String texteOriginal =
		expand(root, HuffmanReadFile.read("Exam_Juin2023/fichier_compresse"));
		System.out.println(texteOriginal);
	}
}
