package com.Bridgelabz.SERVICE;
import  java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Bridgelabz.MODEL.Customer;
public class CustomerDAOImpl implements CustomerDAO {
 static Connection con;
static PreparedStatement ps;
	@Override
	public int insertCustomer(Customer c) {
		int status=0;
		try {
			System.out.println("inside db");
			String sql="insert into CUSTOMER values(?,?,?)";
			System.out.println("sql created");
			con=ConnnectionProvider.getCon();
			System.out.println("con established");
			ps=con.prepareStatement(sql);
			System.out.println("prepared set");
			ps.setString(1, c.getUsername());
			System.out.println("username set");
			ps.setString(2, c.getPassword());
			ps.setString(3, c.getName());
			status=ps.executeUpdate();
			System.out.println("update executed");
			con.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		return status;
	}

	@Override
	public   Customer getCustomer(String userId, String password){
		Customer c=new Customer();
		
		try {
			con=ConnnectionProvider.getCon();
			ps=con.prepareStatement("SELECT * FROM  CUSTOMER WHERE USERNAME=? and PASSWORD=?");
			ps.setString(1,userId );
			ps.setString(2, password);
			ResultSet resultSet=ps.executeQuery();
			
			while(resultSet.next()) {
				c.setName(resultSet.getString(3));
				c.setPassword(resultSet.getString(2));
				c.setUsername(resultSet.getString(1));
			}
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
		return c;
	}

	

}
