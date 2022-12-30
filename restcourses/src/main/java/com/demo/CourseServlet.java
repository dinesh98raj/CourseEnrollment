package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CourseServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dummydb db = Dummydb.getInstance();
		
		List<Course> courseArray = null;
		try {
			courseArray = db.getCourselist();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ccourse: sql exception");
		}
		ObjectMapper om = new ObjectMapper();
		if(courseArray.isEmpty() == true) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
		
		String courseJSONString = om.writeValueAsString(courseArray);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write(courseJSONString);
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Course c = null;
	    try {
			c = om.readValue(request.getInputStream(), Course.class);
	    	
	    } catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    
	    System.out.println(c);
	    if(c == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
	    }
	    if(c.getName() == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
	    }
	    
	    Dummydb db = Dummydb.getInstance();
	    
	    try {
			db.addCourse(c);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("user: sql exception insert");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
		}
	    
		String courseString = om.writeValueAsString(c);
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write(courseString);
		pw.close();
	}

}
