package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.EmployeeDAO;
import com.kh.model.vo.Employee;
import com.kh.view.Menu;

public class EmployeeController_original {
	private EmployeeDAO empDAO = new EmployeeDAO();
	private Menu menu = new Menu();
	
	public void selectAll() {
		ArrayList<Employee> list = empDAO.selectAll();
		
		if(list.isEmpty()) {
			menu.displayError("조회 결과가 없습니다.");
		} else {
			menu.selectAll(list);
		}
	}

	public void selectEmployee() {
		int empNo = menu.selectEmpNo();
		
		// 완성형 쿼리 버전
		// Employee e = empDAO.selectEmployee(empNo);
		
		// 미완성형 쿼리 버전
		Employee e = empDAO.selectEmployee2(empNo);
		
		if(e != null) {
			menu.selectEmployee(e);
		} else {
			menu.displayError("해당 사번의 검색 결과가 없습니다.");
		}
	}

	public void insertEmployee() {
		Employee emp = menu.insertEmployee();
		
		int result = empDAO.insertEmployee(emp);
		if(result > 0) {
			menu.displaySuccess(result + "개의 행이 추가되었습니다.");
		} else {
			menu.displayError("데이터 삽입 과정 중 오류 발생");
		}
	}

	public void updateEmployee() {
		// 수정할 사원 번호 받기
		int empNo = menu.selectEmpNo();
		
		// 변경할 내용 받기
		Employee emp = menu.updateEmployee();
		emp.setEmpNo(empNo);
		
		int result = empDAO.updateEmployee(emp);
		if(result > 0) {
			menu.displaySuccess(result + "개의 행이 수정되었습니다.");
		} else {
			menu.displayError("데이터 수정 과정 중 오류 발생");
		}
	}

	public void deleteEmployee() {
		
	}
}
