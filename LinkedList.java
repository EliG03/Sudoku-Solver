import java.util.Iterator; // defines the iterator interface
import java.util.ArrayList;
import java.util.Collections; // contains a shuffle function

/**
 * Eli Giglietti
 * 
 * The purpose of this class is to make a linked list 
 */

public class LinkedList<T> implements Stack<T>, Iterable<T>{ //note: T is generic object that will be filled when called

    private class LLIterator implements Iterator<T>{
        private Node<T> traversal;

        public LLIterator(Node<T> head){
            this.traversal = head;
        }

        /**
         * Checks if there is a next item
         */
        public boolean hasNext(){
            if (traversal != null){
                return true;
            }
            else{
                return false;
            }
        }

        /**
         * returns the current node and moves foward by one node
         * @return
         */
        public T next(){
            T toReturn = traversal.field;
            traversal = traversal.next;
            return toReturn;
        }

        public void remove(){

        }
    }

    private class Node<T>{

        private Node<T> next; //path to next node
        private T field; // what node contains

        public Node(T item){
            field = item;
            next = null;
        }

        /**
         * returns the data of the node
         */
        public T getData(){
            return this.field;
        }

        /**
         * sets what the next node is for a given node
         */
        public void setNext(Node<T> n){
            this.next = n;
        }

        /**
         * returns the next item of the linked list
         */
        public Node<T> getNext(){
            return this.next;
        }

    }

    private int numNode;
    private Node<T> head; 

    public LinkedList(){
        head = null;
        numNode = 0;
    }

    /**
     * adds an item to the beginning of the linked list
     */
    public void add(T item){ //inserts item at beginning of field
    
        Node<T> newNode = new Node<T>(item);
        newNode.next = head;
        head = newNode;
        numNode++;
    }

    /**
     * adds an item to a given index 
     * @return
     */
    public void add(int index, T item){
        Node<T> newNode = new Node<T>(item);
        if (index==0){
            add(item);
            return;
        }

        Node<T> walker = head;
        for (int i = 0; i<index-1; i++){
            walker = walker.next;
        }
        newNode.next = walker.next;
        walker.next = newNode;
        numNode++;

    }

    /**
     * clears the linked list
     */
    public void clear(){
        Node<T> newNode = new Node<T>(null);
        head = newNode;
        numNode = 0;
        // head.item = null;
        // head.next = null;
    }

    /**
     * checks if a given object is in the list
     */
    public boolean contains(Object o){
        Node<T> walker = head;
        for (int i = 0; i<numNode; i++){ //iterates through whole list
            if (walker.field.equals(o)){ //checks if they are the same
                return true;
            }
            walker = walker.next; //moves foward if not equal
        }
        return false;

    }

    /**
     * checks if a given list has the same items in the same order as the linked list
     * @param o
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o){

        if (!(o instanceof LinkedList)){
            return false;
        }

        LinkedList oAsALinkedList = (LinkedList) o;

        if (this.numNode != oAsALinkedList.numNode){
            return false;
        }

        Node<T> myWalker = this.head;
        Node<T> theirWalker = oAsALinkedList.head;
        for (int i=0; i<numNode; i++){ //iterates through the list
            if (myWalker.field != theirWalker.field){ //checks if they are not the same
                return false;
            }
            //moves both walkers up one index
            myWalker = myWalker.next;
            theirWalker = theirWalker.next;
        }
        return true;
    }

    /**
     * gets the item at the given index
     */
    public T get(int index){
        Node<T> walker = head;
        for (int i=0; i<index; i++){ //walks to the index given
            walker = walker.next;
        }
        return walker.field;
    }

    /**
     * checks if the list is empty
     */
    public boolean isEmpty(){
        if (numNode == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * removes the first item of the list
     */
    public T remove(){
        Node<T> removed = head;
        head = head.next;
        numNode--;
        return removed.field;

    }

    /**
     * removes an item at the given index
     */
    public T remove(int index){
        if (index==0){
            return remove(); //just does the other remove method if you want to get rid of the head
        }
        Node<T> walker = head;
        for (int i=0; i<index-1; i++){ //goes to the item before the index
            walker = walker.next;
        }
        Node<T> removed = walker.next;
        walker.next = walker.next.next;
        numNode--;
        return removed.field;
    }

    /**
     * resturns the size of the list
     */
    public int size(){
        return numNode;
    }

    /**
     * makes the list a list of numbers in the terminal 
     */
    public String toString(){
        String statement = "[";
        Node walker = head;
        for (int i=0; i< numNode; i++){
            // if (i == numNode-1){
            //     statement += walker.field + "]";
            // }
            statement += walker.field + ", ";
            walker = walker.next;
        }
        statement += "]";
        return statement;
    }

    public Iterator<T> iterator(){
            return new LLIterator(this.head);
    }

    /**
     * converts the linked list to an array list
     */
    public ArrayList<T> toArrayList(){
        ArrayList<T> arrayList = new ArrayList<>();

        Node<T> traversal = head;
        while (traversal != null){
            arrayList.add(traversal.field);
            traversal = traversal.next;
        }
        return arrayList;
    }


    //Implementing the Stack methods

    /*
     * gets the first item of the list and returns it
     */
    public T peek(){
        return head.getData();
    }

    public T pop(){
        return remove(0);
    }

    public void push(T item){
        add(item);
    }

    public static void main(String[] args){
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.push(1);
        list.push(2);
        System.out.println(list);
        System.out.println(list.pop());
        System.out.println(list);
    }




    
}
       