package com.demo;

import java.io.IOException;
import java.io.PrintWriter;

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
		
		if(courseId == 0) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("application/json");
			return;
		}
		
		CourseEnrollment course = db.findCourseEnrollementByCourseId(courseId);
		
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

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}

}
