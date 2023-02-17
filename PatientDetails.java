package hospitalManagementSystem;

import java.util.*;
import java.sql.*;
import java.lang.*;

public class PatientDetails
{
	public void entry(Connection con)
	{
		PatientDetails obj = new PatientDetails();
		Payment_class vobj = new Payment_class();
		Scanner sc = new Scanner(System.in);
		System.out.println("--------- WELCOME TO SMART HOSPITAL ----------    ");
		System.out.println("Please select any one option as given below :");
		System.out.println("1.Book Appoinment");
		System.out.println("2.Register");
		System.out.println("3.Patient Details");
		System.out.println("I changed the code Kindly check and approve");
		int choise = sc.nextInt();
		try 
		{

			switch (choise)
			{
			case 1:
				obj.patientEntry(con);
				break;
			case 2:
				obj.register(con);
				break;
			case 3:
				vobj.viewHistory(con);
				break;
			}

		} 
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	public boolean check(Connection con, long phNumber)
	{
		PatientDetails obj = new PatientDetails();
		ResultSet rs;
		boolean has_results = false;
		try (Statement stmt = con.createStatement()) 
	    {
			rs = stmt.executeQuery("select mobile_number from patient_details where mobile_number='" + phNumber + "'");
			has_results = rs.next();
			
	    }
		catch (SQLException e)
		{
			System.out.println(e);
		}
		if (has_results)
		{
			return has_results;
		} else 
		{
			return has_results;
		}	
	}
	public void register(Connection con) {
		PreparedStatement preparedStatement = null;
		PatientDetails pobj = new PatientDetails();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the  Mobile number : ");
		long number = sc.nextLong();
		boolean result = pobj.check(con, number);
		try 
		{
			if (result == false)
			{
			System.out.println("Enter the Patient Name : ");
			String name = sc.next();
			System.out.println("Enter the Patient Disease  : ");
			String disease = sc.next();
			System.out.println("Enter the Patient Gender  : ");
			String gender = sc.next();
			System.out.println("Enter the Patient Age : ");
			int age = sc.nextInt();
			System.out.println("Enter the Patient Address : ");
			String address = sc.next();	
			preparedStatement = con.prepareStatement(
						"insert into patient_details( mobile_number, name ,disease,gender, age ,address ) values (?,?,?,?,?,?)");

				preparedStatement.setLong(1, number);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, disease);
				preparedStatement.setString(4, gender);
				preparedStatement.setInt(5, age);
				preparedStatement.setString(6, address);
				preparedStatement.addBatch();
				preparedStatement.executeUpdate();
				preparedStatement.close();
				System.out.println("Record Successfully Inserted...");
				pobj.entry(con);
			}
			else
			{
				pobj.entry(con);
			}
		}	
			catch (SQLException e)
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 

	

	public void patientEntry(Connection con)
	{
		PatientDetails pobj = new PatientDetails();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter mobile number :");
		long num = sc.nextLong();
		Doctor_class dobj = new Doctor_class();
		PreparedStatement preparedStatement = null;
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT mobile_number FROM patient_details where mobile_number = '" + num + "';");
			int pId = 0;
			boolean has_result = rs.next();
			if (has_result)
			{
				ResultSet rs2 = stmt.executeQuery("SELECT * FROM patient_details where mobile_number = '" + num + "';");

//				String disease;
				while (rs2.next()) 
				{

					System.out.println("patient_name :" + rs2.getString("name") + "  " + "\npatient disease :"
							+ rs2.getString("disease") + "  " + "\npatient gender :" + rs2.getString("gender") + "  "
							+ "\npatient age :" + rs2.getInt("age") + "  " + "\npatient address :"
							+ rs2.getString("address") + "\n--------------");
					pId = rs2.getInt("patient_id");

					dobj.doctorDetails(con, pId);
				}
			}
			else
			{
				System.out.println(" You Are Not Registered Please Register Your Mobile Number");
				pobj.entry(con);
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws Exception
	{
		PatientDetails obj = new PatientDetails();

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalManagementSystem_schema",
					"root", "tiger");
			PatientDetails pobj = new PatientDetails();
			obj.entry(con);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

	