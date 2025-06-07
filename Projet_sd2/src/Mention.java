/**
 * Mention.
 */
public class Mention {
  private Artist source;
  private Artist target;
  private double weight;

  /**
   * Instantiates a new Mention.
   *
   * @param source     the source
   * @param target     the target
   * @param nbMentions the nb mentions
   */
  public Mention(Artist source, Artist target, int nbMentions) {
    this.source = source;
    this.target = target;
    this.weight = 1.0 / nbMentions;
  }

  /**
   * Gets source artist.
   *
   * @return the source
   */
  public Artist getSource() {
    return source;
  }

  /**
   * Gets target artist.
   *
   * @return the target
   */
  public Artist getTarget() {
    return target;
  }

  /**
   * Gets weight of the mention.
   *
   * @return the weight
   */
  public double getWeight() {
    return weight;
  }

}
