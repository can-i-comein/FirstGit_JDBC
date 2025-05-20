package com.kh.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Employee;

public class EmployeeDAO_original {

	public ArrayList<Employee> selectAll() {
		// 0. Oracle 드라이버 등록 : 생략 가능, 다만 생략하게 되면 에러가
		// 날 수도 있어서 하는 것을 권장
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		// close 때문에 변수 try-catch밖에 선언
		ArrayList<Employee> list = new ArrayList<Employee>();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 1. 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "SCOTT", "SCOTT");
			// System.out.println(conn);

			// 2. 쿼리작성
			// 완성형
			String query = "SELECT * FROM EMP";

			// 3. 쿼리 전송 & 반환값 받기
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				int empNo = rset.getInt("EMPNO");
				String empName = rset.getString("ENAME");
				String job = rset.getString("JOB");
				int mgr = rset.getInt("MGR");
				Date hireDate = rset.getDate("hireDate");
				int sal = rset.getInt("sal");
				int comm = rset.getInt("comm");
				int deptNo = rset.getInt("deptno");

				Employee e = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
				list.add(e);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. 자원 반납
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 완성형 쿼리 버전
	public Employee selectEmployee(int empNo) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		Employee emp = null;

		try {
			// 0. Oracle 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 1. 데이터 베이스 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "SCOTT", "SCOTT");

			// 2. 쿼리 작성 (완성형)
			String query = "SELECT * FROM EMP WHERE EMPNO = " + empNo;

			// 3. 쿼리 전달 및 데이터 반환
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				String empName = rset.getString("ename");
				String job = rset.getString("job");
				int mgr = rset.getInt("mgr");
				Date hireDate = rset.getDate("hireDate");
				int sal = rset.getInt("sal");
				int comm = rset.getInt("comm");
				int deptNo = rset.getInt("deptno");

				emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. 자원 반납
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return emp;
	}

	// 미완성형 쿼리 버전
	public Employee selectEmployee2(int empNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Employee emp = null;
		
		try {
			// 0. Oracle 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 1. 데이터 베이스 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "SCOTT", "SCOTT");
			
			// 2. 쿼리 작성
			//String query = "select * from emp where empNo = " + empNo; //완성형 쿼리
			String query = "select * from emp where empno = ?"; 	//미완성형 쿼리
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empNo);
			
		
			rset = pstmt.executeQuery();
			if (rset.next()) {
				String empName = rset.getString("ename");
				String job = rset.getString("job");
				int mgr = rset.getInt("mgr");
				Date hireDate = rset.getDate("hireDate");
				int sal = rset.getInt("sal");
				int comm = rset.getInt("comm");
				int deptNo = rset.getInt("deptno");

				emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int insertEmployee(Employee emp) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			// 자동커밋 해제
			conn.setAutoCommit(false);
			// 3. 쿼리 작성
			// - 완성형
			//String query = "insert into emp values(" + emp.getEmpNo() + "', '" + emp.getEmpName() + "', '"
			//				+ emp.getJob() + "', '" + emp.getMgr() + ", sysdate, " + emp.getSal() + ", " + emp.getComm()
			//				+ emp.getDeptNo() + ")";
			
			// - 비완성형
			String query = "insert into emp values(?, ?, ?, ?, sysdate, ?, ?, ?)";
			System.out.println(query);
			
			// 4. 쿼리 전송 및 반환값 받기
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getJob());
			pstmt.setInt(4, emp.getMgr());
			pstmt.setInt(5, emp.getSal());
			pstmt.setInt(6, emp.getDeptNo());
			pstmt.setInt(7, emp.getDeptNo());
			
			result = pstmt.executeUpdate();
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. 자원 반납
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int updateEmployee(Employee emp) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			// 1. 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			// 오토커밋 해제
			conn.setAutoCommit(false);
			
			// 3. 쿼리 작성
			String query = "update emp set job=?, sal=?, comm=? where empno =?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emp.getJob());
			pstmt.setInt(2, emp.getSal());
			pstmt.setInt(3, emp.getComm());
			pstmt.setInt(4, emp.getEmpNo());
			
			result = pstmt.executeUpdate();
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. 자원반납
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
