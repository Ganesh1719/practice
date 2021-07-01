package com.nt.jdbc2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

/*
create or replace PROCEDURE P_GET_EMPS_BYNAME_INITIAL 
(
  INITIALCHARS IN VARCHAR2 
, DETAILS OUT SYS_REFCURSOR
) AS 
BEGIN
  OPEN DETAILS FOR
  SELECT EMPNO,ENAME,JOB,SAL,DEPTNO FROM EMP WHERE ENAME LIKE INITIALCHARS;
END P_GET_EMPS_BYNAME_INITIAL;
*/

public class CsProcedureCursorTest {
	private static final String PROCEDURE_CALL_QUERY="{CALL P_GET_EMPS_BYNAME_INITIAL(?,?)}";

	public static void main(String[] args) {
		//read inputs
		
		try(Scanner sc=new Scanner(System.in)){
			String initialChars=null;
			if(sc!=null) {
			System.out.println(" Enter initial chars of employee name::");
		     initialChars=sc.next()+"%";
			
			}//if
			//establish the connection
			try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","ganesh");
					//create callable statement obj having the query calling PL/SQL procedure as the pre-compiled SQL query
					CallableStatement cs=con.prepareCall(PROCEDURE_CALL_QUERY);){
				//register OUT param with jdbc data types
				if(cs!=null) 
					cs.registerOutParameter(2,OracleTypes.CURSOR);
				
				
				//set values to IN params
				if(cs!=null) 
					cs.setString(1,initialChars);
				
				//execute/call the PL/SQL function
				if(cs!=null) 
					cs.execute();
					//gather results from OUT params
				if(cs!=null) {
					ResultSet rs=(ResultSet)cs.getObject(2);
					System.out.println("The output is");
					boolean flag=false;
					while(rs.next()) {
						flag=true;
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4)+" "+rs.getInt(5));
					}
					if(flag==false)
						System.out.println("Records not found");
				}//if
					
				
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
