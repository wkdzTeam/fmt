package com.wk.dz.utils;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class RandomUtil 
{
	public static String generateUiqueCode()
	{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int millSecond = cal.get(Calendar.MILLISECOND);
		StringBuilder sbl = new StringBuilder();
		int d = getRandomIntVal();
		sbl.append(year).append(month).append(day).append(hour).append(minute).
		append(second).append(millSecond).append(d).append(UUID.randomUUID());
		return sbl.toString().replace("-", "");
	}
	
	public static int getRandomIntVal()
	{
        int max=99999;
        int min=1;
        Random random = new Random();
        int ranInt = random.nextInt(max)%(max-min+1) + min;
        return ranInt;
	}
	public static int getRandomIntVal(int max,int min)
	{
		Random random = new Random();
		int ranInt = random.nextInt(max)%(max-min+1) + min;
		return ranInt;
	}
	public static void main(String[] args) 
	{
		String s = RandomUtil.generateUiqueCode();
		System.out.println(s);
		System.out.println(s.length());
	}
}
