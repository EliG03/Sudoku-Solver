import java.io.*;
import java.util.Random;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Eli Giglietti
 * 
 * The prupose of this class is to make a board object  
 */


public class Board{

    Cell[][] board;
    boolean finished;
    public static final int size = 9;

    //constructor
    public Board(){
        board = new Cell[this.size][this.size];
        for (int i = 0; i<9; i++){
            for (int j = 0; j<9; j++){
                board[i][j] = new Cell(i, j, 0, false);
            }
        }
    }

    //constructor when given a file
    public Board(String file){
        read(file);
    }

    /**
     * constructor that makes a board with the given number of cells filled out
     * @param n number of locked cells
     */
    public Board(int n){
        board = new Cell[this.size][this.size];
        for (int i = 0; i<9; i++){
            for (int j = 0; j<9; j++){
                board[i][j] = new Cell(i, j, 0, false);
            }
        }
        Random rand = new Random(); //make random object

        for (int x = 0; x<n; x++){
            int val = rand.nextInt(1,10);
            int r = rand.nextInt(0,9);
            int c = rand.nextInt(0,9);

            if (validValue(r+1, c+1, val) && !(board[r][c].isLocked())) {
                board[r][c].setValue(val);
                board[r][c].setLocked(true);
            }else{
                x--;
            }
        }
    }   

    public void setFinished(boolean fin){
        this.finished = fin;
    }
    
    public boolean read(String filename) {
        try {
        // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
        FileReader fr = new FileReader(filename);
        // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
        BufferedReader br = new BufferedReader(fr);
        
        // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
        String line = br.readLine();
        // start a while loop that loops while line isn't null
        int row = 0;
        while(line != null){
            // print line
        System.out.println( line );
            // assign to an array of Strings the result of splitting the line up by spaces (line.split("[ ]+"))
            String[] arr = line.split( "[ ]+" );
            // let's see what this array holds: 
            System.out.println("the first item in arr: " + arr[0] + ", the second item in arr: " + arr[1]);
            // print the size of the String array (you can use .length)
            System.out.println( arr.length );
            // use the line to set various Cells of this Board accordingly
            for (int col = 0; col<arr.length; col++){
                this.board[row][col].setValue(Integer.parseInt(arr[col]));
            }
            // assign to line the result of calling the readLine method of your BufferedReader object.
            line = br.readLine();
            row++;
        }
        // call the close method of the BufferedReader
        br.close();
        return true;
        }
        catch(FileNotFoundException ex) {
        System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
        System.out.println("Board.read():: error reading file " + filename);
        }

        return false;
    }


    public String toString(){

        String str = "";
        for (int r = 0; r<9; r++){

            for (int c = 0; c<9; c++){
                str += board[r][c];
                if ((c + 1) % 3 == 0 && (c+1) != 9){
                    str += " | ";
                }
            }

            if ((r + 1) % 3 == 0 && (r+1) != 9){
                str += "\n";
                str += "-----------------------------------";
                
            }
            str += ("\n");
        }
        
        return str;
    }

    /**
     * gets the number of columns 
     * @return
     */
    public int getCols(){
        return size;
    }

    /**
     * gets the number of rows
     * @return
     */
    public int getRows(){
        return size;
    }

    /**
     * gets the cell at the given row and column
     * @param r
     * @param c
     * @return
     */
    public Cell get(int r, int c){
        return board[r-1][c-1];
    }

    /**
     * returns if the cell at the given row and column is locked
     * @param r
     * @param c
     * @return
     */
    public boolean isLocked(int r, int c){
        return board[r][c].isLocked();
    }

    public int numLocked(){
        int numLocked = 0;
        //iterates through all rows and columns
        for (int r = 0; r<this.getRows(); r++){ 
            for (int c = 0; c<this.getCols(); c++){
                if (isLocked(r, c)){
                    numLocked ++;
                }
            }
        }
        return numLocked;
    }

    /**
     * returns the value of the cell at the given row and column 
     * @param r
     * @param c
     * @return
     */
    public int value(int r, int c){
        Cell cell = board[r][c]; //makes a cell at the given row and column
        return cell.getValue();
    }

    /*
     * sets the cell at the given r and c to the given value
     * give an r and c between 1-9
     */
    public void set(int r, int c, int value){
        board[r-1][c-1].setValue(value);
    }

    /**
     * sets the cell to the given value and to the given locked value (T/F)
     * give an r and c between 1-9
     * @param r
     * @param c
     * @param value
     * @param locked
     */
    public void set(int r, int c, int value, boolean locked){
        board[r-1][c-1].setValue(value);
        board[r-1][c-1].setLocked(locked);
    }


