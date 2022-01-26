package com.nrifintech.medico.utility;

import java.util.HashMap;

public class TimeSlotConversion {
	 HashMap<Integer,String> slotToTime=new HashMap<Integer,String>() {{
		 put(1, "10:00"); put(2, "10:15"); put(3, "10:30"); put(4, "10:45");
		 put(5, "11:00"); put(6, "11:15"); put(7, "11:30"); put(8, "11:45"); 
		 put(9, "12:00"); put(10, "12:15"); put(11, "12:30"); put(12, "12:45"); 
		 put(13, "14:00"); put(14, "14:15"); put(15, "14:30"); put(16, "14:45");
		 put(17, "15:00"); put(18, "15:15"); put(19, "15:30"); put(20, "15:45");
		 put(21, "16:00"); put(22, "16:15"); put(23, "16:30"); put(24, "16:45");}};
	 
		 HashMap<String,Integer> timeToSlot=new HashMap<String,Integer>() {{
             put("10:00",1); put("10:15",2); put("10:30",3); put("10:45",4);
             put("11:00",5); put("11:15",6); put("11:30",7); put("11:45",8); 
             put("12:00",9); put("12:15",10); put("12:30",11); put("12:45",12); 
             put("14:00",13); put("14:15",14); put("14:30",15); put("14:45",16);
             put("15:00",17); put("15:15",18); put("15:30",19); put("15:45",20);
             put("16:00",21); put("16:15",22); put("16:30",23); put("16:45",24);}};
}
