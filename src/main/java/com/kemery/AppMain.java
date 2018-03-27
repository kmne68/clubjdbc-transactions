package com.kemery;

import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppMain {
	
	public static void main(String[] args) throws SQLException {
		
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClubDBConfiguration.class);
		
		MemberService memberService = applicationContext.getBean(MemberService.class);
		
		Scanner sc = new Scanner(System.in);
		String memid, success;
		
		System.out.print("Member (or quit): ");
		memid = sc.nextLine();
		while(!memid.equalsIgnoreCase("quit")) {
			System.out.print("Succeed? (Y/N): ");
			success = sc.nextLine();
			
			memberService.renewMember(memid, success);
			
			System.out.print("Member (or quit): ");
			memid = sc.nextLine();
		}		
		applicationContext.close();		
	}
}
