package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class AdminManageServiceImpl implements AdminManageService{
	
	//DB parameters
	private static final String USERNAME = "root";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/electrogriddb";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String PASSWORD = "";
	private static Connection connection = null;
	private static String query = "";
	private static PreparedStatement preparedStatement = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	
	private static ArrayList<Employee> employeeList = null;
	String output = "";
	
	//Employee model
	private static Employee employee;
	
	//Connection
	private Connection connect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			return connection;
		}
		else {
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
				System.out.println("Successfully Connected");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return connection;
		}
	}
	
	//View Employees
	@Override
	public ArrayList<Employee> viewEmployees(String UID) {
		//Employee attributes
		String empID = "";
		String name = "";
		String email = "";
		String empType = "";
		
		//Employee List
		employeeList = new ArrayList<Employee>();
		
		//Connection
		try {
			connection = connect();
			
			if(connection == null) {
				System.err.println("Error while connecting to the database");
				return null;
			}
			
			//Query
			query = "SELECT * FROM employees WHERE empID = " + empID;
			
			//Execute
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			//Get all results
			while(resultSet.next()) {
				empID = resultSet.getString("empID");
				name = resultSet.getString("name");
				email = resultSet.getString("email");
				empType = resultSet.getString("empType");
				
				//Add to list
				employeeList.add(new Employee(empID, name, email, empType));
			}
			
		}catch(Exception e) {
			System.err.println("Error getting data " + e.getMessage());
		}
		
		return employeeList;
	}
}