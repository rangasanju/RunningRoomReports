package com.tayal.reports.runningroom.action;





import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tayal.reports.runningroom.actionforms.RunningRoomForm;
import com.tayal.reports.runningroom.actionforms.RunningRoomReportForm;
import com.tayal.reports.utility.DBConnection;

// STEP 1 OF ROTATE IMAGE
//public class LoginAction extends DispatchAction implements WebcamImageTransformer{


public class RunningRoomAction extends DispatchAction{
	
	//private final BufferedImageOp filter = new JHFlipFilter(JHFlipFilter.FLIP_90CW);
	
	









public ActionForward initiateRunningRoomReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  initiateRunningRoomReport   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	RunningRoomReportForm fb = (RunningRoomReportForm) form;	
	
 		forward = mapping.findForward("ReportDates");
 	
 		populateLobbyDropDown(request);
 		
 		
 		
 		System.out.println(" Lobby : " + fb.getLobby_filter());
 		// POPULATE ROOM DROPDOWN
 		if(fb.getLobby_filter() != null)
 		{
 			if(!fb.getLobby_filter().equals("Select"))
 			{
 				populateRoomDropDown(request,fb.getLobby_filter());
 			}else
 	 		{
 				populateRoomDropDown(request,"NA");
 	 		}
 			
 		}
 		else
 		{
 			populateRoomDropDown(request,"NA");
 		}
 		
 		
 		System.out.println(" Room : " + fb.getRoom_no_filter());
 		//POPULATE BED DROPDOWN
 		
 		if(fb.getRoom_no_filter() != null)
 		{
 			if(!fb.getRoom_no_filter().equals("Select"))
 			{
 				populateBedDropDown(request,fb.getLobby_filter(), fb.getRoom_no_filter());
 			}else
 	 		{
 	 			populateBedDropDown(request,fb.getLobby_filter(), "NA");
 	 		}
 			
 		}
 		else
 		{
 			populateBedDropDown(request,fb.getLobby_filter(), "NA");
 		}
 		
 		
 		
 		
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  initiateRunningRoomReport   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}



