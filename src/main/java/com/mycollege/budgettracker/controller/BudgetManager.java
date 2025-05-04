package com.mycollege.budgettracker.controller;

import com.mycollege.budgettracker.model.Budget;
import com.mycollege.budgettracker.model.Transaction;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller class for managing budget and transactions.
 */
public class BudgetManager {
    private List<Transaction> transactions;
    private Budget budget;
    
    /**
     * Constructor for BudgetManager.
     */
    public BudgetManager() {
        this.transactions = new ArrayList<>();
        this.budget = new Budget("Default Budget");
    }
    
    /**
     * Add a new transaction.
     * 
     * @param transaction The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    
    /**
     * Remove a transaction.
     * 
     * @param index The index of the transaction to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
            return true;
        }
        return false;
    }
    
    /**
     * Get all transactions.
     * 
     * @return List of all transactions
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    
    /**
     * Get transactions by type.
     * 
     * @param type The transaction type
     * @return List of transactions of the specified type
     */
    public List<Transaction> getTransactionsByType(Transaction.TransactionType type) {
        return transactions.stream()
                .filter(t -> t.getType() == type)
                .collect(Collectors.toList());
    }
    
    /**
     * Get transactions by category.
     * 
     * @param category The category name
     * @return List of transactions in the specified category
     */
    public List<Transaction> getTransactionsByCategory(String category) {
        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    /**
     * Get transactions by date range.
     * 
     * @param startDate Start date (inclusive)
     * @param endDate End date (inclusive)
     * @return List of transactions within the date range
     */
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
    
    /**
     * Calculate total income.
     * 
     * @return Total income amount
     */
    public double calculateTotalIncome() {
        return getTransactionsByType(Transaction.TransactionType.INCOME).stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    /**
     * Calculate total expenses.
     * 
     * @return Total expense amount
     */
    public double calculateTotalExpenses() {
        return getTransactionsByType(Transaction.TransactionType.EXPENSE).stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    /**
     * Calculate balance (income - expenses).
     * 
     * @return Current balance
     */
    public double calculateBalance() {
        return calculateTotalIncome() - calculateTotalExpenses();
    }
    
    /**
     * Calculate expenses by category.
     * 
     * @return Map of categories and their total expenses
     */
    public Map<String, Double> calculateExpensesByCategory() {
        Map<String, Double> expensesByCategory = new HashMap<>();
        
        for (Transaction t : getTransactionsByType(Transaction.TransactionType.EXPENSE)) {
            String category = t.getCategory();
            double currentAmount = expensesByCategory.getOrDefault(category, 0.0);
            expensesByCategory.put(category, currentAmount + t.getAmount());
        }
        
        return expensesByCategory;
    }
    
    /**
     * Calculate monthly spending.
     * 
     * @param year The year
     * @param month The month
     * @return Total expenses for the specified month
     */
    public double calculateMonthlySpending(int year, Month month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        
        return getTransactionsByDateRange(startDate, endDate).stream()
                .filter(t -> t.getType() == Transaction.TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    /**
     * Check if any category exceeds its budget limit.
     * 
     * @return Map of categories that exceed their budget and the amount exceeded by
     */
    public Map<String, Double> checkBudgetExceeded() {
        Map<String, Double> expensesByCategory = calculateExpensesByCategory();
        Map<String, Double> exceededCategories = new HashMap<>();
        
        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            String category = entry.getKey();
            double expenses = entry.getValue();
            double limit = budget.getCategoryLimit(category);
            
            if (limit > 0 && expenses > limit) {
                exceededCategories.put(category, expenses - limit);
            }
        }
        
        return exceededCategories;
    }
    
    /**
     * Get the budget.
     * 
     * @return The budget
     */
    public Budget getBudget() {
        return budget;
    }
    
    /**
     * Set a new budget.
     * 
     * @param budget The new budget
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}