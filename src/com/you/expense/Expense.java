/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.you.expense;

/**
 *
 * @author Mycha Shem Jimenea
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_DATE;
    private LocalDate date;
    private String category;
    private String note;
    private double amount;
    
    public Expense(LocalDate date, String category, String note, double amount){
        this.date = date;
        this.category = category;
        this.note = note;
        this.amount = amount;
    }
    public LocalDate getDate() {return date;}
    public String getCategory() {return category;}
    public String getNote(){return note;}
    public Double getAmount() {return amount;}
    
    public String toCsvLine(){
         return date.format(F) + "," + escapeCsv(category) + "," + escapeCsv(note) + "," + amount;
    }
    public static Expense fromCsvLine(String line){
        String[] parts = line.split(",", 4);
        LocalDate d = LocalDate.parse(parts[0], F);
        String cat = unescapeCsv(parts[1]);
        String note = unescapeCsv(parts[2]);
        double amt = Double.parseDouble(parts[3]);
        return new Expense(d, cat, note, amt);
    }
    private static String escapeCsv(String s){
        if (s.contains(",") || s.contains("\"")) {
            s = s.replace("\"", "\"\"");
            return "\"" + s + "\"";
        }
        return s;  
    }
    private static String unescapeCsv(String s){
         s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length()-1).replace("\"\"", "\"");
        }
        return s;
    } 
     @Override
    public String toString() {
        return date.format(F) + " | " + category + " | " + note + " | " + amount;
    }
}

