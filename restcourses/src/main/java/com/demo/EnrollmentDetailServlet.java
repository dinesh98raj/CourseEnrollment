package com.demo;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EnrollmentDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EnrollmentDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer courseId = Util.getIdFromPath(request);
		Dummydb db = Dummydb.getInstance();
		
		if(courseId == -1) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("application/json");
			return;
		}
		
		CourseEnrollment course = null;
		try {
			course = db.getCourseEnrollmentByCourseId(courseId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("course enrollment sql exception");
		}
		
		if(course == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write("{\"message\":\"resourse not found\"}");
			pw.close();
			return;
		}
		
		ObjectMapper om = new ObjectMapper();
		
		String courseString = om.writeValueAsString(course);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write(courseString);
		pw.close();
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer courseId = Util.getIdFromPath(request);
		Dummydb db = Dummydb.getInstance();
		
		if(courseId == -1) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("application/json");
			return;
		}
		
		boolean deleteresult = false;
		try {
			deleteresult = db.deleteCourseEnrollment(courseId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("course enrollment delete exception");
			
			return;
		}
		
		if(!deleteresult) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write("{\"message\":\"resourse not found\"}");
			pw.close();
			return;
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		
	}

}
