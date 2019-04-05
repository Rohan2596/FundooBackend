package com.Bridgelabz.Controller;

import java.sql.Connection;

import com.Bridgelabz.DataAccess.ControllerDao;

public class demo {
public static void main(String[] args) {
	
	Connection con=ControllerDao.getCon();
	System.out.println("demo.main()::Success");
}
}
