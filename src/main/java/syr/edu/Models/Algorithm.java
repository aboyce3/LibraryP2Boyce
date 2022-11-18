package syr.edu.Models;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Algorithm {
    private String date;
    private double originalPrice;
    private double newPrice;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private Algorithm(String date, double price) {
        this.date = date;
        this.originalPrice = price;
        this.newPrice = calculatePrice();
    }

    public static Algorithm getInstance(String date, double price) {return new Algorithm(date, price);}

    public double getNewPrice(){return newPrice;}

    public double getOriginalPrice(){return originalPrice;}

    private double calculatePrice() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateBefore = LocalDate.parse(date);
        LocalDate dateAfter = LocalDate.parse(myFormatObj.format(currentDate));
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        if(noOfDaysBetween <= 328){
            double discount = ((noOfDaysBetween/365.0) * 100.0);
            double s = 100 - discount;
            return Double.parseDouble(df.format((s * originalPrice)/100));
        } else {
            return Double.parseDouble(df.format(originalPrice * .1));
        }
    }
}
