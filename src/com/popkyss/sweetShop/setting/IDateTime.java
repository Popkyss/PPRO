package com.popkyss.sweetShop.setting;

import java.text.ParseException;
import java.util.Date;

public interface IDateTime {
	Date getCurrentDate();

	Date getCurrentDate(boolean paramBoolean);

	Date getCurrentDateNoTime();

	Date getCurrentTimestamp();

	int getCurrentYear();

	int getYear(Date paramDate);

	int getCurrentMonth();

	int getMonth(Date paramDate);

	int getCurrentWeek();

	int getWeek(Date paramDate);

	int getCurrentDay();

	int getDay(Date paramDate);

	int getCurrentDayOfWeek();

	int getDayOfWeek(Date paramDate);

	int getCurrentHour();

	int getHour(Date paramDate);

	int getCurrentMinute();

	int getMinute(Date paramDate);

	int getCurrentSecond();

	int getSecond(Date paramDate);

	int getCurrentQuarter();

	int getQuarter(Date paramDate);

	int getField(int paramInt, Date paramDate);

	Date getFirstSecondToday();

	Date getLastSecondToday();

	Date getFirstSecondOfHour(Date paramDate);

	Date getLastSecondOfHour(Date paramDate);

	Date getFirstSecondOfDay(Date paramDate);

	Date getLastSecondOfDay(Date paramDate);

	Date getFirstSecondOfWeek(int paramInt1, int paramInt2);

	Date getLastSecondOfWeek(int paramInt1, int paramInt2);

	Date getFirstSecondOfMonth(int paramInt1, int paramInt2);

	Date getLastSecondOfMonth(int paramInt1, int paramInt2);

	Date getFirstSecondOfYear(int paramInt);

	Date getLastSecondOfYear(int paramInt);

	Date setDate(Date paramDate, ZmenaCasuEntry... paramVarArgs) throws IllegalArgumentException;

	Date shiftDate(Date paramDate, ZmenaCasuEntry... paramVarArgs);

	Date getDate(ZmenaCasuEntry... paramVarArgs) throws IllegalArgumentException;

	boolean isTodayDate(Date paramDate);

	int compareDateWithoutTime(Date paramDate1, Date paramDate2) throws NullPointerException;

	String formatDate(Date paramDate, String paramString);

	Date parseDate(String paramString1, String paramString2)
			throws ParseException, IllegalArgumentException, ParseException;

	public static final class ZmenaCasuEntry {
		private final int key;

		public ZmenaCasuEntry(int key, int value) {
			this.key = key;
			this.value = value;
		}

		private final int value;

		public int getKey() {
			return this.key;
		}

		public int getValue() {
			return this.value;
		}
	}
}