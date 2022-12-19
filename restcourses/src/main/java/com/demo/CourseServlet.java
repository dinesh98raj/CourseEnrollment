package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
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
		
		List<Course> courseArray = db.getCourselist();
		ObjectMapper om = new ObjectMapper();
//		System.out.println(courseArray.isEmpty());
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
		System.out.println(request.getInputStream());
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
	    
	    int id = db.getCourseId();
	    c.setId(id);
	    db.addCourse(c);
		String userString = om.writeValueAsString(c);
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write(userString);
		pw.close();
	}

}
