package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentsGetResponse {
	private int no;
	private int number;
	private String name;
	private int asset;

	public StudentsGetResponse(
		int no,
		String id,
		String name,
		int asset
	) {
		this.no = no;
		this.number = Integer.parseInt(id.substring(6).strip());
		this.name = name;
		this.asset = asset;
	}
}
