package com.Bridgelabz.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bridgelabz.DataAccess.ControllerDao;
import com.Bridgelabz.DataAccess.UserDaoImpl;


public class RegistrationServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDaoImpl userDaoImpl=new UserDaoImpl();

		PrintWriter writer=response.getWriter();
		
		response.setContentType("text/html");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String contact = request.getParameter("contact");
		

		if (first_name.isEmpty() || last_name.isEmpty() || username.isEmpty() || password.isEmpty() || address.isEmpty()
				|| contact.isEmpty()) {
			RequestDispatcher req = request.getRequestDispatcher("Registration.jsp");
			req.forward(request, response);
		} else {
			
			try {
				int status=userDaoImpl.insertUser(first_name, last_name, username, password, address, contact);
				System.out.println("INSERTUSER");
				RequestDispatcher req = request.getRequestDispatcher("welcome.jsp");
				req.forward(request, response);
			
			} catch (SQLException e) {
		
				e.printStackTrace();
			}
	}
	}

}
