package minimax_TRAN_THUY;

import java.util.Random;

//Cette classe repr�sente un joueur qui joue al�atoirement.  
public class RandomPlayer extends SimpleSpectator implements Player {

  private Random rand = new Random();

  public boolean nextPlay() {
    return rand.nextBoolean();
  }
}
