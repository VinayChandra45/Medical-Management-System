package com.nrifintech.medico.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class CredentialFormatValidator {
	
	public boolean isNotBlank(String name) {
		name = name.trim();
		return !(name.equals(""));
	}
	
	public boolean patternMatcher(String regex, String obj) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(obj);
		return m.matches();
	}
	
	public boolean passwordValidator(String password) {
        String regex = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        return patternMatcher(regex, password);
    }
	
	public boolean isValidPhone(String phone) {
		String regex = "(^$|[0-9]{10})";
		
		if(!(patternMatcher(regex, phone)) || phone.length()!=10)
			return false;
		int count=0;
		char start = phone.charAt(0);
		if(start<'6' || start>'9')
			return false;
		String end = phone.substring(2);
		char mid = phone.charAt(1);
		for(int i=0;i<10;i++) {
			if(mid==phone.charAt(i))
				count++;
		}
		return (count<8) && (phone.chars().filter(ch -> ch == start).count()<9) ;
	}
	
	public boolean isValidEmail(String username) {
	    
	    String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	    return patternMatcher(regex, username);
	}

}
