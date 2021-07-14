package mnkgame;

import java.util.Random;

public class Solution implements MNKPlayer {
    private Random rand;
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;

    //cose che credo che mi serviranno
    private int lastMyMove[] = {-1,-1};
    private int totalCells;

	
	/**
     * Default empty constructor
     */
	public Solution() {}

    public int max(int a, int b) {
        return a > b ? a : b;
    }

    public int min(int a, int b) {
        return a < b ? a : b;
    }

    public int evaluate(MNKBoard B) {
        return 1;
    }

    public int alphabetaPruning(Albero T, boolean myNode, int depth, float alpha, float beta) {
        float eval;
        List figli = T.getChildren();
        if (depth == 0 || T.isLeaf()) {
            return T.getValue();    //dobbiamo decidere come e quando fare l'evaluate, secondo me
                                    //non conviene mettere nell'albero del gioco tutte le composizioni
                                    //del gioco possiamo direttamente mettere i valori, allora si fa
                                    //l'evaluete solo alle foglie dopo aver creato il game tree.
        } else if(myNode) {
            eval = Float.MAX_VALUE;
            for(Albero c : figli) {
                eval = min(eval, alphabetaPruning(c,false,depth-1, alpha, beta));
                beta = min(eval, beta);
                if(beta <= alpha)
                    break;
            }
            return eval;
        } else {
            eval = Float.MIN_VALUE;
            for(Albero c : figli) {
                eval = max(eval, alphabetaPruning(c,true,depth-1,alpha,beta));
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
        totalCells = M*N;
		TIMEOUT = timeout_in_secs;
	}

	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {

        long start = System.currentTimeMillis();

		if(MC.length > 0) {
            //System.out.println(MC[0]);
			MNKCell c = MC[MC.length-1]; // Recover the last move from MC
			B.markCell(c.i,c.j);         // Save the last move in the local MNKBoard
		}

		// If there is just one possible move, return immediately
		if(FC.length == 1)
			return FC[0];
		
        // If board is clear, return center of the board
        if(MC.length == 0){
            int i = B.M * B.N/2;        //dovrebbe essere il centro
            B.markcell(i,i);
            MC = B.getMarkedCells();
            lastMyMove[0] = i;
            lastMyMove[1] = i;
            return MC[0];
        }



        /*
        Seleziono una cella che blocchi l'ultima mossa dell'avversario oppure 
        che sia vicina a un'altra mia cella così se il tempo finisce ritorno quella
        poi avvio l'alphabeta pruning per tot (pochi, tipo 3) livelli
        poi man mano che il gioco va avanti posso aumentare i livelli dell'alpha beta pruning.

        Creo prima l'albero di gioco e poi avvio l'algoritmo.
        Posso creare una funzione che crea l'albero delle mosse in modo più intelligente:
        Le mosse che mi interessano sono quelle che sono fino a K celle vicine 
        (in tutte le direzioni) a quelle selezionate.

        Sarebbe ancora più efficiente creare l'albero di gioco mentre si esegue l'alpabeta.
		*/
        

		
	}
	

	public String playerName() {
		return "monkiflip";
	}
}
