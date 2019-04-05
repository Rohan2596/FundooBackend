package com.Bridgelabz.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Userfogot {
public static Connection con;
public  static boolean uservalidate(String username)
{boolean login=true;
try {PreparedStatement ps;
ps=con.prepareStatement("SELECT * FROM  userinfo");
ResultSet resultSet=ps.executeQuery();
	
	while(resultSet.next()) {
		if ((username.equals(resultSet.getString(3)))) {
			System.out.println("Login successfull");
			login = false;
		} else {
			login = true;

		}
	}
}catch(Exception e) {
	System.out.println(e);
}
return login;}

public static  int  insertUser(String password,String username) throws SQLException {
	
	int status=0;
try {
	
	con=ControllerDao.getCon();
    String sql="UPDATE  userinfo set password=? WHERE username=?";

	PreparedStatement ps=con.prepareStatement(sql);

	
	ps.setString(1,password);
	ps.setString(2, username);
//	ps=con.prepareStatement(sql);
	status = ps.executeUpdate();
	System.out.println("INSERTpassword");
}
catch (Exception e) {
	e.printStackTrace();
}
	return status;
	
}
	
	
	
}

