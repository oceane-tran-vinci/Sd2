package graphe_TRAN_THUY;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private final String artistFile;
    private final String mentionFile;   
    private final HashMap<String, Artist> artistsbyName = new HashMap<String, Artist>();
    private final HashMap<Integer, Artist> artistsById = new HashMap<Integer, Artist>();
    private final HashMap<Artist, HashSet<Mention>> listeAdjacence = new HashMap<Artist,HashSet<Mention>>();

    public Graph(String artistFile, String mentionFile) {
    	this.artistFile=artistFile;
    	this.mentionFile=mentionFile;
        readArtists();
        readMentions();
    }
    
    private void readArtists() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.artistFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(",");
                int id = Integer.parseInt(datas[0]) ;
                String name = datas[1];
                String category = datas[2];
                Artist artist = new Artist(id,name,category) ;
                artistsbyName.put(name, artist);
                artistsById.put(id, artist);
                listeAdjacence.put(artist, new HashSet<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readMentions() {
        try (Scanner sc = new Scanner(new File(this.mentionFile))) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                int from = Integer.parseInt(line[0]);
                int to = Integer.parseInt(line[1]);
                int number = Integer.parseInt(line[2]);
                Artist fromArtist = artistsById.get(from);
                Artist toArtist = artistsById.get(to);
                Mention mention = new Mention(fromArtist, toArtist, number);
                listeAdjacence.get(fromArtist).add(mention);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //TODO : Compléter
    /*AVANT :
    public void trouverCheminLePlusCourt(String from, String to) {
        Artist fromArtist = artistsbyName.get(from);
        Artist toArtist = artistsbyName.get(to);

        Queue<Artist> queue = new LinkedList<>();
        queue.add(fromArtist);
        HashSet<Artist> visited = new HashSet<>();
        visited.add(fromArtist);

        while (!queue.isEmpty() ) {
            Artist current = queue.poll();
            System.out.println(current);
            for (Mention mention : listeAdjacence.get(current)) {
                Artist next = mention.getDestination();
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }
    }
     */

    //TODO APRES :
    public void trouverCheminLePlusCourt(String from, String to) {
        Artist fromArtist = artistsbyName.get(from);
        Artist toArtist = artistsbyName.get(to);

        // (Optionnel) garde-fous simples
        if (fromArtist == null || toArtist == null) {
            System.out.println("Artistes introuvables.");
            return;
        }

        Queue<Artist> queue = new LinkedList<>();
        queue.add(fromArtist);
        HashSet<Artist> visited = new HashSet<>();
        visited.add(fromArtist);

        // Pour reconstruire le chemin
        HashMap<Artist, Artist> parent = new HashMap<>();
        parent.put(fromArtist, null);

        boolean found = false;

        while (!queue.isEmpty()) {
            Artist current = queue.poll();
            System.out.println(current.getName());

            if (current.equals(toArtist)) {
                found = true;
                break; // BFS garantit un plus court chemin en nombre d’arêtes
            }

            for (Mention m : listeAdjacence.getOrDefault(current, new HashSet<>())) {
                Artist next = m.getDestination();
                if (!visited.contains(next)) {
                    visited.add(next);
                    parent.put(next, current);
                    queue.add(next);
                }
            }
        }

        if (!found) {
            System.out.println("Aucun chemin trouvé entre " + from + " et " + to + ".");
            return;
        }

        // Reconstruction du chemin to -> from puis affichage dans l'ordre
        List<Artist> path = new ArrayList<>();
        Artist artist = toArtist;
        while (artist != null) {
            path.add(artist);
            artist = parent.get(artist);
        }
        Collections.reverse(path);
        for (Artist a : path) {
            System.out.println(a.getName());
        }

    }

}
