package hospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Payment_class {

	public void insertPayment(Connection con, int amount, int docid, String specialist, int pId, int appoinment) {
		PatientDetails pobj = new PatientDetails();
		Payment_class obj = new Payment_class();
		try {
			Statement stmt = con.createStatement();
			PreparedStatement preparedStatement = null;
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);

			preparedStatement = con
					.prepareStatement("insert into payment_details(amount,date,patient_id) values (?,?,?)");

			preparedStatement.setInt(1, amount);
			preparedStatement.setDate(2, date);
			preparedStatement.setInt(3, pId);
			ResultSet rs = stmt.executeQuery("select MAX(payment_id) from payment_details");

			int payId = 0;
			while (rs.next()) {
				payId = rs.getInt("MAX(payment_id)");
			}
			obj.insertHistory(con, docid, pId, specialist, appoinment, payId);
			pobj.entry(con);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void insertHistory(Connection con, int docid, int pId, String specialist, int no_of_appoinment,
			int payment_id) {
		PreparedStatement preparedStatement = null;
		Payment_class obj = new Payment_class();
		try {
			/*
			 * System.out.println(docid); System.out.println(pId);
			 * System.out.println(specialist); System.out.println(no_of_appoinment);
			 * System.out.println(payment_id);
			 */

			preparedStatement = con.prepareStatement(
					"insert into history_table(doctor_id,patient_id,specialist,no_of_appointment,payment_id) values(?,?,?,?,?)");
			preparedStatement.setInt(1, docid);
			preparedStatement.setInt(2, pId);
			preparedStatement.setString(3, specialist);
			preparedStatement.setInt(4, no_of_appoinment);
			preparedStatement.setInt(5, payment_id);
			preparedStatement.executeUpdate();
			obj.viewHistory(con);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void viewHistory(Connection con) {
		Scanner sc = new Scanner(System.in);
		PatientDetails pobj = new PatientDetails();
		System.out.println("Enter your number : ");
		
		try {
			long number = sc.nextLong();
			Statement stmt = con.createStatement();
			ResultSet rs1 =stmt.executeQuery("select patient_id from patient_details where mobile_number='"+number+"' ");
			int patient_id=0;
			while(rs1.next())
			{
				patient_id=rs1.getInt("patient_id");
			}
			boolean result = pobj.check(con, number);
			if(result)
			{
			ResultSet rs = stmt.executeQuery(
					"SELECT doctor_table.doctor_name,history_table.doctor_id ,history_table.patient_id,patient_details.name,history_table.specialist,history_table.no_of_appointment, payment_details.amount, payment_details.date FROM history_table inner join payment_details on history_table.payment_id=payment_details.payment_id inner join doctor_table on history_table.doctor_id=doctor_table.doctor_id inner join patient_details on history_table.patient_id=patient_details.patient_idwhere history_table.patient_id='"+patient_id+"' ");
			
		/*	while (rs.next()) {
				System.out.println("doctor_name: " + rs.getString("doctor_name") + "doctor_id: "
						+ rs.getInt("doctor_id") + "patient_id: " + rs.getInt("patient_id") + "Specialist: "
						+ rs.getString("specialist") + "no_of_appointment: "
								+ rs.getInt("no_of_appointment") + "Amount: " + rs.getInt("amount") + " " + "\nDate :"
						+ rs.getString("date"));
			}*/
			System.out.printf("%10s %10s %10s %10s %8s %10s %10s", " doctor_name |", " doctor_id |", " patient_id |", " patient_name |","  specialist  |", " no_of_appointment |", " amount |" , " date |");
			System.out.println();
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			while(rs.next())
			{
			System.out.format("%8s %14s %12s %19s %12s %15s %15s",rs.getString("doctor_name"),rs.getInt("doctor_id"),rs.getInt("patient_id"),rs.getString("patient_name"),rs.getString("specialist"),rs.getInt("no_of_appointment"),rs.getInt("amount"),rs.getString("date") +"\n");
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			}
			System.out.println("\n"+ "-------------------------------------------ThankYou-----------------------------------------------");
			pobj.entry(con);
			}
			else
			{
				pobj.entry(con);
			}
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);

		}
	}

}