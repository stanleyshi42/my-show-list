package com.cognixia.jump.myshowlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.myshowlist.ConnManagerWithProperties;

//DAO concrete class

// first create class then implement DepartmentDAO, add unimplemented methods
public class DepartmentDAOClass implements DepartmentDAO {
	
	// save connection as attribute so access is easier
	private Connection conn = ConnManagerWithProperties.getConnection();

	
	@Override
	public List<Department> getAllDepartments() {
		
		try {
			// find all the departments...
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM department");
			
			List<Department> deptList = new ArrayList<Department>();
			
			//rs.first();
			
			while(rs.next()) {
				// ...iterate through to get column info...
				int id = rs.getInt("dept_id");
				String name = rs.getString("dept_name");
				String phone = rs.getString("dept_phone");
				
				// ...then add them to a list...
				Department dept = new Department(id, name, phone);
				deptList.add(dept);
			}
			
			// ...and return that list once finished
			return deptList;
			
		} catch (SQLException e) {
			System.out.println("Could not retrieve list of departments from database");
		}
		
		// return null just in case exception is thrown
		return null;
	}

	@Override
	public Department getDepartmentById(int deptId) {
		
		try {
			// set up prepared statement to get a department using its id
			PreparedStatement pstmt = conn.prepareStatement("select * from department where dept_id = ?");
			pstmt.setInt(1, deptId);
			
			ResultSet rs = pstmt.executeQuery();
			
			//rs.first();
			
			
			// retrieve all column info and save it to Department object and return that object
			int id = rs.getInt("dept_id");
			String name = rs.getString("dept_name");
			String phone = rs.getString("dept_phone");
				
			Department dept = new Department(id, name, phone);
			return dept;
			
		} catch (SQLException e) {
			System.out.println("Department with id = " + deptId + " not found.");
			e.printStackTrace();
		}
		
		// if department not found, will return null
		return null;
	}
	
	@Override
	public Department getDepartmentByName(String deptName) {

		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from department where dept_name = ?");
			pstmt.setString(1, deptName);

			ResultSet rs = pstmt.executeQuery();

			rs.first();

			int id = rs.getInt("dept_id");
			String name = rs.getString("dept_name");
			String phone = rs.getString("dept_phone");

			Department dept = new Department(id, name, phone);
			return dept;

		} catch (SQLException e) {
			
			System.out.println("Department with name = " + deptName + " not found.");
		}

		return null;
	}

	@Override
	public boolean addDepartment(Department dept) {
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT into department(dept_id, dept_name, dept_phone) values(?, ?, ?)");
			pstmt.setInt(1, 0); // TODO: test!!!
			pstmt.setString(2, dept.getName());
			pstmt.setString(3, dept.getPhone());
			
			int i = pstmt.executeUpdate();
			
			if(i > 0) {
				return true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteDepartment(int deptId) {
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("DELETE from department WHERE dept_id = ?");
			pstmt.setInt(1, deptId);
			
			int i = pstmt.executeUpdate();
			
			if(i > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println("Department with id = " + deptId + " not found.");
		}
		
		
		return false;
	}

	@Override
	public boolean updateDepartment(Department dept) {
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("UPDATE department SET dept_name = ?, dept_phone = ? WHERE dept_id = ?");
			pstmt.setString(1, dept.getName());
			pstmt.setString(2, dept.getPhone());
			pstmt.setInt(3, dept.getId());
			
			int i = pstmt.executeUpdate();
			
			if(i > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

}
