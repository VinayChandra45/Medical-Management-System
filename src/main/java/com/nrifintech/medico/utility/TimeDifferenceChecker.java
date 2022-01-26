package com.nrifintech.medico.utility;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimeDifferenceChecker {
	

	public boolean check(Date apptDate, String time)
	{
		System.out.println(" >>>>>>>>>>>>>>>>>> In TDC " + apptDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		String appt_date = sdf.format(apptDate);
		String appt_time = appt_date.substring(11);
		
		//current date and time retrieval
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String curr_date_and_time = dtf.format(now);
				
		//difference calculation
		LocalDate d1 = LocalDate.parse(curr_date_and_time, dtf);
		LocalTime t1 = LocalTime.parse(curr_date_and_time, dtf);
	
		LocalDate d2 = LocalDate.parse(appt_date + " " + appt_time, dtf);
		LocalTime t2 = LocalTime.parse(appt_date + " " + appt_time,dtf);
		
		Duration diff = Duration.between(d1.atTime(t1),d2.atTime(t2));
		
		long diffSeconds = diff.getSeconds();
		
		//final condition check
		if (diffSeconds>86400)
		{
			return true;
		}
		
		return false;
	}
	
}
