package com.Bridgelabz.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bridgelabz.DataAccess.ControllerDao;
import com.Bridgelabz.DataAccess.UserDaoImpl;

@SuppressWarnings("serial")
public class login extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserDaoImpl userDaoImpl=new UserDaoImpl();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username.isEmpty() || password.isEmpty()) {
			RequestDispatcher req = request.getRequestDispatcher("Registration.jsp");
			req.forward(request, response);
		} else {
			userDaoImpl.loginvalidate(username, password);
			RequestDispatcher req = request.getRequestDispatcher("welcome.jsp");
			req.forward(request, response);
		}
	}
}
