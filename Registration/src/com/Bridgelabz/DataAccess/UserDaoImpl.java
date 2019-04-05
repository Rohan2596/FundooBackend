package com.Bridgelabz.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {
 public static Connection con;
	
public static boolean loginvalidate(String username,String password) {
	boolean login = true;
	try {
	PreparedStatement ps;
		ps=con.prepareStatement("SELECT * FROM  userinfo WHERE username=? and Password=?");
	   ResultSet resultSet=ps.executeQuery();
		
		while(resultSet.next()) {
			if ((username.equals(resultSet.getString(3))) && (password.equals(resultSet.getString(4)))) {
				System.out.println("Login successfull");
				login = false;
			} else {
				login = true;

			}
		}
	}catch(Exception e) {
		System.out.println(e);
	}
	return login;
}
public  int  insertUser(String first_name,String last_name,String username,String password,String address,String contact) throws SQLException {
	
	int status=0;
try {
	
	con=ControllerDao.getCon();
//String sql="INSERT INTO userinfo VAlUES(\""+first_name+"\",\""+last_name+"\",\""+username+"\",\""+password+"\",\""+address+"\",\""+contact+"\")";
String sql="INSERT INTO userinfo VALUES(?,?,?,?,?,?)";
	PreparedStatement ps=con.prepareStatement(sql);

	ps.setString(1,first_name);
	ps.setString(2,last_name);
	ps.setString(3,username);
	ps.setString(4,password);
	ps.setString(5,address);
	ps.setString(6,contact);
//	ps=con.prepareStatement(sql);
	status = ps.executeUpdate();
	System.out.println("INSERTUSER");
}
catch (Exception e) {
	e.printStackTrace();
}
	return status;
	
}
	
	
	
}
