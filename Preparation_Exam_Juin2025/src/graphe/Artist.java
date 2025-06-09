package graphe;

import java.util.Objects;

public class Artist {

  private int idArtist;
  private String name;
  private String category;
  private double distanceFromSource = Double.MAX_VALUE;


  public Artist(int idArtist, String name, String category) {
    this.idArtist = idArtist;
    this.name = name;
    this.category = category;
  }

  public int getIdArtist() {
    return idArtist;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }


  public double getDistanceFromSource() {
    return distanceFromSource;
  }

  public void setDistanceFromSource(double distanceFromSource) {
    this.distanceFromSource = distanceFromSource;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Artist artist)) {
      return false;
    }
    return idArtist == artist.idArtist;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(idArtist);
  }
}
