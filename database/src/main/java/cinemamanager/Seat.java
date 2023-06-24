package cinemamanager;

import exceptions.InvalidSeatLocatorException;
import tools.AppSettings;

public class Seat implements Comparable<Seat>{
    private final char row;
    private final int column;
    private String bookingCode;

    public Seat(char row, int column, String bookingCode) throws InvalidSeatLocatorException {
        char c = Character.toUpperCase(row);
        if( c < AppSettings.MIN_SEAT_ROW || c > AppSettings.MAX_SEAT_ROW || column < AppSettings.MIN_SEAT_COLUMN || column > AppSettings.MAX_SEAT_COLUMN)
            throw new InvalidSeatLocatorException();
        this.row = c;
        this.column = column;
        this.bookingCode = bookingCode;
    }

    public Seat(char row, int column) throws InvalidSeatLocatorException {
        this(row, column, null);
    }

    public String toEasyString(){
        return this.row + String.format("%02d", this.column);
    }

    public static Seat parseSeat(String input) throws InvalidSeatLocatorException {
        char row = input.charAt(0);
        int column = Integer.parseInt(input.substring(1));
        return new Seat(row, column);
    }

    public boolean equalsToLocator(String locator) throws InvalidSeatLocatorException {
        Seat s = Seat.parseSeat(locator);
        s.setBookingCode(this.bookingCode);
        return this.compareTo(s) == 0;
    }

    //// INTERFACE METHODS
    // Getters
    public char getRow() { return row; }
    public int getColumn() { return column; }
    public String getBookingCode() { return bookingCode; }

    //Setters
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

    @Override
    public int compareTo(Seat s) {
        int diffBookingCode = this.bookingCode.compareTo(s.bookingCode);
        int diffColumn = this.column - s.column;
        int diffRow = this.row - s.row;
        if( diffBookingCode == 0 )
            if(diffColumn == 0)
                return diffRow;     // if zero, objects are equal
            else
                return diffColumn;  // same booking, different column
        else
            return diffBookingCode; // different bookings
    }

}
