package br.com.noverde.challenge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommitmentUtils {

	private static final int SCALE = 2;
	private static List<InterestFeeTable> list;
	static {
		list = Arrays.asList(new InterestFeeTable(900, 1000, 6, 3.9), new InterestFeeTable(900, 1000, 9, 4.2),
				new InterestFeeTable(900, 1000, 12, 4.5), new InterestFeeTable(800, 899, 6, 4.7),
				new InterestFeeTable(800, 899, 9, 5.0), new InterestFeeTable(800, 899, 12, 5.3),
				new InterestFeeTable(700, 799, 6, 5.5), new InterestFeeTable(700, 799, 9, 5.8),
				new InterestFeeTable(700, 799, 12, 6.1), new InterestFeeTable(600, 699, 6, 6.4),
				new InterestFeeTable(600, 699, 9, 6.6), new InterestFeeTable(600, 699, 12, 6.9));

	}

	public static BigDecimal getParcelInterest(int score, int parcel, BigDecimal amount) {

		int minScore = 0;
		int maxScore = 0;
		if (score > 899) {
			minScore = 900;
			maxScore = 1000;
		} else if (score < 900 && score > 799) {
			minScore = 800;
			maxScore = 899;
		} else if (score < 800 && score > 699) {
			minScore = 700;
			maxScore = 799;
		} else if (score < 700 && score >= 600) {
			minScore = 600;
			maxScore = 699;
		}

		InterestFeeTable temp = new InterestFeeTable(minScore, maxScore, parcel, 0.0);

		int idx = list.indexOf(temp);

		BigDecimal interest = list.get(idx).getInterest().divide(BigDecimal.valueOf(100));

		
		BigDecimal lineOne = BigDecimal.ONE.add(interest).pow(parcel).subtract(BigDecimal.ONE);

		BigDecimal lineTwo = BigDecimal.ONE.add(interest).pow(parcel).multiply(interest);

		BigDecimal fee = lineOne.divide(lineTwo, SCALE, RoundingMode.HALF_EVEN);

		BigDecimal parcelInterest = amount.divide(fee, SCALE, RoundingMode.HALF_EVEN);

		return parcelInterest;
	}

	
}

class InterestFeeTable {

	private Integer minScore;
	private Integer maxScore;
	private Integer parcel;
	private BigDecimal interest;

	InterestFeeTable(Integer minScore, Integer maxScore, Integer parcel, Double interest) {
		this.minScore = minScore;
		this.maxScore = maxScore;
		this.parcel = parcel;
		this.interest = BigDecimal.valueOf(interest);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof InterestFeeTable)) {
			return false;
		}
		InterestFeeTable interestFeeTable = (InterestFeeTable) o;
		return Objects.equals(minScore, interestFeeTable.minScore)
				&& Objects.equals(maxScore, interestFeeTable.maxScore)
				&& Objects.equals(parcel, interestFeeTable.parcel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(minScore, maxScore, parcel);
	}

	public BigDecimal getInterest() {
		return this.interest;
	}

}
