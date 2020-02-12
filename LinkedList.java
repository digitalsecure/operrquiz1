
import java.io.Console;

/*
    Design an int type singly linkedlist class, and then implement some functions below using the self designed class.
    Can not use Java built in List interface
    1. Append an element into the linkedlist
    2. Remove the tail element from a linkedlist
    3. Remove all element in the linkedlist that is great than a target value
 */

/**
 * LinkObj is implemented as a default class to be used within this package only.
 * Each instance of LinkObj encapsulates an integer value
 *
 */
class LinkObj {
    LinkObj prevNode = null;
    LinkObj nextNode = null;
    private int value = -1;

    public LinkObj(int value) {
        this.value = value;
    }

    public void linkNodes(LinkObj prev, LinkObj next) {
        this.prevNode = prev;
        this.nextNode = next;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isNodeLesserThan(int value) {
        return this.value < value?true:false;
    }

    public boolean isNodeLesserThan(LinkObj node) {
        return node!=null? isNodeLesserThan(node.getValue()) : false;
    }

    // Override the superclass to return the stringify value
    public String toString() {
        return ""+value;
    }
}

public class LinkedList {

    private LinkObj firstNode;
    private LinkObj lastNode;
    private int count = 0;

    private static LinkedList singleton = new LinkedList();

    /**
     * Private constructor as this is implemented as Singleton pattern.
     *
     */
    private LinkedList() {
        super();
    }


    private int count(LinkObj startNode) {
        LinkObj currNode = startNode;
        int count = 0;
        do {
            count++;
            currNode = currNode.nextNode;
        } while(currNode!=null);
        return count;
    }


    /**
       @returns total number of item in the linked list
     */
    public int count() {
        return firstNode==null?0:count(firstNode);
    }

    /**
     * Quiz Part 1) Append to list.
     *
     * @returns Total number of items in the linked list after adding the integer
     */
    public int addItem(int value) {
        if(this.firstNode==null) {
            // First item in the list
            this.firstNode = new LinkObj(value);
            this.lastNode  = this.firstNode;
        } else {
            LinkObj newNode = new LinkObj(value);
            newNode.prevNode = this.lastNode;
            this.lastNode.nextNode = newNode;
            this.lastNode = newNode;
        }
        return ++ this.count;
    }

    /**
     * Quiz Part 2) Remove last item from list
     * @return Value of the last item from list
     */
     public int removeLast() {
         if(this.lastNode==null) return 0;
         if(this.lastNode.prevNode==null) {
             // this is the first and last node
             this.firstNode = null;
             this.lastNode = null;
             return 0;
         }
         LinkObj prevNode = this.lastNode.prevNode;
         prevNode.nextNode = null; // set the prev node's next node to null;
         this.lastNode = prevNode;

         return --count;
     }

     /**
      * Quiz Part 3) Remove a node item matching the value of the input
      *
      * @value Value of the node to remove
      *
      * @return true if number is found in the list and removed successfully, false otherwise
      */
     public boolean removeOnlyGreaterThan(int value) {
          boolean success = false;
          if(value<=0) {
              removeAll();
          }

          if(count==0) return false; // nothing to return
          
          LinkObj currNode = firstNode;
          while(currNode!=null) {
              // System.out.println("Current node value = "+currNode.getValue()+", Input value = "+value);

              if(currNode.isNodeLesserThan(value)) {
                  // Curr node value is < value, remove this node and update the links
                  
                  LinkObj pNode = currNode.prevNode;
                  LinkObj nNode = currNode.nextNode;
                  if(pNode!=null) {
                      pNode.nextNode = nNode; // link the previous node to the next node
                  } else {
                      this.firstNode = nNode; // curr note is the first note. Move the first node to the next one
                  } 

                  if(nNode!=null) {
                      nNode.prevNode = pNode; // link the next node to the previous node
                  } else {
                      this.lastNode = pNode;  // this is the last node, move the last node to the previous one
                  }
                  count--;
                  success = true;
              }
              currNode = currNode.nextNode;
          } // while loop
          return success;
     }


     /**
      * Removes the entire list
      *
      * @return Total number of items removed.
      */
     public int removeAll() {
         this.firstNode = null;
         this.lastNode = null;
         int currCount = count;
         count = 0;
         return currCount;
     }


    public int getInput(String promptMessage) {
        int input = 0;
        Console console = System.console();

        try {
                input = Integer.parseInt(console.readLine(promptMessage));
        } catch(NumberFormatException nfe) {
        }
        return input;
    }

    public void printList() {
        if(firstNode==null) {
            System.out.println("List is empty");
        } else {
            LinkObj currNode = this.firstNode;
            do {
                System.out.print(currNode+" ");
                currNode = currNode.nextNode;
            } while (currNode!=null);
            System.out.println("");
        }
    }


    public static LinkedList getLinkedList() {
            return singleton;
    }

    

    public static void main(String [] args) {
        LinkedList list = LinkedList.getLinkedList();        
        int selectedOption = 0;
                            
        do {
                try {
                            System.out.println("Please select one of the options: ");
                            System.out.println("1) Add an integer");
                            System.out.println("2) Remove tail (last) item");
                            System.out.println("3) Remove all items > than input");
                            System.out.println("4) List all items");
                            System.out.println("5) Quit");
                            System.out.println("\n"+list.count()+" item(s) in list\n");
                            selectedOption = list.getInput("\nPlease select option: ");

                            switch(selectedOption) {
                                case 1: // Add integer
                                        int num = list.getInput("\nPlease enter an integer number: ");
                                        if(num>0) {
                                            list.addItem(num);
                                        }
                                        break;

                                case 2: // remove tail item
                                        list.removeLast();
                                        break;

                                case 3: // remove item > input number
                                        int minNum = list.getInput("\nEnter a number (all numbers > than this input will be removed. Enter key will remove whole list):  ");
                                        if(minNum==0) {
                                            System.out.println(list.removeAll()+" items removed");
                                        } else {
                                            list.removeOnlyGreaterThan(minNum);
                                        }
                                        break;


                                case 4: // List all items
                                        list.printList();
                                        break;

                                default: // No action required
                            } // Switch

                } catch (NumberFormatException nfe) {
                           // user entered a non numeric
                           selectedOption = 0;
                }
        } while (selectedOption!=5);
        System.out.println("Thank You!");
    }        
        
}