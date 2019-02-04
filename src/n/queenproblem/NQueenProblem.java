/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queenproblem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * n pieces of identical, standard chess queen pieces are placed on an imaginary chess board of size n(n*n).
 * each queen must be placed in a way that no single piece can attack another
 * @author nickw
 */
public class NQueenProblem {
    
    //markers on board that represents following condition
    public static final int EMPTY = 0;
    public static final int ATTACKED = 1;
    public static final int QUEEN = 2;
    
    /**
     * Represents the action of placing an queen. Contains the location of the queen to be placed
     */
    private class Action{
        
        private int row, col;
        
        /**
         * 
         * @param row
         * @param col 
         */
        public Action(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        /**
         * 
         * @return 
         */
        @Override
        public String toString(){
            return String.format("row : %d, col : %d", row, col);
        }
        
    }
    
    /**
     * A description of the entire board at any single point.
     */
    private class State{
        
        private Board board;
        //all possible actions
        private LinkedList<Action> actions;

        /**
         * 
         * @param board
         * @param actions
         */
        public State(Board board, LinkedList<Action> actions) {
            this.board = new Board(board.arrayMap());
            this.actions = actions;
        }
         
        /**
         * 
         * @param row
         * @param col
         * @param val 
         */
        public void set(int row, int col, int val){
            board.set(row, col, val);
            board.markInvalidPosition(row, col);
            actions = formulateActions(board);
        }
        
    }
    
    //current state
    private State currState;
    //number of queens left
    private int queens;
    //all past states
    private ArrayList<State> states;
    
    //Debugging settings
    private boolean debugger = false, idle = false;
    
    /**
     * 
     * @param queens number of queens and size of the board
     */
    public NQueenProblem(int queens) {
        this.queens = queens;
        Board board = new Board(queens);
        this.currState = new State(new Board(board.arrayMap()), formulateActions(board));
        states = new ArrayList<>();
    }
    
    /**
     * finds the first solution out of all possible actions
     * @return 
     */
    public Action solve(){
        
        if(states.size() < queens-1){
            //if there is still queens to be placed
            if(!currState.actions.isEmpty()){
                //if possible actions list is not empty, poll the first action
                return currState.actions.poll();
            }else{
                if(states.size() > 0){
                    //if no action can be performed, revert the state to last state
                    log("No actions can be performed, reverting to previous state", states.size());
                    currState = states.remove(states.size()-1);
                    log(this);
                    idle(5000);
                    return solve();
                }else{
                    log("No solution can be found!");
                    System.exit(0);
                    return null;
                }
            }
        }else{
            //return null if a solution is not found
            display("Solution found :" ,this);
            System.exit(0);
            return null;
        }
        
    }
    
    /**
     * solve the entire puzzle
     */
    public void solvePuzzle(){
        
        //while game is not over
        while(queens != 0){
            //updates state with the Action returned from solve()
            updateState(solve());
            //prints board
            log(this);
            
            
        }
    }
    
    /**
     * updates current state and stores it as previous state
     * @param row
     * @param col
     * @return 
     */
    public boolean updateState(int row, int col){
        //checks the value of the position 
        switch(currState.board.get(row, col)){
            case EMPTY:
                //if the location is empty then adds current state to previous states
                states.add(currState);
                log("Moving to next state and adding to state");
                //sets current state to a new copy of current State
                currState = new State(new Board(currState.board.arrayMap()), formulateActions(currState.board));
                //set indicated element to queen
                currState.set(row, col, QUEEN);
                idle(200);
                return true;
            case ATTACKED:
                //if the location is attacked
                log(String.format("Invalid positioning at row: %d, col: %d. \n Current piece is attacked by another queen", row, col));
                return false;
            case QUEEN:
                //if the location is occupied
                log(String.format("Invalid positioning at row: %d, col: %d. \n Current position is occupied by another queen", row, col));
                return false;
            default:
                return false;
        }
    }
    
    /**
     * overloaded call of updateState(int row, int col)
     * @param act
     * @return 
     */
    public boolean updateState(Action act){
        return updateState(act.row, act.col);
    }
    
    /**
     * formulates all possible actions with a given board
     * @param board
     * @return
     */
    public LinkedList<Action> formulateActions(Board board){
        LinkedList<Action> act = new LinkedList<>();
        //finds every spot where a new queen can be placed
        for(int i = 0; i < queens; i++){
            for(int j = 0; j < queens; j++){
                if(board.get(i, j) == 0)
                    act.add(new Action(i, j));
            }
        }
        return act;
    }
    
    /**
     * prints message to console when debugging is enabled
     * @param msg 
     */
    private void log(Object... msg){
        if(debugger) 
            for(Object m : msg)
                System.out.println(m);
    }
    
    /**
     * prints message to console
     * @param msg 
     */
    private void display(Object... msg){
            for(Object m : msg)
                System.out.println(m);
    }
    
    /**
     * idle for time in milisec
     * @param miliSec 
     */
    private void idle(long miliSec){
        if(idle)
            try {
                TimeUnit.MILLISECONDS.sleep(miliSec);
            } catch (InterruptedException ex) {}
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
        return currState.board.toString();
    }
        
}
