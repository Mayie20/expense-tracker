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
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String FILE = "expenses.csv";
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager(FILE);
        loop:
        while (true) {
            printMenu();
            String cmd = in.nextLine().trim();
            switch (cmd) {
                case "1":
                    add(manager);
                    break;
                case "2":   
                    list(manager);
                    break;
                case "3":
                    delete(manager);
                    break;
                case "4":
                    summary(manager);
                    break;
                case "5":
                    break loop;
                default:
                    System.out.println("Unknown choice");
            }
        }
        System.out.println("bye");
    }

    private static void printMenu() {
        System.out.println("\nPersonal Expense Tracker");
        System.out.println("1) Add expense");
        System.out.println("2) List expenses");
        System.out.println("3) Delete expense");
        System.out.println("4) Summary by category");
        System.out.println("5) Exit");
        System.out.print("Choice: ");
    }

    private static void add(ExpenseManager m) {
        try {
            System.out.print("Date (YYYY-MM-DD) or leave blank for today: ");
            String d = in.nextLine().trim();
            LocalDate date = d.isEmpty() ? LocalDate.now() : LocalDate.parse(d);
            System.out.print("Category: ");
            String cat = in.nextLine().trim();
            System.out.print("Note: ");
            String note = in.nextLine().trim();
            System.out.print("Amount: ");
            double amt = Double.parseDouble(in.nextLine().trim());
            Expense e = new Expense(date, cat, note, amt);
            m.addExpense(e);
            System.out.println("Saved.");
        } catch (Exception ex) {
            System.out.println("Failed to add expense: " + ex.getMessage());
        }
    }

    private static void list(ExpenseManager m) {
        List<Expense> all = m.loadAll();
        if (all.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        for (int i = 0; i < all.size(); i++) {
            System.out.println(i + ") " + all.get(i).toString());
        }
    }

    private static void delete(ExpenseManager m) {
        list(m);
        System.out.print("Index to delete: ");
        try {
            int idx = Integer.parseInt(in.nextLine().trim());
            boolean ok = m.deleteByIndex(idx);
            System.out.println(ok ? "Deleted." : "Invalid index.");
        } catch (Exception ex) {
            System.out.println("Invalid input.");
        }
    }

    private static void summary(ExpenseManager m) {
        Map<String, Double> s = m.summaryByCategory();
        if (s.isEmpty()) {
            System.out.println("No data.");
            return;
        }
        s.forEach((k,v) -> System.out.println(k + " : " + v));
    }
}
