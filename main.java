//Shreya S Ramani

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.FileNotFoundException;

public class Main  {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the file name: ");
        String FILE_NAME = input.nextLine();       //get the file from the user to run the program

        new File("A1.txt");
        String original = FILE_NAME;  //create the ouput file
        String output = "A1.txt";
        try {
            Files.copy(Paths.get(original), Paths.get(output), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

      //initialization
        int adultTix = 0;
        int childTix = 0;
        int srTix = 0;
        int row = 0;
        char seat = Character.MIN_VALUE;
        Scanner inputFile = null;
        boolean exit = false;

        Auditorium grid;                       // used to hold entire auditorium 
        do  {
            // display menu for user
            System.out.println("\nMain Menu:");
            System.out.println("1 - Reserve Seats");
            System.out.println("2 - Exit");
            System.out.print("Please enter your choice: ");
            String choice = input.nextLine();
            try {
                inputFile = new Scanner(new File(output));
            } catch (FileNotFoundException e) {
                System.out.print("Could not find reservation file");
                e.printStackTrace();
                break;
            } 
            //making reservation
            if (choice.equals("1")){                      // if user wants to reserve seats
                grid = new Auditorium(inputFile);                          // close scanner stream
                grid.displayAuditorium();   // display auditorium
                String user = null;
                System.out.println("Please enter reservation information: ");
                do {
                    try {
                        System.out.print("Enter row number: ");
                        user = input.nextLine();
                        if (grid.invalidRow(user))
                            throw new InvalidRowException(user);  //validate
                        else
                            row = Integer.parseInt(user) - 1;   
                    } catch (InvalidRowException e) {
                        System.out.println(e.getInvalidRow() + " is not a valid row ");
                    }
                } while (grid.invalidRow(user));
                do {
                    try {
                        System.out.print("\nEnter starting seat number (as a character A, B, C, etc.): ");
                        user = input.nextLine();         //validate
                        if (grid.invalidSeat(user))
                            throw new InvalidSeatException(user);
                        else
                            seat = user.charAt(0);
                    } catch (InvalidSeatException e) {
                        System.out.println(e.getInvalidSeat() + " is not a valid seat ");
                    }
                } while (grid.invalidSeat(user));
                do {
                    try {
                       //ask user for number of adult tickets
                        System.out.print("\nEnter amount of adult tickets: ");
                        user = input.nextLine();
                        if (grid.invalidTix(user))
                            throw new InvalidTicketException(user);
                        else
                            adultTix = Integer.parseInt(user);
                    } catch (InvalidTicketException e) { 
                        System.out.println(e.getInvalidNumTickets() + " is not a valid ticket quantity ");  //validate
                    }
                } while (grid.invalidTix(user));
                do {
                    try {
                       //ask user for number of child tickets
                        System.out.print("\nEnter amount of child tickets: ");
                        user = input.nextLine();
                        if (grid.invalidTix(user))
                            throw new InvalidTicketException(user);
                        else
                            childTix = Integer.parseInt(user);
                    } catch (InvalidTicketException e) {  
                        System.out.println(e.getInvalidNumTickets() + " is not a valid ticket quantity ");   //validate
                    }
                } while (grid.invalidTix(user));
                do {
                    try {
                       //ask user for number of senior tickets
                        System.out.print("\nEnter amount of senior tickets: ");
                        user = input.nextLine();
                        if (grid.invalidTix(user))
                            throw new InvalidTicketException(user);
                        else
                            srTix = Integer.parseInt(user);
                    } catch (InvalidTicketException e) {  
                        System.out.println(e.getInvalidNumTickets() + " is not a valid ticket quantity ");  //validate
                    }
                } while (grid.invalidTix(user));              
                int totalTix = adultTix + childTix + srTix;
                if (grid.checkAvailable(row, seat, totalTix))
                    grid.reserveSeats(row, seat, adultTix, childTix, srTix);
                else {
                    System.out.println("\nThose seats are currently not available.");
                    Seat nextBest = grid.bestAvailable(totalTix);
                    if (nextBest  == null)
                        System.out.println("\nThere are currently no other seat options. ");
                    else {
                       //ask user if they would like to reserve next best option
                        System.out.print("\nWould you like to reserve at seat " + (nextBest.getRow() + 1) + nextBest.getSeat() +
                                " - " + (nextBest.getRow() + 1) + (char)(nextBest.getSeat() + totalTix - 1) + " instead?");
                        String newRes;
                        do {
                          
                            System.out.print("\nEnter Y or N: ");
                            newRes = input.nextLine();
                            if (newRes.toUpperCase().equals("Y"))   // user indicates yes - reserve
                                grid.reserveSeats(nextBest.getRow(), nextBest.getSeat(), adultTix, childTix, srTix);
                            else if (newRes.toUpperCase().equals("N")) {   // user indicates no - do not reserve
                                System.out.println("Unable to complete reservation.");
                            } else
                                System.out.println("Please enter Y or N.");
                        }
                        while (!((newRes.toUpperCase().equals("Y")) || (newRes.toUpperCase().equals("N"))));
                    }
                }
            }
            //exiting the program
            else if (choice.equals("2")) {
                    grid = new Auditorium(inputFile);
                    
                    grid.displayReport();
                    
                    exit = true;
                }
            else  {
                    System.out.println("\nPlease try again and enter either '1' (Reserve Seats) or '2' (Exit)");
                }
        } while(!exit);       // repeat until user wants to exit
        
        inputFile.close();
        input.close();
    }
}

//~~~~~~~~~~~~~~~~~~~Exception Handling~~~~~~~~~~~~~~~~~~
class InvalidRowException extends Exception  {
    private String invalidRow;
      private static final long serialVersionUID = 1L;
      
    InvalidRowException(String message)  {
        super(message);
        this.invalidRow = message;
    }

    public String getInvalidRow()  {  return this.invalidRow;  }

}
class InvalidSeatException extends Exception  {
    private String invalidSeat;
      private static final long serialVersionUID = 1L;
      
    InvalidSeatException(String message)  {
        super(message);
        this.invalidSeat = message;
    }

    public String getInvalidSeat()  {  return this.invalidSeat;  }
}
class InvalidTicketException extends Exception  {
    private String invalidNumTickets;
    private static final long serialVersionUID = 1L;

    InvalidTicketException(String message)  {
        super(message);
        this.invalidNumTickets = message;
    }

    public String getInvalidNumTickets()  {  return this.invalidNumTickets;  }
}
