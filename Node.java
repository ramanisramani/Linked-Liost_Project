//Shreya S Ramani netID:ssr210006

//creating the generic node for the linked list
public class Node<T> {
    private int row;
    private char seat;
    private boolean reserved;
    private char ticketType;

    //create the row
    public Node() {
        this.setRow(0);
        this.setSeat(Character.MIN_VALUE);
        this.setReserved(false);
        this.setType(Character.MIN_VALUE);
    }

    public Node(int r, char s, boolean res, char type){
        this.setRow(r);
        this.setSeat(s);
        this.setReserved(res);
        this.setType(type);
    }

   //getter functions
    public int getRow() { return this.row; }
    public char getSeat() { return this.seat; }
    public boolean getReserved() { return this.reserved; }
    public char getTicketType() { return this.ticketType; }

    public void setRow(int r) {this.row = r; }
    public void setSeat(char s) {this.seat = s; }
    public void setReserved(boolean res) {this.reserved = res; }
    public void setType(char type) { this.ticketType = type; }
}
