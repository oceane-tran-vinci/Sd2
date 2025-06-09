package graphe;

import java.util.Map;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Graph graph = new Graph("Preparation_Juin2025_Vide/artists.txt", "Preparation_Juin2025_Vide/mentions.txt");
//    graph.trouverCheminLePlusCourt("The Beatles", "Kendji Girac");
//    System.out.println("--------------------------");
//    graph.trouverCheminMaxMentions("The Beatles", "Kendji Girac");

    /*AJOUTER pour examen !!!
    * A décommenter et tester un par un sinon terminal très long */
//    System.out.println("--------------------------");
//    graph.bfs("The Beatles");

//    System.out.println("--------------------------");
//    graph.cheminMinimisantNombreDeMentions("The Beatles", "Kendji Girac");

//    System.out.println("--------------------------");
//    graph.mentionsEntrantes("Kendji Girac").forEach(System.out::println);

//    System.out.println("--------------------------");
//    Map<Artist, Set<Artist>> listeAdjacence = graph.toListeDAdjacence();
//    System.out.println(listeAdjacence.size());
  }
}
