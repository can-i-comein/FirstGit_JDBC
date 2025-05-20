package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {
	private MemberMenu menu = new MemberMenu();
	private MemberService mService = new MemberService();
	
	public int insertMember() {
		Member mem = menu.insertMember();
		int result = mService.insertMember(mem);
		if(result > 0) {
			menu.displaySuccess(result + "개의 행이 추가되었습니다.");
		} else {
			menu.displayedError("데이터 삽입 과정 중 오류 발생");
		}
		return result;
	}

	public ArrayList<Member> selectAll() {
		ArrayList<Member> list = mService.selectAll();
		
		if(list.isEmpty()) {
			menu.displayedError("조회 결과가 없습니다.");
		} else {
			menu.displayMember(list);
		}
	}

}