public ActionForward runningRoomReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  runningRoomReport Reports  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	DBConnection db = new DBConnection(); 
	RunningRoomReportForm fb = (RunningRoomReportForm) form;
	String checkin_date;
	String checkout_date;
	
	String fromdate = fb.getFrom_date();
	String todate = fb.getTo_date();
	String lobby_filter = fb.getLobby_filter();
	String roomno_filter = fb.getRoom_no_filter();
	String bedno_filter = fb.getBed_no_filter();
	
	
	ArrayList<String> crewid = new ArrayList<String>();
	ArrayList<String> bedno = new ArrayList<String>();
	ArrayList<String> roomno = new ArrayList<String>();
	ArrayList<String> checkin = new ArrayList<String>();
	ArrayList<String> checkin_time = new ArrayList<String>();
	ArrayList<String> checkout = new ArrayList<String>();
	ArrayList<String> checkout_time = new ArrayList<String>();
	ArrayList<String> resthrs = new ArrayList<String>();
	ArrayList<String> wakeuptime = new ArrayList<String>();
	
	
	try{
		
		String query1="";
		String where_clause="";
		
		

		if(!roomno_filter.equals("Select"))
		{
			where_clause += " AND ROOM_NO_I=" + roomno_filter + " ";
		}
		
		
		if(!bedno_filter.equals("Select"))
		{
			where_clause += " AND BED_NO_I=" + bedno_filter + " ";
		}
		
		
		if(fromdate == null || todate == null || fromdate.trim().equals("") || todate.trim().equals(""))
		{
			query1 = "SELECT USER_ID_V,BED_NO_I,ROOM_NO_I,DATE_FORMAT(CHECK_IN_D,'%d-%m-%Y %H:%i') CHECK_IN_D, DATE_FORMAT(CHECK_OUT_D,'%d-%m-%Y %H:%i') CHECK_OUT_D FROM BED_ALLOCATION_MST WHERE LOCATION_ID_V='" + lobby_filter + "' AND USER_ID_V IS NOT NULL " +  where_clause;
		}
		else
		{
//			query1 = "SELECT USER_ID_V,BED_NO_I,ROOM_NO_I,DATE_FORMAT(CHECK_IN_D,'%d-%m-%Y %H:%i') CHECK_IN_D, DATE_FORMAT(CHECK_OUT_D,'%d-%m-%Y %H:%i') CHECK_OUT_D FROM BED_ALLOCATION_HIS"
//					+ " WHERE LOCATION_ID_V='" + lobby_filter + "' AND STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d') BETWEEN STR_TO_DATE('" + fromdate + "','%d/%m/%Y') AND STR_TO_DATE('" + todate + "','%d/%m/%Y') " + where_clause
//					+ " UNION SELECT USER_ID_V,BED_NO_I,ROOM_NO_I,DATE_FORMAT(CHECK_IN_D,'%d-%m-%Y %H:%i') CHECK_IN_D, DATE_FORMAT(CHECK_OUT_D,'%d-%m-%Y %H:%i') CHECK_OUT_D FROM BED_ALLOCATION_MST WHERE LOCATION_ID_V='" + lobby_filter + "' AND USER_ID_V IS NOT NULL "
//					+  where_clause;
			
			query1 = "SELECT USER_ID_V,BED_NO_I,ROOM_NO_I,DATE_FORMAT(CHECK_IN_D,'%d-%m-%Y %H:%i') CHECK_IN_D, DATE_FORMAT(CHECK_OUT_D,'%d-%m-%Y %H:%i') CHECK_OUT_D, TIMESTAMPDIFF(HOUR,CHECK_IN_D,CHECK_OUT_D) AS 'DIFF' FROM BED_ALLOCATION_HIS"
					+ " WHERE LOCATION_ID_V='" + lobby_filter + "' AND STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d') BETWEEN STR_TO_DATE('" + fromdate + "','%d/%m/%Y') AND STR_TO_DATE('" + todate + "','%d/%m/%Y') " + where_clause;
		
		}
			
		System.out.println("QUERY 1 : " + query1);
		
		
		
		ResultSet rs = db.executeQuery(query1);
		
		while(rs.next())
		{
			checkin_date = rs.getString("CHECK_IN_D");
			checkout_date = rs.getString("CHECK_OUT_D");
			
			//System.out.println("checkin_date : " + checkin_date.substring(0,checkin_date.indexOf(" ")) + " Time : " + checkin_date.substring(checkin_date.indexOf(" ")));
			//System.out.println("checkout_date : " + checkout_date.substring(0,checkout_date.indexOf(" ")) + " Time : " + checkout_date.substring(checkout_date.indexOf(" ")));
			
			crewid.add(rs.getString("USER_ID_V"));
			bedno.add(rs.getString("BED_NO_I"));
			roomno.add(rs.getString("ROOM_NO_I"));
			checkin.add(checkin_date.substring(0,checkin_date.indexOf(" ")));
			checkin_time.add(checkin_date.substring(checkin_date.indexOf(" ")));
			
			if(checkout_date == null)
			{
				checkout.add("-");
				checkout_time.add("-");
				resthrs.add("-");
			}
			else
			{
				checkout.add(checkout_date.substring(0,checkout_date.indexOf(" ")));
				checkout_time.add(checkout_date.substring(checkout_date.indexOf(" ")));
				resthrs.add(rs.getString("DIFF"));
			}
			wakeuptime.add("-");
			
		}		
		
		fb.setCrew_id((String[])crewid.toArray(new String[crewid.size()]));
		fb.setBed_no((String[])bedno.toArray(new String[bedno.size()]));;
		fb.setRoom_no((String[])roomno.toArray(new String[roomno.size()]));
		fb.setCheckin((String[])checkin.toArray(new String[checkin.size()]));
		fb.setCheckin_time((String[])checkin_time.toArray(new String[checkin_time.size()]));
		fb.setCheckout((String[])checkout.toArray(new String[checkout.size()]));
		fb.setCheckout_time((String[])checkout_time.toArray(new String[checkout_time.size()]));
		fb.setRest_hrs((String[])resthrs.toArray(new String[resthrs.size()]));
		fb.setWakeup_time((String[])wakeuptime.toArray(new String[wakeuptime.size()]));
		
		
	}catch(Exception e)
	{
		System.out.println("Ex : " + e);
		
	}
	
	String role = request.getSession().getAttribute("role").toString();
 
 		forward = mapping.findForward("RunningRoomReport");
 	
 
	
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  runningRoomReport   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}


public ActionForward initiateOccupancyCountReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  initiateOccupancyCountReport   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	forward = mapping.findForward("CountReportDates");
	populateLobbyDropDown(request);
 		
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  initiateOccupancyCountReport   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}




