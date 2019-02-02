/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queenproblem;

/**
 *
 * @author nickw
 */
public class Board {
    
    private int[][] board;
    private int size;

    /**
     * initialize board as a square board. 
     * @param size the size of the board as well as the number of queens
     */
    public Board(int size) {
        this.size = size;
        board = new int[size][size];
    }

    /**
     * initializes board with a given boardS
     * @param board 
     */
    public Board(int[][] board){
        this.board  = board;
        this.size = board.length;
    }
    
    /**
     * returns board size
     * @return 
     */
    public int size(){
        return size;
    }
    
    /**
     * 
     * @return 
     */
    public int[][] arrayMap(){
        int[][] temp = new int[size][size];
    }
    
    /**
     * gets the element with given row, col
     * @param row
     * @param col
     * @return 
     */
    public int get(int row, int col){
        return board[row][col];
    }
    
    /**
     * sets the given element to val
     * @param row
     * @param col
     * @param val 
     */
    public void set(int row, int col, int val){
        board[row][col] = val;
    }
    
    /**
     * mark all the places that the current Queen piece can attack
     * used in incremental formulation to build the board piece by piece
     * @param row row of Queen piece
     * @param col col of Queen piece
     */
    public void markInvalidPosition(int row, int col) {

        /*
        starting from the index to the top left of the current element, loop through 
        every i,j value around the Queen's position in a 3*3 square.
        i, j values are treated as offsets to be added to a pointer to the element 
        being inspected. So the element being inspected extends outwards in the direction of the i, j
        */
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                
                //if offset is pointing at the center element, or the Queen's position, move on to next iteration
                if(j == i && j  == 0){
                    continue;
                }
                
                //initialize pointer to the element to be the Queen's position
                int currRow = row + i, currCol =col + j;
                //while currRow, currCol are still in bound of board
                while(currRow >= 0 && currRow < board.length &&
                      currCol >= 0 && currCol < board.length  ){
                    
                    if(board[currRow][currCol] != 2)
                        //mark the element on the path extending outward 1 if its currently not 2(occupied)
                        board[currRow][currCol] = 1;
                    else
                        //otherwise another piece of Queen must blocked, and breaks while loop
                        break;
                    
                    //update currRow, currCol
                    currRow += i;
                    currCol += j;
                }
            }
        }
        
    }
    
    /**
     * return a printed chart of each element
     * @return 
     */
    @Override
    public String toString(){
        String str = "";
        
        for(int[] row : board){
            for(int element : row)
                str += " " + element;
            str += "\n";
        }
        return str;
    }
    
}
