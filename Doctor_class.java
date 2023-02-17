package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Doctor_class {

	private static final String Connection = null;
       System.out.println("sivanath");

	public  void doctorDetails(Connection con, int pId) {
		PatientDetails obj = new PatientDetails();
		Scanner sc = new Scanner(System.in);
		PreparedStatement preparedStatement = null;
		Payment_class pobj= new Payment_class();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT distinct specialist FROM doctor_table  ");
			boolean has_result = rs.next();
//			ResultSet rs2 = stmt.executeQuery("SELECT * FROM patient_details where mobile_number = '" + num + "';");
			while (rs.next()) 
			{
				System.out.println("specialist :" + rs.getString("specialist") + "\n-----------");
			}
			String specialist = sc.nextLine();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM doctor_table where specialist = '" + specialist + "';");
			String doctor_name=null;
			while (rs1.next())
			{
				System.out.println("doctor_id :" + rs1.getInt("doctor_id") +" " +"\ndoctor_name :" + rs1.getString("doctor_name") + "  " + "\nspecialist :"
						+ rs1.getString("specialist") + "  " + "\ndoctor mobile_number :" + rs1.getLong("mobile_number")
						+ "  " + "\nstatus :" + rs1.getString("status") + "  " + "\namount :" + rs1.getInt("amount")
						+ "\nno_of_appoinment :" + rs1.getInt("no_of_appoinment") + "\n--------------");
				doctor_name=rs1.getString("doctor_name");
				specialist = rs1.getString ("specialist");
				
			}
			System.out.println("Enter the doctor's id to select");
			int id=sc.nextInt();
			int amount=0;
			int docid=0;
			int appoinment=0;
			ResultSet rs2 =stmt.executeQuery("SELECT doctor_id, doctor_name, amount ,no_of_appoinment FROM doctor_table where doctor_id = '" +id+ "';");
			while (rs2.next())
			{
				System.out.println("doctor_id :" + rs2.getInt("doctor_id") +" " +"\ndoctor_name :" + rs2.getString("doctor_name") + "  " +"\namount :" + rs2.getInt("amount") + "\n------------");
				docid=rs2.getInt("doctor_id");
				amount=rs2.getInt("amount");
				appoinment=rs2.getInt("no_of_appoinment");
				
			}
			System.out.println(" Your Appoinment Has Fixed " +"\n-------");
			
			pobj.insertPayment(con,amount,docid,specialist,pId,appoinment);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	 
	
}
