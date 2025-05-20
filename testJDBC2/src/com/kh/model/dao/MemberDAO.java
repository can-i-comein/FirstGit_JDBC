package com.kh.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