public ActionForward occupancyCountReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  occupancyCountReport   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	

	ActionForward forward = new ActionForward();
	DBConnection db = new DBConnection(); 
	RunningRoomReportForm fb = (RunningRoomReportForm) form;
	String checkin_date;
	String checkout_date;
	
	String fromdate = fb.getFrom_date();
	String todate = fb.getTo_date();
	String hourlydate = fb.getHourly_date();
	String frequency = fb.getFrequency();
	String location = fb.getLobby_filter();

	
	
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> hour = new ArrayList<String>();
	ArrayList<String> crewcount = new ArrayList<String>();
	
	
	try{
		
		String query1="";
		String where_clause="";
		
		
		
		
		if(frequency.equals("day"))
		{
			
			query1 = "SELECT CHECK_IN_D,SUM(COUNT1) AS COUNT FROM (" + 
					"	SELECT DATE(CHECK_IN_D) AS CHECK_IN_D, COUNT(OCCUPANCY_I) AS COUNT1 FROM BED_ALLOCATION_HIS WHERE OCCUPANCY_I=1 AND LOCATION_ID_V='" + location + "' "
				  + "   AND STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d %H:%i') BETWEEN STR_TO_DATE('" + fromdate + " 00:00','%d/%m/%Y %H:%i') AND STR_TO_DATE('" + todate + " 23:59','%d/%m/%Y %H:%i')  "
				  + "   GROUP BY DATE(CHECK_IN_D)" + 
					"	UNION ALL " + 
					"	SELECT DATE(CHECK_IN_D) AS CHECK_IN_D, COUNT(OCCUPANCY_I) AS COUNT1 FROM BED_ALLOCATION_MST WHERE OCCUPANCY_I=1 AND LOCATION_ID_V='" + location + "' "
				  + "   AND STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d %H:%i') BETWEEN STR_TO_DATE('" + fromdate + "00:00','%d/%m/%Y %H:%i') AND STR_TO_DATE('" + todate + " 23:59','%d/%m/%Y %H:%i')  "
				  + "   GROUP BY DATE(CHECK_IN_D)"
				  + "    ) AS U GROUP BY CHECK_IN_D ORDER BY 1";
			
			
			
		}
		else
		{			
			
			
			query1 = "SELECT HOUR,SUM(COUNT1) AS COUNT FROM (" + 
						"SELECT HOUR(CHECK_IN_D) AS HOUR, COUNT(*) AS COUNT1 FROM `bed_allocation_his` WHERE OCCUPANCY_I=1 AND LOCATION_ID_V='" + location + "' "
					  + "AND STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d') = STR_TO_DATE('" + hourlydate + "','%d/%m/%Y') GROUP BY HOUR(CHECK_IN_D) " 
					  + "UNION ALL " 
					  + "SELECT HOUR(CHECK_IN_D) AS HOUR, COUNT(*) AS COUNT1 FROM `bed_allocation_mst` WHERE OCCUPANCY_I=1 AND LOCATION_ID_V='" + location + "' " 
					  + "AND STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d') = STR_TO_DATE('" + hourlydate + "','%d/%m/%Y') GROUP BY HOUR(CHECK_IN_D) " 
					  +	"    ) AS U GROUP BY HOUR ORDER BY 1";
			
		}
		
			
		
		ResultSet rs = db.executeQuery(query1);
		
		
		if(frequency.equals("day"))
		{
			while(rs.next())
			{			
				date.add(rs.getString("CHECK_IN_D"));
				hour.add("-");
				crewcount.add(rs.getString("COUNT"));
			}		
			
		}
		else
		{	
			int counter = 0;
			
			if(rs != null)
			{
				rs.next();
				while(counter < 24)
				{			
					
					if(counter == rs.getInt("HOUR"))
					{
						date.add(hourlydate);
						
						if(counter == 0)
							hour.add("00 - " + (counter+1));
						else if(counter == 23)
							hour.add("" + counter + " - 00");
						else
							hour.add("" + counter + " - " + (counter+1));
						
						
						crewcount.add(rs.getString("COUNT"));
						if(!rs.isLast())
						{
							rs.next();
						}
							
						
					}					
					
					counter++;
				}		
		
			}
			
					
		}
		
	
		
		
		fb.setDate((String[])date.toArray(new String[date.size()]));
		fb.setHour((String[])hour.toArray(new String[hour.size()]));
		fb.setCrew_count((String[])crewcount.toArray(new String[crewcount.size()]));
		
	}catch(Exception e)
	{
		System.out.println("Ex : " + e);
		
	}
	
	String role = request.getSession().getAttribute("role").toString();
 
 		forward = mapping.findForward("OccupancyCountReport");
 	
 
	
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  occupancyCountReport   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}




