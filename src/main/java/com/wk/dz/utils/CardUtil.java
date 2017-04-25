package com.wk.dz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prism.constant.entity.MSGException;

public class CardUtil {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CardUtil.class);
//身份证15位转18位计算最后一位校验码的用到的系数数组
	private static final int[] coefficientArray = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
	//身份证15位转18位计算最后一位校验码的用到的余数数组(X表示10)
	private static final char[] remainderArray = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

	private static final Integer[] provinceArray = { 11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 
		     37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 
		     63, 64, 65, 71, 81, 82, 91}; 
	
	/**
	 * 校验身份证合法性
	 * @param idcard
	 * @return
	 */
	public static boolean isLegalIdcard(String idcard) 
	{
		boolean isRight = false;
		try 
		{
			//校验身份证位数
			checkIdcardLength(idcard);
			//身份证位数转化(如果为15位则转化，否则不做转化但是要做身份证最后一位校验码校验)
			idcard = idcardFormat(idcard);
			//检验省级区域编号
			checkProvinceCode(idcard);
			//校验出生日期
			checkBirthday(idcard);
			//校验最后一位校验码是否正确
			checkVerifiedCode(idcard);
			isRight = true;
			return isRight;
		} 
		catch (Exception e) 
		{
			LOGGER.error("========isLegalIdcard error============={}",e.getMessage());
			return isRight;
		}
	}
	
	private static void checkIdcardLength(String idcard)
	{
		if(StringUtils.isEmpty(idcard))
		{
			throw new MSGException("身份证号为空!");
		}else if(idcard.length() != 15 && idcard.length() != 18)
		{
			throw new MSGException("身份证号位数错误！");
		}
	}
	private static String idcardFormat(String idcard)
	{
		if(idcard.length() == 15)
		{
			return idcardTransformation(idcard);
		}else
		{
			return idcard;
		}
		
	}
	private static void checkProvinceCode(String idcard)
	{
		List<Integer> provinceCodes = Arrays.asList(CardUtil.provinceArray);
		int code = Integer.valueOf(idcard.substring(0,2)).intValue();
		if(!provinceCodes.contains(code))
		{
			throw new MSGException("身份证号省级区域编号(身份证号前两位)错误!");
		}
	}
	/**
	 * 15、18身份证相互转化
	 * @param idcard
	 * @return
	 */
	public static String idcardTransformation(String idcard)
	{
		StringBuilder newIdcard = null;
		if(null != idcard && idcard.length() == 15)
		{
			newIdcard = new StringBuilder();
			newIdcard.append(idcard.substring(0, 6)).append("19").append(idcard.substring(6));
			int sum = 0;
			for(int i=0; i<17; i++)
			{
				sum += Integer.valueOf(newIdcard.substring(i, i+1))*CardUtil.coefficientArray[i];
			}
			newIdcard.append(CardUtil.remainderArray[sum%11]);
		}else if(null != idcard && idcard.length() == 18)
		{
			newIdcard = new StringBuilder();
			newIdcard.append(idcard.substring(0, 6)).append(idcard.substring(8, 17));
		}else 
		{
			throw new MSGException("身份证号错误!");
		}
		return newIdcard.toString();
	}
	private static void checkBirthday(String idcard)
	{
		String birthday = idcard.substring(6,14);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		/*禁用宽松验证日期*/
		simpleDateFormat.setLenient(false);
		try 
		{
			/*能够转换成功说明日期合法，否则抛异常*/
			simpleDateFormat.parse(birthday);
		} 
		catch (ParseException e) 
		{
			throw new MSGException("身份证号出生日期不合法！");
		}
	}
	private static void checkVerifiedCode(String idcard)
	{
		MSGException msgException = new MSGException("身份证输入错误!");
		if(idcard.length() == 15 && !idcard.toUpperCase().equals(idcardTransformation(idcard)))
		{
			throw msgException;
		}else 
		{
			int summary = 0;
			char verifiedCode = idcard.substring(17).toUpperCase().toCharArray()[0];
			for(int i=0; i<17; i++)
			{
				summary += Integer.valueOf(idcard.substring(i, i+1))*CardUtil.coefficientArray[i];
			}
			
			if(remainderArray[(summary % 11)] != verifiedCode)
			{
				throw msgException;
			}
		}
	}
	
	/**
	 * 根据身份证号计算年龄
	 * @param idcard
	 * @return
	 */
	public static int getAge(String idcard)
	{
		idcard = idcardFormat(idcard);
		String birthdaystr = idcard.substring(6,14);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		simpleDateFormat.setLenient(false);
		int age = -1;
		try {
			long birthday = simpleDateFormat.parse(birthdaystr).getTime();
			long now = new Date().getTime();
			age = Integer.valueOf(String.valueOf((now - birthday)/(1000L*3600*24*365))).intValue();
		} catch (ParseException e) 
		{
			LOGGER.error("=====getAge=======身份证号出生日期不合法！=============");
		}
		return age;
	}
}
