package com.jiuzhou.plat.util;

import java.io.UnsupportedEncodingException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Property {
    private static String RESOURCE;    
    private static ResourceBundle resource;
    
    public static Property config(){    	    	
    	return new Property();    	
    	
    }
    
    public static Property error(){    	    		
    	return new Property("errorMessage");    	    	
    }
    
    
    public static Property mail(){    	   		
    	return new Property("mail");    	   
    }
    
    public Property() {
        RESOURCE = "jdbc";
        getBundle();
    }

    public Property(String bd) {
        RESOURCE = bd;
        getBundle();
    }

    private void getBundle() {
        try {
            resource = ResourceBundle.getBundle(RESOURCE);
        } catch (MissingResourceException e) {
        }
    }

    public static String getString(String akey, String avalue) {
        String value = null;
        try {
            value = resource.getString(akey);
        } catch (Exception e) {}
        if (value == null || value.length() < 1) {
            value = avalue;
        }
        try {
            return new String(value.getBytes("ISO_8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return value;
        }
    }


    private  static String[] getParameter(String strMessage) {
        char cTemp = 0;
        String[] parameter = null;
        int intPos = strMessage.indexOf(":");
        if (intPos > 0) {
            strMessage = strMessage.substring(intPos + 1);
            parameter = strMessage.split(Character.toString(cTemp));
            return parameter;
        } else {
            return null;
        }
    }

    /**
     *
     * @param astrMessage String
     * @return String
     */
    public  static String getMessage(String astrMessage) {
        int intPos = astrMessage.indexOf(":");
        String strNum = "";
        if (intPos > 0) {
            strNum = astrMessage.substring(0, intPos);
        } else {
            return getString(astrMessage, "");
        }
        String strSource = getString(strNum, "");
        String[] strParam = getParameter(astrMessage);
        if (strParam != null) {
            for (int i = 0; i < strParam.length; i++) {
                strSource = strSource.replace("{" + i + "}", strParam[i]);
            }
        }
        return strSource;
    }
}
