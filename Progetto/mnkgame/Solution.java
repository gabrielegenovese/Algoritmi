package mnkgame;

import java.util.Random;
import java.util.TreeSet;


public class Solution implements MNKPlayer {
    	private Random rand;
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;

	
	/**
     * Default empty constructor
     */
	public Solution() {}

    public float max(float a, float b) {
        return a > b ? a : b;
    }

    public float min(float a, float b) {
        return a < b ? a : b;
    }

    public int evaluate(int value) {
        return 1;
    }

    public int alphabeta(Tree T, boolean myNode, int depth, float alpha, float beta) {
        int eval;
        if (depth == 0 || T.isLeaf()) {
            return evaluate(T.value);
        } else if(myNode) {
            eval = Integer.MAX_VALUE;
            for(Tree c : T.children()) {
                eval = min(eval, alphabeta(c,false,depth-1, alpha, beta));
                beta = min(eval, beta);
                if(beta <= alpha)
                    break;
            }
            return eval;
        } else {
            eval = Integer.MIN_VALUE;
            for(Tree c : T.children()) {
                eval = max(eval, alphabeta(c, true, depth-1, alpha, beta));
                alpha = max(eval, alpha);
                if(beta <= alpha)
                    break;
            }
            return eval;
        }

    }

    public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		// New random seed for each game
		rand    = new Random(System.currentTimeMillis()); 
		B       = new MNKBoard(M,N,K);
		myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
        System.out.println("r u winning son? " + first);
		TIMEOUT = timeout_in_secs;
	}

	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
		long start = System.currentTimeMillis();
		if(MC.length > 0) {
            System.out.println(MC[0]);
			MNKCell c = MC[MC.length-1]; // Recover the last move from MC
			B.markCell(c.i,c.j);         // Save the last move in the local MNKBoard
		}
		// If there is just one possible move, return immediately
		if(FC.length == 1)
			return FC[0];
		
		// Check whether there is single move win 
		for(MNKCell d : FC) {
			// If time is running out, select a random cell
			if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
				MNKCell c = FC[rand.nextInt(FC.length)];
				B.markCell(c.i,c.j);
				return c;
			} else if(B.markCell(d.i,d.j) == myWin) {
				return d;  
			} else {
				B.unmarkCell();
			}
		}
		
		// Check whether there is a single move loss:
		// 1. mark a random position
		// 2. check whether the adversary can win
		// 3. if he can win, select his winning position 
		int pos   = rand.nextInt(FC.length); 
		MNKCell c = FC[pos]; // random move
		B.markCell(c.i,c.j); // mark the random position	
		for(int k = 0; k < FC.length; k++) {
			// If time is running out, return the randomly selected  cell
            if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
                return c;
            } else if(k != pos) {     
                MNKCell d = FC[k];
                if(B.markCell(d.i,d.j) == yourWin) {
                    B.unmarkCell();        // undo adversary move
                    B.unmarkCell();	       // undo my move	 
                    B.markCell(d.i,d.j);   // select his winning position
                    return d;							 // return his winning position
                } else {
                    B.unmarkCell();	       // undo adversary move to try a new one
                }	
            }	
        }
		// No win or loss, return the randomly selected move
		return c;
	}
	

	public String playerName() {
		return "W0lfG3n0";
	}
}