public ActionForward initiateRunningRoomStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  initiateRunningRoomStatus   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	forward = mapping.findForward("RunningRoomStatusDisplay");
	
	
	RunningRoomReportForm rf = (RunningRoomReportForm) form;
	populateLobbyDropDown(request);

	rf.setLobby_filter("Select");
	
 		
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  initiateRunningRoomStatus   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}





public ActionForward getRunningRoomLayoutForAdmin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  getRunningRoomLayout   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	DBConnection db = new DBConnection(); 
	RunningRoomForm rf = (RunningRoomForm) form;
	PrintWriter out = response.getWriter();
	int rowcounter = 0;		
	boolean changerow = false;
	int bookedroom=0;
	int bookedbed=0;
	int floor = 0;
	
	HashMap<String,Integer> hmap = new HashMap<String,Integer>();
	String location = rf.getLobby_filter();
	
	try{
		String query1 = "SELECT * FROM BED_ALLOCATION_MST WHERE LOCATION_ID_V='" + location + "' ORDER BY ROOM_NO_I";
		ResultSet rs2 = db.executeQuery(query1);
		while(rs2.next())
		{
			
			hmap.put(rs2.getString("ROOM_NO_I") + rs2.getString("BED_NO_I"), rs2.getInt("OCCUPANCY_I"));
			
		}
		
	}catch(Exception e)
	{
		System.out.println("Ex : " + e);
		
	}
	
	
			
	String lt="";
	
	try{

    		    			
    		String query = "SELECT * FROM ROOM_MST WHERE LOCATION_ID_V='" + location + "' ORDER BY FLOOR_I,ROOM_NO_I";    		
    		
    		ResultSet rs = db.executeQuery(query);
    		
			
				lt +="<div class='col-sm-12'>";
    			lt +="<table class='table table-bordered' >";
    			lt +="<thead><tr><th bgcolor='#47d147' class='text-center' style='white-space: nowrap'>  GROUND FLOOR  </th></tr></thead>";
    			lt +="</table>";
    		
    		
    		while(rs.next())
    		{
    			int room = rs.getInt("ROOM_NO_I");
    			int cur_floor = rs.getInt("FLOOR_I");
    			int beds = rs.getInt("NO_OF_BEDS_I");
    			
    			
    			if(floor != cur_floor)
    			{
    				
    				lt +="<div class='col-sm-12'>";
        			lt +="<table class='table table-bordered' >";
        			lt +="<thead><tr><th bgcolor='#47d147' class='text-center' style='white-space: nowrap'> FLOOR " + cur_floor + " </th></tr></thead>";
        			lt +="</table>";
        			
        			floor = cur_floor;
    			}
    			
    			lt +="<div class='col-sm-" + beds + "'>";
    			lt +="<table class='table table-bordered' >";
    			lt +="<thead><tr><th bgcolor='#ffbf80' class='text-center' colspan='" + beds + "' style='white-space: nowrap'> Room " + room + " </th></tr></thead>";
    			//System.out.println("rowcounter  : " + rowcounter  + "Result : " +  (rowcounter % 3));
    			
    		
    			
    			lt += "<tbody><tr>"; 
    			
    			for(int z=0;z<beds;z++)
    			{
    				
    				String rb = room + "" + (z+1);
    				int oc = hmap.get(rb);
    				
    				
    				
    				lt += "<td bgcolor='#ffbf80'>"; 
//    				
//    				if(oc == 0)
//    					lt += "<table class='table table-bordered' id='" + room + "" + (z+1) + "'  onclick=selectBed('" + room + "','" + (z+1) + "') ><thead><tr  bgcolor='lightgreen'><th class='text-center'>" + (z+1) + "</th></tr><tbody><tr><td><span style='color:red' class='glyphicon glyphicon-off' onclick=\"block('-1','BED','" + room + "','" + (z+1) + "')\"></span></td></tr></tbody></table>";
//    				else if(oc == -1)
//    					lt += "<table class='table table-bordered' id='" + room + "" + (z+1) + "' ><thead onclick=getInfo('" + room + "','" + (z+1) + "')><tr  bgcolor='red'><th class='text-center'>" + (z+1) + "</th></tr><tbody><tr><td><span style='color:green' class='glyphicon glyphicon-off' onclick=\"block('0','BED','" + room + "','" + (z+1) + "')\"></span></td></tr></tbody></table>";
//    				else
//    					lt += "<table class='table table-bordered' id='" + room + "" + (z+1) + "' ><thead onclick=getInfo('" + room + "','" + (z+1) + "')><tr  bgcolor='pink'><th class='text-center'>" + (z+1) + "</th></tr><tbody><tr><td>&nbsp;</td></tr></tbody></table>";
//    				
    				
    				
    				
    				if(oc == 0)
    					lt += "<table class='table table-bordered' id='" + room + "" + (z+1) + "'  onclick=selectBed('" + room + "','" + (z+1) + "') ><thead><tr  bgcolor='lightgreen'><th class='text-center'>" + (z+1) + "</th></tr><tbody><tr><td></td></tr></tbody></table>";
    				else if(oc == -1)
    					lt += "<table class='table table-bordered' id='" + room + "" + (z+1) + "' ><thead onclick=getInfo('" + room + "','" + (z+1) + "')><tr  bgcolor='red'><th class='text-center'>" + (z+1) + "</th></tr><tbody><tr><td></td></tr></tbody></table>";
    				else
    					lt += "<table class='table table-bordered' id='" + room + "" + (z+1) + "' ><thead onclick=getInfo('" + room + "','" + (z+1) + "')><tr  bgcolor='pink'><th class='text-center'>" + (z+1) + "</th></tr><tbody><tr><td>&nbsp;</td></tr></tbody></table>";
    				
    				
    				
    				lt += "</td>"; 
    				
    			}
    			lt += "</tr>";
    			lt += "</tbody>"; 
    			lt += "</table>";
    			lt += "</div>";
    			
    			
    			
    			
    			
    			
    		}
    		
    		
    		 
    		System.out.println("lt : " + lt);
        	rs.close();
        
        	 out.println(lt);
        	 out.flush();
        	     
        
}catch(Exception e)
{
	System.out.println("Ex : " + e);
	e.printStackTrace();
	
}finally
{
	 db.closeCon();
	 
}

	
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  getRunningRoomLayout   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return null;



}



