package com.demo;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserDetailServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer usrId = Util.getIdFromPath(request);
		Dummydb db = Dummydb.getInstance();
		
		if(usrId == 0) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("application/json");
			return;
		}
		
		User user = null;
		try {
			user = db.findUserById(usrId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("user detail: sql exception");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
		}
		
		if(user == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write("{\"message\":\"resourse not found\"}");
			pw.close();
			return;
		}
		
		ObjectMapper om = new ObjectMapper();
		
		String userString = om.writeValueAsString(user);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write(userString);
		pw.close();
	}
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer userId = Util.getIdFromPath(request);
		Dummydb db = Dummydb.getInstance();
		
		if(userId == 0) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("application/json");
			return;
		}
	
		boolean deleteresult = false;
		
		try {
			deleteresult = db.deleteUser(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("use delete: sql exception");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
