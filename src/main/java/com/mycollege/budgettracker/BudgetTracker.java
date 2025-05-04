package com.mycollege.budgettracker;

import com.mycollege.budgettracker.controller.BudgetManager;
import com.mycollege.budgettracker.model.Transaction;
import com.mycollege.budgettracker.model.Budget;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for the Budget Tracker application.
 */
public class BudgetTracker {
    private static BudgetManager budgetManager;
    private static Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Main method to run the application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        budgetManager = new BudgetManager();
        scanner = new Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Budget Tracker =====");
        
        while (running) {
            printMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewTransactions();
                    break;
                case 3:
                    viewIncomeExpenseSummary();
                    break;
                case 4:
                    viewCategoryExpenses();
                    break;
                case 5:
                    setBudgetLimit();
                    break;
                case 6:
                    viewBudget();
                    break;
                case 7:
                    checkBudgetStatus();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println(); // Add a blank line for readability
        }
        
        System.out.println("Thank you for using Budget Tracker!");
        scanner.close();
    }
    
    /**
     * Print the main menu options.
     */
    private static void printMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("1. Add a transaction");
        System.out.println("2. View all transactions");
        System.out.println("3. View income/expense summary");
        System.out.println("4. View expenses by category");
        System.out.println("5. Set budget limit for category");
        System.out.println("6. View budget");
        System.out.println("7. Check budget status");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Get user's menu choice.
     * 
     * @return User's choice as an integer
     */
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }
    
    /**
     * Add a new transaction based on user input.
     */
    private static void addTransaction() {
        System.out.println("\n=== Add Transaction ===");
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        double amount = 0;
        boolean validAmount = false;
        while (!validAmount) {
            System.out.print("Enter amount: $");
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) {
                    System.out.println("Amount must be positive. Please try again.");
                } else {
                    validAmount = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
        
        LocalDate date = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Enter date (yyyy-MM-dd) or leave blank for today: ");
            String dateInput = scanner.nextLine();
            
            try {
                if (dateInput.isEmpty()) {
                    date = LocalDate.now();
                } else {
                    date = LocalDate.parse(dateInput, DATE_FORMATTER);
                }
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        
        Transaction.TransactionType type = null;
        boolean validType = false;
        while (!validType) {
            System.out.print("Enter type (1 for Income, 2 for Expense): ");
            String typeInput = scanner.nextLine();
            
            if (typeInput.equals("1")) {
                type = Transaction.TransactionType.INCOME;
                validType = true;
            } else if (typeInput.equals("2")) {
                type = Transaction.TransactionType.EXPENSE;
                validType = true;
            } else {
                System.out.println("Invalid type. Please enter 1 for Income or 2 for Expense.");
            }
        }
        
        Transaction transaction = new Transaction(description, amount, date, category, type);
        budgetManager.addTransaction(transaction);
        
        System.out.println("Transaction added successfully!");
    }
    
    /**
     * Display all transactions.
     */
    private static void viewTransactions() {
        System.out.println("\n=== All Transactions ===");
        
        List<Transaction> transactions = budgetManager.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println("Date       | Type       | Category        | Amount      | Description");
        System.out.println("--------------------------------------------------------------------------");
        
        int index = 0;
        for (Transaction t : transactions) {
            System.out.println(index + ". " + t);
            index++;
        }
    }
    
    /**
     * Display income and expense summary.
     */
    private static void viewIncomeExpenseSummary() {
        System.out.println("\n=== Income/Expense Summary ===");
        
        double totalIncome = budgetManager.calculateTotalIncome();
        double totalExpenses = budgetManager.calculateTotalExpenses();
        double balance = budgetManager.calculateBalance();
        
        System.out.printf("Total Income: $%.2f\n", totalIncome);
        System.out.printf("Total Expenses: $%.2f\n", totalExpenses);
        System.out.printf("Balance: $%.2f\n", balance);
    }
    
    /**
     * Display expenses by category.
     */
    private static void viewCategoryExpenses() {
        System.out.println("\n=== Expenses by Category ===");
        
        Map<String, Double> expensesByCategory = budgetManager.calculateExpensesByCategory();
        if (expensesByCategory.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        
        System.out.println("Category            | Amount");
        System.out.println("-----------------------------");
        
        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            System.out.printf("%-20s| $%.2f\n", entry.getKey(), entry.getValue());
        }
    }
    
    /**
     * Set a budget limit for a category.
     */
    private static void setBudgetLimit() {
        System.out.println("\n=== Set Budget Limit ===");
        
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        
        double limit = 0;
        boolean validLimit = false;
        while (!validLimit) {
            System.out.print("Enter limit amount: $");
            try {
                limit = Double.parseDouble(scanner.nextLine());
                if (limit <= 0) {
                    System.out.println("Limit must be positive. Please try again.");
                } else {
                    validLimit = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
        
        budgetManager.getBudget().setCategoryLimit(category, limit);
        System.out.println("Budget limit set successfully!");
    }
    
    /**
     * Display current budget.
     */
    private static void viewBudget() {
        System.out.println("\n=== Budget ===");
        
        Budget budget = budgetManager.getBudget();
        System.out.println(budget);
        
        if (budget.getAllCategoryLimits().isEmpty()) {
            System.out.println("No budget limits set yet.");
        }
    }
    
    /**
     * Check budget status and show exceeded categories.
     */
    private static void checkBudgetStatus() {
        System.out.println("\n=== Budget Status ===");
        
        Map<String, Double> exceededCategories = budgetManager.checkBudgetExceeded();
        if (exceededCategories.isEmpty()) {
            System.out.println("All categories are within budget!");
            return;
        }
        
        System.out.println("The following categories have exceeded their budget:");
        System.out.println("Category            | Exceeded By");
        System.out.println("-----------------------------------");
        
        for (Map.Entry<String, Double> entry : exceededCategories.entrySet()) {
            System.out.printf("%-20s| $%.2f\n", entry.getKey(), entry.getValue());
        }
    }
}