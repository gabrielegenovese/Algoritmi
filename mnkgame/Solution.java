package mnkgame;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import mnkgame.MNKGameState;

public class Solution implements MNKPlayer {
    private Random rand;
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;
    private long start;

    //cose che credo che mi serviranno
    private int myLastMove[];
    private int totalCells;
    int k;

	
	/**
     * Default empty constructor
     */
	public Solution() {}

    public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		// New random seed for each game
		rand    = new Random(System.currentTimeMillis()); 
		B       = new MNKBoard(M,N,K);
		myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
		TIMEOUT = timeout_in_secs;

        totalCells = M*N;
        k = K;
	}

    public float max(float a, float b) {
        return a > b ? a : b;
    }

    public float min(float a, float b) {
        return a < b ? a : b;
    }

    public float evaluate(MNKBoard B) {
        //per ora valuto solo lo stato della partita
        MNKGameState state = B.gameState();
        if((state == MNKGameState.WINP1 && myWin == MNKGameState.WINP1) || (state == MNKGameState.WINP2 && myWin == MNKGameState.WINP2))
            return 1;
        else if(state == MNKGameState.DRAW)
            return 0.5f;
        else if(state == MNKGameState.OPEN)
            return 0;
        else
            return -1;
    }

    public float alphabetaPruning(Albero T, boolean myNode, int depth, float alpha, float beta) {
        float eval;
        if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
            return evaluate(T.getValue());
        } else {
            if(T.getValue().gameState()==MNKGameState.OPEN)
                T = createGameTree(T, 1);
            List<Albero> figli = T.getChildren();
            if (depth == 0 || T.isLeaf()) {
                return evaluate(T.getValue());
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
    }

    // per ora crea un game tree completo
    public Albero createGameTree(Albero T, int depth) { //, int startI, int endI, int startJ, int endJ) {
        
        if (depth == 0) return T;

        MNKBoard B = T.getValue();
        MNKCell[] FC = B.getFreeCells();

        for(int i = 0; i < FC.length; i++) {
            //copio la board
		    MNKBoard tmp = new MNKBoard(B.M,B.N,B.K);
            MNKCell[] MC = B.getMarkedCells();
            for(MNKCell m : MC)
                tmp.markCell(m.i,m.j);
            //inserisco la mossa
            tmp.markCell(FC[i].i,FC[i].j);
            Albero child = T.addChild(tmp);

            //se la partita non e' finita vado avanti nella creazione dell'albero
            if(tmp.gameState() == MNKGameState.OPEN) createGameTree(child, depth-1);
                // oppure child = createGameTree(child, depth-1);
        }
        return T;
    }

    public void printTree(Albero T, String ramo) {
        System.out.println(ramo+T);
        MNKCell[] FC = T.getValue().getFreeCells();
        System.out.println(ramo+FC.length);
        if(!T.isLeaf()){
            for(Albero R : T.getChildren())
                printTree(R,ramo+" ");
        }
    }

	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {

        MNKCell selected;
        start = System.currentTimeMillis();

		if(MC.length > 0) {
			MNKCell c = MC[MC.length-1]; // Recover the last move from MC
			B.markCell(c.i,c.j);         // Save the last move in the local MNKBoard
		}

		// If there is just one possible move, return immediately
		if(FC.length == 1) return FC[0];
		
        // If board is clear, then im first, return right corner of the board
        if(MC.length == 0){
            B.markCell(0,0);
            MC = B.getMarkedCells();
            return MC[0];
        }


        /*
        //Limitare le celle per createTree
        MNKCell f = MC[0];
        int startI = f.i + k, endI = f.i - k, startJ = f.j - k, endJ = f.j + k;
        for (MNKCell c : MC) {
            if(c.i + k < startI)
                startI = c.i + k;
            if(c.i - k > endI)
                endI = c.i - k;
            if(c.j - k < startJ)
                startJ = c.j - k;
            if(c.i + k > endJ)
                endJ = c.j + k;
            if -> vado oltre alla board coi valori, metto i limiti giusti
        }*/

        //creo uno strato dell'albero di gioco
        Albero T = new Albero(B);
        T.setParent(null);
        T = createGameTree(T,1);//, startI, endI, startJ, endJ);

        //uso alphabeta su tutti i figli dell'albero di gioco
        List<Albero> children = T.getChildren();
        selected = FC[0];
        float tmp, max = -1;
        for(Albero asb : children) {
            if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
                return FC[rand.nextInt(FC.length)];
            } else {
                tmp = alphabetaPruning(asb, true, 30, Float.MIN_VALUE, Float.MAX_VALUE);
                if (tmp > max){
                    max = tmp;
                    MNKCell[] temp = asb.getValue().getMarkedCells();
                    selected = temp[temp.length-1];
                }
            }
        }
        B.markCell(selected.i,selected.j);

        return selected;
	}
	

	public String playerName() {
		return "monkiflip";
	}
}