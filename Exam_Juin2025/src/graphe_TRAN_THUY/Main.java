package graphe_TRAN_THUY;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("Exam_Juin2025/artists.txt", "Exam_Juin2025/mentions.txt");
        graph.trouverCheminLePlusCourt("The Beatles", "Kendji Girac");
    }
}
