package com.wk.dz.utils;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prism.constant.entity.MSGException;
 
public class MoneyUtil 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MoneyUtil.class);
    /**
     * @param d1
     * @param d2
     * @return d1+d2
     * number after more than 5 to up value 
     */
    public static BigDecimal add(BigDecimal d1,BigDecimal d2)
    {
        return d1.add(d2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * @param dArray
     * @return d1+d2+....
     * number after more than 5 to up value 
     */
    public static BigDecimal add(BigDecimal[] dArray)
    {
    	BigDecimal result = new BigDecimal("0");
    	if(null != dArray && dArray.length >0)
    	{
    		for(BigDecimal item : dArray)
    		{
    			if(null != item)
    			{
    				result = result.add(item);
    			}
    			
    		}
    	}
    	else
    	{
    		LOGGER.info("===============darray is empty");
    		return new BigDecimal("-1");
    	}
    	return result.setScale(4, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * @param d1
     * @param d2
     * @return d1 - d2
     * number after more than 5 to up value 
     */
    public static BigDecimal sub(BigDecimal d1,BigDecimal d2)
    {
        return d1.subtract(d2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * @param d1
     * @param d2
     * @return d1*d2
     * number after more than 5 to up value 
     */
    public static BigDecimal mul(BigDecimal d1,BigDecimal d2)
    {
   
        return d1.multiply(d2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
 
    /**
     * @param d1
     * @param d2
     * @return d1/d2
     * number after more than 5 to up value 
     */
    public static BigDecimal divide(BigDecimal d1,BigDecimal d2)
    {
        return d1.divide(d2,4,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * @param d1
     * @param d2
     * @Param roundRuleValue保留位数舍取规则值(0-7)
     * @return d1/d2
     * number after more than 5 to up value 
     */
    public static BigDecimal divide(BigDecimal d1,BigDecimal d2,int roundRuleValue)
    {
        if(roundRuleValue >= 0 && roundRuleValue <= 7 )
        {
        	return d1.divide(d2,4,roundRuleValue);
        }else 
        {
			throw new MSGException("舍取规则值roundRuleValue不合法！");
		}
    }
    
    public static Double mul(Double d1,Double d2)
    {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.multiply(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
 
    /**
     * @param d1
     * @param d2
     * @return d1+d2
     * number after more than 5 to up value 
     */
    public static Double add(Double d1,Double d2)
    {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.add(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
  
    /**
     * @param d1
     * @param d2
     * @return d1-d2
     * number after more than 5 to up value 
     */
    public static Double sub(Double d1,Double d2){
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.subtract(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
  
    /**
     * @param d1
     * @param d2
     * @return d1/d2
     * number after more than 5 to up value 
     */
    public static Double divide(Double d1,Double d2)
    {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.divide(bd2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
