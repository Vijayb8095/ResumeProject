package com.resume.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resume.util.DatabaseConnection;

public class LoginServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try (Connection conn = DatabaseConnection.getConnection()) {
			String query = "SELECT * FROM users WHERE username = ? AND password = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				request.getSession().setAttribute("userId", rs.getInt("id"));
				response.sendRedirect("resume.html");
			} else {
				response.sendRedirect("login.html?error=1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
