package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
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
		
		User user = db.findUserById(usrId);
		
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

}
