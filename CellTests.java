/**
 * Eli Gigliett
 * 
 *  The purpose of this class is to test the cell methods
 */ 


public class CellTests{

    public static void main(String[] args){

        Cell newCell = new Cell(10, 10, 15, true);

        //testing getRow
        System.out.println("10 = " + newCell.getRow());

        //testing getCol
        System.out.println("10 = " + newCell.getCol());

        //testing value methods
        System.out.println("15 = " + newCell.getValue());
        newCell.setValue(5);
        System.out.println("assigning a new value of: 5 ");
        System.out.println("5 = " + newCell.getValue());

        //testing out lockedMethods
        System.out.println(newCell.isLocked());
        newCell.setValue(5);
        System.out.println("swithcing locked value");
        System.out.println(newCell.isLocked());
    }
}