//Shreya S Ramani netID:ssr210006

import java.io.File;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.LinkedList;

public class Auditorium {

    private Seat first;      // pointer to list head

    private static final double ADULT_PRICE = 10.00;
    private static final double CHILD_PRICE = 5.00;
    private static final double SR_PRICE = 7.50;

    private static final String FILE_NAME = "A1.txt";

    public Auditorium()  {          // default constructor - empty auditorium
        this.setFirst(null);
    }
    public Auditorium(Scanner input)  {     // overloaded constructor

        LinkedList<Seat> list = new LinkedList<>();

        int numRows = 0;                            // row placeholder
        int numCols;                                // column placeholder
        String currentRow = null;                   // track rows to post-fact assign seats
        String previousRow = null;
        boolean reserved;                           // indicates empty seat or not

        while (input.hasNextLine())  {              // iterate through each theater row

            Seat previousSeat = null;
            Seat up, left;
            currentRow = input.nextLine();          // save entire row as string
            numCols = currentRow.length();          // save num. of theater columns

            for (int col = 0; col < numCols; col++)  {

                if (currentRow.charAt(col) == '.')  {       // if seat is empty
                    reserved = false;
                }
                else
                    reserved = true;

                if (col == 0)
                    left = null;
                else  {
                 
                    left = previousSeat;
                }

                if (numRows == 0)        // no possible seat above
                    up = null;
                else  {                 // same exact column but in above row
                    up = list.get(list.size() - numCols);
                }
                Seat newSeat = new Seat(numRows, colToChar(col), reserved, currentRow.charAt(col),
                                        up, null, left, null);
                list.add(newSeat);

                if ((numRows == 0) && (newSeat.getSeat() == 'A'))  {
                    this.first = newSeat;
                }

                if (previousSeat != null)  {    // not first iteration / first seat in col
                    previousSeat.setRightSeat(newSeat);
                }

                if (previousRow != null)  {
                    list.get(list.size() - numCols - 1).setDownSeat(newSeat);
                }

                previousSeat = newSeat;
            }

            numRows++;                          // increment to next row

            previousRow = currentRow;           // save previous row for later use
        }

    }

    public Seat getFirst()  {  return this.first;  }

    public void setFirst(Seat seat)  {  this.first = seat;  }

    public void displayAuditorium()  {

        
        System.out.println();
        System.out.print("  "); // align columns
        for (int firstCol = 65; firstCol < getNumCols() + 65; firstCol++) {
            System.out.print((char) firstCol);
        }
        System.out.println();

        Seat current = this.getFirst();

        int currentRow = 0;
        while (current != null)  {
            System.out.print((currentRow + 1) + " ");

            while (current != null) {
                if (current.getTicketType() != '.')
                    System.out.print('#');
                else
                    System.out.print('.');

                current = current.getRightSeat();
            }
            currentRow++;

            current = this.getFirst();
            for (int k = 0; k < currentRow; k++)  {
                current = current.getDownSeat();
            }

            System.out.println();
        }
        System.out.println();
    }
    
    //display the final report of the purchase
    public void displayReport()  {
        int totalSold = 0, adultSold = 0, childSold = 0, srSold = 0;

        Seat current = this.getFirst();
        int currentRow = 0;
        while (current != null)  {
            while (current != null) {
                if (Character.isLetter(current.getTicketType()))  {
                    totalSold++;

                    // check whether seat is adult, child, or senior
                    switch (current.getTicketType()) {
                        case 'A':           // indicates adult seat
                            adultSold++;
                            break;
                        case 'C':           // indicates child seat
                            childSold++;
                            break;
                        case 'S':           // indicates senior seat
                            srSold++;
                            break;
                        default:
                            System.out.println("Seat data has been corrupted.");
                            break;

                    }
                }
                current = current.getRightSeat();
            }

            currentRow++;

            current = this.getFirst();
            for (int k = 0; k < currentRow; k++)  {
                current = current.getDownSeat();
            }

        }
        System.out.println();
        
        NumberFormat money = NumberFormat.getCurrencyInstance(Locale.US);
        double totalSales = (adultSold * ADULT_PRICE) + (childSold * CHILD_PRICE) + (srSold * SR_PRICE);
        String sales = money.format(totalSales);

        System.out.println("Total Seats: " + (this.getNumCols() * this.getNumRows()));
        System.out.println("Total Tickets: " + totalSold);
        System.out.println("Adult Tickets: " + adultSold);
        System.out.println("Child Tickets: " + childSold);
        System.out.println("Senior Tickets: " + srSold);
        System.out.println("Total Sales: " + sales);
    }
    //~~~~~~~~~~~~~~~~Making the Grid~~~~~~~~~~~~~~~~~~
    public int getNumCols()  {
        int numCols = 0;
        Seat current = this.getFirst();
        while (current != null)  {
            numCols++;
            current = current.getRightSeat();
        }

        return numCols;
    }
    public int getNumRows()  {
        int numRows = 0;
        Seat current = this.getFirst();
        while (current != null)  {
            numRows++;
            current = current.getDownSeat();
        }

        return numRows;
    }

    public char colToChar (int col){
        return (char) (col + 65);
    }

    private int charToCol (char seat){
        return (int) (Character.toUpperCase(seat)) - 65;
    }

   //row validation
    public boolean invalidRow(String row)  {

        for (int k = 0; k < row.length(); k++)  {
            if (!Character.isDigit(row.charAt(k)))
                return true;
        }
        if ((Integer.parseInt(row) > getNumRows()) || (Integer.parseInt(row) < 0))
            return true;
        else
            return false;
    }