public ActionForward initiateMaxMinposition(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  initiateMaxMinposition   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	forward = mapping.findForward("MaxMinPositionDates");
	populateLobbyDropDown(request);
 		
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  initiateMaxMinposition   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}






public ActionForward getMaxMinPosition(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  getMaxMinPosition   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	

	ActionForward forward = new ActionForward();
	DBConnection db = new DBConnection(); 
	RunningRoomReportForm fb = (RunningRoomReportForm) form;
	
	String fromdate = fb.getFrom_date();
	String todate = fb.getTo_date();
	String location = fb.getLobby_filter();

	
	
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> max_hour = new ArrayList<String>();
	ArrayList<String> min_hour = new ArrayList<String>();
	ArrayList<String> max_count = new ArrayList<String>();
	ArrayList<String> min_count = new ArrayList<String>();
	
	
	try{
		
		String query1="";		
	
		query1 = "SELECT DATE(CHECK_IN_D) CHECK_IN_D,  COUNT(OCCUPANCY_I) AS COUNT FROM BED_ALLOCATION_HIS WHERE OCCUPANCY_I=1 AND LOCATION_ID_V='" + location + "' AND "
					+ "STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d') BETWEEN STR_TO_DATE('" + fromdate + "','%d/%m/%Y') AND STR_TO_DATE('" + todate + "','%d/%m/%Y')  "
					+ "GROUP BY DATE(CHECK_IN_D)";
		
		
			
		String inner_query="";
		ResultSet rs = db.executeQuery(query1);
		while(rs.next())
		{			
			
			inner_query = "SELECT HOUR(CHECK_IN_D) AS HOUR, COUNT(*) AS COUNT FROM `bed_allocation_his` WHERE OCCUPANCY_I=1 AND LOCATION_ID_V='" + location + "' AND STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d') = STR_TO_DATE('" + rs.getString("CHECK_IN_D") + "','%Y-%m-%d') GROUP BY HOUR(CHECK_IN_D)";
			
			ResultSet in_rs = db.executeQuery(inner_query);
			int max = 0;
			int min = 10000;
			
			int maxhour = 0;
			int minhour = 0;
			
			while(in_rs.next())
			{
				if(in_rs.getInt("COUNT") > max )
				{
					max = in_rs.getInt("COUNT");
					maxhour = in_rs.getInt("HOUR");
				}
					
				if(in_rs.getInt("COUNT") < min )
				{
					min = in_rs.getInt("COUNT");
					minhour = in_rs.getInt("HOUR");
				}
					
				
			}
			
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
			String checkin = simpleDateFormat.format(rs.getDate("CHECK_IN_D"));
			
			
			date.add(checkin);
			max_hour.add("" + maxhour + ":00 - " + (maxhour+1) + ":00 HRS" );
			min_hour.add("" + minhour + ":00 - " + (minhour+1) + ":00 HRS" );
			max_count.add("" + max);
			min_count.add("" + min);
			
			
		}		
		
				
		
		fb.setDate((String[])date.toArray(new String[date.size()]));
		fb.setMax_hour((String[])max_hour.toArray(new String[max_hour.size()]));
		fb.setMin_hour((String[])min_hour.toArray(new String[min_hour.size()]));
		fb.setMax_count((String[])max_count.toArray(new String[max_count.size()]));
		fb.setMin_count((String[])min_count.toArray(new String[min_count.size()]));
		
	}catch(Exception e)
	{
		e.printStackTrace();
		
	}
	
	String role = request.getSession().getAttribute("role").toString();
 
 		forward = mapping.findForward("MaxMinPositionReport");
 	
 
	
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  getMaxMinPosition   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}





