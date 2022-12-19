package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EnrollementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EnrollementServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dummydb db = Dummydb.getInstance();
		
		List<CourseEnrollment> courseEnrollmentArray = db.getCourseEnrollmentlist();
		ObjectMapper om = new ObjectMapper();
//		System.out.println(courseArray.isEmpty());
		if(courseEnrollmentArray.isEmpty() == true) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
		
		String courseJSONString = om.writeValueAsString(courseEnrollmentArray);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write(courseJSONString);
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		CourseEnrollment cr = null;
		System.out.println(request.getInputStream());
	    try {
			cr = om.readValue(request.getInputStream(), CourseEnrollment.class);
	    	
	    } catch (JsonProcessingException e) {
			e.printStackTrace();
//			System.out.println("size is greater");
//	    	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			response.setContentType("application/json");
//			response.setCharacterEncoding("UTF-8");
//			return;
		}
	    
	    System.out.println(cr);
	    
	    if(cr == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
	    }
	    if(cr.getCourse() == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
	    }
	    if(cr.getEnrolledUserList() == null) {
	    	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
	    }
	    
	    Dummydb db = Dummydb.getInstance();
	    int crsinput = cr.getCourse().getId();
	    
	    if(crsinput == 0) {
	    	System.out.println("no course id");
	    	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter pw = response.getWriter();
			pw.write("{\"message\":\"course id not found\"}");
			pw.close();
			return;
	    }
	    
	    System.out.print("course :");
	    System.out.println(crsinput);
	    
	    CourseEnrollment crsenroll = db.findCourseEnrollementByCourseId(crsinput);
	    
	    Course course_obj = db.findCourseById(crsinput);
	    
	    
	    int capacity = course_obj.getCapacity();
	    int crsinputarraysize = cr.getEnrolledUserList().size();
	    
	    if(crsenroll == null) {
			CourseEnrollment ce = new CourseEnrollment();
			ce.setCourse(course_obj);
			ArrayList<User> newuserlist = new ArrayList<User>();
			
			if(crsinputarraysize > capacity) {
				System.out.println("size is greater");
		    	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				PrintWriter pw = response.getWriter();
				pw.write("{\"message\":\"user enrollment list provided exceeds the available capacity\"}");
				pw.close();
				return;
			}
			for(User u: cr.getEnrolledUserList()) {
//				int tempid = u.getId();
//				System.out.println(u.getId());
//				if(tempid == 0) {
//					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			    	return;
//				}
				User temp = db.findUserById(u.getId());
				if(temp == null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			    	return;
				}
				newuserlist.add(temp);
			}
			ce.setEnrolledUserList(newuserlist);
			db.getCourseEnrollmentlist().add(ce);
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter pw = response.getWriter();
			pw.write("{\"message\":\"enrollment successful\"}");
			pw.close();
			return;
	    }
	    
	    int exisitingsize = crsenroll.getEnrolledUserList().size();
	    
	    System.out.print("course capacity :");
	    System.out.println(capacity);
	    System.out.print("exisiting size :");
	    System.out.println(exisitingsize);
	    System.out.print("crsinputarraysize");
	    System.out.println(crsinputarraysize);
	    
	    if(exisitingsize + crsinputarraysize > capacity) {
	    	System.out.println("size is greater");
	    	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter pw = response.getWriter();
			pw.write("{\"message\":\"user enrollment exceeds the available capacity\"}");
			pw.close();
			return;
	    }
	    ArrayList<User> newuserlist = new ArrayList<User>();
	    for(User u: cr.getEnrolledUserList()) {
//			int tempid = u.getId();
//			System.out.println(u.getId());
//			if(tempid == 0) {
//				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//		    	return;
//			}
			User temp = db.findUserById(u.getId());
			if(temp == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    	return;
			}
			newuserlist.add(temp);
		}
	    crsenroll.getEnrolledUserList().addAll(newuserlist);
    	
    	response.setStatus(HttpServletResponse.SC_CREATED);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write("{\"message\":\"enrollment successful\"}");
		pw.close();
	    
	}

}
