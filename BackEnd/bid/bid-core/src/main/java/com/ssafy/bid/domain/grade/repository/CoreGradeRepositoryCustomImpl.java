package com.ssafy.bid.domain.grade.repository;

import static com.ssafy.bid.domain.coupon.QUserCoupon.*;
import static com.ssafy.bid.domain.grade.QGrade.*;

import java.util.Optional;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.coupon.UsageStatus;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreGradeRepositoryCustomImpl implements CoreGradeRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<GradeStatisticsFindResponse> findGradeStatisticsByGradeNo(int gradeNo) {
		return Optional.ofNullable(
			queryFactory
				.select(Projections.constructor(GradeStatisticsFindResponse.class,
						ExpressionUtils.as(
							JPAExpressions
								.select(userCoupon.no.count())
								.from(userCoupon)
								.where(userCoupon.gradeNo.eq(gradeNo))
								.groupBy(userCoupon.useStatus)
								.having(userCoupon.useStatus.eq(UsageStatus.REQUEST_PROGRESS))
							, "unapprovedCouponCount"
						),
						grade.salary,
						grade.expenditureStatistics.sumSnackExpenditure,
						grade.expenditureStatistics.sumLearningExpenditure,
						grade.expenditureStatistics.sumCouponExpenditure,
						grade.expenditureStatistics.sumGameExpenditure,
						grade.expenditureStatistics.sumEtcExpenditure,
						grade.asset,
						grade.transferAlertPeriod,
						grade.transferPeriod,
						grade.biddingStatistics.countFourteenDaysAgo,
						grade.biddingStatistics.countThirteenDaysAgo,
						grade.biddingStatistics.countTwelveDaysAgo,
						grade.biddingStatistics.countElevenDaysAgo,
						grade.biddingStatistics.countTenDaysAgo,
						grade.biddingStatistics.countNineDaysAgo,
						grade.biddingStatistics.countEightDaysAgo,
						grade.biddingStatistics.countSevenDaysAgo,
						grade.biddingStatistics.countSixDaysAgo,
						grade.biddingStatistics.countFiveDaysAgo,
						grade.biddingStatistics.countFourDaysAgo,
						grade.biddingStatistics.countThreeDaysAgo,
						grade.biddingStatistics.countTwoDaysAgo,
						grade.biddingStatistics.countOneDaysAgo
					)
				)
				.from(grade)
				.where(
					grade.no.eq(gradeNo),
					grade.deletedAt.isNull()
				)
				.fetchOne()
		);
	}
}