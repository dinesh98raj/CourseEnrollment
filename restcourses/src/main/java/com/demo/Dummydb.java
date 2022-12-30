package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Dummydb {
	
	private static Dummydb db_obj = null;
//	private Connection conn = null;
	
	private Dummydb() {
		
	}
	
	public static Dummydb getInstance() {
		if(db_obj == null) {
			db_obj = new Dummydb();
		}
		return db_obj;
	}
	
	private Connection getDbConnection() {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("driver class exception");
		}
		String uname = "dinesh-16169";
		String pass = "";
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/course_enrollment", uname, pass);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(" db connection error");
		}
		
		return conn;
	}
	
	private void closeDbConnection(Connection c) throws SQLException {
		if(c != null) {
			c.close();
		}
	}
	
	private ArrayList<User> userlist = new ArrayList<User>();
	private ArrayList<Course> courselist = new ArrayList<Course>();
	private ArrayList<CourseEnrollment> courseEnrollmentlist = new ArrayList<CourseEnrollment>();
	
	public ArrayList<User> getUserList() throws SQLException {
		Connection con = db_obj.getDbConnection();
		String query = "Select * from user_table";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<User> ul = new ArrayList<User>();
		while(rs.next()) {
			User u = new User();
			System.out.println(u);
			u.setId(rs.getInt("id"));
			u.setName(rs.getString("name"));
			ul.add(u);
			System.out.println(rs.getString("name"));
		}
		
		st.close();
		db_obj.closeDbConnection(con);
		return ul;
	}
	
	public User addUser(User u) throws SQLException {
		Connection con = db_obj.getDbConnection();
		String query = "Insert into user_table(name) values(?)";
		PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		st.setString(1, u.getName());
		st.executeUpdate();
		ResultSet rs = st.getGeneratedKeys();
		if(rs != null && rs.next()){
            u.setId(rs.getInt(1));
            System.out.println("Generated Emp Id: "+rs.getInt(1));
        }		
		
		st.close();
		db_obj.closeDbConnection(con);
		return u;
	}
	
	public boolean deleteUser(int id) throws SQLException{
		boolean result = false;
		Connection con = db_obj.getDbConnection();
		String query = "Delete from user_table where id = ?";
		PreparedStatement st = con.prepareStatement(query);
		st.setInt(1, id);
		int resultcount = st.executeUpdate();	
		result = resultcount >= 1 ? true : false;
		st.close();
		db_obj.closeDbConnection(con);
		return result;
	}
	
	public ArrayList<Course> getCourselist() throws SQLException{
		Connection con = db_obj.getDbConnection();
		String query = "Select * from course";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<Course> cl = new ArrayList<Course>();
		while(rs.next()) {
			Course c = new Course();
			System.out.println(c);
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("name"));
			c.setCapacity(rs.getInt("capacity"));
			cl.add(c);
			System.out.println(rs.getString("name"));
		}
		
		st.close();
		db_obj.closeDbConnection(con);
		return cl;
	}
		
	public void addCourse(Course c) throws SQLException{
		Connection con = db_obj.getDbConnection();
		String query = "Insert into course(name, capacity) values(?, ?)";
		PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		st.setString(1, c.getName());
		st.setInt(2, c.getCapacity());
		st.executeUpdate();
		ResultSet rs = st.getGeneratedKeys();
		if(rs != null && rs.next()){
            c.setId(rs.getInt(1));
            System.out.println("Generated Emp Id: "+rs.getInt(1));
        }		
		
		st.close();
		db_obj.closeDbConnection(con);
	}
	
