public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("Projet_sd2/artists.txt", "Projet_sd2/mentions.txt");
        graph.trouverCheminLePlusCourt("The Beatles", "Kendji Girac");
        System.out.println("--------------------------");

        graph.trouverCheminMaxMentions("The Beatles", "Kendji Girac");
    }
}