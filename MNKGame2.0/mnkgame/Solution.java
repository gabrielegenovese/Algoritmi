package mnkgame;

import java.util.Set;
import java.util.HashSet;

public class Solution implements MNKPlayer {
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;
    private long start;

    boolean CelleControllate[][];
    boolean rev = false;
    int eur, k;
	

	/**
     * Default empty constructor
     */
	public Solution() {}


    public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		B       = new MNKBoard(M,N,K);
		myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
		TIMEOUT = timeout_in_secs;
        CelleControllate = new boolean[M][N];
        k = K-1;
	}


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


    /*
    ritorna un valore double che corrisponde alla lunghezza del filo pi?? lungo del giocatore S
    controlla anche se c'?? un buco tra due fili
    */
    public double isThereAThread(MNKBoard B, MNKCellState s, int i, int j) {
        boolean hole = false;
        int max, tmp, n = 1;

        // Orizzontal check
        for(int k = 1; j-k >= 0 && B.cellState(i,j-k) == s; k++){ n++; CelleControllate[i][j-k] = true;} // backward check

        // vedo se trovo un buco e continuo
        if(j-n-1 >= 0 && B.cellState(i,j-n-1) == MNKCellState.FREE) {
            n++;
            tmp = n;
            for(int k = n+2; j-k >= 0 && B.cellState(i,j-k) == s; k++){ n++; CelleControllate[i][j-k] = true;} // backward check
            if(n > tmp)
                hole = true;
        }
        
        for(int k = 1; j+k < B.N && B.cellState(i,j+k) == s; k++){ n++; CelleControllate[i][j+k] = true;} // forward check

        // se prima non c'era lo cerco dall'altra parte
        if(!hole) {
            if(j+n+1 < B.N && B.cellState(i,j+n+1) == MNKCellState.FREE){
                n++;
                for(int k = n+2; j+k <B.N && B.cellState(i,j+k) == s; k++){ n++; CelleControllate[i][j+k] = true;} // forward check
            }
        }

        max = n/(k+1);

        if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) return max;

        // Vertical check
        hole = false;
        n = 1;
        for(int k = 1; i-k >= 0  && B.cellState(i-k,j) == s; k++){ n++; CelleControllate[i-k][j] = true;}  // backward check

        // vedo se trovo un buco e continuo
        if(i-n-1 >= 0 && B.cellState(i-n-1,j) == MNKCellState.FREE) {
            n++;
            tmp = n;
            for(int k = n+2; i-k >= 0  && B.cellState(i-k,j) == s; k++){ n++; CelleControllate[i-k][j] = true;}  // backward check
            if(n > tmp)
                hole = true;
        }

        for(int k = 1; i+k < B.M && B.cellState(i+k,j) == s; k++){ n++; CelleControllate[i+k][j] = true;} // forward check

        // se prima non c'era lo cerco dall'altra parte
        if(!hole) {
            if(i+n+1 < B.M && B.cellState(i+n+1,j) == MNKCellState.FREE){
                n++;
                for(int k = n+2; i+k < B.M && B.cellState(i+k,j) == s; k++){ n++; CelleControllate[i+k][j] = true;} // forward check
            }
        }

        if(n/k > max) max = n/(k+1);

        if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) return max;


        // Diagonal check
        hole = false;
        n = 1;
        for(int k = 1; i-k >= 0  && j-k >= 0  && B.cellState(i-k,j-k) == s; k++){ n++; CelleControllate[i-k][j-k] = true;} // backward check

        // vedo se trovo un buco e continuo
        if(i-n-1 >= 0  && j-n-1 >= 0  && B.cellState(i-n-1,j-n-1) == s) {
            n++;
            tmp = n;
            for(int k = n+2; i-k >= 0  && j-k >= 0  && B.cellState(i-k,j-k) == s; k++){ n++; CelleControllate[i-k][j-k] = true;} // backward check
            if(n > tmp)
                hole = true;
        }

        for(int k = 1; i+k < B.M && j+k < B.N && B.cellState(i+k,j+k) == s; k++){ n++; CelleControllate[i+k][j+k] = true;} // forward check

        // se prima non c'era lo cerco dall'altra parte
        if(!hole) {
            if(i+n+1 < B.M && j+n+1 < B.N && B.cellState(i+n+1,j+n+1) == MNKCellState.FREE){
                n++;
                for(int k = n+2; i+k < B.M && j+k < B.N && B.cellState(i+k,j+k) == s; k++){ n++; CelleControllate[i+k][j+k] = true;} // forward check
            }
        }

        if(n/k > max) max = n/(k+1);

        if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) return max;


        // Anti-diagonal check
        hole = false;
        n = 1;
        for(int k = 1; i-k >= 0 && j+k < B.N && B.cellState(i-k,j+k) == s; k++){ n++; CelleControllate[i-k][j+k] = true;} // backward check

        // vedo se trovo un buco e continuo
        if(i-n-1 >= 0  && j+n+1 < B.N  && B.cellState(i-n-1,j+n+1) == s) {
            n++;
            tmp = n;
            for(int k = n+2; i-k >= 0  && j+k < B.N  && B.cellState(i-k,j+k) == s; k++){ n++; CelleControllate[i-k][j+k] = true;} // backward check
            if(n > tmp)
                hole = true;
        }

        for(int k = 1; i+k < B.M && j-k >= 0 && B.cellState(i+k,j-k) == s; k++){ n++; CelleControllate[i+k][j-k] = true;} // forward check

        // se prima non c'era lo cerco dall'altra parte
        if(!hole) {
            if(i+n+1 < B.M && j-n-1 >= 0 && B.cellState(i+n+1,j-n-1) == MNKCellState.FREE){
                n++;
                for(int k = n+2; i+k < B.M && j-k >= 0 && B.cellState(i+k,j-k) == s; k++){ n++; CelleControllate[i+k][j-k] = true;} // forward check
            }
        }

        if(n/k > max) max = n/(k+1);


        return max;
    }


    public double evaluate(MNKBoard B) {
        MNKGameState state = B.gameState();

        if(state == myWin)
            return 100;
        else if(state == MNKGameState.DRAW)
            return 0;
        else if(state == MNKGameState.OPEN) {
            // stabilisco che valore ha la board di gioco in questo momento
            double value, tmp, pos = -1, neg = 0;
            MNKCellState s;
            MNKCell[] MC = B.getMarkedCells();

            for(int w = 0; w < B.M ; w++)
                for (int z = 0; z < B.N; z++)
                    if(B.cellState(w, z) != MNKCellState.FREE)
                        CelleControllate[w][z] = false;

            for (MNKCell M : MC) {
                if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) break;
                // se sono i miei simboli
                if(!CelleControllate[M.i][M.j] && (M.state == MNKCellState.P1 && myWin == MNKGameState.WINP1) || (M.state == MNKCellState.P2 && myWin == MNKGameState.WINP2)) {
                    
                    CelleControllate[M.i][M.j] = true;
                    s = myWin == MNKGameState.WINP1 ? MNKCellState.P1 : MNKCellState.P2;

                    tmp = isThereAThread(B,s,M.i,M.j); // aggiungo valore se trovo un filo dei miei simboli
                    if (tmp > pos) pos = tmp;
                }
                // se sono dell'avversario
                if(!CelleControllate[M.i][M.j] && (M.state == MNKCellState.P1 && yourWin == MNKGameState.WINP1) || (M.state == MNKCellState.P2 && yourWin == MNKGameState.WINP2)) {
                    
                    CelleControllate[M.i][M.j] = true;
                    s = yourWin == MNKGameState.WINP1 ? MNKCellState.P1 : MNKCellState.P2;

                    tmp = isThereAThread(B,s,M.i,M.j); // tolgo valore se trovo un filo dei simboli dell'avversario
                    if (tmp > neg) neg = tmp;
                }
            }
            if (pos > neg) 
                value = pos;
            else
                value = neg*-1;

            return value;
        } else
            return -100;
    }


    public double alphabetaPruning(MNKBoard B, boolean myNode, int depth, double alpha, double beta) {
        double eval;
        MNKCell usefullFC [] = removeUselessCells(B);
        if (depth == 0 || B.gameState != MNKGameState.OPEN || (System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
            return evaluate(B);
        } else if(myNode) {
            eval = 1000;
            for(MNKCell c : usefullFC) {
                B.markCell(c.i, c.j);
                eval = Math.min(eval, alphabetaPruning(B,false,depth-1,alpha,beta));
                beta = Math.min(eval, beta);
                B.unmarkCell();
                if(beta <= alpha)
                    break;
            }
            return eval;
        } else {
            eval = -1000;
            for(MNKCell c : usefullFC) {
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


    public MNKCell strategiaIniziale(MNKCell[] FC, MNKCell[] MC) {
        int i, j;
        
        // se sono primo
        if(MC.length == 0){
            if(B.M > B.K || B.N > B.K) // se non M o N sono maggiori di K allora posso andare a fare doppio gioco
                B.markCell(1,1);
            else
                B.markCell(0,0);
            MC = B.getMarkedCells();
            return MC[0];
        } else if(MC.length == 1) { // se sono secondo
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

        if (B.M * B.N > 25) {
            boolean stop = false;
            MC = B.getMarkedCells();
            if(MC[MC.length-1].i == MC[MC.length-1].j && MC[MC.length-1].i <= B.K) stop = true; // se mi blocca all'inizio mi fermo funziona solo quando vado in diagonale
            if(MC.length > 0 && MC.length < (k*2) && !stop) {  
                int add = 0;
                if(MC.length % 2 == 0){
                    i = MC[0].i;
                    j = MC[0].j;
                } else {
                    i = MC[1].i;
                    j = MC[1].j;
                    add = 3; // si ferma un po' prima quando ?? secondo perch?? cos?? risponde con alphabeta
                }

                for(int w = 1; w < k-add; w++){
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
                eur *= 2; // tolgo 4 da k cos?? esploro di meno il quando K >= 7
            else
                eur = -1;
            if(B.M * B.N > 100)
                eur = 1-k; // depth = 1 se la matrice ?? troppo grande
            if(MC.length > 100) eur = -k;
        } else {
            eur = 2;
        }
        return null;
    }

    
	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
        MNKCell selected;
        double moveValue, max = -10000;
        start = System.currentTimeMillis();

		if(MC.length > 0) {
			MNKCell c = MC[MC.length-1]; // Recover the last move from MC
			B.markCell(c.i,c.j);         // Save the last move in the local MNKBoard
		}

		// if there is just one possible move, return immediately
		if(FC.length == 1) return FC[0];

        selected = strategiaIniziale(FC, MC);
        if (selected != null) return selected;
        
        selected = FC[0];
        // valuto la mossa migliore tramite alphabeta
        MNKCell[] interestingFC = removeUselessCells(B);
        for(MNKCell A : interestingFC) {
            if ((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0))
                break;
            else {
                B.markCell(A.i,A.j);
                moveValue = alphabetaPruning(B,true,k+eur,-1000,1000);
                B.unmarkCell();
                if (moveValue > max) {
                    max = moveValue;
                    selected = A;
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