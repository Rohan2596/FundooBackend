package com.Bridgelabz.DataAccess;

import java.sql.*;



public class ControllerDao {
	
	//public static PreparedStatement ps;
  private static Connection con = null;
private ControllerDao() {
	// TODO Auto-generated constructor stub
}
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "test");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static Connection getCon() {
		return con;
	}

}


