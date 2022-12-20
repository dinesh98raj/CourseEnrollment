package com.demo;

import java.util.ArrayList;
import java.util.List;



public class Dummydb {
	
	private static Dummydb db_obj = null;
	
	private Dummydb() {
		
	}
	
	public static Dummydb getInstance() {
		if(db_obj == null) {
			db_obj = new Dummydb();
		}
		return db_obj;
	}
	
	private ArrayList<User> userlist = new ArrayList<User>();
	private ArrayList<Course> courselist = new ArrayList<Course>();
	private ArrayList<CourseEnrollment> courseEnrollmentlist = new ArrayList<CourseEnrollment>();

	public ArrayList<User> getUserlist() {
		 ArrayList<User> copiedUserList= new ArrayList<User>(userlist);
		 return copiedUserList;
	}
	
	public ArrayList<Course> getCourselist() {
		ArrayList<Course> copiedCourseList= new ArrayList<Course>(courselist);
		return copiedCourseList;
	}
	
	public void addUser(User u) {
		userlist.add(u);
	}
	
	public void deleteUser(int id) throws IndexOutOfBoundsException{
		userlist.remove(id);
	}
	
	public void addCourse(Course c) {
		courselist.add(c);
	}
	
	public void addCourseEnrollment(CourseEnrollment ce) {
		courseEnrollmentlist.add(ce);
	}
	
	public void deleteCourse(int id) throws IndexOutOfBoundsException{
		courselist.remove(id);
	}
	
	public User findUserById(int id) {
		int index;
		
		for(index = 0; index < userlist.size(); index++) {
			User temp = userlist.get(index);
			if(temp.getId() == id) {
				return temp;
			}
		}
		
		return null;
	}
	
	public Course findCourseById(int id) {
		int index;
		
		for(index = 0; index < courselist.size(); index++) {
			Course temp = courselist.get(index);
			if(temp.getId() == id) {
				return temp;
			}
		}
		
		return null;
	}
	
	public CourseEnrollment findCourseEnrollementByCourseId(int id) {
		int index;
		Course coursetemp = findCourseById(id);
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
	
	public ArrayList<CourseEnrollment> getCourseEnrollmentlist() {
//		User u = new User();
//		u.setName("dinesh");
//		Course c1 = new Course();
//		c1.setName("History");
//		ArrayList<User> ul = new ArrayList<User>();
//		ul.add(u);
//		CourseEnrollment ce = new CourseEnrollment();
//		ce.setCourse(c1);
//		ce.setEnrolledUserList(ul);
//		courseEnrollmentlist.add(ce);
//		ArrayList<CourseEnrollment> copiedCourseList= new ArrayList<CourseEnrollment>(courseEnrollmentlist);
//		return copiedCourseList;
		return courseEnrollmentlist;
	}
	
	public void deleteCourseEnrollmen(int id) throws IndexOutOfBoundsException{
		courseEnrollmentlist.remove(id);
	}
	
	public void deleteCourseEnrollmen(CourseEnrollment ce){
		courseEnrollmentlist.remove(ce);
	}
	
}