    /**
     * checks if the value is valid based on the rules of sudoku
     * will adjust the input rows to be aligned with the correct peices of the board
     * @param row give a number 1-9
     * @param col give a number 1-9
     * @param value give a value 1-9
     * @return
     */
    public boolean validValue(int row, int col, int value){
        //checks if value is out of range
        if (value > 9 || value < 1){ 
            return false;
        }

        //iterates through each row in the given column
        for (int r = 0; r<getRows(); r++){
            if (value == board[r][col-1].getValue()){
                return false;
            }
        } 
        
        //iterates through each column in the given row
        for (int c = 0; c<getCols(); c++){
            if (value == board[row-1][c].getValue()){
                return false;
            }
        }


        if ((row) % 3 == 0){ //if on row 3, 6, 9.
            for (int r = 3; r>0; r--){ //iterates through each row
                if (col % 3 == 0){ //checks the column for 3, 6, and 9
                    for (int c = 3; c>0; c--){ //iterates through column
                        if (value == board[row-r][col-c].getValue()){ //checks value
                            return false;
                        }
                    }
                }
                if ((col) % 3 == 2){ //checks for col 2, 5, 8
                    for (int c = 2; c>-1; c--){
                        if (value == board[row-r][col-c].getValue()){
                            return false;
                        }
                    }
                }
                if ((col) % 3 == 1){ //checks for col 1, 4, 7
                    for (int c = 1; c>-2; c--){
                        if (value == board[row-r][col-c].getValue()){
                            return false;
                        }
                    }
                }
            }
        }

        //repeats above process but for different rows
        if ((row) % 3 == 2){ //if on row 2, 5, 8.
            for (int r = 2; r>-1; r--){ //iterates through each row
                if ((col) % 3 == 0){ //checks the column for 3, 6, and 9
                    for (int c = 3; c>0; c--){ //iterates through column
                        if (value == board[row-r][col-c].getValue()){ //checks value
                            return false;
                        }
                    }
                }
                if ((col) % 3 == 2){ //checks for col 2, 5, 8
                    for (int c = 2; c>-1; c--){
                        if (value == board[row-r][col-c].getValue()){
                            return false;
                        }
                    }
                }
                if ((col) % 3 == 1){ //checks for col 1, 4, 7
                    for (int c = 1; c>-2; c--){
                        if (value == board[row-r][col-c].getValue()){
                            return false;
                        }
                    }
                }
            }
        }


        //checks the last set of rows
        if ((row) % 3 == 1){ //if on row 1, 4, 7.
            for (int r = 1; r>-2; r--){ //iterates through each row
                if ((col) % 3 == 0){ //checks the column for 3, 6, and 9
                    for (int c = 3; c>0; c--){ //iterates through column
                        if (value == board[row-r][col-c].getValue()){ //checks value
                            return false;
                        }
                    }
                }
                if ((col) % 3 == 2){ //checks for col 2, 5, 8
                    for (int c = 2; c>-1; c--){
                        if (value == board[row-r][col-c].getValue()){
                            return false;
                        }
                    }
                }
                if ((col) % 3 == 1){ //checks for col 1, 4, 7
                    for (int c = 1; c>-2; c--){
                        if (value == board[row-r][col-c].getValue()){
                            return false;
                        }
                    }
                }
            }
        }
 
        //if it gets through the loops, the value is valid
        return true;
    }


    /**
     * chekcks if there is a valid solution
     * @return
     */
    public boolean validSolution(){
        for (int r = 1; r<10; r++){
            for (int c = 1; c<10; c++){
                if (value(r,c) == 0){
                    return false;
                }
                if (!(validValue(r, c, value(r,c)))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *This helps draw the board
     * @param g
     * @param scale
     */
    public void draw(Graphics g, int scale){
        for(int i = 0; i<getRows(); i++){
            for(int j = 0; j<getCols(); j++){
                get(i+1, j+1).draw(g, j*scale+5, i*scale+10, scale);
            }
            g.drawLine(0,80,260,80);
            g.drawLine(0, 170, 260, 170);
            g.drawLine(85, 0, 85, 260);
            g.drawLine(170, 80, 260, 80);

        } if(finished){
            if(validSolution()){
                g.setColor(new Color(0, 127, 0));
                g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale*3+5, scale*10+10);
            } else {
                g.setColor(new Color(127, 0, 0));
                g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale*3+5, scale*10+10);
            }
        }
    }

    public static void main(String[] args){
        Board sudokuBoard = new Board(50);
        //sudokuBoard.read(args[0]);
        System.out.println(sudokuBoard.toString());
        

        //OLD MAIN FUNCTION to test some methods
        // System.out.println(sudokuBoard.value(0, 3));
        // sudokuBoard.set(0, 0, 5);
        // sudokuBoard.set(1,1, 5, true);
        // System.out.println("this is locked: " + sudokuBoard.isLocked(1,1));
        // System.out.println("1 = " + sudokuBoard.numLocked());
        // System.out.println(sudokuBoard.toString());


        //TESTING VALID VALUE USING BOARD 1
        Board board = new Board(); 
        board.read(args[0]); 
        System.out.println(board);
        System.out.println("should be false: " +  board.validValue(1,1,3)); 
        System.out.println("should be false: " +  board.validValue(1,1,1)); 
        System.out.println("should be true: " +  board.validValue(2,2,5));
        System.out.println("should be false: " + board.validSolution()); 
        System.out.println("should be true: " +  board.validValue(1,2,8));
        board.set(1,1,8); 
        System.out.println("should be false: " +  board.validValue(1,2,8));
        System.out.println("should be 8: " +  board.value(0,0));

        
    }    

}

