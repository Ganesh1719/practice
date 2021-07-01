package com.nt.jdbc2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;


/*
CREATE OR REPLACE PROCEDURE STUDENT_PRO 
(
  NO IN NUMBER 
, NAME OUT VARCHAR2 
, ADDRESS OUT VARCHAR2 
, AVERAGE OUT VARCHAR2 
) AS 
BEGIN
 SELECT SNAME,SADD,AVG INTO NAME,ADDRESS,AVERAGE FROM STUDENT WHERE SNO=NO;
END;
*/
public class CsProcedureTest3 {
	private static final String CALL_PROCEDURE="{CALL STUDENT_PRO (?,?,?,?)}";

	public static void main(String[] args) {
		//read inputs
		int sno=0;
		try(Scanner sc=new Scanner(System.in)){
			System.out.println(" Enter student details ::");
			if(sc!=null) {
				System.out.println("Enter sno::");
				sno=sc.nextInt();
			}//if
			//establish the connection
			try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","ganesh");
					//create callable statement obj having the query calling PL/SQL procedure as the pre-compiled SQL query
					CallableStatement cs=con.prepareCall(CALL_PROCEDURE);){
				//register OUT param with jdbc data types
				if(cs!=null) {
					cs.registerOutParameter(2,Types.VARCHAR);
					cs.registerOutParameter(3,Types.VARCHAR);
					cs.registerOutParameter(4,Types.FLOAT);
				}
				//set values to IN params
				if(cs!=null) {
					cs.setInt(1, sno);
				}
				//execute/call the PL/SQL function
				if(cs!=null) 
					cs.execute();
					//gather results from OUT params
				if(sc!=null) {
					String name=cs.getString(2);
					String address=cs.getString(3);
					float average=cs.getFloat(4);
					System.out.println("name:"+name);  
					System.out.println("address::"+address); 
					System.out.println("average:"+average); 
					 
				}
					
				
			}//try2
		}//try1
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main

}//class
