package com.kh.model.service;

import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.rollback;

import java.sql.Connection;

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
}
