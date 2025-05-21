package com.kh.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

public class MemberDAO {

	public int insertMember(Connection conn, Member mem) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "insert into member values(?, ?, ?,?,?,?,?,?, sysdate)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mem.getMemberId());
			pstmt.setString(2, mem.getMemberPwd());
			pstmt.setString(3, mem.getMemberName());
			pstmt.setString(4, mem.getGender() + "");
			pstmt.setString(5, mem.getEmail());
			pstmt.setString(6, mem.getPhone());
			pstmt.setString(7, mem.getAddress());
			pstmt.setInt(8, mem.getAge());
			
			result = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public ArrayList<Member> selectAll(Connection conn){
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		String query = "select * from member"; // 완성형 쿼리이므로, select에 대한 결과를 받아와야하므로
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			// ResultSet 안에는 0개의 행이 있을 수도, 1개의 행이 있을수도 있지만
			// n개의 행이 있을수도 있으므로 while로 전부 확인
			// getString, getInt, getDate안에 들어가는 인자 값은 ResultSet의 컬럼명이 들어가야함
			// 현재는 왼쪽에 보이는 Member테이블의 컬럼명으로 참고해서 작성할건데 그 이유는 select * from member라고해서
			// 단순하게 member테이블에 있는 전체 컬럼을 조회하는 걸로만 했기 때문에 가능
			while(rset.next()) {
				list.add(new Member(rset.getString("member_id"),
									rset.getString("member_pwd"),
									rset.getString("member_name"),
									rset.getString("gender").charAt(0),
									rset.getString("email"),
									rset.getString("phone"),
									rset.getInt("age"),
									rset.getString("address"),
									rset.getDate("enroll_date")));
				// testJDBC1에서는 모두 변수로 받아온 다음에 그변수들을 생성자에 하나씩 넣어 객체를 만들고
				// 만들어놓은 객체를 list에다가 add하는 방식으로 진행했는데 그 코드들을 모두 한번에 작성한 것
				
				// Member 안에 들어가는 인자의 순서는 Member클래스의 매개변수 생성자의
				// 순서와 일치해야함
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}

	public ArrayList<Member> selectMemberId(Connection conn, String id) {
		//Statement stmt = null; //완성형
		PreparedStatement pstmt = null; //미완성형
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		
		//String query = "select * from member where member_id like '%" + id + "%'"; // 완성형 쿼리
		String query = "select * from member where member_id like ?"; // 미완성형
		//System.out.println(query);
		
		try {
			//stmt = conn.createStatement(); // 완성형
			//rset = stmt.executeQuery(query); // 완성형
			pstmt = conn.prepareStatement(query);
			//pstmt.setString(1,id); // 완성형
			pstmt.setString(1,"%" + id + "%"); // 완성형
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(rset.getString("member_id"),
									rset.getString("member_pwd"),
									rset.getString("member_name"),
									rset.getString("gender").charAt(0),
									rset.getString("email"),
									rset.getString("phone"),
									rset.getInt("age"),
									rset.getString("address"),
									rset.getDate("enroll_date")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			//close(stmt); 완성형
			close(pstmt);
		}
		return list;
	}

	public ArrayList<Member> selectGender(Connection conn, char gen) {
		// 미완성형 쿼리문
		PreparedStatement pstmt = null; 
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
				
		String query = "select * from member where gender = ?"; 
				
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, gen+""); // 작은 따옴표가 자동으로 감싸지는 것을 알 수 있다
				rset = pstmt.executeQuery();
					
				while(rset.next()) {
					list.add(new Member(rset.getString("member_id"),
										rset.getString("member_pwd"),
										rset.getString("member_name"),
										rset.getString("gender").charAt(0),
										rset.getString("email"),
										rset.getString("phone"),
										rset.getInt("age"),
										rset.getString("address"),
										rset.getDate("enroll_date")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rset);
				close(pstmt);
			}
			return list;
	}

	public int checkId(Connection conn, String id) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int check = 0;
		
		String query = "select count(*) from member where member_id =?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,id);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				check = rset.getInt("count(*)"); // resultSet의 컬럼명을 따라가야된다.
									// 1이라고만 써도 가능
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return check;
	}

	public int updateMember(Connection conn, String id, String column, String input) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "update member set " + column + " = ? where member_id = ?";
		
		try {
			pstmt= conn.prepareStatement(query);
			pstmt.setString(1, input);
			pstmt.setString(2, id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String id) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "delete from member where member_id =?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
}
