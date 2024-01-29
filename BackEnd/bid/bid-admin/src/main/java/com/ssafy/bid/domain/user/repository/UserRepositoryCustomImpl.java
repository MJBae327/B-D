package com.ssafy.bid.domain.user.repository;

import static com.querydsl.core.types.ExpressionUtils.*;
import static com.ssafy.bid.domain.coupon.QCoupon.*;
import static com.ssafy.bid.domain.coupon.QUserCoupon.*;
import static com.ssafy.bid.domain.saving.QSaving.*;
import static com.ssafy.bid.domain.saving.QUserSaving.*;
import static com.ssafy.bid.domain.user.QAccount.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.user.dto.AccountsResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<UserCouponsResponse> searchUserCoupons(int userNo) {
		return queryFactory
			.select(Projections.constructor(UserCouponsResponse.class,
					coupon.name,
					count(userCoupon.no)
				)
			)
			.from(coupon)
			.innerJoin(userCoupon).on(userCoupon.couponNo.eq(coupon.no))
			.where(userCoupon.userNo.eq(userNo))
			.groupBy(userCoupon.couponNo)
			.fetch();
	}

	@Override
	public List<AccountsResponse> searchAccounts(int userNo, StudentRequest studentRequest) {
		return queryFactory
			.select(Projections.constructor(AccountsResponse.class,
					account.createdAt.dayOfMonth(),
					account.price.sum(),
					account.accountType,
					account.dealType
				)
			)
			.from(account)
			.where(
				account.userNo.eq(userNo),
				account.createdAt.goe(studentRequest.getStartDate().atStartOfDay()),
				account.createdAt.loe(studentRequest.getEndDate().atStartOfDay())
			)
			.groupBy(
				account.createdAt.dayOfMonth(),
				account.accountType,
				account.dealType
			)
			.orderBy(account.createdAt.dayOfMonth().desc())
			.fetch();
	}

	@Override
	public Optional<StudentResponse> searchStudent(int userNo) {
		return Optional.ofNullable(
			queryFactory
				.select(Projections.constructor(StudentResponse.class,
						saving.no,
						saving.depositPeriod,
						saving.interestRate,
						userSaving.startPeriod,
						saving.depositPrice,
						userSaving.resultPrice,
						student.attendance.monday,
						student.attendance.tuesday,
						student.attendance.wednesday,
						student.attendance.thursday,
						student.attendance.friday,
						student.ballCount
					)
				)
				.from(student)
				.innerJoin(userSaving).on(userSaving.userNo.eq(student.no))
				.innerJoin(saving).on(saving.no.eq(userSaving.savingNo))
				.where(student.no.eq(userNo))
				.fetchOne()
		);
	}
}
