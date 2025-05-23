package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.PreparableStatement;

public class Patient {

	private Connection connection;
	
	private Scanner scanner;
	
	public Patient(Connection connection, Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	public void addPatient() {
		
		System.out.print("Enter Patient Name: ");
		String name = scanner.next();
		System.out.print("Enter Patient Age: ");
		int age= scanner.nextInt();
		System.out.print("Enter Patient Gender: ");
		String gender=scanner.next();
		
		try {
			String query = "INSERT INTO patients(name , age , gender) VALUES(?,?,?)";
		PreparedStatement prepredStatement = connection.prepareStatement(query);
		prepredStatement.setString(1, name);
		prepredStatement.setInt(2, age);
		prepredStatement.setString(3, gender);
		int affectedRows=  prepredStatement.executeUpdate();
		if(affectedRows > 0) {
			System.out.println("Patient Added Successfully");
		}else {
			
			System.out.println("Failed to Add Patients");
		}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	
	}
	
	public void viewPatients() {
		String query = "select * from patients";
		try {
			PreparedStatement prepredStatement = connection.prepareStatement(query);
			
			ResultSet resultSet =  prepredStatement.executeQuery();
		System.out.println("Patients");
		System.out.println("+------------+-------------------+-------+----------------+");
		System.out.println("| Patient Id | Name              | Age   | Gender         |");
		System.out.println("+------------+-------------------+-------+----------------+");
        while(resultSet.next()) {
        	int id = resultSet.getInt("id");
            String name= resultSet.getString("name");
            int age = resultSet.getInt("age");
            String gender = resultSet.getString("gender");
            System.out.printf("|%-12s|%-20s|%-10s|%-12s\n", id,name, age , gender); 
    		System.out.println("+------------+-------------------+-------+----------------+");
  
        }
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public boolean getPatientById(int id) {
		String query = "Select * from patients where id = ?";
		try {
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
		     
			 preparedStatement.setInt(1, id);
		  ResultSet resultSet = preparedStatement.executeQuery();
			  if(resultSet.next()) {
				  return true;
			  }else{
				  return false;
			  }
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
