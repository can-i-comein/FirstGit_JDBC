package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	
	public void mainMenu() {
		MemberController mc = new MemberController();
		int select = 0;
		do {
			System.out.println("=== 회원 관리 프로그램 ===");
			System.out.println("1. 새 회원 등록");
			System.out.println("2. 모든 회원 조회");
			System.out.println("3. 특정 조건 회원 조회");
			System.out.println("4. 회원 정보 수정");
			System.out.println("5. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");
			System.out.println("번호 선택 : ");
			select = Integer.parseInt(sc.nextLine());
			
			switch(select) {
			case 1:
				mc.insertMember();
				break;
			case 2:
				mc.selectAll();
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				break;
			default:
				System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
			}
		} while(select != 0);
	}

	public Member insertMember() {
		System.out.println("아이디 : ");
		String memberId = sc.nextLine();
		
		System.out.println("비밀번호 : ");
		String memberPwd = sc.nextLine();
		
		System.out.println("이름 : ");
		String memberName = sc.nextLine();
		
		System.out.println("성별 : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);
		
		System.out.println("이메일 : ");
		String email = sc.nextLine();
		
		System.out.println("전화번호 : ");
		String phone = sc.nextLine();
		
		System.out.println("나이 : ");
		int age = Integer.parseInt(sc.nextLine());
		
		System.out.println("주소 : ");
		String address = sc.nextLine();
		
		Member mem = new Member(memberId, memberPwd, memberName, gender, email, phone, age, address, null);
		
		return mem;
	}

	public void displaySuccess(String string) {
		System.out.println("서비스 요청 성공 : " + string);
	}
	
	public void displayedError(String string) {
		System.out.println("서비스 요청 실패" + string);
	}

	public void displayMember(ArrayList<Member> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
	}

}
