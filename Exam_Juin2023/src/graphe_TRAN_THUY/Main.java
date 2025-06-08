package graphe_TRAN_THUY;

import java.io.File;

public class Main {

  public static void main(String[] args) {
    try {
      File lignes = new File("Exam_Juin2023/lignes.txt");
      File troncons = new File("Exam_Juin2023/troncons.txt");
      Graph g = new Graph(lignes, troncons);
      g.tronconsEntrants(g.getStation("ALMA")).forEach(System.out::println);
      System.out.println("--------------------------------------");
      g.calculerCheminMinimisantNombreTroncons("BOILEAU", "ALMA");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
