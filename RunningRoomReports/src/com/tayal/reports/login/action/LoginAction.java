package com.tayal.reports.login.action;




import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tayal.reports.login.actionforms.LoginForm;
import com.tayal.reports.utility.DBConnection;
import com.tayal.reports.utility.AESencrp;

// STEP 1 OF ROTATE IMAGE
//public class LoginAction extends DispatchAction implements WebcamImageTransformer{


public class LoginAction extends DispatchAction{
	
	//private final BufferedImageOp filter = new JHFlipFilter(JHFlipFilter.FLIP_90CW);
	
	
	
	


public ActionForward Execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - Execute >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	
	 LoginForm fb = (LoginForm) form;
	 fb.setMessage("");
	 
	 
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - Execute <<<<<<<<<<<<<<<<<<<<<<");
	  
    forward = mapping.findForward("Admin");
    return (forward);
}




public ActionForward home(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - home >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - home <<<<<<<<<<<<<<<<<<<<<<");
	  
    forward = mapping.findForward("Master");
    return (forward);
}





public ActionForward initiateLogin(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
throws Exception{

System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - initiateLogin >>>>>>>>>>>>>>>>>>>>>>");
 ActionForward forward = new ActionForward();

 LoginForm lf = (LoginForm) form;
 String userid = lf.getUser_id();
 String pwd = lf.getPassword();
 
 
  
  DBConnection db = new DBConnection();
 
  String role="MONITOR";
  
  lf.setMessage("");
  
		 
 // lf.setMessage("");
  
  
	 try{
		 
		 ResultSet rs  = db.executeQuery("SELECT * FROM user_mast WHERE USER_ID_V='" + userid + "'");				
		 
		if(rs.next())
		{
			role = rs.getString("ROLE_V");
			String decryppass = AESencrp.decrypt(rs.getString("PASSWORD_V"));
			System.out.println("Provided : " + pwd);
			System.out.println("Role : " + role);

			if(role.equals("REPORTSADMIN"))
			{
				if(decryppass.equals(pwd))
				{
								
					HttpSession session = request.getSession(true);
					
					session.setAttribute("username", userid);
					session.setAttribute("division", rs.getString("LOCATION_ID_V"));
					session.setAttribute("role", role);
					
					System.out.println("username : " + userid);
					forward = mapping.findForward("Master");
				}
				else
				{
					lf.setMessage("Invalid user id / password");
					forward = mapping.findForward("Admin");
				}
			}else
			{
				lf.setMessage("Invalid user id / password");
				forward = mapping.findForward("Admin");
				
			}
			
			
		
		}else
		{
			lf.setMessage("Invalid user id / password");
			forward = mapping.findForward("Admin");
			
		}
		
	
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		 db.closeCon();
	 }
	

System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - initiateLogin <<<<<<<<<<<<<<<<<<<<<<");
  

return (forward);
}




public ActionForward LogoutAdmin(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - LogoutAdmin >>>>>>>>>>>>>>>>>>>>>>");
	ActionForward forward = new ActionForward();
	
	 request.getSession().invalidate();
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - LogoutAdmin <<<<<<<<<<<<<<<<<<<<<<");
	  
    forward = mapping.findForward("Home");
    return (forward);
}



public ActionForward LogoutKiosk(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - LogoutKiosk >>>>>>>>>>>>>>>>>>>>>>");
	ActionForward forward = new ActionForward();
	String mac_address = request.getSession().getAttribute("mac_address").toString();
	request.getSession().invalidate();
	
	HttpSession session = request.getSession(true);
	session.setAttribute("mac_address", mac_address);
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - LogoutKiosk <<<<<<<<<<<<<<<<<<<<<<");
	  
    forward = mapping.findForward("ReKiosk");
    return (forward);
}



public ActionForward requestAccess(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - requestAccess >>>>>>>>>>>>>>>>>>>>>>");
	ActionForward forward = new ActionForward();
	
	 
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - requestAccess <<<<<<<<<<<<<<<<<<<<<<");
	  
    forward = mapping.findForward("RequestAccess");
    return (forward);
}



public ActionForward saveRequestAccess(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - saveRequestAccess >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	
	 LoginForm fb = (LoginForm) form;
	 
	 
	 String division = fb.getDivision_code();
	 String lobby = fb.getLobby_code();
	 String mobile = fb.getMobile();
	 String macaddress = fb.getMac_address();
	 
	 DBConnection db = new DBConnection();
	 
	 
	 try{
		 
		 String query = "INSERT INTO REQUEST_ACCESS (DIVISION_CODE_V, LOBBY_CODE_V, MOBILE_V,MAC_ADDRESS_V) VALUES('" + division + "','" + lobby + "','" + mobile + "','" + macaddress + "')";
		 System.out.println("Query  : " + query);
		 int rs  = db.executeUpdate(query);				
		
				 
		 if(rs == 1)
			 fb.setMessage("Request submitted successfully");
		 else
			 fb.setMessage("Request failed");
		
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		 db.closeCon();
	 }
	
	 
	
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - saveRequestAccess <<<<<<<<<<<<<<<<<<<<<<");
	  
    forward = mapping.findForward("Home");
    return (forward);
}





public ActionForward initiateChangePass(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - initiateChangePass >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	 LoginForm lf = (LoginForm) form;
	 
	 lf.setPassword("");
	 lf.setOldpassword("");
	 lf.setRe_password("");
	 
	 String role = request.getSession().getAttribute("role").toString();
	 
	 if(role.equals("ADMIN"))
		 forward = mapping.findForward("ChangePass");
	 else 
		 forward = mapping.findForward("ChangePin");
	 
	 
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - initiateChangePass <<<<<<<<<<<<<<<<<<<<<<");
	  
   
    return (forward);
}





public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
{
	
	 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  changePassword   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	
	 PrintWriter out = response.getWriter();
	 LoginForm lf = (LoginForm) form;
	 
	  ActionForward forward = new ActionForward();
	  
	  
	  DBConnection db = new DBConnection();
	  
	 String password = lf.getPassword(); 
	 String oldpassword = lf.getOldpassword();
	 String re_password = lf.getRe_password();
	 
	 String username = (String) request.getSession().getAttribute("username");
	 
	

		try{
			       
			
			String query = "SELECT * FROM user_mast WHERE USER_ID_V='" + username + "'";
			System.out.println("Query  : " + query);
			ResultSet rs = db.executeQuery(query);
		        
			if(rs.next())
			{
				String decryppass = AESencrp.decrypt(rs.getString("password_v"));
				
				System.out.println("decryppass : " + decryppass);
				System.out.println("oldpassword : " + oldpassword);
				
				if(oldpassword.equals(decryppass))
				{	
					
					db.executeUpdate("UPDATE user_mast SET PASSWORD_V='" + AESencrp.encrypt(password) + "' WHERE USER_ID_V='" + username + "'");
					request.getSession().invalidate();
					lf.setPassword(null);
					lf.setOldpassword(null);
					lf.setRe_password(null);
					
					out.println("SUCCESS");
		        	out.flush();	
		        	db.closeCon();
		        	return null;
				}
				else
				{
					out.println("Old password does not match");
		        	out.flush();	
		        	db.closeCon();
		        	lf.setPassword(null);
					lf.setOldpassword(null);
					lf.setRe_password(null);
		        	return null;
				}
			}else
			{
				out.println("Invalid Username / Password");
	        	out.flush();	
	        	db.closeCon();
	        	lf.setPassword(null);
				lf.setOldpassword(null);
				lf.setRe_password(null);
	        	return null;
			}
			
			
		        
		}catch(Exception e)
		{
			System.out.println("Ex : " + e);
		}
		
		lf.setPassword("");
		 lf.setRe_password("");
		 lf.setOldpassword("");
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  changePassword   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	System.out.println("\n\n\n\n\n");

	
	db.closeCon();
	
	lf.setPassword(null);
	lf.setOldpassword(null);
	lf.setRe_password(null);
	return null;


}


public ActionForward getLocation(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - getLocation >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	 PrintWriter out = response.getWriter();
	 
	 LoginForm fb = (LoginForm) form;
	 
	 String location="";
	 String division="";
	 //fb.setMac_address("E4-B3-18-BD-21-D6");
	 
	  DBConnection db = new DBConnection();
	  HttpSession session = request.getSession(true);
	
		 try{
			 
			ResultSet rs  = db.executeQuery("SELECT * FROM mac_location_mapping WHERE MAC_ADDRESS_V='" + fb.getMac_address() + "'");
			 
			if(rs.next())
			{
				division = rs.getString("DIVISION_ID_V");
				location = rs.getString("LOCATION_ID_V");	
					
				fb.setLocation(location);
				session.setAttribute("location", location);	
				session.setAttribute("division", division);
				out.write(location);
				
			}else
			{
				out.write("UA");
									
			}
			
			 
		 }catch(Exception e)
		 {
			 System.out.println("Error : " + e);
		 }
		 finally
		 {		
			 db.closeCon();
		 }
		

	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - getLocation <<<<<<<<<<<<<<<<<<<<<<");
	  
    return null;
}






public ActionForward initiateAdmin(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - initiateAdmin >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	 
	 
	 LoginForm fb = (LoginForm) form;
	 fb.setUser_id("");
	 fb.setPassword("");
	 fb.setCrewid("");
	 
	 String location="";
	 String division="";
	 //fb.setMac_address("E4-B3-18-BD-21-D6");
	 
	  DBConnection db = new DBConnection();
	  HttpSession session = request.getSession(true);
	
		 try{
			 
			ResultSet rs  = db.executeQuery("SELECT * FROM mac_location_mapping WHERE MAC_ADDRESS_V='" + fb.getMac_address() + "'");
			 
			if(rs.next())
			{
				division = rs.getString("DIVISION_ID_V");
				location = rs.getString("LOCATION_ID_V");	
					
				fb.setLocation(location);
				session.setAttribute("location", location);	
				session.setAttribute("division", division);	
				
				forward = mapping.findForward("Admin");
				
			}else
			{
				fb.setMessage("Unauthorised Access");
				forward = mapping.findForward("Home");
							
			}
			
			 
		 }catch(Exception e)
		 {
			 System.out.println("Error : " + e);
		 }
		 finally
		 {		
			 db.closeCon();
		 }
		

	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - initiateAdmin <<<<<<<<<<<<<<<<<<<<<<");
	  
    return (forward);
}





public ActionForward initiateSuperAdmin(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - initiateSuperAdmin >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	 	
	 forward = mapping.findForward("SuperAdmin");
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - initiateSuperAdmin <<<<<<<<<<<<<<<<<<<<<<");
	  
    return (forward);
}







public ActionForward initiateSuperLogin(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
throws Exception{

System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - initiateSuperLogin >>>>>>>>>>>>>>>>>>>>>>");
 ActionForward forward = new ActionForward();

 LoginForm lf = (LoginForm) form;
 String userid = lf.getUser_id();
 String pwd = lf.getPassword();
 
 
  
  DBConnection db = new DBConnection();
 
    
  lf.setMessage("");
  
		 
 // lf.setMessage("");
  
  
	 try{
		 
		 ResultSet rs  = db.executeQuery("SELECT * FROM user_mast WHERE USER_ID_V='" + userid + "' AND ROLE_V='SUPERSR'");				
		 
		if(rs.next())
		{
			
			String decryppass = AESencrp.decrypt(rs.getString("PASSWORD_V"));
			
			
			if(decryppass.equals(pwd))
			{
							
				HttpSession session = request.getSession(true);				
				
				System.out.println("username : " + userid);
				forward = mapping.findForward("SuperMaster");
			}
			else
			{
				lf.setMessage("Invalid user id / password");
				forward = mapping.findForward("SuperAdmin");
			}
		}else
		{
			lf.setMessage("Invalid user id / password");
			forward = mapping.findForward("SuperAdmin");
			
		}
		
	
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		 db.closeCon();
	 }
	

System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - initiateSuperLogin <<<<<<<<<<<<<<<<<<<<<<");
  

return (forward);
}




public ActionForward validateUser(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
throws Exception{

System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - validateUser >>>>>>>>>>>>>>>>>>>>>>");

 LoginForm lf = (LoginForm) form;
 String userid = lf.getUser_id();
 String pwd = lf.getPassword();
 
  DBConnection db = new DBConnection();
  String location="";
  String role="MONITOR";
  String desig="";
 
	
  System.out.println("userid : " + userid);
	 try{
		 PrintWriter out = response.getWriter();
		 ResultSet rs  = db.executeQuery("SELECT * FROM user_mast WHERE USER_ID_V='" + userid + "'");				
		 
		if(rs.next())
		{
			role = rs.getString("ROLE_V");
			location = rs.getString("LOCATION_ID_V");
			String decryppass = AESencrp.decrypt(rs.getString("PASSWORD_V"));
			
			//if(decryppass.equals(pwd))
			//{
								
				HttpSession session = request.getSession(true);
				
				session.setAttribute("username", userid);
				session.setAttribute("location", location);
				session.setAttribute("role", role);
				System.out.println("location : " + location);
				 
				out.println("VALID");
				out.flush();
				
				
			//}
			//else
			//{
			//	out.println("Invalid user ID / Pin");
			//	out.flush();
			//}
		}else
		{			
			out.println("Invalid user ID / Pin");
			out.flush();
		}
		
	
		 rs  = db.executeQuery("SELECT DESIG_V FROM CREW_BIODATA WHERE USER_ID_V='" + userid + "'");				
		 
			if(rs.next())
			{
				desig = rs.getString("DESIG_V");
				
					HttpSession session = request.getSession(true);					
					session.setAttribute("designation", desig);
					System.out.println("desig : " + desig);
						
				
			}else
			{			
				out.println("Invalid user ID / Pin");
				out.flush();
			}
		
		
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		 db.closeCon();
	 }
	
	 
System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - validateUser <<<<<<<<<<<<<<<<<<<<<<");
  

return null;
}




public ActionForward logout(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
throws Exception{

System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - logout >>>>>>>>>>>>>>>>>>>>>>");
 ActionForward forward = new ActionForward();


 
  DBConnection db = new DBConnection();
  forward = mapping.findForward("Admin");
  
	 try{
		 
		 HttpSession session = request.getSession(true);
			
			session.invalidate();
			
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		 db.closeCon();
	 }
	

System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - logout <<<<<<<<<<<<<<<<<<<<<<");
  

return (forward);
}




public ActionForward getAccessRequests(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - getAccessRequests >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	
	
	
	 DBConnection db = new DBConnection();

	 try{
		 
		 
		 PrintWriter out = response.getWriter();
		 
		 //String location = request.getSession().getAttribute("location").toString();
			
			
			
		 String res = "<table class='table table-striped table-bordered table-condensed table-responsive'>"
		 		+ "<thead><tr>"
		 		+ "<th class='text-center' style='white-space: nowrap'>Division</th>"
		 		+ "<th class='text-center' style='white-space: nowrap'>Lobby</th>"
		 		+ "<th class='text-center' style='white-space: nowrap'>Mobile</th>"
		 		+ "<th class='text-center' style='white-space: nowrap'>Grant</th>"
		 		+ "</tr></thead>"
		 		+ "<tbody>";
		 

		 String query = "SELECT * FROM REQUEST_ACCESS";
		 System.out.println("Query  : " + query);
		 ResultSet rs  = db.executeQuery(query);				
		 
		 while(rs.next())
		 {
			 
			 res+= "<tr>";
			 res+= "<td align='center' > " + rs.getString("DIVISION_CODE_V") + "</td>";
			 res+= "<td align='center' > " + rs.getString("LOBBY_CODE_V") + "</td>";
			 res+= "<td align='center' > " + rs.getString("MOBILE_V") + "</td>";
			 res+= "<td align='center' ><img src='images/righttick.jpg' height='20' width='20' onclick=\"allowAccess('" + rs.getString("MAC_ADDRESS_V") + "','" + rs.getString("DIVISION_CODE_V") + "')\"></td>";
			 res+= "</tr>";
			
		 }
		 
		 res+= "</tbody>";
		 res+= "</table>";
		 System.out.println("Res  : " + res);
		 out.println(res);
		 out.flush();
		
		
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		// db.closeCon();
	 }
	
	 


	 
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - getAccessRequests <<<<<<<<<<<<<<<<<<<<<<");
	  
    
    return null;
}




public ActionForward allowAccess(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 throws Exception{
	
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> LoginAction - allowAccess >>>>>>>>>>>>>>>>>>>>>>");
	 ActionForward forward = new ActionForward();
	
	
LoginForm fb = (LoginForm) form;	
DBConnection db = new DBConnection();
String division = fb.getDivision_code();
String macaddress = fb.getMac_address();

String res = "FAIL";

	 try{
		 
		 PrintWriter out = response.getWriter();
		
			
		 String query = "INSERT INTO MAC_LOCATION_MAPPING VALUES('" + macaddress + "','" + division + "')";
		 	
		 System.out.println("Query  : " + query);
		 int rs  = db.executeUpdate(query);		
		 
		
		 System.out.println("Res  : " + rs);
		 
		 if(rs > 0)
		 {
			 res = "SUCCESS";
			 query = "DELETE FROM REQUEST_ACCESS WHERE MAC_ADDRESS_V='" + macaddress + "'";
			 int drs  = db.executeUpdate(query);		
		 }			 
		 else
			 res = "FAIL";
		 
		 System.out.println("Res  : " + res);

		 out.println(res);
		 out.flush();
		
		
		 
	 }catch(Exception e)
	 {
		 System.out.println("Error : " + e);
	 }
	 finally
	 {		
		// db.closeCon();
	 }
	
	 


	 
	
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<< LoginAction - allowAccess <<<<<<<<<<<<<<<<<<<<<<");
	  
    
    return null;
}





}
















