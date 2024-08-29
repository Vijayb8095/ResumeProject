package com.resume.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resume.model.Resume;
import com.resume.util.DatabaseConnection;
import com.resume.util.PDFGenerator;

public class ResumeServlet extends HttpServlet{

	
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int userId = (int) request.getSession().getAttribute("userId");

	        Resume resume = new Resume();
	        resume.setUserId(userId);
	        resume.setName(request.getParameter("name"));
	        resume.setEmail(request.getParameter("email"));
	        resume.setPhone(request.getParameter("phone"));
	        resume.setSkills(request.getParameter("skills"));
	        resume.setExperience(request.getParameter("experience"));
	        resume.setEducation(request.getParameter("education"));

	        try (Connection conn = DatabaseConnection.getConnection()) {
	            String query = "INSERT INTO resumes (user_id, name, email, phone, skills, experience, education) VALUES (?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setInt(1, resume.getUserId());
	            stmt.setString(2, resume.getName());
	            stmt.setString(3, resume.getEmail());
	            stmt.setString(4, resume.getPhone());
	            stmt.setString(5, resume.getSkills());
	            stmt.setString(6, resume.getExperience());
	            stmt.setString(7, resume.getEducation());
	            stmt.executeUpdate();

	            String pdfFilePath = getServletContext().getRealPath("/") + "resumes/" + resume.getName() + "_resume.pdf";
	            PDFGenerator.generateResumePDF(resume, pdfFilePath);

	            response.sendRedirect("resume.html?success=1&file=" + pdfFilePath);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
