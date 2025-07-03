import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//database config
public class EmployeeApp {
    public static void main(String[] args) {
        // use this on terminal to see if server downloaded /usr/local/mysql/bin/mysql -u root -p
// after this create a db table inside terminal CREATE DATABASE company;
//USE company;

//CREATE TABLE employees (
   // id INT PRIMARY KEY AUTO_INCREMENT,
    //name VARCHAR(100),
    //position VARCHAR(100),
    //salary DOUBLE
//);
String url="jdbc:mysql://localhost:3306/company";
String user="root";
String password="Naman@123";//sql password
try {
            Connection c = DriverManager.getConnection(url, user, password); // Connect to DB
            System.out.println(" Connected to database!");
            c.close();
            int choice;

            do {
                System.out.println("\n Employee Menu ");
                System.out.println("1. Add Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter position: ");
                        String position = scanner.nextLine();
                        System.out.print("Enter salary: ");
                        double salary = scanner.nextDouble();
                        addEmployee(c, name, position, salary);
                        break;
                    case 2:
                        viewEmployees(c);
                        break;
                    case 3:
                        System.out.print("Enter ID of employee to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.print("Enter new position: ");
                        String newPosition = scanner.nextLine();
                        System.out.print("Enter new salary: ");
                        double newSalary = scanner.nextDouble();
                        updateEmployee(c, updateId, newPosition, newSalary);
                        break;
                    case 4:
                        System.out.print("Enter ID of employee to delete: ");
                        int deleteId = scanner.nextInt();
                        deleteEmployee(c, deleteId);
                        break;
                    case 0:
                        System.out.println(" Exiting...");
                        break;
                    default:
                        System.out.println(" Invalid choice!");
                }
            } while (choice != 0);
    }
    catch (SQLException e) {
            System.out.println(" Database connection failed: " + e.getMessage()); // If error
        }
    }
}
//ccd ~/Desktop/employeeapp
//to compile the code javac -cp ".:lib/mysql-connector-j-9.3.0.jar" EmployeeApp.java
//to run java -cp ".:lib/mysql-connector-j-9.3.0.jar" EmployeeApp
//create
public static void adde(Connection c, String name, String position, double salary) throws SQLException {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";//v parameters
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.setDouble(3, salary);
            int rows = stmt.executeUpdate();
            System.out.println(" Employee added (" + rows + " row).");
        }
    }
// Read
    public static void viewEmployees(Connection c) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n Employee List ");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("position") + " | $" +
                                   rs.getDouble("salary"));
            }
        }
    }
    // Update
    public static void updateEmployee(Connection c, int id, String position, double salary) throws SQLException {
        String sql = "UPDATE employees SET position = ?, salary = ? WHERE id = ?";
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, position);
            stmt.setDouble(2, salary);
            stmt.setInt(3, id);
            int rows = stmt.executeUpdate();
            System.out.println(" Employee updated (" + rows + " row)");
        }
    }
    // Delete
    public static void deleteEmployee(Connection c, int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println("🗑️ Employee deleted (" + rows + " row).");
        }
    }