//	public void addCourseEnrollment(CourseEnrollment ce) {
//		courseEnrollmentlist.add(ce);
//	}
		
	public boolean deleteCourse(int id) throws SQLException{
		boolean result = false;
		Connection con = db_obj.getDbConnection();
		String query = "Delete from course where id = ?";
		PreparedStatement st = con.prepareStatement(query);
		st.setInt(1, id);
		int resultcount = st.executeUpdate();	
		result = resultcount >= 1 ? true : false;
		st.close();
		db_obj.closeDbConnection(con);
		return result;
	}
	
	public User findUserById(int id) throws SQLException {

		User u = null;
		Connection con = db_obj.getDbConnection();
		String query = "Select * from user_table where id = ?";
		PreparedStatement st = con.prepareStatement(query);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		if(rs != null && rs.next()){
			u = new User();
            u.setId(rs.getInt("id"));
            u.setName(rs.getString("name"));
            System.out.println("Generated Emp Id: "+rs.getInt(1));
        }		
		
		st.close();
		db_obj.closeDbConnection(con);
		return u;
	}
	
	public Course findCourseById(int id) throws SQLException {
		Course c = null;
		Connection con = db_obj.getDbConnection();
		String query = "Select * from course where id = ?";
		PreparedStatement st = con.prepareStatement(query);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		if(rs != null && rs.next()){
			c = new Course();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setCapacity(rs.getInt("capacity"));
            System.out.println("Generated Crs Id: "+rs.getInt(1));
        }		
		
		st.close();
		db_obj.closeDbConnection(con);
		return c;
	}
	
	public CourseEnrollment findCourseEnrollementByCourseId(int id) {
		int index;
		Course coursetemp = null;
		try {
			coursetemp = findCourseById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(coursetemp != null) {			
			for(index = 0; index < courseEnrollmentlist.size(); index++) {		
				CourseEnrollment courseEnrollmentTemp = courseEnrollmentlist.get(index);
				if(courseEnrollmentlist.get(index).getCourse() == coursetemp) {
					return courseEnrollmentTemp;
				}
			}
		}
		
		return null;
	}
	
	public int getUserId() {
		return userlist.size() + 1;
	}
	
	public int getCourseId() {
		return courselist.size() + 1;
	}
	
	public ArrayList<CourseEnrollment> getCourseEnrollmentlist() throws SQLException{
		Dummydb db_obj = Dummydb.getInstance();
		Connection con = db_obj.getDbConnection();
		String query = "Select course.id as course_id, course.name as course_name, course.capacity as course_capacity, user_table.id as user_id, user_table.name as user_name" +
					   " from course inner join course_enrollment on course.id = course_enrollment.course_id" +
					   " inner join user_table on course_enrollment.user_id = user_table.id";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<CourseEnrollment> crsEnrollList = new ArrayList<CourseEnrollment>();
		
		while(rs.next()) {
			CourseEnrollment crsEnroll = null;
			int course_id = rs.getInt("course_id");
			User u = new User(rs.getInt("user_id"), rs.getString("user_name"));
			
			for(int index = 0; index < crsEnrollList.size(); index++) {		
				CourseEnrollment courseEnrollmentTemp = crsEnrollList.get(index);
				if(crsEnrollList.get(index).getCourse().getId() == course_id) {
					crsEnroll = courseEnrollmentTemp;
					break;
				}
			}
			
			if(crsEnroll == null) {
				crsEnroll = new CourseEnrollment();
				Course c = new Course(rs.getInt("course_id"), 
						  rs.getString("course_name"),
						  rs.getInt("course_capacity"));
				crsEnroll.setCourse(c);
				ArrayList<User> ul = new ArrayList<User>();
				ul.add(u);
				crsEnroll.setEnrolledUserList(ul);
				crsEnrollList.add(crsEnroll);
			}
			else {
				crsEnroll.getEnrolledUserList().add(u);
			}
		}
		
		st.close();
		db_obj.closeDbConnection(con);
		return crsEnrollList;
//		return courseEnrollmentlist;
	}
	
	public CourseEnrollment getCourseEnrollmentByCourseId(int courseid) throws SQLException{
		Dummydb db_obj = Dummydb.getInstance();
		Connection con = db_obj.getDbConnection();
		
        String query = "Select course.id as course_id, course.name as course_name, course.capacity as course_capacity, user_table.id as user_id, user_table.name as user_name from course inner join course_enrollment on course.id = course_enrollment.course_id inner join user_table on course_enrollment.user_id = user_table.id where course_enrollment.course_id ="+ courseid ;
        
        Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		CourseEnrollment crsEnroll = null;
		
		while(rs.next()) {
			User u = new User(rs.getInt("user_id"), rs.getString("user_name"));
			
//			for(int index = 0; index < crsEnrollList.size(); index++) {		
//				CourseEnrollment courseEnrollmentTemp = crsEnrollList.get(index);
//				if(crsEnrollList.get(index).getCourse().getId() == course_id) {
//					crsEnroll = courseEnrollmentTemp;
//					break;
//				}
//			}
			
			if(crsEnroll == null) {
				crsEnroll = new CourseEnrollment();
//				int course_id = rs.getInt("course_id");
				Course c = new Course(rs.getInt("course_id"), 
						  rs.getString("course_name"),
						  rs.getInt("course_capacity"));
				crsEnroll.setCourse(c);
				ArrayList<User> ul = new ArrayList<User>();
				ul.add(u);
				crsEnroll.setEnrolledUserList(ul);
//				crsEnrollList.add(crsEnroll);
			}
			else {
				crsEnroll.getEnrolledUserList().add(u);
			}
		}
		
		st.close();
		db_obj.closeDbConnection(con);
		return crsEnroll;
//		return courseEnrollmentlist;
	}
	
//	public void deleteCourseEnrollmen(int id) throws IndexOutOfBoundsException{
//		courseEnrollmentlist.remove(id);
//	}
	
	public boolean deleteCourseEnrollment(int courseid) throws SQLException{
		boolean result = false;
		Connection con = db_obj.getDbConnection();
		String query = "Delete from course_enrollment where id = ?";
		PreparedStatement st = con.prepareStatement(query);
		st.setInt(1, courseid);
		int resultcount = st.executeUpdate();	
		result = resultcount >= 1 ? true : false;
		st.close();
		db_obj.closeDbConnection(con);
		return result;
	}
	
	public int checkEnrollmentAvailability(int courseid) throws SQLException{
//		Course c = null;
		Connection con = db_obj.getDbConnection();
		String query = "Select count(*) from course_enrollment where course_id = ?";
		PreparedStatement st = con.prepareStatement(query);
		st.setInt(1, courseid);
		ResultSet rs = null;
		rs = st.executeQuery();
		int course_entry_count = 0;
		if(rs != null && rs.next()){
//			c = new Course();
            course_entry_count = rs.getInt("count");
//            c.setName(rs.getString("name"));
//            c.setCapacity(rs.getInt("capacity"));
            System.out.println("entry for course "+rs.getInt("count"));
        }		
		
		st.close();
		db_obj.closeDbConnection(con);
		return course_entry_count;
	}
	
	public boolean addCourseEnrollment(CourseEnrollment cr) throws SQLException {
		Connection conn = db_obj.getDbConnection();
		String query = "Insert into course_enrollment(user_id, course_id) values(?,?)";
		PreparedStatement st = conn.prepareStatement(query);
		conn.setAutoCommit(false);
		int courseId = cr.getCourse().getId();
        int count = 0;
        boolean result = false;
        int insertedCount[] = {0};
        for (User u : cr.getEnrolledUserList()) {
            st.setInt(1, u.getId());
            st.setInt(2, courseId);

            st.addBatch();    
	    }
        try {
        insertedCount = st.executeBatch();
        }
        catch(BatchUpdateException e) {
        	insertedCount = e.getUpdateCounts();
        }
        
        for(int i=0;i<insertedCount.length;i++) {
        	if(insertedCount[i]>0 || insertedCount[i] == Statement.SUCCESS_NO_INFO) {
        		count++;
        	}
        }
        if(count == cr.getEnrolledUserList().size()) {        	
        	result = true;
        	conn.commit();
        }
        else {        	
        	conn.rollback();
        }
        return result;
	}
}