   //seat validation
    public boolean invalidSeat(String seat)  {

        if (seat.length() != 1)
            return true;

        return ((charToCol(seat.charAt(0)) >= getNumCols()) || (!Character.isLetter(seat.charAt(0))));
    }

   //ticket validation
    public boolean invalidTix(String num)  {

        for (int k = 0; k < num.length(); k++)  {
            if (!Character.isDigit(num.charAt(k)))
                return true;
        }

        return (Integer.parseInt(num) < 0);
    }

   //check if amount of seats and seat is available
    public boolean checkAvailable(int row, char seat, int totalTix)  {
        Seat current = this.getFirst();

        for (int k = 0; k < row; k++)  {
            current = current.getDownSeat();
        }

        for (int k = 0; k < charToCol(seat); k++)  {
            current = current.getRightSeat();
        }

        for (int k = 0; k < totalTix; k++)  {
            if (current.getTicketType() != '.')
                return false;
            else  {
                // check if at end of row
                if (charToCol(current.getSeat()) >= (getNumCols() - 1))
                    return false;
                else
                    current = current.getRightSeat();   // analyze next seat
            }

        }

        return true;    //  only reached if all seats available
    }

    public boolean checkAvailable(Seat seat, int totalTix)  {

        Seat startMark = seat;

        for (int k = 0; k < totalTix; k++)  {
            if (seat.getTicketType() != '.')    // check if seat is empty
                return false;
            else  {
                // check if at end of row
                if (charToCol(seat.getSeat()) >= (getNumCols() - 1))
                    return false;
                else
                    seat = seat.getRightSeat();     // iterate to check next seat
            }

        }

        return true;    // only reached if all seats available
    }
    
    //reserve the chose seats
    public void reserveSeats(int row, char seat, int numAdult, int numChild, int numSr)  {

        Seat current = this.getFirst();

        for (int k = 0; k < row; k++)  {
            current = current.getDownSeat();
        }

        for (int k = 0; k < charToCol(seat); k++)  {
            current = current.getRightSeat();
        }

        for (int k = 0; k < numAdult; k++)  {
            current.setType('A');
            current = current.getRightSeat();
        }

        for (int k = 0; k < numChild; k++)  {
            current.setType('C');
            current = current.getRightSeat();
        }

        for (int k = 0; k < numSr; k++)  {
            current.setType('S');
            current = current.getRightSeat();
        }

        try  {
          
            PrintWriter output = new PrintWriter(new File(FILE_NAME));

            current = this.getFirst();

            int currentRow = 0;

            while (current != null) {
                // print seat by seat
                while (current != null)  {
                    output.print(current.getTicketType());
                    current = current.getRightSeat();
                }
                currentRow++;           // track num of rows
                output.print("\n");     // print next row

                // move head down (num. of row) times
                current = this.getFirst();
                for (int k = 0; k < currentRow; k++)  {
                    current = current.getDownSeat();
                }
            }

            output.close();
            System.out.println("\nYour reservation is confirmed! ");
        }
        catch (Exception FileNotFoundException)  {
            System.out.println("Cannot save reservation to file");
        }


    }

   // the best seat conditions
    public Seat bestAvailable(int totalTix) {
    Seat starter = this.getFirst();
    Seat bestSeat = null;
    double dist = distance(-1, -1);
    int currentRow = 0;

    while (starter != null) {
        while (starter != null) {
            if (checkAvailable(starter, totalTix)) {
                double tempDist = distance(center(starter, totalTix), (double) currentRow);
                if (tempDist < dist || bestSeat == null) {
                    dist = tempDist;
                    bestSeat = starter;
                } else if (tempDist == dist) {
                    double centerRow = (getNumRows() - 1) / 2.0;

                    int diffStarter = (int) Math.abs(starter.getRow() - centerRow);
                    int diffBestSeat = (int) Math.abs(bestSeat.getRow() - centerRow);

                    if (diffStarter < diffBestSeat) {
                        bestSeat = starter;
                    }
                }
            }

            starter = starter.getRightSeat();
        }

        currentRow++;

        starter = this.getFirst();

        for (int k = 0; k < currentRow; k++) {
            starter = starter.getDownSeat();
        }

        if (starter != null && starter.getRightSeat() == null) {
            // Handle end of the row
            Seat nextRowStarter = this.getFirst();
            for (int k = 0; k < currentRow + 1; k++) {
                nextRowStarter = nextRowStarter.getDownSeat();
            }

            if (nextRowStarter == null) {
                // No more rows, so there are no other seat options
                return bestSeat;
            } else {
                currentRow++;
                starter = nextRowStarter;
            }
        }
    }

    return bestSeat;
}


   //using distance formula to calculate the best seat
    public double distance(double x, double y)  {

        double dist = 0.0;
        double centerX = (getNumCols() - 1) / 2.0;
        double centerY = (getNumRows() - 1) / 2.0;

        dist += Math.pow(x - centerX, 2.0);
        dist += Math.pow(y - centerY, 2.0);
        dist = Math.sqrt(dist);

        return dist;
    }

   //finding the center of the auditorium
    public double center(Seat starter, int totalTix)  {
        int index1 = charToCol(starter.getSeat());
        int index2 = index1 + totalTix - 1;

        return (index1 + index2) / 2.0;
    }
}
