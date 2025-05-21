package com.kh.controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {
	private MemberMenu menu = new MemberMenu();
	private MemberService mService = new MemberService();
	Scanner sc = new Scanner(System.in);
	
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

	public void selectAll() {
		ArrayList<Member> list = mService.selectAll();
		
		if(list.isEmpty()) {
			menu.displayedError("조회 결과가 없습니다.");
		} else {
			menu.displayMember(list);
		}
	}
	
	public void selectMember() {
		int sel = menu.selectMember(); // 1,2,0
		ArrayList<Member> list = null;
		
			switch(sel) {
			case 1:
				// 아이디로 회원조회(부분조회)
				String id = menu.inputMemberId();
				list = mService.selectMemberId(id);
				break;
			case 2:
				// 성별로 회원 조회
				char gen = menu.inputGender();
				list = mService.selectGender(gen);
				break;
			case 0: return;
			}
			
			if(list.isEmpty()) {
				menu.displayedError("조회 결과가 없습니다.");
			} else {
				menu.displayMember(list);
			}
	}

	public void updateMember() {
		String id = menu.inputMemberId();
		
		int check = mService.checkId(id);
		//System.out.println(check);
		if(check == 1) {
			// 수정할 수 있도록 다음 단계
			int sel = menu.updateMember();
			String column = null;
			switch(sel) {
			case 1: column = "MEMBER_PWD"; break;
			case 2: column = "EMAIL"; break;
			case 3: column = "PHONE"; break;
			case 4: column = "ADDRESS"; break;
			case 0: return;
			}
			
			String input = menu.inputUpdate();
			
			int result = mService.updateMember(id, column, input);
			if(result >0) {
				menu.displaySuccess(result + "개의 행이 수정되었습니다.");
			} else {
				menu.displayedError("데이터 수정 과정 중 오류 발생");
			}
		} else {
			menu.displayedError("입력한 아이디가 존재하지 않습니다.");
		}
	}

	public void deleteMember() {
		String id = menu.inputMemberId();
		
		int check = mService.checkId(id);
		if(check == 1) {
			char yn = menu.checkDelete();
			if(yn == 'Y') {
				int result = mService.deleteMember(id);
				if(result >0) {
					menu.displaySuccess(result + "개의 행이 삭제되었습니다.");
				} else {
					menu.displayedError("데이터 삭제 과정 중 오류 발생");
				}
			} else {
				return;
			}
		} else {
			menu.displayedError("입력한 아이디가 존재하지 않습니다.");
		}	
	}

	public void exitProgram() {
		mService.exitProgram();
	}
}
