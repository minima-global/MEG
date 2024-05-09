package com.minima.meg.endpoints.node;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.JettyServer;

public class minimanode extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h2>MINIMA NODE</h2>");
		
		//What is the current Host
		String mhost 	= MegDB.getDB().getPrefsDB().getMinimaNode();
		String meghost 	= MegDB.getDB().getPrefsDB().getMEGNode();
		
		zOut.println("Minima needs to talk to MEG and vice versa<br>");
		zOut.println("<br>You can enable SSL via STUNNEL or any other SSL Proxy<br>");
		
		//Add new User
		zOut.println("<h3>Host Details</h3>");
		
		zOut.println(""
				+ "<form action=\"setnode.html\" method=\"post\">\r\n"
				+ "		<table>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>MEG Host : </td>\r\n"
				+ "				<td><input size=50 value='"+meghost+"' type=text name=megip></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr><td colspan=2 style='text-align:right;font-size:12;'>Your local MEG address is http://127.0.0.1:"+JettyServer.MEG_PORT+"/</td></tr>"
				+ "			<tr><td>&nbsp;</td></tr>"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>MINIMA Host : </td>\r\n"
				+ "				<td><input size=50 value='"+mhost+"' type=text name=hostip></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr><td colspan=2 style='text-align:right;font-size:12;'>Minima RPC host:port</td></tr>"
				+ "			<tr><td>&nbsp;</td></tr>"
				+ "			<tr>\r\n"
				+ "				<td colspan=2 class=newuserform><input class='solobutton' type=submit value=\"Update Node Details\"></td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		</form>");
		
		zOut.println("<br>"
				+ "<center>"
				+ "<button class='solobutton' onclick=\"location.href='setnode.html?checkonly=1'\">TEST CONNECTION</button>"
				+ "</center>");
	}
}
