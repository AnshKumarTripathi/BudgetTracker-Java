package com.mycollege.budgettracker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a budget with category-specific spending limits.
 */
public class Budget {
    private String name;
    private Map<String, Double> categoryLimits;
    
    /**
     * Constructor for Budget.
     * 
     * @param name Name of the budget
     */
    public Budget(String name) {
        this.name = name;
        this.categoryLimits = new HashMap<>();
    }
    
    /**
     * Set a spending limit for a specific category.
     * 
     * @param category Category name
     * @param limit Spending limit
     */
    public void setCategoryLimit(String category, double limit) {
        categoryLimits.put(category, limit);
    }
    
    /**
     * Get the spending limit for a specific category.
     * 
     * @param category Category name
     * @return The spending limit or 0.0 if not set
     */
    public double getCategoryLimit(String category) {
        return categoryLimits.getOrDefault(category, 0.0);
    }
    
    /**
     * Remove a category limit.
     * 
     * @param category Category name
     */
    public void removeCategoryLimit(String category) {
        categoryLimits.remove(category);
    }
    
    /**
     * Get all category limits.
     * 
     * @return Map of categories and their spending limits
     */
    public Map<String, Double> getAllCategoryLimits() {
        return new HashMap<>(categoryLimits);
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Budget: ").append(name).append("\n");
        
        for (Map.Entry<String, Double> entry : categoryLimits.entrySet()) {
            sb.append(String.format("  %-15s: $%.2f\n", entry.getKey(), entry.getValue()));
        }
        
        return sb.toString();
    }
}