package mnkgame;

import java.util.Random;

public class Solution implements MNKPlayer {
    private Random rand;
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;

    //cose che credo che mi serviranno
    private int myLastMove[] = {-1,-1};
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

    public float evaluate(MNKBoard B, MNKCell c) {
        MNKGameState state = B.markCell(c.i,c.j);
        if((state == WINP1 && myWin) || (state == WINP2 && yourWin))
            return 1;
        else if(state == DRAW)
            return 0
        else
            return -1;
    }

    public float alphabetaPruning(Albero T, boolean myNode, int depth, float alpha, float beta) {
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
                eval = min(eval, alphabetaPruning(c,false,depth-1,alpha,beta));
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

    // per ora crea un game tree con con tutte le celle
    public Albero createGameTree(Albero T, MNKBoard B, int depth, int startI, int endI, int startJ, int endJ) {
        if (depth == 0)
            return;
            
        MNKCell[] FC = B.getFreeCells();
        int conta = 0;

        for (MNKCell c : FC) {
            if (c.i <= endI && c.i >= startI && c.j >= startJ && c.j <= endJ) {
                float tmp = evalute(B,c);
                T.addChild(tmp);
                Albero child = T.getChildren().get(conta);
                createGameTree(child, B, depth-1);
                B.unmarkCell(c.i,c.j);
                conta++;
            }
        }
        return T;
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
            myLastMove[0] = i;
            myLastMove[1] = i;
            return MC[0];
        }

        int startI = c.i + k, endI = c.i - k, startJ = c.j - k, endJ = c.j + k;
        for (MNKCell c : MC) {
            if(c.i + k < startI)
                startI = c.i + k;
            if(c.i - k > endI)
                endI = c.i - k;
            if(c.j - k < startJ)
                startJ = c.j - k;
            if(c.i + k > endJ)
                endJ = c.j + k;
        }

        Albero T = new Albero();
        T.setParent(NULL);
        T = createGameTree(T, B, 3, startI, endI, startJ, endJ);

        //return alphabetaPruning(T, true, 3, ?, ?);
        return FC[rand.nextInt(FC.length)];

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