public ActionForward initiateRestPosition(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  initiateRestPosition   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	forward = mapping.findForward("RestPositionDates");
	populateLobbyDropDown(request);
 		
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  initiateRestPosition   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}





public ActionForward getRestPosition(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  getRestPosition  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	DBConnection db = new DBConnection(); 
	RunningRoomReportForm fb = (RunningRoomReportForm) form;
	String checkin_date;
	String checkout_date;
	
	String fromdate = fb.getFrom_date();
	String todate = fb.getTo_date();
	String lobby_filter = fb.getLobby_filter();
	
	ArrayList<String> date = new ArrayList<String>();
	ArrayList<String> r1_4 = new ArrayList<String>();
	ArrayList<String> r4_6 = new ArrayList<String>();
	ArrayList<String> r6_8 = new ArrayList<String>();
	ArrayList<String> r8_10 = new ArrayList<String>();
	ArrayList<String> r10_12 = new ArrayList<String>();
	ArrayList<String> r12_14 = new ArrayList<String>();
	ArrayList<String> r14_16 = new ArrayList<String>();
	ArrayList<String> r16_above = new ArrayList<String>();
	
	
	try{
		
		String query1="";
		String where_clause="";
		
		

	
		
		
		if(fromdate == null || todate == null || fromdate.trim().equals("") || todate.trim().equals(""))
		{
			query1 = "SELECT DATE_FORMAT(CHECK_IN_D,'%d-%m-%Y') AS CHECK_IN_D_STR, TIMESTAMPDIFF(HOUR,CHECK_IN_D,NOW()) AS 'DIFF' FROM BED_ALLOCATION_MST WHERE LOCATION_ID_V='" + lobby_filter + "' AND USER_ID_V IS NOT NULL ORDER BY CHECK_IN_D";
		}
		else
		{
			query1 = "SELECT DATE_FORMAT(CHECK_IN_D,'%d-%m-%Y') AS CHECK_IN_D_STR, TIMESTAMPDIFF(HOUR,CHECK_IN_D,CHECK_OUT_D) AS 'DIFF' FROM BED_ALLOCATION_HIS"
					+ " WHERE LOCATION_ID_V='" + lobby_filter + "' AND STR_TO_DATE(CHECK_IN_D,'%Y-%m-%d') BETWEEN STR_TO_DATE('" + fromdate + "','%d/%m/%Y') AND STR_TO_DATE('" + todate + "','%d/%m/%Y') ORDER BY CHECK_IN_D";
		
		}
			
		
		
		
		
		ResultSet rs = db.executeQuery(query1);
		String row_date="";
		
		if(rs.next())
		{
			row_date=rs.getString("CHECK_IN_D_STR");
			date.add(row_date);
			rs.beforeFirst();
		}
		

		int r14=0;
		int r46=0;
		int r68=0;
		int r810=0;
		int r1012=0;
		int r1214=0;
		int r1416=0;
		int r16=0;
		
		
		while(rs.next())
		{
			
			checkin_date = rs.getString("CHECK_IN_D_STR");
			
			System.out.println("row_date : " + row_date);
			System.out.println("checkin_date : " + checkin_date);

			if(!row_date.trim().equals(checkin_date.trim()))
			{
				
				System.out.println(">>>>>>>>>>> row_date : " + row_date);
				System.out.println(">>>>>>>>>>> checkin_date : " + checkin_date);
				// ADD THE ROW
				r1_4.add(""+r14);
				r4_6.add(""+r46);
				r6_8.add(""+r68);
				r8_10.add(""+r810);
				r10_12.add(""+r1012);
				r12_14.add(""+r1214);
				r14_16.add(""+r1416);
				r16_above.add(""+r16);
				
				
				
				// RESET COUNTERS
				 r14=0;
				 r46=0;
				 r68=0;
				 r810=0;
				 r1012=0;
				 r1214=0;
				 r1416=0;
				 r16=0;
				
				// START THE NEXT ROW
				date.add(checkin_date);
				
				row_date=checkin_date;
				
			}
			
			
				int hours = rs.getInt("DIFF");
				
				if( hours > 0 && hours <= 4)
					r14++;
				else if( hours > 4 && hours <= 6)
					r46++;
				else if( hours > 6 && hours <= 8)
					r68++;
				else if( hours > 8 && hours <= 10)
					r810++;
				else if( hours > 10 && hours <= 12)
					r1012++;
				else if( hours > 12 && hours <= 14)
					r1214++;
				else if( hours > 14 && hours <= 16)
					r1416++;
				else if( hours > 16)
					r16++;
					
		}		
		
		
		r1_4.add(""+r14);
		r4_6.add(""+r46);
		r6_8.add(""+r68);
		r8_10.add(""+r810);
		r10_12.add(""+r1012);
		r12_14.add(""+r1214);
		r14_16.add(""+r1416);
		r16_above.add(""+r16);
		
		
		fb.setDate((String[])date.toArray(new String[date.size()]));
		fb.setR1_4((String[])r1_4.toArray(new String[r1_4.size()]));
		fb.setR4_6((String[])r4_6.toArray(new String[r4_6.size()]));;
		fb.setR6_8((String[])r6_8.toArray(new String[r6_8.size()]));
		fb.setR8_10((String[])r8_10.toArray(new String[r8_10.size()]));
		fb.setR10_12((String[])r10_12.toArray(new String[r10_12.size()]));
		fb.setR12_14((String[])r12_14.toArray(new String[r12_14.size()]));
		fb.setR14_16((String[])r14_16.toArray(new String[r14_16.size()]));
		fb.setR16_above((String[])r16_above.toArray(new String[r16_above.size()]));
		
		
		
	}catch(Exception e)
	{
		System.out.println("Ex : " + e);
		
	}
	
	
 	forward = mapping.findForward("RestPositionReport");
 	
 
	
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  getRestPosition   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return (forward);



}



