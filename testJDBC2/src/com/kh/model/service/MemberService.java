package com.kh.model.service;

import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.rollback;
import static com.kh.common.JDBCTemplate.close;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.model.dao.MemberDAO;
import com.kh.model.vo.Member;

public class MemberService {

	private MemberDAO mDAO = new MemberDAO();
	
	public int insertMember(Member mem) {
		Connection conn = getConnection();
		int result = mDAO.insertMember(conn, mem);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public ArrayList<Member> selectAll() {
		Connection conn = getConnection(); // 제일 먼저 Connection객체 받아오기
		//(앞서 JDBCTemplate에 대해 import static 해놨으므로 생략)
		ArrayList<Member> list = mDAO.selectAll(conn);
		return list;
	}

	public ArrayList<Member> selectMemberId(String id) {
		Connection conn = getConnection();
		ArrayList<Member> list = mDAO.selectMemberId(conn, id);
		return list;
	}

	public ArrayList<Member> selectGender(char gen) {
		Connection conn = getConnection();
		ArrayList<Member> list = mDAO.selectGender(conn, gen);
		return list;
	}

	public int checkId(String id) {
		Connection conn = getConnection();
		int check = mDAO.checkId(conn, id);
		
		return check;
	}

	public int updateMember(String id, String column, String input) {
		Connection conn = getConnection();
		int result = mDAO.updateMember(conn, id, column, input);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}
	

	public int deleteMember(String id) {
		Connection conn = getConnection();
		int result = mDAO.deleteMember(conn, id);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public void exitProgram() {
		close(getConnection());
	}
}
