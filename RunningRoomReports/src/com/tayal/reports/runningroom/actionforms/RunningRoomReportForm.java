package com.tayal.reports.runningroom.actionforms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class RunningRoomReportForm extends ActionForm{ 
	
	
	String from_date;
	String to_date;
	String hourly_date;
	String crew_id_filter;
	String room_no_filter;
	String bed_no_filter;
	String lobby_filter;
	String frequency;
	
	// FOR OCCUPANCY REPORTS
	String[] date;
	String[] crew_id;
	String[] bed_no;
	String[] room_no;
	String[] checkin;
	String[] checkin_time;
	String[] checkout;
	String[] checkout_time;
	String[] rest_hrs;
	String[] wakeup_time;
	String[] crew_count;
	String[] hour;
	
	// FOR MAX MIN COUNT
	String[] max_hour;
	String[] min_hour;
	String[] max_count;
	String[] min_count;
	
	
	// FOR REST POSITION
	String[] r1_4;
	String[] r4_6;
	String[] r6_8;
	String[] r8_10;
	String[] r10_12;
	String[] r12_14;
	String[] r14_16;
	String[] r16_above;
	
	
	
	
	
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	
	public String getCrew_id_filter() {
		return crew_id_filter;
	}
	
	public String getRoom_no_filter() {
		return room_no_filter;
	}
	public void setRoom_no_filter(String room_no_filter) {
		this.room_no_filter = room_no_filter;
	}
	public void setCrew_id_filter(String crew_id_filter) {
		this.crew_id_filter = crew_id_filter;
	}
	public String[] getCrew_id() {
		return crew_id;
	}
	public void setCrew_id(String[] crew_id) {
		this.crew_id = crew_id;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getHourly_date() {
		return hourly_date;
	}
	public void setHourly_date(String hourly_date) {
		this.hourly_date = hourly_date;
	}

	public String getLobby_filter() {
		return lobby_filter;
	}
	public void setLobby_filter(String lobby_filter) {
		this.lobby_filter = lobby_filter;
	}
	
	
	public String getBed_no_filter() {
		return bed_no_filter;
	}
	public void setBed_no_filter(String bed_no_filter) {
		this.bed_no_filter = bed_no_filter;
	}
	public String[] getBed_no() {
		return bed_no;
	}
	public void setBed_no(String[] bed_no) {
		this.bed_no = bed_no;
	}
	public String[] getRoom_no() {
		return room_no;
	}
	public void setRoom_no(String[] room_no) {
		this.room_no = room_no;
	}
	public String[] getCheckin() {
		return checkin;
	}
	public void setCheckin(String[] checkin) {
		this.checkin = checkin;
	}
	public String[] getCheckout() {
		return checkout;
	}
	public void setCheckout(String[] checkout) {
		this.checkout = checkout;
	}
	
	public String[] getWakeup_time() {
		return wakeup_time;
	}
	public void setWakeup_time(String[] wakeup_time) {
		this.wakeup_time = wakeup_time;
	}
	public String[] getCheckin_time() {
		return checkin_time;
	}
	public void setCheckin_time(String[] checkin_time) {
		this.checkin_time = checkin_time;
	}
	public String[] getCheckout_time() {
		return checkout_time;
	}
	public void setCheckout_time(String[] checkout_time) {
		this.checkout_time = checkout_time;
	}
	
	public String[] getRest_hrs() {
		return rest_hrs;
	}
	public void setRest_hrs(String[] rest_hrs) {
		this.rest_hrs = rest_hrs;
	}

	public String[] getDate() {
		return date;
	}
	public void setDate(String[] date) {
		this.date = date;
	}
	public String[] getCrew_count() {
		return crew_count;
	}
	public void setCrew_count(String[] crew_count) {
		this.crew_count = crew_count;
	}
	public String[] getHour() {
		return hour;
	}
	public void setHour(String[] hour) {
		this.hour = hour;
	}
	
	
	
	
	// FOR MAX MIN COUNT
	public String[] getMax_count() {
		return max_count;
	}
	public void setMax_count(String[] max_count) {
		this.max_count = max_count;
	}
	public String[] getMin_count() {
		return min_count;
	}
	public void setMin_count(String[] min_count) {
		this.min_count = min_count;
	}
	public String[] getMax_hour() {
		return max_hour;
	}
	public void setMax_hour(String[] max_hour) {
		this.max_hour = max_hour;
	}
	public String[] getMin_hour() {
		return min_hour;
	}
	public void setMin_hour(String[] min_hour) {
		this.min_hour = min_hour;
	}




// FOR REST POSITION REPORTS

	public String[] getR1_4() {
		return r1_4;
	}
	public void setR1_4(String[] r1_4) {
		this.r1_4 = r1_4;
	}
	public String[] getR4_6() {
		return r4_6;
	}
	public void setR4_6(String[] r4_6) {
		this.r4_6 = r4_6;
	}
	public String[] getR6_8() {
		return r6_8;
	}
	public void setR6_8(String[] r6_8) {
		this.r6_8 = r6_8;
	}
	public String[] getR8_10() {
		return r8_10;
	}
	public void setR8_10(String[] r8_10) {
		this.r8_10 = r8_10;
	}
	public String[] getR10_12() {
		return r10_12;
	}
	public void setR10_12(String[] r10_12) {
		this.r10_12 = r10_12;
	}
	public String[] getR12_14() {
		return r12_14;
	}
	public void setR12_14(String[] r12_14) {
		this.r12_14 = r12_14;
	}
	public String[] getR14_16() {
		return r14_16;
	}
	public void setR14_16(String[] r14_16) {
		this.r14_16 = r14_16;
	}
	public String[] getR16_above() {
		return r16_above;
	}
	public void setR16_above(String[] r16_above) {
		this.r16_above = r16_above;
	}










	public class Myrow
	{

		
		String crew_id;
		String bed_no;
		String room_no;
		String checkin;
		String checkin_time;
		String checkout;
		String checkout_time;
		String rest_hrs;
		String wakeup_time;
		
		
		
		public String getCrew_id() {
			return crew_id;
		}
		public void setCrew_id(String crew_id) {
			this.crew_id = crew_id;
		}
		public String getBed_no() {
			return bed_no;
		}
		public void setBed_no(String bed_no) {
			this.bed_no = bed_no;
		}
		public String getRoom_no() {
			return room_no;
		}
		public void setRoom_no(String room_no) {
			this.room_no = room_no;
		}
		public String getCheckin() {
			return checkin;
		}
		public void setCheckin(String checkin) {
			this.checkin = checkin;
		}
		public String getCheckout() {
			return checkout;
		}
		public void setCheckout(String checkout) {
			this.checkout = checkout;
		}
		public String getCheckin_time() {
			return checkin_time;
		}
		public void setCheckin_time(String checkin_time) {
			this.checkin_time = checkin_time;
		}
		public String getCheckout_time() {
			return checkout_time;
		}
		public void setCheckout_time(String checkout_time) {
			this.checkout_time = checkout_time;
		}
		
		public String getRest_hrs() {
			return rest_hrs;
		}
		public void setRest_hrs(String rest_hrs) {
			this.rest_hrs = rest_hrs;
		}
		public String getWakeup_time() {
			return wakeup_time;
		}
		public void setWakeup_time(String wakeup_time) {
			this.wakeup_time = wakeup_time;
		}
		
		
		
		
		
		
	}
	
	
	

	public ArrayList<Myrow> getMyrows()
	{
		int len=crew_id.length;
		
		
		ArrayList<Myrow> selrow=new ArrayList<Myrow>();
		try
		{
		
			
			if(len!=0)
			{
				for(int i=0; i < len ;i++)
				{
				RunningRoomReportForm.Myrow rowobj=this.new Myrow();
					try{
				
						
						rowobj.crew_id = (crew_id[i] == null ) ? "-" : crew_id[i];
						rowobj.bed_no = (bed_no[i] == null ) ? "-" : bed_no[i];
						rowobj.room_no = (room_no[i] == null ) ? "-" : room_no[i];
						rowobj.checkin = (checkin[i] == null ) ? "-" : checkin[i];
						rowobj.checkin_time = (checkin_time[i] == null ) ? "-" : checkin_time[i];
						rowobj.checkout = (checkout[i] == null ) ? "-" : checkout[i];
						rowobj.checkout_time = (checkout_time[i] == null ) ? "-" : checkout_time[i];
						rowobj.rest_hrs = (rest_hrs[i] == null ) ? "-" : rest_hrs[i];
						rowobj.wakeup_time = (wakeup_time[i] == null ) ? "-" : wakeup_time[i];
						
										
						
					}catch(Exception e)
					{	
					e.printStackTrace();
					}
					
				selrow.add(rowobj);
				}
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		return selrow;
	} // GETMYROWS ENDS 


	public class Mycount
	{

		
		String date;
		String hour;
		String crew_count;
		
		
		
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getHour() {
			return hour;
		}
		public void setHour(String hour) {
			this.hour = hour;
		}
		public String getCrew_count() {
			return crew_count;
		}
		public void setCrew_count(String crew_count) {
			this.crew_count = crew_count;
		}		
		
		
	}

	

	public ArrayList<Mycount> getMycounts()
	{
		int len=date.length;
		
		
		ArrayList<Mycount> selrow=new ArrayList<Mycount>();
		try
		{
		
			
			if(len!=0)
			{
				for(int i=0; i < len ;i++)
				{
				RunningRoomReportForm.Mycount rowobj=this.new Mycount();
					try{
				
						
						rowobj.date = (date[i] == null ) ? "-" : date[i];
						rowobj.hour = (hour[i] == null ) ? "-" : hour[i];
						rowobj.crew_count = (crew_count[i] == null ) ? "-" : crew_count[i];
						
										
						
					}catch(Exception e)
					{	
					e.printStackTrace();
					}
					
				selrow.add(rowobj);
				}
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		return selrow;
	} // GETMYROWS ENDS 




	public class Myposition
	{

		
		String date;
		String max_hour;
		String min_hour;
		String max_count;
		String min_count;
		
		
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getMax_hour() {
			return max_hour;
		}
		public void setMax_hour(String max_hour) {
			this.max_hour = max_hour;
		}
		public String getMin_hour() {
			return min_hour;
		}
		public void setMin_hour(String min_hour) {
			this.min_hour = min_hour;
		}
		public String getMax_count() {
			return max_count;
		}
		public void setMax_count(String max_count) {
			this.max_count = max_count;
		}
		public String getMin_count() {
			return min_count;
		}
		public void setMin_count(String min_count) {
			this.min_count = min_count;
		}
		
		
	}


	

	public ArrayList<Myposition> getMypositions()
	{
		int len=date.length;
		
		
		ArrayList<Myposition> selrow=new ArrayList<Myposition>();
		try
		{
		
			
			if(len!=0)
			{
				for(int i=0; i < len ;i++)
				{
				RunningRoomReportForm.Myposition rowobj=this.new Myposition();
					try{
				
						
						rowobj.date = (date[i] == null ) ? "-" : date[i];
						rowobj.max_hour = (max_hour[i] == null ) ? "-" : max_hour[i];
						rowobj.min_hour = (min_hour[i] == null ) ? "-" : min_hour[i];
						rowobj.max_count = (max_count[i] == null ) ? "-" : max_count[i];
						rowobj.min_count = (min_count[i] == null ) ? "-" : min_count[i];
						
					}catch(Exception e)
					{	
					e.printStackTrace();
					}
					
				selrow.add(rowobj);
				}
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		return selrow;
	} // GETMYROWS ENDS 



	

	public class Restposition
	{

		
		String date;
		String r1_4;
		String r4_6;
		String r6_8;
		String r8_10;
		String r10_12;
		String r12_14;
		String r14_16;
		String r16_above;
		
		
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getR1_4() {
			return r1_4;
		}
		public void setR1_4(String r1_4) {
			this.r1_4 = r1_4;
		}
		public String getR4_6() {
			return r4_6;
		}
		public void setR4_6(String r4_6) {
			this.r4_6 = r4_6;
		}
		public String getR6_8() {
			return r6_8;
		}
		public void setR6_8(String r6_8) {
			this.r6_8 = r6_8;
		}
		public String getR8_10() {
			return r8_10;
		}
		public void setR8_10(String r8_10) {
			this.r8_10 = r8_10;
		}
		public String getR10_12() {
			return r10_12;
		}
		public void setR10_12(String r10_12) {
			this.r10_12 = r10_12;
		}
		public String getR12_14() {
			return r12_14;
		}
		public void setR12_14(String r12_14) {
			this.r12_14 = r12_14;
		}
		public String getR14_16() {
			return r14_16;
		}
		public void setR14_16(String r14_16) {
			this.r14_16 = r14_16;
		}
		public String getR16_above() {
			return r16_above;
		}
		public void setR16_above(String r16_above) {
			this.r16_above = r16_above;
		}
		
		
	}


	

	public ArrayList<Restposition> getRestpositions()
	{
		int len=date.length;
		
		
		ArrayList<Restposition> selrow=new ArrayList<Restposition>();
		try
		{
		
			
			if(len!=0)
			{
				for(int i=0; i < len ;i++)
				{
				RunningRoomReportForm.Restposition rowobj=this.new Restposition();
					try{
				
						
						rowobj.date = (date[i] == null ) ? "-" : date[i];
						rowobj.r1_4 = (r1_4[i] == null ) ? "-" : r1_4[i];
						rowobj.r4_6 = (r4_6[i] == null ) ? "-" : r4_6[i];
						rowobj.r6_8 = (r6_8[i] == null ) ? "-" : r6_8[i];
						rowobj.r8_10 = (r8_10[i] == null ) ? "-" : r8_10[i];
						rowobj.r10_12 = (r10_12[i] == null ) ? "-" : r10_12[i];
						rowobj.r12_14 = (r12_14[i] == null ) ? "-" : r12_14[i];
						rowobj.r14_16 = (r14_16[i] == null ) ? "-" : r14_16[i];
						rowobj.r16_above = (r16_above[i] == null ) ? "-" : r16_above[i];
						
					}catch(Exception e)
					{	
					e.printStackTrace();
					}
					
				selrow.add(rowobj);
				}
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		return selrow;
	} // GETMYROWS ENDS 


	
	
	
	

	

}

