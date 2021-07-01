package com.nt.jdbc2;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CsFunctionTest {
	private static final String CALL_FX_QUERY="{?=call FX_GET_STUDENT_DETAILS_BY_NO(?,?,?)}";

	public static void main(String[] args) {
		try(Scanner sc=new Scanner(System.in)){
			//read inputs
			int no=0;
			if(sc!=null) {
				System.out.println("Enter Student number::");
				no=sc.nextInt();
			}
			try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","ganesh");
					CallableStatement cs=con.prepareCall(CALL_FX_QUERY);
					){
				//register return,OuT params with jdbc types
				if(cs!=null) {
					cs.registerOutParameter(1,Types.FLOAT); //return param
	                cs.registerOutParameter(3, Types.VARCHAR);
	                cs.registerOutParameter(4, Types.VARCHAR);
				
			}
			//set values to in params
			if(cs!=null)
				cs.setInt(2,no);
			//call/execute pl/sql function
			if(cs!=null)
				cs.execute();
			//gather results from return , out params
			if(cs!=null) {
				System.out.println("student name::"+cs.getString(3));
				System.out.println("student addrs::"+cs.getString(4));
				System.out.println("student avg::"+cs.getFloat(1));
			}
		}//try2
	}//try1
	catch(SQLException se) {
		System.out.println("Records not found");
		se.printStackTrace();
	}
	catch(Exception e) {
		e.printStackTrace();
	}

}//main
}//class