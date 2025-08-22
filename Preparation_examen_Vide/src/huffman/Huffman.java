package huffman;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
	// renvoie une map qui a comme cl� les lettres de la chaine de
	// caract�re donn�e en param�tre et comme valeur la fr�quence de
	// cette lettre dans cette chaine de caract�re
	public static Map<Character, Integer> computeFreq(String s) {
		//TODO
		return null;
	}


	/***********************************************************
	 M�thode buildTree
	 ***********************************************************/
	// renvoie l'arbre de Huffman obtenu gr�ce � la map des fr�quences des lettres
	public static Node buildTree(Map<Character, Integer> freq) {
		//TODO
		return null;
	}

	/***********************************************************
	 M�thode buildCode
	 ***********************************************************/
	// renvoie une map qui associe chaque lettre � son code. Ce code est obtenu
	// en parcourant l'arbre de Huffman donn� en param�tre
	public static Map<Character, String> buildCode(Node root) {
		//TODO
		return null;
	}

	/***********************************************************
	 M�thode compress
	 ***********************************************************/
	// encode la chaine de caract�re prise en param�tre en une chaine de
	// bit 0 et 1 en utilisant la map des codes codeMap
	public static String compress(String s, Map<Character, String> codeMap) {
		//TODO
		return null;
	}

	/***********************************************************
	 M�thode expand
	 ***********************************************************/
	// Cette m�thode d�code une chaine de 0 et de 1 cod� � l'aide de l'algorithme de Huffman
	// En param�tre, en plus de la chaine � d�coder, est sp�cifi� la racine de l'arbre de
	// Huffman
	public static String expand(Node root, String t) {
		//TODO
		return null;
	}

	/*MAIN GLOBAL*/
	public static void main(String[] args) throws IOException {
		String s= HuffmanReadFile.loadFile(new File("Preparation_Juin2025_Vide/11-0.txt"));
		Map<Character, Integer> freq = computeFreq(s);
		Node root = buildTree(freq);
		Map<Character, String> code= buildCode(root);
		String compress = compress(s, code);
		HuffmanWriteFile.write("Preparation_Juin2025_Vide/fichier_compresse",compress);
		String texteOriginal =
				expand(root, HuffmanReadFile.read("Preparation_Juin2025_Vide/fichier_compresse"));
		System.out.println(texteOriginal);
	}
}
