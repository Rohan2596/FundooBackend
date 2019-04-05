package com.Bridgelabz.SERVICE;

import java.sql.Connection;

public class DemoGB {
public static void main(String[] args) {
	Connection connection=ConnnectionProvider.getCon();
	System.out.println("DemoGB.main()::Connection established");
}
}
