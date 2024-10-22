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
 * Eli Gigietti
 * 
 * The purpose of this class is to be a cell object on the sudoku board
 */


public class Cell {

    private int row;
    private int col;
    private int value;
    private boolean locked;

    /**
     * initiates the fields to 0 or false
     */
    public Cell(){
        this.row = 0;
        this.col = 0;
        this.value = 0;
        this.locked = false;
    }

    /**
     * constrcuts the cell with more fields
     * @param status
     */
    public Cell(int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
    }

    /**
     * constructs the cell and assigns each feild to the given one
     * @param status
     */
    public Cell(int row, int col, int value, boolean locked){
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }

    /**
     * gets the row of this cell
     */
    public int getRow(){
        return this.row;
    }

    /**
     * gets the column of the cell
     * @return
     */
    public int getCol(){
        return this.col;
    }

    /**
     * gets the value of the cel
     * @return
     */
    public int getValue(){
        return this.value;
    }
    
    /**
     * assigns a new value to the cell
     */
    public void setValue(int newVal){
        this.value = newVal;
    }

    /**
     * returns wether the cell is locked or no
     */
    public boolean isLocked(){
        return this.locked;
    }

    /**
     * assigns a new lock value, true or false
     */
    public void setLocked(boolean lock){
        this.locked = lock;
    }


    public void draw(Graphics g, int x, int y, int scale){
        char toDraw = (char) ((int) '0' + getValue());
        g.setColor(isLocked()? Color.BLUE : Color.RED);
        g.drawChars(new char[] {toDraw}, 0, 1, x, y);
    }

    public String toString(){
        String str = new String();
        str = "[" + this.getValue() + "]";
        return str;
    }
}