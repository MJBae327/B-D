package com.ssafy.bid.domain.grade.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.grade.ExpenditureStatistics;
import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;

import lombok.Getter;

@Getter
public class StudentListSaveRequest {
	private String id;
	private String password;
	private String name;
	private String birthDate;
	private int number;

	public Student toEntity(PasswordEncoder passwordEncoder, int schoolNo, int gradeNo, String id) {
		return Student.builder()
			.id(id + String.format("%02d", this.number))
			.password(passwordEncoder.encode(password))
			.name(name)
			.schoolNo(schoolNo)
			.birthDate(birthDate)
			.asset(0)
			.ballCount(1)
			.profileImgUrl("https://ssafya306.s3.ap-northeast-2.amazonaws.com/DefaultBody.png")
			.attendance(new Attendance())
			.expenditureStatistics(new ExpenditureStatistics())
			.gradeNo(gradeNo)
			.taxRate(3)
			.build();
	}
}
