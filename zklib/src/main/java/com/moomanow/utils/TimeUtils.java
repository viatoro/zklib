package com.moomanow.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

	public static Date decodeTime(long  t) {
//		System.out.println(timeLong);
//		int t = (int) timeLong;
//		System.out.println(t);
		int second = (int) (t % 60);
		t = t / 60;

		int minute = (int) (t % 60);
		t = t / 60;

		int hour = (int) (t % 24);
		t = t / 24;

		int day = (int) (t % 31)+1;
		t = t / 31;

		int month = (int) (t % 12);
		t = t / 12;

		int year = (int) (t + 2000);

		Calendar calendar = Calendar.getInstance();
//		System.out.println(year);
//		System.out.println(month);
//		System.out.println(day);
//		System.out.println(minute);
//		System.out.println(second);
		calendar.set(year, month, day,hour, minute, second);
		return calendar.getTime();
	}

}
