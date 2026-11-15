import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class PostgreSQLJDBCConnection {
	public static String url = "jdbc:postgresql://localhost:5432/student_db";
  public static String user = "postgres";
  public static String password = "NewPassword123";
	public static void main(String[] args) {

			// JDBC & Database credentials
			try { // Load PostgreSQL JDBC Driver
				Class.forName("org.postgresql.Driver");
				// Connect to the database
				Connection conn = DriverManager.getConnection(url, user, password);
				if (conn != null) {
					System.out.println("Connected to PostgreSQL successfully!");
					Scanner scanner = new Scanner(System.in);

					String userInput = "";
					while (!userInput.equals("5"))
					{
						System.out.println("Please select a menu option: ");
						System.out.println("1. View all students");
						System.out.println("2. Add new student");
						System.out.println("3. Update student email");
						System.out.println("4. Delete student");
						System.out.println("5. Quit");
						userInput = scanner.nextLine();
						if (userInput.equals("1"))
						{
							System.out.println("\n------All students----- \n");
							getAllStudents();
							System.out.println("\n-----\n");
						}
						else if (userInput.equals("2"))
						{
							System.out.println("Please enter first name: ");
							String first_name = scanner.nextLine();
							System.out.println("Please enter last name: ");
							String last_name = scanner.nextLine();
							System.out.println("Please enter email: ");
							String email = scanner.nextLine();
							System.out.println("Please enter enrollment date: ");
							String enrollment_date = scanner.nextLine();
							addStudent(first_name, last_name, email, enrollment_date);
						}
						else if (userInput.equals("3"))
						{
							System.out.println("Please enter student_id: ");
							int student_id = scanner.nextInt();
							scanner.nextLine();
							System.out.println("Please enter new email: ");
							String new_email = scanner.nextLine();
							updateStudentEmail(student_id, new_email);
						}
						else if (userInput.equals("4"))
						{
							System.out.println("Please enter student_id: ");
							int student_id = scanner.nextInt();
							scanner.nextLine();
							deleteStudent(student_id);
						}
					}
					
				} else {
				System.out.println("Failed to establish connection.");
				} // Close the connection (in a real scenario, do this in a finally block
					conn.close();
			}
			catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
		}
	}
	
	/*
	Prints all rows of the students id
	*/
	public static void getAllStudents()
	{
		// Create statement
		try
		{
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = conn.createStatement(); // Execute SQL query
			String SQL = "SELECT * FROM Students";
			ResultSet rs = stmt.executeQuery(SQL); // Process the result set 
			while(rs.next()){
					int id = rs.getInt("student_id");
		      String firstName = rs.getString("first_name");
		      String lastName = rs.getString("last_name");
		      String email = rs.getString("email");
		      Date enrollmentDate = rs.getDate("enrollment_date");
		      System.out.println("Id: "+id);
		      System.out.println("First name: "+firstName);
		      System.out.println("Last name: "+lastName);
		      System.out.println("Email: "+email);
		      System.out.println("Enrollment date: "+enrollmentDate);
			}
			// Close resources
			rs.close();
			stmt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	Given a student name, email, and enrollment date, adds a student to the database.
	*/
	public static void addStudent(String first_name, String last_name, String email, String enrollment_date)
	{
		// INSERT Operation
		try
		{
			Connection conn = DriverManager.getConnection(url, user, password);
			String insertSQL = "INSERT INTO Students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, first_name);
			pstmt.setString(2, last_name);
			pstmt.setString(3, email);
			pstmt.setDate(4, java.sql.Date.valueOf(enrollment_date));
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/*
	Updates the email of a student given its id
	*/
	public static void updateStudentEmail(int student_id, String new_email)
	{
	  try
		{
			Connection conn = DriverManager.getConnection(url, user, password);
			String insertSQL = "UPDATE Students SET email = ? WHERE student_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, new_email);
			pstmt.setInt(2, student_id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	Deletes a student given its id
	*/
	public static void deleteStudent(int student_id)
	{
	  try
		{
			Connection conn = DriverManager.getConnection(url, user, password);
			String insertSQL = "DELETE FROM Students WHERE student_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, student_id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
