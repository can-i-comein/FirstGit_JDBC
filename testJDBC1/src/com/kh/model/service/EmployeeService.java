package com.kh.model.service;

import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.model.dao.EmployeeDAO;
import com.kh.model.vo.Employee;

public class EmployeeService {
	/*
	 	Service의 업무
	 		1) Controller가 전달하는 인자를 받음
	 		2) Connection 객체 생성(JDBCTemplate클래스를 통해)
	 		3) DAO에 Connection, 받은 데이터를 전달
	 		4) DAO가 전달하는 리턴값 받아오기 -> 트랜잭션 처리 및 Controller에 전달
	 */
	private EmployeeDAO empDAO = new EmployeeDAO();
	
	public ArrayList<Employee> selectAll(){
		Connection conn = getConnection();
		ArrayList<Employee> list = empDAO.selectAll(conn);
		return list;
	}
	
	public Employee selectEmployee2(int empNo) {
		Connection conn = getConnection();
		Employee emp = empDAO.selectEmployee2(conn, empNo);
		return emp;
	}
	
	public int insertEmployee(Employee emp) {
		Connection conn = getConnection();
		int result = empDAO.insertEmployee(conn, emp);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}
	
	public int updateEmployee(Employee emp) {
		Connection conn = getConnection();
		int result = empDAO.updateEmployee(conn, emp);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}
	
	public int deleteEmployee(int empNo) {
		Connection conn = getConnection();
		int result = empDAO.deleteEmployee(conn, empNo);
		if(result >0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}
	
}
