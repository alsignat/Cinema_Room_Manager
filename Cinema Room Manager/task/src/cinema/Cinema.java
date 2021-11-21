package cinema;

import java.util.Scanner;
import java.util.Arrays;

public class Cinema {

    String[][] cinema;
    int income;
    boolean opened = true;
    int ticketsSold;
    double ticketsTotal;
    final int maxIncome;

    Cinema(Scanner input) {
        System.out.println("Enter the number of rows:");
        int rows = input.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = input.nextInt();
        this.ticketsTotal = rows * seats;
        this.maxIncome = calculateMaxIncome(rows, seats);
        this.cinema = new String[rows][seats];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(cinema[i], "S");
        }
    }

    public void menu(Scanner input) {
        String menu = "\n1. Show the seats\n"
                + "2. Buy a ticket\n"
                + "3. Statistics\n"
                + "0. Exit";
        System.out.println(menu);
        int userAnswer = input.nextInt();
        switch (userAnswer) {
            case 1:
                showSeats(input);
                break;
            case 2:
                buyTicket(input);
                break;
            case 3:
                showStatistics();
                break;
            case 0:
                this.opened = false;
                break;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Cinema cinema = new Cinema(input);
        while (cinema.opened) {
            cinema.menu(input);
        }
    }

    private void showSeats(Scanner input) {
        String header = "\nCinema:\n";
        for (int j = 0; j < cinema[0].length; j++) {
            header += " " + (j + 1);
        }
        System.out.println(header);
        for (int i = 0; i < cinema.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cinema[0].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void buyTicket(Scanner input) {
        System.out.println("\nEnter a row number:");
        int rowChosen = input.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatChosen = input.nextInt();
        if (rowChosen < 1 || seatChosen < 1 || rowChosen > cinema.length || seatChosen > cinema[0].length) {
            System.out.println("\nWrong input!");
            buyTicket(input);
        } else if (cinema[rowChosen - 1][seatChosen - 1].equals("B")) {
            System.out.println("\nThat ticket has already been purchased!");
            buyTicket(input);
        } else {
            System.out.print("Ticket price: $");
            int ticket = cinema.length * cinema[0].length > 60 && rowChosen - 1 >= cinema.length / 2 ? 8 : 10;
            System.out.println(ticket);
            cinema[rowChosen - 1][seatChosen - 1] = "B";
            ticketsSold++;
            income += ticket;
        }
    }

    public void showStatistics() {
        System.out.printf("%nNumber of purchased tickets: %d%n", ticketsSold);
        System.out.printf("Percentage: %.2f%%%n", ticketsSold / ticketsTotal * 100);
        System.out.printf("Current income: $%d%n", income);
        System.out.printf("Total income: $%d%n", maxIncome);
    }

    public int calculateMaxIncome(int rows, int seats) {
        return seats * rows > 60 ? ((rows / 2) * 10 + (rows - rows / 2) * 8) * seats : seats * rows * 10;
    }
}
