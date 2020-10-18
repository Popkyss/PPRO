package com.popkyss.sweetShop.setting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime implements IDateTime {
	private final ICoreDao coreDao = CoreDaoFactory.getCoreDao();

	public Date getCurrentDate() {
		return getCurrentTimestamp();
	}

	@Deprecated
	public Date getCurrentDate(boolean ommitTime) {
		if (ommitTime) {
			return this.coreDao.getCurrentDate();
		}
		return this.coreDao.getCurrentTimestamp();
	}

	public Date getCurrentDateNoTime() {
		return this.coreDao.getCurrentDate();
	}

	public Date getCurrentTimestamp() {
		return this.coreDao.getCurrentTimestamp();
	}

	public Date getFirstSecondToday() {
		Date date = getCurrentTimestamp();
		return getFirstSecondOfDay(date);
	}

	public Date getLastSecondToday() {
		Date date = getCurrentTimestamp();
		return getLastSecondOfDay(date);
	}

	public Date getFirstSecondOfHour(Date date) {
		return setDate(date, new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(12, 0),
				new IDateTime.ZmenaCasuEntry(13, 0), new IDateTime.ZmenaCasuEntry(14, 0) });
	}

	public Date getLastSecondOfHour(Date date) {
		return setDate(date, new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(12, 59),
				new IDateTime.ZmenaCasuEntry(13, 59), new IDateTime.ZmenaCasuEntry(14, 999) });
	}

	public Date getFirstSecondOfDay(Date date) {
		return setDate(date,
				new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(11, 0),
						new IDateTime.ZmenaCasuEntry(12, 0), new IDateTime.ZmenaCasuEntry(13, 0),
						new IDateTime.ZmenaCasuEntry(14, 0) });
	}

	public Date getLastSecondOfDay(Date date) {
		return setDate(date,
				new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(11, 23),
						new IDateTime.ZmenaCasuEntry(12, 59), new IDateTime.ZmenaCasuEntry(13, 59),
						new IDateTime.ZmenaCasuEntry(14, 999) });
	}

	public Date getFirstSecondOfWeek(int week, int year) {
		return getFirstSecondOfDay(getDate(new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(1, year),
				new IDateTime.ZmenaCasuEntry(3, week), new IDateTime.ZmenaCasuEntry(7, 2) }));
	}

	public Date getLastSecondOfWeek(int week, int year) {
		Date startOfWeek = getDate(new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(1, year),
				new IDateTime.ZmenaCasuEntry(3, week), new IDateTime.ZmenaCasuEntry(7, 2) });
		return getLastSecondOfDay(shiftDate(startOfWeek, new IDateTime.ZmenaCasuEntry[] {
				new IDateTime.ZmenaCasuEntry(3, 1), new IDateTime.ZmenaCasuEntry(5, -1) }));
	}

	public Date getFirstSecondOfMonth(int month, int year) {
		return getFirstSecondOfDay(getDate(new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(1, year),
				new IDateTime.ZmenaCasuEntry(2, month), new IDateTime.ZmenaCasuEntry(5, 1) }));
	}

	public Date getLastSecondOfMonth(int month, int year) {
		Date startOfMonth = getDate(new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(1, year),
				new IDateTime.ZmenaCasuEntry(2, month), new IDateTime.ZmenaCasuEntry(5, 1) });

		return getLastSecondOfDay(shiftDate(startOfMonth, new IDateTime.ZmenaCasuEntry[] {
				new IDateTime.ZmenaCasuEntry(2, 1), new IDateTime.ZmenaCasuEntry(5, -1) }));
	}

	public Date getFirstSecondOfYear(int year) {
		return getFirstSecondOfDay(getDate(new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(1, year),
				new IDateTime.ZmenaCasuEntry(6, 1) }));
	}

	public Date getLastSecondOfYear(int year) {
		Date startOfYear = getDate(new IDateTime.ZmenaCasuEntry[] { new IDateTime.ZmenaCasuEntry(1, year),
				new IDateTime.ZmenaCasuEntry(6, 1) });
		return getLastSecondOfDay(shiftDate(startOfYear, new IDateTime.ZmenaCasuEntry[] {
				new IDateTime.ZmenaCasuEntry(1, 1), new IDateTime.ZmenaCasuEntry(5, -1) }));
	}

	public Date setDate(Date date, IDateTime.ZmenaCasuEntry... zmeny) throws IllegalArgumentException {
		Calendar cal = getCalendarInstance(date);
		byte b;
		int i;
		IDateTime.ZmenaCasuEntry[] arrayOfZmenaCasuEntry;
		for (i = (arrayOfZmenaCasuEntry = zmeny).length, b = 0; b < i;) {
			IDateTime.ZmenaCasuEntry zmena = arrayOfZmenaCasuEntry[b];
			int val = (zmena.getKey() == 2) ? (zmena.getValue() - 1) : zmena.getValue();
			if (val < 0) {
				throw new IllegalArgumentException("Pole datumu lze nastavit pouze na nezaporne hodnoty");
			}
			cal.set(zmena.getKey(), val);
			b++;
		}

		return cal.getTime();
	}

	public Date shiftDate(Date date, IDateTime.ZmenaCasuEntry... zmeny) {
		Calendar cal = getCalendarInstance(date);
		byte b;
		int i;
		IDateTime.ZmenaCasuEntry[] arrayOfZmenaCasuEntry;
		for (i = (arrayOfZmenaCasuEntry = zmeny).length, b = 0; b < i;) {
			IDateTime.ZmenaCasuEntry zmena = arrayOfZmenaCasuEntry[b];
			cal.add(zmena.getKey(), zmena.getValue());
			b++;
		}

		return cal.getTime();
	}

	protected Calendar getCalendarInstance(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);
		cal.setFirstDayOfWeek(2);
		cal.setTime(date);
		return cal;
	}

	public Date getDate(IDateTime.ZmenaCasuEntry... zmeny) throws IllegalArgumentException {
		return setDate(new Date(), zmeny);
	}

	public boolean isTodayDate(Date date) {
		long dnesMili = getFirstSecondToday().getTime();
		long dateMili = getFirstSecondOfDay(date).getTime();
		return (dnesMili == dateMili);
	}

	public int getCurrentYear() {
		return getYear(getCurrentDate());
	}

	public int getYear(Date date) {
		return getField(1, date);
	}

	public int getCurrentMonth() {
		return getMonth(getCurrentDate());
	}

	public int getMonth(Date date) {
		return getField(2, date);
	}

	public int getCurrentWeek() {
		return getWeek(getCurrentDate());
	}

	public int getWeek(Date date) {
		return getField(3, date);
	}

	public int getCurrentDay() {
		return getDay(getCurrentDate());
	}

	public int getDay(Date date) {
		return getField(5, date);
	}

	public int getCurrentDayOfWeek() {
		return getDayOfWeek(getCurrentDate());
	}

	public int getDayOfWeek(Date date) {
		return getField(7, date);
	}

	public int getCurrentHour() {
		return getHour(getCurrentDate());
	}

	public int getHour(Date date) {
		return getField(11, date);
	}

	public int getCurrentMinute() {
		return getMinute(getCurrentDate());
	}

	public int getMinute(Date date) {
		return getField(12, date);
	}

	public int getCurrentSecond() {
		return getSecond(getCurrentDate());
	}

	public int getSecond(Date date) {
		return getField(13, date);
	}

	public int getField(int key, Date date) {
		Calendar cal = getCalendarInstance(date);
		int value = cal.get(key);
		if (key == 2) {
			value++;
		} else if (key == 7) {
			value = (value == 1) ? 7 : (value - 1);
		}
		return value;
	}

	public int getCurrentQuarter() {
		return getQuarter(getCurrentDate());
	}

	public int getQuarter(Date datum) {
		int mesicMinus1 = getMonth(datum) - 1;
		return 1 + mesicMinus1 / 3;
	}

	public int compareDateWithoutTime(Date a, Date b) throws NullPointerException {
		if (a == null || b == null) {
			throw new NullPointerException("Argumenty metody nesmi byt null");
		}
		Date dateA = getFirstSecondOfDay(a);
		Date dateB = getFirstSecondOfDay(b);
		return dateA.compareTo(dateB);
	}

	public String formatDate(Date date, String formatPattern) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isEmpty(formatPattern)) {
			return date.toString();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
		return sdf.format(date);
	}

	public Date parseDate(String formattedDate, String formatPattern) throws ParseException, IllegalArgumentException {
		if (formattedDate == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
		return sdf.parse(formattedDate);
	}
}