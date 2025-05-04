# Budget Tracker Application

A simple command-line Java application for tracking personal finances, managing budgets, and monitoring expenses.

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache NetBeans IDE

### Project Setup
1. Open Apache NetBeans
2. Go to File > Open Project and select the BudgetTracker project folder
3. Build the project by right-clicking on the project name and selecting "Build"
4. Run the application by right-clicking on the project and selecting "Run"

## Project Structure

### Core Files
- **BudgetTracker.java**: Main application class with command-line interface
- **Transaction.java**: Model class representing financial transactions
- **Budget.java**: Model class for managing budget limits by category
- **BudgetManager.java**: Controller class handling business logic

### Packages
- **com.mycollege.budgettracker**: Root package with main application class
- **com.mycollege.budgettracker.model**: Contains data models
- **com.mycollege.budgettracker.controller**: Contains business logic

## Features

### Transaction Management
- Add income and expense transactions
- View all transactions
- Each transaction includes:
  - Description
  - Amount
  - Date
  - Category
  - Type (Income/Expense)

### Budget Management
- Set spending limits for different categories
- Track whether categories exceed their budget
- View current budget limits

### Financial Reporting
- Income/Expense summary
- Balance calculation
- Expenses by category
- Budget status check

## How to Use

1. **Run the application** from NetBeans or through the command line
2. **Navigate the menu** using number inputs (1-8)
3. **Add transactions** by selecting option 1 and following the prompts
4. **Set budget limits** by selecting option 5 and specifying category and amount
5. **View reports** through options 2, 3, 4, 6, and 7
6. **Exit** the application using option 8

## Example Workflow

1. Set budget limits for categories like "Groceries", "Entertainment", etc.
2. Add your income transactions as they occur
3. Add your expenses as they occur, categorizing them appropriately
4. Periodically check your budget status to see if you're staying within your limits
5. View summary reports to understand your spending patterns

## Future Enhancements

Future versions may include:
- Database storage for persistent data
- Graphical user interface
- Data export functionality
- Transaction editing
- Multi-month budget tracking
