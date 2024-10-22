/**
 * Eli Giglietti
 * 
 * The purpose of this class is to fill the board and cells with values that will solve the game
 */

import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

public class Sudoku{

    private Board board;
    private LandscapeDisplay ld;
    //private int iterations;

    /**
     * makes a board with n number of cells filled
     * @param n
     */
    public Sudoku(int n){
        this.board = new Board(n);
        this.ld = new LandscapeDisplay(board);
    }

    /**
     * finds if there is a value that would work for the given cell
     * @param cell
     * @return
     */
    public int findNextValue(Cell cell){
        int x = cell.getRow() + 1;
        int y = cell.getCol() + 1;

        for (int i = cell.getValue() + 1; i<10; i++){
            if (board.validValue(x, y, i)){
                return i;
            }
        }
        return 0;

    }

    /**
     * finds an empty cell and finds a value for it
     * @return
     */
    public Cell findNextCell(){
        for (int x = 0; x<9; x++){
            for (int y = 0; y<9; y++){
                if (board.value(x,y) == 0){
                    int val = findNextValue(board.get(x+1, y+1));
                    if (val > 0){
                        board.set(x+1, y+1, val);
                        return board.get(x+1,y+1);
                    }
                    return null;
                }

            }
        }
        return null; 
    }

    /**
     * this solves the game
     * @param delay
     * @return
     */
    public boolean solve(int delay){
        LinkedList<Cell> stack = new LinkedList<>();
        ArrayList<Integer> calculations = new ArrayList<>();
        int iterations = 0;

        
        // if (delay > 0)
        //     Thread.sleep(delay);
        // if (ld != null)
        //     ld.repaint();
        while (stack.size() < 81 - board.numLocked()){
            Cell next = findNextCell();
            //iterations = 0;

            while(next == null && stack.size() > 0){
                Cell cell = stack.pop();
                cell.setValue(findNextValue(cell));
                if (cell.getValue() > 0){
                    next = cell;
                    //System.out.println(board.toString());
                }
                //counts the number of calcutions to solve the board
                iterations ++;
            }

            if (next == null){
                //System.out.println("no solutions");
                return false;
            }
            else{
                stack.push(next);
                
            }
        }
        board.setFinished(true);
        calculations.add(iterations);
        System.out.println(calculations);
        return true;
    }

    /**
     * makes the board a string
     */
    public String toString(){
        return this.board + "";
    }

    /**
     * simulates many iterations of sudoku
     */
    public void simulation(){
        int trials = 5;
        int[] numLocked = {10, 20, 30, 40};
        int counter;
        ArrayList<Integer> results = new ArrayList<>();
        //int[] results = new int[4];

        for (int locked: numLocked){
            counter = 0;
            
            ArrayList<Integer> time = new ArrayList<>();

            for (int i = 0; i<trials; i++){
                Sudoku sudoku = new Sudoku(locked);
                
                boolean solved = sudoku.solve(0);
                if (solved){
                    counter++;
                    //results.add(Integer.valueOf(sudoku.iterations));
                    //System.out.println(locked + " " +  counter);
                }
                //System.out.println(locked + " " +  counter);
            }
           // double percent = counter/trials;
            results.add(counter);
            //results[locked] += counter;
        }
        System.out.println(results);
    }


    public static void main(String[] args){

        Sudoku game = new Sudoku(10);
        // System.out.println(game.toString());
        // game.solve(10);
        // System.out.println(game.toString());

        game.simulation();

        //TESTING METHODS BELOW:
        // Sudoku game = new Sudoku(50);
        // System.out.println(game.toString());
        // Cell nextCell = game.findNextCell();
        // System.out.println(game.findNextValue(nextCell));
        // System.out.println(game.toString());
    }
}

