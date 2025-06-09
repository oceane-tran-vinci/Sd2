package graphe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Graph {

  private Map<Integer, Artist> artists = new HashMap<>();
  private Map<String, Artist> artistsName = new HashMap<>();
  private Map<Artist, Set<Mention>> mentions = new HashMap<>();


  /**
   * Constructs a Graph from the given files.
   *
   * @param artistsFile  the file containing artist data
   * @param mentionsFile the file containing mention data
   */
  public Graph(String artistsFile, String mentionsFile) {
    loadArtists(artistsFile);
    loadMentions(mentionsFile);
  }

  /**
   * Loads artists from the given file.
   *
   * @param file the file containing artist data
   */
  private void loadArtists(String file) {
    try (BufferedReader scanner = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = scanner.readLine()) != null) {
        String[] infos = line.split(",", 3);
        int idArtist = Integer.parseInt(infos[0]);
        String name = infos[1];
        String category = infos[2];
        Artist artist = new Artist(idArtist, name, category);
        artists.put(idArtist, artist);
        artistsName.put(name, artist);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Loads mentions (connections) from the given file.
   *
   * @param file the file containing mention data
   */
  private void loadMentions(String file) {
    try (BufferedReader scanner = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = scanner.readLine()) != null) {
        String[] infos = line.split(",", 3);
        Artist sourceArtist = artists.get(Integer.parseInt(infos[0]));
        Artist destinationArtist = artists.get(Integer.parseInt(infos[1]));
        int mentionCount = Integer.parseInt(infos[2]);
        addMention(sourceArtist, destinationArtist, mentionCount);
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Adds a mention (connection) between two artists.
   *
   * @param sourceArtist      the source artist
   * @param destinationArtist the destination artist
   * @param mentionCount      the number of mentions
   */
  private void addMention(Artist sourceArtist, Artist destinationArtist, int mentionCount) {
    Mention mention = new Mention(sourceArtist, destinationArtist, mentionCount);
    Set<Mention> mentionsSrcArtist = mentions.get(sourceArtist);
    if (mentionsSrcArtist == null) {
      mentionsSrcArtist = new HashSet<>();
      mentions.put(sourceArtist, mentionsSrcArtist);
    }

    mentionsSrcArtist.add(mention);
  }

  public void trouverCheminLePlusCourt(String sourceArtist, String destinationArtist) {
    Artist src = artistsName.get(sourceArtist);
    Artist dst = artistsName.get(destinationArtist);

    Set<Artist> visited = new HashSet<>();
    Deque<Artist> artistQueue = new LinkedList<>();
    Map<Artist, Mention> artistMentions = new HashMap<>();
    boolean isFound = false;

    artistQueue.add(src);
    visited.add(src);

    while (!artistQueue.isEmpty()) {
      Artist currentArtist = artistQueue.poll();
      Set<Mention> mentionsSrcArtist = mentions.get(currentArtist);

      if (mentionsSrcArtist == null) {
        continue;
      }

      if (currentArtist.equals(dst)) {
        isFound = true;
        break;
      }

      for (Mention mention : mentionsSrcArtist) {
        Artist a = mention.getDestinationArtist();
        if (!visited.contains(a)) {
          visited.add(a);
          artistQueue.add(a);
          artistMentions.put(a, mention);
        }
      }
    }
    if (!isFound) {
      throw new RuntimeException("Aucun chemin entre " + sourceArtist + " et " + destinationArtist);
    }
    System.out.println(print(artistMentions, src, dst));
  }

  public void trouverCheminMaxMentions(String sourceArtist, String destinationArtist) {
    Artist src = artistsName.get(sourceArtist);
    Artist dst = artistsName.get(destinationArtist);

    Artist currentArtist;
    boolean isFound = false;

    Map<Artist, Mention> artistMentions = new HashMap<>();
    TreeMap<Artist, Double> artistsToExplore = new TreeMap<>(
        Comparator.comparing(Artist::getDistanceFromSource).thenComparing(Artist::getIdArtist));
    Set<Artist> exploredArtists  = new HashSet<>();

    src.setDistanceFromSource(0.0);
    artistsToExplore.put(src, 0.0);

    while (!artistsToExplore.isEmpty()) {
      currentArtist = artistsToExplore.pollFirstEntry().getKey();
      exploredArtists.add(currentArtist);

      if (currentArtist.equals(dst)) {
        isFound = true;
        break;
      }

      Set<Mention> outgoingMentions  = mentions.get(currentArtist);
      if (outgoingMentions == null) {
        continue;
      }

      for (Mention mention : outgoingMentions) {
        Artist nextArtist  = mention.getDestinationArtist();
        if (exploredArtists .contains(nextArtist )) {
          continue;
        }

        double weight = 1.0 / mention.getMention();
        double newDistance = currentArtist.getDistanceFromSource() + weight;

        if (!artistsToExplore.containsKey(nextArtist ) || newDistance < artistsToExplore.get(nextArtist)) {
          artistsToExplore.remove(nextArtist );
          nextArtist.setDistanceFromSource(newDistance);
          artistsToExplore.put(nextArtist, newDistance);
          artistMentions.put(nextArtist, mention);
        }
      }
    }
    if (!isFound) {
      throw new RuntimeException(
          "Aucun chemin trouvé entre " + sourceArtist + " et " + destinationArtist);
    }

    System.out.println(print(artistMentions, src, dst));
  }

  public String print(Map<Artist, Mention> artistMentions, Artist src, Artist dst) {
    String output = "";
    double mentionCount = 0;
    int pathLength = 0;

    Artist currentArtist = dst;
    while (currentArtist != null && !currentArtist.equals(src)) {
      Mention mention = artistMentions.get(currentArtist);
      if (mention != null) {
        mentionCount += 1.0 / mention.getMention();
        pathLength++;
      }
      output = currentArtist.getName() + " (" + currentArtist.getCategory() + ")\n" + output;

      currentArtist = artistMentions.get(currentArtist).getSourceArtist();
    }

    output = src.getName() + " (" + src.getCategory() + ")\n" + output;

    return "Longueur du chemin : " + pathLength + "\n" + "Coût total du chemin : " + mentionCount
        + "\n" + "Chemin :\n" + output;
  }


  /***********************************************************
   MÉTHODE GÉNÉRÉE (possible à l'exam de Juin 2025)
   ***********************************************************/

  /**
   * Affiche tous les artistes accessibles depuis l’artiste donné,
   * en utilisant un parcours en largeur (BFS).
   *
   * @param nomArtist nom de l’artiste de départ
   */
  public void bfs(String nomArtist) {
    // On récupère l'artiste source à partir de son nom (par ex. "The Beatles")
    Artist source = artistsName.get(nomArtist);

    // Si l'artiste n'existe pas dans la map, on affiche un message d'erreur et on quitte la méthode
    if (source == null) {
      System.out.println("Artiste introuvable : " + nomArtist);
      return;
    }

    Set<Artist> visited = new HashSet<>(); // Ensemble des artistes déjà visités (pour ne pas les visiter plusieurs fois)
    Deque<Artist> queue = new LinkedList<>(); // File utilisée pour le parcours en largeur (FIFO = premier entré, premier sorti)

    // On ajoute la source dans la file et on la marque comme visitée
    queue.add(source);
    visited.add(source);

    // Tant qu'il reste des artistes à explorer dans la file
    while (!queue.isEmpty()) {
      Artist current = queue.poll(); // On enlève le premier artiste de la file
      System.out.println(current.getName()); // On affiche son nom (ordre de visite BFS)

      // On récupère toutes les mentions sortantes depuis cet artiste (ses voisins)
      Set<Mention> out = mentions.get(current);
      // Si l’artiste a effectivement des voisins (il peut n’en avoir aucun → null), on les explore
      if (out != null) {
        for (Mention m : out) {
          Artist voisin = m.getDestinationArtist(); // Artiste atteint par la mention

          // Si ce voisin n'a pas encore été visité, on l'ajoute à la file et on le marque comme visité
          if (!visited.contains(voisin)) {
            queue.add(voisin);
            visited.add(voisin);
          }
        }
      }
    }
  }


  /**
   * Affiche le chemin avec le plus petit nombre de mentions
   * entre deux artistes (d’un artiste source à un artiste destination).
   * Utilise un parcours en largeur (BFS).
   *
   * @param sourceArtist nom de l’artiste de départ
   * @param destinationArtist nom de l’artiste d’arrivée
   */
  public void cheminMinimisantNombreDeMentions(String sourceArtist, String destinationArtist) {
    Set<Artist> visited = new HashSet<>(); // Set pour mémoriser les artistes déjà visités
    Deque<Artist> queue = new LinkedList<>(); // File pour le parcours en largeur (BFS)
    // Map pour reconstruire le chemin : pour chaque artiste visité, on garde la mention qui y mène
    Map<Artist, Mention> chemin = new HashMap<>();

    // On récupère les objets Artist depuis leurs noms
    Artist start = artistsName.get(sourceArtist);
    Artist end = artistsName.get(destinationArtist);

    // Vérifie que les deux artistes existent bien dans le graphe
    if (start == null || end == null) {
      System.out.println("Artiste introuvable.");
      return;
    }

    // On commence le parcours à partir de l'artiste de départ
    queue.add(start); // Ajout dans la file BFS
    visited.add(start); // Marque comme visité

    // Parcours en largeur (BFS)
    while (!queue.isEmpty()) {
      Artist current = queue.poll(); // Récupère le premier artiste dans la file

      // Si on a atteint l'artiste d'arrivée, on peut arrêter
      if (current.equals(end)){
        break;
      }

      // On récupère les mentions sortantes depuis l'artiste actuel
      Set<Mention> out = mentions.get(current);
      // S'il a bien des voisins (i.e. au moins une mention sortante)
      if (out != null) {
        // On parcourt toutes les mentions sortantes (chaque mention va d'un artiste vers un autre)
        for (Mention m : out) {
          Artist voisin = m.getDestinationArtist(); // L'artiste atteint par cette mention

          // Si le voisin n’a pas encore été visité
          if (!visited.contains(voisin)) {
            visited.add(voisin);   // Marquer comme visité
            queue.add(voisin);     // L’ajouter à la file
            chemin.put(voisin, m); // Retenir la mention par laquelle on l’a atteint
          }
        }
      }
    }

    // Si aucune mention n’a permis d’atteindre l’arrivée, il n’y a pas de chemin
    if (!chemin.containsKey(end)) {
      System.out.println("Aucun chemin trouvé.");
      return;
    }

    // Reconstruction du chemin à partir des mentions utilisées
    // On part de la fin (destination) et on remonte chaque mention jusqu’à atteindre le départ
    LinkedList<Mention> path = new LinkedList<>();
    Artist current = end;
    while (!current.equals(start)) {
      Mention m = chemin.get(current); // On récupère la mention qui a mené à l’artiste actuel
      path.addFirst(m);                // On insère cette mention au début de la liste (ordre inverse)
      current = m.getSourceArtist();   // On remonte vers l’artiste précédent
    }

    // Affichage final
    System.out.println("Chemin de " + sourceArtist + " à " + destinationArtist + " :");
    for (Mention m : path) {
      System.out.println(m.getSourceArtist().getName() + " → " + m.getDestinationArtist().getName());
    }
    System.out.println("Nombre de mentions (étapes) : " + path.size());
  }


  /**
   * Renvoie toutes les mentions entrantes vers un artiste donné.
   * Autrement dit, toutes les mentions dont la destination est cet artiste.
   *
   * @param nomArtist nom de l’artiste ciblé
   * @return ensemble des mentions entrantes (Set<Mention>), ou un ensemble vide si aucun trouvé
   */
  public Set<Mention> mentionsEntrantes(String nomArtist) {
    Artist target = artistsName.get(nomArtist); // On récupère l’objet Artist correspondant au nom donné
    Set<Mention> entrantes = new HashSet<>(); // On crée un ensemble vide pour stocker les mentions entrantes

    // Si l’artiste n’existe pas dans la map, on retourne l’ensemble vide directement
    if (target == null) {
      return entrantes; // Artiste introuvable → retourne un ensemble vide
    }

    // On parcourt toutes les entrées (source → ensemble de mentions sortantes) du graphe
    for (Map.Entry<Artist, Set<Mention>> entry : mentions.entrySet()) {
      // Pour chaque mention sortante de l’artiste courant
      for (Mention m : entry.getValue()) {
        // Si la mention aboutit à l’artiste ciblé → c’est une mention entrante
        if (m.getDestinationArtist().equals(target)) {
          entrantes.add(m); // On l’ajoute à l’ensemble résultat
        }
      }
    }

    // On retourne toutes les mentions entrantes trouvées
    return entrantes;
  }


  /**
   * Construit et retourne une liste d'adjacence simplifiée :
   * chaque artiste est associé aux artistes qu'il mentionne.
   */
  public Map<Artist, Set<Artist>> toListeDAdjacence() {
    // On crée une nouvelle Map vide pour stocker la liste d’adjacence :
    // à chaque artiste source (clé) on associera un ensemble (Set) des artistes qu’il mentionne (valeur)
    Map<Artist, Set<Artist>> adjacence = new HashMap<>();

    // On parcourt toutes les paires (source → ensemble de mentions sortantes)
    for (Map.Entry<Artist, Set<Mention>> entry : mentions.entrySet()) {
      Artist source = entry.getKey();                   // L’artiste qui mentionne d’autres artistes
      Set<Mention> outgoingMentions = entry.getValue(); // Toutes les mentions faites par cet artiste

      Set<Artist> voisins = new HashSet<>(); // On prépare un ensemble vide pour stocker ses voisins (destinations)

      // Pour chaque mention sortante...
      for (Mention m : outgoingMentions) {
        // ...on récupère l'artiste mentionné (destination) et on l’ajoute dans l’ensemble des voisins
        voisins.add(m.getDestinationArtist());
      }

      // On associe maintenant l’artiste source à l’ensemble de ses voisins (artistes qu’il mentionne)
      adjacence.put(source, voisins);
    }

    return adjacence;// On retourne la map complète représentant la liste d’adjacence
  }

}