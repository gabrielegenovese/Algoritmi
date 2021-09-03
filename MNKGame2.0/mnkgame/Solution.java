package mnkgame;

import java.util.Set;
import java.util.HashSet;

public class Solution implements MNKPlayer {
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;
    private long start;
    boolean rev = false;
    int k;
	
	/**
     * Default empty constructor
     */
	public Solution() {}

    public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		B       = new MNKBoard(M,N,K);
		myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
		TIMEOUT = timeout_in_secs;
        k = K-1;
	}

    //posso evitare di usa il new?
    public MNKCell[] removeUselessCells(MNKBoard B) {
        int i, j, add = 0;
        Set<MNKCell> FC = new HashSet<MNKCell>();
        MNKCell[] MC = B.getMarkedCells();

        if(B.M * B.N < 17)
            add = 1;
        for(MNKCell m : MC) {
            i = m.i;
            j = m.j;
            if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(98.0/100.0)) break;
            for(int w = 1; w <= 1+add; w++) {
                if(i-w >= 0)
                    if(B.cellState(i-w,j) == MNKCellState.FREE)
                        FC.add(new MNKCell(i-w,j));
                if(j-w >= 0)
                    if(B.cellState(i,j-w) == MNKCellState.FREE)
                        FC.add(new MNKCell(i,j-w));
                if(i+w < B.M)
                    if(B.cellState(i+w,j) == MNKCellState.FREE)
                        FC.add(new MNKCell(i+w,j));
                if(j+w < B.N)
                    if(B.cellState(i,j+w) == MNKCellState.FREE)
                        FC.add(new MNKCell(i,j+w));
                if(i+w < B.M && j+w < B.N)
                    if(B.cellState(i+w,j+w) == MNKCellState.FREE)
                        FC.add(new MNKCell(i+w,j+w));
                if(i+w < B.M && j-w >= 0)
                    if(B.cellState(i+w,j-w) == MNKCellState.FREE)
                        FC.add(new MNKCell(i+w,j-w));
                if(i-w >= 0 && j+w < B.N)
                    if(B.cellState(i-w,j+w) == MNKCellState.FREE)
                        FC.add(new MNKCell(i-w,j+w));
                if(i-w >= 0 && j-w >= 0)
                    if(B.cellState(i-w,j-w) == MNKCellState.FREE)
                        FC.add(new MNKCell(i-w,j-w));
            }
        }
        MNKCell[] tmpFC = new MNKCell[FC.size()];
        return FC.toArray(tmpFC);
    }

    public MNKCell getRandomUsefullCell(MNKBoard B) {
        int i, j;
        MNKCell[] FC = B.getFreeCells();

        for(MNKCell f : FC) {
            if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(98.0/100.0)) break;
            i = f.i;
            j = f.j;
            if (i+1 < B.M)
                if(B.cellState(i+1,j) != MNKCellState.FREE)
                    return f;
            if (i-1 >= 0)
                if(B.cellState(i-1,j) != MNKCellState.FREE)
                    return f;
            if (j+1 < B.N)
                if(B.cellState(i,j+1) != MNKCellState.FREE)
                    return f;
            if (j-1 >= 0)
                if(B.cellState(i,j-1) != MNKCellState.FREE)
                    return f;
            if (i+1 < B.M && j+1 < B.N)
                if(B.cellState(i+1,j+1) != MNKCellState.FREE)
                    return f;
            if (i+1 < B.M && j-1 >= 0)
                if(B.cellState(i+1,j-1) != MNKCellState.FREE)
                    return f;
            if (i-1 >= 0 && j+1 < B.N)
                if(B.cellState(i-1,j+1) != MNKCellState.FREE)
                    return f;
            if (i-1 >= 0 && j-1 >= 0)
                if(B.cellState(i-1,j-1) != MNKCellState.FREE)
                    return f;
        }
        return FC[0];
    }

    public double isThereAThread(MNKBoard B, MNKCellState s, int i, int j) {
        int n = 1;
        // Orizzontal check
        for(int k = 1; j-k >= 0 && B.cellState(i,j-k) == s; k++) n++; // backward check
        for(int k = 1; j+k <B.N && B.cellState(i,j+k) == s; k++) n++; // forward check   
        if(n >= k/3 ) return n/k;

        // Vertical check
        n = 1;
        for(int k = 1; i-k >= 0  && B.cellState(i-k,j) == s; k++) n++; // backward check
        for(int k = 1; i+k < B.M && B.cellState(i+k,j) == s; k++) n++; // forward check
        if(n >= k/3 ) return n/k;

        // Diagonal check
        n = 1;
        for(int k = 1; i-k >= 0  && j-k >= 0  && B.cellState(i-k,j-k) == s; k++) n++; // backward check
        for(int k = 1; i+k < B.M && j+k < B.N && B.cellState(i+k,j+k) == s; k++) n++; // forward check
        if(n >= k/3 ) return n/k;

        // Anti-diagonal check
        n = 1;
        for(int k = 1; i-k >= 0 && j+k < B.N && B.cellState(i-k,j+k) == s; k++) n++; // backward check
        for(int k = 1; i+k < B.M && j-k >= 0 && B.cellState(i+k,j-k) == s; k++) n++; // backward check
        if(n >= k/3 ) return n/k;

        return -2;
    }

    public double evaluate(MNKBoard B) {
        //per ora valuto solo lo stato della partita
        MNKGameState state = B.gameState();
        int i,j;
        if(state == myWin)
            return 1;
        else if(state == MNKGameState.DRAW)
            return 0;
        else if(state == MNKGameState.OPEN) {
            
            MNKCell[] MC = B.getMarkedCells();

            for (MNKCell M : MC) {
                i = M.i;
                j = M.j;
                // se sono i miei simboli
                if((M.state == MNKCellState.P1 && myWin == MNKGameState.WINP1) || (M.state == MNKCellState.P2 && myWin == MNKGameState.WINP2)) {
                    MNKCellState s;
                    if(myWin == MNKGameState.WINP1)
                        s = MNKCellState.P1;
                    else
                        s = MNKCellState.P2;

                    double tmp = isThereAThread(B,s,i,j);
                    if(tmp != -2) return tmp;
                }
                // se sono dell'avversario
                if((M.state == MNKCellState.P1 && yourWin == MNKGameState.WINP1) || (M.state == MNKCellState.P2 && yourWin == MNKGameState.WINP2)) {
                    MNKCellState s;
                    if(yourWin == MNKGameState.WINP1)
                        s = MNKCellState.P1;
                    else
                        s = MNKCellState.P2;

                    double tmp = isThereAThread(B,s,i,j);
                    if(tmp != -2) return tmp*(-1);
                } 
            }

            return 0.1;
        } else
            return -1;
    }

    public double alphabetaPruning(MNKBoard B, boolean myNode, int depth, double alpha, double beta) {
        double eval;
        MNKCell FC [] = removeUselessCells(B);
        if (depth == 0 || B.gameState != MNKGameState.OPEN || (System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
            return evaluate(B);
        } else if(myNode) {
            eval = 10;
            for(MNKCell c : FC) {
                B.markCell(c.i, c.j);
                eval = Math.min(eval, alphabetaPruning(B,false,depth-1,alpha,beta));
                beta = Math.min(eval, beta);
                B.unmarkCell();
                if(beta <= alpha)
                    break;
            }
            return eval;
        } else {
            eval = -10;
            for(MNKCell c : FC) {
                B.markCell(c.i, c.j);
                eval = Math.max(eval, alphabetaPruning(B,true,depth-1,alpha,beta));
                alpha = Math.max(eval, alpha);
                B.unmarkCell();
                if(beta <= alpha)
                    break;
            }
            return eval;
        }
    }

	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
        int eur;
        MNKCell selected = getRandomUsefullCell(B);
        start = System.currentTimeMillis();

		if(MC.length > 0) {
			MNKCell c = MC[MC.length-1]; // Recover the last move from MC
			B.markCell(c.i,c.j);         // Save the last move in the local MNKBoard
		}

		// If there is just one possible move, return immediately
		if(FC.length == 1) return FC[0];
		
        // DA RIFARE MEGLIO
        if(MC.length == 0){
            if(B.M > B.K || B.N > B.K)
                B.markCell(1,1);
            else
                B.markCell(0,0);
            MC = B.getMarkedCells();
            return MC[0];
        } else if(MC.length == 1) {
            if(B.M > B.K || B.N > B.K) {
                if(B.cellState(1,1) == MNKCellState.FREE) {
                    B.markCell(1,1);
                    rev = false;
                } else {
                    B.markCell(B.M-2, 1);
                    rev = true;
                }
            } else {
                if(B.cellState(0,0) == MNKCellState.FREE)
                    B.markCell(0,0);
                else
                    B.markCell(1,1);
            }
            MC = B.getMarkedCells();
            return MC[1];
        }

        int i, j;
        if (B.M * B.N > 23) {
            if(MC.length > 0 && MC.length < (k*2)) {
                
                if(MC.length % 2 == 0){
                    i = MC[0].i;
                    j = MC[0].j;
                } else {
                    i = MC[1].i;
                    j = MC[1].j;
                }

                for(int w = 1; w < k; w++){
                    if (!rev) {
                        if(B.M > B.N) {
                            if(B.cellState(i+w,j) == MNKCellState.FREE) {
                                B.markCell(i+w, j);
                                MC = B.getMarkedCells();
                                return MC[MC.length-1];
                            }
                        } else if(B.M < B.N){
                            if(B.cellState(i,j+w) == MNKCellState.FREE) {
                                B.markCell(i,j+w);
                                MC = B.getMarkedCells();
                                return MC[MC.length-1];
                            }
                        } else {
                            if (B.cellState(i+w,j+w) == MNKCellState.FREE) {
                                B.markCell(i+w,j+w);
                                MC = B.getMarkedCells();
                                return MC[MC.length-1];
                            }
                        }
                    } else {
                        if(i-w < 0) break; // non so perche' devo fare sta cosa, ma se non la faccio ho degli errori
                        if(B.M > B.N) {
                            if(B.cellState(i-w,j) == MNKCellState.FREE) {
                                B.markCell(i-w, j);
                                MC = B.getMarkedCells();
                                return MC[MC.length-1];
                            }
                        } else if(B.M < B.N){
                            if(B.cellState(i,j-w) == MNKCellState.FREE) {
                                B.markCell(i,j-w);
                                MC = B.getMarkedCells();
                                return MC[MC.length-1];
                            }
                        } else {
                            if(B.cellState(i-w,j+w) == MNKCellState.FREE) {
                                B.markCell(i-w,j+w);
                                MC = B.getMarkedCells();
                                return MC[MC.length-1];
                            }
                        }
                    }
                }
            }
            eur = -2;
            if (k > 5)
                eur *= 2;
        } else {
            eur = 3;
        }
        
        MNKCell[] interestingFC = removeUselessCells(B);
        //System.out.println("Numero di celle interessanti: "+interestingFC.length);
        double value, max = -11;
        for(MNKCell A : interestingFC) {
            if ((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
                //System.out.println("Il tempo e' finito");
                break;
            } else {
                B.markCell(A.i,A.j);
                //System.out.print("Controllo la mossa " + A);
                value = alphabetaPruning(B,true,k+eur,-10,10);
                B.unmarkCell();
                //System.out.println("    Valutata: "+ value);
                if (value > max) {
                    max = value;
                    selected = A;
                    //System.out.println("Era meglio della mossa di prima perche' vale: " + tmp);
                }
            }
        }

        B.markCell(selected.i,selected.j);
        //System.out.println("Alla fine ho selezionato " + selected);
        return selected;
	}

	public String playerName() {
		return "monkiflip";
	}
}