public void populateLobbyDropDown(HttpServletRequest request)
{

	
	 // POPULATE DROPDOWNS
	DBConnection db = new DBConnection();
	String division = request.getSession().getAttribute("division").toString();
	ArrayList<String> lobbylist = new ArrayList<String>();
	 try{
		 
		     String query = "SELECT * FROM RUNNINGROOM_LIST WHERE DIVISION_ID_V='" + division + "' ORDER BY RUNNINGROOM_ID_V";
	 		 ResultSet rs  = db.executeQuery(query);	
	 		 
	 		lobbylist.add("Select");
	 		 while(rs.next())
	 		 {
	 			lobbylist.add(rs.getString("RUNNINGROOM_ID_V").trim());
	 		 }
	 		
	 		 request.setAttribute("lobbylist", lobbylist);	
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		 db.closeCon();
	 }

}

public void populateRoomDropDown(HttpServletRequest request, String location)
{
	
	
	 // POPULATE DROPDOWNS
	DBConnection db = new DBConnection();
	 ArrayList<String> roomlist = new ArrayList<String>();
	
	 try{
		 
		 
		 
		 if(location.equals("NA"))
		 {
			 roomlist.add("Select");
		 }
		 else
		 {
			 
			 String crewid_query = "SELECT DISTINCT ROOM_NO_I FROM bed_allocation_mst WHERE LOCATION_ID_V='" + location + "' ORDER BY ROOM_NO_I";			
			 ResultSet rs1  = db.executeQuery(crewid_query);	
			
			 // CREW LIST DROPDOWN
			 
			 roomlist.add("Select");
			 while(rs1.next())
			 {
				 roomlist.add(rs1.getString("ROOM_NO_I"));
			 }
		 }

		 
	
		 request.setAttribute("roomlist", roomlist);
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		 db.closeCon();
	 }
	 
}



