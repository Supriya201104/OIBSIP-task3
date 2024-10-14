import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return false;
        } else {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            return true;
        }
    }

    public boolean transfer(User recipient, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return false;
        } else {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.getUserId());
            return true;
        }
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}


public class ATM {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        users.add(new User("user1", "1234", 1000.0));
        users.add(new User("user2", "5678", 2000.0));

        System.out.println("Welcome to the ATM!");
        User currentUser = login();

        if (currentUser != null) {
            boolean quit = false;
            while (!quit) {
                System.out.println("\nSelect an operation:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        currentUser.showTransactionHistory();
                        break;
                    case 2:
                        handleWithdraw(currentUser);
                        break;
                    case 3:
                        handleDeposit(currentUser);
                        break;
                    case 4:
                        handleTransfer(currentUser);
                        break;
                    case 5:
                        quit = true;
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static User login() {
        System.out.println("Enter User ID:");
        String userId = scanner.next();
        System.out.println("Enter PIN:");
        String pin = scanner.next();

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                System.out.println("Login successful!");
                return user;
            }
        }

        System.out.println("Invalid User ID or PIN.");
        return null;
    }

    private static void handleWithdraw(User user) {
        System.out.println("Enter amount to withdraw:");
        double amount = scanner.nextDouble();
        if (user.withdraw(amount)) {
            System.out.println("Withdrawal successful. New balance: $" + user.getBalance());
        }
    }

    private static void handleDeposit(User user) {
        System.out.println("Enter amount to deposit:");
        double amount = scanner.nextDouble();
        user.deposit(amount);
        System.out.println("Deposit successful. New balance: $" + user.getBalance());
    }

    private static void handleTransfer(User sender) {
        System.out.println("Enter recipient User ID:");
        String recipientId = scanner.next();
        User recipient = findUserById(recipientId);

        if (recipient == null) {
            System.out.println("Recipient not found.");
        } else {
            System.out.println("Enter amount to transfer:");
            double amount = scanner.nextDouble();
            if (sender.transfer(recipient, amount)) {
                System.out.println("Transfer successful. New balance: $" + sender.getBalance());
            }
        }
    }

    private static User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
