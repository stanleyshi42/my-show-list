package com.cognixia.jump.myshowlist;


public class Main {
	
	public static void main(String[] args) {
		
		// save the implementation to the interface
		DepartmentDAO deptDAO = new DepartmentDAOClass(); 
		
		System.out.println("These are all the departments in the database:");
		
		for(Department dept : deptDAO.getAllDepartments()) {
			System.out.println(dept);
		}
		
		
		
		System.out.println("\nThis is the department with id = 10004");
		System.out.println(deptDAO.getDepartmentById(10004));
		
		
//		System.out.println("\nAdd department for Physics");
//		Department physics = new Department(-1, "Physics", "0987654321");
//		
//		if(deptDAO.addDepartment(physics)) {
//			System.out.println("Added physics to department list");
//		}
		
		
//		System.out.println("\nUpdate phone number for physics");
//		Department physics = new Department(-1, "Physics", "1111111111");
//		
//		if(deptDAO.updateDepartment(physics)) {
//			System.out.println("Updated physics phone number");
//		}
		
//		System.out.println("\nDelete physics");
//		Department toDelete = deptDAO.getDepartmentByName("Physics");
//		
//		if(deptDAO.deleteDepartment(toDelete.getId())) {
//			System.out.println("Deleted physics");
//		}
		
		
	}

}