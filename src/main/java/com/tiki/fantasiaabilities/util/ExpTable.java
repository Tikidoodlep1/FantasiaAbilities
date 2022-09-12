package com.tiki.fantasiaabilities.util;

public enum ExpTable {

	ZERO_NINE(51, 3),
	TEN_NINETEEN(102, 6),
	TWENTY_TWENTYNINE(153, 9),
	THIRTY_THIRTYNINE(204, 12),
	FORTY_FORTYNINE(255, 15),
	FIFTY(825, 30);
	
	private final int exp;
	private final int levels;
	ExpTable(int exp, int levelEquivalent) {
		this.exp = exp;
		this.levels = levelEquivalent;
	}
	
	public int getExp() {
		return this.exp;
	}
	
	public int getLevels() {
		return this.levels;
	}
	
	public static ExpTable getExpforLevelup(int curLevel) {
		if(curLevel < 10) {
			return ZERO_NINE;
		}else if(curLevel < 20) {
			return TEN_NINETEEN;
		}else if(curLevel < 30) {
			return TWENTY_TWENTYNINE;
		}else if(curLevel < 40) {
			return THIRTY_THIRTYNINE;
		}else if(curLevel < 50) {
			return FORTY_FORTYNINE;
		}else {
			return FIFTY;
		}
	}
}
