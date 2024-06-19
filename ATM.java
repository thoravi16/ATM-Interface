import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ATM {
    private String userID;
    private int pin;
    private double balance;
    private TransactionHistory history;

    public ATM(String userID, int pin, double balance) {
        this.userID = userID;
        this.pin = pin;
        this.balance = balance;
        this.history = new TransactionHistory();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");

        // User authentication
        System.out.print("Enter your user ID: ");
        String inputID = scanner.nextLine();
        if (inputID.equals(userID)) {
            System.out.print("Enter your PIN: ");
            int inputPin = Integer.parseInt(scanner.nextLine());
            if (inputPin == pin) {
                System.out.println("Login successful!");
                // Main menu loop
                while (true) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Transactions History");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Deposit");
                    System.out.println("4. Transfer");
                    System.out.println("5. Quit");
                    int option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            displayTransactionsHistory();
                            break;
                        case 2:
                            withdraw(scanner);
                            break;
                        case 3:
                            deposit(scanner);
                            break;
                        case 4:
                            transfer(scanner);
                            break;
                        case 5:
                            System.out.println("Goodbye!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid PIN. Please try again.");
            }
        } else {
            System.out.println("Invalid user ID. Please try again.");
        }
    }

    private void displayTransactionsHistory() {
        history.display();
    }

    private void withdraw(Scanner scanner) {
        System.out.print("Enter amount to withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= balance) {
            balance -= amount;
            history.addTransaction("Withdrawal", -amount);
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void deposit(Scanner scanner) {
        System.out.print("Enter amount to deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        balance += amount;
        history.addTransaction("Deposit", amount);
        System.out.println("Deposit successful!");
    }

    private void transfer(Scanner scanner) {
        System.out.print("Enter recipient's user ID: ");
        String recipientID = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= balance) {
            balance -= amount;
            history.addTransaction("Transfer to " + recipientID, -amount);
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private class TransactionHistory {
        private List<Transaction> transactions;

        public TransactionHistory() {
            transactions = new ArrayList<>();
        }

        public void addTransaction(String type, double amount) {
            transactions.add(new Transaction(type, amount));
        }

        public void display() {
            if (transactions.isEmpty()) {
                System.out.println("No transactions to display.");
            } else {
                for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        }
    }

    private class Transaction {
        private String type;
        private double amount;

        public Transaction(String type, double amount) {
            this.type = type;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return type + ": " + amount;
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM("user123", 1234, 1000.0);
        atm.start();
    }
}
