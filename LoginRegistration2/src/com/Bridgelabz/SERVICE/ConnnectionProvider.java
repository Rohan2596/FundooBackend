package com.Bridgelabz.SERVICE;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnnectionProvider implements MyProvider {
	private static Connection con = null;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Url, Uname, Pword);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static Connection getCon() {
		return con;
	}

}
