import java.util.ArrayList;

/**
 *
 * @author Mbassip2
 */
public class Othello {

    public static final int SQUARESIZE= 60;   // Basic dimensions of board
    public static final double PIECERATIO= 0.4; // ration of radius of piece to square size
    public static final int xBOARDpos= 100;   // Position of board
    public static final int yBOARDpos= 100;   // Position of board
    public static final int xMARGIN= 50;   // Position of board
    public static final int yMARGIN= 50;   // Position of board
    public static final int searchDepth= 8;   // Depth of minimax search
 
    public static int mat[][] = {   {120, -20, 20, 5, 5, 20, -20, 120},
                     				{-20, -40, -5, -5, -5, -5, -40, -20},
                     				{20, -5, 15, 3, 3, 15, -5, 20},
                     				{5, -5, 3, 3, 3, 3, -5, 5},
                     				{5, -5, 3, 3, 3, 3, -5, 5},
                     				{20, -5, 15, 3, 3, 15, -5, 20},
                     				{-20, -40, -5, -5, -5, -5, -40, -20},
                     				{120, -20, 20, 5, 5, 20, -20, 120},
                     				  };

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        BoardState initialState= new BoardState();
        initialState.setContents(3, 3, 1);
        initialState.setContents(3, 4, -1);
        initialState.setContents(4, 3, -1);
        initialState.setContents(4, 4, 1);
        initialState.colour= -1;              // Black to start
        
        OthelloDisplay othelloDisplay= new OthelloDisplay();
        othelloDisplay.boardState= initialState;
        othelloDisplay.repaint();
    }
    
    
    
    public static Move chooseMove(BoardState boardState){

        ArrayList<Move> moves= boardState.getLegalMoves();
        if(moves.isEmpty())
            return null;
        // participant version: replace this line with the following code
	// and provide the routines as directed in the lab exercise script.
        // return moves.get(0);
	 
        return toplevel(boardState, moves);
        
    }

    public static int staticEvaluation(BoardState boardState){
    	int value = 0;
    	for(int i=0; i<8; i++){
    		for(int j=0; j<8;j++){
    			if(boardState.getContents(i, j)==1){
    				value+=mat[i][j];
    			}
    			else if(boardState.getContents(i,j)==-1){
    				value-=mat[i][j];
    			}
    		}
    	}
    		return value;
    } 

    public static int miniMax(BoardState boardState, int searchDepth, int alpha, int beta, Boolean max_player){
    	ArrayList<Move> move = boardState.getLegalMoves();

    	if(searchDepth==0){
    		return staticEvaluation(boardState);
    	}

    	else if(max_player){
    		alpha = Integer.MIN_VALUE;
    		for(int i =0 ; i<move.size();i++){
    			if(alpha >= beta||move.isEmpty()){
    				break;
    			}
    			BoardState temp = boardState.deepCopy();
    			temp.makeLegalMove(move.get(i).x, move.get(i).y);
    			int miniVal = miniMax(temp, searchDepth-1, alpha, beta, false);
    			if(alpha<miniVal){
    				alpha=miniVal;
    			}
    		}
    		return alpha;
    	}
    	else{
    		beta=Integer.MAX_VALUE;
    		for(int i = 0;i<move.size();i++){
    			if(alpha >= beta||move.isEmpty()){
    				break;
    			}
    			BoardState temp = boardState.deepCopy();
    			temp.makeLegalMove(move.get(i).x, move.get(i).y);
    			int maxVal = miniMax(temp, searchDepth-1, alpha, beta, true);
    			if(maxVal<beta){
    				beta=maxVal;
    			}
    		}
    		return beta;

    	}
    }

    public static Move toplevel(BoardState bs, ArrayList<Move> move){
    	int valueIndex = 0;
    	int a = Integer.MIN_VALUE;
    	int b = Integer.MAX_VALUE;
    	Move bestmove = null;

    	for(int i=0; i<move.size();i++){
    		BoardState temp = bs.deepCopy();
    		temp.makeLegalMove(move.get(i).x, move.get(i).y);
    		int minimax = miniMax(temp, searchDepth-1, a, b, false);
    		if(a<minimax){
    			a=minimax;
    			valueIndex = i;
    		}
    	}
    	bestmove = move.get(valueIndex);
    	return bestmove;
    }
}
