/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.you.expense;

/**
 *
 * @author Mycha Shem Jimenea
 */
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager {
    private final Path file;
    
    public ExpenseManager(String filename){
        this.file = Paths.get(filename);
    }
    public List<Expense> loadAll() {
        try {
            if (!Files.exists(file)) return new ArrayList<>();
            return Files.lines(file)
                    .filter(l -> !l.trim().isEmpty())
                    .map(Expense::fromCsvLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Expense> list) {
        try (BufferedWriter w = Files.newBufferedWriter(file)) {
            for (Expense e : list) {
                w.write(e.toCsvLine());
                w.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to write file: " + e.getMessage());
        }
    }

    public void addExpense(Expense e) {
        List<Expense> list = loadAll();
        list.add(e);
        saveAll(list);
    }

    public boolean deleteByIndex(int index) {
        List<Expense> list = loadAll();
        if (index < 0 || index >= list.size()) return false;
        list.remove(index);
        saveAll(list);
        return true;
    }

    public Map<String, Double> summaryByCategory() {
        List<Expense> list = loadAll();
        Map<String, Double> map = new HashMap<>();
        for (Expense e : list) {
            map.put(e.getCategory(), map.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
        }
        return map;
    }
}
