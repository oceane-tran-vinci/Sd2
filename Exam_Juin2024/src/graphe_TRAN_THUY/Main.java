package graphe_TRAN_THUY;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		File cities = new File("Exam_Juin2024/cities.txt");
		File roads = new File("Exam_Juin2024/roads.txt");
		Graph graph = new Graph(cities, roads);
		graph.bfs("Brussels");
	}

}
