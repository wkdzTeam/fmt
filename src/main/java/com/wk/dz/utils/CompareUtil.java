package com.wk.dz.utils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author RuiChar
 *
 */
public class CompareUtil 
{
	/**
	 * @param o
	 * @param v
	 * @return (boolean)obj>V
	 */
	public static boolean isBiggerThan(Integer obj,int v)
	{
		return null != obj && obj > v;
	}
	
	/**
	 * @param obj
	 * @return (boolean)obj>0
	 */
	public static boolean isBiggerThan(Integer obj)
	{
		return null != obj && obj > 0;
	}
	
	/**
	 * 比较传入的值是否都大于0
	 * @param integers
	 * @return
	 */
	public static boolean isAllBiggerThanZero(Integer... integers )
	{
		if(null == integers || integers.length == 0)
		{
			return false;
		}
		for(Integer obj : integers)
		{
			if(!CompareUtil.isBiggerThan(obj))
				return false;
		}
		return true;
	}
	
	/**
	 * @param obj
	 * @return (boolean)obj>0
	 */
	public static boolean isBiggerThan(Double obj)
	{
		return null != obj && new BigDecimal(obj).compareTo(new BigDecimal(0.0)) >0;
	}
	
	/**
	 * 集合非空集合元素判断
	 */
	public static boolean isNullOrZeroList(List<?> list)
	{
		if(null == list || list.isEmpty())
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	/**
	 * 判断两个整数是否相等
	 */
	public static boolean isIntegerEquals(Integer a,Integer b)
	{
		if(null ==   a || null ==  b)
		{
			return false;
		}
		else
		{
			return a.intValue() == b.intValue();
		}
	}
}