public void populateBedDropDown(HttpServletRequest request, String location, String roomno)
{
	
	
	 // POPULATE DROPDOWNS
	DBConnection db = new DBConnection();
	ArrayList<String> bedlist = new ArrayList<String>();
	
	
	
	 try{
		 
		 if(roomno.equals("NA"))
		 {
			 bedlist.add("Select");
		 }
		 else
		 {

			 String crewid_query = "SELECT DISTINCT BED_NO_I FROM bed_allocation_mst WHERE LOCATION_ID_V='" + location + "' AND ROOM_NO_I=" + roomno + " ORDER BY ROOM_NO_I";
			 ResultSet rs1  = db.executeQuery(crewid_query);	
			
			 // CREW LIST DROPDOWN
			 
			 bedlist.add("Select");
			 while(rs1.next())
			 {
				 bedlist.add(rs1.getString("BED_NO_I"));
			 }
			 

		 }
		 
		 
		 
		 request.setAttribute("bedlist", bedlist);
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		 db.closeCon();
	 }

	 
	 
	 
}







public ActionForward getGuestInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  RunningRoomAction - getGuestInfo   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	ActionForward forward = new ActionForward();
	DBConnection db = new DBConnection(); 
	RunningRoomForm rf = (RunningRoomForm) form;
	String room = rf.getBookedroom();
	String bed = rf.getBookedbed();
	
	PrintWriter out = response.getWriter();
	
	String crewid = "";
	String checkin = "";
	String name = "";
	String desig = "";
	String mobile = "";
	String occupancy="";
	String location = rf.getLobby_filter();
	
	
	try{
		String query1 = "SELECT USER_ID_V,CHECK_IN_D,OCCUPANCY_I FROM BED_ALLOCATION_MST WHERE LOCATION_ID_V='" + location + "' AND BED_NO_I=" + bed + " AND ROOM_NO_I=" +room;
		
		ResultSet rs2 = db.executeQuery(query1);
		
		if(rs2.next())
		{
			crewid = rs2.getString("USER_ID_V");
			checkin = rs2.getString("CHECK_IN_D");
			
			
			if (rs2.getInt("OCCUPANCY_I") == 0)
				occupancy = "Available";
			else if (rs2.getInt("OCCUPANCY_I") == 1)
				occupancy = "Occupied";
			else
				occupancy = "Blocked";
		}					
		
		
	}catch(Exception e)
	{
		System.out.println("Ex : " + e);
		
	}
	
	

	try{
		String query1 = "SELECT FIRST_NAME_V,LAST_NAME_V,DESIG_V,MOBILE1_I FROM Crew_Biodata WHERE USER_ID_V='" + crewid + "'";
		ResultSet rs2 = db.executeQuery(query1);
		
		if(rs2.next())
		{
			name  = rs2.getString("FIRST_NAME_V") + " " + rs2.getString("LAST_NAME_V");
			desig = rs2.getString("DESIG_V");
			mobile = rs2.getString("MOBILE1_I");
		}					
		
		
	}catch(Exception e)
	{
		System.out.println("Ex : " + e);
		
	}
	
	String result = "";
	
	if(crewid == null)
	{
		result = "Room : " + room + " Bed : " + bed + "\n"
				+ "===================\n\n"
				+ "Bed Status : " + occupancy + "\n"	;		
	}
	else
	{
		result = "Room : " + room + " Bed : " + bed + "\n"
				+ "===================\n\n"
				+ "Bed Status : " + occupancy + "\n"			
				+ "Crew ID : " + crewid + " \n"
				+ "Name : " + name + " \n"
				+ "Desig : " + desig + " \n"
				+ "Check In Date : " + checkin.substring(0,10) + " \n"
				+ "Check In Time : " + checkin.substring(11,16) + " \n"
				+ "Mobile : " + mobile;
	}
	
	
	
	 out.println(result);
	 out.flush();
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  RunningRoomAction - getGuestInfo   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");
	
	 
    
    return null;



}





}
















