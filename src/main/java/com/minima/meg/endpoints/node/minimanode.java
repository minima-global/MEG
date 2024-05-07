package com.minima.meg.endpoints.node;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;

public class minimanode extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h2>MINIMA NODE</h2>");
		
		//What is the current Host
		String mhost = MegDB.getDB().getPrefsDB().getMinimaNode();
		
		//Add new User
		zOut.println("<h3>Node host</h3>");
		
		zOut.println("<form action=\"setnode.html\" method=\"post\">\r\n"
				+ "		<table>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Host IP : </td>\r\n"
				+ "				<td><input size=50 value='"+mhost+"' type=text name=hostip></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td colspan=2 class=newuserform><input type=submit value=\"Update Minima Node\"></td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		</form>");
		
		zOut.println("<br>"
				+ ""
				+ "<button onclick=\"location.href='setnode.html?checkonly=1'\">TEST CONNECTION</button>");
	}
}
