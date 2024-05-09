package com.minima.meg.mainsite;

import java.io.PrintWriter;

public class header {

	public static void writeHeader(String zLevel, PrintWriter zOut) {
		
		zOut.write("<html>\r\n"
				+ "<head>\r\n"
				+ "	<title>MEG</title>\r\n"
				+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "	<link rel=\"stylesheet\" href=\"style.css\">\r\n"
				+ "</head>\r\n"
				+ "\r\n"
				+ "<body>\r\n"
				+ "\r\n"
				+ "<center>\r\n"
				+ "\r\n"
				+ "<table class=maintable border=0>\r\n"
				+ "\r\n"
				+ "<tr>\r\n"
				+ "	<td colspan=2>\r\n"
				+ "		<table class=titletable>\r\n"
				+ "			<tr>\r\n"
				+ "				<td width=100% nowrap>&nbsp;<b>Minima Enterprise Gateway</b></td>\r\n"
				+ "				<td nowrap><button class=solobutton onclick='location.href=\"logoff.html\"'>LOG OFF</button></td>\r\n"
				//+ "				<td nowrap><a class=menu href='logoff.html'>Log Off</a>&nbsp;&nbsp;</td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "	</td>\r\n"
				+ "</tr>\r\n"
				+ "\r\n"
				+ "<tr>\r\n"
				+ "	<td style=\"vertical-align:top;\">\r\n"
				+ "		<table class=menutable height=100%>\r\n"
				+ "			<tr><td nowrap>&nbsp;<a class=menu href='minimanode.html'>Minima Node</a>&nbsp;</td></tr>\r\n"
				+ "			<tr><td>&nbsp;</td></tr>\r\n"
				+ "			<tr><td nowrap>&nbsp;<a class=menu href='triggers.html'>Triggers</a>&nbsp;</td></tr>\r\n"
				+ "			<tr><td nowrap>&nbsp;<a class=menu href='apiendpoints.html'>Endpoints</a>&nbsp;</td></tr>\r\n"
				+ "			<tr><td nowrap>&nbsp;<a class=menu href='wallet.html'>Wallet API</a>&nbsp;</td></tr>\r\n"
				+ "			<tr><td>&nbsp;</td></tr>\r\n");
		
		//ONLY Admin gets the ADMIN tag
		if(zLevel.equals("admin")) {
			zOut.write(""
					+ "	<tr><td nowrap>&nbsp;<a class=menu href='logs.html'>Logs</a>&nbsp;</td></tr>\r\n"
					+ "	<tr><td nowrap>&nbsp;<a class=menu href='myprofile.html'>Profile</a>&nbsp;</td></tr>\r\n"
					+ "	<tr><td nowrap>&nbsp;<a class=menu href='admin.html'>Admin</a>&nbsp;</td></tr>\r\n"
					+ "	<tr><td>&nbsp;</td></tr>\r\n");
		}else {
			zOut.write(""
					+ "	<tr><td nowrap>&nbsp;<a class=menu href='logs.html'>Logs</a>&nbsp;</td></tr>\r\n"
					+ "	<tr><td nowrap>&nbsp;<a class=menu href='myprofile.html'>My Profile</a>&nbsp;</td></tr>\r\n"
					+ " <tr><td>&nbsp;</td></tr>\r\n");
		}
				
		zOut.write(
				"			<tr><td nowrap>&nbsp;<a class=menu href='help.html'>Help</a>&nbsp;</td></tr>\r\n"
				+ "			<tr><td height=100%>&nbsp;</td></tr>\r\n"
				+ "		</table>\r\n"
				+ "		\r\n"
				+ "	</td>\r\n"
				+ "	\r\n"
				+ "	<td class=mainview>\r\n"
				+ "		");
	}
}
