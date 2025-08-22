package graphe;

import java.util.Map;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    Graph graph = new Graph("Preparation_examen_Vide/artists.txt",
        "Preparation_examen_Vide/mentions.txt");
    graph.trouverCheminLePlusCourt("The Beatles", "Kendji Girac");
    System.out.println("--------------------------");
    graph.trouverCheminMaxMentions("The Beatles", "Kendji Girac");

    System.out.println("--------------------------");
    graph.bfs("The Beatles");

  }
}
