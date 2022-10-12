package com.cognixia.jump.myshowlist;

import java.util.List;

// DAO interface
// Here we list the CRUD methods we may need to make changes and
// retrieve info from our database
public interface DepartmentDAO {

	public List <Department> getAllDepartments();
    public Department getDepartmentById(int deptId);
    public Department getDepartmentByName(String deptName);
    public boolean addDepartment(Department dept);
    public boolean deleteDepartment(int deptId);
    public boolean updateDepartment(Department dept);
    
}