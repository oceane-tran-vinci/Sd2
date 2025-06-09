package graphe;

public class Mention {

  private Artist destinationArtist;
  private Artist sourceArtist;
  private int mention;

  public Mention(Artist sourceArtist, Artist destinationArtist, int mention) {
    this.sourceArtist = sourceArtist;
    this.destinationArtist = destinationArtist;
    this.mention = mention;
  }

  public Artist getDestinationArtist() {
    return destinationArtist;
  }

  public Artist getSourceArtist() {
    return sourceArtist;
  }

  public int getMention() {
    return mention;
  }

//  AJOUTER toString (sans nbr mention)
//  @Override
//  public String toString() {
//    return sourceArtist.getName() + " → " + destinationArtist.getName();
//  }

  //OU : toString (avec nbr mention)
  @Override
  public String toString() {
    return sourceArtist.getName() + " → " + destinationArtist.getName()
        + " (" + mention + " mention" + (mention > 1 ? "s" : "") + ")";
  }
}
