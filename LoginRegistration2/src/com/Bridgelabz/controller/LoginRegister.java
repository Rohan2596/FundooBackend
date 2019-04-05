package com.Bridgelabz.controller;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bridgelabz.MODEL.Customer;
import com.Bridgelabz.SERVICE.CustomerDAO;
import com.Bridgelabz.SERVICE.CustomerDAOImpl;


@WebServlet("/login")
public class LoginRegister extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerDAO cd = new CustomerDAOImpl();
		System.out.println("inside login");
		PrintWriter writer=response.getWriter();
		response.setContentType("text/html");
		String emailId = request.getParameter("Emailid");
		String PASSWORD = request.getParameter("userPass");
		String SUBMITYPE = request.getParameter("submit");
		Customer c= new Customer();
		c=cd.getCustomer(emailId, PASSWORD);
		if (SUBMITYPE.equals("Login")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
			rd.forward(request, response);

		} else if (SUBMITYPE.equals("SignUp")) {
			c.setName(request.getParameter("Username"));
			c.setUsername(emailId);
			c.setPassword(PASSWORD);
			cd.insertCustomer(c);
			
	
				writer.println("Data Registered Successfully.........");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.include(request, response);
			
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);

		}
	}

}
