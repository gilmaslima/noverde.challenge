package br.com.noverde.challenge.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class CommitmentUtilsTest {

	
	
	@Test
	public void parcel_600_6_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(600, 6, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(205.76), parcelInterest);
	}
	
	
	@Test
	public void parcel_600_9_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(600, 9, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(150.83), parcelInterest);
	}
	
	@Test
	public void parcel_600_12_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(600, 12, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(125.16), parcelInterest);
	}
	
	
	@Test
	public void parcel_700_6_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(700, 6, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(200.00).setScale(2), parcelInterest.setScale(2));
	}
	
	
	@Test
	public void parcel_700_9_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(700, 9, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(145.77), parcelInterest);
	}
	
	@Test
	public void parcel_700_12_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(700, 12, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(119.90).setScale(2), parcelInterest.setScale(2));
	}
	
	
	@Test
	public void parcel_800_6_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(800, 6, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(195.31).setScale(2), parcelInterest.setScale(2));
	}
	
	
	@Test
	public void parcel_800_9_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(800, 9, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(140.65), parcelInterest);
	}
	
	@Test
	public void parcel_800_12_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(800, 12, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(114.68).setScale(2), parcelInterest.setScale(2));
	}
	
	@Test
	public void parcel_900_6_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(900, 6, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(190.11).setScale(2), parcelInterest.setScale(2));
	}
	
	
	@Test
	public void parcel_900_9_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(900, 9, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(135.69), parcelInterest);
	}
	
	@Test
	public void parcel_900_12_1000() {
		BigDecimal parcelInterest = CommitmentUtils.getParcelInterest(900, 12, BigDecimal.valueOf(1000));
		assertEquals(BigDecimal.valueOf(109.65).setScale(2), parcelInterest.setScale(2));
	}
}
