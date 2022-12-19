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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 98765446;
       
    public UserServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dummydb db = Dummydb.getInstance();
		
		List<User> userArray = db.getUserlist();
		ObjectMapper om = new ObjectMapper();
		if(userArray.isEmpty() == true) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
		
		String userJSONString = om.writeValueAsString(userArray);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write(userJSONString);
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		User u = null;
		System.out.println(request.getInputStream());
	    try {
			u = om.readValue(request.getInputStream(), User.class);
	    	
	    } catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    System.out.println(u);
	    if(u == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
	    }
	    if(u.getName() == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	return;
	    }
	    
	    Dummydb db = Dummydb.getInstance();
	    
	    int id = db.getUserId();
	    u.setId(id);
	    db.addUser(u);
		String userString = om.writeValueAsString(u);
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.write(userString);
		pw.close();
	    
	}

}
