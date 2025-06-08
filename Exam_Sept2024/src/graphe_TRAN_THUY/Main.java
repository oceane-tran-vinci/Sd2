package graphe_TRAN_THUY;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
	public static void main(String[] args) {
		File cities = new File("Exam_Sept2024/cities.txt");
		File roads = new File("Exam_Sept2024/roads.txt");
		Graph graph = new Graph(cities, roads);	
		HashMap<Ville,HashSet<Route>> listeAdjacence= graph.toListeDAdjacence();		
		System.out.println(listeAdjacence.size());
		System.out.println("-------------------");
		graph.bfs("Brussels");
		
	}

}
