package com.Bridgelabz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bridgelabz.SERVICE.ConnnectionProvider;

public class RegisterServlet extends HttpServlet {
 public  Connection connection=null;
	
 
 
	@Override
	public void init() throws ServletException {
		connection=ConnnectionProvider.getCon();
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		int status = 0;
		String sql="insert into CUSTOMER values(?,?,?)";
		
		PrintWriter writer=resp.getWriter();
		
		String email=req.getParameter("Emailid");
		String userName=req.getParameter("Username");
		String password=req.getParameter("UserPass");
		try {
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,  userName);
			statement.setString(2, password);
			statement.setString(3, email);
			status=statement.executeUpdate();
			
			if (status > 0) {
				writer.println("Data Registered Successfully.........");
				RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
				rd.forward(req, resp);
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
			
			
	}
	
}
