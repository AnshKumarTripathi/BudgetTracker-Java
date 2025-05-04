package com.mycollege.budgettracker.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a financial transaction in the budget tracker.
 */
public class Transaction {
    private String description;
    private double amount;
    private LocalDate date;
    private String category;
    private TransactionType type;
    
    /**
     * Enum representing the type of transaction.
     */
    public enum TransactionType {
        INCOME, EXPENSE
    }
    
    /**
     * Constructor for Transaction.
     * 
     * @param description Description of the transaction
     * @param amount Amount of money involved
     * @param date Date of the transaction
     * @param category Category of the transaction
     * @param type Type of transaction (INCOME or EXPENSE)
     */
    public Transaction(String description, double amount, LocalDate date, 
            String category, TransactionType type) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.type = type;
    }
    
    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String typeStr = type == TransactionType.INCOME ? "Income" : "Expense";
        String amountStr = String.format("%.2f", amount);
        
        return String.format("%-10s | %-10s | %-15s | $%-10s | %s", 
                date.format(formatter), typeStr, category, amountStr, description);
    }
}