//Shreya S Ramani netID:ssr210006

//creating the seat directions to iterate for the linked list
public class Seat extends Node<Seat> {

    private Seat up;
    private Seat down;
    private Seat left;
    private Seat right;

    public Seat()  {         // default constructor
        super();
        this.setUpSeat(null);
        this.setDownSeat(null);
        this.setLeftSeat(null);
        this.setRightSeat(null);
    }

      //setting user seat choice
    public Seat(int r, char s, boolean res, char type,
                       Seat uSeat, Seat dSeat, Seat lSeat, Seat rSeat)  {
        super(r, s, res, type);
        this.setUpSeat(uSeat);
        this.setDownSeat(dSeat);
        this.setLeftSeat(lSeat);
        this.setRightSeat(rSeat);
    }

   //set up validation method
    public void setUpSeat(Seat seat)  {  this.up = seat;  }
    public void setDownSeat(Seat seat)  {  this.down = seat;  }
    public void setLeftSeat(Seat seat)  {  this.left = seat;  }
    public void setRightSeat(Seat seat)  {  this.right = seat;  }

 
    public Seat getUpSeat()  {  return this.up;  }
    public Seat getDownSeat()  {  return this.down;  }
    public Seat getLeftSeat()  {  return this.left;  }
    public Seat getRightSeat()  {  return this.right;  }

}
